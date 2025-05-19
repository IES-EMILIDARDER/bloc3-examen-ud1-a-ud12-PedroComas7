package examen;

import java.util.*;
import java.util.stream.Collectors;

public class Main1 {
    
    public static void main(String[] args) {
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle("1234-HJK", "Toyota", "Corolla", 2010, 12000),
                new Vehicle("5678-KLM", "Volkswagen", "Golf", 2018, 18000),
                new Vehicle("9012-NPR", "Seat", "Ibiza", 2015, 10000),
                new Vehicle("3456-STU", "Tesla", "Model 3", 2022, 40000),
                new Vehicle("7890-VWX", "Renault", "Clio", 2012, 9000)
        );
        
        List<Vehicle> vehiclesO = vehicles.stream()
                .distinct()
                .filter(n-> n.getAny() > 2014)
                .sorted((m1, m2) -> m1.getMarca().
                compareTo(m2.getMarca())).
                collect(Collectors.toList());
        
        for(Vehicle v : vehiclesO){
            System.out.println(v.getMarca());
        }
    }
}

