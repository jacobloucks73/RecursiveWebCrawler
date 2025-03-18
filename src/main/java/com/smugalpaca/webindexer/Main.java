package com.smugalpaca.webindexer;


import com.smugalpaca.webindexer.Crawler.CrawlerManager;
import com.smugalpaca.webindexer.debug.LinkNode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean ready = false;
        int maxHops = 0;
        List<String> seedUrls = null;

        // Prompt for maximum hops.
        while (!ready) {
            System.out.println("Input the maximum number of link hops (e.g., 2):");
            maxHops = input.nextInt();
            input.nextLine(); // consume newline

            if (maxHops >= 0) {
                ready = true;
            } else {
                System.out.println("Please input a non-negative number.");
            }
        }

        // Prompt for the destination web link.
        System.out.println("Input the destination web link (e.g., www.example.com):");
        String webLink = input.nextLine().trim();

        if (!webLink.startsWith("http://") && !webLink.startsWith("https://")) {
            webLink = "http://" + webLink;
        }

        try {
            URL url = new URL(webLink);
            seedUrls = List.of(webLink);
            System.out.println("Valid URL: " + webLink);
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL. Please input a valid web address.");
            System.exit(1);
        }

        // Create a root node for debugging.
        LinkNode root = new LinkNode(webLink);

        // Instantiate and start the crawler.
        CrawlerManager manager = new CrawlerManager(maxHops, root);
        manager.startCrawling(seedUrls);

        System.out.println("Crawling completed.");
        System.out.println("Link tree:");
        manager.printLinkTree();
    }
}