/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ChatbotDAO;
import model.ChatbotDTO;
import model.UserDTO;
import utils.ChatBotLogic;

/**
 *
 * @author admin
 */
@WebServlet(name = "ChatbotController", urlPatterns = {"/ChatbotController"})
public class ChatbotController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("USER");
        String action = request.getParameter("action");

        try {
            ChatbotDAO dao = new ChatbotDAO();

            if ("chat".equals(action)) {
                List<ChatbotDTO> messages = dao.getMessagesByUser(user.getUserId());
                request.setAttribute("CHAT_MESSAGES", messages);
                request.getRequestDispatcher("chat.jsp").forward(request, response);

            } else if ("sendMessage".equals(action)) {
                String message = request.getParameter("message");
                String reply = ChatBotLogic.getReply(message);

                ChatbotDTO chat = new ChatbotDTO();
                chat.setUserId(user.getUserId());
                chat.setMessage(message);
                chat.setReply(reply);
                dao.saveMessage(chat);

                List<ChatbotDTO> messages = dao.getMessagesByUser(user.getUserId());
                request.setAttribute("CHAT_MESSAGES", messages);
                request.getRequestDispatcher("chat.jsp").forward(request, response);
            } else if ("deleteChat".equals(action)) {
                dao.deleteMessagesByUserId(user.getUserId());
                request.setAttribute("CHAT_MESSAGES", null);
                request.getRequestDispatcher("chat.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Chatbot error: " + e.getMessage());
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
