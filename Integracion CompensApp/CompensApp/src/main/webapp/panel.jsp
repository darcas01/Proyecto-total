<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Panel de Usuario</title>
</head>
<body>
    <%
        String usuario = (String) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("index.html");
            return;
        }
    %>
    <h1>Hola, <%= usuario %>!</h1>
    <p>Bienvenido a tu panel de usuario.</p>

    <nav>
        <ul>
            <li><a href="registro.jsp">Registro de Usuario</a></li>
            <li><a href="agendarCita.jsp">Agendar Cita</a></li>
            <li><a href="modificarCita.jsp">Modificar Cita</a></li>
            <li><a href="cancelarCita.jsp">Cancelar Cita</a></li>
            <li><a href="historialCitas.jsp">Historial de Citas</a></li>
            <li><a href="logout.jsp">Cerrar Sesión</a></li>
        </ul>
    </nav>
</body>
</html>
