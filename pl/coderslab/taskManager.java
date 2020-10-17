package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

// generalnie do skończenia komunikacja pomiędzy modułami.


public class taskManager {
    static String[][] tasks;

    public static void main(String[] args) {
        String[][] tasks = readList();
        Scanner scan = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Please select an option");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");

        do {

            switch (scan.nextLine()) {
                case "add":
                    tasks = addToList(tasks);
                    break;
                case "remove":
                    removeFromList(tasks);
                    break;
                case "list":
                    showList(tasks);
                    break;
                case "exit":
                    exit(tasks);
                    System.out.println(ConsoleColors.RED + "Goodbye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong command, please try again.");
                    break;
            }
        } while (!scan.nextLine().equalsIgnoreCase("exit"));

        System.out.println(ConsoleColors.BLUE + "Please select an option");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }



    public static String[][] readList() { // IO exception?

        List<String[]> lines = new ArrayList<>();
        String[][] tempTab = new String[0][];
        try {
            File file = new File("tasks.csv");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                lines.add(scan.nextLine().split(","));
            }
            tempTab = lines.toArray(new String[0][]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tempTab;
    }


    public static String[][] addToList(String[][] tasks) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Specify Task Name");  // kontrola String?
        String taskName = scan.nextLine();

        System.out.println("Specify task due date"); // kontrola date?
        String taskDate = scan.nextLine();

        System.out.println("Is this task important: true / false"); // kontrola boolean!
        String taskStatus = scan.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        String[] arr = {taskName, taskDate, taskStatus};
        tasks[tasks.length - 1] = arr;

        return tasks;
    }

    public static void removeFromList(String[][] tasks) { //do uproszczenia - zawinięcia razem sprawdzanie danych.
        Scanner scan = new Scanner(System.in);
        System.out.println("Select task number to remove");
        String input = scan.nextLine();
        while (!NumberUtils.isParsable(input)) {
            System.out.println("Wrong input, try numbers");
        }
        while (parseInt(input) <= 0) {
            System.out.println("Wrong task number, try again");
        }
        try {
            if (parseInt(input) < tasks.length) {
                tasks = ArrayUtils.remove(tasks, parseInt(input));
            }
        } catch (ArrayIndexOutOfBoundsException ee) {
            System.out.println("Number out of table");

        }

    }

    public static void showList(String[][] tempTab) {

        for (int i = 0; i < tempTab.length; i++) {
            System.out.print(i + 1 + ".) ");
            for (int j = 0; j < tempTab[i].length; j++) {
                System.out.print(tempTab[i][j]);
            }
            System.out.println();

        }
    }

    public static void exit(String[][] tempTab) {
        Path path = Paths.get("tasks.csv");
        String[] lines = new String[tasks.length];
        for (int i = 0; i < tempTab.length; i++) {
            lines[i] = String.join(",", tempTab[i]);
        }
        try {
            Files.write(path, Arrays.asList(lines));
        } catch (IOException eee) {
            eee.printStackTrace();
        }

    }


}



