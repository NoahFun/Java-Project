import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Patient extends Human {
    private static int nextId;
    private int id;
    public Patient() {
        super();
        this.id = nextId;
        nextId++;
    }
    
    public Patient(String name, String password, String phoneNum, int age, LocalDate bDate){
        super(name, password, phoneNum, age, bDate);
        this.id = nextId;
        nextId++;
    }

    @Override
    public boolean login(String password){
        // check password and set current user to the logged in object if login is verified
        if(super.checkPass(password)){
            Human.newLogin(this, "Patient");
            return true;
        }
        return false;
    }

    @Override
    public int getId(){
        return id;
    }


    protected static Patient createFromData(String[] data) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Patient(
            data[0],
            data[1],
            data[2],
            Integer.parseInt(data[3]),
            LocalDate.parse(data[4], formatter)
        );
    }

    @SuppressWarnings("UseSpecificCatch")
    public static ArrayList<Patient> load(){
        ArrayList<Patient> pList = new ArrayList<>();
        // get a list of raw data from human class load method
        ArrayList<String> data = Human.load("data/pFile.txt");
        try {
            int c = 0;
            // loop through every element in the list and assign each element to each instance variable of a new created object 
            while(true){
                pList.add(new Patient(data.get(c), data.get(c+1), data.get(c+2), Integer.parseInt(data.get(c+3)), LocalDate.parse(data.get(c+4))));
                c+=5;
            }
        } catch (Exception e) {
        }
        return pList;
    }
}
