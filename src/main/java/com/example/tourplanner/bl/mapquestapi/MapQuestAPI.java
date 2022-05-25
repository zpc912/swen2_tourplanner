package com.example.tourplanner.bl.mapquestapi;

import com.example.tourplanner.bl.ConfigurationManager;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MapQuestAPI {

    // Method to receive route information about the tour:
    public static Map<String, Object> getRouteDirections(String from, String to, String transportMode) {
        try {
            String consumerKey = ConfigurationManager.getConfigProperty("ConsumerKey");
            String requestData = "?key=" + consumerKey + "&from=" + from + "&to=" + to + "&transportMode=" + transportMode;

            if(transportMode.equals("WALKING")) {
                requestData += "&routeType=pedestrian";
            }
            else if(transportMode.equals("BICYCLE")) {
                requestData += "&routeType=bicycle";
            }

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://www.mapquestapi.com/directions/v2/route" + requestData)).build();

            CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = httpResponse.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);

            Map<String, Object> route = new HashMap<>();

            try {
                route.put("distance", jsonNode.get("route").get("distance").floatValue());
                route.put("formattedTime", jsonNode.get("route").get("formattedTime").textValue());
                route.put("session", jsonNode.get("route").get("sessionId").textValue());

                // "ul" and "lr" from boundingBox:
                JsonNode ul = jsonNode.get("route").get("boundingBox").get("ul");
                JsonNode lr = jsonNode.get("route").get("boundingBox").get("lr");
                route.put("boundingBox", ul.get("lat").floatValue() + "," + ul.get("lng").floatValue() + "," + lr.get("lat").floatValue() + "," + lr.get("lng").floatValue());

                return route;
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        } catch(IOException | ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        return null;
    }


    // Method to receive the static map (= image) of the tour:
    public static byte[] getStaticMap(String from, String to, String transportMode) {
        try {
            Map<String, Object> route = MapQuestAPI.getRouteDirections(from, to, transportMode);

            if(route != null) {
                String consumerKey = ConfigurationManager.getConfigProperty("ConsumerKey");
                String requestData = "?key=" + consumerKey
                        + "&size=900,330"
                        + "&defaultMarker=none"
                        + "&session=" + route.get("session")
                        + "&boundingBox=" + route.get("boundingBox")
                        + "&to=" + to;

                HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://open.mapquestapi.com/staticmap/v5/map" + requestData)).build();

                HttpResponse<byte[]> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

                return httpResponse.body();
            }
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
