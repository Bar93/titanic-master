public class Passenger {

    private int passengerId;
    private boolean survived;
    private int pClass;
    private String fullName;
    private String sex; //true male, false female
    private double age;
    private int sibSp;
    private int parch;
    private String ticket;
    private double fare;
    private String cabin;
    private String embarked;

    public Passenger(String passengerData) {
        String [] data = passengerData.split(",");
        this.passengerId =Integer.valueOf(data[0]) ;
        if (data[1].equals("1")){
            this.survived = true;
        }
        else {
            this.survived = false;
        }
        this.pClass = Integer.valueOf(data[2]);
        this.fullName = getFormattedName(data[3],data[4]);
        this.sex = data[5];
        if (data[6].equals("")){
            this.age = -1;
        }
        else {
            this.age = Double.valueOf(data[6]);
        }
        this.sibSp = Integer.valueOf(data[7]);
        this.parch = Integer.valueOf(data[8]);
        this.ticket = data[9];
        this.fare = Double.valueOf(data[10]);
        this.cabin = data[11];
        try {
            if ((12<data.length) && (data[12] != null)) {
                this.embarked = data[12];
            } else this.embarked = "none";
        }
        catch (IndexOutOfBoundsException e){
            System.out.println(passengerId);
        }

    }

    public String getFormattedName(String firstName,String lastName){
        String fullName = firstName+" ";
        String [] fullLastName = lastName.split(". ");
        for (int i=1;i<fullLastName.length;i++){
            fullName +=fullLastName[i];
        }
        return fullName;

    }
}
