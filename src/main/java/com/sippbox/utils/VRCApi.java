package com.sippbox.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonParser;

public class VRCApi {
    private final HttpClient client;

    public VRCApi() {
        this.client = HttpClient.newHttpClient();
    }

    public int getPlayerCount() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.vrchat.cloud/api/1/visits"))
                .build();

        String responseBody = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        return parse(responseBody);
    }

    private int parse(String responseBody) {
        return JsonParser.parseString(responseBody).getAsInt();
    }
}