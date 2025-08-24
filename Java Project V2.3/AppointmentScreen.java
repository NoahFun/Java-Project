import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AppointmentScreen extends Screen {
    @SuppressWarnings("UseSpecificCatch")
    
    public static boolean BookApp(Appointment[] a, String time, Doctor doctor, Patient patient, String reason, LocalDate date){
        // check if the time is valid (empty and exists)
        try {
            int t = Integer.parseInt(time);
            if(a[t-1] != null){
                return false;
            }

            a[t-1] = new Appointment(doctor, patient, reason, t-1,date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // Overloaded Method for docBookApp
    public static boolean BookApp(Appointment[] a, int time, Doctor doctor, Patient patient, String reason, LocalDate date){
        if(a[time-1] != null){
            return false;
        }

        a[time-1] = new Appointment(doctor, patient, reason, time-1,date);
        return true;
    }

    // book appointemnt for doctor
    @SuppressWarnings("UseSpecificCatch")
    public static boolean docBookApp(Appointment[] a, String time, Doctor doctor, Patient patient, String reason, LocalDate date){
        // test if doctor gonna book time slot 1 - 4 like that
        if(time.contains("-")){
            String[] tList = time.split("-");
            try {
                // convert tList to int
                int[] t = new int[tList.length];
                int c = 0;
                for (String s : tList) {
                    t[c] = Integer.parseInt(s);
                }

                // because we need to use t[0] twice
                int t0 = t[0];

                // check if any appointment in between
                for(;t[0]<=t[1];t[0]++){
                    if(a[t[0]-1] == null){
                        return false;
                    }
                }

                for(;t0<=t[1];t0++){
                    BookApp(a, t0, doctor, patient, reason, date);
                }
            } catch (Exception e) {
                return false;
            }
        } else{
            return BookApp(a, time, doctor, patient, reason, date);
        }
        return true;
    }

    public static File loadDocFile(Doctor doctor){
        File a = new File("appointment/"+doctor.getId());
        try{
            a.mkdir();
        } catch(Exception e){
            print(e);
        }

        return a;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static Appointment[] loadAppList(LocalDate date, File file, ArrayList<Doctor> dList, ArrayList<Patient> pList) throws FileNotFoundException{
        //get all file with name == date
        File[] fList = file.listFiles((dir, name) -> name.contains(date.toString()));
        Appointment[] appList = new Appointment[22];

        for(File f : fList){
            Scanner sc = new Scanner(f);
            int docId = Integer.parseInt(sc.nextLine());
            Doctor doctor = null;
            for(Doctor d : dList){
                if(d.getId() == docId){
                    doctor = d;
                }
            }
    
            int patId = Integer.parseInt(sc.nextLine());
            Patient patient = null;
            for(Patient p : pList){
                if(p.getId() == patId){
                    patient = p;
                }
            }
    
            String reason = sc.nextLine();
    
            int time = Integer.parseInt(sc.nextLine());
            appList[time] = new Appointment(doctor, patient, reason, time, date); // load new appointment based on slot (time)
            sc.close();
        }
        return appList;
    }
    // display time slots
    public static void printAppoint(Appointment[] a){
        if(Human.getLoginType().equalsIgnoreCase("Doctor") || Human.getLoginType().equalsIgnoreCase("Nurse")){
            // if doc or nurse need to display in details
            int c = 0;
            for(Appointment b : a){
                String time;
                // check if it has like 9.5 am like that
                if(c % 2 == 0){
                    time = String.format("%2d",c/2 + 9) + ".00 - " + String.format("%2d",9 + c/2) + ".30";
                } else {
                    time = String.format("%2d",c/2 + 9) + ".30 - " + String.format("%2d",c/2 + 9 + 1) + ".00";
                }

                if(b != null){
                    println("Time slot "+String.format("%2d",c+1)+" ["+time+"]: patient name: "+b.getPatient().getName()+" Treatment: " + b.getReason());
                } else {
                    println("Time slot "+String.format("%2d",c+1)+" ["+time+"]");
                }
                c++;
            }
        } else {
            // for patient need to hide some details, but need to show them which slot is booked
            int c = 0;
            for(Appointment b : a){
                String time;
                if(c % 2 == 0){
                    time = String.format("%2d", (c/2 + 9)) + ".00 - " + String.format("%2d",(9 + c/2)) + ".30";
                } else {
                    time = String.format("%2d",(c/2 + 9)) + ".30 - " + String.format("%2d",(9 + c/2 + 1)) + ".00";
                }

                
                if(b == null){
                    println("Time slot "+String.format("%2d",c+1)+" ["+time+"]: empty");
                } else if (b.getPatient().getId() == Human.getCurrentLogin().getId()){
                    println("Time slot "+String.format("%2d",c+1)+" ["+time+"]: time slot booked");
                }
                 else {
                    println("Time slot "+String.format("%2d",c+1)+" ["+time + "]: sorry,it is booked");
                }
                c++;
            }
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public static void delete(String input, Appointment[] appList, Scanner sc, LocalDate date){ // Deletes specific appointment
        
        try {
            int appPos = Integer.parseInt(input.substring(1)) -1;

            if(appList[appPos] == null){
                println("the time slot is empty");
                wt(sc);
                return;
            }

            if(Human.getCurrentLogin().getClass().equals(Patient.class)){
                if(Human.getCurrentLogin() != appList[appPos].getPatient()){
                    println("it is not your appointment");
                    wt(sc);
                    return;
                }
            }

            appList[appPos].deleteFile(date);
            appList[appPos] = null;
        } catch (Exception e) {
            println("please input a number from 1-22");
            wt(sc);
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public static String screen(ArrayList<Doctor> dList, ArrayList<Patient> pList, Scanner sc){
        clearScreen();
        Doctor doctor = null;
        // Select doctor
        while(doctor == null){
            int c = 1;
            println("Appointment");
            println("-------------------------------------------------");
            println("select the doctor you want to book the appointment for");
            println("╔══════════════════════════════════════════════════════════════════╗");
            println("║                      Available Doctors                           ║");
            println("╠══════════════════════════════════════════════════════════════════╣");

            for(Doctor a : dList){
                String formattedOption = String.format("║  press %-2d for %-45s      ║", c, a.getName());
                println(formattedOption);
                c++;
            }
            println("╠  press b to go back                                              ╣");
            println("╚══════════════════════════════════════════════════════════════════╝");

            String input = sc.nextLine();
            try {
                int docId = Integer.parseInt(input);
                for(Doctor d : dList){
                    if(d.getId() == docId - 1){
                        doctor = d;
                    }
                }

                if(doctor == null){
                    println("out of range");
                    wt(sc);
                    clearScreen();
                }
            } catch (Exception e) {
                if(input.equalsIgnoreCase("B")){
                    return "welcome";
                }
                println("enter a valid number or b");
                wt(sc);
                clearScreen();
            }
        }
        clearScreen();
        File docFile = loadDocFile(doctor);
        LocalDate date = null;
        // Enter Date
        while(date == null){
            println("Appointment");
            println("-------------------------------------------------");
            print("insert date (YYYY-MM-DD): ");
            String d = sc.nextLine();
            try {
                date = LocalDate.parse(d);
            } catch (Exception e) {
                println("enter a valid date");
                wt(sc);
                clearScreen();
            }
        }

        Appointment[] appList = new Appointment[22];
        try {
            appList = loadAppList(date, docFile, dList, pList); // Load all data into array
        } catch (Exception e) {
        }

        String input = "";
        // Loop for booking or deleting 
        while(!input.equalsIgnoreCase("b")){
            clearScreen();
            println("Appointment");
            println("-------------------------------------------------");
            printAppoint(appList);
            println("enter time slot to book (add d in front to delete) or b to go back");
            input = sc.nextLine();

            if(input.equals("")){
                println("Type something...");
                wt(sc);

            }else if(input.toCharArray()[0] == 'D' || input.toCharArray()[0] == 'd'){
                delete(input, appList, sc, date);

            } else if((Human.getLoginType().equalsIgnoreCase("Doctor") || Human.getLoginType().equalsIgnoreCase("Nurse"))){
                // check if input contains digits
                boolean conti = false;
                for(char a: input.toCharArray()){
                    if(Character.isDigit(a)){
                        conti = true;
                    }
                }

                if(!conti){
                    if(!input.equalsIgnoreCase("b")){
                        println("please enter a valid time");
                        wt(sc);
                        clearScreen();
                        continue;
                    }
                }
                try {
                    // check if valid time
                    if (Integer.parseInt(input) > 22 || Integer.parseInt(input) < 1){
                        println("Invalid input, please enter number between 1 - 22");
                        wt(sc);
                        clearScreen();
                        continue;
                    }
                } catch (Exception e) {
                    if(!input.equalsIgnoreCase("b")){
                        println("Invalid input, please enter a number between 1 - 22");
                        wt(sc);
                        clearScreen();
                    }
                    continue;
                    
                }
                print("Enter patientId: ");
                String pId = sc.nextLine();
                try {
                    Patient patient = pList.get(Integer.parseInt(pId));
                    println("What is the reason for the appointment?");
                    String reason = sc.nextLine();
                    boolean created = docBookApp(appList, input, doctor, patient, reason, date);
                    if(!created){
                        println("appointment time already taken or invalid input");
                        wt(sc);
                        clearScreen();
                    }
                } catch (Exception e) {
                    println("Patient not found");
                    wt(sc);
                    clearScreen();
                }
            } else{
                Patient patient = (Patient)Human.getCurrentLogin();
                boolean created = BookApp(appList, input, doctor, patient, "consultation", date);
                if(!created && !input.equalsIgnoreCase("b")){
                    println("appointment time already taken or invalid input");
                    println("Please enter a number between 1 - 22");
                    wt(sc);
                    clearScreen();
                }
            }
        }
        

        return "Welcome";
    }
}

//saving file name gonna be appointment\doctorID\date int