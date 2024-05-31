# JavaRSSReader

JavaRSSReader is a Java-based command-line application that allows users to read and manage RSS feed URLs conveniently.

## Features

- **Show Updates**: View the latest updates from subscribed RSS feeds.
- **Add URL**: Add a new RSS feed URL to the list of subscriptions.
- **Remove URL**: Remove an existing RSS feed URL from the list of subscriptions.

## Prerequisites

- Java Development Kit (JDK) 8 or higher

## Libraries Used

- **Jsoup (version 1.17.2)**: Jsoup is a Java library for working with real-world HTML. It provides a convenient API for extracting and manipulating data from HTML documents. Jsoup is used in this project to parse HTML pages and extract information such as page titles and RSS feed URLs.

## Getting Started

### Running the Application

1. **Clone the repository:**

    ```sh
    git clone https://github.com/ykazemim/JavaRSSReader.git
    cd JavaRSSReader/src
    ```

2. **Compile and run the application:**

    ```sh
    javac Main.java
    java Main
    ```

## Usage

- **Show Updates**: Choose option [1] to display the latest updates from subscribed RSS feeds.
- **Add URL**: Choose option [2] to add a new RSS feed URL to your subscriptions. You will be prompted to enter the URL.
- **Remove URL**: Choose option [3] to remove an existing RSS feed URL from your subscriptions. You will be prompted to enter the index of the URL to remove.
- **Exit**: Choose option [4] to exit the application.

## Assignment

This project was originally created as an assignment for the Advanced Programming course at Amirkabir University of Technology (AUT).
