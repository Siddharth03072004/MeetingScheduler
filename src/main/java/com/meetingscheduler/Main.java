package com.meetingscheduler;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static InputData inputData = null;
    private static List<Meeting> scheduled = null;
    private static List<String> finalRooms = null;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("MeetingSchedulerDSA - Interactive Console UI (Maven)"); 
        System.out.println("Usage: mvn package && java -jar target/MeetingSchedulerDSA-1.0-SNAPSHOT-jar-with-dependencies.jar [input.json] [output.json]"); 
        String defaultIn = args.length>0?args[0]:"input/input.json";
        String defaultOut = args.length>1?args[1]:"output/output.json";
        String inputPath = defaultIn;
        String outputPath = defaultOut;
        while (true) {
            System.out.println(); 
            System.out.println("Menu: \n1) Load input JSON (current: " + inputPath + ")\n2) Show loaded summary\n3) Schedule meetings\n4) Show schedule\n5) Compute minimum rooms required\n6) Detect conflicts\n7) Find best common slot\n8) Write output JSON (current: " + outputPath + ")\n9) Change input/output path\n0) Exit"); 
            System.out.print("Choose: "); 
            String c = sc.nextLine().trim();
            try {
                int choice = Integer.parseInt(c);
                switch (choice) {
                    case 1 -> { System.out.println("Loading input from " + inputPath); inputData = SchedulerUtils.readInput(inputPath); System.out.println("Loaded: rooms=" + (inputData.getRooms()==null?0:inputData.getRooms().size()) + ", employees=" + (inputData.getEmployees()==null?0:inputData.getEmployees().size()) + ", meetings=" + (inputData.getMeetings()==null?0:inputData.getMeetings().size())); }
                    case 2 -> { if (inputData == null) { System.out.println("No input loaded."); } else { System.out.println("Rooms: " + inputData.getRooms()); System.out.println("Employees:"); for (Employee e : inputData.getEmployees()) System.out.println("  " + e); System.out.println("Meetings:"); for (Meeting m : inputData.getMeetings()) System.out.println("  " + m); } }
                    case 3 -> { if (inputData==null) { System.out.println("Load input first."); } else { finalRooms = new java.util.ArrayList<>(); scheduled = SchedulerUtils.scheduleMeetings(inputData.getMeetings(), inputData.getRooms(), finalRooms); System.out.println("Scheduling done. Rooms now: " + finalRooms); } }
                    case 4 -> { if (scheduled==null) System.out.println("No schedule available. Run scheduling first."); else for (Meeting m : scheduled) System.out.println("  " + m); }
                    case 5 -> { if (inputData==null) System.out.println("Load input first."); else System.out.println("Minimum rooms required: " + SchedulerUtils.computeMinRooms(inputData.getMeetings())); }
                    case 6 -> { if (scheduled==null) System.out.println("Run scheduling first."); else { List<Conflict> conf = SchedulerUtils.detectConflicts(scheduled); if (conf.isEmpty()) System.out.println("No conflicts detected."); else for (Conflict cf : conf) System.out.println("  " + cf.getParticipant() + " -> " + cf.getConflictingMeetings()); } }
                    case 7 -> { if (inputData==null) System.out.println("Load input first."); else { Interval best = SchedulerUtils.findBestCommonSlot(inputData.getEmployees()); if (best==null) System.out.println("No common slot found."); else System.out.println("Best slot: " + best); } }
                    case 8 -> { if (scheduled==null) System.out.println("Run scheduling first."); else { OutputData out = new OutputData(finalRooms, scheduled, SchedulerUtils.computeMinRooms(inputData.getMeetings()), SchedulerUtils.detectConflicts(scheduled), SchedulerUtils.findBestCommonSlot(inputData.getEmployees())); SchedulerUtils.writeOutput(outputPath, out); System.out.println("Wrote output to " + outputPath); } }
                    case 9 -> { System.out.print("Enter input path (enter to keep current): "); String ip = sc.nextLine().trim(); if (!ip.isEmpty()) inputPath = ip; System.out.print("Enter output path (enter to keep current): "); String op = sc.nextLine().trim(); if (!op.isEmpty()) outputPath = op; System.out.println("Paths updated."); }
                    case 0 -> { System.out.println("Bye."); sc.close(); return; }
                    default -> System.out.println("Invalid choice."); 
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
