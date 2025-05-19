package examen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @author Pedro
 */
public class GestorVehicle {
    final String MYSQL_CON = "c:\\temp\\mysql.con";
    GestorBBDD gestorBBDD = new GestorBBDD(MYSQL_CON);
    
    Set<Vehicle> vehicles = new HashSet<>();
    
    //CARREGA VEHICLES
    public void carregaVehicles(String path) throws SQLException, IOException {
        carregaVehiclesBBDD(this.vehicles);
        carregaVehiclesCSV(this.vehicles, path);

        System.out.println(this.vehicles);
    }

    private void carregaVehiclesBBDD(Set<Vehicle> vehicles) throws SQLException, IOException {
        String sql = "SELECT matricula, marca, model, any, preu FROM vehicles";

        try (Connection conn = gestorBBDD.getConnectionFromFile(); ResultSet resultSet = gestorBBDD.executaQuerySQL(conn, sql)) {

            while (resultSet.next()) {
                afegeixVehicle(vehicles, new Vehicle(resultSet.getString("matricula"),
                        resultSet.getString("marca"),
                        resultSet.getString("model"),
                        resultSet.getInt("any"),
                        resultSet.getDouble("preu"))
                );
            }

        } catch (SQLException e) {
            System.err.println("Error carregant vehicles BBDD: " + e.getMessage());
        }
    }

    private void carregaVehiclesCSV(Set<Vehicle> vehicles, String path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                if (!linia.startsWith("#") && !linia.isBlank()) {
                    String[] parts = linia.split(",");

                    afegeixVehicle(vehicles, new Vehicle(parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim()),
                            Integer.parseInt(parts[4].trim())));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error carregant vehicles CSV: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general carregant vehicles CSV: " + e.getMessage());
        }
    }

    public void afegeixVehicle(Set<Vehicle> vehicles, Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    private Vehicle cercaVehicle(Vehicle v) throws NoSuchElementException {
        for (Vehicle vehicle : this.vehicles) {
            if (v.equals(vehicle)) {
                return vehicle;
            }
        }

        throw new NoSuchElementException("Vehicle no trobat a la llista.");
    }
    
    //DESCÀRREGA VEHICLES
    public void desaVehicles(String path) throws SQLException, IOException {
        desaVehiclesBBDD(this.vehicles);
        desaVehiclesCSV(this.vehicles, path);
    }

    private void desaVehiclesBBDD(Set<Vehicle> vehicles) throws SQLException, IOException {
        try (Connection conn = gestorBBDD.getConnectionFromFile()) {
            conn.setAutoCommit(true);

            for (Vehicle v : vehicles) {
                try {
                    gestorBBDD.executaSQL(conn, "INSERT INTO vehicles (matricula, marca, model, any, preu) VALUES (?,?,?,?,?)",
                            "4321-JKL", "Ford", "Focus", 2019, 17000);
                    gestorBBDD.executaSQL(conn, "INSERT INTO vehicles (matricula, marca, model, any, preu) VALUES (?,?,?,?,?)",
                            "8765-MNO", "Hyundai", "Ioniq 5", 2023, 42000);
                    gestorBBDD.executaSQL(conn, "INSERT INTO vehicles (matricula, marca, model, any, preu) VALUES (?,?,?,?,?)",
                            "2109-PQR", "Peugeot", "308", 2016, 14000);
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23000") && e.getErrorCode() == 1062) // Error per PK, modificar
                    {
                        gestorBBDD.executaSQL(conn, "UPDATE vehicles SET marca = ?, model = ?, any = ?, preu = ? WHERE matricula = ?",
                                v.getMarca(), v.getModel(), v.getAny(), v.getPreu(), v.getMatricula());
                    } else {
                        throw e; // Re-llan�a si no �s error de PK
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error descarregant vehicles BBDD: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("");
        }
    }

    private void desaVehiclesCSV(Set<Vehicle> vehicles, String path) {
        try (BufferedWriter br = Files.newBufferedWriter(Paths.get(path))) {
            for (Vehicle v : vehicles) {
                br.write(v.getMatricula() + "," + v.getMarca() + "," + v.getModel() + "," + v.getAny() + "," + v.getPreu());
                br.newLine();
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error descarregant clients CSV: " + e.getMessage());
        }
    }
    
    // MODIFICACIONS
    public void modifica() {

        vehicles.forEach(vehicle -> {
            vehicle.setModel(vehicle.getModel().toUpperCase());
            vehicle.setMarca(vehicle.getMarca().toUpperCase());
        });

    }


}
