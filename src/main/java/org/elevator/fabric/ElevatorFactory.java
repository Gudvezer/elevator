package org.elevator.fabric;

import org.elevator.domain.Elevator;

public class ElevatorFactory {

	public static Elevator createElevator(int maxFloor, int minFloor) {
		return new Elevator(maxFloor, minFloor);
	}

	public static Elevator createDefaultElevator() {
		return new Elevator(5, 0);
	}
}
