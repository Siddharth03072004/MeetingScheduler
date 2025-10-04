package com.meetingscheduler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
public class Employee {
    private String name;
    private List<Interval> availability;
    public Employee() { this.availability = new ArrayList<>(); }
    @JsonCreator
    public Employee(@JsonProperty("name") String name,
                    @JsonProperty("availability") List<Interval> availability) {
        this.name = name;
        this.availability = availability == null ? new ArrayList<>() : availability;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Interval> getAvailability() { return availability; }
    public void setAvailability(List<Interval> availability) { this.availability = availability; }
    @Override
    public String toString() {
        return name + " -> " + availability;
    }
}
