# Web Indexer Crawler

A multi-threaded web crawler that recursively follows links up to a user-defined hop limit. It builds an in-memory index of pages and visualizes the discovered link hierarchy as a tree for debugging purposes.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture and Design](#architecture-and-design)
- [Installation](#installation)
- [UML Diagrams](#uml-diagrams)
- [Usage](#usage)
- [Future Enhancements](#future-enhancements)

## Overview

This project is designed to crawl web pages recursively. Instead of limiting the total number of links, the crawler limits the depth (or hops) it will follow from the seed URL. As it crawls, it builds a visual tree structure that maps the relationship between pages.

## Features

- **Recursive Crawling:** Follows links from a seed URL up to a specified hop limit.
- **Multi-threading:** Uses Java's `ExecutorService` for concurrent crawling.
- **Link Tree Visualization:** Constructs a tree (using `LinkNode` objects) that represents the hierarchy of discovered links.
- **Keyword Indexing:** Parses each page for keywords and builds an index mapping keywords to URLs.
- **Thread-Safe Design:** Maintains a thread-safe queue for URLs and a visited set to prevent duplicate processing.

## Architecture and Design

The system consists of several key components:

- **CrawlerManager:**  
  Manages the overall crawling process including the URL queue, thread pool, and hop count limit.

- **CrawlerWorker:**  
  Processes individual URLs by fetching HTML content, parsing for links and keywords, and updating both the index and link tree.

- **HTMLParser:**  
  Parses HTML pages to extract hyperlinks and keywords.

- **Indexer:**  
  Maintains an index mapping keywords to the URLs where they appear.

- **LinkNode:**  
  Represents a node in the link tree for debugging and visualization purposes.

- **UrlDepthPair:**  
  Combines a URL with its current depth (hop count) and a reference to the corresponding `LinkNode` in the tree.

## Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/web-indexer.git
   cd web-indexer

  Build the Project with Maven:
  mvn clean install
  Open the Project in IntelliJ IDEA:

  Launch IntelliJ IDEA.
  
  Select File > Open and choose the web-indexer folder.
  
  Allow IntelliJ to import the Maven project and download dependencies.

  ## Usage 

  **Run the Project:**

  From the command line, you can run:
  
  mvn exec:java -Dexec.mainClass="com.smugalpaca.webindexer.Main"
  Alternatively, run the Main class directly from IntelliJ.

  **Input Parameters:**
  
  When prompted, enter the maximum number of link hops (e.g., 4).
  Then, input the seed URL (e.g., https://en.wikipedia.org/wiki/Motocross).
  
  **Crawling Process:**

  The crawler will:

  Process the seed URL.
  Recursively follow links until the specified hop limit is reached.
  Build and display the link tree in the console for debugging purposes.

  ## UML Diagram

  ![Screenshot 2025-03-18 231535](https://github.com/user-attachments/assets/6336b46b-cd6f-463b-a04c-9e99bb865300)

  ## Future Enhancements

  **Persistent Storage:** Integrate a database to store indexed data.
  
  **Web Interface:** Develop a web-based interface for real-time visualization.
  
  **Enhanced Parsing:** Improve keyword extraction and filtering.
  
  **Improved Logging:** Add more detailed logging for performance analysis.
  
  
