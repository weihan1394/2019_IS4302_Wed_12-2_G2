/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ejb.session.stateless.ActorUserControllerLocal;
import ejb.session.stateless.LoggedInUserRecordEntityControllerLocal;
import ejb.session.stateless.LoggedInUserRecordTransactionEntityControllerLocal;
import entity.LoggedInUserRecordTransactionEntity;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
import util.inject.LookupController;
import util.library.HttpParse;
import util.security.HashHelper;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("producer")
public class ProducerResource {

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
     * Creates a new instance of ProducerResource
     */
    public ProducerResource() {
        lookupController = new LookupController();
        hashHelper = new HashHelper();
        actorUserControllerLocal = lookupController.lookupActorUserControllerLocal();
        loggedInUserRecordEntityControllerLocal = lookupController.lookupLoggedInUserRecordEntityControllerLocal();
        loggedInUserRecordTransactionEntityControllerLocal = lookupController.lookupLoggedInUserRecordTransactionEntityControllerLocal();
        gson = new Gson();
        httpParse = new HttpParse();
        baseURL = "http://localhost:3002/api/";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveCropsByProducer")
    public Response retrieveCropsByProducer() {
        // need to check token first
        JsonObject jsonObject;
        String companyId = httpParse.getCompanyId(request);
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
                    Object object = i.next();
                    if (object == null) {
                        break;
                    }
                    JsonObject jsonObj = (JsonObject) object;
                    if (jsonObj.get("producer") == null
                            || !jsonObj.get("producer").getAsString().equalsIgnoreCase("resource:org.is4302foodie.Producer#" + companyId)
                            || jsonObj.get("collects").getAsString().equals("CONVERTED")) {
                        i.remove();
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
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveFinishedByProducer")
    public Response retrieveFinishedByProducer() {
        JsonObject jsonObject;
        String companyId = httpParse.getCompanyId(request);
        try {
            URL url = new URL(baseURL + "org.is4302foodie.FinishedGood");
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
                    Object object = i.next();
                    if (object == null) {
                        break;
                    }
                    JsonObject jsonObj = (JsonObject) object;
                    if (jsonObj.get("producer") == null
                            || !jsonObj.get("producer").getAsString().equalsIgnoreCase("resource:org.is4302foodie.Producer#" + companyId)) {
                        i.remove();
                    } else {
                        String farmer = jsonObj.get("sourceOwner").getAsString();
                        farmer = farmer.substring(farmer.lastIndexOf("#") + 1);
                        jsonObj.remove("sourceOwner");
                        jsonObj.addProperty("sourceOwner", farmer);
                    }
                }
                return Response.status(Response.Status.OK).entity(jsonArray.toString()).build();
            } else {
                throw new Exception("Error response");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", ex.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveCropById")
    public Response retrieveCropById(@QueryParam("cropId") String cropId) {
        // need to check token first
        JsonObject jsonObject;
        if (cropId != null) {
            try {
                URL url = new URL(baseURL + "org.is4302foodie.Crop/" + cropId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");

                conn.connect();
                int responseCode = conn.getResponseCode();
                System.err.println(responseCode);
                if (responseCode == 200) {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    JsonObject obj = gson.fromJson(out.toString(), JsonObject.class);

                    return Response.status(Response.Status.OK).entity(obj.toString()).build();
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
    @Path(value = "createBatches")
    public Response createBatches(String createBatchesReq) {
        JsonObject jsonObject = gson.fromJson(createBatchesReq, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("batches");
        String cropId = jsonObject.get("cropId").getAsString();

        try {
            int responseCode = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject batchObj = gson.fromJson(jsonArray.get(i), JsonObject.class);
                List<Object> lsReturn = httpParse.getReturnObject(request, batchObj.toString(), Transaction.CREATE_BATCHES);
                String companyId = lsReturn.get(1).toString();
                String hash = lsReturn.get(2).toString();
                LoggedInUserRecordTransactionEntity loggedInUserRecordTransactionEntity = (LoggedInUserRecordTransactionEntity) lsReturn.get(0);

                URL url = new URL(baseURL + "org.is4302foodie.ProducerCreateProcessedBatch/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                String distributor = batchObj.get("distributor").getAsString();
                String retailer = batchObj.get("retailer").getAsString();;
                batchObj.addProperty("goodId", UUID.randomUUID().toString());
                batchObj.addProperty("quantity", batchObj.get("weight").getAsString());
                batchObj.addProperty("hashTransaction", hash);
                batchObj.addProperty("crop", "resource:org.is4302foodie.Crop#" + cropId);
                batchObj.addProperty("producer", "resource:org.is4302foodie.Producer#" + companyId);
                batchObj.remove("distributor");
                batchObj.remove("weight");
                batchObj.remove("retailer");
                batchObj.addProperty("distributor", "resource:org.is4302foodie.Distributor#" + distributor);
                batchObj.addProperty("retailer", "resource:org.is4302foodie.Retailer#" + retailer);

                System.err.println("batch: " + batchObj);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(batchObj.toString());
                wr.flush();
                wr.close();
                responseCode = conn.getResponseCode();
                System.err.println(responseCode);
                if (responseCode == 200) {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    reader.close();
                    loggedInUserRecordTransactionEntity.setOutcomeTransaction(TransactionStatus.PASS);
                    loggedInUserRecordTransactionEntityControllerLocal.updateTransactionStatus(loggedInUserRecordTransactionEntity);
//                    JsonObject obj = gson.fromJson(out.toString(), JsonObject.class);

//                return Response.status(Response.Status.OK).entity(obj.toString()).build();
                } else {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    System.err.println(out);
                    loggedInUserRecordTransactionEntity.setOutcomeTransaction(TransactionStatus.FAIL);
                    loggedInUserRecordTransactionEntityControllerLocal.updateTransactionStatus(loggedInUserRecordTransactionEntity);
                    throw new Exception("Error response");
                }
                conn.disconnect();
            }

            JsonObject jsonMessage = new JsonObject();
            jsonMessage.addProperty("success", "Transaction added successfully");
            return Response.status(Response.Status.OK).entity(jsonMessage.toString()).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", ex.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "collectCrop")
    public Response collectCrop(@QueryParam("cropId") String cropId) {
        JsonObject jsonObject;
        List<Object> lsReturn = httpParse.getReturnObject(request, cropId, Transaction.COLLECT_CROP);
        String companyId = lsReturn.get(1).toString();
        String hash = lsReturn.get(2).toString();
        LoggedInUserRecordTransactionEntity loggedInUserRecordTransactionEntity = (LoggedInUserRecordTransactionEntity) lsReturn.get(0);
        if (cropId != null) {
            try {
                URL url = new URL(baseURL + "org.is4302foodie.ProducerTransferBatch");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                jsonObject = new JsonObject();
                jsonObject.addProperty("$class", "org.is4302foodie.ProducerTransferBatch");
                jsonObject.addProperty("crop", "resource:org.is4302foodie.Crop#" + cropId);
                jsonObject.addProperty("producer", "resource:org.is4302foodie.Producer#" + companyId);
                jsonObject.addProperty("hashTransaction", hash);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(jsonObject.toString());
                wr.flush();

//                conn.connect();
                int responseCode = conn.getResponseCode();
                System.err.println(responseCode);
                if (responseCode == 200) {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    JsonObject obj = gson.fromJson(out.toString(), JsonObject.class);
                    loggedInUserRecordTransactionEntity.setOutcomeTransaction(TransactionStatus.PASS);
                    loggedInUserRecordTransactionEntityControllerLocal.updateTransactionStatus(loggedInUserRecordTransactionEntity);
                    System.out.println(obj);
                    return Response.status(Response.Status.OK).entity(obj.toString()).build();
                } else {
                    throw new Exception("Error response");
                }

            } catch (Exception ex) {
                loggedInUserRecordTransactionEntity.setOutcomeTransaction(TransactionStatus.FAIL);
                loggedInUserRecordTransactionEntityControllerLocal.updateTransactionStatus(loggedInUserRecordTransactionEntity);
                jsonObject = new JsonObject();
                jsonObject.addProperty("message", ex.getMessage());
                return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
            }
        } else {
            loggedInUserRecordTransactionEntity.setOutcomeTransaction(TransactionStatus.FAIL);
            loggedInUserRecordTransactionEntityControllerLocal.updateTransactionStatus(loggedInUserRecordTransactionEntity);
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", "Invalid Crop Id");
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveAllDistributors")
    public Response retrieveAllDistributors() {
        JsonObject jsonObject;
        try {
            URL url = new URL(baseURL + "org.is4302foodie.Distributor");
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveAllRetailers")
    public Response retrieveAllRetailers() {
        // need to check token first
        JsonObject jsonObject;
        try {
            URL url = new URL(baseURL + "org.is4302foodie.Retailer");
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
