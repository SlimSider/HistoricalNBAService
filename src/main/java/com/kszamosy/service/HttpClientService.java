package com.kszamosy.service;

import com.kszamosy.exception.InternalErrorException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

@Service
public class HttpClientService {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public Optional<String> get(String uri, String... headers) {
        var req = initRequestBuilder(uri, headers).GET().build();
        return sendRequest(req);
    }

    private HttpRequest.Builder initRequestBuilder(String uri, String... headers) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .headers(headers)
                .timeout(Duration.ofSeconds(30));
    }

    private Optional<String> sendRequest(HttpRequest req) {
        try {
            var res = CLIENT.send(req, HttpResponse.BodyHandlers.ofString());
            if (String.valueOf(res.statusCode()).startsWith("4") || String.valueOf(res.statusCode()).startsWith("5")) {
                return Optional.empty();
            }

            return Optional.of(res.body());
        } catch (IOException | InterruptedException e) {
            throw new InternalErrorException("Error during request", e);
        }
    }
}
