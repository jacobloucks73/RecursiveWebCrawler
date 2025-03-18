package com.smugalpaca.webindexer.debug;

public class UrlDepthPair {
    private final String url;
    private final int depth;
    private final LinkNode node;  // Reference to the node in the tree

    public UrlDepthPair(String url, int depth, LinkNode node) {
        this.url = url;
        this.depth = depth;
        this.node = node;
    }

    public String getUrl() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

    public LinkNode getNode() {
        return node;
    }
}