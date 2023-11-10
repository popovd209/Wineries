package com.homework.wineries.web.controllers;

import com.homework.wineries.WineriesApplication;
import com.homework.wineries.web.Pipe;
import com.homework.wineries.web.filters.ReadLineFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping({"/"})
public class DisplayController{

    @GetMapping
    public String DisplayPage(Model model) throws FileNotFoundException {
        List<String> LinesList = new ArrayList<>();

        //Neccesities//
        //Scanner scanner = new Scanner(new File("../../resources/wineries_final.csv"));
        Scanner scanner = new Scanner(new File("C:\\Users\\HP\\Desktop\\FINKI\\III - Semestar 5\\DIANS\\Homework\\Wineries\\src\\main\\resources\\TestingFile.csv"));
        Pipe<String> pipe = new Pipe<>();

        //Filters//
        ReadLineFilter readLineFilter = new ReadLineFilter();

        //Filter injection//
        pipe.addFilter(readLineFilter);

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();

            String result = pipe.runFilters(line);
            LinesList.add(result);
            model.addAttribute("Display", LinesList);
        }

        return "MainDisplay";
    }
}
