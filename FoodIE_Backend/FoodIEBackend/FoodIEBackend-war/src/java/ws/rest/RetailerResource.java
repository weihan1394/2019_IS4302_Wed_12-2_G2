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
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
@Path("retailer")
public class RetailerResource {

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
     * Creates a new instance of RetailerResource
     */
    public RetailerResource() {
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
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveGoods")
    public Response retrieveGoods() {
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
                    if (jsonObj.get("retailer") == null
                            || !jsonObj.get("retailer").getAsString().equalsIgnoreCase("resource:org.is4302foodie.Retailer#" + companyId) //                            || !jsonObj.get("status").getAsString().equalsIgnoreCase("unprocessed")
                            ) {
                        i.remove();
                    } else {
                        String distributor = jsonObj.get("distributor").getAsString();
                        distributor = distributor.substring(distributor.lastIndexOf("#") + 1);
                        jsonObj.remove("distributor");
                        jsonObj.addProperty("distributor", distributor);
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
    @Path(value = "collectBatch")
    public Response collectBatch(@QueryParam("batchId") String batchId) {
        JsonObject jsonObject;
        List<Object> lsReturn = httpParse.getReturnObject(request, batchId, Transaction.CONFIRM_COLLECTION);
        String companyId = lsReturn.get(1).toString();
        String hash = lsReturn.get(2).toString();
        LoggedInUserRecordTransactionEntity loggedInUserRecordTransactionEntity = (LoggedInUserRecordTransactionEntity) lsReturn.get(0);
        if (batchId != null) {
            try {
                URL url = new URL(baseURL + "org.is4302foodie.RetailerConfirmCollection");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                jsonObject = new JsonObject();
                jsonObject.addProperty("$class", "org.is4302foodie.RetailerConfirmCollection");
                jsonObject.addProperty("finishedGood", "resource:org.is4302foodie.FinishedGood#" + batchId);
                jsonObject.addProperty("retailer", "resource:org.is4302foodie.Retailer#" + companyId);
                jsonObject.addProperty("hashTransaction", hash);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(jsonObject.toString());
                wr.flush();

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
            jsonObject.addProperty("message", "Invalid Batch Id");
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveBatchById")
    public Response retrieveBatchById(@QueryParam("batchId") String batchId) {
        System.err.println(batchId);
        JsonObject jsonObject;
        String companyId = httpParse.getCompanyId(request);
        try {
            URL url = new URL(baseURL + "org.is4302foodie.FinishedGood/" + batchId);
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
                String farmer, retailer, producer, distributor;
                farmer = obj.get("sourceOwner").getAsString();
                farmer = farmer.substring(farmer.lastIndexOf("#") + 1);
                distributor = obj.get("distributor").getAsString();
                distributor = distributor.substring(distributor.lastIndexOf("#") + 1);
                producer = obj.get("producer").getAsString();
                producer = producer.substring(producer.lastIndexOf("#") + 1);
                retailer = obj.get("retailer").getAsString();
                retailer = retailer.substring(retailer.lastIndexOf("#") + 1);
                obj.remove("sourceOwner");
                obj.remove("distributor");
                obj.remove("producer");
                obj.remove("retailer");
                obj.addProperty("sourceOwner", farmer);
                obj.addProperty("distributor", distributor);
                obj.addProperty("producer", producer);
                obj.addProperty("retailer", retailer);

                return Response.status(Response.Status.OK).entity(obj.toString()).build();
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
}
