package lab_nr_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    private int x;
    private int y;

    Cell(int x,int y,int width, int height) {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setSize(10,10);
        this.x = x;
        this.y =y;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("x:" + x + ";y" + y );
                setBackground(Color.lightGray);
            }
        });
    }
}
