import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        this.setLayout(null);
        this.setBounds(x, y + Constants.MARGIN_FROM_TOP, width, height);
        JLabel survivedLabel = new JLabel("Filter: ");
        survivedLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, y, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.filterStatus = new JLabel("Chose filter option");
        this.filterStatus.setBounds(x + Constants.MARGIN_FROM_LEFT, 200, Constants.LABEL_WIDTH*2, Constants.LABEL_HEIGHT);
        this.add(this.filterStatus);
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
            getUserFilter(passengerList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getUserFilter(List<Passenger> passengerList){
        String [] filterOption = new String[12];
        this.classComboBox = new JComboBox<>(Constants.PASSENGER_CLASS_OPTIONS);
        this.classComboBox.setBounds(60, 10, 50,50);
        this.add(this.classComboBox);
        this.passengerSexBox = new JComboBox<>(Constants.SEX_OPTIONS);
        this.passengerSexBox.setBounds(120, 10, 50,50);
        this.add(this.passengerSexBox);
        this.passengerEmbarkedBox = new JComboBox<>(Constants.EMBER_PASSENGER);
        this.passengerEmbarkedBox.setBounds(180, 10, 50,50);
        this.add(this.passengerEmbarkedBox);
        this.rangeIdMin = new JTextField("Min Id");
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
            switch ((String) this.classComboBox.getSelectedItem()){
                case "1st" :filterOption[0]="1"; break;
                case "2nd" :filterOption[0]="2"; break;
                case "3rd" :filterOption[0]="3"; break;
                case "All" :filterOption[0]="All"; break;
            }
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
                if (filterOption[i].equals(Constants.DEFAULT_FILTER[i])){
                    filterOption[i] = "*";
                }
            }
            for (int i=0;i<filterOption.length;i++){
                System.out.println(filterOption[i]);
            }
            if (checkUserFilter(filterOption)){
                List<Passenger> filteredList = filter(passengerList,filterOption);
                System.out.println(filteredList);
            }
        });
    }

    public boolean checkUserFilter (String[] filterOption) {
        boolean ans = true;
        String filterStatus = "";
        for (int i=0;i<this.rangeIdMin.getText().length();i++){
            if (!Character.isDigit(this.rangeIdMin.getText().charAt(i))){
                ans = false;
                break;
            }
        }
        for (int i=0;i<this.rangeIdMax.getText().length();i++){
            if (!Character.isDigit(this.rangeIdMax.getText().charAt(i))){
                ans = false;
                break;
            }
        }
        for (int i=0;i<this.rangeFareMin.getText().length();i++){
            if (!Character.isDigit(this.rangeFareMin.getText().charAt(i))){
                ans = false;
                break;
            }
        }
        for (int i=0;i<this.rangeFareMax.getText().length();i++){
            if (!Character.isDigit(this.rangeFareMax.getText().charAt(i))){
                ans = false;
                break;
            }
        }
        try {
            if (Integer.valueOf(filterOption[3])>Integer.valueOf(filterOption[4])){
                ans = false;
                filterStatus = "Range of ID is incorrect\n";
            }
            if (Double.valueOf(filterOption[9])>Double.valueOf(filterOption[10])){
                ans = false;
                filterStatus+="\nRange of fare ticket is incorrect";
            }
            if (filterOption[8].length()>1){
                ans = false;
                filterStatus+="\n enter only one number in Ticket";
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        this.filterStatus.setText(filterStatus);
        return ans;
    }

    public List filter(List<Passenger> passengerList, String[] filterOption){
        List<Passenger> filteredList = new ArrayList<>(passengerList);
        filteredList = filterClass(filteredList,filterOption);
        filteredList = filterSex(filteredList,filterOption);
        filteredList = filterEmbarked(filteredList,filterOption);
        //filteredList = filterPassengerID(filteredList,filterOption);
        filteredList = filterFullName(filteredList,filterOption);
        filteredList = filterSibSp(filteredList,filterOption);
        filteredList = filterParch(filteredList,filterOption);
        filteredList = filterTicket(filteredList,filterOption);
        return filteredList;
    }
    public List filterClass (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[0].equals("*")){
            return passengerList.stream().filter(x->x.getpClass()==Integer.valueOf(filterOption[0])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterSex (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[1].equals("*")){
            return passengerList.stream().filter(x->x.getSex().equals(filterOption[1])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }

    public List filterEmbarked (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[2].equals("*")){
            return passengerList.stream().filter(x-> x.getEmbarked().equals(filterOption[2])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterPassengerID(List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[3].equals("*")&&filterOption[4].equals("*")){
            return passengerList.stream().filter(x->x.getPassengerId()>Integer.valueOf(filterOption[3])).collect(Collectors.toList());
        }
        if (filterOption[3].equals("*")&&!filterOption[4].equals("*")){
            return passengerList.stream().filter(x->x.getPassengerId()<Integer.valueOf(filterOption[4])).collect(Collectors.toList());
        }
        if (!filterOption[3].equals("*")&&!filterOption[4].equals("*")){
            return passengerList.stream().filter(x->x.getPassengerId()>Integer.valueOf(filterOption[3])&&x.getPassengerId()<Integer.valueOf(filterOption[4])).collect(Collectors.toList());
        }
        return passengerList;
    }
    public List filterFullName (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[5].equals("*")){
            return passengerList.stream().filter(x->x.getFullName().contains(filterOption[5])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterSibSp (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[6].equals("*")){
            return passengerList.stream().filter(x->x.getSibSp()==Integer.valueOf(filterOption[6])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterParch (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[7].equals("*")){
            return passengerList.stream().filter(x->x.getParch()==Integer.valueOf(filterOption[7])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterTicket (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[8].equals("*")){
            return passengerList.stream().filter(x->x.getTicket().contains(filterOption[8])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterPassengerFare (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[9].equals("*")&&filterOption[10].equals("*")){
            return passengerList.stream().filter(x->x.getFare()>Integer.valueOf(filterOption[9])).collect(Collectors.toList());
        }
        if (filterOption[9].equals("*")&&!filterOption[10].equals("*")){
            return passengerList.stream().filter(x->x.getFare()<Integer.valueOf(filterOption[10])).collect(Collectors.toList());
        }
        if (!filterOption[9].equals("*")&&!filterOption[10].equals("*")){
            return passengerList.stream().filter(x->x.getFare()>Integer.valueOf(filterOption[9])&&x.getFare()<Integer.valueOf(filterOption[10])).collect(Collectors.toList());
        }
        return passengerList;
    }
    public List filterCabin (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[11].equals("*")){
            return passengerList.stream().filter(x->x.getCabin().contains(filterOption[10])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
}

