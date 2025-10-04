package com.meetingscheduler;
import java.util.List;
public class OutputData {
    private List<String> rooms;
    private List<Meeting> schedule;
    private Integer minRooms;
    private List<Conflict> conflicts;
    private Interval bestSlot;
    public OutputData() {}
    public OutputData(List<String> rooms, List<Meeting> schedule, Integer minRooms, List<Conflict> conflicts, Interval bestSlot) {
        this.rooms = rooms;
        this.schedule = schedule;
        this.minRooms = minRooms;
        this.conflicts = conflicts;
        this.bestSlot = bestSlot;
    }
    public List<String> getRooms() { return rooms; }
    public void setRooms(List<String> rooms) { this.rooms = rooms; }
    public List<Meeting> getSchedule() { return schedule; }
    public void setSchedule(List<Meeting> schedule) { this.schedule = schedule; }
    public Integer getMinRooms() { return minRooms; }
    public void setMinRooms(Integer minRooms) { this.minRooms = minRooms; }
    public List<Conflict> getConflicts() { return conflicts; }
    public void setConflicts(List<Conflict> conflicts) { this.conflicts = conflicts; }
    public Interval getBestSlot() { return bestSlot; }
    public void setBestSlot(Interval bestSlot) { this.bestSlot = bestSlot; }
}
