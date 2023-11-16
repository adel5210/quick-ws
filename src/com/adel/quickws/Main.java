package com.adel.quickws;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static final ExecutorService EXECUTOR = Executors.newWorkStealingPool();

	public static void main(String[] args) throws IOException {
		System.out.println("Starting java ws ...");
		final HttpServer server = HttpServer.create(
						new InetSocketAddress("localhost", 8082), 0
		);
		server.createContext("/ping", exchange -> {
			try(final OutputStream stream = exchange.getResponseBody()){
				final String responseBody = "pong";
				exchange.sendResponseHeaders(200, responseBody.length());
				stream.write(responseBody.getBytes());
				stream.flush();
			}
		});
		server.setExecutor(EXECUTOR);
		server.start();
	}
}
