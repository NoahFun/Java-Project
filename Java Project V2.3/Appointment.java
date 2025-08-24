
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;

public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String reason;
    private int time;

    public Appointment(Doctor doctor, Patient patient, String reason, int time, LocalDate date){
        this.doctor = doctor;
        this.patient = patient;
        this.reason = reason;
        this.time = time;
        // every time a new appointment is created a file is created to save it.
        // prevent data loss if the file is closed suddenly
        newFile(date);
    }

    @Override
    public String toString(){
        return doctor.getName() + "|" + patient.getName() + "|" + reason;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    @SuppressWarnings({"UseSpecificCatch", "ConvertToTryWithResources"})
    private void newFile(LocalDate date) {
        File a = new File("appointment/"+doctor.getId()+"/"+date.toString() + " " + time + ".txt"); // object a point to the file path
        try {
            a.createNewFile();  // Creates file if not exist
            FileWriter wr = new FileWriter(a);  // Write into the file path
            wr.write(doctor.getId() + "\n");
            wr.write(patient.getId() + "\n");
            wr.write(reason + "\n");
            wr.write(time + "\n");
            wr.close();
        } catch (Exception e) {}
    }

    public void deleteFile(LocalDate date){
        File a = new File("appointment/"+doctor.getId()+"/"+date.toString() + " " + (time) + ".txt");
        try {
            a.delete();
            System.err.println(a.getAbsoluteFile());
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
