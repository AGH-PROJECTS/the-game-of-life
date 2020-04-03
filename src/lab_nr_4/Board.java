package lab_nr_4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

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

    private boolean isActionStart = false;
    private int rows = 70;
    private int cols = 70;

    private Thread threadStartStop;
    private Integer[][] mainTab = new Integer[rows + 2][cols + 2];
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
            if(threadStartStop != null && !threadStartStop.isInterrupted()) {
                isActionStart = false;

                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            body.removeAll();
            cells.clear();
            body.revalidate();
            body.repaint();
            rows = Integer.parseInt(textFieldHeight.getText());
            cols = Integer.parseInt(textFieldWidth.getText());
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

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(comboBoxInitialStates.getSelectedIndex() == 1) {
                        doNotChanged(cells);
                    }
                    else if(comboBoxInitialStates.getSelectedIndex() == 2) {
                        doGlider(cells);
                    }
                    else if(comboBoxInitialStates.getSelectedIndex() == 3) {
                        doManual(cells);
                    }
                    else if(comboBoxInitialStates.getSelectedIndex() == 4) {
                        doOscilator(cells);
                    }
                    else if(comboBoxInitialStates.getSelectedIndex() == 5) {
                        doRandom(cells);
                    }
                }
            });
            thread1.start();
        });

        buttonChangeSize.addActionListener(e->{
            body.removeAll();
            cells.clear();
            body.revalidate();
            body.repaint();
            rows = Integer.parseInt(textFieldHeight.getText());
            cols = Integer.parseInt(textFieldWidth.getText());
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
        });

        buttonStartStop.addActionListener(e-> {

            if(!isActionStart) {
                threadStartStop = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        isActionStart = true;
                        //iteracja

                        //          doOscilator(cells);
                        while (isActionStart) {
                            cells.forEach(rowCells -> rowCells.forEach(cell -> {
                                if (cell.getBackground() == Color.black) {
                                    mainTab[cells.indexOf(rowCells) + 1][rowCells.indexOf(cell) + 1] = 1;
                                } else {
                                    mainTab[cells.indexOf(rowCells) + 1][rowCells.indexOf(cell) + 1] = 0;
                                }
                            }));

                            for (int i = 1; i < cols + 1; i++) {
                                mainTab[0][i] = cells.get(rows - 1).get(i - 1).getIsColoured();
                                mainTab[rows + 1][i] = cells.get(0).get(i - 1).getIsColoured();
                            }

                            for (int i = 1; i < cols + 1; i++) {
                                mainTab[i][0] = cells.get(i - 1).get(cols - 1).getIsColoured();
                                mainTab[i][cols + 1] = cells.get(i - 1).get(0).getIsColoured();
                            }
                            mainTab[0][0] = cells.get(rows - 1).get(cols - 1).getIsColoured();
                            mainTab[rows + 1][cols + 1] = cells.get(0).get(0).getIsColoured();
                            mainTab[0][cols + 1] = cells.get(rows - 1).get(0).getIsColoured();
                            mainTab[rows + 1][0] = cells.get(0).get(cols - 1).getIsColoured();

                            Integer[][] newTab = new Integer[rows + 2][cols + 2];
                            for(int k = 0; k < rows +2 ; k++) {
                                for(int l = 0; l < cols +2; l++) {
                                    newTab[k][l] = mainTab[k][l];
                                }
                            }


                            for(int i = 1; i <= rows; i++) {
                                for(int j = 1; j <= cols; j++) {
                                    Integer[] neighboursTab = new Integer[8];
                                    int isAlive = mainTab[i][j];

                                    neighboursTab[0]= mainTab[i-1][j-1];
                                    neighboursTab[1] = mainTab[i-1][j];
                                    neighboursTab[2] = mainTab[i-1][j+1];
                                    neighboursTab[3] = mainTab[i][j-1];
                                    neighboursTab[4] = mainTab[i][j+1];
                                    neighboursTab[5] = mainTab[i+1][j-1];
                                    neighboursTab[6] = mainTab[i+1][j];
                                    neighboursTab[7] = mainTab[i+1][j+1];

                                    int aliveNeighbours = (int) Arrays.stream(neighboursTab).filter(a->a==1).count();

                                    if(isAlive == 0) {
                                        if(aliveNeighbours == 3) {
                                            newTab[i][j] = 1;
                                            cells.get(i - 1).get(j - 1).setBackground(Color.black);
                                        }
                                    }
                                    else {
                                        if(aliveNeighbours == 3 || aliveNeighbours == 2) {
                                            newTab[i][j] = 1;
                                            cells.get(i - 1).get(j - 1).setBackground(Color.black);
                                        }

                                        if(aliveNeighbours > 3) {
                                            newTab[i][j] = 0;
                                            cells.get(i - 1).get(j - 1).setBackground(Color.white);
                                        }

                                        if(aliveNeighbours < 2) {
                                            newTab[i][j] = 0;
                                            cells.get(i - 1).get(j - 1).setBackground(Color.white);
                                        }
                                    }
                                }
                            }

                            for (int i = 0; i < rows + 2; i++) {
                                for (int j = 0; j < cols + 2; j++) {
                                    System.out.print(newTab[i][j] + "\t");
                                }
                                System.out.println();
                            }
                            try {
                                if(!threadStartStop.isInterrupted()) {
                                    sleep(200);
                                    System.out.println();
                                }
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }

                            for(int k = 0; k < rows +2 ; k++) {
                                for(int l = 0; l < cols +2; l++) {
                                    mainTab[k][l] = newTab[k][l];
                                }
                            }
                        }
                    }
                });

                threadStartStop.start();
            }
            else {
                isActionStart = false;
            }

        });

        add(body,BorderLayout.CENTER);

        add(header,BorderLayout.PAGE_START);
        setVisible(true);
    }

    private void doManual(List<List<Cell>> cells) {
        int width = cells.get(0).size()/2;
        int height = cells.size()/2;
        cells.stream().forEach(rowCells -> {
            rowCells.stream().filter(cell -> cell.getBackground() == Color.black).forEach(cell -> cell.setBackground(Color.white));
        });
        cells.get(height).get(width).setBackground(Color.black);
        cells.get(height).get(width + 1).setBackground(Color.black);
        cells.get(height).get(width + 2).setBackground(Color.black);
        cells.get(height - 1).get(width + 2).setBackground(Color.black);
        cells.get(height - 2).get(width + 2).setBackground(Color.black);
        cells.get(height - 2).get(width + 1).setBackground(Color.black);
        cells.get(height - 2).get(width).setBackground(Color.black);
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
