package com.crudapp;

import java.util.Scanner;
import com.crudapp.controller.*;
import com.crudapp.repository.impl.*;
import com.crudapp.service.impl.*;

public class Main {
    public static void main(String[] args) {
        var writerRepo = new WriterRepositoryImpl();
        var postRepo = new PostRepositoryImpl();
        var labelRepo = new LabelRepositoryImpl();

        var writerService = new WriterServiceImpl(writerRepo);
        var postService = new PostServiceImpl(postRepo, writerRepo, labelRepo);
        var labelService = new LabelServiceImpl(labelRepo);

        Scanner scanner = new Scanner(System.in);

        var writerController = new WriterController(writerService, scanner);
        var postController = new PostController(postService, scanner);
        var labelController = new LabelController(labelService, scanner);

        MainController mainController = new MainController(writerController, postController, labelController, scanner);
        mainController.start();
    }
}