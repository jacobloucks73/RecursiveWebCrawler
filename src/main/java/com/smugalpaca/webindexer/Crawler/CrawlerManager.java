package com.smugalpaca.webindexer.Crawler;

import com.smugalpaca.webindexer.debug.LinkNode;
import com.smugalpaca.webindexer.debug.UrlDepthPair;
import com.smugalpaca.webindexer.Index.Indexer;
import com.smugalpaca.webindexer.Parser.HTMLParser;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


    public class CrawlerManager {
        // Queue now holds UrlDepthPair objects.
        private final BlockingQueue<UrlDepthPair> urlQueue = new LinkedBlockingQueue<>();
        private final Set<String> visited = ConcurrentHashMap.newKeySet();
        private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
        private final Indexer indexer = new Indexer();
        private final HTMLParser parser = new HTMLParser();
        private final int maxHops;  // Maximum depth (number of hops)

        // Keep a reference to the root node for debugging visualization.
        private final LinkNode root;

        public CrawlerManager(int maxHops, LinkNode root) {
            this.maxHops = maxHops;
            this.root = root;
        }

        public void startCrawling(List<String> seedUrls) {
            // Seed the queue with UrlDepthPair objects starting at depth 0.
            for (String seed : seedUrls) {
                if (visited.add(seed)) {
                    // Each seed gets its own node; if there's only one seed, it should be your root.
                    urlQueue.offer(new UrlDepthPair(seed, 0, root));
                }
            }

            // Instead of looping while the queue is non-empty, poll with timeout.
            while (true) {
                try {
                    // Poll with a timeout so we wait for new tasks if they are being added.
                    UrlDepthPair pair = urlQueue.poll(1, TimeUnit.SECONDS);
                    if (pair != null) {
                        threadPool.submit(new CrawlerWorker(pair, indexer, parser, urlQueue, visited, maxHops));
                    } else {
                        // No new task was available for 1 second.
                        // Check if there are any active tasks running.
                        if (((ThreadPoolExecutor) threadPool).getActiveCount() == 0 && urlQueue.isEmpty()) {
                            break; // No active tasks and no waiting tasks: we're done.
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            threadPool.shutdown();
            try {
                threadPool.awaitTermination(5, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public Indexer getIndexer() {
            return indexer;
        }

        // A helper method to print the link tree for debugging.
        public void printLinkTree() {
            printTree(root, 0);
        }

        private void printTree(LinkNode node, int indent) {
            System.out.println(" ".repeat(indent) + node.getUrl());
            for (LinkNode child : node.getChildren()) {
                printTree(child, indent + 2);
            }
        }
    }