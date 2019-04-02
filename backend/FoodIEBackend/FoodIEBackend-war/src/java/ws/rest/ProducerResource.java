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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.UUID;
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

/**
 * REST Web Service
 *
 * @author User
 */
@Path("producer")
public class ProducerResource {

    @Context
    private UriInfo context;

    private final Gson gson;

    private final String baseURL;


    /**
     * Creates a new instance of ProducerResource
     */
    public ProducerResource() {
        gson = new Gson();
        baseURL = "http://localhost:3002/api/";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveCropsByProducer")
    public Response retrieveCropsByProducer(@QueryParam("email") String email) {
        // need to check token first
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
                        Object object = i.next();
                        if (object == null) {
                            break;
                        }
                        JsonObject jsonObj = (JsonObject) object;
                        if (jsonObj.get("producer") == null || !jsonObj.get("producer").getAsString().equalsIgnoreCase("resource:org.is4302foodie.Producer#IinFoodSupply")) {
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
        } else {
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", "invalid email");
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "retrieveFinishedByProducer")
    public Response retrieveFinishedByProducer(@QueryParam("email") String email) {
        // need to check token first
        JsonObject jsonObject;
        if (email != null) {
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
                        if (jsonObj.get("producer") == null || !jsonObj.get("producer").getAsString().equalsIgnoreCase("resource:org.is4302foodie.Producer#IinFoodSupply")) {
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
        } else {
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", "invalid email");
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
        System.err.println(jsonObject);
        JsonArray jsonArray = jsonObject.getAsJsonArray("batches");
        String cropId = jsonObject.get("cropId").getAsString();
        String token = "abc";
        String producer = "IinFoodSupply";
        try {

            int responseCode = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                URL url = new URL(baseURL + "org.is4302foodie.ProducerCreateProcessedBatch/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                System.err.println(jsonArray);
                JsonObject batchObj = gson.fromJson(jsonArray.get(i), JsonObject.class);
                System.err.println(batchObj);
                batchObj.addProperty("goodId", UUID.randomUUID().toString());
                batchObj.addProperty("quantity", batchObj.get("weight").getAsString());
                batchObj.addProperty("hashTransaction", token);
                batchObj.addProperty("crop", "resource:org.is4302foodie.Crop#" + cropId);
                batchObj.addProperty("producer", "resource:org.is4302foodie.Producer#" + producer);
                batchObj.remove("distributorId");
                batchObj.remove("weight");
                batchObj.remove("retailerId");
                System.err.println(batchObj);
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
                    throw new Exception("Error response");
                }
                conn.disconnect();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            jsonObject = new JsonObject();
            jsonObject.addProperty("message", ex.getMessage());
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(jsonObject.toString()).build();
        }

//        if(createBatchesReq != null) {
//            for(String batch: createBatchesReq.getBatches()) {
//                System.out.println(batch);
//            }
//        }
        JsonObject jsonMessage = new JsonObject();
        jsonMessage.addProperty("success", "Transaction added successfully");
        return Response.status(Response.Status.OK).entity(jsonMessage.toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(value = "collectCrop")
    public Response collectCrop(@QueryParam("cropId") String cropId) {
        // need to check token first
        JsonObject jsonObject;
        if (cropId != null) {
            String producer = "IinFoodSupply";
            String hash = "abc";
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
                jsonObject.addProperty("producer", "resource:org.is4302foodie.Crop#" + producer);
                jsonObject.addProperty("hashTransaction", hash);
                

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

                    System.out.println(obj);
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
}
