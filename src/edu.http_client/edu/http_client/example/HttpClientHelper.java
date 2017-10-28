package edu.http_client.example;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpRequest.Builder;
import jdk.incubator.http.HttpResponse;

final class HttpClientHelper {

    static final HttpClient CLIENT = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
    static final Map<String, String> HEADERS = new HashMap<>();
    static {
	HEADERS.put("Accept", "application/json");
	HEADERS.put("Content-Type", "text/plain");
    }


    static HttpRequest getRequest(final HttpClient client, final URI uri, final Map<String, String> headers) {
	assert Objects.nonNull(client) && Objects.nonNull(uri) && Objects.nonNull(headers);

	Builder builder = HttpRequest.newBuilder().version(client.version()).uri(uri).GET();

	fillHeaders(builder, headers);
	return builder.build();
    }

  

    static void printResponse(final HttpResponse<String> response, final String message) {
	assert Objects.nonNull(response) && Objects.nonNull(message);

	System.out.printf("%s\nStatus code : %d\n %s\n%s", message, response.statusCode(), response.body(),
		"-----\n\n");
    }

    private static void fillHeaders(final Builder builder, final Map<String, String> headers) {
	assert Objects.nonNull(builder) && Objects.nonNull(headers);

	headers.forEach((k, v) -> builder.header(k, v));
    }

}
