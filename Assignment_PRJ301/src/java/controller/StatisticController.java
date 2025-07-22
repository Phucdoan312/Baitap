/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import model.StatisticDAO;
import model.UserDTO;

/**
 *
 * @author admin
 */
@WebServlet(name = "StatisticController", urlPatterns = {"/StatisticController"})
public class StatisticController extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        UserDTO currentUser = (UserDTO) session.getAttribute("USER");

                if (currentUser == null || !"ROLE_ADMIN".equals(currentUser.getRole())) {
                    response.sendRedirect("login.jsp");
                    return;
                }
        try {
            StatisticDAO dao = new StatisticDAO();
            int totalUsers = dao.countUsers();
            int totalServices = dao.countServices();
            int totalBookings = dao.countBookings();
            Map<String, Integer> bookingsByStatus = dao.countBookingsByStatus();
            Map<String, Integer> topServices = dao.topServices(3);

            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalServices", totalServices);
            request.setAttribute("totalBookings", totalBookings);
            request.setAttribute("bookingsByStatus", bookingsByStatus);
            request.setAttribute("topServices", topServices);

            request.getRequestDispatcher("statistic.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("ERROR", "Lỗi khi thống kê: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
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
        processRequest(request, response);
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
