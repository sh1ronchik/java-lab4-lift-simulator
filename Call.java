/**
 * Represents a call made to the elevator system.
 * Each call includes the current floor, target floor, and an ID.
 * The direction of the call (up or down) is determined based on the comparison of the target and current floors.
 */
public class Call {
    private int targetFloor;
    private int currentFloor;
    private int id;
    private Direction direction;

    /**
     * Constructs a new Call with the specified current floor, target floor, and ID.
     * The direction of the call is automatically determined based on the comparison of the target and current floors.
     *
     * @param currentFloor the current floor of the call
     * @param targetFloor the target floor of the call
     * @param id the unique identifier of the call
     */
    public Call(int currentFloor, int targetFloor, int id) {
        this.targetFloor = targetFloor;
        this.currentFloor = currentFloor;
        this.id = id;

        if (targetFloor < currentFloor) direction = Direction.DOWN;
        else direction = Direction.UP;
    }

    /**
     * Returns the target floor of the call.
     *
     * @return the target floor
     */
    public int getTargetFloor() { return targetFloor; }

    /**
     * Sets the target floor of the call.
     *
     * @param targetFloor the new target floor
     */
    public void setTargetFloor(int targetFloor) { this.targetFloor = targetFloor; }

    /**
     * Returns the current floor of the call.
     *
     * @return the current floor
     */
    public int getCurrentFloor() { return currentFloor; }

    /**
     * Sets the current floor of the call.
     *
     * @param currentFloor the new current floor
     */
    public void setCurrentFloor(int currentFloor) { this.currentFloor = currentFloor; }

    /**
     * Returns the unique identifier of the call.
     *
     * @return the ID
     */
    public int getId() { return id; }

    /**
     * Sets the unique identifier of the call.
     *
     * @param id the new ID
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the direction of the call (UP or DOWN).
     *
     * @return the direction
     */
    public Direction getDirection() { return direction; }

    /**
     * Sets the direction of the call.
     *
     * @param direction the new direction
     */
    public void setDirection(Direction direction) { this.direction = direction; }
}