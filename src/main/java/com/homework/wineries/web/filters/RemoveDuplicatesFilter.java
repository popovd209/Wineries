package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

import java.util.HashSet;

public class RemoveDuplicatesFilter implements IFilter<String[]> {
    HashSet<String> previousIds = new HashSet<>();
    HashSet<String> previousNames = new HashSet<>();

    @Override
    public String[] execute(String[] input) {
        if(previousIds.contains(input[1])) {
            return new String[0];
        }
        previousIds.add(input[1]);

        if(previousNames.contains(input[2])) {
            return new String[0];
        }
        previousNames.add(input[2]);

        if (!input[0].equals("ID")) {
            input[0] = String.valueOf((long) previousIds.size() - 1);
        }

        return input;
    }
}
