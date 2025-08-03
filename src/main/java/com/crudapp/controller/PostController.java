package com.crudapp.controller;

import com.crudapp.model.Post;
import com.crudapp.model.PostStatus;
import com.crudapp.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostController {
    private final PostService postService;
    private final Scanner scanner;

    public PostController(PostService postService, Scanner scanner) {
        this.postService = postService;
        this.scanner = scanner;
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Меню поста ===");
            System.out.println("1. Создать пост");
            System.out.println("2. Обновить пост");
            System.out.println("3. Получить пост по ID");
            System.out.println("4. Получить все посты");
            System.out.println("5. Удалить пост");
            System.out.println("0. Вернуться в основное меню");
            System.out.print("Тыкни куда-нибудь, пж: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> createPost();
                case "2" -> updatePost();
                case "3" -> getPostById();
                case "4" -> getAllPosts();
                case "5" -> deletePost();
                case "0" -> exit = true;
                default -> System.out.println("Чепуху тыкнул. Тыкай снова");
            }
        }
    }

    private void createPost() {
        System.out.print("Введите пост: ");
        String content = scanner.nextLine();

        System.out.print("Введи ID писателя: ");
        Long writerId = Long.parseLong(scanner.nextLine());

        System.out.print("Введи ID лейбла через запятую: ");
        String[] labelIdsInput = scanner.nextLine().split(",");
        List<Long> labelIds = new ArrayList<>();
        for (String id : labelIdsInput) {
            try {
                labelIds.add(Long.parseLong(id.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Неверный ID лейбла: " + id + ", пропущен.");
            }
        }

        Post post = postService.createPost(content, writerId, labelIds);
        System.out.println("Создан пост: " + post);
    }

    private void updatePost() {
        System.out.print("Введите ID поста для обновления: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("Введите контент для поста: ");
        String content = scanner.nextLine();

        System.out.print("Введите новый статус (ACTIVE, UNDER_REVIEW, DELETED): ");
        String statusStr = scanner.nextLine();
        PostStatus status;
        try {
            status = PostStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Не корректный статус. Обновление прервано");
            return;
        }

        Post updatePost = postService.updatePost(id, content, status);
        System.out.println("Пост обновлен: " + updatePost);
    }

    private void getPostById() {
        System.out.print("Введите ID поста: ");
        Long id = Long.parseLong(scanner.nextLine());
        Post post = postService.getPostById(id);
        System.out.println("Post: " + post);
    }

    private void getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            System.out.println("Посты не найдены.");
        } else {
            posts.forEach(System.out::println);
        }
    }

    private void deletePost() {
        System.out.print("Введите ID поста для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());
        postService.deletePost(id);
        System.out.println("Пост удален.");
    }
}
