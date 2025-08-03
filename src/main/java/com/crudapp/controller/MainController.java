package com.crudapp.controller;

import java.util.Scanner;

public class MainController {
    private final WriterController writerController;
    private final PostController postController;
    private final LabelController labelController;
    private final Scanner scanner;

    public MainController(WriterController writerController, PostController postController, LabelController labelController, Scanner scanner) {
        this.writerController = writerController;
        this.postController = postController;
        this.labelController = labelController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> writerController.run();
                case "2" -> postController.run();
                case "3" -> labelController.run();
                case "0" -> {
                    System.out.println("Выход....");
                }
                default -> System.out.println("Нет такой команды, тыкай снова.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("""
                === Главное меню ===
                1 - Управление авторами (Writer)
                2 - Управление постами (Post)
                3 - Управление метками (Label)
                0 - Выход
                Введите команду:
                """);
    }
}
