package api;

import auth.AuthenticatableResource;
import com.google.gson.Gson;
import entities.Vehicle;
import fetch.VehicleCheckcouk;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.io.IOException;

@Slf4j
public class RegCheckResource extends AuthenticatableResource {
    @Get
    public String represent() {
        return "Reg check";
    }

    @Post
    public Representation acceptItem(Representation entity) throws IOException, JSONException {
        //Get me the JSON
        JSONObject json = new JsonRepresentation(entity).getJsonObject();
        JSONObject authJSON;

        if(!authenticateRequest(json))
            return authenticationError();

        //Log it
        logRequest(json.toString());

        //Get the device specific JSON
        if(json.has("auth") )
            authJSON = json.getJSONObject("auth");
        else
            return getResponseRepresentation(false, "No data for a device was supplied");

        if(!json.has("data")) {
            return getResponseRepresentation(false, "No data field supplied");
        }

        String registration = json.getJSONObject("data").getString("registration");
        Vehicle vehicle = performLookup(registration);
        Gson gson = new Gson();

        //remove the id field
        JSONObject object = new JSONObject(gson.toJson(vehicle, Vehicle.class));
        object.remove("id");

        return new JsonRepresentation(object.toString());
    }

    private Vehicle performLookup(String registration) {
        VehicleCheckcouk vehicleCheck = new VehicleCheckcouk();
        Vehicle vehicle = vehicleCheck.loadInfo(registration);

        return vehicle;
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
}
