import java.util.*;

/**
 * Represents an elevator in the building.
 * Each elevator has an ID, current and target floors, directions, and a map of passengers.
 */
public class Elevator {
    private int id;
    private Direction directionCurrent;
    private Direction directionTarget;
    private int currentFloor;
    private int targetFloor;
    private Map<Integer, Integer> passengersInElevator = new HashMap<>();
    private Map<Integer, List<Call>> queuePassengers = new HashMap<>();

    /**
     * Constructs a new Elevator with the specified ID.
     * Initializes the current floor to 1, target floor to 0, and direction to STOP.
     *
     * @param id the unique identifier of the elevator
     */
    public Elevator(int id) {
        this.id = id;
        targetFloor = 0;
        currentFloor = 1;
        directionCurrent = Direction.STOP;
    }

    /**
     * Returns the unique identifier of the elevator.
     *
     * @return the ID
     */
    public int getId() { return id; }

    /**
     * Sets the unique identifier of the elevator.
     *
     * @param id the new ID
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the current direction of the elevator.
     *
     * @return the current direction
     */
    public Direction getDirectionCurrent() { return directionCurrent; }

    /**
     * Sets the current direction of the elevator.
     *
     * @param directionCurrent the new current direction
     */
    public void setDirectionCurrent(Direction directionCurrent) { this.directionCurrent = directionCurrent; }

    /**
     * Returns the target direction of the elevator.
     *
     * @return the target direction
     */
    public Direction getDirectionTarget() { return directionTarget; }

    /**
     * Sets the target direction of the elevator.
     *
     * @param directionTarget the new target direction
     */
    public void setDirectionTarget(Direction directionTarget) { this.directionTarget = directionTarget; }

    /**
     * Returns the current floor of the elevator.
     *
     * @return the current floor
     */
    public int getCurrentFloor() { return currentFloor; }

    /**
     * Sets the current floor of the elevator.
     *
     * @param currentFloor the new current floor
     */
    public void setCurrentFloor(int currentFloor) { this.currentFloor = currentFloor; }

    /**
     * Returns the target floor of the elevator.
     *
     * @return the target floor
     */
    public int getTargetFloor() { return targetFloor; }

    /**
     * Sets the target floor of the elevator.
     *
     * @param targetFloor the new target floor
     */
    public void setTargetFloor(int targetFloor) { this.targetFloor = targetFloor; }

    /**
     * Returns the map of passengers currently in the elevator.
     *
     * @return the map of passengers
     */
    public Map<Integer, Integer> getPassengersInElevator() { return passengersInElevator; }

    /**
     * Sets the map of passengers currently in the elevator.
     *
     * @param passengersInElevator the new map of passengers
     */
    public void setPassengersInElevator(Map<Integer, Integer> passengersInElevator) { this.passengersInElevator = passengersInElevator; }

    /**
     * Returns the queue of passengers waiting for the elevator.
     *
     * @return the queue of passengers
     */
    public Map<Integer, List<Call>> getQueuePassengers() { return queuePassengers; }

    /**
     * Sets the queue of passengers waiting for the elevator.
     *
     * @param queuePassengers the new queue of passengers
     */
    public void setQueuePassengers(Map<Integer, List<Call>> queuePassengers) { this.queuePassengers = queuePassengers; }

    /**
     * Increments the current floor of the elevator by one.
     */
    public void incrementFloor(){ currentFloor ++; }

    /**
     * Decrements the current floor of the elevator by one.
     */
    public void decrementFloor() {currentFloor --; }
}