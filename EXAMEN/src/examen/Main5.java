package examen;

import java.io.IOException;
import java.sql.SQLException;

public class Main5 {
    
    public static void main(String[] args) throws SQLException, IOException {
        GestorVehicle gestor = new GestorVehicle();
        
        gestor.carregaVehicles("C:\\temp\\vehicles_output.csv");
        gestor.desaVehicles("C:\\temp\\vehicles_output.csv");
    }
}
