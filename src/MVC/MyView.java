package MVC;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Master.Params;
import java.util.LinkedList;

/**
 * Prototype Viewable
 *
 * @author levenick
 */
public class MyView extends javax.swing.JPanel implements Viewable {

     GenericMVC_Controller theController;
    Params params;
    Road theRoad;
    JFrame theFrame;
    ArrayList<Car> theCarList;
    int edge=800;
    int roadWidth=75;
    int leftView=0;
    int zoom=1000;
    int rightView=leftView+zoom;
    boolean hasMC;
    private Statistics stats;
    

    /**
     * Creates new form PendulaPanel
     */
    public MyView(ViewFrame f) {
        hasMC = true;
        initComponents();
        theFrame = f;
        theController = new GenericMVC_Controller(this, params);
        theRoad = theController.getRoad();
        theController.start();
        setCarList(theRoad.getCarList());
        //theRoad.doStuff();
        System.out.println("theRoad = " + theRoad);
        //theController.toggleRunning();
    }
    
    public MyView(ViewFrame f, GenericMVC_Controller c) {
        hasMC = true;
        initComponents();
        theFrame = f;
        theController = c;
        theRoad = theController.getRoad();
        setCarList(theRoad.getCarList());
        theController.start();
        
        //theRoad.doStuff();
        System.out.println("theRoad = " + theRoad);
        //theController.toggleRunning();
    }
    public MyView(ViewFrame f, boolean val)
    {
        initComponents();
        theFrame = f;
        
    }
    public void setController(GenericMVC_Controller c)
    {
        hasMC = true;
        theController = c;
        theRoad = theController.getRoad();
        setCarList(theRoad.getCarList());
    }
    

    /**
     * repaint the Frame which will cause paintComponent to be sent with the
     * Graphics context
     */
    public void display() {
        theFrame.repaint();
    }

    /**
     * Displays theRoad
     *
     * @param g
     */
    
     public void paintComponent(Graphics g){
         
      edge = (int)getVisibleRect().getWidth();
    g.setColor(Color.green);
    g.fillRect(0, 200, edge, 200);
    int y=250;
    addLane( y,g);
    if(stats != null)
    {
        g.drawString("Avg speed: " + Math.round(stats.getLastSpeed())+"", 100, 500);
        g.drawString("Ratio near prefered speed: "+stats.getLastPrefSpeed()+"", 100, 600);
        g.drawString("Ratio much lower than prefered speed: " + stats.getLastLessPref()+"", 500, 500);
        g.drawString("Ratio stoped: " +stats.getLastStoped()+"", 500, 600);
        g.setFont(new Font("Arial",Font.PLAIN, 20));
        g.drawString("Time: " + Math.round(theRoad.time)+"", 300, 150);
        g.setFont(new Font("Arial",Font.PLAIN,40));
        g.drawString("Traffic Simulator", 250, 700);
    }
    //addLane(y+150,g);
   
   paintCars(g);
}

    void setCarList(ArrayList<Car> thelist) {
        theCarList=thelist;
    }

    private void paintCars(Graphics g) {
        g.setColor(Color.red);
        if(hasMC)
        {
            for(int i = 0; i < theCarList.size();i++)
        {
            Car next = theCarList.get(i);
            if (next.getCarLoc()>leftView &&next.getCarLoc()<rightView){
            int x = (int)(next.carLoc);
            
            next.paint((x-leftView)*edge/(rightView-leftView),270 +(int)(next.carLane*10),edge/(rightView-leftView+1.0),g);
            }
        }
        }
        
            
        

        
    }

    private void addLane(int y, Graphics g) {
            Color roadColor = Color.BLACK;
    Color lineColor = Color.YELLOW;
        g.setColor(roadColor);
    g.fillRect(0, y, edge, roadWidth);
   // g.setColor(lineColor);
   // for(int i=0;i<10;i++){
  //      g.fillRect(i*80, y+30, 20, 10);
  //  }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        runstopButton = new javax.swing.JButton();
        theSlider = new javax.swing.JSlider();
        SelectViewButton = new javax.swing.JButton();
        zoomSlider = new javax.swing.JSlider();

        jButton1.setText("jButton1");

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        setLayout(null);

        stepButton.setText("step");
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt);
            }
        });
        add(stepButton);
        stepButton.setBounds(295, 14, 75, 29);

        runstopButton.setText("run/stop");
        runstopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runstopButtonActionPerformed(evt);
            }
        });
        add(runstopButton);
        runstopButton.setBounds(295, 55, 100, 29);

        theSlider.setMaximum(3000);
        theSlider.setMinimum(0);
        theSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                theSliderStateChanged(evt);
            }
        });
        add(theSlider);
        theSlider.setBounds(40, 390, 320, 29);

        SelectViewButton.setText("Select View");
        SelectViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectViewButtonActionPerformed(evt);
            }
        });
        add(SelectViewButton);
        SelectViewButton.setBounds(40, 420, 120, 29);

        zoomSlider.setMaximum(5000);
        zoomSlider.setMinimum(800);
        zoomSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zoomSliderStateChanged(evt);
            }
        });
        add(zoomSlider);
        zoomSlider.setBounds(430, 390, 200, 29);
    }// </editor-fold>                        

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        theController.setStep();
    }                                          

    private void runstopButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        theController.toggleRunning();
        if (theController.getRunning()) {
            runstopButton.setText("Stop");
        } else {
            runstopButton.setText("Go!");
        }

    }                                             

    private void formMouseReleased(java.awt.event.MouseEvent evt) {                                   

    }                                  

    private void formMouseDragged(java.awt.event.MouseEvent evt) {                                  

    }                                 

    private void theSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                       
        leftView=5*theSlider.getValue();
        rightView= leftView+zoom;
        theFrame.repaint();
    }                                      

    private void SelectViewButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    leftView=Integer.parseInt( JOptionPane.showInputDialog("Input Road View"));
        rightView =leftView+zoom;
        theFrame.repaint();
    }                                                

    private void zoomSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                        
        zoom=zoomSlider.getValue(); 
       rightView= leftView+zoom;
        theFrame.repaint();
    }                                       


    // Variables declaration - do not modify                     
    private javax.swing.JButton SelectViewButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton runstopButton;
    private javax.swing.JButton stepButton;
    private javax.swing.JSlider theSlider;
    private javax.swing.JSlider zoomSlider;
    // End of variables declaration                   

    void setStats(Statistics stats) {
        this.stats = stats;
    }

}



