package ws.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ejb.session.stateless.ActorUserControllerLocal;
import ejb.session.stateless.LoggedInUserRecordEntityControllerLocal;
import ejb.session.stateless.LoggedInUserRecordTransactionEntityController;
import ejb.session.stateless.LoggedInUserRecordTransactionEntityControllerLocal;
import entity.ActorUserEntity;
import entity.LoggedInUserRecordEntity;
import entity.LoggedInUserRecordTransactionEntity;
import entity.payload.ActorUserJWT;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.enumeration.Transaction;
import util.enumeration.TransactionStatus;
import util.exception.UserActorNotFoundException;
import util.inject.LookupController;
import util.library.HttpParse;
import util.security.HashHelper;

@Path("farmer")
public class FarmerResource {

    @Context
    private UriInfo context;

    @Inject
    private HttpServletRequest request;

    private final LookupController lookupController;

    private final HashHelper hashHelper;

    private final ActorUserControllerLocal actorUserControllerLocal;

    private final LoggedInUserRecordEntityControllerLocal loggedInUserRecordEntityControllerLocal;

    private final LoggedInUserRecordTransactionEntityControllerLocal loggedInUserRecordTransactionEntityControllerLocal;

    private final HttpParse httpParse;

    private final Gson gson;
    ServletRequest servletRequest;
    
    private final String baseURL;

    /**
     * Creates a new instance of FarmerResource
     */
    public FarmerResource() {
        lookupController = new LookupController();
        hashHelper = new HashHelper();
        actorUserControllerLocal = lookupController.lookupActorUserControllerLocal();
        loggedInUserRecordEntityControllerLocal = lookupController.lookupLoggedInUserRecordEntityControllerLocal();
        loggedInUserRecordTransactionEntityControllerLocal = lookupController.lookupLoggedInUserRecordTransactionEntityControllerLocal();
        gson = new Gson();
        httpParse = new HttpParse();
        baseURL = "http://localhost:3001/api/";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "HelloWorld")
    public String HelloWorld() throws UserActorNotFoundException, NoSuchAlgorithmException {
        List<String> lsName = new ArrayList<>();
        lsName.add("test1");
        lsName.add("test2");
        lsName.add("test3");

        String jsonStr = gson.toJson(lsName);
        List<Object> lsReturn = httpParse.getReturnObject(request, jsonStr, Transaction.CREATE_CROP);
        String companyId = lsReturn.get(1).toString();
        LoggedInUserRecordTransactionEntity loggedInUserRecordTransactionEntity = (LoggedInUserRecordTransactionEntity) lsReturn.get(0);

        System.out.println("yes:" + companyId);
        loggedInUserRecordTransactionEntity.setOutcomeTransaction(TransactionStatus.PASS);
        loggedInUserRecordTransactionEntityControllerLocal.updateTransactionStatus(loggedInUserRecordTransactionEntity);

        return jsonStr;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveCropsByFarmer")
    public Response retrieveCropsByFarmer(@QueryParam("email") String email) {
        // need to check token first
        String companyId = httpParse.getCompanyId(request);
        JsonObject jsonObject;
        if (email != null) {
            try {
                URL url = new URL(baseURL + "org.is4302foodie.Crop");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");

                conn.connect();
                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    JsonArray jsonArray = gson.fromJson(out.toString(), JsonArray.class);
                    Iterator i = jsonArray.iterator();
                    while (i.hasNext()) {
                        JsonObject obj = (JsonObject) i.next();
                        if (!obj.get("farmer").getAsString().equalsIgnoreCase("resource:org.is4302foodie.Farmer#" + companyId)) {
                            i.remove();
                        } else {
                            String producerId = obj.get("producer").getAsString();
                            producerId = producerId.substring(producerId.lastIndexOf("#") + 1);
                            obj.remove("producer");
                            obj.addProperty("producer", producerId);
                        }
                    }
                    return Response.status(Response.Status.OK).entity(jsonArray.toString()).build();
                } else {
                    throw new Exception("Error response");
                }

            } catch (Exception ex) {
                jsonObject = new JsonObject();
                jsonObject.addProperty("message", ex.getMessage());
                return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
            }
        } else {
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", "invalid email");
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(value = "createCrop")
    public Response createCrop(String createCropsReq) {
        JsonObject jsonObject = gson.fromJson(createCropsReq, JsonObject.class);
        System.out.println(jsonObject);

        List<Object> lsReturn = httpParse.getReturnObject(request, createCropsReq, Transaction.CREATE_CROP);
        String companyId = lsReturn.get(1).toString();
        String hash = lsReturn.get(2).toString();
        System.err.println(companyId);
        LoggedInUserRecordTransactionEntity loggedInUserRecordTransactionEntity = (LoggedInUserRecordTransactionEntity) lsReturn.get(0);

        String cropId = UUID.randomUUID().toString();
        String producer = jsonObject.get("producer").getAsString();
        jsonObject.addProperty("$class", "org.is4302foodie.FarmerCreateBatch");
        jsonObject.addProperty("cropId", cropId);
        jsonObject.addProperty("collects", "NOT_YET_COLLECTED");
        jsonObject.addProperty("hashTransaction", hash);
        jsonObject.addProperty("farmer", "resource:org.is4302foodie.Farmer#" + companyId);
        jsonObject.remove("producer");
        jsonObject.addProperty("producer", "resource:org.is4302foodie.Producer#" + producer);

        System.err.println(jsonObject);

        try {
            URL url = new URL(baseURL + "org.is4302foodie.FarmerCreateBatch");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonObject.toString());
            wr.flush();

            //weihan: hash the body and save (call)
            //weihan:add the hash of the body to hyperledger
            int responseCode = conn.getResponseCode();

            System.err.println(responseCode);
            if (responseCode == 200) {
                jsonObject = new JsonObject();
                jsonObject.addProperty("success", "Crop successfully created");
                loggedInUserRecordTransactionEntity.setOutcomeTransaction(TransactionStatus.PASS);
                loggedInUserRecordTransactionEntityControllerLocal.updateTransactionStatus(loggedInUserRecordTransactionEntity);
                return Response.status(Response.Status.OK).entity(jsonObject.toString()).build();
                // weihan: update transaction entity to say success
            } else {
                throw new Exception("Error response");
            }

        } catch (Exception ex) {
            loggedInUserRecordTransactionEntity.setOutcomeTransaction(TransactionStatus.FAIL);
            loggedInUserRecordTransactionEntityControllerLocal.updateTransactionStatus(loggedInUserRecordTransactionEntity);
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", ex.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
            // weihan: update transaction entity to say fail
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "retrieveAllProducers")
    public Response retrieveAllProducers() {
        JsonObject jsonObject;
        try {
            URL url = new URL(baseURL + "org.is4302foodie.Producer");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                JsonArray jsonArray = gson.fromJson(out.toString(), JsonArray.class);
                return Response.status(Response.Status.OK).entity(jsonArray.toString()).build();
            } else {
                throw new Exception("Error response");
            }

        } catch (Exception ex) {
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", ex.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
        }
    }
}
