package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

public class WheelchairAccesibleFilter implements IFilter<String[]> {

    @Override
    public String[] execute(String[] input) {
        if(input[13].equals("True"))
            input[13] = "Yes";
        else input[13] = "No";
        return input;
    }
}
