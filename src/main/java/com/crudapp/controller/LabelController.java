package com.crudapp.controller;

import com.crudapp.model.Label;
import com.crudapp.service.LabelService;

import java.util.List;
import java.util.Scanner;

public class LabelController {

    private final LabelService labelService;
    private  final Scanner scanner;

    public LabelController(LabelService labelService, Scanner scanner) {
        this.labelService = labelService;
        this.scanner = scanner;
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Меню лейбла ===");
            System.out.println("1. Создать лейбл");
            System.out.println("2. Обновить лейбл");
            System.out.println("3. Получить лейбл по ID");
            System.out.println("4. Получить все лейблы");
            System.out.println("5. Удалить лейбл");
            System.out.println("0. Назад в основное меню");
            System.out.println("Выбери что нибудь, пж :)");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> createLabel();
                case "2" -> updateLabel();
                case "3" -> getLabelById();
                case "4" -> getAllLabels();
                case "5" -> deleteLabel();
                case "0" -> exit = true;
                default -> System.out.println("Чепуху тыкнул. Тыкай снова");
            }
        }
    }

    private void createLabel() {
        System.out.print("Введите имя лейбла: ");
        String name = scanner.nextLine();
        Label label = labelService.createLabel(name);
        System.out.println("Создан лейбл: " + label);
    }

    private void updateLabel() {
        System.out.print("Введите ID лейбла для обновления: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Введите новое имя для лейбла: ");
        String name = scanner.nextLine();
        Label updatedLabel = labelService.updateLabel(id, name);
        System.out.println("Лейбл обновлен: " + updatedLabel);
    }

    private void getLabelById() {
        System.out.print("Введите ID лейбла: ");
        Long id = Long.parseLong(scanner.nextLine());
        Label label = labelService.getLabelById(id);
        System.out.println("Лейбл: " + label);
    }

    private void getAllLabels() {
        List<Label> labels = labelService.getAllLabels();
        if (labels.isEmpty()) {
            System.out.println("No labels found");
        } else {
            labels.forEach(System.out::println);
        }
    }

    private void deleteLabel() {
        System.out.print("Введи ID лейбла для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());
        labelService.deleteLabel(id);
        System.out.println("Label deleted.");
    }
}
