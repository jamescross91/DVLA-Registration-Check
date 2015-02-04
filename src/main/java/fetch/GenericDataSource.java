package fetch;

import entities.Vehicle;
import persistence.VehicleDAO;

public abstract class GenericDataSource {
    public Vehicle fetchFromDb(String registration) {
        VehicleDAO dao = new VehicleDAO();
        Vehicle byRegistration = dao.findByRegistration(registration);

        return byRegistration;
    }

    public void storeInDb(Vehicle vehicle) {
        VehicleDAO dao = new VehicleDAO();
        dao.save(vehicle);
    }
}
