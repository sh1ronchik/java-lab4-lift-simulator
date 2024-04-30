import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Main class for the elevator simulation application.
 * This class initializes the simulation, sets up the building, and schedules tasks for the elevator.
 */
public class Main {

    /**
     * Entry point of the elevator simulation application.
     * This method initializes the simulation by setting up the building and scheduling tasks for the elevator.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of floors in the building: ");
            int maxFloor = scanner.nextInt();

            System.out.print("Enter the interval for requests in seconds: ");
            int interval = scanner.nextInt();

            Building building = new Building(maxFloor);

            ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
            service.scheduleAtFixedRate(new Runnable() {
                int idPassenger = 0;
                @Override
                public void run() {
                    int currentFloor = ThreadLocalRandom.current().nextInt(1, maxFloor + 1);
                    int targetFloor;
                    do {
                        targetFloor = ThreadLocalRandom.current().nextInt(1, maxFloor + 1);
                    } while (currentFloor == targetFloor);
                    idPassenger ++;
                    building.addPassengersInQueue(new Call(currentFloor, targetFloor, idPassenger));
                }}, 0, interval, TimeUnit.SECONDS);

            service.scheduleAtFixedRate(building::run1, 0, 1, TimeUnit.SECONDS);
            service.scheduleAtFixedRate(building::run2, 0, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}