package org.elevator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.elevator.domain.type.Direction;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@Setter
public class Elevator {

	private int currentFloor;
	private int maxFloor;
	private int minFloor;
	private Direction direction;
	private Queue<Integer> requests;

	public Elevator(int maxFloor, int minFloor) {
		currentFloor = 0;
		this.maxFloor = maxFloor;
		this.minFloor = minFloor;
		if (minFloor > currentFloor) {
			currentFloor = minFloor;
		}
		requests = new LinkedList<>();
		direction = Direction.IDLE;
	}

	public void addRequest(int floor) {
		if (!requests.contains(floor) && floor <= maxFloor && floor >= minFloor) {
			requests.add(floor);
			System.out.println("Ваш запрос принят в обработку!");
		} else {
			System.out.println("Ваш запрос уже в обработке или недоступен.");
		}
	}
}
