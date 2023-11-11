package com.homework.wineries.web.controllers;

import com.homework.wineries.web.Pipe;
import com.homework.wineries.web.filters.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/"})
public class DisplayController{

    private static List<String[]> parseCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }
    }
    @GetMapping
    public String displayPage(Model model) throws IOException, CsvException {

        List<String[]> linesList = new ArrayList<>();

        // Use ClassPathResource to obtain a File object from a relative path fixed
        File file = new ClassPathResource("wineries_final.csv").getFile();
        String csvFilePath = file.getAbsolutePath();

        List<String[]> allRows = parseCsv(csvFilePath);
        Pipe<String[]> pipe = getPipe();

        linesList.add(allRows.get(0));
        for(String[] Row : allRows.subList(1, allRows.size())){
            String[] result = pipe.runFilters(Row);

            if(!Arrays.stream(result).allMatch(String::isEmpty))
                linesList.add(result);
        }
        model.addAttribute("DisplayLines", linesList.stream().flatMap(Arrays::stream).toArray(String[]::new));

        return "MainDisplay";
    }

    private static Pipe<String[]> getPipe() {
        Pipe<String[]> pipe = new Pipe<>();

        //Filters initialization
        //This filter not really necessary column international phone number != phone number
        HandlePhoneNumbersFilter handlePhoneNumbersFilter = new HandlePhoneNumbersFilter();
        HandleReviewsFilter handleReviewsFilter = new HandleReviewsFilter();
        HandleWheelchairColumnFilter handleWheelchairColumnFilter = new HandleWheelchairColumnFilter();
        HandleTypesColumnFilter handleTypesColumnFilter = new HandleTypesColumnFilter();
        HandleWebsitesFilter handleWebsitesFilter = new HandleWebsitesFilter();
        RemoveDuplicatesFilter removeDuplicatesFilter = new RemoveDuplicatesFilter();
        WorkingHoursFilter workingHoursFilter = new WorkingHoursFilter();
        WheelchairAccesibleFilter wheelchairAccesibleFilter = new WheelchairAccesibleFilter();

        pipe.addFilter(handlePhoneNumbersFilter);
        pipe.addFilter(handleReviewsFilter);
        pipe.addFilter(handleWheelchairColumnFilter);
        pipe.addFilter(handleTypesColumnFilter);
        pipe.addFilter(handleWebsitesFilter);
        pipe.addFilter(workingHoursFilter);
        pipe.addFilter(wheelchairAccesibleFilter);
        pipe.addFilter(removeDuplicatesFilter); //Must always be last!!!!
        return pipe;
    }
}