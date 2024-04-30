import java.util.*;

/**
 * Represents a building with an elevator system, managing the movement of elevators and passengers.
 */
public class Building {
    int maxFloor;
    int minFloor;
    List<Call> queuePassengers = new ArrayList<>();
    final Elevator elevator1 = new Elevator(1);
    final Elevator elevator2 = new Elevator(2);

    /**
     * Constructs a new Building with the specified maximum number of floors.
     * Initializes the building with a minimum floor of 1 and the specified maximum floor.
     *
     * @param maxFloor the maximum number of floors in the building
     */
    public Building(int maxFloor) {
        minFloor = 1;
        this.maxFloor = maxFloor;
    }

    /**
     * Adds a passenger call to the queue. This method is thread-safe and synchronized.
     * It adds a call to the queue and prints a message indicating the passenger's wait status.
     *
     * @param call the call to be added to the queue
     */
    public synchronized void addPassengersInQueue(Call call) {
        queuePassengers.add(call);
        System.out.printf("Passenger №%d is waiting on floor №%d%n", call.getId(), call.getCurrentFloor());
    }

    /**
     * Updates the position of the first elevator by calling the step method with the first elevator and the second elevator as parameters.
     */
    public void run1() {
        step(elevator1, elevator2);
    }

    /**
     * Updates the position of the second elevator by calling the step method with the second elevator and the first elevator as parameters.
     */
    public void run2() {
        step(elevator2, elevator1);
    }

    /**
     * Moves the specified elevator. This method is responsible for the elevator's movement logic, including handling passenger calls and direction changes.
     * It is synchronized to ensure thread safety during concurrent operations.
     *
     * @param elevator the elevator to move
     * @param another the elevator that will not move, used for comparison to determine the best elevator for a call
     */
    private synchronized void step(Elevator elevator, Elevator another) {
        System.out.printf("Elevator №%d is currently on floor №%d %n", elevator.getId(), elevator.getCurrentFloor());

        Out(elevator);
        updateQueue(elevator);
        InElevator(elevator);

        if (elevator.getDirectionCurrent() == another.getDirectionCurrent() && another.getDirectionCurrent() == Direction.STOP
                && !queuePassengers.isEmpty()) {
            if (Math.abs(another.getCurrentFloor() - queuePassengers.get(0).getCurrentFloor())
                    < Math.abs(elevator.getCurrentFloor() - queuePassengers.get(0).getCurrentFloor())) {
                return;
            }
        }

        if (elevator.getDirectionCurrent() == Direction.STOP && !queuePassengers.isEmpty()) {
            Call call = queuePassengers.remove(0);
            elevator.setTargetFloor(call.getCurrentFloor());

            if (!elevator.getQueuePassengers().containsKey(call.getCurrentFloor())) {
                elevator.getQueuePassengers().put(call.getCurrentFloor(), new ArrayList<>());
            }
            elevator.getQueuePassengers().get(call.getCurrentFloor()).add(call);

            if (call.getCurrentFloor() > elevator.getCurrentFloor()) elevator.setDirectionCurrent(Direction.UP);
            else elevator.setDirectionCurrent(Direction.DOWN);

            elevator.setDirectionTarget(call.getDirection());

            updateQueue(elevator);
            InElevator(elevator);
        }

        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        if (elevator.getDirectionCurrent() == Direction.UP && elevator.getCurrentFloor() + 1 <= maxFloor) {
            elevator.incrementFloor();
        }
        if (elevator.getDirectionCurrent() == Direction.DOWN && elevator.getCurrentFloor() - 1 >= minFloor) {
            elevator.decrementFloor();
        }
    }

    /**
     * Adds suitable calls to the elevator's queue based on the elevator's current direction and target direction.
     * This method updates the elevator's queue with calls that match its direction, ensuring efficient movement.
     *
     * @param elevator the elevator currently moving, whose queue is to be updated
     */
    private void updateQueue(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        if (elevator.getDirectionCurrent() == elevator.getDirectionTarget()) {
            Iterator<Call> iterator = queuePassengers.iterator();
            while (iterator.hasNext()) {
                Call call = iterator.next();
                if (call.getDirection() == elevator.getDirectionCurrent()) {
                    if (call.getCurrentFloor() <= elevator.getCurrentFloor() && elevator.getDirectionCurrent() == Direction.DOWN
                            || call.getCurrentFloor() >= elevator.getCurrentFloor() && elevator.getDirectionCurrent() == Direction.UP) {

                        if (!elevator.getQueuePassengers().containsKey(call.getCurrentFloor())) {
                            elevator.getQueuePassengers().put(call.getCurrentFloor(), new ArrayList<>());
                        }

                        elevator.getQueuePassengers().get(call.getCurrentFloor()).add(call);
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * Handles passengers entering the elevator. This method checks if there are any passengers on the current floor who are waiting to enter the elevator.
     * It adds these passengers to the elevator's passenger list and updates the elevator's direction based on the passengers' target floors.
     *
     * @param elevator the elevator currently moving, to which passengers may enter
     */
    private void InElevator(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        if (!elevator.getQueuePassengers().containsKey(elevator.getCurrentFloor())) {
            elevator.getQueuePassengers().put(elevator.getCurrentFloor(), new ArrayList<>());
        }

        Iterator<Call> iterator = elevator.getQueuePassengers().get(elevator.getCurrentFloor()).iterator();
        while (iterator.hasNext()) {
            Call call = iterator.next();
            elevator.getPassengersInElevator().put(call.getId(), call.getTargetFloor());
            elevator.setDirectionCurrent(call.getDirection());

            if (elevator.getTargetFloor() < call.getTargetFloor() && elevator.getDirectionCurrent() == Direction.UP) {
                elevator.setTargetFloor(call.getTargetFloor());
            }
            if (elevator.getTargetFloor() > call.getTargetFloor() && elevator.getDirectionCurrent() == Direction.DOWN) {
                elevator.setTargetFloor(call.getTargetFloor());
            }

            System.out.printf("Passenger №%d entered elevator №%d on floor №%d and is going to floor №%d%n",
                    call.getId(), elevator.getId(), call.getCurrentFloor(), call.getTargetFloor());
            iterator.remove();
        }
        if (elevator.getQueuePassengers().get(elevator.getCurrentFloor()).isEmpty()) {
            elevator.getQueuePassengers().remove(elevator.getCurrentFloor());
        }
    }

    /**
     * Handles passengers exiting the elevator. This method checks if any passengers in the elevator have reached their target floor.
     * It removes these passengers from the elevator and prints a message indicating their exit.
     *
     * @param elevator the elevator currently moving, from which passengers may exit
     */
    private void Out(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        List<Integer> keysForRemove = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : elevator.getPassengersInElevator().entrySet()) {
            if (entry.getValue() == elevator.getCurrentFloor()) {
                System.out.printf("Passenger №%d exited elevator №%d on floor №%d%n",
                        entry.getKey(), elevator.getId(), entry.getValue());
                keysForRemove.add(entry.getKey());
            }
        }

        keysForRemove.forEach(i -> elevator.getPassengersInElevator().remove(i));

        if (elevator.getPassengersInElevator().isEmpty() && elevator.getQueuePassengers().isEmpty()) {
            elevator.setDirectionCurrent(Direction.STOP);
        }
    }
}