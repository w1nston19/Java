package bg.uni.sofia.fmi.mjt.Labs.RacingTrack.pit;


import java.util.concurrent.atomic.AtomicInteger;

public class PitTeam extends Thread {


    int id;
    final Pit pit;

    private final AtomicInteger stoppedCarsCount;

    public PitTeam(int id, Pit pitStop) {
        this.id = id;
        this.pit = pitStop;
        this.stoppedCarsCount = new AtomicInteger(0);
    }

    @Override
    public void run() {
        pit.repairCar();
        stoppedCarsCount.getAndIncrement();
    }

    public int getPitStoppedCars() {
        return this.stoppedCarsCount.get();
    }

}