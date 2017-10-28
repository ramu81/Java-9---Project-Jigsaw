package edu.http_client.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public final class HttpClient {

	public static void main(String[] args) {
		try {
			get();
		} catch (URISyntaxException | IOException | InterruptedException e) {
			throw new RuntimeException("Unable to run Java 9 Http Client examples", e);
		}
	}

	static void get() throws URISyntaxException, IOException, InterruptedException {
		final HttpRequest request = HttpClientHelper.getRequest(HttpClientHelper.CLIENT,
				new URI("http://www.infoq.com"), HttpClientHelper.HEADERS);

		final HttpResponse<String> response = HttpClientHelper.CLIENT.send(request,
				HttpResponse.BodyHandler.asString());

		HttpClientHelper.printResponse(response, "'Get': 'http://www.infoq.com'");
	}
}
