package persistence;

import com.google.code.morphia.dao.BasicDAO;
import entities.Vehicle;
import org.bson.types.ObjectId;

public class VehicleDAO extends BasicDAO<Vehicle, ObjectId> {
    public VehicleDAO() {
        super(Vehicle.class, MongoConnectionManager.instance().getDb());
    }

    public Vehicle findByRegistration(String registration){
        return ds.createQuery(Vehicle.class).field("registration").equal(registration).get();
    }
}
