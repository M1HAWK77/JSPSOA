<%-- 
    Document   : logout
    Created on : 28 jun 2023, 16:58:45
    Author     : ariel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <%
            // Invalidar la sesión actual
            session.invalidate();
            // Redirigir al usuario a la página de inicio
            response.sendRedirect("index.jsp");
        %>