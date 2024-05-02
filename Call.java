/**
 * Represents a call made to the elevator system. Each call includes the current floor, target floor, and an ID.
 * The direction of the call (up or down) is determined based on the comparison of the target and current floors.
 */
public class Call {
    private final int targetFloor;
    private final int currentFloor;
    private final int id;
    private final Direction direction;

    /**
     * Constructs a new Call with the specified current floor, target floor, and ID.
     * The direction of the call is automatically determined.
     *
     * @param currentFloor the current floor of the call
     * @param targetFloor the target floor of the call
     * @param id the unique identifier of the call
     */
    public Call(int currentFloor, int targetFloor, int id) {
        this.targetFloor = targetFloor;
        this.currentFloor = currentFloor;
        this.id = id;

        if (targetFloor < currentFloor) {
            this.direction = Direction.DOWN;
        } else {
            this.direction = Direction.UP;
        }
    }

    /**
     * Returns the target floor of the call.
     *
     * @return the target floor
     */
    public int getTargetFloor() { return targetFloor; }

    /**
     * Returns the current floor of the call.
     *
     * @return the current floor
     */
    public int getCurrentFloor() { return currentFloor; }

    /**
     * Returns the unique identifier of the call.
     *
     * @return the ID
     */
    public int getId() { return id; }

    /**
     * Returns the direction of the call (UP or DOWN).
     *
     * @return the direction
     */
    public Direction getDirection() { return direction; }
}