package bg.uni.sofia.fmi.mjt.Labs.RentalService.Vehicle;

public class Bicycle extends VehicleAbstract implements Vehicle {
    @Override
    public double getPricePerMinute() {
        return 0.2;
    }

    @Override
    public String getType() {
        return "BICYCLE";
    }
}
