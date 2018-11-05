package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    private HttpServer server;
    private HttpHandler handler;
    private int port = 8000;

    public SimpleHttpServer(int port) {
        this.port = port;
    }

    public void createServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        handler = new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                BufferedReader br = new BufferedReader(new FileReader("./www" + httpExchange.getRequestURI().getPath()));
                httpExchange.sendResponseHeaders(200, 0);
                OutputStream os = httpExchange.getResponseBody();
                String line = "";
                while ((line = br.readLine()) != null) {
                    os.write(line.getBytes());
                    os.write("\n".getBytes());
                }
                os.close();
            }
        };

        server.createContext("/", handler);
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public void closeServer() throws Exception {
        server.stop(0);
    }
}
