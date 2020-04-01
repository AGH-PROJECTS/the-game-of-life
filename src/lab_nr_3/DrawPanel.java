package lab_nr_3;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

class DrawPanel extends JPanel {

    private void doDrawing(Graphics g) {
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}