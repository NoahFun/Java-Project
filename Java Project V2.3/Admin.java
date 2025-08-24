import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Admin extends Human {
    private static int nextId;  // Keep track next adminID
    private int id;

    public Admin() {
        super();
        this.id = nextId;
        nextId++;
    }
    
    public Admin(String name, String password, String phoneNum, int age, LocalDate bDate){
        super(name, password, phoneNum, age, bDate);
        this.id = nextId;
        nextId++;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public boolean login(String password){

        // check password and set current user to the logged in object if login is verified
        if(super.checkPass(password)){
            Human.newLogin(this, "Admin");
            return true;
        }
        return false;
    }

    

    protected static Admin createFromData(String[] data) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Admin(
            data[0],
            data[1],
            data[2],
            Integer.parseInt(data[3]),  // String to int (age)
            LocalDate.parse(data[4], formatter) // String to LocalDate (bDate)
        );
    }

    @SuppressWarnings("UseSpecificCatch")
    // Loads the admin list
    public static ArrayList<Admin> load(){
        ArrayList<Admin> aList = new ArrayList<>();
        // get a list of raw data from human class load method
        ArrayList<String> data = Human.load("data/aFile.txt");
        try {
            int c = 0;
            // loop through every element in the list and assign each element to each instance variable of a new created object 
            while(true){
                aList.add(new Admin(data.get(c), data.get(c+1), data.get(c+2), Integer.parseInt(data.get(c+3)), LocalDate.parse(data.get(c+4))));
                c+=5;
            }
        } catch (Exception e) {
        }
        return aList;
    }
}
