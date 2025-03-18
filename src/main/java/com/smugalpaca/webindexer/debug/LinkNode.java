package com.smugalpaca.webindexer.debug;

import java.util.ArrayList;
import java.util.List;

public class LinkNode {
    private final String url;
    private final List<LinkNode> children = new ArrayList<>();

    public LinkNode(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public List<LinkNode> getChildren() {
        return children;
    }

    public void addChild(LinkNode child) {
        children.add(child);
    }
}