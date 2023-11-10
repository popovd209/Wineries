package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

import java.util.HashSet;

public class RemoveDuplicatesFilter implements IFilter<String[]> {
    HashSet<String> previousIds = new HashSet<>();

    @Override
    public String[] execute(String[] input) {
        if(previousIds.contains(input[1])) {
            return new String[0];
        }
        previousIds.add(input[1]);

        return input;
    }
}
