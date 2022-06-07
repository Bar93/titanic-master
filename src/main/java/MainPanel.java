import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

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
    private JButton statisticButton;
    private JLabel filterStatus;
    private JLabel filterResult;
    private int filterCount;
    public static final String PATH_STATISTIC_FILE = "C:\\Users\\USER\\Documents\\Titanic Report\\Statistic.txt";


    public MainPanel (int x, int y, int width, int height) {
        this.setLayout(null);
        this.setBounds(x, y + Constants.MARGIN_FROM_TOP, width, height);
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
            addComponent();
            this.filterButton.addActionListener((e1) -> {
                String[] filterOption = getUserFilter(passengerList);
               if (checkUserFilter(filterOption)){
                    List<Passenger> filteredList = filter(passengerList,filterOption);
                    System.out.println(filteredList);
                    this.filterResult.setText(resultOfFilter(filteredList));
                    this.filterCount++;
                    createSummaryReport(filteredList);
            }
            });
            this.statisticButton.addActionListener((e2) -> {
                createStatisticReport(passengerList);

            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void addComponent (){
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
        this.statisticButton = new JButton("Statistic");
        this.statisticButton.setBounds(450, 300, 100, 50);
        this.add(this.statisticButton);
        this.filterStatus = new JLabel("Chose filter option");
        this.filterStatus.setBounds(10 + Constants.MARGIN_FROM_LEFT, 200, Constants.LABEL_WIDTH*2, 200);
        this.add(this.filterStatus);
        this.filterResult = new JLabel("Filter result:");
        this.filterResult.setBounds(10 + Constants.MARGIN_FROM_LEFT, 500, Constants.LABEL_WIDTH*2, Constants.LABEL_HEIGHT);
        this.add(this.filterResult);
        }

    public String[] getUserFilter(List<Passenger> passengerList){
        String [] filterOption = new String[12];
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
//                if (filterOption[i].equals("")){
//                    filterOption[i] = "-1";
//                }
                if (filterOption[i].equals(Constants.DEFAULT_FILTER[i])){
                    filterOption[i] = "";
                }
            }
            for (int i=0;i<filterOption.length;i++){
                System.out.println(filterOption[i]);
            }
        return filterOption;
    }

    public boolean checkUserFilter (String[] filterOption) {
        boolean ans = true;
        String filterStatus = "<html>";
        for (int i=0;i<this.rangeIdMin.getText().length();i++){
            if (!Character.isDigit(this.rangeIdMin.getText().charAt(i))){
                ans = false;
                filterStatus = filterStatus + "Enter numbers only<br/>";
                break;
            }
        }
        for (int i=0;i<this.rangeIdMax.getText().length();i++){
            if (!Character.isDigit(this.rangeIdMax.getText().charAt(i))){
                filterStatus = filterStatus + "Enter numbers only<br/>";
                ans = false;
                break;
            }
        }
        for (int i=0;i<this.rangeFareMin.getText().length();i++){
            if (!Character.isDigit(this.rangeFareMin.getText().charAt(i))){
                filterStatus = filterStatus + "Enter numbers only<br/>";
                ans = false;
                break;
            }
        }
        for (int i=0;i<this.rangeFareMax.getText().length();i++){
            if (!Character.isDigit(this.rangeFareMax.getText().charAt(i))){
                filterStatus = filterStatus + "Enter numbers only<br/>";
                ans = false;
                break;
            }
        }
        try {
            int minID,maxID,minFare,maxFare;
            if (!filterOption[3].equals("")){
                minID =Integer.valueOf(filterOption[3]);
            }
            else minID=0;

            if (!filterOption[4].equals("")){
                maxID =Integer.valueOf(filterOption[4]);
            }
            else maxID=891;

            if (!filterOption[9].equals("")){
                minFare =Integer.valueOf(filterOption[9]);
            }
            else minFare=0;

            if (!filterOption[10].equals("")){
                maxFare =Integer.valueOf(filterOption[10]);
            }
            else maxFare=513;

            if (minID>maxID){
                ans = false;
                filterStatus = filterStatus + "Range of ID is incorrect<br/>";
            }
            if (minFare>maxFare){
                ans = false;
                filterStatus= filterStatus +"Range of fare ticket is incorrect<br/>";
            }
            if (!filterOption[8].equals("")){
                if (filterOption[8].length()>1) {
                    ans = false;
                    filterStatus =filterStatus+ "enter only one number in Ticket<br/>";
                }
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        filterStatus = filterStatus + "</html>";
        this.filterStatus.setText(filterStatus);
        return ans;
    }

    public List filter(List<Passenger> passengerList, String[] filterOption){
        List<Passenger> filteredList = new ArrayList<>(passengerList);
        filteredList = filterClass(filteredList,filterOption);
        filteredList = filterSex(filteredList,filterOption);
        filteredList = filterEmbarked(filteredList,filterOption);
        filteredList = filterPassengerID(filteredList,filterOption);
        filteredList = filterFullName(filteredList,filterOption);
        filteredList = filterSibSp(filteredList,filterOption);
        filteredList = filterParch(filteredList,filterOption);
        filteredList = filterTicket(filteredList,filterOption);
        filteredList = filterPassengerFare(filteredList,filterOption);
        filteredList = filterCabin(filteredList,filterOption);
        return filteredList;
    }
    public List filterClass (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[0].equals("")){
            return passengerList.stream().filter(x->x.getpClass()==Integer.valueOf(filterOption[0])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterSex (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[1].equals("")){
            return passengerList.stream().filter(x->x.getSex().equals(filterOption[1])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterEmbarked (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[2].equals("")){
            return passengerList.stream().filter(x-> x.getEmbarked().equals(filterOption[2])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterPassengerID(List<Passenger> passengerList, String[] filterOption){
        try {
            if (!filterOption[3].equals("") && filterOption[4].equals("")) {
                return passengerList.stream().filter(x -> x.getPassengerId() >= Integer.valueOf(filterOption[3])).collect(Collectors.toList());
            }
            if (filterOption[3].equals("") && !filterOption[4].equals("")) {
                return passengerList.stream().filter(x -> x.getPassengerId() <= Integer.valueOf(filterOption[4])).collect(Collectors.toList());
            }
            if (!filterOption[3].equals("") && !filterOption[4].equals("")) {
                return passengerList.stream().filter(x -> x.getPassengerId() >= Integer.valueOf(filterOption[3]) && x.getPassengerId() <= Integer.valueOf(filterOption[4])).collect(Collectors.toList());
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        return passengerList;
    }
    public List filterFullName (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[5].equals("")){
            return passengerList.stream().filter(x->x.getFullName().contains(filterOption[5])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterSibSp (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[6].equals("")){
            return passengerList.stream().filter(x->x.getSibSp()==Integer.valueOf(filterOption[6])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterParch (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[7].equals("")){
            return passengerList.stream().filter(x->x.getParch()==Integer.valueOf(filterOption[7])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterTicket (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[8].equals("")){
            return passengerList.stream().filter(x->x.getTicket().contains(filterOption[8])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }
    public List filterPassengerFare (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[9].equals("")&&filterOption[10].equals("")){
            return passengerList.stream().filter(x->x.getFare()>=Integer.valueOf(filterOption[9])).collect(Collectors.toList());
        }
        if (filterOption[9].equals("")&&!filterOption[10].equals("")){
            return passengerList.stream().filter(x->x.getFare()<=Integer.valueOf(filterOption[10])).collect(Collectors.toList());
        }
        if (!filterOption[9].equals("")&&!filterOption[10].equals("")){
            return passengerList.stream().filter(x->x.getFare()>=Integer.valueOf(filterOption[9])&&x.getFare()<=Integer.valueOf(filterOption[10])).collect(Collectors.toList());
        }
        return passengerList;
    }
    public List filterCabin (List<Passenger> passengerList, String[] filterOption){
        if (!filterOption[11].equals("")){
            return passengerList.stream().filter(x->x.getCabin().contains(filterOption[10])).collect(Collectors.toList());
        }
        else {
            return passengerList;
        }
    }

    public String resultOfFilter (List<Passenger> filteredList){
        int row;
        long survived,notSurvived;
        row = filteredList.size();
        survived = filteredList.stream().filter(x->x.isSurvived()).count();
        notSurvived = filteredList.stream().filter(x->!x.isSurvived()).count();
        String st = "Total row: " +row+" ("+survived+" is survived, "+notSurvived+" is didn't";
        return st;
    }

    public  List<Passenger> sortByName(List<Passenger> filteredList){
        return filteredList.stream().sorted(Comparator.comparing(Passenger::getFullName)).collect(Collectors.toList());
    }

    public void createSummaryReport (List<Passenger> filteredList){
        String path = "C:\\Users\\USER\\Documents\\Titanic Report\\report"+filterCount+".csv";
        List<Passenger> sortedByNameList = sortByName(filteredList);
        StringBuilder fullPassengerList = new StringBuilder();
        StringBuilder titleSB = new StringBuilder();
        titleSB.append("ID").append(",").append("Survived").append(",")
                .append("pClass").append(",").append("Name").append(",")
                .append("Sex").append(",").append("Age").append(",")
                .append("SibSp").append(",").append("Parch").append(",")
                .append("Ticket").append(",").append("Fare").append(",")
                .append("Cabin").append(",").append("Embarked").append(",").append("\n");
        for (int i=0;i<sortedByNameList.size();i++) {
            StringBuilder passengerSB = new StringBuilder();
            passengerSB.append(sortedByNameList.get(i).getPassengerId()).append(",").append(sortedByNameList.get(i).isSurvived()).append(",")
                    .append(sortedByNameList.get(i).getpClass()).append(",").append(sortedByNameList.get(i).getFullName()).append(",")
                    .append(sortedByNameList.get(i).getSex()).append(",").append(sortedByNameList.get(i).getAge()).append(",")
                    .append(sortedByNameList.get(i).getSibSp()).append(",").append(sortedByNameList.get(i).getParch()).append(",")
                    .append(sortedByNameList.get(i).getTicket()).append(",").append(sortedByNameList.get(i).getFare()).append(",")
                    .append(sortedByNameList.get(i).getCabin()).append(",").append(sortedByNameList.get(i).getEmbarked()).append(",").append("\n");
            fullPassengerList =fullPassengerList.append(passengerSB);
        }
        titleSB.append(fullPassengerList);
        writeToTextFile2(titleSB, path);
    }

    public void writeToTextFile(List<Passenger> filteredList,String path){
        try {
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream writer = new ObjectOutputStream(file);
            writer.writeObject(filteredList);
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeToTextFile2(StringBuilder sb,String path){
        try {
            PrintWriter printWriter = new PrintWriter(path);
            printWriter.print(sb);
            printWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String readFromTextFile (String path){
        String report = "";
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream reader = new ObjectInputStream(file);
            report = (String) reader.readObject();
            reader.close();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return report;

    }

    public void createStatisticReport (List<Passenger> passengerList){
        String path = PATH_STATISTIC_FILE;
        int countFamilyLeastOne=0,countNonFamily=0;
        double numOfPassengerClass1 = passengerList.stream().filter(x->x.getpClass()==Integer.valueOf("1")).count();
        double numOfPassengerClass2 = passengerList.stream().filter(x->x.getpClass()==Integer.valueOf("2")).count();
        double numOfPassengerClass3 = passengerList.stream().filter(x->x.getpClass()==Integer.valueOf("3")).count();
        double numOfSurvivedClass1 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getpClass()==Integer.valueOf("1")).count();
        double numOfSurvivedClass2 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getpClass()==Integer.valueOf("2")).count();
        double numOfSurvivedClass3 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getpClass()==Integer.valueOf("3")).count();
        double numOfPassengerMale = passengerList.stream().filter(x->x.getSex().equals("male")).count();
        double numOfPassengerFemale = passengerList.stream().filter(x->x.getSex().equals("female")).count();
        double numOfSurvivedMale = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getSex().equals("male")).count();
        double numOfSurvivedFemale = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getSex().equals("female")).count();
        double numOfPassengerAge10 = passengerList.stream().filter(x->x.getAge()<=10).count();
        double numOfSurvivedAge10 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getAge()<=10).count();
        double numOfPassengerAge20 = passengerList.stream().filter(x->x.getAge()<=20 && x.getAge()>=11).count();
        double numOfSurvivedAge20 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getAge()<=30 && x.getAge()>=21).count();
        double numOfPassengerAge30 = passengerList.stream().filter(x->x.getAge()<=20 && x.getAge()>=11).count();
        double numOfSurvivedAge30 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getAge()<=30 && x.getAge()>=21).count();
        double numOfPassengerAge40 = passengerList.stream().filter(x->x.getAge()<=40 && x.getAge()>=31).count();
        double numOfSurvivedAge40 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getAge()<=40 && x.getAge()>=31).count();
        double numOfPassengerAge50 = passengerList.stream().filter(x->x.getAge()<=50 && x.getAge()>=41).count();
        double numOfSurvivedAge50 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getAge()<=50 && x.getAge()>=41).count();
        double numOfPassengerAge51 = passengerList.stream().filter(x->x.getAge()>=51).count();
        double numOfSurvivedAge51 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getAge()>=51).count();
        double numOfPassengerFare10 = passengerList.stream().filter(x->x.getFare()<10).count();
        double numOfSurvivedFare10 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getFare()<10).count();
        double numOfPassengerFare20 = passengerList.stream().filter(x->x.getFare()>10 &&x.getFare()<30 ).count();
        double numOfSurvivedFare20 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getFare()>10 &&x.getFare()<30 ).count();
        double numOfPassengerFare30 = passengerList.stream().filter(x->x.getFare()>30).count();
        double numOfSurvivedFare30 = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getFare()>30).count();
        double numOfPassengerEmbarkedS = passengerList.stream().filter(x->x.getEmbarked().equals("S")).count();
        double numOfSurvivedEmbarkedS = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getEmbarked().equals("S")).count();
        double numOfPassengerEmbarkedC = passengerList.stream().filter(x->x.getEmbarked().equals("C")).count();
        double numOfSurvivedEmbarkedC = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getEmbarked().equals("C")).count();
        double numOfPassengerEmbarkedQ = passengerList.stream().filter(x->x.getEmbarked().equals("Q")).count();
        double numOfSurvivedEmbarkedQ = passengerList.stream().filter(Passenger::isSurvived).filter(x->x.getEmbarked().equals("Q")).count();
        for (int i=0;i<passengerList.size();i++) {
            if (passengerList.get(i).isSurvived()) {
                int numOfFamily = passengerList.get(i).getSibSp() + passengerList.get(i).getParch();
                if (numOfFamily > 0) {
                    countFamilyLeastOne++;
                } else {
                    countNonFamily++;
                }
            }
        }
        double numOfSurvived = passengerList.stream().filter(Passenger::isSurvived).count();
        StringBuilder fullStatistic = new StringBuilder();
        StringBuilder rowByClass = new StringBuilder();
        rowByClass.append("Class: ").append("Class1 :"+(int)(numOfSurvivedClass1*100/numOfPassengerClass1)+"%")
                .append(", ").append("Class2 "+(int)(numOfSurvivedClass2*100/numOfPassengerClass2)+"%")
                .append(", ").append("Class3 "+(int)(numOfSurvivedClass3*100/numOfPassengerClass3)+"%").append("\n");
        StringBuilder rowByAge = new StringBuilder();
        rowByAge.append("Age: ").append("0-10: "+(int)(numOfSurvivedAge10*100/numOfPassengerAge10)+"%")
                .append(", ").append("11-20: "+(int)(numOfSurvivedAge20*100/numOfPassengerAge20))
                .append(", ").append("21-30: "+(int)(numOfSurvivedAge30*100/numOfPassengerAge30)+"%")
                .append(", ").append("31-40: "+(int)(numOfSurvivedAge40*100/numOfPassengerAge40)+"%")
                .append(", ").append("40-51: "+(int)(numOfSurvivedAge50*100/numOfPassengerAge50)+"%")
                .append(", ").append("51+: "+(int)(numOfSurvivedAge51*100/numOfPassengerAge51)+"%").append("\n");
        StringBuilder rowByFare = new StringBuilder();
        rowByFare.append("Fare: ").append("0-10 : "+(int)(numOfSurvivedFare10*100/numOfPassengerFare10)+"%")
                .append(", ").append("11-30 : "+(int)(numOfSurvivedFare20*100/numOfPassengerFare20)+"%")
                .append(", ").append("30+ : "+(int)(numOfSurvivedFare30*100/numOfPassengerFare30)+"%").append("\n");
        StringBuilder rowByEmbarked = new StringBuilder();
        rowByEmbarked.append("Embarked: ").append("Q : "+(int)(numOfSurvivedEmbarkedQ*100/numOfPassengerEmbarkedQ)+"%")
                .append(", ").append("S : "+(int)(numOfSurvivedEmbarkedS*100/numOfPassengerEmbarkedS)+"%")
                .append(", ").append("C : "+(int)(numOfSurvivedEmbarkedC*100/numOfPassengerEmbarkedC)+"%").append("\n");
        StringBuilder rowBySex = new StringBuilder();
        rowBySex.append("Sex: ").append("Male : "+(int)(numOfSurvivedMale*100/numOfPassengerMale)+"%")
                .append(", ").append("Female : "+(int)(numOfSurvivedFemale*100/numOfPassengerFemale)+"%").append("\n");
        StringBuilder rowByFamily = new StringBuilder();
        rowByFamily.append("Family: ").append("With least one family : "+(int)(countFamilyLeastOne*100/numOfSurvived)+"%")
                .append(", ").append("Without family : "+(int)(countNonFamily*100/numOfSurvived)+"%").append("\n");


        fullStatistic.append(rowByClass).append(rowByAge).append(rowByFare).append(rowBySex).append(rowByEmbarked).append(rowByFamily);
        writeToTextFile2(fullStatistic,path);

    }

}

