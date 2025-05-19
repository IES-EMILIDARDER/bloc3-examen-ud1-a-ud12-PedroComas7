package examen;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main3 {

    public static void main(String[] args) {
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle("1234-HJK", "Toyota", "Corolla", 2010, 12000),
                new Vehicle("5678-KLM", "Volkswagen", "Golf", 2018, 18000),
                new Vehicle("9012-NPR", "Seat", "Ibiza", 2015, 10000),
                new Vehicle("3456-STU", "Tesla", "Model 3", 2022, 40000),
                new Vehicle("7890-VWX", "Renault", "Clio", 2012, 9000)
        );
        
        BiFunction<Vehicle, Double, Vehicle> incrementaPreu2 = (v, f) -> new Vehicle(v.getMatricula(), 
                                                                                       v.getMarca(), 
                                                                                       v.getModel(), 
                                                                                       v.getAny(), 
                                                                                       v.getPreu() * (1 + f));

        List<Vehicle> vehicles2 = vehicles.stream()
                .map(v -> incrementaPreu2.apply(v, 0.1))
                .toList();

        vehicles2.forEach(System.out::println);

        
    }
}

