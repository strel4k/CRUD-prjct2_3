package com.crudapp.controller;

import com.crudapp.model.Writer;
import com.crudapp.service.WriterService;

import java.util.List;
import java.util.Scanner;

public class WriterController {
    private final WriterService writerService;
    private final Scanner scanner;

    public WriterController(WriterService writerService, Scanner scanner) {
        this.writerService = writerService;
        this.scanner = scanner;
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Меню писателя ===");
            System.out.println("1. Создать писателя");
            System.out.println("2. Обновить писателя");
            System.out.println("3. Получить данные писателя по ID");
            System.out.println("4. Получить всех писателей");
            System.out.println("5. Удалить писателя");
            System.out.println("0. Вернуться в основное меню");
            System.out.print("Тыкни куда-нибудь, пж: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> createWriter();
                case "2" -> updateWriter();
                case "3" -> getWriterById();
                case "4" -> getAllWriters();
                case "5" -> deleteWriter();
                case "0" -> exit = true;
                default -> System.out.println("Чепуху тыкнул. Тыкай снова");
            }
        }
    }
    private void createWriter() {
        System.out.print("Введите имя писателя: ");
        String newFirstName = scanner.nextLine();

        System.out.print("Введите фамилию писателя: ");
        String newLastName = scanner.nextLine();

        Writer writer = writerService.createWriter(newFirstName, newLastName);
        System.out.println("Создан писатель: " + writer);

    }

    private void updateWriter() {
        System.out.print("Введите ID писателя для обновления: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("Введите новое имя: ");
        String newFirstName = scanner.nextLine();

        System.out.print("Введите новую фамилию: ");
        String newLastName = scanner.nextLine();

        Writer updatedWriter = writerService.updateWriter(id, newFirstName, newLastName);
        System.out.println("Писатель обновлен: " + updatedWriter);
    }

    private void getWriterById() {
        System.out.print("Введите ID писателя: ");
        Long id = Long.parseLong(scanner.nextLine());
        Writer writer = writerService.getWriterById(id);
        System.out.println("Post: " + writer);
    }

    private void getAllWriters() {
        List<Writer> writers = writerService.getAllWriters();
        if (writers.isEmpty()) {
            System.out.println("Писатели не найдены.");
        } else {
            writers.forEach(System.out::println);
        }
    }

    private void deleteWriter() {
        System.out.print("Введите ID писателя для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());
        writerService.deleteWriter(id);
        System.out.println("Писатель удален.");
    }
}
