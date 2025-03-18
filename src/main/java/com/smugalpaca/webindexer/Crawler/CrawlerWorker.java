package com.smugalpaca.webindexer.Crawler;

import com.smugalpaca.webindexer.debug.LinkNode;
import com.smugalpaca.webindexer.debug.UrlDepthPair;
import com.smugalpaca.webindexer.Index.Indexer;
import com.smugalpaca.webindexer.Parser.HTMLParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;


    public class CrawlerWorker implements Runnable {
        private final UrlDepthPair pair;
        private final Indexer indexer;
        private final HTMLParser parser;
        private final BlockingQueue<UrlDepthPair> urlQueue;
        private final Set<String> visited;
        private final int maxHops;
        private final HttpClient httpClient;

        public CrawlerWorker(UrlDepthPair pair, Indexer indexer, HTMLParser parser,
                             BlockingQueue<UrlDepthPair> urlQueue, Set<String> visited,
                             int maxHops) {
            this.pair = pair;
            this.indexer = indexer;
            this.parser = parser;
            this.urlQueue = urlQueue;
            this.visited = visited;
            this.maxHops = maxHops;
            this.httpClient = HttpClient.newHttpClient();
        }

        @Override
        public void run() {
            try {
                // Fetch the page.
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(pair.getUrl()))
                        .GET()
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                String html = response.body();

                // Parse and index the page.
                var keywords = parser.parseKeywords(html);
                indexer.index(pair.getUrl(), keywords);

                // Parse links from the page.
                List<String> links = parser.parseLinks(html);

                // Only add new links if we haven't reached the max hops.
                if (pair.getDepth() < maxHops) {
                    for (String link : links) {
                        // Check if the URL is new.
                        if (visited.add(link)) {
                            // Create a new LinkNode for this discovered URL.
                            LinkNode childNode = new LinkNode(link);
                            // Attach the child node to the parent's node.
                            pair.getNode().addChild(childNode);
                            // Enqueue the link with incremented depth and its corresponding node.
                            urlQueue.offer(new UrlDepthPair(link, pair.getDepth() + 1, childNode));
                        }
                    }
                }
                System.out.println("Crawled: " + pair.getUrl() + " at depth " + pair.getDepth() +
                        " | Found " + links.size() + " links.");
            } catch (Exception e) {
                System.err.println("Failed to crawl " + pair.getUrl() + ": " + e.getMessage());
            }
        }
    }