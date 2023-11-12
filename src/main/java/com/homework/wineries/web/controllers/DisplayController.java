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

import java.io.*;
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

    public static void writeCsv(String filePath, List<String[]> data) {
        try {
            // Create a file object
            File file = new File(filePath);

            // Check if the file exists
            if (!file.exists()) {
                // Create a new file if it doesn't exist
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Unable to create the file.");
                    return; // Exit the method if the file creation fails
                }
            }

            // Open a BufferedWriter to write to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                String formattedLine = "";
                // Write data to the CSV file
                for (String[] line : data) {
                    for(String word : line){
                        formattedLine += (word + "\t");
                    }

                    formattedLine = formattedLine.substring(0, formattedLine.length()-1);
                    formattedLine = String.join("\t", line).replace("<br/>","");
                    writer.write(formattedLine);
                    writer.newLine(); // Add a new line for each record
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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
        int IDCounter = 0;
        for(String[] Row : allRows.subList(1, allRows.size())){
            String[] result = pipe.runFilters(Row);

            if(!Arrays.stream(result).allMatch(String::isEmpty)){
                result[0] = Integer.toString(IDCounter++);
                linesList.add(result);
            }
        }

        model.addAttribute("DisplayLines", linesList.stream().flatMap(Arrays::stream).toArray(String[]::new));

        // File path
        String filePath = file.getParent() + "\\wineries.csv";

        // Write to CSV
        writeCsv(filePath, linesList);

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