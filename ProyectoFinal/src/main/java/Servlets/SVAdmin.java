/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ariel
 */
@WebServlet(name = "SVAdmin", urlPatterns = {"/SVAdmin"})
public class SVAdmin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Crear el cliente HTTP
        HttpClient httpClient = HttpClient.newHttpClient();

        // Construir la URL del servicio REST
        String url = "http://localhost:8080/ServiciosProyectoFinal/api/getInfo/login";

        // Crear el cuerpo de la solicitud POST
        String requestBody = "username=" + username + "&password=" + password;

        // Crear la solicitud POST
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(BodyPublishers.ofString(requestBody))
                .build();

        // Ejecutar la solicitud y obtener la respuesta
        HttpResponse<String> respuesta;
        try {
            respuesta = httpClient.send(solicitud, BodyHandlers.ofString());
            // Obtener el código de respuesta HTTP
            int statusCode = respuesta.statusCode();

            // Obtener las cabeceras de la respuesta HTTP
            HttpHeaders headers = respuesta.headers();

            // Obtener el cuerpo de la respuesta HTTP
            String responseBody = respuesta.body();

            // Realizar el procesamiento adicional según tus necesidades
            System.out.println("Código de respuesta: " + statusCode);
            System.out.println("Cabeceras de respuesta: " + headers);
            System.out.println("Cuerpo de respuesta: " + responseBody);
            //JSONObject jsonObject=  (JSONObject) responseBody;
            //String isValid= jsonObject.get("value").toString();
            String[] valuesResponse = responseBody.split("[:}]");
            String isValid = valuesResponse[1];
            System.out.println(isValid);
            if (isValid.equals("true")) {

                HttpSession mysession = request.getSession();

                //asigno como atributo de la sesion de la persona, funciona agregando un boton para que se envie a otra pagina
                //en este caso por funciones de los servlets funcionan mandando la peticion a traves de eventos como dar click en un btn
                mysession.setAttribute("userSession", username);
                //response.sendRedirect("principal.jsp");

                response.sendRedirect("principal.jsp");
            } else if (isValid.equals("false")) {
                response.sendRedirect("index.jsp");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SVAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
