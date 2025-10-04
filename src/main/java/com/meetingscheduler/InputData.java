package com.meetingscheduler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
public class InputData {
    private List<String> rooms;
    private List<Employee> employees;
    private List<Meeting> meetings;
    public InputData() {}
    @JsonCreator
    public InputData(@JsonProperty("rooms") List<String> rooms,
                     @JsonProperty("employees") List<Employee> employees,
                     @JsonProperty("meetings") List<Meeting> meetings) {
        this.rooms = rooms;
        this.employees = employees;
        this.meetings = meetings;
    }
    public List<String> getRooms() { return rooms; }
    public void setRooms(List<String> rooms) { this.rooms = rooms; }
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
    public List<Meeting> getMeetings() { return meetings; }
    public void setMeetings(List<Meeting> meetings) { this.meetings = meetings; }
}
