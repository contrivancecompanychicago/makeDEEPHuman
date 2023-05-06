import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Patient {

    private String patientId;
    private Date startDate;
    private Map<String, Object> medicalRecord;

    public Patient(String patientId, Date startDate) {
        this.patientId = patientId;
        this.startDate = startDate;
        this.medicalRecord = new HashMap<>();
    }

    public String getPatientId() {
        return patientId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Map<String, Object> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(Map<String, Object> medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public void addMedicalRecordEntry(String key, Object value) {
        medicalRecord.put(key, value);
    }

    public Object getMedicalRecordEntry(String key) {
        return medicalRecord.get(key);
    }

    public boolean isTakingSodiumPhenylbutyrate() {
        return getMedicalRecordEntry("medications").contains("sodium phenylbutyrate");
    }

    public int getTimeSinceStart() {
        Date currentDate = new Date();
        return (int) ((currentDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    public double getBloodLevel() {
        return (double) getMedicalRecordEntry("blood_level_sodium_phenylbutyrate");
    }

    public void reportSideEffects(String[] sideEffects) {
        // Send a message to the patient's healthcare provider
        String message = "Patient ID: " + patientId + " has reported the following side effects: " + String.join(", ", sideEffects);
        sendMessageToHealthcareProvider(message);
    }

    private void sendMessageToHealthcareProvider(String message) {
        // TODO: Implement this method
    }

    public boolean isExperiencingHyperammonemia() {
        // Get the list of signs and symptoms of hyperammonemia
        String[] signsAndSymptoms = getSignsAndSymptomsOfHyperammonemia();

        // Check if the patient is experiencing any of the signs and symptoms
        for (String signOrSymptom : signsAndSymptoms) {
            if (getMedicalRecordEntry("signs_and_symptoms").contains(signOrSymptom)) {
                return true;
            }
        }

        return false;
    }

    private String[] getSignsAndSymptomsOfHyperammonemia() {
        // TODO: Implement this method
        return new String[] {"headache", "nausea", "vomiting", "lethargy", "confusion", "seizures"};
    }
}
