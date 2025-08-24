
import java.util.ArrayList;
import java.util.Scanner;

public class MainFile extends Screen{

    public static void main(String[] args) {

        //load data from all data files into an ArrayList
        ArrayList<Doctor> dList = Doctor.load();
        ArrayList<Patient> pList = Patient.load();
        ArrayList<Nurse> nList = Nurse.load();
        ArrayList<Admin> aList = Admin.load();

        Scanner sc = new Scanner(System.in);

        String screen = "login"; 
        while(!screen.equalsIgnoreCase("quit")){ // Continue application until user choose to quit
            //switches screen as 'screen' variable cahnges to different values
            if(screen.equalsIgnoreCase("login")){
                screen = LoginScreen.screen(sc, pList, dList, nList, aList);
            }
            else if(screen.equalsIgnoreCase("welcome")){
                screen = WelcomeScreen.screen(sc, pList, dList, nList, aList);
            }else if(screen.equalsIgnoreCase("appointment")){
                screen = AppointmentScreen.screen(dList, pList, sc);
            } else if(screen.equalsIgnoreCase("medical record")){
                screen = MedicalRecordScreen.screen(sc, pList, dList);
            } else if(screen.equalsIgnoreCase("new medical record")){
                screen = NewMedicalRecordScreen.screen(sc, pList);
            }
        }
    }
}
