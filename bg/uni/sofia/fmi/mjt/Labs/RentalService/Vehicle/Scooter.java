package bg.uni.sofia.fmi.mjt.Labs.RentalService.Vehicle;

public class Scooter extends VehicleAbstract implements Vehicle {
    @Override
    public double getPricePerMinute() {
        return 0.3;
    }

    @Override
    public String getType() {
        return "Scooter";
    }
}
