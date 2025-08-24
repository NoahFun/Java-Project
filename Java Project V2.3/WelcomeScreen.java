import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class WelcomeScreen extends Screen{
    public static String screen(Scanner sc, ArrayList<Patient> pList, ArrayList<Doctor> dList, ArrayList<Nurse> nList, ArrayList<Admin> aList){
        String screen = "welcome";

        while(screen.equalsIgnoreCase("welcome")){
            try{
                clearScreen();
                println("Welcome " + Human.getCurrentLogin().getName() + " id number: " + Human.getCurrentLogin().getId());
                println("-------------------------------------------------");
                // Print different menu options displayed based on user type
                if(Human.getLoginType().equalsIgnoreCase("patient")){
                    Screen.printOptions("make an appointment","access medical record","update details","logout","exit");
                    
                    System.out.print("Enter your choice: ");
                    int choice = sc.nextInt();  
                    sc.nextLine();

                    //direct user to different functions based on option chosen
                    switch (choice) {
                        case 1 : 
                        screen = "appointment";
                        break;
                            
                        case 2 : 
                        screen = "medical record";
                        break;
                            
                        case 3 : 
                        updateDetails(sc, pList, dList, nList, aList);
                        break;

                        case 4 : 
                        screen = "Login";
                        break;
                        
                        case 5 : 
                        screen = "quit";
                        break;
                            
                        default : 
                        System.out.println("Invalid choice. Try again.");
                        break;
                    }
                } else if(Human.getLoginType().equalsIgnoreCase("nurse")){
                    Screen.printOptions("make an appointment","update details","search patient id","logout","exit");
                    
                    System.out.print("Enter your choice: ");
                    int choice = sc.nextInt();  
                    sc.nextLine();

                    switch (choice) {
                        case 1 : 
                        screen = "appointment";
                        break;
                            
                        case 2 : 
                        updateDetails(sc, pList, dList, nList, aList);
                        break;

                        case 3 : 
                        searchId(sc, pList);
                        break;

                        case 4 : 
                        screen = "Login";
                        break;
                        
                        case 5 : 
                        screen = "quit";
                        break;
                            
                        default : 
                        System.out.println("Invalid choice. Try again.");
                        break;
                    }
                } else if (Human.getLoginType().equalsIgnoreCase("doctor")){
                    Screen.printOptions("make an appointment","access medical record","make a new medical record","update details","search patient id","logout","exit");
                    
                    System.out.print("Enter your choice: ");
                    int choice = sc.nextInt();  
                    sc.nextLine();

                    switch (choice) {
                        case 1 : 
                        screen = "appointment";
                        break;
                            
                        case 2 : 
                        screen = "medical record";
                        break;

                        case 3 : 
                        screen = "new medical record";
                        break;
                            
                        case 4 : 
                        updateDetails(sc, pList, dList, nList, aList);
                        break;

                        case 5 : 
                        searchId(sc, pList);
                        break;
                            
                        case 6 : 
                        screen = "Login";
                        break;
                            
                        case 7 : 
                        screen = "quit";
                        break;
                            
                        default : 
                        System.out.println("Invalid choice. Try again.");
                        break;
                    }
                } else if (Human.getLoginType().equalsIgnoreCase("admin")){
                    Screen.printOptions("add new doctor", "add new nurse", "add new patient", "add new admin", "delete admin", "logout", "exit");
                    
                    System.out.print("Enter your choice: ");
                    int choice = sc.nextInt();  
                    sc.nextLine();

                    //add new objects into their respective data list based on different classes as chosen by user
                    switch (choice) {
                        case 1 : 
                        newDoctor(sc, dList);
                        break;
                            
                        case 2 : 
                        newNurse(sc, nList);
                        break;

                        case 3 : 
                        newPatient(sc, pList);
                        break;
                            
                        case 4 : 
                        newAdmin(sc, aList);
                        break;

                        case 5 : 
                        delAdmin(sc, aList);
                        break;
                            
                        case 6 : 
                        screen = "Login";
                        break;
                            
                        case 7 : 
                        screen = "quit";
                        break;
                            
                        default : 
                        System.out.println("Invalid choice. Try again.");
                        break;
                    }
                }
            } catch(Exception e){
                println("please enter numbers only"); // Handle numeric input errors
                wt(sc);
                sc.nextLine();
            }
        }
        return screen;
    }

    @SuppressWarnings("UseSpecificCatch")
    public static void updateDetails(Scanner sc, ArrayList<Patient> pList, ArrayList<Doctor> dList, ArrayList<Nurse> nList, ArrayList<Admin> aList){
        println("please enter your name (press b to go back):");
        String name = sc.nextLine();
        if(name.equalsIgnoreCase("b")){
            return;
        }

        boolean run = false;

        println("please enter your age:");
        int age = 0;
        while(!run){
            try {
                age = Integer.parseInt(sc.nextLine());
                run = true;
            } catch (Exception e) {
                // Error handling for invalid age input
                println("please enter a number"); 
                println("please enter your age:");
            }
        }

        println("please enter your phone number (with dash)");
        String phoneNum = sc.nextLine();

        println("please enter your birthdate (YYYY-MM-DD)");
        run = false;
        LocalDate bDate = LocalDate.now();
        while(!run){
            try {
                bDate = LocalDate.parse(sc.nextLine());
                run = true;
            } catch (Exception e) {
                // Error handling for invalid date format
                println("please enter a valid date according to the format"); 
                println("please enter your birthdate (YYYY-MM-DD)");
            }
        }

        // Additional fields for medical staff only
        String department = "";
        int yearsOExp = 0;

        if(Human.getLoginType().equalsIgnoreCase("doctor") || Human.getLoginType().equalsIgnoreCase("nurse")){
            println("please enter your department");
            department = sc.nextLine();

            println("please enter your years of experience");
            run = false;
            while(!run){
                try {
                    yearsOExp = Integer.parseInt(sc.nextLine());
                    run = true;
                } catch (Exception e) {
                    // Validate date format
                    println("please enter a valid number");
                    println("please enter your years of experience");
                }
            }
        }

        // Update the appropriate list based on user type and save to respective file
        if(Human.getLoginType().equalsIgnoreCase("Patient")){
            pList.get(Human.getCurrentLogin().getId()).updateDetails(name, phoneNum, age, bDate);  
            Human.save(pList);  
        } else if (Human.getLoginType().equalsIgnoreCase("Nurse")){
            nList.get(Human.getCurrentLogin().getId()).updateDetails(name, phoneNum, age, bDate, department, yearsOExp);
            Human.save(nList);  
        } else if (Human.getLoginType().equalsIgnoreCase("doctor")){
            dList.get(Human.getCurrentLogin().getId()).updateDetails(name, phoneNum, age, bDate, department, yearsOExp);
            Human.save(dList);  
        } else if (Human.getLoginType().equalsIgnoreCase("admin")){
            aList.get(Human.getCurrentLogin().getId()).updateDetails(name, phoneNum, age, bDate);
            Human.save(aList);  
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public static String[] newHuman(Scanner sc){
        String[] data = new String[5];

        println("please enter their name");
        data[0] = sc.nextLine();

        println("please enter their password");
        data[1] = sc.nextLine();

        println("please enter their age");
        boolean run = false;
        while(!run){
            try {
                data[3] = sc.nextLine();
                @SuppressWarnings("unused") // Attempt to parse input to validate it's a number but the result is unused
                int a = Integer.parseInt(data[3]);
                run = true;
            } catch (Exception e) {
                println("please enter a valid number");
                println("please enter their age");
            }
        }

        println("please enter their phone number");
        data[2] = sc.nextLine();

        println("please enter their birth date (YYYY-MM-DD)");
        run = false;
        while(!run){
            try {
                data[4] = sc.nextLine();
                @SuppressWarnings("unused")
                LocalDate a = LocalDate.parse(data[4]);
                run = true;
            } catch (Exception e) {
                println("please enter a date");
                println("please enter their birth date (YYYY-MM-DD)");
            }
        }
        return data;
    }

    public static void newPatient(Scanner sc, ArrayList<Patient> pList){
        String[] data = newHuman(sc);
        Patient p = new Patient(data[0], data[1], data[2], Integer.parseInt(data[3]), LocalDate.parse(data[4]));
        println("their id is: p"+p.getId());
        pList.add(p);
        Human.save(pList);  
        wt(sc);
    }

    public static void newAdmin(Scanner sc, ArrayList<Admin> aList){
        String[] data = newHuman(sc);
        Admin a = new Admin(data[0], data[1], data[2], Integer.parseInt(data[3]), LocalDate.parse(data[4]));
        println("their id is: a"+a.getId());
        aList.add(a);
        Human.save(aList);  
        wt(sc);
    }

    @SuppressWarnings("UseSpecificCatch")
    public static void newNurse(Scanner sc, ArrayList<Nurse> nList){
        String[] data = newHuman(sc);

        println("please enter your department");
        String department = sc.nextLine();

        println("please enter your years of experience");
        boolean run = false; 
        int yearsOExp = 0;

        while(!run){
            try {
                yearsOExp = Integer.parseInt(sc.nextLine());
                run= true;
            } catch (Exception e) {
                println("please enter a valid number");
                println("please enter your years of experience");
            }
        }

        Nurse n = new Nurse(data[0], data[1], data[2], Integer.parseInt(data[3]), LocalDate.parse(data[4]),department, yearsOExp);
        println("their id is: n"+n.getId());
        nList.add(n);
        Human.save(nList);  
        wt(sc);
    }

    @SuppressWarnings("UseSpecificCatch")
    public static void newDoctor(Scanner sc, ArrayList<Doctor> dList){
        String[] data = newHuman(sc);

        println("please enter their department");
        String department = sc.nextLine();

        println("please enter their years of experience");
        boolean run = false;
        int yearsOExp = 0;

        
        while(!run){
            try {
                yearsOExp = Integer.parseInt(sc.nextLine());
                run= true;
            } catch (Exception e) {
                println("please enter a valid number");
                println("please enter your years of experience");
            }
        }

        Doctor d = new Doctor(data[0], data[1], data[2], Integer.parseInt(data[3]), LocalDate.parse(data[4]),department, yearsOExp);
        println("their id is: d"+d.getId());
        dList.add(d);
        Human.save(dList);  
        wt(sc);
    }

    @SuppressWarnings("UseSpecificCatch")
    public static void delAdmin(Scanner sc, ArrayList<Admin> aList){
        println("please enter admin id");
        int id = -1;
        boolean run = false;
        while (!run){
            try {
                id = Integer.parseInt(sc.nextLine());
                run = true;
            } catch (Exception e) {
                println("please enter a id");
                println("please enter admin id");
            }
        }

        print("please enter their password: ");
        String pass = sc.nextLine();

        if(aList.get(id).checkPass(pass)){
            aList.get(id).updateDetails("<deleted>", "<deleted>", 0, LocalDate.now());
            println("admin deleted");
            Human.save(aList);  
            wt(sc);
        }
    }

    public static void searchId(Scanner sc, ArrayList<Patient> pList){
        print("please enter their name: ");
        String name = sc.nextLine();
        println("Result:\n");
        for (Patient p : pList) {

            //display details of object(user) which the object's name matched the entered name
            if(p.getName().equalsIgnoreCase(name)){
                println("id: p"+p.getId());
                println("name: "+p.getName());
                println("age: "+p.getAge());
                println("bDate: "+p.getbDate());
                println("");
            }
            
        }
        println("");
        wt(sc);
    }
}

