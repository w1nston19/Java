package bg.uni.sofia.fmi.mjt.Labs.RacingTrack;

public class CarFactory {

    public static Car of(int id, int nPitStops, Track track) {
        return new Car(id, nPitStops, track);
    }

    public static Car of(Car other) {
        return new Car(other.getCarId(), other.getNPitStops() - 1, other.getTrack());
    }
}
