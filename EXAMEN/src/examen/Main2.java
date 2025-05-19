package examen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main2 {

    public static void main(String[] args) {
        try (Stream<String> lineas = Files.lines(Paths.get("c:\\temp\\vehicles.csv"))) {
            List<Vehicle> vehicles = lineas.filter(n -> (!n.isBlank()) && (!n.startsWith("#")) )
                                           .map(n -> n.split(","))
                                           .sorted((p1, p2) -> p1[1].compareTo(p2[1]))
                                           .map(p -> new Vehicle(p[0].trim(),
                                                                 p[1].trim(),
                                                                 p[2].trim(),
                                                                 Integer.parseInt(p[3].trim()),
                                                                 Integer.parseInt(p[4].trim())
                                                                )
                                                )
                                            .collect(Collectors.toList());
            long Major = vehicles.stream()
                                 .filter(v -> v.getPreu() > 15000)
                                 .count();
            double max = vehicles.stream().mapToDouble(n -> n.getPreu()).max().orElse(0);
            double min = vehicles.stream().mapToDouble(n -> n.getPreu()).min().orElse(0);
            double avg = vehicles.stream().mapToDouble(n -> n.getPreu()).average().orElse(0);
            
            System.out.println("Vehicles amb preu major de 1500 EUR: " + Major);
            System.out.println("Preu mitjà: " + avg + " EUR");
            System.out.println("Més car: " + max + " EUR");
            System.out.println("Més barat: " + min + " EUR");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

