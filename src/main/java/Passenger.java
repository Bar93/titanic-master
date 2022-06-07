public class Passenger {

    private int passengerId;
    private boolean survived;
    private int pClass;
    private String fullName;
    private String sex;
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
        String [] fullLastName = lastName.split(" ");
        for (int i=2;i<fullLastName.length;i++){
            fullName =fullName+" " +fullLastName[i];
        }
        return fullName;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public int getpClass() {
        return pClass;
    }

    public void setpClass(int pClass) {
        this.pClass = pClass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public int getSibSp() {
        return sibSp;
    }

    public void setSibSp(int sibSp) {
        this.sibSp = sibSp;
    }

    public int getParch() {
        return parch;
    }

    public void setParch(int parch) {
        this.parch = parch;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getEmbarked() {
        return embarked;
    }

    public void setEmbarked(String embarked) {
        this.embarked = embarked;
    }


}
