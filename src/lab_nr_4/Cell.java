package lab_nr_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    private int x;
    private int y;
    private int isColoured = 0;

    Cell(int x,int y,int width, int height) {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setSize(10,10);
        this.x = x;
        this.y = y;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("x:" + x + ";y" + y );
                setBackground(Color.black);
                isColoured = 1;
            }
        });
    }

    public int getIsColoured() {
        return isColoured;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if(bg == Color.black) {
            isColoured = 1;
        }
    }
}
