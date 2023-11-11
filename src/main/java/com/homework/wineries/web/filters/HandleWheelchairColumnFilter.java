package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

public class HandleWheelchairColumnFilter implements IFilter<String[]> {
    @Override
    public String[] execute(String[] input) {
        if (input[13].isEmpty()) {
            input[13] = "False";
        }

        return input;
    }
}
