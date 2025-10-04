package com.meetingscheduler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
public class SchedulerUtils {
    public static InputData readInput(String inputPath) throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return mapper.readValue(Paths.get(inputPath).toFile(), InputData.class);
    }
    public static List<Meeting> scheduleMeetings(List<Meeting> meetings, List<String> initialRooms, List<String> roomsOut) {
        if (meetings == null) return Collections.emptyList();
        List<String> rooms = new ArrayList<>();
        if (initialRooms != null) rooms.addAll(initialRooms);
        if (rooms.isEmpty()) { rooms.add("Room-1"); }
        meetings.sort((a, b) -> {
            if (b.getPriority() != a.getPriority()) return b.getPriority() - a.getPriority();
            return a.getTime().start - b.getTime().start;
        });
        PriorityQueue<int[]> roomHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        for (int i = 0; i < rooms.size(); i++) { roomHeap.offer(new int[]{0, i}); }
        int nextRoomIndex = rooms.size();
        for (Meeting m : meetings) {
            int start = m.getTime().start;
            if (!roomHeap.isEmpty() && roomHeap.peek()[0] <= start) {
                int[] pair = roomHeap.poll();
                int idx = pair[1];
                String roomId = rooms.get(idx);
                m.setAssignedRoom(roomId);
                roomHeap.offer(new int[]{m.getTime().end, idx});
            } else {
                String newRoomId = "Room-" + (nextRoomIndex + 1);
                rooms.add(newRoomId);
                int idx = nextRoomIndex;
                nextRoomIndex++;
                m.setAssignedRoom(newRoomId);
                roomHeap.offer(new int[]{m.getTime().end, idx});
            }
        }
        roomsOut.clear();
        roomsOut.addAll(rooms);
        return meetings;
    }
    public static int computeMinRooms(List<Meeting> meetings) {
        if (meetings == null || meetings.isEmpty()) return 0;
        List<Meeting> copy = new ArrayList<>(meetings);
        copy.sort(Comparator.comparingInt(m -> m.getTime().start));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (Meeting m : copy) {
            if (!heap.isEmpty() && heap.peek() <= m.getTime().start) { heap.poll(); }
            heap.offer(m.getTime().end);
        }
        return heap.size();
    }
    public static List<Conflict> detectConflicts(List<Meeting> meetings) {
        Map<String, List<Meeting>> byParticipant = new HashMap<>();
        for (Meeting m : meetings) {
            for (String p : m.getParticipants()) {
                byParticipant.computeIfAbsent(p, k -> new ArrayList<>()).add(m);
            }
        }
        List<Conflict> conflicts = new ArrayList<>();
        for (Map.Entry<String, List<Meeting>> e : byParticipant.entrySet()) {
            String participant = e.getKey();
            List<Meeting> list = e.getValue();
            list.sort(Comparator.comparingInt(m -> m.getTime().start));
            List<String> currentConflictGroup = new ArrayList<>();
            int curEnd = Integer.MIN_VALUE;
            for (Meeting m : list) {
                if (currentConflictGroup.isEmpty()) {
                    currentConflictGroup.add(m.getTitle());
                    curEnd = m.getTime().end;
                } else {
                    if (m.getTime().start < curEnd) {
                        currentConflictGroup.add(m.getTitle());
                        curEnd = Math.max(curEnd, m.getTime().end);
                    } else {
                        if (currentConflictGroup.size() > 1) {
                            conflicts.add(new Conflict(participant, new ArrayList<>(currentConflictGroup)));
                        }
                        currentConflictGroup.clear();
                        currentConflictGroup.add(m.getTitle());
                        curEnd = m.getTime().end;
                    }
                }
            }
            if (currentConflictGroup.size() > 1) {
                conflicts.add(new Conflict(participant, new ArrayList<>(currentConflictGroup)));
            }
        }
        return conflicts;
    }
    public static Interval findBestCommonSlot(List<Employee> employees) {
        List<int[]> events = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getAvailability() == null) continue;
            for (Interval it : e.getAvailability()) {
                if (it == null) continue;
                events.add(new int[]{it.start, 1});
                events.add(new int[]{it.end, -1});
            }
        }
        if (events.isEmpty()) return null;
        events.sort((a,b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return b[1] - a[1];
        });
        int cur = 0;
        int max = 0;
        Integer bestStart = null;
        Integer bestEnd = null;
        for (int i = 0; i < events.size(); i++) {
            int t = events.get(i)[0];
            int delta = events.get(i)[1];
            cur += delta;
            if (cur > max) {
                max = cur;
                bestStart = t;
                int cur2 = cur;
                int j = i + 1;
                while (j < events.size()) {
                    cur2 += events.get(j)[1];
                    if (cur2 < max) {
                        bestEnd = events.get(j)[0];
                        break;
                    }
                    j++;
                }
                if (bestEnd == null && j >= events.size()) {
                    bestEnd = events.get(events.size()-1)[0];
                }
                break;
            }
        }
        if (bestStart != null && bestEnd != null && bestEnd > bestStart) {
            return new Interval(bestStart, bestEnd);
        }
        return null;
    }
    public static void writeOutput(String outputPath, OutputData out) throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(outputPath), out);
    }
}
