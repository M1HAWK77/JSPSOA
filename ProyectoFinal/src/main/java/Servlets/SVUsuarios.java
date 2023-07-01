/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Controller.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ariel
 */
@WebServlet(name = "SVUsuarios", urlPatterns = {"/SVUsuarios"})
public class SVUsuarios extends HttpServlet {

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
        //Crear cliente http 
        try {

            URL url = new URL("http://localhost:8080/ServiciosProyectoFinal/api/getInfo");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseStrB = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseStrB.append(line);
            }

            JSONParser parse = new JSONParser();
            JSONArray jsonArray;
            List<User> data = new ArrayList<>();

            jsonArray = (JSONArray) parse.parse(responseStrB.toString());
            System.out.println(jsonArray);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String dni = jsonObject.get("dni").toString();
                String name = jsonObject.get("name").toString();
                String lastname = jsonObject.get("lastName").toString();
                String ageStr = jsonObject.get("age").toString();
                int age = Integer.parseInt(ageStr);
                User userDoGet = new User(dni, name, lastname, age);
                data.add(userDoGet);

            }
            //Clase para crear la sesion de usuario que se creo en el momento de ejecucion
            /*HttpSession mysession = request.getSession();

            //asigno como atributo de la sesion de la persona, funciona agregando un boton para que se envie a otra pagina
            //en este caso por funciones de los servlets funcionan mandando la peticion a traves de eventos como dar click en un btn
            mysession.setAttribute("exito", data);
            response.sendRedirect("principal.jsp");*/

            //----------------------------------------------------
            /*request.setAttribute("userList", data);

            // Obtener el contenido HTML de tu JSP
            String jspContent = getJSPContent("principal.jsp", request);

            // Enviar la respuesta con el contenido HTML
            response.setContentType("text/html");
            response.getWriter().write(jspContent);*/
            //---------------------------------------servlet
        } catch (ParseException ex) {
            //Logger.getLogger(SVUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /* private String getJSPContent(String jspPath, HttpServletRequest request)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
        StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        HttpServletResponse responseWrapper = new HttpServletResponseWrapper(null) {
            @Override
            public PrintWriter getWriter() {
                return printWriter;
            }
        };
        dispatcher.include(request, responseWrapper);
        return stringWriter.toString();
    }*/
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

        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String edad = request.getParameter("edad");
        //saber como llegaron al servlet
        //puedo probar a lado del servidor osea en este archivo xd
        System.out.println("cedula: " + cedula);
        System.out.println("nombre: " + nombre);
        System.out.println("apellido: " + apellido);
        System.out.println("edad: " + edad);

        // Crear el cliente HTTP
        HttpClient httpClient = HttpClient.newHttpClient();

        // Construir la URL del servicio REST
        String url = "http://localhost:8080/ServiciosProyectoFinal/api/getInfo";

        // Crear el cuerpo de la solicitud POST
        String requestBody = "dni=" + cedula + "&name=" + nombre + "&lastname=" + apellido + "&age=" + edad;

        // Crear la solicitud POST
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Ejecutar la solicitud y obtener la respuesta
        HttpResponse<String> respuesta;
        try {
            respuesta = httpClient.send(solicitud, HttpResponse.BodyHandlers.ofString());
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
        } catch (InterruptedException ex) {
            Logger.getLogger(SVAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Leer los datos enviados en el cuerpo de la solicitud PUT
        StringBuilder requestData = new StringBuilder();
        String line;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            requestData.append(line);
        }

        // Procesar los datos recibidos
        String dataPut = requestData.toString();
        // Aquí puedes realizar el procesamiento adicional según tus necesidades
        System.out.println(dataPut);
         String[] valuesResponse= dataPut.split("[=&]");
         String cedula= valuesResponse[1];
         String nombre= valuesResponse[3];
         String apellido= valuesResponse[5];
         String edad= valuesResponse[7];
        // Enviar la respuesta
        resp.getWriter().println("Datos recibidos correctamente");

        //saber como llegaron al servlet
        //puedo probar a lado del servidor osea en este archivo xd
        System.out.println("cedula: " + cedula);
        System.out.println("nombre: " + nombre);
        System.out.println("apellido: " + apellido);
        System.out.println("edad: " + edad);

        // Crear el cliente HTTP
        HttpClient httpClient = HttpClient.newHttpClient();

        // Construir la URL del servicio REST
        String url = "http://localhost:8080/ServiciosProyectoFinal/api/getInfo";

        // Crear el cuerpo de la solicitud POST
        String requestBody = "dni=" + cedula + "&name=" + nombre + "&lastname=" + apellido + "&age=" + edad;

        // Crear la solicitud POST
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Ejecutar la solicitud y obtener la respuesta
        HttpResponse<String> respuesta;
        try {
            respuesta = httpClient.send(solicitud, HttpResponse.BodyHandlers.ofString());
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
            
        } catch (InterruptedException ex) {
            Logger.getLogger(SVAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
          // Leer los datos enviados en el cuerpo de la solicitud PUT
        StringBuilder requestData = new StringBuilder();
        String line;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            requestData.append(line);
        }

        // Procesar los datos recibidos
        String dataDelete = requestData.toString();
        System.out.println(dataDelete);
        String[] valuesResponse= dataDelete.split("[=]");
        String cedula= valuesResponse[1];
        System.out.println(cedula);
        
        
            // Crear el cliente HTTP
        HttpClient httpClient = HttpClient.newHttpClient();

        // Construir la URL del servicio REST
        String url = "http://localhost:8080/ServiciosProyectoFinal/api/getInfo";

        // Crear el cuerpo de la solicitud POST
        String requestBody = "dni=" + cedula;

        // Crear la solicitud POST
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Ejecutar la solicitud y obtener la respuesta
        HttpResponse<String> respuesta;
        try {
            respuesta = httpClient.send(solicitud, HttpResponse.BodyHandlers.ofString());
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
