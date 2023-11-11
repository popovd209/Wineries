package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

public class HandlePhoneNumbersFilter implements IFilter<String[]> {
    @Override
    public String[] execute(String[] input) {
//        if (input[6].startsWith("+389 ")) {
//            input[6] = input[6].replace("+389 ", "0");
//        }
        if(input[6].isEmpty())
            input[6] = "Not found";

        return input;
    }
}
