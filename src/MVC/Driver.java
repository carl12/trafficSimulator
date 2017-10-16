package MVC;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author carlr_000
 */
public class Driver {
    


    public double prefSpeed = 70;
    private int wantToMerge = 0;
    private boolean merging = false;
    private double prefAcc = 10;
    private double prefDec = -5;
    public Color myColor = Color.BLUE;
    private Road road;
    double timeStep;
    double roadRAGE = 0;
    double mergeAmount = .25;
    double acceleration = 0;
    Car myCar;
    ArrayList<Action> myActions = new ArrayList<Action>();

    public static Driver getAggressiveDriver(Road r, Car car, double timeStep) {
        return new AggressiveDriver(r, car, timeStep);

    }

    public static Driver getPassiveDriver(Road r, Car car, double timeStep) {
        Driver d = new PassiveDriver(r, car, timeStep);
        d.myColor = Color.GRAY;

        return d;
    }

    public static Driver getNormalDriver(Road r, Car car, double loc) {

        return new Driver(r, car, loc);
    }

    public static Driver getDistanceDriver(Road r, Car car, double loc) {

        return new DistanceDriver(r, car, loc);
    }

//    public Action getAction(int timeAgo) {
//        return myActions.get(myActions.size() - (1 + timeAgo));
//    }
    public Driver(Road r, Car car, double timeStep) {
        myColor = Color.BLUE;
        road = r;
        prefSpeed = Math.random() * 10 + 65;
        this.timeStep = timeStep;
        myCar = car;
    }

    public void setPrefSpeed(double pref) {
        prefSpeed = pref;

    }

    public Car getNextCarUpInMyLane() {
        return road.getNextCarUp(myCar.myId);
    }

    public Car getNextCarUp(double lane) {
        return road.getNextCarUp(myCar.myId);
    }

    public boolean wantToMerge() {
        Car nextCar = getNextCarUpInMyLane();
        if (nextCar == null) {
            return false;
        }
        double diff = nextCar.getCarLoc() - myCar.getCarLoc();
        double carLengths = diff / nextCar.getCarLength();
        //check if want to merge
        if (carLengths < 5 && nextCar.getCarSpeed() < prefSpeed - 10) {
            return true;
        }
        return false;

    }

    public void setWantToMerge() {
        if (!merging) {

            if (wantToMerge()) {
                myCar.wishCouldMerge = true;
                //check up lane
                Car nextMyLane = getNextCarUpInMyLane();
                Car nextUpLane = road.getNextCarUp(myCar.myId, myCar.getCarLane() + 1);
                if (nextUpLane == null || 
                        nextUpLane.getCarSpeed() > nextMyLane.getCarSpeed()  || 
                        nextUpLane.getCarLoc() - myCar.getCarLoc() > myCar.getCarLength()*7) {
                    if (checkSafeMerge(myCar.getCarLane() + 1)) {
                        wantToMerge = 1;
                        merging = true;
                        return;
                    }
                }
                //check down lane
                Car nextDownLane = road.getNextCarUp(myCar.myId, myCar.getCarLane() - 1);
                if (nextDownLane == null || nextDownLane.getCarSpeed() > nextMyLane.getCarSpeed()) {
                    if (checkSafeMerge(myCar.getCarLane() - 1)) {
                        wantToMerge = -1;
                        merging = true;
                        return;
                    }
                }

            }
            else{
                myCar.wishCouldMerge = false;
            }
            wantToMerge = 0;

        }

        return;
    }

