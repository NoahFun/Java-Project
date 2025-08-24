import java.time.LocalDate;
import java.util.*;

public class LoginScreen extends Screen {
    
    public static String screen(Scanner sc, ArrayList<Patient> pList, ArrayList<Doctor> dList, ArrayList<Nurse> nList, ArrayList<Admin> aList){
        
        // display welcome message and general functions to access
        while(true){
            clearScreen();
                println("╔═══════════════════════════════╗");
                println("║   Welcome to Freedom Hospital ║");
                println("╠═══════════════════════════════╣");
                println("║   1. Login                    ║");
                println("║   2. Sign Up as New Patient   ║");
                println("║   3. Exit                     ║");
                println("╚═══════════════════════════════╝");
                print  ("Choose an option: ");

            String choice = sc.nextLine();

            // different options chosen by users will navigate them to different functions
            switch (choice) {
                case "1" :
                    try {
                        if(verifyLogIn(sc, pList, dList, nList, aList)){
                            println("\n");
                            return "welcome";  
                        }
                    } catch (Exception e) {
                    }
                break;
                case "2" :
                signUp(pList, sc);
                break;
                case "3" :
                    System.out.println("Goodbye!");
                    return "exit";
                default: 
                System.out.println("Invalid choice.");
                wt(sc);
                break;
            }
        }
    }

    // telling the compiler not to give warning when a generic catch is used 
    @SuppressWarnings("UseSpecificCatch")
    private static boolean verifyLogIn(Scanner sc, ArrayList<Patient> pList, ArrayList<Doctor> dList, ArrayList<Nurse> nList, ArrayList<Admin> aList) throws Exception{

        while(true){

            // prompt to ask for login id and return boolean true if they give correct information
            // if they entered 'b' or give incorrect information, return false and the login is considered not verified
            print("Enter ID (press 'b' for back): ");
            String idStr = sc.nextLine();
            if (idStr.equalsIgnoreCase("b")){
                return false;
            }
            else{
                print("Enter Password: ");
                String password = sc.nextLine();
                try {
                    int id = Integer.parseInt(idStr.substring(1));
                    switch(idStr.charAt(0)){
                        case 'd': case'D' :
                            if(dList.get(id).login(password)){
                                return true;
                            }
                            break;
                        case 'n': case'N' :
                            if(nList.get(id).login(password)){
                                return true;    
                        }
                        break;
                        case 'p': case 'P': 
                            if(pList.get(id).login(password)){
                                return true;
                            }
                        case 'a': case 'A': 
                            if(aList.get(id).getName().equalsIgnoreCase("<deleted>")){
                                
                            }else if(aList.get(id).login(password)){
                                return true;
                            }
                            break;
                        default:
                        break; 

                        
                    }
                } catch (Exception e) {
                }
            }
            // display error message if incorrect information given 
            println("Invalid username or password. ");
            wt(sc);
            return false;
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private static void signUp(ArrayList<Patient> pList, Scanner sc){

        // prompt to let new patients enter their personal information 
        println("please enter your name:");
        String name = sc.nextLine();
        println("please enter your password:");
        String pass = sc.nextLine();
        println("please enter your phone number:");
        String phoneNum = sc.nextLine();

        println("please enter your age: ");
        boolean run = false;
        int age = 0;

        // validate 'age' entered and keep looping if the age given is not integer 
        while(!run){
            try {
                age = Integer.parseInt(sc.nextLine());
                run = true;
            } catch (Exception e) {
                println("please enter a number");
                println("please entry your age:");
            }
        }

        // validate 'birth date' entered and keep looping if the date given is not a proper date
        println("please enter your birth date (YYYY-MM-DD):");
        run = false;
        LocalDate bDate = LocalDate.now();
        while(!run){
            try {
                bDate = LocalDate.parse(sc.nextLine());
                run = true;
            } catch (Exception e) {
                println("Invalid date format. Please enter date follow the format yyyy-mm-dd.");
                println("please enter your birth date (YYYY-MM-DD):");
            }
        }

        // create a patient object and save to file once all information entered
        Patient p = new Patient(name, pass, phoneNum, age, bDate);
        println("Your id is: p"+p.getId());
        println("");
        pList.add(p);
        Human.save(pList);
        wt(sc);
    }
}