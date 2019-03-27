package main.java;

import java.util.Arrays;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.*;

public class Main {

    public static final int MaxNumberOfLines = 9375000;

    public static void main(String[] args) {
        File[] listOfFiles = new File("files/todo").listFiles();
        listOfFiles = Arrays.stream(listOfFiles).filter(f -> f.getName().contains(".csv")).toArray(File[]::new);


    }

    public static void parseFile(File file) {

    }

}
