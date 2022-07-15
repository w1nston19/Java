package bg.uni.sofia.fmi.mjt.Labs.RentalService.Vehicle;

public class Car extends VehicleAbstract implements Vehicle {

    @Override
    public double getPricePerMinute() {
        return 0.50;
    }

    @Override
    public String getType() {
        return "CAR";
    }

}
