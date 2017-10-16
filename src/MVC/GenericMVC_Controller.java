package MVC;

import Master.Params;

/**
 * Generic Controller for the Model/View/Controller pattern using interfaces NB:
 * it knows *nothing* about what is is the model or the view
 *
 * @author levenick
 */
public class GenericMVC_Controller extends Thread {

    private boolean running = false;
    public MyView theView;
    public Params params;
    public double time;
    public double maxTime;
    public Statistics stats;
    public boolean keepStats = false;
    public Road theRoad;
    private boolean stepping;

    public GenericMVC_Controller(MyView theView, Params params) {
        this.params = params;
        this.theView = theView;
        theRoad = new Road(.1, params);
        maxTime = params.duration;
        theView.setController(this);
        System.out.println("asdfasdf");
    }

    public GenericMVC_Controller(MyView theView, Params params, boolean keepStats) {
        this.params = params;
        this.theView = theView;
        theRoad = new Road(.1, params);
        maxTime = params.duration;
        if (keepStats) {
            stats = new Statistics(theRoad, params);
        }
        this.keepStats = keepStats;
        theView.setStats(stats);
        theView.setController(this);
        System.out.println(running + " is running");

    }

//    public GenericMVC_Controller(Params params) {
//        
//        this.params = params;
//        theRoad = new Road(); 
//        ViewFrame a = new ViewFrame(this);
//       this.theView = a.getView();
//        
//    }
    public void run() {
        for (;;) {
            if (running || stepping) {
                step();
                if (keepStats) {
                    stats.measure();
                }
                if (time > maxTime) {
                    running = false;
                    System.out.println("time = " + time);
                    return;
                    //close thread?
                }

                stepping = false;  // only take one step per button press

            }
            delay();
        }
    }

    public Statistics getStats() {
        return stats;
    }

    private void delay() {
        try {
            Thread.sleep(10);
        } catch (Exception ex) {
        }
    }

    public void toggleRunning() {
        running = !running;
    }

    private void step() {
        time = theRoad.time;
        theRoad.step();
        theView.display();

    }

    void setStep() {
        stepping = true;        // set stepping
        running = false;        // unset running (so it stops if you press step)
    }

    Road getRoad() {
        return theRoad;
    }

    boolean getRunning() {
        return running;
    }

}
