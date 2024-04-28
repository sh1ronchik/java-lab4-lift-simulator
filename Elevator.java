import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elevator {
    private int id;
    private Direction directionCurrent;
    private Direction directionTarget;
    private int currentFloor;
    private int targetFloor;
    private Map<Integer, Integer> passengersInElevator = new HashMap<>();
    private Map<Integer, List<Call>> queuePassengers = new HashMap<>();

    public Elevator(int id) {
        this.id = id;
        targetFloor = 0;
        currentFloor = 1;
        directionCurrent = Direction.STOP;
    }

    public void incrementFloor() { currentFloor++; }
    public void decrementFloor() { currentFloor--; }

    public int getId() { return id; }
    public Direction getDirectionCurrent() { return directionCurrent; }
    public Direction getDirectionTarget() { return directionTarget; }
    public int getCurrentFloor() { return currentFloor; }
    public int getTargetFloor() { return targetFloor; }
    public Map<Integer, Integer> getPassengersInElevator() { return passengersInElevator; }
    public Map<Integer, List<Call>> getQueuePassengers() { return queuePassengers; }

    public void setId(int id) { this.id = id; }
    public void setDirectionCurrent(Direction directionCurrent) { this.directionCurrent = directionCurrent; }
    public void setDirectionTarget(Direction directionTarget) { this.directionTarget = directionTarget; }
    public void setCurrentFloor(int currentFloor) { this.currentFloor = currentFloor; }
    public void setTargetFloor(int targetFloor) { this.targetFloor = targetFloor; }
    public void setPassengersInElevator(Map<Integer, Integer> passengersInElevator) { this.passengersInElevator = passengersInElevator; }
    public void setQueuePassengers(Map<Integer, List<Call>> queuePassengers) { this.queuePassengers = queuePassengers; }
}