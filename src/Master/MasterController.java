package Master;

import MVC.GenericMVC_Controller;
import MVC.MyView;
import MVC.ViewFrame;
import MVC.Statistics;
import io.MyReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The MasterController reads in the parameter files and runs all the
 * simulations. Then it will save the Statistics to a uniquely named file.
 *
 * @author levenick
 */
public class MasterController {

    ScenarioList list;
    String fileList = "src/Master/paramFiles";
    String statFile = "src/Master/statList";
    MyView theView;

    MasterController() {

    }

    /**
     * Read file names from the fileList for each read the params and add a
     * Scenario to the list
     */
    void init() {
        list = new ScenarioList();
        MyReader mr = new MyReader(fileList);
        while (mr.hasMoreData()) {
            String filename = mr.giveMeTheNextLine();
            System.out.println("filename = " + filename);
            list.add(new Scenario(new Params(filename)));
        }

        mr.close();
        ViewFrame a = new ViewFrame(false);
        theView = a.getView();

    }

    @Override
    public String toString() {
        String returnMe = "I be a MasterController";

        returnMe += list.toString();

        return returnMe;
    }

    public static void main(String[] args) {
        MasterController myMaster = new MasterController();
        myMaster.init();
        System.out.println("myMaster = " + myMaster);

        myMaster.runEmAll();
        
        System.out.println("Thank you and good night!");
    }

    private void runEmAll() {
        clearStatFile();
        for (Scenario nextScenario : list) {
            GenericMVC_Controller aController = new GenericMVC_Controller(theView,
                    nextScenario.getParams(), true);
            System.out.println("Running scenario " + nextScenario.getParams().scenarioName);
            aController.start();

            aController.toggleRunning();
            try {
                aController.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MasterController.class.getName()).log(Level.SEVERE, null, ex);
            }

            writeStatFile(aController.getStats());
        }
    }
    public void clearStatFile()
    {
        Writer output;
        try {
            output = new BufferedWriter(new FileWriter(statFile));  //clears file every time
            output.write("");
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(MasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    public void writeStatFile(Statistics stats) {
        System.out.println("Saving modified data to " + statFile);
        Writer output;
        try {
            output = new BufferedWriter(new FileWriter(statFile, true));  //clears file every time
            output.append("\n ----------------------------------------");
            output.append("\n\n" + stats.toString());
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(MasterController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
