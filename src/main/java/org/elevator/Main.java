package org.elevator;

import org.elevator.domain.Elevator;
import org.elevator.service.ElevatorService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.elevator.fabric.ElevatorFactory.createDefaultElevator;
import static org.elevator.fabric.ElevatorFactory.createElevator;

public class Main {
	public static void main(String[] args) {
		System.out.println("Здравствуйте! Вы в приложении по использованию лифта.");
		ElevatorService elevatorService = new ElevatorService();
		Scanner scanner = new Scanner(System.in);
		Elevator elevator = createElevatorFlow(scanner);
		while (true) {

			System.out.println("Введите один или несколько этажей, на которые нужно отправить. Через запятую и без пробелов.");
			String inputFloors = getValidInput(scanner);
			List<Integer> floors = parseFloors(inputFloors);

			for (Integer floor : floors) {
				elevatorService.callElevator(elevator, floor);
			}
			while (!elevator.getRequests().isEmpty()) {
				int floor = elevator.getRequests().poll();
				boolean result = false;

				while (!result) {
					result = elevatorService.move(elevator, floor);
				}
			}

			int decision = getIntInput(scanner, "Вы проехали все этажи. Желаете продолжить с нынешнего этажа или создать новый лифт? (1 или 2)");

			if (decision == 2) {
				elevator = createElevatorFlow(scanner);
			}
		}
	}

	private static String getValidInput(Scanner scanner) {
		String input = scanner.next();
		while (input == null || input.trim().isEmpty()) {
			System.out.println("Пожалуйста, введите корректные этажи через запятую:");
			input = scanner.next();
		}
		return input;
	}

	private static List<Integer> parseFloors(String input) {
		try {
			return Arrays.stream(input.split(","))
					.map(String::trim)
					.map(Integer::valueOf)
					.toList();
		} catch (NumberFormatException e) {
			System.out.println("Некорректный ввод. Убедитесь, что вы вводите только числа, разделённые запятыми.");
			return List.of();
		}
	}

	private static int getIntInput(Scanner scanner, String message) {
		System.out.println(message);
		while (!scanner.hasNextInt()) {
			System.out.println("Пожалуйста, введите корректное число.");
			scanner.next();
		}
		return scanner.nextInt();
	}

	public static Elevator createElevatorFlow(Scanner scanner) {
		Elevator elevator;
		System.out.println("Хотите ли создать собственный лифт или стандартный? Введите число 1 для создания своего или любое другое для стандартного.");
		int choice = scanner.nextInt();
		if (choice == 1) {
			int max = getIntInput(scanner, "Введите макс. число этажей.");
			int min = getIntInput(scanner, "Введите мин. число этажей.");

			while (min > max) {
				max = getIntInput(scanner, "Неверно. Введите макс. число этажей.");
				min = getIntInput(scanner, "Введите мин. число этажей.");
			}
			elevator = createElevator(max, min);
		} else  {
			elevator = createDefaultElevator();
		}

		System.out.printf("Был создан лифт с параметрами: макс. этажи %s, мин. этажи %s \n", elevator.getMaxFloor(), elevator.getMinFloor());
		return elevator;
	}
}