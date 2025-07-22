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
import java.util.List;
import model.ServiceDAO;
import model.ServiceDTO;
import model.UserDTO;

/**
 *
 * @author ad
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String url = "login.jsp";

        try {
            if (action == null || action.isEmpty()) {
                url = "login.jsp";
            } else if (action.equals("login")
                    || action.equals("logout")
                    || action.equals("register")
                    || action.equals("listUsers")
                    || action.equals("editUser")
                    || action.equals("updateUser")) {
                url = "UserController";
            } else if ("viewServices".equals(action)) {
                url = "ServiceController";
            } else if ("backToHome".equals(action)) {
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("USER") != null) {
                    UserDTO user = (UserDTO) session.getAttribute("USER");
                    if ("ROLE_USER".equals(user.getRole())) {
                        url = "home.jsp";
                    } else if ("ROLE_ADMIN".equals(user.getRole())) {
                        url = "dashboard.jsp";
                    }
                }
            } else if (action.equals("form")) {
                List<ServiceDTO> services = new ServiceDAO().getAllServices();
                request.setAttribute("SERVICE_LIST", services);
                url = "form.jsp";
            } else if (action.equals("createBooking")
                    || action.equals("listBooking")
                    || action.equals("editBooking")
                    || action.equals("updateBooking")
                    || action.equals("deleteBooking")
                    || action.equals("updateStatus")) {
                url = "BookingController";
            } else if ("viewStats".equals(action)) {
                url = "StatisticController";
            } else if (action.equals("generateQR")) {
                url = "QRCodeController";
            } else if (action.equals("sendMail")) {
                url = "MailController";
            }else if (action.equals("chat")
                    || action.equals("sendMessage")
                    || action.equals("deleteChat")) {
                url = "ChatbotController";
            } else if (action.equals("sendContact")) {
                url = "ContactController";
            } else {
                request.setAttribute("ERROR", "❌ Không hỗ trợ action: " + action);
                url = "error.jsp";
            }

        } catch (Exception e) {
            request.setAttribute("ERROR", "Lỗi MainController: " + e.getMessage());
            url = "error.jsp";
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
