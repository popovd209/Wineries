package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

public class HandleTypesColumnFilter implements IFilter<String[]> {
    @Override
    public String[] execute(String[] input) {
        input[3] = input[3].replaceAll("[\\[\\]']", "");
        input[3] = input[3].replaceAll("_", " ");

        return input;
    }
}
