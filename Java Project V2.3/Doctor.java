
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Doctor extends Human {
    private static int nextId;
    private int id;
    private String department;
    private int yearsOexp;

    public Doctor(){
        super();
        this.id = nextId;
        nextId++;
    }

    public Doctor(String name, String password, String phoneNum, int age, LocalDate bDate, String department, int yearsOexp){
        super(name, password, phoneNum, age, bDate);
        this.department = department;
        this.yearsOexp = yearsOexp;
        this.id = nextId;
        nextId++;
    }

    public void updateDetails(String name, String phoneNum, int age, LocalDate bDate, String department, int yearsOexp){
        super.updateDetails(name, phoneNum, age, bDate);
        this.department = department;
        this.yearsOexp = yearsOexp;
    }

    @Override
    public int getId(){
        return id;
    }

    public String getDepartment(){
        return department;
    }

    public int getYearsOexp(){
        return yearsOexp;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    public void setYearsOexp(int yearsOexp) {
        this.yearsOexp = yearsOexp;
    }

    @Override
    public boolean login(String password){

        // check password and set current user to the logged in object if login is verified
        if(super.checkPass(password)){
            Human.newLogin(this, "Doctor");
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return super.toString()+","+department+","+yearsOexp;
    }

    
    protected static Doctor createFromData(String[] data) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Doctor(
            data[0],
            data[1],
            data[2],
            Integer.parseInt(data[3]),
            LocalDate.parse(data[4], formatter),
            data[5],
            Integer.parseInt(data[6])
        );
    }
    
    @SuppressWarnings("UseSpecificCatch")
    public static ArrayList<Doctor> load(){
        ArrayList<Doctor> dList = new ArrayList<>();
        // get a list of raw data from human class load method
        ArrayList<String> data = Human.load("data/dFile.txt");
        try {
            int c = 0;
            // loop through every element in the list and assign each element to each instance variable of a new created object 
            while(true){
                dList.add(new Doctor(data.get(c), data.get(c+1), data.get(c+2), Integer.parseInt(data.get(c+3)), LocalDate.parse(data.get(c+4)), data.get(5),Integer.parseInt(data.get(6))));
                c+=7;
            }
        } catch (Exception e) {
        }
        return dList;
    }
}
