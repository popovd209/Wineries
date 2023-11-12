package com.homework.wineries.web.filters;

import com.homework.wineries.interfaces.IFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandleReviewsFilter implements IFilter<String[]> {

    //TO-DO: Some Ratings are shown mixed up, needs fix
    public static String parseReviews(String input) {
        // Use a regex pattern to extract review information
//        String patternString = "\\{'review_author': '(.*?)', 'rating': '(.*?)', 'text': '(.*?)', 'relative_time_description': '(.*?)'\\}";
//        String patternString = "\\{'review_author':\\s*'([^']+)',\\s*'rating':\\s*'([^']+)',\\s*'text':\\s*'([^']+)'\\}";
        String patternString = "\\{'review_author': '(.*?)', 'rating': '(.*?)', 'text': '(.*?)'\\}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);
        List<StringBuilder> formattedReviews = new ArrayList<>();

        while (matcher.find()) {
            String reviewAuthor = matcher.group(1);
            String rating = matcher.group(2);
            String text = matcher.group(3).replace("\"", ""); // Remove escaped quotes
//            String relativeTimeDescription = matcher.group(4);

            if(rating.length()>2)
                rating = rating.substring(0,1);
            if(text.contains("relative_time_description"))
                text=text.substring(0, text.indexOf("', 'relative_time_description'"));

            StringBuilder formattedReview = new StringBuilder();
            // Formatting the extracted information
            formattedReview.append("Review Author: ").append(reviewAuthor).append(" | <br/>");
            formattedReview.append("Rating: ").append(rating).append(" | <br/>");
            formattedReview.append("Text: ").append(text).append(" | <br/>");
//            formattedReview.append("Relative Time Description: ").append(relativeTimeDescription).append("<br/>");
            formattedReview.append("------<br/>");

            // Add the formatted review to the list
            formattedReviews.add(formattedReview);
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : formattedReviews) {
            result.append(sb.toString());
        }
        return result.toString();
    }
        @Override
    public String[] execute(String[] input) {
        if(!input[12].equals("[]"))
            input[12] = parseReviews(input[12]).replace("[","").replace("]","");
        else
            input[12] = "Not found";
        return input;
    }
}
