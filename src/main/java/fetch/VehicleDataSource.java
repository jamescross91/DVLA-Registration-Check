package fetch;

import entities.Vehicle;

public interface VehicleDataSource {
    public Vehicle loadInfo(String registration);
}
