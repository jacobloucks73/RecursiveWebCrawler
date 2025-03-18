package com.smugalpaca.webindexer.Index;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Indexer {
    // A thread-safe map to store keywords and associated URLs.
    private final Map<String, Set<String>> indexMap = new ConcurrentHashMap<>();

    public void index(String url, List<String> keywords) {
        for (String keyword : keywords) {
            indexMap.computeIfAbsent(keyword.toLowerCase(), k -> ConcurrentHashMap.newKeySet()).add(url);
        }
    }

    public Set<String> search(String keyword) {
        return indexMap.getOrDefault(keyword.toLowerCase(), Collections.emptySet());
    }
}