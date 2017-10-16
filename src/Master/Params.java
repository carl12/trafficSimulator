package Master;

//import MVC.Position;
import MVC.Road;
import io.MyReader;
import java.util.StringTokenizer;

/**
 * The parameters for a one run of the simulation
 *
 * @author levenick Nov 30, 2015 5:40:30 PM
 */
public class Params {

    boolean debug = false;
    public int numLanes;
    public int duration;
    //private Position roadLength;
     public int vehiclesPerMinute;
     public  int viewStart;
     public  int viewWidth;
    public  String scenarioName;
    
    public  double aggD;
    public  double avgD;
    public  double distD;
    public  double passiveD;
    

    public Params(String s) {
        MyReader mr = new MyReader(s);
        readParams(mr);
    }

    private void readParams(MyReader mr) {
        scenarioName = readString(mr);
        duration = read(mr);
        numLanes = read(mr);
        
        vehiclesPerMinute = read(mr);
        aggD = read(mr);
        avgD = read(mr);
        distD = read(mr);
        passiveD = read(mr);
        
        viewStart = read(mr);
        viewWidth = read(mr);
    }

    /**
     * Read one int from the front of the next line of the MyReader
     * @param mr
     * @return 
     */
    private int read(MyReader mr) {
        String s = mr.giveMeTheNextLine();
        StringTokenizer st = new StringTokenizer(s);
        return Integer.parseInt(st.nextToken());
    }
    private String readString(MyReader mr) {
        String s = mr.giveMeTheNextLine();
        StringTokenizer st = new StringTokenizer(s);
        return s;
    }

    public static void main(String[] args) {
        Params myParams = new Params("src/MVC/params");
        System.out.println("myParams = " + myParams);
    }

    public String toString() {
        String returnMe = ""+ scenarioName;
        returnMe += "\tParams!";

        returnMe += "\n\t\tduration=" + duration;
        returnMe += "\n\t\tnumLanes=" + numLanes;
        returnMe += "\n\t\tvehiclesPerMinute=" + vehiclesPerMinute;
        returnMe += "\n\t\taggD=" + aggD;
        returnMe += "\n\t\tavgD=" + avgD;
        returnMe += "\n\t\tdistD=" + distD;
        returnMe += "\n\t\tpassiveD=" + passiveD;
      
        returnMe += "\n\t\tviewStart=" + viewStart;
        returnMe += "\n\t\tviewWidth=" + viewWidth;

        return returnMe;
    }
}
