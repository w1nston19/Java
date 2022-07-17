package bg.uni.sofia.fmi.mjt.Labs.RentalService.Vehicle;

public class Scooter extends VehicleAbstract implements Vehicle {
    private final double pricePerMinute = 0.3;

    @Override
    public double getPricePerMinute() {
        return pricePerMinute;
    }

    @Override
    public String getType() {
        return "Scooter";
    }
}
