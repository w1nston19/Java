package bg.uni.sofia.fmi.mjt.RentalService.Vehicle;

import bg.uni.sofia.fmi.mjt.RentalService.Location.Location;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

public abstract class VehicleAbstract implements Vehicle{
    String id;
    Location location;
    LocalDateTime endTime = null;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public LocalDateTime getEndOfReservationPeriod() {
        return endTime == null ? LocalDateTime.now() : endTime;
    }

    @Override
    public void setEndOfReservationPeriod(LocalDateTime until) {
        endTime = until;
    }

    private boolean isBooked() {
        return (endTime == null || endTime.isAfter(LocalDateTime.now()));
    }

    public double book(LocalDateTime until){
        if(isBooked()){
            return -1.0;
        }
        endTime = until;
        Duration period = Duration.between(LocalDateTime.now(), until);
        return period.toMinutes()*this.getPricePerMinute();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleAbstract that = (VehicleAbstract) o;
        return id.equals(that.id) && location.equals(that.location) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, endTime);
    }
}
