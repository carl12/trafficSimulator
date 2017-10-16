package MVC;

import Master.Params;
import java.util.ArrayList;

/**
 * The statistics from a simulation run
 *
 * @author levenick Nov 30, 2015 6:38:53 PM
 */
public class Statistics {

    private double inPrefSpeed;
    private double lessPrefSpeed;
    private double currAverageSpeed;
    private int numStop;
    private double maxLoc = Integer.MAX_VALUE;
    private double minLoc = 0;

    Params param;

    ArrayList<Double> atPrefSpeed = new ArrayList<>();
    ArrayList<Double> lessThanPrefSpeed = new ArrayList<>();
    ArrayList<Double> avgSpeeds = new ArrayList<>();
    ArrayList<Double> stoped = new ArrayList<>();

    private Road theRoad;

    public Statistics(Road theRoad, Params paramIn) {
        param = paramIn;
        this.theRoad = theRoad;

    }

    public void measure() {
        getPrefSpeed();
        getLessPrefSpeed();
        getAvgSpeed();
        getNumStop();
    }

    public double getLastSpeed() {
        if(avgSpeeds.size() == 0)
            return 0;
       return avgSpeeds.get(avgSpeeds.size()-1);
    }

    public double getLastPrefSpeed() {
        if(atPrefSpeed.size() == 0)
            return 0;
         return atPrefSpeed.get(atPrefSpeed.size()-1);
    }

    public double getLastStoped() {
        if(stoped.size() == 0)
            return 0;
        return stoped.get(stoped.size()-1);
    }
    public double getLastLessPref()
    {
        if(lessThanPrefSpeed.size() == 0)
            return 0;
        return lessThanPrefSpeed.get(lessThanPrefSpeed.size()-1);
    }

    public void getPrefSpeed() {
        int count = 0;
        for (Car c : theRoad.vehicles) {
            Driver theDriver = c.getDriver();
            if (c.carLoc >= minLoc && c.carLoc <= maxLoc) {
                if (theDriver.prefSpeed <= (c.carSpeed + (theDriver.prefSpeed * .10))
                        && theDriver.prefSpeed >= (c.carSpeed - (theDriver.prefSpeed * .10))) {
                    count++;
                }
            }
        }
        inPrefSpeed = (double) (count * 1.0) / theRoad.vehicles.size();
        atPrefSpeed.add(inPrefSpeed);
    }

    public void getLessPrefSpeed() {
        int count = 0;
        for (Car c : theRoad.vehicles) {
            Driver theDriver = c.getDriver();

            if (c.carLoc >= minLoc && c.carLoc <= maxLoc && c.carSpeed <=( theDriver.prefSpeed - (theDriver.prefSpeed * .20))) {
                count++;
            }
        }

        lessPrefSpeed = (double) (count * 1.0) / theRoad.vehicles.size();
        lessThanPrefSpeed.add(lessPrefSpeed);
    }

    public void getAvgSpeed() {
        double total = 0;

        for (Car c : theRoad.vehicles) {
            if (c.carLoc >= minLoc && c.carLoc <= maxLoc) {
                total += c.carSpeed;
            }
        }

        currAverageSpeed = total / theRoad.vehicles.size();
        avgSpeeds.add(currAverageSpeed);
    }

    public void getNumStop() {
        numStop = 0;

        for (Car c : theRoad.vehicles) {
            if (c.carLoc >= minLoc && c.carLoc <= maxLoc) {
                if (c.carSpeed == 0) {
                    numStop++;
                }
            }
        }
        double percentStop = (numStop + 0.0) / theRoad.vehicles.size();
        stoped.add(percentStop);
    }

    public String toString() {
        String returnString = param.toString();
        returnString += "\n\nprefSpeed: " + this.getLastPrefSpeed();
        returnString += "\nbelow prefSpeed: " + this.getLastLessPref();
        returnString += "\navg Speed: " + this.getLastSpeed();
        returnString += "\nPercent stopped: " + this.getLastStoped();
        System.out.println("returnString = " + returnString);
        return returnString;
    }
}
