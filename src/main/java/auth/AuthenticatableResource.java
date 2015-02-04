package auth;


import api.LoggableResource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import persistence.UserDAO;

@Slf4j
public class AuthenticatableResource extends LoggableResource {

	@SneakyThrows
	public boolean authenticateRequest(JSONObject json){

		try {
			JSONObject authData;

			if(json.has("auth"))
				authData = json.getJSONObject("auth");
			else
				return false;

			if(authData.has("email") && authData.has("token")) {
				String emailAddress = authData.getString("email");
				String token = authData.getString("token");

				log.info("Authenticating " + emailAddress);

				UserDAO dao = new UserDAO();
				User user = dao.findByUsername(emailAddress);

				if(user == null)
					return false;

				return user.getToken().validateToken(token);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}



		return false;
	}

	public JsonRepresentation authenticationError(){
		JSONObject object = new JSONObject();
		try {
			object.put("auth_result", false);
			object.put("message", "Invalid auth token or email address");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		log.warn("Device authentication failed");
		return new JsonRepresentation(object);
	}
}
