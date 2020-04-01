package lab_nr_4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board extends JFrame {

    private JButton buttonChangeSize = new JButton("Zmień rozmiary");
    private JButton buttonStartStop = new JButton("Start/Stop");
    private JLabel labelInitialStates = new JLabel("Stany początkowe:");
    private String [] initialStates = {"---","Niezmienne", "Glider","Ręczne", "Oscylator", "Losowy"};
    private JComboBox comboBoxInitialStates = new JComboBox(initialStates);
    private JLabel labelWidth = new JLabel("Szerokość");
    private JTextField textFieldWidth = new JTextField("30");
    private JLabel labelHeight = new JLabel("Wysokość");
    private JTextField textFieldHeight = new JTextField("30");
    private JPanel header = new JPanel();
    private JPanel body = new JPanel();

    private List<List<Cell>> cells = new ArrayList<>();

    Board() {
        super("Gra w życie");
        setSize(1000,800);
        setResizable(false);
        setLayout(new BorderLayout());

        textFieldHeight.setPreferredSize(new Dimension(50,30));
        textFieldWidth.setPreferredSize(new Dimension(50,30));
        header.add(buttonChangeSize);
        header.add(buttonStartStop);
        header.add(labelInitialStates);
        header.add(comboBoxInitialStates);
        header.add(labelWidth);
        header.add(textFieldWidth);
        header.add(labelHeight);
        header.add(textFieldHeight);

        int rows = 50;
        int cols = 70;
        body.setLayout(new GridLayout(rows,cols));
        for(int i = 0; i < rows ; i++) {
            List<Cell> rowCells = new ArrayList<>();
            for(int j = 0; j < cols ; j++) {
                Cell newCell = new Cell(j,i,body.getWidth()/cols,body.getHeight()/rows);
                rowCells.add(newCell);
                body.add(newCell);
            }
            cells.add(rowCells);
        }

        comboBoxInitialStates.addActionListener(e->{
            System.out.println(comboBoxInitialStates.getSelectedIndex());
            if(comboBoxInitialStates.getSelectedIndex() == 1) {
                doNotChanged(cells);
            }
            else if(comboBoxInitialStates.getSelectedIndex() == 2) {
                doGlider(cells);
            }
            else if(comboBoxInitialStates.getSelectedIndex() == 4) {
                doOscilator(cells);
            }
            else if(comboBoxInitialStates.getSelectedIndex() == 5) {
                doRandom(cells);
            }
        });

        buttonChangeSize.addActionListener(e->{
            body.removeAll();
            cells.clear();
            body.revalidate();
            body.repaint();
            int rows_new = Integer.parseInt(textFieldHeight.getText());
            int cols_new = Integer.parseInt(textFieldWidth.getText());
            body.setLayout(new GridLayout(rows_new,cols_new));
            for(int i = 0; i < rows_new ; i++) {
                List<Cell> rowCells = new ArrayList<>();
                for(int j = 0; j < cols_new ; j++) {
                    Cell newCell = new Cell(j,i,body.getWidth()/cols_new,body.getHeight()/rows_new);
                    rowCells.add(newCell);
                    body.add(newCell);
                }
                cells.add(rowCells);
            }
        });

        add(body,BorderLayout.CENTER);

        add(header,BorderLayout.PAGE_START);
        setVisible(true);
    }

    private void doNotChanged(List<List<Cell>> cells) {
        int width = cells.get(0).size()/2;
        int height = cells.size()/2;
        cells.stream().forEach(rowCells -> {
            rowCells.stream().filter(cell -> cell.getBackground() == Color.black).forEach(cell -> cell.setBackground(Color.white));
        });
        cells.get(height).get(width).setBackground(Color.black);
        cells.get(height).get(width + 1).setBackground(Color.black);
        cells.get(height+1).get(width - 1).setBackground(Color.black);
        cells.get(height+1).get(width + 2).setBackground(Color.black);
        cells.get(height + 2).get(width).setBackground(Color.black);
        cells.get(height + 2).get(width + 1).setBackground(Color.black);
    }

    private void doGlider(List<List<Cell>> cells) {
        int width = cells.get(0).size()/2;
        int height = cells.size()/2;
        cells.stream().forEach(rowCells -> {
            rowCells.stream().filter(cell -> cell.getBackground() == Color.black).forEach(cell -> cell.setBackground(Color.white));
        });
        cells.get(height).get(width).setBackground(Color.black);
        cells.get(height).get(width + 1).setBackground(Color.black);
        cells.get(height+1).get(width - 1).setBackground(Color.black);
        cells.get(height+1).get(width).setBackground(Color.black);
        cells.get(height + 2).get(width + 1).setBackground(Color.black);
    }

    private void doOscilator(List<List<Cell>> cells) {
        int width = cells.get(0).size()/2;
        int height = cells.size()/2;
        cells.stream().forEach(rowCells -> {
            rowCells.stream().filter(cell -> cell.getBackground() == Color.black).forEach(cell -> cell.setBackground(Color.white));
        });
        cells.get(height).get(width).setBackground(Color.black);
        cells.get(height + 1).get(width).setBackground(Color.black);
        cells.get(height - 1).get(width).setBackground(Color.black);
    }

    private void doRandom(List<List<Cell>> cells) {
        int width = cells.get(0).size();
        int height = cells.size();

        cells.stream().forEach(rowCells -> {
            rowCells.stream().filter(cell -> cell.getBackground() == Color.black).forEach(cell -> cell.setBackground(Color.white));
        });

        int amountOfCells = (int)(Math.random() * cells.size()) + 1;

        for(int i = 0; i< amountOfCells ; i++) {
            int x = (int)(Math.random() * (width - 1));
            int y = (int)(Math.random() * (height - 1));
            cells.get(y).get(x).setBackground(Color.black);
        }
    }
}
