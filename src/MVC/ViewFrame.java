package MVC;

/**
 *
 * @author levenick
 */
public class ViewFrame extends javax.swing.JFrame {

    /** Creates new form PendulaFrame */
    public ViewFrame() {
        add(new MyView(this));
        initComponents();
        setSize(800,800);
        setVisible(true);
        
    }
    MyView v;
    public ViewFrame(GenericMVC_Controller c) {
        v = new MyView(this,c);
        add(v);
        initComponents();
        setSize(800,800);
        setVisible(true);
        
    }
    public ViewFrame(boolean a)
    {
        v = new MyView(this,a);
        add(v);
        initComponents();
        setSize(800,800);
        setVisible(true);
        this.setTitle("Traffic Simulator");
    }
    public MyView getView()
    {
        return v;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
