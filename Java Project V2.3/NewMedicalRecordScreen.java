import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class NewMedicalRecordScreen extends Screen {

    public static String screen(Scanner sc, ArrayList<Patient> pList) {
        String screen = "New Medical Record";

        while (screen.equalsIgnoreCase("New Medical Record")) {
            print("Enter patient id (enter b to go back): ");
            String id = sc.nextLine();
            Patient patient = null;

            // get patient via their ID
            while (patient == null) {
                patient = getPatientFromId(id, pList);
                if(id.equalsIgnoreCase("b")){
                    return "welcome";
                }
                if (patient == null) {
                    println("Invalid patient ID, please try again.");
                    print("Enter patient id: ");
                    id = sc.nextLine();
                }
            }

            
            print("Enter medical notes: ");
            String data = sc.nextLine().trim(); // remove leading and trailing whitespace

        
            while (data.isEmpty()) {
                println("Medical notes cannot be empty. Please enter again: ");
                data = sc.nextLine().trim();
            }

            // Only doctors can create MR
            if (Human.getCurrentLogin() instanceof Doctor) {
                Doctor doctor = (Doctor) Human.getCurrentLogin();
                if (doctor != null && !data.isEmpty()) {   
                    MedicalRecord mr = new MedicalRecord(doctor, patient, data);
                    save(mr);   // save to file
                    println("Medical record successfully created and saved.");
                } else {
                    println("Error: Missing information.");
                }
            } else {
                println("Error: Current user is not a doctor!");
            }

            screen = "welcome"; // back to welcome screen
        }

        return screen;
    }

    private static Patient getPatientFromId(String id, ArrayList<Patient> pList) {
        Patient patient = null;
        if(id.equals("")){
            println("write something??");
            return patient;
        }
        try {
            // extract the id if first char is 'p'
            if (id.toCharArray()[0] == 'p') {
                id = id.substring(1);  
            }
            int patientIndex = Integer.parseInt(id);
            // Validation
            if (patientIndex >= 0 && patientIndex < pList.size()) {
                patient = pList.get(patientIndex);
            } else {
                println("Patient ID out of range.");
            }
        } catch (NumberFormatException e) {
            println("Invalid patient ID format.");
        }
        return patient;
    }

    
    private static void save(MedicalRecord mr) {
        int drId = mr.getDoctor().getId();
        int pId = mr.getPatient().getId();
        String data = mr.getData();
        LocalDate now = LocalDate.now();

        // Create folder for the patient if not exist
        File patientDir = new File("MR/" + pId);
        if (!patientDir.exists() && !patientDir.mkdir()) {
            println("Failed to create directory for patient records.");
            return;
        }

        // Avoid multiple files with same name
        File file = createUniqueFile(patientDir, now + ".txt");

        try (FileWriter wr = new FileWriter(file)) {
            wr.write(drId + "\n");
            wr.write(data);
        } catch (IOException e) {
            println("Error saving the medical record: " + e.getMessage());
        }
    }

    // Add number if multiple files with same name
    private static File createUniqueFile(File directory, String fileName) {
        File file = new File(directory, fileName);
        int count = 1;
        while (file.exists()) {
            file = new File(directory, fileName.replace(".txt", " " + count + ".txt"));
            count++;
        }
        return file;
    }
}
