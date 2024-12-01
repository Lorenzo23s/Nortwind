package com.northwind;
import com.northwind.ConexionBD;
import com.northwind.InsertarDatos;
import com.northwind.JSON;
import com.northwind.Consultas;

import java.sql.Connection;
public class Main {
    public static void main (String[] args) {
        System.out.println("Probando conexión a la base de datos...");
        try (Connection connection = ConexionBD.getConnection()) {
            if (connection != null) {
                System.out.println("Conexión establecida correctamente.");
            } else {
                System.out.println("No se pudo establecer la conexión.");
                return; // Finalizar si no hay conexión
            }

            // Paso 2: Insertar productos desde JSON
            JSON.addProductsFromJson();

            // Paso 3: Insertar empleados y pedidos
            InsertarDatos.insertEmployeesAndOrders();

            // Paso 4: Ejecutar consultas
            Consultas.executeQueries();

            System.out.println("Finalizando el programa.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public class TestConnection {
        public static void main(String[] args) {
            Connection connection = ConexionBD.getConnection();
            if (connection != null) {
                System.out.println("La conexión se estableció correctamente.");
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        }
    }
}