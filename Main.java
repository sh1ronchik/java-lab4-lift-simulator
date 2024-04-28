import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of floors in the house: ");
            int maxFloor = scanner.nextInt();

            System.out.print("Enter the interval in seconds: ");
            int interval = scanner.nextInt();

            Building building = new Building(maxFloor);

            ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
            AtomicInteger idPassenger = new AtomicInteger(0);

            service.scheduleAtFixedRate(() -> {
                int currentFloor = ThreadLocalRandom.current().nextInt(1, maxFloor + 1);
                int targetFloor = ThreadLocalRandom.current().nextInt(1, maxFloor + 1);
                while (currentFloor == targetFloor) {
                    targetFloor = ThreadLocalRandom.current().nextInt(1, maxFloor + 1);
                }
                building.addPassengersInQueue(new Call(currentFloor, targetFloor, idPassenger.incrementAndGet()));
            }, 0, interval, TimeUnit.SECONDS);

            service.scheduleAtFixedRate(building::run1, 0, 1, TimeUnit.SECONDS);
            service.scheduleAtFixedRate(building::run2, 0, 1, TimeUnit.SECONDS);
        }
    }
}