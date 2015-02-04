package api;

import entities.Logger;
import org.json.JSONException;
import org.restlet.resource.ServerResource;
import persistence.LoggerDAO;

import java.io.IOException;

public class LoggableResource extends ServerResource{

	public void logRequest(String data) throws JSONException, IOException {
		new LoggerDAO().save(new Logger(data));
	}
}
