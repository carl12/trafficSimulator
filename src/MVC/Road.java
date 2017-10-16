package MVC;

import Master.Params;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Mylinh
 */
public class Road implements Modelable {

    public ArrayList<Car> vehicles = new ArrayList<>();
    public double timeStep = .1;
    public double time = 0;
    public int numLane = 3;
    private int roadLength;
    int vehiclesPerMin = 60;
    public boolean crashed = false;
    double aggD = 0.25;
    double avgD = 0.25;
    double distD = 0.25;
    double passiveD = 0.25;
    public static final double secPerMin = 60;

    public Road(double timeStep) {
        this.timeStep = timeStep;
    }

    public Road(double timeStep, Params param) {
        this.timeStep = timeStep;

        boolean debug = false;
        numLane = param.numLanes;

        //private Position roadLength;
        vehiclesPerMin = param.vehiclesPerMinute;
        aggD = param.aggD;
        avgD = param.avgD;
        distD = param.distD;
        passiveD = param.passiveD;

    }

    public ArrayList<Car> getCarList() {
        return vehicles;
    }

    public Car getNextCarUp(int id) {
        return getNextCarUp(id, vehicles.get(id).getCarLane());
    }

    public Car getNextCarUp(int id, double lane) {
        if (id >= vehicles.size() || vehicles.isEmpty()) {
            return null;
        } else if (id == -1) {
            for (int i = vehicles.size()-1; i >= 0; i--) {
                Car c = vehicles.get(i);
                if (Math.abs(c.getCarLane() - lane) < 0.9) {
                    return c;
                }
            }
            return null;
        }
        double nextLoc = Integer.MAX_VALUE;
        Car car = vehicles.get(id);
        double loc = car.getCarLoc();
        Car next = null;
        for (int i = id - 1; i >= 0; i--) {
            Car c = vehicles.get(i);
            if (Math.abs(c.getCarLane() - lane) < 0.9) {
                return c;
            }
        }
        return null;
    }

    public void batchStep() {
        if (!crashed) {
            for (int i = 0; i < vehicles.size(); i++) {
                vehicles.get(i).driverLook();
            }
            for (int i = 0; i < vehicles.size(); i++) {
                vehicles.get(i).step();
            }
            Car check = checkCollision();
            if (check != null) {
                //JOptionPane.showMessageDialog(null, "collision at " + check.myId + " at " + check.getCarLoc());
                crashed = true;
            }
            time += timeStep;
            time = Globals.removeExtraDecimals(time);
        }
    }

    public boolean isSpawnTime() {
        return ((time % (secPerMin / vehiclesPerMin) < timeStep / 2)
                || time % (secPerMin / vehiclesPerMin)
                > (secPerMin / vehiclesPerMin - timeStep / 2));
    }

    @Override
    public void step() {
        vehicles.sort(null);
        int j = 0;
        for (Car c : vehicles) {
            c.setId(j++);
        }

        if (isSpawnTime()) {
            double max = 0;
            int lane = -1;
            Car c = null;
            for (int i = 1; i <= numLane; i++) {
                c = getNextCarUp(-1, i);
                if (c == null) {
                    lane = i;
                    break;
                }
                if (c.getCarLoc() > max) {
                    max = c.getCarLoc();
                    lane = i;
                }

            }
            if (c == null) {
                addCar(0, lane);
            } else {
                addCar(0, lane, c);
            }

        }

        for (int i = 0; i < vehicles.size(); i++) {
            vehicles.get(i).driverLook();
        }
        for (int i = 0; i < vehicles.size(); i++) {
            vehicles.get(i).step();
        }
        Car check = checkCollision();
        if (check != null) {
            //JOptionPane.showMessageDialog(null, "collision at " + check.myId + " at " + Math.round(check.getCarLoc()) + " in lane " + check.getCarLane());
            //crashed = true;
        }
        time += timeStep;
        time = Globals.removeExtraDecimals(time);

    }

    public void addCar(double loc, double lane) {
        Car newCar = new Car(timeStep, this, loc, lane);
        newCar.setDriver(giveDriver());
        vehicles.add(newCar);
    }

    public void addCar(double loc, double lane, Car c) {
        Car newCar = new Car(timeStep, this, loc, lane);
        newCar.setDriver(giveDriver());
        newCar.carSpeed = c.getCarSpeed();
        vehicles.add(newCar);

    }

    public int giveDriver() {
        double total = aggD + avgD + distD + passiveD;
        double val = Math.random() * total;

        if (val < aggD) {
            return 0;
        } else if (val < aggD + avgD) {
            return 1;
        } else if (val < aggD + avgD + distD) {
            return 2;
        } else {
            return 3;
        }
    }

    public Car checkCollision() {

        for (int j = 0; j < vehicles.size() - 1; j++) {
            Car a = vehicles.get(j);
            Car b = vehicles.get(j + 1);
            double dist = b.getCarLoc() - a.getCarLoc();

            if (dist < b.getCarLength()) {

                return a;
            }

        }
        return null;

    }

    @Override
    public double getT() {
        return time;
    }

    @Override
    public int getNumLanes() {
        return numLane;

    }

    @Override
    public int getRoadLength() {
        return roadLength;
    }

    @Override
    public int[] getOnRamps() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] getOffRamps() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Car getBeforeCar(int id, double lane) {
        if (id >= vehicles.size()) {
            return null;
        }
        Car myCar = vehicles.get(id);
        double loc = myCar.getCarLoc();
        for (int i = id + 1; i < vehicles.size(); i++) {
            Car otherCar = vehicles.get(i);
            if (Math.abs(otherCar.getCarLane() - lane) < 0.9) {
                if (otherCar.getCarLoc() < loc) {
                    return otherCar;
                }
            }
        }
        return null;

    }

}
