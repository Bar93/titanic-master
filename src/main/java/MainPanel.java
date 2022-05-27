import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class MainPanel extends JPanel {
    private JComboBox<String> classComboBox;
    private JComboBox<String> passengerSexBox;
    private JComboBox<String> passengerEmbarkedBox;
    private JTextField rangeIdMin;
    private JTextField rangeIdMax;
    private JTextField passengerName;
    private JTextField passengerSibSp;
    private JTextField passengerParch;
    private JTextField passengerTicket;
    private JTextField rangeFareMax;
    private JTextField rangeFareMin;
    private JTextField passengerCabin;
    private JButton filterButton;
    private JLabel filterStatus;

    public MainPanel (int x, int y, int width, int height) {
        File file = new File(Constants.PATH_TO_DATA_FILE);
        try {
            Scanner scanner = new Scanner(file);
            List <Passenger> passengerList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String passengerData = scanner.nextLine();
                if (Character.isDigit(passengerData.charAt(0))){
                    Passenger newPassenger = new Passenger(passengerData);
                    passengerList.add(newPassenger);
            }
            }
            System.out.println(passengerList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.setLayout(null);
        this.setBounds(x, y + Constants.MARGIN_FROM_TOP, width, height);
        JLabel survivedLabel = new JLabel("Filter: ");
        survivedLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, y, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.filterStatus = new JLabel("Chose filter option");
        this.filterStatus.setBounds(x + Constants.MARGIN_FROM_LEFT, 200, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(this.filterStatus);
        this.getUserFilter();
    }

    public void getUserFilter (){
        String [] filterOption = new String[12];
        this.classComboBox = new JComboBox<>(Constants.PASSENGER_CLASS_OPTIONS);
        this.classComboBox.setBounds(60, 10, 50,50);
        this.add(this.classComboBox);
        this.passengerSexBox = new JComboBox<>(Constants.SEX_OPTIONS);
        this.passengerSexBox.setBounds(120, 10, 50,50);
        this.add(this.passengerSexBox);
        this.passengerEmbarkedBox = new JComboBox<>(Constants.SEX_OPTIONS);
        this.passengerEmbarkedBox.setBounds(180, 10, 50,50);
        this.add(this.passengerEmbarkedBox);
        this.rangeIdMin = new JTextField("min Id");
        this.rangeIdMin.setBounds(60,100,50,50);
        this.add(this.rangeIdMin);
        this.rangeIdMax = new JTextField("Max Id");
        this.rangeIdMax.setBounds(110,100,50,50);
        this.add(this.rangeIdMax);
        this.passengerName = new JTextField("Name");
        this.passengerName.setBounds(170,100,50,50);
        this.add(this.passengerName);
        this.passengerSibSp = new JTextField("SibSp");
        this.passengerSibSp.setBounds(230,100,50,50);
        this.add(this.passengerSibSp);
        this.passengerParch = new JTextField("Parch");
        this.passengerParch.setBounds(290,100,50,50);
        this.add(this.passengerParch);
        this.passengerTicket = new JTextField("Ticket");
        this.passengerTicket.setBounds(350,100,50,50);
        this.add(this.passengerTicket);
        this.rangeFareMin = new JTextField("Min fare");
        this.rangeFareMin.setBounds(410,100,50,50);
        this.add(this.rangeFareMin);
        this.rangeFareMax = new JTextField("Max fare");
        this.rangeFareMax.setBounds(460,100,50,50);
        this.add(this.rangeFareMax);
        this.passengerCabin = new JTextField("Cabin");
        this.passengerCabin.setBounds(520,100,50,50);
        this.add(this.passengerCabin);


        this.filterButton = new JButton("Filter");
        this.filterButton.setBounds(300, 300, 100, 50);
        this.add(this.filterButton);
        this.filterButton.addActionListener((e) -> {
            filterOption[0] = (String) this.classComboBox.getSelectedItem();
            filterOption[1] = (String) this.passengerSexBox.getSelectedItem();
            filterOption[2] = (String) this.passengerEmbarkedBox.getSelectedItem();
            filterOption[3] = this.rangeIdMin.getText();
            filterOption[4] = this.rangeIdMax.getText();
            filterOption[5] = this.passengerName.getText();
            filterOption[6] = this.passengerSibSp.getText();
            filterOption[7] = this.passengerParch.getText();
            filterOption[8] = this.passengerTicket.getText();
            filterOption[9] = this.rangeFareMin.getText();
            filterOption[10] = this.rangeFareMax.getText();
            filterOption[11] = this.passengerCabin.getText();
            for (int i=0;i<filterOption.length;i++){
                if (filterOption[i].equals("")){
                    filterOption[i] = "*";
                }
            }
            checkUserFilter(filterOption);


        });
    }

    public boolean checkUserFilter (String[] filterOption) {
        boolean ans = true;
        String filterStatus = "";
        if (filterOption[])
        return ans;
    }
}