    public boolean checkSafeMerge(double carLane) {
        if (myCar.getCarLoc() < 200) {
            return false;
        }
        double crashTimeNext = -1;
        double crashTimeBehind = -1;
        double carLengthsBehind = -1;
        double carLengthsNext = -1;
        Car next;
        Car behind;

        if (carLane > 0 && carLane <= road.getNumLanes()) {
            next = road.getNextCarUp(myCar.myId, carLane);
            behind = road.getBeforeCar(myCar.myId, carLane);

            if (next != null) {
                double diffNext = next.getCarLoc() - myCar.getCarLoc();
                carLengthsNext = diffNext / myCar.getCarLength();
                crashTimeNext = diffNext / (myCar.getCarSpeed() - next.getCarSpeed());
                if (carLengthsNext < 2 || (crashTimeNext > 0 && crashTimeNext < 1)) {
                    return false;
                }
            }

            if (behind != null) {
                double diffBehind = myCar.getCarLoc() - behind.getCarLoc();
                carLengthsBehind = diffBehind / myCar.getCarLength();
                crashTimeBehind = diffBehind / (behind.getCarSpeed() - myCar.getCarSpeed());
                if (carLengthsBehind < 2 || (crashTimeBehind > 0 && crashTimeBehind < 1)) {
                    return false;
                }
            }

        } else {
            return false;
        }

        return true;

    }

    public void look() {
        setWantToMerge();
        setAcc();
    }

    public void step() {
        myCar.acc(acceleration);
        merge();
        // myActions.add(new Action(myCar.getCarSpeed(), myCar.getCarLoc(), myCar.getCarLane(), acceleration, wantToMerge, road.getT()));

    }

    public double setAcc() {
        acceleration = slowForNext();

        return acceleration;
    }

    private double slowForNext() {
        Car nextCar = getNextCarUpInMyLane();
        if (nextCar == null) {

            return 0;
        }
        double diff = nextCar.getCarLoc() - myCar.getCarLoc();
        double carLengths = diff / nextCar.getCarLength();
        double crashTime = diff / (myCar.getCarSpeed() - nextCar.getCarSpeed());
        if (crashTime > 0 && crashTime < 5) {
            return myCar.getCarMaxDec();
        } else if (carLengths < 2) {
            return myCar.getCarMaxDec();
        } else if (crashTime > 0 && crashTime < 15) {
            return myCar.getDec(prefDec);
        } else if (carLengths < myCar.getCarSpeed() / 10) {
            return myCar.getDec(prefDec);
        } else if (carLengths < 4) {
            return 0;
        } else {
            if (myCar.getCarSpeed() < prefSpeed) {
                return myCar.getAcc(prefAcc);
            } else if (myCar.getCarSpeed() > prefSpeed) {
                return myCar.getDec(prefDec);
            }
            return 0;
        }

    }

    public void merge() {
        myCar.merge(mergeAmount * wantToMerge);

        if (myCar.getCarLane() % 1 < 0.01 || myCar.getCarLane() % 1 > 0.99) {
            {
                wantToMerge = 0;
                merging = false;

            }
        }

    }

    private static class AggressiveDriver extends Driver {

        public AggressiveDriver(Road r, Car car, double timeStep) {
            super(r, car, timeStep);
            myColor = Color.RED;
            setPrefSpeed(Math.random()*10+75);
        }
    }
    
    private static class DistanceDriver extends Driver {

        public DistanceDriver(Road r, Car car, double timeStep) {
            super(r, car, timeStep);
            myColor = Color.GREEN;
            setPrefSpeed(Math.random()*10+ 65);

        }

        public boolean wantToMerge() {
            Car nextCar = getNextCarUpInMyLane();
            if (nextCar == null) {
                return false;
            }
            double diff = nextCar.getCarLoc() - myCar.getCarLoc();
            double carLengths = diff / nextCar.getCarLength();
            //check if want to merge
            if (carLengths < 15) {

                Car up = getNextCarUp(myCar.getCarLane() + 1);
                Car down = getNextCarUp(myCar.getCarLane() - 1);
                if (up == null || down == null) {
                    return true;
                }
                double diffUp = up.getCarLoc() - myCar.getCarLoc();
                double diffDown = up.getCarLoc() - myCar.getCarLoc();
                double max = Math.max(diffUp, diffDown);
                if (max > diff) {
                    return true;
                }
                return false;
            }
            return false;
        }
    }

    private static class PassiveDriver extends Driver{
    public PassiveDriver(Road r, Car car, double timeStep) {
        super(r, car, timeStep);
        setPrefSpeed(Math.random()*10+ 55);
        myColor = Color.GRAY;

    }
    @Override
    public boolean wantToMerge()
    {
        return false;
    }
    
}

    
}
