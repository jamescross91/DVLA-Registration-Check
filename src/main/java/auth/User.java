package auth;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;
import entities.MongoBase;
import lombok.Data;
import lombok.SneakyThrows;
import org.json.JSONObject;

@Data
@Entity
public class User extends MongoBase{
    @Indexed(value= IndexDirection.ASC, name="emailAddressIndex", unique=true)
    String emailAddress;
    String firstName;
    String lastName;
    Password password;
    Token token;

    @SneakyThrows
    public boolean validatePassword(String password){
        if(password == null)
            return false;

        return this.getPassword().validate(password);
    }

    @SneakyThrows
    public JSONObject authInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", this.getEmailAddress());
        jsonObject.put("token", this.getToken().getToken());

        return jsonObject;
    }
}
