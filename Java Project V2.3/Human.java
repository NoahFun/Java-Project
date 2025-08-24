
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Human{
    private String name;
    private String password;
    private String phoneNum;
    private int age;
    private LocalDate bDate;
    private static Human currentLogin;
    private static String loginType;

    public Human(){

    }

    public Human(String name, String password){
        this.name = name;
        this.password = password;
    }

    public Human(String name, String password, String phoneNum, int age, LocalDate bDate){
        this.name = name;
        this.password = password;
        this.age = age;
        this.phoneNum = phoneNum;
        this.bDate = bDate;
    }

    public void updateDetails(String name, String phoneNum, int age, LocalDate bDate){
        this.name = name;
        this.age = age;
        this.phoneNum = phoneNum;
        this.bDate = bDate;
    }

    public abstract int getId();

    public String getName(){
        return name;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    protected String getPassword(){
        return password;
    }

    public int getAge(){
        return age;
    }

    public LocalDate getbDate(){
        return bDate;
    }

    public boolean checkPass(String password){
        return password.equals(this.password);
    }

    public static Human getCurrentLogin(){
        return currentLogin;
    }

    public static String getLoginType(){
        return loginType;
    }

    public static void newLogin(Human login, String type){

        // set the login type to type of currently logged in user (such as doctor)
        loginType = type;
        // set the current login to specific object who are logged in currently
        currentLogin = login;
    }

    public boolean updatePass(String password){

        // only admins can update password
        if(loginType.equalsIgnoreCase("Admin")){
            this.password = password;
            return true;
        }
        return false;
    }

    public abstract boolean login(String password);

    @SuppressWarnings({"ConvertToTryWithResources", "UseSpecificCatch"})
    public static ArrayList<String> load(String filePath) {

        // return a list of raw data from a specific file and this method will be overriden by subclass 
        ArrayList<String> list = new ArrayList<>();
        File file = new File(filePath);

        try{

            // add every line of data to a list until there is no data to read
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                if (sc.hasNextLine()) {
                    list.add(sc.nextLine());
                }
            }
            sc.close();
        } catch(Exception e){

        }

        return list;
    }

    public static ArrayList<? extends Human> load(){
        return new ArrayList<>();
    }

    @Override
    public String toString(){
        return name+","+ password+","+phoneNum+","+age+","+bDate;
    }

    @SuppressWarnings({"UseSpecificCatch", "ConvertToTryWithResources"})
    public static void save(ArrayList<? extends Human> dataList){

        // the class of object will determine which file will the data save to
        String fileLoc = "";
        if (dataList.get(0) instanceof Admin){
            fileLoc = "data/aFile.txt";
        } else if (dataList.get(0) instanceof Doctor){
            fileLoc = "data/dFile.txt";
        } else if (dataList.get(0) instanceof Nurse){
            fileLoc = "data/nFile.txt";
        } else if (dataList.get(0) instanceof Patient){
            fileLoc = "data/pFile.txt";
        }

        File f = new File(fileLoc);
        try {

            // loop through all the objects in the list and write every data of each data into file
            FileWriter wr = new FileWriter(f);
            for(Human object : dataList){
                for(String data : object.toString().split(",")){
                    wr.write(data + "\n");
                }
            }
            wr.close();
        } catch (Exception e) {
        }
    }
}