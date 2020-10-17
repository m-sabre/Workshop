package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class taskManager {

    public static void main(String[] args) {
        String[][] tasks = readList();
        Scanner scan = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Please select an option");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");

        while (scan.hasNextLine()){
            switch (scan.nextLine()) {
                case "add":
                    addToList(tasks);
                    break;
                case "remove":
                    removeFromList(tasks);
                    break;
                case "list":
                    showList(tasks);
                    break;
                case "exit":
                    exit(tasks);
                    break;
                default:
                    System.out.println("Wrong command, please try again.");
                    break;
            }
        }

        System.out.println(ConsoleColors.BLUE + "Please select an option");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");

    }

    public static String[][] readList() {

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


    public static void addToList(String[][] tasks) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Specify Task Name");
        String taskName = scan.nextLine();
        System.out.println("Specify task due date");
        String taskDate = scan.nextLine();
        System.out.println("Is this task important: true / false");
        String taskStatus = scan.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        String[] arr = {taskName, taskDate, taskStatus};
        tasks[tasks.length - 1] = arr;

    }

    public static void removeFromList(String[][] tasks) {
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

    public static void showList(String[][] tasks) {

        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + 1 + ".) ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j]);
            }
            System.out.println();

        }
    }

    public static void exit(String[][] tasks) {
        Path path = Paths.get("tasks.csv");
        String[] lines = new String[tasks.length];
        for (int i = 0; i<tasks.length; i++){
            lines[i]=String.join(",",tasks[i]);
        }
        try{
            Files.write(path,Arrays.asList(lines));
        } catch (IOException eee) {
            eee.printStackTrace();
        }

    }


}



