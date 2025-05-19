package examen;

import java.io.IOException;
import java.sql.SQLException;

public class Main4 {

    public static void main(String[] args) throws SQLException, IOException {
        GestorVehicle gestor = new GestorVehicle();
        
        gestor.carregaVehicles("c:\\temp\\vehicles.csv");
    }
    
}