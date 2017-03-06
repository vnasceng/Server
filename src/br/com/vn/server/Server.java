package br.com.vn.server;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
public class Server {
	public static final String BASE_API_URI = "http://localhost:8080/api/";
	
	  public boolean getFileCacheEnabled() {
	        return false;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
        final HttpServer httpServer = server.startServer();
        System.out.println("Press enter to stop the server...");
        try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        httpServer.shutdownNow();

	}
	
	public HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("br.com.vn.server");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_API_URI), rc);
        server.getServerConfiguration().addHttpHandler(getHttpHandler(), "/page");
        return server;
    }

    public HttpHandler getHttpHandler() {
        StaticHttpHandler handler = new StaticHttpHandler("src/main/resources/webapp/");
        handler.setFileCacheEnabled(getFileCacheEnabled());
        return handler;
    }

}
