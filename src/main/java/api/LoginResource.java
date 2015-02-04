package api;

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
public class LoginResource extends LoggableResource{
    @Get
    public String represent() {
        return "User login";
    }

    @Post
    public Representation acceptItem(Representation entity) throws JSONException, IOException {
        //Get me the JSON
        JSONObject json = new JsonRepresentation(entity).getJsonObject();
        JSONObject authJSON;

        //Log it
        logRequest(json.toString());

        //Get the device specific JSON
        if(json.has("auth") )
            authJSON = json.getJSONObject("auth");
        else
            return getResponseRepresentation(false, "No data for a device was supplied");

        String username = authJSON.getString("username");
        String password = authJSON.getString("password");

        if(username == null)
            return new JsonRepresentation(
                    getErrorRepresentation("Invalid username or password")
            );

        UserDAO dao = new UserDAO();
        User thisUser = dao.findByUsername(username);

        //User doesnt exist
        if(thisUser == null)
            return new JsonRepresentation(
                    getErrorRepresentation("Invalid username or password")
            );

        //Validate the password for this user
        if(password != null){
            if(!thisUser.validatePassword(password)){
                return new JsonRepresentation(
                        getErrorRepresentation("Invalid username or password"));
            }
        }

        //Authenticated
        thisUser.setToken(TokenFactory.generate());
        dao.save(thisUser);

        return new JsonRepresentation(thisUser.authInfo());
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
