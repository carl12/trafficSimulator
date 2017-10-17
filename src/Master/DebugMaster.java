package Master;

import MVC.GenericMVC_Controller;
import MVC.MyView2;
import MVC.ViewFrame;
import io.MyReader;

/**
 *
 * @author carlr_000
 */
public class DebugMaster {
    public static void main(String[] args) {
        ViewFrame a = new ViewFrame(false);
        MyView2 theView = a.getView();
        ScenarioList list = new ScenarioList();
        String fileList = "src/Master/paramFiles";
        MyReader mr = new MyReader(fileList);
        
        if(mr.hasMoreData()){
            Scenario scenario = new Scenario(new Params(mr.giveMeTheNextLine()));
            GenericMVC_Controller aController = new GenericMVC_Controller(theView,scenario.getParams(), true);
           aController.start();
        } 

    }
    
}
