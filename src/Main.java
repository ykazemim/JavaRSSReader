import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final File file = new File("data.txt");


    public static void main(String[] args) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error: Failed to create the file. Exiting...");
            System.exit(-1);
        }
        new Options(file);
        System.out.println("Welcome to RSS Reader!");
        while (displayStart() != -1) ;
    }

    private static int displayStart() {
        System.out.println("Type a valid number for your desired action: ");
        System.out.println("[1] Show updates");
        System.out.println("[2] Add URL");
        System.out.println("[3] Remove URL");
        System.out.println("[4] Exit");
        int userChoice;
        while (true) {
            try {
                userChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input (Input not a integer).");
                scanner.next();
                continue;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Unknown Error");
                continue;
            }
            if (userChoice > 4 || userChoice < 1) {
                System.out.println("Invalid input (Number not in range 1-4).");
            } else break;
        }
        switch (userChoice) {
            case 1:
                Options.showUpdates(scanner);
                break;
            case 2:
                Options.addURL(scanner, file);
                break;
            case 3:
                Options.removeURL(scanner);
                break;
            case 4:
                Options.prepareToExit(file);
                return -1;
        }
        return 0;
    }
}
