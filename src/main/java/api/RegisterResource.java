package api;

import auth.Password;
import auth.TokenFactory;
import auth.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import persistence.UserDAO;

import java.io.IOException;

@Slf4j
public class RegisterResource extends LoggableResource{
    @Get
    public String represent() {
        return "User register";
    }

    @Post
    public Representation acceptItem(Representation entity) throws JSONException, IOException {
        //Get me the JSON
        JSONObject json = new JsonRepresentation(entity).getJsonObject();
        JSONObject userJSON;

        //Log it
        logRequest(json.toString());

        if(json.has("auth") )
            userJSON = json.getJSONObject("auth");
        else
            return getResponseRepresentation(false, "No data for a device was supplied");

        String email = userJSON.getString("email");
        String password = userJSON.getString("password");

        UserDAO dao = new UserDAO();

        User user = new User();

        if(!(dao.findByUsername(email) == null)) {
            return getResponseRepresentation(false, "Email already exists");
        }

        user.setEmailAddress(email);
        user.setPassword(new Password(password));
        user.setToken(TokenFactory.generate());
        dao.save(user);

        return new JsonRepresentation(user.authInfo());
    }

    private JsonRepresentation getResponseRepresentation(boolean success, String message){
        JSONObject object = new JSONObject();
        try {
            object.put("operation_success", success);
            object.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        log.info("Response to client was " + " sucess: " + success + " message: " + message);

        return new JsonRepresentation(object);
    }

    private JSONObject getErrorRepresentation(String message){
        JSONObject object = new JSONObject();
        try {
            object.put("login_success", false);
            object.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
