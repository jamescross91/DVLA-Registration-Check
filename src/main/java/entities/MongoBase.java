package entities;

import com.google.code.morphia.annotations.Id;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class MongoBase {
    @Id
    private ObjectId id;
}
