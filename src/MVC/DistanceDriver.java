package MVC;

import java.awt.Color;

public class DistanceDriver extends Driver {

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
