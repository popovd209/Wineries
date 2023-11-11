package com.homework.wineries.web.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.wineries.interfaces.IFilter;

import java.util.ArrayList;
import java.util.List;

public class HandleReviewsFilter implements IFilter<String[]> {
    public static String fixJson(String jsonString) {
        return jsonString.replace("'", "\"");
    }
    public static List<StringBuilder> parseJson(String jsonString) {
        List<StringBuilder> formattedReviews = new ArrayList<>();
        try {
            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON string into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Now you can work with the parsed JSON
            for (JsonNode reviewNode : jsonNode) {
                String reviewAuthor = reviewNode.get("review_author").asText();
                String rating = reviewNode.get("rating").asText();
                String text = reviewNode.get("text").asText().replace("\n", "<br/>"); // Replace newline characters with <br/>
                String relativeTimeDescription = reviewNode.get("relative_time_description").asText();

                StringBuilder formattedReview = new StringBuilder();
                formattedReview.append("Review Author: ").append(reviewAuthor).append("<br/>");
                formattedReview.append("Rating: ").append(rating).append("<br/>");
                formattedReview.append("Text: ").append(text).append("<br/>");
                formattedReview.append("Relative Time Description: ").append(relativeTimeDescription).append("<br/>");
                formattedReview.append("------<br/>");

                // Add the formatted review to the list
                formattedReviews.add(formattedReview);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            // 36 reviews perish here in this black hole due to bad format unable to fix it with code
            //Must fix this try separation by {} or smth
        }
        return formattedReviews;
    }
    @Override
    public String[] execute(String[] input) {
        if(!input[12].equals("[]"))
            input[12] = parseJson(fixJson(input[12])).toString().replace("[","").replace("]","");
        else
            input[12] = "Not found";
        return input;
    }
}
