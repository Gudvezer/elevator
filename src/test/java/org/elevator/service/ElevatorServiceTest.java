package org.elevator.service;

import org.elevator.domain.Elevator;
import org.elevator.domain.type.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.elevator.fabric.ElevatorFactory.createDefaultElevator;

public class ElevatorServiceTest {

	private final ElevatorService elevatorService = new ElevatorService();

	@Test
	public void testElevatorDefaultFloor() {
		Elevator elevator = createDefaultElevator();
		Assertions.assertEquals(elevator.getCurrentFloor(), 0);
	}

	@Test
	public void testElevatorDefaultDirection() {
		Elevator elevator = createDefaultElevator();
		Assertions.assertEquals(elevator.getDirection(), Direction.IDLE);
	}

	@Test
	public void testElevatorIsCalled() {
		Elevator elevator = createDefaultElevator();
		elevatorService.callElevator(elevator, 2);
		Assertions.assertTrue(elevator.getRequests().contains(2));
	}

	@Test
	public void testElevatorIsCalledNonValidMax() {
		Elevator elevator = createDefaultElevator();
		elevatorService.callElevator(elevator, 6);
		Assertions.assertFalse(elevator.getRequests().contains(6));
	}

	@Test
	public void testElevatorIsCalledNonValidMin() {
		Elevator elevator = createDefaultElevator();
		elevatorService.callElevator(elevator, -1);
		Assertions.assertFalse(elevator.getRequests().contains(-1));
	}

	@Test
	public void testHandleTempArrivalSuccess() {
		Elevator elevator = createDefaultElevator();
		elevatorService.callElevator(elevator, 4);
		elevatorService.callElevator(elevator, 2);
		for (int i = 0; i < 4; i++){
			elevatorService.move(elevator, 4);
		}
		Assertions.assertFalse(elevator.getRequests().contains(2));
	}

	@Test
	public void testMoveUp() {
		Elevator elevator = createDefaultElevator();
		Assertions.assertEquals(elevator.getCurrentFloor(), 0);
		elevatorService.moveUp(elevator);
		Assertions.assertEquals(elevator.getCurrentFloor(), 1);
		Assertions.assertEquals(elevator.getDirection(), Direction.UP);
	}

	@Test
	public void testMoveDown() {
		Elevator elevator = createDefaultElevator();
		Assertions.assertEquals(elevator.getCurrentFloor(), 0);
		elevatorService.moveDown(elevator);
		Assertions.assertEquals(elevator.getCurrentFloor(), -1);
		Assertions.assertEquals(elevator.getDirection(), Direction.DOWN);
	}

	@Test
	public void testHandleArrival() {
		Elevator elevator = createDefaultElevator();
		elevatorService.callElevator(elevator, 0);
		elevatorService.handleArrival(elevator);
		Assertions.assertFalse(elevator.getRequests().contains(0));
		Assertions.assertEquals(elevator.getDirection(), Direction.IDLE);
	}

	@Test
	public void testMoveTrue() {
		Elevator elevator = createDefaultElevator();
		elevatorService.callElevator(elevator, 0);
		boolean result = elevatorService.move(elevator, 0);
		Assertions.assertFalse(elevator.getRequests().contains(0));
		Assertions.assertEquals(elevator.getDirection(), Direction.IDLE);
		Assertions.assertTrue(result);
	}

	@Test
	public void testMoveFalse() {
		Elevator elevator = createDefaultElevator();
		elevatorService.callElevator(elevator, 2);
		boolean result = elevatorService.move(elevator, 2);
		Assertions.assertTrue(elevator.getRequests().contains(2));
		Assertions.assertEquals(elevator.getDirection(), Direction.UP);
		Assertions.assertFalse(result);
	}
}
