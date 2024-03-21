package traingles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import javax.swing.UIManager;

/**
 * <p>Title: triagles</p>
 *
 * <p>Description: nice triangles using recursive drawing </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: IT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class Frame1 extends JFrame {
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JPanel north = new JPanel();
    JPanel center = new JPanel();
    JButton stopBtn = new JButton();
    JTextField levelTxt = new JTextField();
    JLabel jLabel1 = new JLabel();

    public Frame1() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(660, 600));
        setTitle("triagular lines");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new Frame1_jMenuFileExit_ActionAdapter(this));
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About");
        jMenuHelpAbout.addActionListener(new
                                         Frame1_jMenuHelpAbout_ActionAdapter(this));
        stopBtn.setText("start");
        stopBtn.addActionListener(new Frame1_stopBtn_actionAdapter(this));
        levelTxt.setPreferredSize(new Dimension(50, 20));
        levelTxt.setText("5");
        jLabel1.setText("level");
        center.setBackground(Color.black);
        stepTxt.setPreferredSize(new Dimension(50, 20));
        stepTxt.setText("50");
        jLabel2.setToolTipText("");
        jLabel2.setText("time step(millis)");
        stop.setText("stop");
        stop.addActionListener(new Frame1_stop_actionAdapter(this));
        jMenuBar1.add(jMenuFile);
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        north.add(jLabel2);
        north.add(stepTxt);
        north.add(jLabel1);
        north.add(levelTxt);
        north.add(stopBtn);
        north.add(stop);
        contentPane.add(center, java.awt.BorderLayout.CENTER);
        contentPane.add(north, java.awt.BorderLayout.NORTH);
        setJMenuBar(jMenuBar1);
    }

    /**
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Help | About action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
        Frame1_AboutBox dlg = new Frame1_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.show();
    }

    DrawingThread d=null;
    JTextField stepTxt = new JTextField();
    JLabel jLabel2 = new JLabel();

    int originalHeight=0;
    int originalWidth=0;
    boolean working=false;
    JButton stop = new JButton();
    AffineTransformOp op = new AffineTransformOp(new AffineTransform(),AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

    public void stopBtn_actionPerformed(ActionEvent e) {
  /*      Dimension dim=center.getSize();
        if(dim.width>dim.height)
            originalHeight=dim.height;
        else
            originalHeight=dim.width;

     */   originalHeight=600;
        originalWidth=originalHeight;
        DrawingThread.enableDrawing=true;
        d=new DrawingThread((Graphics2D)center.getGraphics(),originalHeight,Integer.parseInt(levelTxt.getText()),center);
        d.step=Long.parseLong(stepTxt.getText());

    /*    AffineTransform t = new AffineTransform();
        Dimension dim = center.getSize();
        double xFactor = (double) dim.width / originalWidth;
        double yFactor = (double) dim.height / originalHeight;
        t.scale(xFactor, yFactor);
        AffineTransformOp op = new AffineTransformOp(t,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
*/
        d.start();
    }

    public void paint(Graphics g){
        if (DrawingThread.image != null) {
            ((Graphics2D) center.getGraphics()).drawImage(DrawingThread.image, op,
                    0, 0);

        }
        super.paint(g);
}

    public void stop_actionPerformed(ActionEvent e) {
        DrawingThread.enableDrawing=false;
    }

}


class Frame1_stop_actionAdapter implements ActionListener {
    private Frame1 adaptee;
    Frame1_stop_actionAdapter(Frame1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.stop_actionPerformed(e);
    }
}


class Frame1_stopBtn_actionAdapter implements ActionListener {
    private Frame1 adaptee;
    Frame1_stopBtn_actionAdapter(Frame1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.stopBtn_actionPerformed(e);
    }
}


class Frame1_jMenuFileExit_ActionAdapter implements ActionListener {
    Frame1 adaptee;

    Frame1_jMenuFileExit_ActionAdapter(Frame1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}


class Frame1_jMenuHelpAbout_ActionAdapter implements ActionListener {
    Frame1 adaptee;

    Frame1_jMenuHelpAbout_ActionAdapter(Frame1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
    }
}
