package com.homework.wineries.web;

import com.homework.wineries.interfaces.IFilter;

import java.util.ArrayList;
import java.util.List;

public class Pipe<T> {
    private final List<IFilter<T>> filters = new ArrayList<>();

    public void addFilter(IFilter<T> filter) {
        filters.add(filter);
    }

    public T runFilters(T input) {
        for (IFilter<T> filter: filters) {
            input = filter.execute(input);
        }
        return input;
    }
}
