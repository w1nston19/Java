package bg.uni.sofia.fmi.mjt.Labs.RacingTrack;

import java.util.Random;

public class Car implements Runnable {
    private final static int BOUND = 10;
    private final int id;
    private final int nPitStops;
    private final Track track;

    public Car(int id, int nPitStops, Track track) {
        this.id = id;
        this.nPitStops = nPitStops;
        this.track = track;
    }

    @Override
    public void run() {
        try {
            Random rnd = new Random();
            Thread.sleep(rnd.nextInt(BOUND));
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        track.enterPit(this);
    }

    public int getCarId() {
        return id;
    }

    public int getNPitStops() {
        return nPitStops;
    }

    public Track getTrack() {
        return track;
    }

}