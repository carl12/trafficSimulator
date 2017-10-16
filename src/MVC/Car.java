
package MVC;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Mylinh
 */
public class Car implements Comparable{


    //step and time step when make a car object
    //variables with accessors
    public double carSpeed;
    public double carLoc;
    public double carLane = 1;
    public int carLength = 15;
    public int carWidth = 7;
    public int tireLength = 2;
    public int tireWidth = 1;
    public final int CAR_MAX_DEC = -20;
    public final int CAR_MAX_ACC = 20;
    public double timeStep;
    private Road road;
    private Driver theDriver;
    public Color carColor = Color.BLACK;
    public static int idCounter = 0;
    public int myId;
    public static final double milesToFeet = 5280;
    public static final double hoursToSeconds = 1.0 / 3600;
    public boolean wishCouldMerge = false;

    //private Driver theDriver;
    public Car(double timeStep, Road road) {

        
        this.carLoc = myId * 50;
        this.carSpeed = 60;
        this.timeStep = timeStep;
        this.road = road;

        theDriver = Driver.getNormalDriver(road, this, timeStep);

    }

    public Car(double timeStep, Road in, double loc) {
        myId = road.vehicles.size();
        this.carLoc = loc;
        this.carSpeed = 20 + Math.random() * 50;
        this.timeStep = timeStep;
        road = in;

        theDriver = Driver.getNormalDriver(road, this, timeStep);
    }

    public Car(double timeStep, Road road, double loc, double lane) {
        myId = road.vehicles.size();
        this.carLoc = loc;
        this.carSpeed = 20 + Math.random() * 50;
        this.timeStep = timeStep;
        this.road = road;
        this.carLane = lane;
        theDriver = Driver.getNormalDriver(road, this, timeStep);
    }

    public Car(double timeStep, Road in, double loc, double lane, double speed) {
        myId = idCounter++;
        this.carLoc = loc;
        this.carSpeed = speed;
        this.timeStep = timeStep;
        road = in;
        this.carLane = lane;
        double rand = Math.random();
        if (rand < 0.25) {
            theDriver = Driver.getNormalDriver(road, this, timeStep);
        } else if (rand < 0.5) {
            theDriver = Driver.getAggressiveDriver(road, this, timeStep);
        } else if (rand < 0.75) {
            theDriver = Driver.getDistanceDriver(road, this, timeStep);
        } else {
            theDriver = Driver.getPassiveDriver(road, this, timeStep);
        }
        carColor = theDriver.myColor;
    }
    
    public void setId(int id){
        myId = id;
    }

    public void setDriver(Driver in) {
        theDriver = in;
    }
    
    public void setDriver(int in)
    {
        if (in == 0) {
            theDriver = Driver.getAggressiveDriver(road, this, timeStep);
        } else if (in == 1) {
            theDriver = Driver.getNormalDriver(road, this, timeStep);
        } else if (in == 2) {
            theDriver = Driver.getDistanceDriver(road, this, timeStep);
        } else {
            theDriver = Driver.getPassiveDriver(road, this, timeStep);
        }
       carColor = theDriver.myColor;
    
    }

    public Driver getDriver() {
        return theDriver;
    }

    public double getCarLoc() {
        return carLoc;
    }

    public double getCarSpeed() {
        return carSpeed;
    }

    public double getCarLane() {
        return carLane;
    }

    public int getCarLength() {
        return carLength;
    }

    public int getCarWidth() {
        return carWidth;
    }

    public int getCarMaxDec() {
        return CAR_MAX_DEC;
    }

    public double getDec(double decIn) {
        return Math.max(CAR_MAX_DEC, decIn);
    }

    public int getCarMaxAcc() {
        return CAR_MAX_ACC;
    }

    public void acc(double amount) {
        if (amount > CAR_MAX_ACC) {
            carSpeed += timeStep * CAR_MAX_ACC;
        } else if (amount < CAR_MAX_DEC) {
            carSpeed += timeStep * CAR_MAX_DEC;
        } else {
            carSpeed += timeStep * amount;
        }
        if (carSpeed < 0) {
            carSpeed = 0;
        }
    }

    public void merge(double mergeAmount) {
        if (road.getNumLanes() > carLane) {
            carLane += timeStep * mergeAmount;
        }
        if (carLane % 1 < 0.0001 || carLane % 1 > 0.999) {
            carLane = Math.round(carLane);
        }

    }

    public void driverLook() {
        theDriver.look();
    }

    public void step() {

        theDriver.step();
        carLoc += carSpeed * timeStep * milesToFeet * hoursToSeconds;
    }

    public double getTireLength() {
        return tireLength;
    }

    public double getTireWidth() {
        return tireWidth;
    }

    public void paint(int x, int y, Graphics g) {
        int half = getCarLength() / 2;
        g.setColor(carColor);
        int left = x - getCarLength();
        int right = x;
        int up = y - getCarWidth() / 2;
        int down = y + getCarWidth() / 2;
        g.fillRect(left, up,
                getCarLength(), getCarWidth());
        g.setColor(Color.GREEN);
        g.fillRect(left, up, tireLength, tireWidth);
        g.fillRect(left, down - tireWidth, tireLength, tireWidth);
        g.fillRect(right - tireLength, up, tireLength, tireWidth);
        g.fillRect(right - tireLength, down - tireWidth, tireLength, tireWidth);
        
    }

    public void paint(double x, double y, double feetToPixels, Graphics g) {
//        double half = feetToPixels * getCarLength() / 2;
        double tireLength = feetToPixels * getTireLength();
        double tireWidth = feetToPixels * getTireWidth();
        if(wishCouldMerge){
            g.setColor(Color.PINK);
        } else {
            g.setColor(carColor);
        }
        double left = x - (feetToPixels * getCarLength());
        double right = x;
        double up = y - feetToPixels * getCarWidth() / 2;
        double down = y + feetToPixels * getCarWidth() / 2;
        g.fillRect((int) left, (int) up,
                (int) (right - left), (int) (down - up));
        g.setColor(Color.GRAY);
        g.fillRect((int) left, (int) up, (int) tireLength, (int) tireWidth);
        g.fillRect((int) left, (int) (down - tireWidth), (int) tireLength, (int) tireWidth);

        g.fillRect((int) (right - tireLength), (int) up, (int) tireLength, (int) tireWidth);
        g.fillRect((int) (right - tireLength), (int) (down - tireWidth), (int) (tireLength), (int) (tireWidth));
        Font a = new Font("Courier", Font.PLAIN, (int) (10 * feetToPixels));
        g.setFont(a);
        g.drawString(myId + "", (int) ((right - left) / 2 + left), (int) (up));

    }

    double getAcc(double prefAcc) {
        return Math.min(CAR_MAX_ACC, prefAcc);

    }

    @Override
    public int compareTo(Object t) {
        try{
            Car c = (Car)t;
            if(c.getCarLoc() > getCarLoc())
                return 1;
            else if(c.getCarLoc() == getCarLoc())
                return 0;
            else 
                return -1;
        }
        catch(Exception e)
        {
            return 0;
        }
    }
}
