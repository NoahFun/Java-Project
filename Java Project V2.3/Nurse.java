import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Nurse extends Human {
    
    private static int nextId;
    private int id;
    private int yearsOExp;
    private String department;

    public Nurse() {
        super();
        this.id = nextId;
        nextId++;
    }
    
    public Nurse(String name, String password, String phoneNum, int age, LocalDate bDate,String department, int yearsOexp){
        super(name, password, phoneNum, age, bDate);
        this.department = department;
        this.yearsOExp = yearsOexp;
        this.id = nextId;
        nextId++;
    }

    public void updateDetails(String name, String phoneNum, int age, LocalDate bDate, String department, int yearsOExp){
        super.updateDetails(name, phoneNum, age, bDate);
        this.department = department;
        this.yearsOExp = yearsOExp;
    }

    public String getDepartment(){
        return department;
    }

    public int getYearsOexp(){
        return yearsOExp;
    }

    @Override
    public int getId(){
        return id;
    }

    public void setYearsOexp(int yearsOExp){
        this.yearsOExp = yearsOExp;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    @Override
    public boolean login(String password){
        // check password and set current user to the logged in object if login is verified
        if(super.checkPass(password)){
            Human.newLogin(this, "Nurse");
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return super.toString()+","+department+","+yearsOExp;
    }

    
    protected static Nurse createFromData(String[] data) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Nurse(
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
    public static ArrayList<Nurse> load(){
        ArrayList<Nurse> nList = new ArrayList<>();
        // get a list of raw data from human class load method
        ArrayList<String> data = Human.load("data/nFile.txt");
        try {
            int c = 0;
            // loop through every element in the list and assign each element to each instance variable of a new created object 
            while(true){
                nList.add(new Nurse(data.get(c), data.get(c+1), data.get(c+2), Integer.parseInt(data.get(c+3)), LocalDate.parse(data.get(c+4)), data.get(5),Integer.parseInt(data.get(6))));
            c+=7;
            }
        } catch (Exception e) {
        }
        return nList;
    }
}
