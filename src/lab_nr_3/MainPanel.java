package lab_nr_3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JFrame {
    JButton buttonStartStop = new JButton("Start/Stop");
    JLabel labelKind = new JLabel("Rodzaj");
    String [] kindsType = {"30","60","90","120","225"};
    JSpinner spinnerKinds = new JSpinner(new SpinnerNumberModel(1,1,255,1));
    JLabel labelAmountsIterations = new JLabel("Ilość iteracji");
    JTextField textFieldIterations = new JTextField("125");
    JLabel labelNetSize = new JLabel("Wielkość siatki");
    JTextField textFieldNetSize = new JTextField("250");
    JLabel labelPixelSize = new JLabel("Wielkość pixela");
    JTextField textFieldPixelSize = new JTextField("1");
    JPanel header = new JPanel();
    JPanel body = new JPanel();

    MainPanel() {
        super("Automaty komórkowe");
        setSize(1000,800);
        setResizable(false);
        setLayout(new BorderLayout());
        textFieldNetSize.setPreferredSize(new Dimension(50,30));
        textFieldIterations.setPreferredSize(new Dimension(50, 30));
        textFieldPixelSize.setPreferredSize(new Dimension(50,30));

        header.add(buttonStartStop);
        header.add(labelKind);
        header.add(spinnerKinds);
        header.add(labelAmountsIterations);
        header.add(textFieldIterations);
        header.add(labelNetSize);
        header.add(textFieldNetSize);
        header.add(labelPixelSize);
        header.add(textFieldPixelSize);

        buttonStartStop.addActionListener(e -> {
            body.getGraphics().clearRect(0,0,body.getWidth(),body.getHeight());
            body.getGraphics().setColor(Color.white);
            generate(body.getGraphics(), spinnerKinds.getValue(),textFieldIterations.getText(),textFieldNetSize.getText(),textFieldPixelSize.getText());
        });


        add(header,BorderLayout.PAGE_START);
        body.setBackground(Color.WHITE);
        add(body,BorderLayout.CENTER);
        setVisible(true);
    }

    private void generate(Graphics g, Object selectedItem, String iterationsAmounts, String netSize, String pixelSize) {
        g.setColor(Color.blue);
        int[] rule = getBinaryRule(selectedItem);
        List<List<Integer>> mainMatrix = new ArrayList<>();
        fillMatrix(mainMatrix, Integer.parseInt(netSize));
        doIterations(mainMatrix, Integer.parseInt(iterationsAmounts), rule);
        drawPixels(mainMatrix, Integer.parseInt(iterationsAmounts), Integer.parseInt(netSize), g, Integer.parseInt(pixelSize));
    }

    private void drawPixels(List<List<Integer>> mainMatrix, int iterationsAmounts,int netSize, Graphics g, int pixelSize) {
        //rysowanie
        for(int y = 0; y < iterationsAmounts ; y++) {
            for (int x = 0; x < netSize; x++) {
                Integer isTrue = mainMatrix.get(y).get(x);
                if (isTrue == 1) {
                    g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                }
            }
        }
    }

    private void doIterations(List<List<Integer>> mainMatrix, int iterationsAmount, int[] rule) {
        for (int i = 1; i < iterationsAmount; i++) {
            List<Integer> oldList = mainMatrix.get(i - 1);
            List<Integer> newList = new ArrayList<>();

            for (int j = 0; j < oldList.size(); j++) {
                Integer[] tempTab = new Integer[3];
                if (j == 0) {
                    tempTab[1] = oldList.get(j);
                    tempTab[0] = oldList.get(oldList.size() - 1);
                    tempTab[2] = oldList.get(j + 1);
                } else if (j ==  oldList.size() - 1) {
                    tempTab[0] = oldList.get(j - 1);
                    tempTab[1] = oldList.get(j);
                    tempTab[2] = oldList.get(1);
                } else {
                    tempTab[0] = oldList.get(j - 1);
                    tempTab[1] = oldList.get(j);
                    tempTab[2] = oldList.get(j + 1);
                }

                String binaryString = tempTab[0].toString() + tempTab[1].toString() + tempTab[2].toString();
                int decimal = Integer.parseInt(binaryString, 2);
                newList.add(rule[decimal]);
            }
            mainMatrix.add(newList);
        }
    }

    private void fillMatrix(List<List<Integer>> mainMatrix, int matrixSize) {
        List<Integer> row = new ArrayList<>();
        for(int i = 0 ; i < matrixSize ; i++) {
            if(i == matrixSize/2 ) {
                row.add(1);
            } else {
                row.add(0);
            }
        }
        mainMatrix.add(row);
    }

    private int[] getBinaryRule(Object selectedItem) {
        int decimalRule = Integer.parseInt(selectedItem.toString());
        int[] binaryRule = new int[8];

        for(int i = 0; i < 8 ; i++) {
            binaryRule[i] = decimalRule%2;
            decimalRule/=2;
        }

        return binaryRule;
    }
}
