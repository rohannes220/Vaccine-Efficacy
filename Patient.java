import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Patient {
    private String name;
    private String gender;
    private int age;
    private String symptoms;
    private boolean takingPlacebo;
    private boolean covidNegative;
    private String observations;
    private int healTime;

    public Patient(String subject, String sex, int age, String earlySymptoms, boolean isPlacebo, boolean isCured,
            String sideEffects, int timeToCure) {
        this.name = subject;
        this.gender = sex;
        this.age = age;
        this.symptoms = earlySymptoms;
        this.takingPlacebo = isPlacebo;
        this.covidNegative = isCured;
        this.observations = sideEffects;
        this.healTime = timeToCure;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public boolean isTakingPlacebo() {
        return takingPlacebo;
    }

    public boolean isCovidNegative() {
        return covidNegative;
    }

    public String getObservations() {
        return observations;
    }

    public int getHealTime() {
        return healTime;
    }

    public String toString() {
        return name + " : " + age + ": " + gender + " : initial symptoms: " + symptoms + " taking placebo: "
                + takingPlacebo + " is cured: " + covidNegative + " final observations: " + observations
                + " heal time: "
                + healTime;
    }

    public static int[] convertToIntegerArray(double[] data) {
        int[] intData = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            intData[i] = (int) Math.round(data[i]); // Round or cast to int
        }
        return intData;
    }

    @SuppressWarnings("deprecation")
    public static ArrayList<Patient> loadPatientData(String fileName) {
        ArrayList<Patient> answer = new ArrayList<>();

        try (FileReader fr = new FileReader(fileName);
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader() // Skip the first record assuming it's the header
                        .parse(fr)) {

            for (CSVRecord record : csvParser) {
                String name = record.get("name");
                String gender = record.get("gender");
                int age = Integer.parseInt(record.get("age"));
                String symptoms = record.get("symptoms");
                boolean placebo = Boolean.parseBoolean(record.get("placebo"));
                boolean covidNegative = Boolean.parseBoolean(record.get("covidNegative"));
                String observations = record.get("observations");
                int healTime = Integer.parseInt(record.get("healTime"));
                Patient participant = new Patient(name, gender, age, symptoms, placebo, covidNegative, observations,
                        healTime);
                answer.add(participant);
            }
        } catch (IOException e) {
            // Handle the exception or rethrow it if needed
            e.printStackTrace();
        }

        return answer;
    }

    public static HashMap<String, Integer> PlaceboTakers(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<String, Integer>();
        int maleTakers = 0;
        int femaleTakers = 0;
        String m = "Male";
        String f = "Female";
        for (Patient patient : patients) {
            if (patient.getGender().equals(m)) {
                if (patient.isTakingPlacebo()) {
                    maleTakers += 1;
                }
            } else {
                if (patient.isTakingPlacebo()) {
                    femaleTakers += 1;
                }
            }
        }
        answer.put(m, maleTakers);
        answer.put(f, femaleTakers);
        return answer;
    }

    public static HashMap<String, Integer> PlaceboCureRateforTakers(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<String, Integer>();
        int maleCured = 0;
        int femaleCured = 0;
        String m = "Male";
        String f = "Female";
        for (Patient patient : patients) {
            if (patient.gender.equals(m)) {
                if (patient.isTakingPlacebo() && patient.isCovidNegative()) {
                    maleCured += 1;
                }
            } else {
                if (patient.isTakingPlacebo() && patient.isCovidNegative()) {
                    femaleCured += 1;
                }
            }
        }
        answer.put(m, maleCured);
        answer.put(f, femaleCured);
        return answer;
    }

    public static HashMap<String, Integer> MedicineTakers(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<String, Integer>();
        int maleTakers = 0;
        int femaleTakers = 0;
        String m = "Male";
        String f = "Female";
        for (Patient patient : patients) {
            if (patient.gender.equals(m)) {
                if (!patient.isTakingPlacebo()) {
                    maleTakers += 1;
                }
            } else {
                if (!patient.isTakingPlacebo()) {
                    femaleTakers += 1;
                }
            }
        }
        answer.put(m, maleTakers);
        answer.put(f, femaleTakers);
        return answer;
    }

    public static HashMap<String, Integer> MedicineCureRateforTakers(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<String, Integer>();
        int maleCured = 0;
        int femaleCured = 0;
        String m = "Male";
        String f = "Female";
        for (Patient patient : patients) {
            if (patient.gender.equals(m)) {
                if (!patient.isTakingPlacebo() && patient.isCovidNegative()) {
                    maleCured += 1;
                }
            } else {
                if (!patient.isTakingPlacebo() && patient.isCovidNegative()) {
                    femaleCured += 1;
                }
            }
        }
        answer.put(m, maleCured);
        answer.put(f, femaleCured);
        return answer;
    }

    public static HashMap<String, Integer> AverageAgeForEachGender(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<String, Integer>();
        int maleAge = 0;
        int femaleAge = 0;
        int totalFemale = getFemaleParticipants(patients);
        int totalMale = getMaleParticipants(patients);
        String m = "Male";
        String f = "Female";

        int maleCount = 0;
        int femaleCount = 0;

        for (Patient patient : patients) {
            if (patient.gender.equals(m)) {
                maleAge += patient.getAge();
                maleCount++;
            } else {
                femaleAge += patient.getAge();
                femaleCount++;
            }
        }

        int maleAverage;
        if (maleCount > 0) {
            maleAverage = maleAge / maleCount;
        } else {
            maleAverage = 0;
        }

        int femaleAverage;
        if (femaleCount > 0) {
            femaleAverage = femaleAge / femaleCount;
        } else {
            femaleAverage = 0;
        }

        answer.put(m, maleAverage);
        answer.put(f, femaleAverage);

        return answer;
    }

    public static HashMap<String, Integer> medicineTakersForAge(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<>();
        for (Patient patient : patients) {
            boolean takingPlacebo = patient.isTakingPlacebo();
            if (takingPlacebo == false) {
                int currentAge = patient.getAge();
                String currAgeString = Integer.toString(currentAge);
                // Check if the age is already in the map, if so, increment the count
                if (answer.containsKey(currAgeString)) {
                    int count = answer.get(currAgeString);
                    count += 1;
                    answer.put(currAgeString, count);
                } else {
                    // If age is not in the map, add it with count 1
                    answer.put(currAgeString, 1);
                }
            }
        }
        return answer;
    }

    public static HashMap<String, Integer> medicineCureRatevsAge(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<>();
        for (Patient patient : patients) {
            boolean takingPlacebo = patient.isTakingPlacebo();
            boolean cured = patient.isCovidNegative();
            if (takingPlacebo == false && cured == true) {
                int currentAge = patient.getAge();
                String currAgeString = Integer.toString(currentAge);
                // Check if the age is already in the map, if so, increment the count
                if (answer.containsKey(currAgeString)) {
                    int count = answer.get(currAgeString);
                    count += 1;
                    answer.put(currAgeString, count);
                } else {
                    // If age is not in the map, add it with count 1
                    answer.put(currAgeString, 1);
                }
            }
        }
        return answer;
    }

    public static HashMap<String, Integer> PlaceboCureRatevsAge(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<>();
        for (Patient patient : patients) {
            boolean takingPlacebo = patient.isTakingPlacebo();
            boolean cured = patient.isCovidNegative();
            if (takingPlacebo == false && cured == true) {
                int currentAge = patient.getAge();
                String currAgeString = Integer.toString(currentAge);
                // Check if the age is already in the map, if so, increment the count
                if (answer.containsKey(currAgeString)) {
                    int count = answer.get(currAgeString);
                    count += 1;
                    answer.put(currAgeString + " years old: ", count);
                } else {
                    // If age is not in the map, add it with count 1
                    answer.put(currAgeString + " years old: ", 1);
                }
            }
        }
        return answer;
    }

    public static ArrayList<String> getAllSyptoms(List<Patient> patients) {
        ArrayList<String> answer = new ArrayList<>();
        for (Patient patient : patients) {
            String currentSymtpms = patient.getSymptoms();
            if (answer.contains(currentSymtpms)) {
                continue;
            }
            answer.add(currentSymtpms);
        }
        return answer;
    }

    private static int getAverageHealTime(String currentSymptoms, List<Patient> patients) {
        int totalTime = 0;
        int totalInstances = 0;

        for (Patient patient : patients) {
            String symptom = patient.getSymptoms();
            if (symptom.equals(currentSymptoms)) {
                totalInstances++;
                totalTime += patient.getHealTime();
            }
        }

        // Check if totalInstances is greater than zero to avoid division by zero
        if (totalInstances > 0) {
            int averageTime = totalTime / totalInstances;
            return averageTime;
        } else {
            // Handle the case where there are no instances of the symptom
            return 0; // You can return any appropriate value here based on your requirement
        }
    }

    public static HashMap<String, Integer> symptomsvsHealTime(List<Patient> patients, ArrayList<String> symptoms) {
        HashMap<String, Integer> answer = new HashMap<>();
        for (String symptom : symptoms) {
            int averageHealTime = getAverageHealTime(symptom, patients);
            answer.put(symptom, averageHealTime);
        }
        return answer;
    }

    public static ArrayList<String> getAllGenders(List<Patient> patients) {
        ArrayList<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        return genders;
    }

    private static int getAverageHealTimeGenders(String gender, List<Patient> patients) {
        int totalHealTimes = 0;
        int totalParticipants = 0;
        for (Patient patient : patients) {
            String currentGender = patient.getGender();
            if (currentGender.equals(gender)) {
                totalHealTimes += patient.getHealTime();
                totalParticipants += 1;
            }
        }
        int averageAge = totalHealTimes / totalParticipants;
        return averageAge;
    }

    public static HashMap<String, Integer> gendervsHealTime(List<Patient> patients, ArrayList<String> genders) {
        HashMap<String, Integer> answer = new HashMap<>();
        for (String gender : genders) {
            int averageHealTime = getAverageHealTimeGenders(gender, patients);
            answer.put(gender, averageHealTime);
        }
        return answer;
    }

    public static ArrayList<String> getAllAges(List<Patient> patients) {
        ArrayList<Integer> ages = new ArrayList<>();
        for (Patient patient : patients) {
            int currentAge = patient.getAge();
            if (!ages.contains(currentAge)) {
                ages.add(currentAge);
            }
        }
        Collections.sort(ages);
        ArrayList<String> stringAges = new ArrayList<>();
        for (int age : ages) {
            stringAges.add(String.valueOf(age));
        }
        return stringAges;
    }

    public static int averageHealTimeAge(List<Patient> patients, String age) {
        int totalHealTime = 0;
        int totalInstances = 0;
        for (Patient patient : patients) {
            String currentAge = String.valueOf(patient.getAge());
            if (age.equals(currentAge)) {
                totalHealTime += patient.getHealTime();
                totalInstances += 1;
            }
        }
        return totalHealTime / totalInstances;
    }

    public static TreeMap<String, Integer> agevsHealTime(List<Patient> patients, ArrayList<String> ages) {
        TreeMap<String, Integer> answer = new TreeMap<>();
        for (String age : ages) {
            int averageHealTime = averageHealTimeAge(patients, age);
            answer.put(age, averageHealTime);
        }
        return answer;
    }

    public static ArrayList<String> takingPlaceboTorF() {
        ArrayList<String> types = new ArrayList<>();
        types.add("True");
        types.add("False");
        return types;
    }

    public static int placeboorNotPlaceboHealTime(List<Patient> patients, String takingPlacebo) {
        int totalHealTime = 0;
        int totalInstances = 0;

        boolean placeboValue = Boolean.parseBoolean(takingPlacebo);

        for (Patient patient : patients) {
            if (patient != null) {
                boolean patientPlacebo = patient.takingPlacebo;
                if (patientPlacebo == placeboValue) {
                    totalInstances++;
                    totalHealTime += patient.getHealTime();
                }
            }
        }

        if (totalInstances == 0) {
            return 0;
        }

        return (int) Math.round((double) totalHealTime / totalInstances);
    }

    public static HashMap<String, Integer> placeboandNonPlacebovsHealTime(List<Patient> patients,
            ArrayList<String> takers) {
        HashMap<String, Integer> answer = new HashMap<>();
        for (String taker : takers) {
            int averageHealTime = placeboorNotPlaceboHealTime(patients, taker);
            answer.put(taker, averageHealTime);
        }
        return answer;
    }

    public static int covidNegativeCount(List<Patient> patients, String symptoms) {
        int count = 0;
        for (Patient patient : patients) {
            String currentSym = patient.getSymptoms();
            if (currentSym.equals(symptoms) && patient.isCovidNegative()) {
                count += 1;
            }
        }
        return count;
    }

    public static int covidNegativeCountAge(List<Patient> patients, String age) {
        int count = 0;
        for (Patient patient : patients) {
            String currentAge = Integer.toString(patient.getAge());
            if (currentAge.equals(age) && patient.isCovidNegative()) {
                count += 1;
            }
        }
        return count;
    }

    public static HashMap<String, Integer> symptomsvsCovid(List<Patient> patients, ArrayList<String> allSymptoms) {
        HashMap<String, Integer> answer = new HashMap<>();
        for (String symptom : allSymptoms) {
            int covidNegativeCount = covidNegativeCount(patients, symptom);
            answer.put(symptom, covidNegativeCount);
        }
        return answer;
    }

    public static TreeMap<String, Integer> agevsCovid(List<Patient> patients, ArrayList<String> ages) {
        TreeMap<String, Integer> answer = new TreeMap<>();
        for (String age : ages) {
            int covidNegative = covidNegativeCountAge(patients, age);
            answer.put(age, covidNegative);
        }
        return answer;
    }

    public static HashMap<String, Integer> genderDistribution(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<>();
        String m = "Male";
        int mVal = getMaleParticipants(patients);
        String f = "Female";
        int fVal = getFemaleParticipants(patients);
        answer.put(m + ": ", mVal);
        answer.put(f + ": ", fVal);
        return answer;
    }

    public static HashMap<String, Integer> symptomsDistribution(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<>();

        for (Patient patient : patients) {
            String currentSymtpm = patient.getSymptoms() + " : ";
            if (answer.containsKey(currentSymtpm)) {
                answer.put(currentSymtpm, answer.get(currentSymtpm) + 1);
            } else {
                answer.put(currentSymtpm, 1);
            }
        }

        return answer;
    }

    public static HashMap<String, Integer> healTimeDistribution(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<>();

        for (Patient patient : patients) {
            String currentHealTime = Integer.toString(patient.getHealTime()) + " days: ";

            if (answer.containsKey(currentHealTime)) {
                answer.put(currentHealTime, answer.get(currentHealTime) + 1);
            } else {
                answer.put(currentHealTime, 1);
            }
        }

        return answer;
    }

    public static HashMap<String, Integer> placeboDistribution(List<Patient> patients) {
        int count = 0;
        int notTaking = 0;
        String takingPlacebo = "Taking Placebo";
        HashMap<String, Integer> answer = new HashMap<>();
        for (Patient patient : patients) {
            if (patient.isTakingPlacebo()) {
                count += 1;
                answer.put(takingPlacebo + " : ", count);
            } else {
                notTaking += 1;
                answer.put("Not Taking" + " : ", notTaking);
            }

        }
        return answer;
    }

    public static HashMap<String, Integer> finalObservationDistribution(List<Patient> patients) {
        HashMap<String, Integer> observationCounts = new HashMap<>();

        for (Patient patient : patients) {
            String currentObservation = patient.getObservations();
            if (observationCounts.containsKey(currentObservation)) {
                observationCounts.put(currentObservation, observationCounts.get(currentObservation) + 1);
            } else {

                observationCounts.put(currentObservation, 1);
            }
        }

        return observationCounts;
    }

    public static HashMap<String, Integer> ageDistribution(List<Patient> patients) {
        HashMap<String, Integer> answer = new HashMap<>();
        answer.put("20-30: ", 0);
        answer.put("30-40: ", 0);
        answer.put("40-50: ", 0);
        answer.put("50-60: ", 0);
        for (Patient patient : patients) {
            int currentAge = patient.getAge();
            if (currentAge >= 20 && currentAge <= 30) {
                answer.put("20-30: ", answer.get("20-30: ") + 1);
            } else if (currentAge >= 30 && currentAge <= 40) {
                answer.put("30-40: ", answer.get("30-40: ") + 1);
            } else if (currentAge >= 40 && currentAge <= 50) {
                answer.put("40-50: ", answer.get("40-50: ") + 1);
            } else {
                answer.put("50-60: ", answer.get("50-60: ") + 1);
            }
        }
        return answer;
    }

    public static int getFemaleParticipants(List<Patient> patients) {
        int totalFemales = 0;
        for (Patient patient : patients) {
            String gender = patient.getGender();
            if (gender.equals("Female")) {
                totalFemales += 1;
            }
        }
        return totalFemales;
    }

    public static int getMaleParticipants(List<Patient> patients) {
        return patients.size() - getFemaleParticipants(patients);
    }

    public static double getAverageAge(ArrayList<Patient> patients) {
        int totalData = patients.size();
        int totalAge = 0;
        for (Patient patient : patients) {
            int currentAge = patient.getAge();
            totalAge += currentAge;
        }
        double finalAge = (1.0 * totalAge) / totalData;
        return Math.round(finalAge);
    }

    public static double getAverageHealTime(ArrayList<Patient> patients) {
        int totalData = patients.size();
        int totalTime = 0;
        for (Patient patient : patients) {
            int currentTime = patient.getHealTime();
            totalTime += currentTime;
        }
        double averageTime = (1.0 * totalTime) / totalData;
        return Math.round(averageTime);
    }

    public static double medicineCureRate(ArrayList<Patient> patients) {
        int success = 0;
        int howManyTookMedicine = 0;
        for (Patient patient : patients) {
            boolean takePlacebo = patient.isTakingPlacebo();
            if (!takePlacebo) {
                howManyTookMedicine += 1;
                boolean covidFree = patient.isCovidNegative();
                if (covidFree) {
                    success += 1;
                }
            }
        }
        if (howManyTookMedicine == 0)
            return 0.0;
        double cureRate = ((1.0 * success) / howManyTookMedicine) * 100;
        return Math.round(cureRate);
    }

    public static double placeboCureRate(ArrayList<Patient> patients) {
        int success = 0;
        int howManyTookPlacebo = 0;
        for (Patient patient : patients) {
            boolean takePlacebo = patient.isTakingPlacebo();
            if (takePlacebo) {
                howManyTookPlacebo += 1;
                boolean covidFree = patient.isCovidNegative();
                if (covidFree) {
                    success += 1;
                }
            }
        }
        if (howManyTookPlacebo == 0)
            return 0.0;
        double cureRate = ((1.0 * success) / howManyTookPlacebo) * 100;
        return Math.round(cureRate);
    }

    public static boolean doesMedicineWork(ArrayList<Patient> patients) {
        double placeboRate = placeboCureRate(patients);
        double medicineCureRate = medicineCureRate(patients);
        if (medicineCureRate > placeboRate) {
            return true;
        }
        return false;
    }

}
