
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MedicalRecord {
    private Doctor doctor;
    private Patient patient;
    private String data;

    public MedicalRecord(Doctor doctor, Patient patient, String data) {
        this.doctor = doctor;
        this.patient = patient;
        this.data = data;
    }

    // Constructor that use IDs to find matching objects
    public MedicalRecord(int doctorID, int patientID, String data, ArrayList<Doctor> dList, ArrayList<Patient> pList){
        this.data = data;
        for(Doctor d : dList){
            if(d.getId() == doctorID){
                doctor = d;
                break;
            }
        }

        for(Patient p : pList){
            if(p.getId() == patientID){
                patient = p;
                break;
            }
        }
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public Patient getPatient(){
        return patient;
    }

    public String getData(){
        return data;
    }

    // Only shows data for doctor or patient
    @Override
    public String toString(){
        if(Human.getLoginType().equalsIgnoreCase("doctor") || Human.getCurrentLogin() == patient){
            return "doctor name: " + doctor.getName() +"\n" + "patient name: " + patient.getName() + "\n" + data;
        } else{
            return "abcdefg";
        }
    }

    // Checks for specific patient(only view own record)
    public String toString(Patient p){
        if(Human.getCurrentLogin() == p){
            return "doctor name: " + doctor.getName() +"\n" + "patient name: " + patient.getName() + "\n" + data;
        } else{
            return "abcdefg";
        }
    }

    @SuppressWarnings({"ConvertToTryWithResources", "UseSpecificCatch"})
    public static MedicalRecord load(File a, int patientID, ArrayList<Patient> pList, ArrayList<Doctor> dList){
        Patient p = null;
        Doctor d = null;
        String data = "";
        try {
            Scanner sc = new Scanner(a);
            p = pList.get(patientID);   // Use id to get patient
            d = dList.get(Integer.parseInt(sc.nextLine())); // drID  

            while(sc.hasNextLine()){    // Read others as data
                data += sc.nextLine() + "\n";
            }
            sc.close();
        } catch (Exception e) {
            System.err.println("ERROR ERROR EROR");
        }
        return new MedicalRecord(d, p, data);   // return MR object
    }
}
