package MVC;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The interface your model will implement
 * Add all the the methods that will be sent to the Model
 * 
 * @author levenick
 */
public interface Modelable {
    public void step();
   
    
    public double getT();
    public int getNumLanes();
    public int getRoadLength();
    public ArrayList<Car> getCarList();
    
    //not implemented yet
    public int[] getOnRamps();
    public int[] getOffRamps();
   
    
}
