import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Options {
    private static final int MAX_WEBSITES = 100;
    private static final Website[] websites = new Website[MAX_WEBSITES];
    private static int free_index = 0;

    /**
     * Reads the whole data file
     */
    public Options(File file) {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null && free_index < 100) {
                String[] fields = line.split(";");
                websites[free_index] = new Website(fields[0], fields[1], fields[2]);
                free_index++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error: Failed to read the file. Exiting...");
            System.exit(-1);
        }
    }

    public static void showUpdates(Scanner scanner) {
        if (free_index == 0) {
            System.out.println("There is no added RSS feeds.");
            System.out.println();
            return;
        }
        System.out.println("[0]" + " All websites");
        for (int i = 0; i < free_index; i++) {
            System.out.println("[" + (i + 1) + "]" + " " + websites[i].getPageTitle());
        }
        System.out.println("Enter a valid number to select (Enter -1 to return to menu) :");
        int selection;
        while (true) {
            try {
                selection = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input (Input not a number).");
                scanner.next();
                continue;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Unknown Error");
                continue;
            }
            if (selection >= free_index + 1 || selection < -1) {
                System.out.println("Invalid input (Number not in valid range).");
            } else break;
        }
        if (selection == -1) {
            System.out.println();
        } else if (selection == 0) {
            for (int i = 0; i < free_index; i++) {
                System.out.println("*** " + websites[i].getPageTitle() + " ***");
                Fetch.retrieveRssContent(websites[i].getRssUrl());
                System.out.println();
            }
        } else {
            System.out.println("*** " + websites[selection - 1].getPageTitle() + " ***");
            Fetch.retrieveRssContent(websites[selection - 1].getRssUrl());
            System.out.println();
        }
    }

    public static void addURL(Scanner scanner, File file) {

        if (free_index >= 100) {
            System.out.println("Error: too many RSS Feeds. Can't add a new one.");
            System.out.println();
            return;
        }
        System.out.println("Please enter website URL to add (Enter -1 to return to menu) :");
        String urlAddress = scanner.next();
        if (urlAddress.equals("-1")) {
            System.out.println();
            return;
        }
        //Checking if the URL is redundant
        for (int i = 0; i < free_index; i++) {
            if (websites[i].getUrlAddress().equals(urlAddress)) {
                System.out.println("Error: " + urlAddress + " already exists.");
                System.out.println();
                return;
            }
        }

        String htmlAddress;
        try {
            htmlAddress = Fetch.fetchPageSource(urlAddress);
        } catch (Exception e) {
            System.out.println("Error: Failed to fetch page HTML address");
            System.out.println();
            return;
        }

        String rssUrl;
        String pageTitle = Fetch.extractPageTitle(htmlAddress);
        try {
            rssUrl = Fetch.extractRssUrl(urlAddress);
        } catch (IOException e) {
            System.out.println("Error: Failed to fetch page RSS URL");
            System.out.println();
            return;
        }
        if (rssUrl.isBlank()) {
            System.out.println("Error: Empty page RSS URL");
            System.out.println();
            return;
        }
        if (pageTitle.isBlank()) {
            System.out.println("Error: Empty page title. It will be set to NULL.");
            System.out.println();
            pageTitle = "NULL";
        }

        websites[free_index] = new Website(pageTitle, urlAddress, rssUrl);
        System.out.println("Added " + websites[free_index].getUrlAddress() + " successfully.");
        System.out.println();
        free_index++;

    }

    public static void removeURL(Scanner scanner) {
        if (free_index == 0) {
            System.out.println("There is no added RSS feeds.");
            System.out.println();
            return;
        }
        System.out.println("Please enter website URL to remove (Enter -1 to return to menu) :");
        String urlAddress = scanner.next();
        if (urlAddress.equals("-1")) {
            System.out.println();
            return;
        }
        //Checking if the URL exists
        for (int i = 0; i < free_index; i++) {
            if (websites[i].getUrlAddress().equals(urlAddress)) {
                removeOneIndex(i);
                return;
            }
        }
        System.out.println("Couldn't find " + urlAddress);
        System.out.println();
    }

    public static void prepareToExit(File file) {
        //Writing into the file
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            System.out.println("Error: Failed to write into the file. Changes will NOT be saved.");
            return;
        }
        for (int i = 0; i < free_index; i++) {
            try {
                bufferedWriter.write(websites[i].getPageTitle() + ";" + websites[i].getUrlAddress() + ";" + websites[i].getRssUrl() + "\n");
            } catch (IOException e) {
                System.out.println("Error: Failed to write into the file" + " (URL: " + websites[i].getUrlAddress() + " )");
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error: Failed to close the buffer");
        }
    }

    private static void removeOneIndex(int indexToDelete) {
        Website tempWebsite = websites[indexToDelete];
        for (int i = indexToDelete + 1; i < free_index; i++) {
            websites[i - 1] = websites[i];
        }
        System.out.println("Removed " + websites[indexToDelete].getUrlAddress() + " successfully.");
        System.out.println();
        websites[--free_index] = null;
    }
}
