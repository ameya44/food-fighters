package com.bitlu.foodfightersapi.foodfighters.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        System.out.println("----- HTTP REQUEST -----");
        System.out.println("URI         : " + request.getURI());
        System.out.println("Method      : " + request.getMethod());
        System.out.println("Headers     : " + request.getHeaders());
        System.out.println("Body        : " + new String(body, StandardCharsets.UTF_8));

        ClientHttpResponse originalResponse = execution.execute(request, body);
        return new CustomBufferedClientHttpResponse(originalResponse);
    }

    private static class CustomBufferedClientHttpResponse implements ClientHttpResponse {
        private final ClientHttpResponse response;
        private final byte[] body;

        public CustomBufferedClientHttpResponse(ClientHttpResponse response) throws IOException {
            this.response = response;
            this.body = response.getBody().readAllBytes();

            System.out.println("----- HTTP RESPONSE -----");
            System.out.println("Status code  : " + response.getStatusCode());
            System.out.println("Status text  : " + response.getStatusText());
            System.out.println("Response body: " + new String(body, StandardCharsets.UTF_8));
        }

        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(body);
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return response.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public void close() {
            response.close();
        }

        @Override
        public HttpHeaders getHeaders() {
            return response.getHeaders();
        }
    }
}
