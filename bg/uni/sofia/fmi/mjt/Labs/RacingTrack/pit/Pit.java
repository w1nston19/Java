package bg.uni.sofia.fmi.mjt.Labs.RacingTrack.pit;

import bg.uni.sofia.fmi.mjt.Labs.RacingTrack.Car;
import bg.uni.sofia.fmi.mjt.Labs.RacingTrack.CarFactory;

import java.util.*;


public class Pit {
    private final static int BOUND = 2;
    private final int nPitTeams;

    private int pitStopsCount;

    private final List<PitTeam> pitTeams;

    Queue<Car> waitingCars;


    public Pit(int nPitTeams) {
        this.nPitTeams = nPitTeams;

        this.pitTeams = new ArrayList<>();

        this.waitingCars = new ArrayDeque<>();

        for (int i = 0; i < nPitTeams; i++) {
            pitTeams.add(i, new PitTeam(i, this));
            pitTeams.get(i).start();
        }
    }

    public void submitCar(Car car) {
        synchronized (this) {
            this.waitingCars.add(car);
            pitStopsCount++;
            notifyAll();
        }
    }

    public synchronized Car getCar() {
        return waitingCars.poll();
    }

    public int getPitStopsCount() {
        return pitStopsCount;
    }

    public List<PitTeam> getPitTeams() {
        return pitTeams;
    }

    public void finishRace() {
        try {
            for (PitTeam pitTeam : pitTeams) {
                pitTeam.join();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void repairCar() {
        Car repairedCar = null;
        while (this.waitingCars.isEmpty() || ((repairedCar = this.getCar()) == null)) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Random rnd = new Random();

        //Repair the car
        try {
            Thread.sleep(rnd.nextInt(BOUND));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Make new Thread for the same car and start it
        Car next = CarFactory.of(repairedCar);

        Thread nextThread = new Thread(next);
        nextThread.start();
    }

}