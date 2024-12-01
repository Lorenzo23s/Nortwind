package com.northwind;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InsertarDatos {
    public static void insertEmployeesAndOrders() {
        System.out.println("Insertando empleados y pedidos...");
        Connection connection = ConexionBD.getConnection();

        if (connection != null) {
            try {
                // Verificar y evitar duplicados antes de insertar empleados
                if (!emailExists(connection, "juan.perez@example.com")) {
                    insertEmployee(connection, "Juan", "Pérez", "juan.perez@example.com");
                } else {
                    System.out.println("El correo 'juan.perez@example.com' ya existe. Saltando inserción.");
                }

                if (!emailExists(connection, "ana.lopez@example.com")) {
                    insertEmployee(connection, "Ana", "López", "ana.lopez@example.com");
                } else {
                    System.out.println("El correo 'ana.lopez@example.com' ya existe. Saltando inserción.");
                }

                // Insertar pedidos (sin verificar duplicados aquí)
                insertOrder(connection, 1, "Pedido 1", 150.00);
                insertOrder(connection, 2, "Pedido 2", 300.00);

                System.out.println("Empleados y pedidos insertados correctamente.");
            } catch (Exception e) {
                System.out.println("Error al insertar empleados y pedidos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception closeEx) {
                    System.out.println("Error al cerrar la conexión: " + closeEx.getMessage());
                }
            }
        } else {
            System.out.println("No se pudo conectar a la base de datos.");
        }
    }

    private static boolean emailExists(Connection connection, String email) throws Exception {
        String query = "SELECT COUNT(*) FROM Empleados WHERE correo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }

    private static void insertEmployee(Connection connection, String nombre, String apellidos, String correo) throws Exception {
        String query = "INSERT INTO Empleados (nombre, apellidos, correo) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setString(3, correo);
            preparedStatement.executeUpdate();
        }
    }

    private static void insertOrder(Connection connection, int idProducto, String descripcion, double precioTotal) throws Exception {
        String query = "INSERT INTO Pedidos (id_producto, descripcion, precio_total) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idProducto);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setDouble(3, precioTotal);
            preparedStatement.executeUpdate();
        }
    }
}
