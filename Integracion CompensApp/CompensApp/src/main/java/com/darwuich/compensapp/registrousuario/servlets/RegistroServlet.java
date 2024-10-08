
package com.darwuich.compensapp.registrousuario.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/proyecto_generales";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreUsuario = request.getParameter("nombreUsuario");
        String apellidosUsuario = request.getParameter("apellidosUsuario");
        String tipoDocumento = request.getParameter("tipoDocumento");
        String numeroDocumento = request.getParameter("numeroDocumento");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("password");
        String confirmarContrasena = request.getParameter("confirmPassword");

        if (!contrasena.equals(confirmarContrasena)) {
            response.getWriter().println("Las contraseñas no coinciden.");
            return;
        }

        try {
            // Convertir la fecha de nacimiento a java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimientoUtil = sdf.parse(fechaNacimientoStr);
            java.sql.Date fechaNacimiento = new java.sql.Date(fechaNacimientoUtil.getTime());

            // Insertar el registro en la base de datos
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO registro (nombreUsuario, apellidosUsuario, tipoDocumento, numeroDocumento, fechaNacimiento, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombreUsuario);
                stmt.setString(2, apellidosUsuario);
                stmt.setString(3, tipoDocumento);
                stmt.setString(4, numeroDocumento);
                stmt.setDate(5, fechaNacimiento);
                stmt.setString(6, email);
                stmt.setString(7, contrasena);
                stmt.executeUpdate();
            }

            // Redirigir a la página de agendar cita
            response.sendRedirect("Agendar.html");

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(RegistroServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().println("Error en el registro: " + ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
