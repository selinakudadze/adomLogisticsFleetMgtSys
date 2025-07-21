package utils;
import datastructures.LinkedList;
import datastructures.HashMap;
import models.Maintenance;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class MaintenanceReader {
    String fileName;
    public MaintenanceReader(String fileName){
        this.fileName = fileName;
    }

    public HashMap<Integer, Maintenance> readMaintenancesFromFile() {
        int numLines = 0;
        try (Scanner counterScanner = new Scanner(new File(this.fileName))) {
            while (counterScanner.hasNextLine()) {
                if (!counterScanner.nextLine().trim().isEmpty()) {
                    numLines++;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Maintenance file not found: " + fileName);
            return new HashMap<>();
        }
        HashMap<Integer, Maintenance> maintenances = new HashMap<>();
        try (Scanner MaintenanceScanner = new Scanner(new File(this.fileName))) {
            while (MaintenanceScanner.hasNextLine()) {
                String line = MaintenanceScanner.nextLine().trim();
                if(line.isEmpty()){
                    continue;
                }
                try {
                    String[] fields = line.split(",");
                    int n = fields.length;
                    int vehicleId = Integer.parseInt(fields[0]);
                    int daysSinceLastRepairs = Integer.parseInt(fields[1]);
                    Date dateOfLastRepairs =  !Objects.equals(fields[2], "null") ? new Date(fields[2]) : new Date();
                    if(n == 7){
                        HashMap<String, LinkedList<Float>> vehiclePartsRepaired = new HashMap<>();
                        String[] partsRepaired = fields[3].split(";");
                        String[] costsOfRepairs = fields[4].split(";");
                        int countPartsRepaired = partsRepaired.length;
                        for (int j = 0; j < countPartsRepaired; j++) {
                            LinkedList<Float> currLinkedList = new LinkedList<>();
                            String[] costsOfPartRepaired = costsOfRepairs[j].split("-");
                            for (String cost: costsOfPartRepaired) {
                                currLinkedList.add(Float.parseFloat(cost));
                            }
                            vehiclePartsRepaired.put(partsRepaired[j], currLinkedList);
                        }
                        String mechanicShop = fields[5];
                        Maintenance maintenance = new Maintenance(
                                vehicleId,
                                vehiclePartsRepaired,
                                daysSinceLastRepairs,
                                dateOfLastRepairs,
                                mechanicShop
                        );
                        String partsNeedingRepairsStr = fields[6];
                        if (!partsNeedingRepairsStr.isEmpty()) {
                            String[] partsNeedingRepairs = partsNeedingRepairsStr.split(";");
                            for (String partEntry : partsNeedingRepairs) {
                                String[] partAndPriority = partEntry.split(":");
                                String partName = partAndPriority[0];
                                int priority = Integer.parseInt(partAndPriority[1]);
                                maintenance.addPartNeedingRepairs(partName, priority);
                            }
                        }
                        maintenances.put(vehicleId, maintenance);
                    }
                    else if(n == 5){
                        String mechanicShop = fields[3];
                        Maintenance maintenance = new Maintenance(
                                vehicleId,
                                daysSinceLastRepairs,
                                dateOfLastRepairs,
                                mechanicShop
                        );
                        String partsNeedingRepairsStr = fields[4];
                        if (!partsNeedingRepairsStr.isEmpty()) {
                            String[] partsNeedingRepairs = partsNeedingRepairsStr.split(";");
                            for (String partEntry : partsNeedingRepairs) {
                                String[] partAndPriority = partEntry.split(":");
                                String partName = partAndPriority[0];
                                int priority = Integer.parseInt(partAndPriority[1]);
                                maintenance.addPartNeedingRepairs(partName, priority);
                            }
                        }
                        maintenances.put(vehicleId, maintenance);
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid Maintenance: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Maintenance file not found: " + fileName);
        }
        return maintenances;
    }
}