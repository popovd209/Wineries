package com.homework.wineries.web.controllers;

import com.homework.wineries.WineriesApplication;
import com.homework.wineries.web.Pipe;
import com.homework.wineries.web.filters.ReadLineFilter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping({"/"})
public class DisplayController{

    @GetMapping
    public String displayPage(Model model) throws IOException {
        List<String> linesList = new ArrayList<>();

        // Use ClassPathResource to obtain a File object from a relative path fixed
        File file = new ClassPathResource("wineries_final.csv").getFile();

        try (Scanner scanner = new Scanner(file)) {
            Pipe<String> pipe = new Pipe<>();
            ReadLineFilter readLineFilter = new ReadLineFilter();
            pipe.addFilter(readLineFilter);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String result = pipe.runFilters(line);
                linesList.add(result);
            }
        }

        model.addAttribute("DisplayLines", linesList);

        return "MainDisplay";
    }}
