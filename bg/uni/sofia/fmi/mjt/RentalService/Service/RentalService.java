package bg.uni.sofia.fmi.mjt.RentalService.Service;

import bg.uni.sofia.fmi.mjt.RentalService.Location.Location;
import bg.uni.sofia.fmi.mjt.RentalService.Vehicle.Vehicle;

import java.time.LocalDateTime;

public class RentalService implements RentalServiceAPI{

    private Vehicle[] vehicles;
    int index = 0;

    public RentalService(int n){
        vehicles = new Vehicle[n];
    }
    public RentalService(Vehicle[] vehicles) {
        this.vehicles = vehicles;
        this.index = vehicles.length;
    }

    @Override
    public double rentUntil(Vehicle vehicle, LocalDateTime until) {
        return isVehicle(vehicle) ? vehicle.book(until) : -1.0;
    }

    @Override
    public Vehicle findNearestAvailableVehicleInRadius(String type, Location location, double maxDistance) {
        return null;
    }

    public void addVehicle(Vehicle vehicle){
        vehicles[index] = vehicle;
    }

    private boolean isVehicle(Vehicle vehicle){
        for(Vehicle v: vehicles){
            if(v == vehicle){
                return true;
            }
        }
        return false;
    }
}
