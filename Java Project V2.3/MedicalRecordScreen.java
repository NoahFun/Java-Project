import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MedicalRecordScreen extends Screen {
    
    // loads MR based on patID
    private static File[] loadAllInPatient(int patientID){
        File pFile = new File("MR/"+patientID);
        try {
            pFile.mkdir();  // create folder is not exists
        } catch (Exception e) {
        }

        return pFile.listFiles();
    }

    // Only take the filename without extension
    private static ArrayList<String> fileToString(File[] a){
        ArrayList<String> str = new ArrayList<>();
        for(File b : a){
            if(b != null){
                String[] c = b.getName().split("\\.");
                str.add(c[0]);
            }
        }
        return str;
    }

    @SuppressWarnings("UseSpecificCatch")
    public static String screen(Scanner sc, ArrayList<Patient> pList, ArrayList<Doctor> dList){
        clearScreen();
        if(Human.getLoginType().equalsIgnoreCase("doctor")){
            // enter patID to view records
            println("Medical Record");
            println("-------------------------------------------------");
            print("enter patient id (without 'p'): ");
            String id = sc.nextLine();
            Patient patient = null;

            // validates patID
            while(patient == null){
                try {
                    patient = pList.get(Integer.parseInt(id));
                } catch (Exception e) {
                    println("invalid id");
                    wt(sc);
                    clearScreen();
                    println("Medical Record");
                    println("-------------------------------------------------");
                    print("enter patient id (without 'p'): ");
                    id = sc.nextLine();
                }
            }
            File[] pFile = loadAllInPatient(patient.getId());
            int c = 1;
            String input = "";
            while(!input.equalsIgnoreCase("b")){
                clearScreen();
                println("Medical Record");
                println("-------------------------------------------------");
                println("╔══════════════════════════════════════════════════════════════════╗");
                println("║                     Available Medical Records                    ║");
                println("╠══════════════════════════════════════════════════════════════════╣");

                // Doctor choose which records to view
                for(String a : fileToString(pFile)){
                    String formattedOption = String.format("║  press %-2d for %-49s  ║", c, a);
                    println(formattedOption);
                    c++;
                }
                c = 1;
                println("╠  press b to go back                                              ╠");
                println("╚══════════════════════════════════════════════════════════════════╝");
                input = sc.nextLine().trim();
                if(input.equalsIgnoreCase("B")){

                } else{
                    try {
                        clearScreen();
                        println("Medical Record");
                        println("-------------------------------------------------");
                        println(MedicalRecord.load(pFile[Integer.parseInt(input)-1],patient.getId(),pList,dList).toString()); // show selected medical record
                        wt(sc);
                    } catch (NumberFormatException e) {
                        println("invalid please enter a vaild number or 'b'");
                        wt(sc);
                        clearScreen();
                    }
                }
            }
        } else if (Human.getLoginType().equalsIgnoreCase("patient")){
            // Patient only view own records
            File[] pFile = loadAllInPatient(Human.getCurrentLogin().getId());
            int c = 1;
            String input = "";
            while(!input.equalsIgnoreCase("b")){
                println("Medical Record");
                println("-------------------------------------------------");
                println("╔══════════════════════════════════════════════════════════════════╗");
                println("║                     Available Medical Records                    ║");
                println("╠══════════════════════════════════════════════════════════════════╣");
                for(String a : fileToString(pFile)){
                    String formattedOption = String.format("║  press %-2d for %-49s  ║", c, a);
                    println(formattedOption);
                    c++;
                }
                c = 1;
                println("╠  press b to go back                                              ╠");
                println("╚══════════════════════════════════════════════════════════════════╝");
               
                input = sc.nextLine().trim();
                if(input.equalsIgnoreCase("B")){

                } else{
                    try {
                        clearScreen();
                        println(MedicalRecord.load(pFile[Integer.parseInt(input)-1],Human.getCurrentLogin().getId(),pList,dList));
                        wt(sc);
                        clearScreen();
                    } catch (Exception e) {
                        println("invalid please enter a vaild number or 'b'");
                        wt(sc);
                        clearScreen();
                    }
                }
            }
        } else {
            println("u no access la no peeking"); // If don't have access
            wt(sc);
            clearScreen();
        }
        return "welcome";
    }
}