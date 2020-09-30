package aplicacao;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

public class Main {

	public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
            server.createContext("/enderecos", new EnderecoHttpHandler());
            server.setExecutor(threadPoolExecutor);
            server.start();
            Logger.getLogger("Server started on port 8001");

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
