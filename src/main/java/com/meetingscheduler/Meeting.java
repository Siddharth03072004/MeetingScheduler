package com.meetingscheduler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
public class Meeting {
    private String title;
    private Interval time;
    private int priority;
    private List<String> participants;
    private String assignedRoom;
    public Meeting() { this.participants = new ArrayList<>(); }
    @JsonCreator
    public Meeting(@JsonProperty("title") String title,
                   @JsonProperty("time") List<Integer> timeArr,
                   @JsonProperty("priority") Integer priority,
                   @JsonProperty("participants") List<String> participants) {
        this.title = title == null ? "Untitled" : title;
        if (timeArr != null && timeArr.size() >= 2) {
            this.time = new Interval(timeArr.get(0), timeArr.get(1));
        } else {
            this.time = new Interval(0,0);
        }
        this.priority = priority == null ? 1 : priority;
        this.participants = participants == null ? new ArrayList<>() : participants;
        this.assignedRoom = null;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Interval getTime() { return time; }
    public void setTime(Interval time) { this.time = time; }
    public void setTimeFromList(List<Integer> arr) { if (arr != null && arr.size() >= 2) this.time = new Interval(arr.get(0), arr.get(1)); }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public List<String> getParticipants() { return participants; }
    public void setParticipants(List<String> participants) { this.participants = participants; }
    public String getAssignedRoom() { return assignedRoom; }
    public void setAssignedRoom(String assignedRoom) { this.assignedRoom = assignedRoom; }
    @Override
    public String toString() {
        return String.format("%s %s-%s p=%d participants=%s room=%s", title, time.start, time.end, priority, participants, assignedRoom);
    }
}
