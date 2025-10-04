package com.meetingscheduler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Interval {
    public int start;
    public int end;
    public Interval() {}
    @JsonCreator
    public Interval(@JsonProperty("0") Integer t0, @JsonProperty("1") Integer t1) {
        if (t0 != null && t1 != null) {
            this.start = t0;
            this.end = t1;
        }
    }
    public Interval(int s, int e) { this.start = s; this.end = e; }
    @Override
    public String toString() { return "[" + start + ", " + end + "]"; }
}
