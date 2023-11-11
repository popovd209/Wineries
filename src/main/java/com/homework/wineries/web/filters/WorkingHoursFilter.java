package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

import java.util.*;

public class WorkingHoursFilter implements IFilter<String[]> {

    public String appendConsecutives(LinkedHashMap<String, String> inputMap){
        LinkedHashMap<String, String> temporaryMap = new LinkedHashMap<>();

        Map.Entry<String, String> lastEntry = null;
        for (Map.Entry<String, String> entry : inputMap.entrySet()) {
            if(temporaryMap.isEmpty()){
                temporaryMap.put(entry.getKey(), entry.getValue());
                lastEntry = entry;
            }
            if(Objects.equals(entry.getValue(), lastEntry.getValue())){
                lastEntry = entry;
            }
            else{
                temporaryMap.put(lastEntry.getKey(), lastEntry.getValue());
                lastEntry = entry;
            }
        }
        temporaryMap.putIfAbsent(lastEntry.getKey(), lastEntry.getValue());
        String finalString = "";
        lastEntry = null;
        int cc = 0;

        for (Map.Entry<String, String> entry : temporaryMap.entrySet()){
            if(lastEntry == null){
                lastEntry = entry;
                if(temporaryMap.size() == 1)
                    finalString += "Monday - Sunday: " + entry.getValue();
            } else {
                if(Objects.equals(lastEntry.getValue(),entry.getValue())){
                    finalString += lastEntry.getKey() + " - " + entry.getKey() + ": " + entry.getValue() + " ";
                    lastEntry = entry;
                }else{
                    cc++;
                    if(cc == 2){
                        finalString += " " + lastEntry.getKey() + ": " + lastEntry.getValue();
                        cc=0;
                        lastEntry = entry;
                    }else{
                        lastEntry = entry;
                    }
                }
            }
        }
        if(!finalString.contains(temporaryMap.lastEntry().getKey()))
            finalString += " " + temporaryMap.lastEntry().getKey() + ": " + temporaryMap.lastEntry().getValue();

        return finalString;
    }
    public String formatOpeningHours(String openingHoursString) {
        // Replace Unicode escape sequences with actual characters
        String formattedString = openingHoursString
                .replaceAll("u202f", "\u202F") // Narrow no-break space
                .replaceAll("u2009", "\u2009"); // Thin space

        // Remove single quotes from the beginning and end
        formattedString = formattedString.substring(1, formattedString.length() - 1).replace("'","");
        String[] openingHoursArray = formattedString.split(", ");

        // Create a map to store days with their respective working hours
        LinkedHashMap<String, String> dayToHoursMap = new LinkedHashMap<>();

        for(String dayHours : openingHoursArray){
            String []dayHoursParts = dayHours.split(":",2);
            String Day = dayHoursParts[0];
            String Hours = dayHoursParts[1];

            dayToHoursMap.put(Day,Hours);
        }

        formattedString = appendConsecutives(dayToHoursMap);

        return formattedString;
    }


    @Override
    public String[] execute(String[] input) {
        if(!input[7].equals("[]") && input[7].length() > 25){
            String formattedString = formatOpeningHours(input[7]);
            input[7]=formattedString;
        } else{
            input[7]="Not found";
        }
        return input;
    }
}
