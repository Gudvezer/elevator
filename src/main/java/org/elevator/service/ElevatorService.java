package org.elevator.service;

import org.elevator.domain.Elevator;
import org.elevator.domain.type.Direction;

public class ElevatorService {

	public void callElevator(Elevator elevator, int floor) {
		elevator.addRequest(floor);
	}

	public boolean move(Elevator elevator, int requestFloor) {
		if (elevator.getCurrentFloor() > requestFloor) {
			moveDown(elevator);
		}

		if (elevator.getCurrentFloor() < requestFloor) {
			moveUp(elevator);
		}

		if (elevator.getRequests().contains(elevator.getCurrentFloor()) && requestFloor != elevator.getCurrentFloor()) {
			handleTempArrival(elevator);
		}

		if (elevator.getCurrentFloor() == requestFloor) {
			handleArrival(elevator);
			removeFloor(elevator);
			return true;
		}

		return false;
	}

	public void handleTempArrival(Elevator elevator) {
		System.out.println("Мы достигли промежуточного этажа. Делаем остановку и едем дальше.");
		removeFloor(elevator);
	}

	public void moveUp(Elevator elevator) {
		elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
		elevator.setDirection(Direction.UP);
		System.out.printf("Мы поднялись на %s этаж \n", elevator.getCurrentFloor());
	}

	public void moveDown(Elevator elevator) {
		elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
		elevator.setDirection(Direction.DOWN);
		System.out.printf("Мы опустились на %s этаж \n", elevator.getCurrentFloor());
	}

	public void handleArrival(Elevator elevator) {
		System.out.println("Мы достигли необходимого этажа.");
		removeFloor(elevator);
		elevator.setDirection(Direction.IDLE);
	}

	private void removeFloor(Elevator elevator) {
		elevator.getRequests().remove(elevator.getCurrentFloor());
	}
}
