package bg.uni.sofia.fmi.mjt.Labs.RentalService.Vehicle;

public class Bicycle extends VehicleAbstract implements Vehicle {
    private final double pricePerMinute = 0.2;

    @Override
    public double getPricePerMinute() {
        return pricePerMinute;
    }

    @Override
    public String getType() {
        return "BICYCLE";
    }
}
