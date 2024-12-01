package com.northwind;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Consultas {
    public static void executeQueries() {
        System.out.println("Ejecutando consultas...");
        Connection connection = ConexionBD.getConnection();

        if (connection != null) {
            try {
                queryAllEmployees(connection);
                queryAllProducts(connection);
                queryAllOrders(connection);
                queryProductsUnder600(connection);
                queryFavoriteProducts(connection);
            } catch (Exception e) {
                System.out.println("Error al ejecutar consultas: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) connection.close();
                } catch (Exception closeEx) {
                    System.out.println("Error al cerrar la conexión: " + closeEx.getMessage());
                }
            }
        } else {
            System.out.println("No se pudo conectar a la base de datos.");
        }
    }

    private static void queryAllEmployees(Connection connection) {
        System.out.println("\n--- Empleados ---");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Empleados")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                String correo = resultSet.getString("correo");
                System.out.println("ID: " + id + ", Nombre: " + nombre + " " + apellidos + ", Correo: " + correo);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar empleados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void queryAllProducts(Connection connection) {
        System.out.println("\n--- Productos ---");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Productos")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Descripción: " + descripcion + ", Cantidad: " + cantidad + ", Precio: " + precio + "€");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void queryAllOrders(Connection connection) {
        System.out.println("\n--- Pedidos ---");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Pedidos")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idProducto = resultSet.getInt("id_producto");
                String descripcion = resultSet.getString("descripcion");
                double precioTotal = resultSet.getDouble("precio_total");
                System.out.println("ID: " + id + ", Producto ID: " + idProducto + ", Descripción: " + descripcion + ", Precio Total: " + precioTotal + "€");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void queryProductsUnder600(Connection connection) {
        System.out.println("\n--- Productos con precio menor a 600€ ---");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Productos WHERE precio < 600")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Precio: " + precio + "€");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar productos con precio menor a 600€: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void queryFavoriteProducts(Connection connection) {
        System.out.println("\n--- Productos favoritos (precio > 1000€) ---");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Productos WHERE precio > 1000")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Precio: " + precio + "€");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar productos favoritos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
