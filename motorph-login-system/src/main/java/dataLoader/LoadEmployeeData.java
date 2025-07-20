package dataLoader;

import models.EmployeeProfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for loading employee profile data from a specified file path
 * and populating a list of EmployeeProfile objects.
 */
public class LoadEmployeeData {

    public static List<EmployeeProfile> loadFromFile(String filePath) {
    List<EmployeeProfile> employees = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line = br.readLine(); // Skip header
        int lineNumber = 1;

        while ((line = br.readLine()) != null) {
            lineNumber++;
            String[] fields = line.split("\t");

            if (fields.length < 19) {
                System.err.println("Skipping malformed line " + lineNumber + ": " + line);
                continue;
            }

            try {
                EmployeeProfile employee = new EmployeeProfile(
                        fields[0].trim(),
                        fields[1].trim(),
                        fields[2].trim(),
                        fields[3].trim(),
                        fields[4].trim(),
                        fields[5].trim(),
                        fields[6].trim(),
                        fields[7].trim(),
                        fields[8].trim(),
                        fields[9].trim(),
                        fields[10].trim(),
                        fields[11].trim(),
                        fields[12].trim(),
                        Double.parseDouble(fields[13].replace(",", "").trim()),
                        Double.parseDouble(fields[14].replace(",", "").trim()),
                        Double.parseDouble(fields[15].replace(",", "").trim()),
                        Double.parseDouble(fields[16].replace(",", "").trim()),
                        Double.parseDouble(fields[17].replace(",", "").trim()),
                        Double.parseDouble(fields[18].replace(",", "").trim())
                );
                employees.add(employee);
            } catch (NumberFormatException nfe) {
                System.err.println("Number format error on line " + lineNumber + ": " + line);
                nfe.printStackTrace();
            }
        }
    } catch (IOException e) {
        System.err.println("I/O Error loading employee data: " + e.getMessage());
        e.printStackTrace();
    }

    return employees;
    }
}