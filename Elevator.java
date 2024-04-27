class Elevator {
    private int currentFloor;
    private Direction direction;

    public Elevator() {
        this.currentFloor = 1;
        this.direction = Direction.NONE;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void moveToFloor(int targetFloor) {
        if (direction != Direction.NONE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Elevator moving ").append(direction).append(" from floor ").append(currentFloor).append(" to floor ").append(targetFloor);
            System.out.println(sb.toString());
        }
        currentFloor = targetFloor;
    }
}