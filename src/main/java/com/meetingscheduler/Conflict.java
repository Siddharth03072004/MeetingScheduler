package com.meetingscheduler;
import java.util.List;
public class Conflict {
    private String participant;
    private List<String> conflictingMeetings;
    public Conflict() {}
    public Conflict(String participant, List<String> conflictingMeetings) {
        this.participant = participant;
        this.conflictingMeetings = conflictingMeetings;
    }
    public String getParticipant() { return participant; }
    public void setParticipant(String participant) { this.participant = participant; }
    public List<String> getConflictingMeetings() { return conflictingMeetings; }
    public void setConflictingMeetings(List<String> conflictingMeetings) { this.conflictingMeetings = conflictingMeetings; }
}
