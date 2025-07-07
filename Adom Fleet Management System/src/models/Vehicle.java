package models;

public class Vehicle implements Comparable<Vehicle> {
    private String registrationNumber;
    private int mileage;
    private int lastServiceDate; // days since last service

    public Vehicle(String registrationNumber, int mileage, int lastServiceDate) {
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.lastServiceDate = lastServiceDate;
    }

    public int getMileage() {
        return mileage;
    }

    public int getDaysSinceLastService() {
        return lastServiceDate;
    }

    public void setDaysSinceLastService(int days) {
        this.lastServiceDate = days;
    }

    @Override
    public int compareTo(Vehicle other) {
        // Lower mileage comes first
        if (this.mileage != other.mileage) {
            return Integer.compare(other.mileage, this.mileage);  // Higher mileage = higher priority
        }
        return Integer.compare(other.lastServiceDate, this.lastServiceDate);
    }

    @Override
    public String toString() {
        return registrationNumber + " | Mileage: " + mileage + " | LastService: " + lastServiceDate;
    }
}
