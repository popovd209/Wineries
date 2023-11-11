package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

public class HandleWebsitesFilter implements IFilter<String[]> {
    @Override
    public String[] execute(String[] input) {
        if (input[8].startsWith("http://")) {
            input[8] = input[8].replace("http://", "");
        } else if (input[8].startsWith("https://")) {
            input[8] = input[8].replace("https://", "");
        }

//        if (input[8].startsWith("www.")) {
//            input[8] = input[8].replace("www.", "");
//        }

        if (input[8].endsWith("/")) {
            input[8] = input[8].substring(0, input[8].length() - 1);
        }

        if(!input[8].isEmpty() && !input[8].contains("www."))
            input[8] = "www."+input[8];
        else
            input[8] = "Not found";

        return input;
    }
}
