package aplicacao;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import controller.CidadeController;

public class CidadeHttpHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		
		if (null == httpExchange.getRequestMethod()) {

		} else
			switch (httpExchange.getRequestMethod()) {
			case "GET":
				handleGetRequest(httpExchange);
			case "POST":
				handlePostRequest(httpExchange);
			case "PUT":
				handlePutRequest(httpExchange);
			case "DELETE":
				handleDeleteRequest(httpExchange);
			default:
			}
	}

	private void handleGetRequest(HttpExchange httpExchange) throws IOException {
		CidadeController controller = new CidadeController();

		String request_uri = httpExchange.getRequestURI().toString();
		OutputStream outStream = httpExchange.getResponseBody();

		JSONObject json;
		int id = 0;

		if (request_uri.split("/").length <= 2) {
			JSONArray json_array = new JSONArray();
			
			try {
		
				httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");

			    if (httpExchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
			            httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE");
			            httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
			            httpExchange.sendResponseHeaders(204, -1);
			            return;
			        }

				json_array = controller.Index();
				httpExchange.sendResponseHeaders(200, json_array.toString().getBytes().length);
				outStream.write(json_array.toString().getBytes("UTF-8"));

			} catch (IOException e) {

				json = new JSONObject();
				json.put("status", "not found");
				outStream.write(json.toString().getBytes());
				Logger logger = Logger.getLogger(EnderecoHttpHandler.class.getName(), null);
				logger.info(e.getMessage());
			}

			outStream.flush();
			outStream.close();

		} else {

			try {

				id = Integer.valueOf(request_uri.split("/")[2]);
				json = controller.Show(id);
				httpExchange.sendResponseHeaders(200, json.toString().getBytes().length);

			} catch (IOException e) {

				Logger logger = Logger.getLogger(EnderecoHttpHandler.class.getName());
				logger.info(e.getMessage());
				json = new JSONObject();
				json.put("status", "server error");
				httpExchange.sendResponseHeaders(500, json.toString().length());
			}

			outStream.write(json.toString().getBytes());
			outStream.flush();
			outStream.close();
		}
		
	}

	private void handlePostRequest(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		
	}

	private void handlePutRequest(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		
	}

	private void handleDeleteRequest(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		
	}
}
