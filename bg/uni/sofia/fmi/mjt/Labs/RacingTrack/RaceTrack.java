package bg.uni.sofia.fmi.mjt.Labs.RacingTrack;

import bg.uni.sofia.fmi.mjt.Labs.RacingTrack.pit.Pit;

import java.util.ArrayList;
import java.util.List;


public class RaceTrack implements Track {
    Pit pit;

    List<Integer> finishedCars;

    public RaceTrack(int pitCount) {
        this.pit = new Pit(pitCount);

        finishedCars = new ArrayList<>();
    }

    @Override
    public void enterPit(Car car) {
        if (car.getNPitStops() == 0) {
            synchronized (finishedCars) {
                this.finishedCars.add(car.getCarId());
            }
        } else {
            pit.submitCar(car);
        }
    }

    @Override
    public int getNumberOfFinishedCars() {
        return finishedCars.size();
    }

    @Override
    public List<Integer> getFinishedCarsIds() {
        return this.finishedCars;
    }

    @Override
    public Pit getPit() {
        return pit;
    }
}
