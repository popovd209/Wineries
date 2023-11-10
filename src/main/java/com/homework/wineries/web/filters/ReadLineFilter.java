package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

public class ReadLineFilter implements IFilter<String> {

    @Override
    public String execute(String input) {
        return input;
    }
}
