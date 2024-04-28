public class Call {
    private final int targetFloor;
    private final int currentFloor;
    private final int id;
    private final Direction direction;

    public Call(int currentFloor, int targetFloor, int id) {
        this.targetFloor = targetFloor;
        this.currentFloor = currentFloor;
        this.id = id;
        this.direction = targetFloor < currentFloor ? Direction.DOWN : Direction.UP;
    }

    public int getTargetFloor() { return targetFloor; }
    public int getCurrentFloor() { return currentFloor; }
    public int getId() { return id; }
    public Direction getDirection() { return direction; }
}