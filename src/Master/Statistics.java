package Master;

import MVC.Car;
import MVC.Driver;
import MVC.Road;
import io.MyWriter;
import java.util.ArrayList;

/**
 * The statistics from a simulation run
 *
 * @author levenick Nov 30, 2015 6:38:53 PM
 */
class Statistics {

    private double inPrefSpeed;
    private double lessPrefSpeed;
    private double avgSpeed;
    private int numStop;
    private double maxLoc;
    private double minLoc;


    private Road theRoad;
    private Driver theDriver;
    double timeStep = theRoad.timeStep;

    public Statistics(String s) {
        timeStep = 0;
        getPrefSpeed();
        getLessPrefSpeed();
        getAvgSpeed();
        getNumStop();
    }

    public double getPrefSpeed() {
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            timeStep++;
            for (Car c : theRoad.vehicles) {
                if (c.carLoc >= minLoc && c.carLoc <= maxLoc) {
                    if (theDriver.prefSpeed <= (c.carSpeed + (theDriver.prefSpeed * .10)) && theDriver.prefSpeed >= (c.carSpeed + (theDriver.prefSpeed * .10))) {
                        count++;
                    }
                }
            }
        }
        inPrefSpeed = (double) (count * 1.0) / theRoad.vehicles.size();
        return inPrefSpeed;
    }

    public double getLessPrefSpeed() {
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            timeStep++;
            for (Car c : theRoad.vehicles) {
                if (c.carLoc >= minLoc && c.carLoc <= maxLoc && theDriver.prefSpeed <= (c.carSpeed - (theDriver.prefSpeed * .20))) {
                    count++;
                }
            }
        }
        lessPrefSpeed = (double) (count * 1.0) / theRoad.vehicles.size();
        return lessPrefSpeed;
    }

    public double getAvgSpeed() {
        double total = 0;
        for (int i = 0; i < 10000; i++) {
            timeStep++;
            for (Car c : theRoad.vehicles) {
                if (c.carLoc >= minLoc && c.carLoc <= maxLoc) {
                    total += c.carSpeed;
                }
            }
        }
        avgSpeed = total / theRoad.vehicles.size();
        return avgSpeed;
    }

    public int getNumStop() {
        numStop = 0;
        for (int i = 0; i < 10000; i++) {
            timeStep++;
            for (Car c : theRoad.vehicles) {
                if (c.carLoc >= minLoc && c.carLoc <= maxLoc) {
                    if (c.carSpeed == 0) {
                        numStop++;
                    }
                }
            }
        }
        return numStop;
    }
    public static void main(String[] args) {
        Statistics myStatistics = new Statistics("src/MVC/stats");
        System.out.println("myStatistics = " + myStatistics);
    }
    
    public String toString() {
        return "PrefSpeed " + inPrefSpeed + "\tLessPrefSpeed: " + lessPrefSpeed + "\tAverageSpeed: " + avgSpeed + "\tNumStoppedCars: " + numStop;
    }
}
