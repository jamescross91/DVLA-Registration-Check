package entities;

import org.json.JSONException;
import persistence.MongoBase;

import java.io.IOException;

public class Logger extends MongoBase {
	private String log;

	public Logger(String data) throws JSONException, IOException {
		this.log = data;
	}
}
