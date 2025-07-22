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
import model.UserDAO;
import model.UserDTO;
import utils.PasswordUtil;

/**
 *
 * @author ad
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String url = "login.jsp";

        try {
            if ("login".equals(action)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                String hashedPassword = PasswordUtil.hash(password);

                UserDAO dao = new UserDAO();
                UserDTO user = dao.checkLogin(email, hashedPassword);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", user);

                    if ("ROLE_ADMIN".equals(user.getRole())) {
                        url = "dashboard.jsp";
                    } else {
                        url = "home.jsp";
                    }
                } else {
                    request.setAttribute("ERROR", "Invalid email or password.");
                }

            } else if ("logout".equals(action)) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                url = "login.jsp";

            } else if ("register".equals(action)) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                UserDAO dao = new UserDAO();
                if (dao.emailExists(email)) {
                    request.setAttribute("ERROR", "Email already exists.");
                    url = "register.jsp";
                } else {
                    String hashed = PasswordUtil.hash(password);
                    UserDTO newUser = new UserDTO(0, name, email, hashed, "ROLE_USER");
                    dao.register(newUser);
                    url = "login.jsp";
                }
            } else if ("listUsers".equals(action)) {
                UserDAO dao = new UserDAO();
                List<UserDTO> users = dao.getAllUsers();
                request.setAttribute("USER_LIST", users);
                url = "user.jsp";
            } else if ("deleteUser".equals(action)) {
                HttpSession session = request.getSession(false);
                UserDTO currentUser = (UserDTO) session.getAttribute("USER");

                if (currentUser == null || !"ROLE_ADMIN".equals(currentUser.getRole())) {
                    url = "login.jsp";
                } else {
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    UserDAO dao = new UserDAO();
                    try {
                        dao.deleteUserById(userId);
                    } catch (Exception ex) {
                        request.setAttribute("ERROR", ex.getMessage());
                    }

                    List<UserDTO> users = dao.getAllUsers();
                    request.setAttribute("USER_LIST", users);
                    url = "user.jsp";
                }
            } else if ("editUser".equals(action)) {
                HttpSession session = request.getSession(false);
                UserDTO currentUser = (UserDTO) session.getAttribute("USER");

                if (currentUser == null || !"ROLE_ADMIN".equals(currentUser.getRole())) {
                    url = "login.jsp";
                } else {
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    UserDAO dao = new UserDAO();

                    UserDTO userToEdit = dao.getUserById(userId);
                    request.setAttribute("USER_TO_EDIT", userToEdit);

                    url = "editUser.jsp";
                }
            } else if ("updateUser".equals(action)) {
                HttpSession session = request.getSession(false);
                UserDTO currentUser = (UserDTO) session.getAttribute("USER");

                if (currentUser == null || !"ROLE_ADMIN".equals(currentUser.getRole())) {
                    url = "login.jsp";
                } else {
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String role = request.getParameter("role");

                    UserDAO dao = new UserDAO();
                    UserDTO updatedUser = new UserDTO(userId, name, email, null, role);
                    dao.updateUser(updatedUser);

                    List<UserDTO> users = dao.getAllUsers();
                    request.setAttribute("USER_LIST", users);

                    url = "user.jsp";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "UserController Error: " + e.getMessage());

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
