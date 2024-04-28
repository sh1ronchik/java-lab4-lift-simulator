import java.util.*;

public class Building {
    private final int maxFloor;
    private final int minFloor = 1;
    private final List<Call> queuePassengers = new ArrayList<>();
    private final Elevator elevator1 = new Elevator(1);
    private final Elevator elevator2 = new Elevator(2);

    public Building(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public synchronized void addPassengersInQueue(Call call) {
        queuePassengers.add(call);
        System.out.printf("Passenger №%d is waiting on floor №%d%n", call.getId(), call.getCurrentFloor());
    }

    public void run1() {
        step(elevator1, elevator2);
    }

    public void run2() {
        step(elevator2, elevator1);
    }

    private synchronized void step(Elevator elevator, Elevator another) {
        System.out.printf("Elevator №%d is now on the floor №%d %n", elevator.getId(), elevator.getCurrentFloor());
        Out(elevator);
        updateQueue(elevator);
        InElevator(elevator);

        if (elevator.getDirectionCurrent() == another.getDirectionCurrent() && another.getDirectionCurrent() == Direction.STOP && !queuePassengers.isEmpty()) {
            if (Math.abs(another.getCurrentFloor() - queuePassengers.get(0).getCurrentFloor()) < Math.abs(elevator.getCurrentFloor() - queuePassengers.get(0).getCurrentFloor())) {
                return;
            }
        }

        if (elevator.getDirectionCurrent() == Direction.STOP && !queuePassengers.isEmpty()) {
            Call call = queuePassengers.remove(0);
            elevator.setTargetFloor(call.getCurrentFloor());

            elevator.getQueuePassengers().computeIfAbsent(call.getCurrentFloor(), k -> new ArrayList<>()).add(call);

            elevator.setDirectionCurrent(call.getCurrentFloor() > elevator.getCurrentFloor() ? Direction.UP : Direction.DOWN);
            elevator.setDirectionTarget(call.getDirection());

            updateQueue(elevator);
            InElevator(elevator);
        }

        if (elevator.getDirectionCurrent() != Direction.STOP) {
            elevator.setCurrentFloor(elevator.getDirectionCurrent() == Direction.UP && elevator.getCurrentFloor() + 1 <= maxFloor ? elevator.getCurrentFloor() + 1 :
                    elevator.getDirectionCurrent() == Direction.DOWN && elevator.getCurrentFloor() - 1 >= minFloor ? elevator.getCurrentFloor() - 1 : elevator.getCurrentFloor());
        }
    }

    private void updateQueue(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        if (elevator.getDirectionCurrent() == elevator.getDirectionTarget()) {
            queuePassengers.removeIf(call -> {
                if (call.getDirection() == elevator.getDirectionCurrent() &&
                        ((call.getCurrentFloor() <= elevator.getCurrentFloor() && elevator.getDirectionCurrent() == Direction.DOWN) ||
                                (call.getCurrentFloor() >= elevator.getCurrentFloor() && elevator.getDirectionCurrent() == Direction.UP))) {
                    elevator.getQueuePassengers().computeIfAbsent(call.getCurrentFloor(), k -> new ArrayList<>()).add(call);
                    return true;
                }
                return false;
            });
        }
    }

    private void InElevator(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        elevator.getQueuePassengers().getOrDefault(elevator.getCurrentFloor(), new ArrayList<>()).removeIf(call -> {
            elevator.getPassengersInElevator().put(call.getId(), call.getTargetFloor());
            elevator.setDirectionCurrent(call.getTargetFloor() > elevator.getCurrentFloor() ? Direction.UP : Direction.DOWN);
            System.out.printf("Passenger №%d went into the lift №%d on the floor №%d and goes to the floor №%d%n", call.getId(), elevator.getId(), call.getCurrentFloor(), call.getTargetFloor());
            return true;
        });
    }

    private void Out(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        elevator.getPassengersInElevator().entrySet().removeIf(entry -> {
            if (entry.getValue() == elevator.getCurrentFloor()) {
                System.out.printf("Passenger №%d got out of the lift №%d on the floor №%d%n", entry.getKey(), elevator.getId(), entry.getValue());
                return true;
            }
            return false;
        });

        if (elevator.getPassengersInElevator().isEmpty() && elevator.getQueuePassengers().isEmpty()) {
            elevator.setDirectionCurrent(Direction.STOP);
        }
    }
}