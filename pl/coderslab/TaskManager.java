package pl.coderslab.pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] arrayOfTasks;

    public static void main(String args[]){

        arrayOfTasks = loadToTab(FILE_NAME);
        showOptions(OPTIONS);

        Scanner in = new Scanner(System.in);

        while(in.hasNextLine()){
            String choice = in.nextLine();

            switch(choice){
                case "exit":
                    saveToFile(FILE_NAME, arrayOfTasks);
                    System.out.println(ConsoleColors.RED +"Naura");
                    System.exit(0);
                    break;
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(arrayOfTasks, getNumber());
                    System.out.println("Wpis został usunięty.");
                    break;
                case "list":
                    showTasks(arrayOfTasks);
                    break;
                default:
                    System.out.println("Wybierz poprawną opcję.");
            }
            showOptions(OPTIONS);
        }

    }


    public static String[][] loadToTab(String fileName) {
        Path path = Paths.get(fileName);

        if(!Files.exists(path)){
            System.out.println("Plik nie istnieje.");
            System.exit(0);
        }

        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(path);
            tab = new String[strings.size()][strings.get(0).split(",").length];

            for(int i=0; i<strings.size(); i++){
                String[] split = strings.get(i).split(",");
                    for(int j=0; j<split.length; j++){
                        tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void showOptions(String[] tab){
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for(String choice : tab){
            System.out.println(choice);
        }
    }

    public static void showTasks(String[][] tab){
        for(int i=0; i<tab.length; i++){
            System.out.print(i + " : ");
                for (int j=0; j<tab[i].length; j++){
                    System.out.print(tab[i][j] + " ");
                }
            System.out.println();
        }
    }

    private static void addTask(){
        Scanner in = new Scanner(System.in);
            System.out.println("Podaj opis zadania.");
        String description = in.nextLine();
            System.out.println("Podaj datę do wykonania zadania");
        String toDoDate = in.nextLine();
            System.out.println("Czy to zadanie jest ważne? Wybierz - true/false");
        String isImportant = in.nextLine();

        arrayOfTasks =  Arrays.copyOf(arrayOfTasks, arrayOfTasks.length+1);
        arrayOfTasks[arrayOfTasks.length-1] = new String[3];
        arrayOfTasks[arrayOfTasks.length-1][0] = description;
        arrayOfTasks[arrayOfTasks.length-1][1] = toDoDate;
        arrayOfTasks[arrayOfTasks.length-1][2] = isImportant;
    }

    private static void removeTask(String[][] tab, int index) {
        try {
            if(index<tab.length){
                arrayOfTasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Element nie istnieje w tablicy");
        }
    }

    public static boolean isNumberGreaterAndEqualZero(String input){
        if(NumberUtils.isParsable(input)){
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static int getNumber(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz numer zadania do usunięcia.");

        String input = scanner.nextLine();
        while (!isNumberGreaterAndEqualZero(input)){
            System.out.println("Nieprawidłowy argument. Podaj argument większy lub równy 0");
            scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    public static void saveToFile(String fileName, String[][] tab) {
        Path path = Paths.get(fileName);

        String[] rows = new String[arrayOfTasks.length];
        for (int i=0; i<tab.length; i++) {
            rows[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(path, Arrays.asList(rows));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
