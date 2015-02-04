package entities;

import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;
import lombok.Data;

@Data
public class Vehicle extends MongoBase{
    @Indexed(value= IndexDirection.ASC, name="emailAddressIndex", unique=true)
    private String registration;
    private String colour;
    private String description;
}
