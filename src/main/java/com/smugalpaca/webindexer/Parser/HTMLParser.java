package com.smugalpaca.webindexer.Parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.stream.Collectors;

public class HTMLParser {

    // Extracts all hyperlinks from the HTML.
    public List<String> parseLinks(String html) {
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("a[href]");
        return links.stream()
                .map(link -> link.attr("abs:href"))
                .filter(link -> !link.isEmpty())
                .collect(Collectors.toList());
    }

    // A simple example: extract keywords from the body text by splitting on spaces.
    // (You can expand this with more advanced parsing later.)
    public List<String> parseKeywords(String html) {
        Document doc = Jsoup.parse(html);
        String bodyText = doc.body().text();
        // Simple split by space and remove punctuation; in a real app, you’d filter stopwords, etc.
        return List.of(bodyText.split("\\s+"));
    }
}