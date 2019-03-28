package main.java;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.io.*;

public class Main {

    public static final int MaxNumberOfLines = 9375000;
    public static final String endpoint = "http://localhost:8080/table/taxi2newYork/load/csv";
    public static final long maxStringSize = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        File[] listOfFiles = new File("files/todo").listFiles();
        listOfFiles = Arrays.stream(listOfFiles).filter(f -> f.getName().contains(".csv")).toArray(File[]::new);
        Arrays.stream(listOfFiles).forEach(f -> parseFile(f));
    }

    public static void parseFile(File file) {
        try {
            System.out.println("unFile");
            StringBuffer stringBuff = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                read(stringBuff, reader);
                String allLines = new String(stringBuff);
                System.out.println("unDataset");
                doRequest(allLines);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }

    }

    public static void read(StringBuffer buff, BufferedReader reader) throws IOException {
        long i = 0;
        String line = new String();
        while (((line = reader.readLine()) != null) && i < maxStringSize) {
            buff.append(line+"\n");
            i += line.length();
        }
    }

    public static void doRequest(String body) {
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();


        HttpRequest loadRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "text/csv")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        try {
            var loadResponse = client.send(loadRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(loadResponse.statusCode() + " " + loadResponse.body());
        } catch (Exception exp) {
            System.out.println("RESEXP " + exp);
        }
    }

}
