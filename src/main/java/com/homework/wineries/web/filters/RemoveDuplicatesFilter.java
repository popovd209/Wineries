package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class RemoveDuplicatesFilter implements IFilter<String[]> {
    HashSet<String> previousIds = new HashSet<>();
    HashSet<String> previousNames = new HashSet<>();

    @Override
    public String[] execute(String[] input) {
        if(previousIds.contains(input[1]) || previousNames.contains(input[2])){
            Arrays.fill(input, "");
        }else{
            previousIds.add(input[1]);
            previousNames.add(input[2]);
        }
        return input;
    }
}
