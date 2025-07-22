/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import model.BookingDAO;
import model.BookingDTO;
import model.ServiceDAO;
import model.ServiceDTO;
import model.UserDAO;
import model.UserDTO;
import utils.MailUtil;

/**
 *
 * @author ad
 */
@WebServlet(name = "BookingController", urlPatterns = {"/BookingController"})
public class BookingController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("USER");
        String url = "error.jsp";

        try {
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            BookingDAO dao = new BookingDAO();

            switch (action) {

                case "createBooking": {
                    int serviceId = Integer.parseInt(request.getParameter("service_id"));
                    String bookingTimeStr = request.getParameter("booking_time");
                    String phone = request.getParameter("phone");
                    String paymentMethod = request.getParameter("payment_method");

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    Date bookingDate = sdf.parse(bookingTimeStr);

                    if (bookingDate.before(new Date())) {
                        request.setAttribute("ERROR", "⚠ Thời gian đặt phải ở tương lai!");
                        request.setAttribute("SERVICE_LIST", new ServiceDAO().getAllServices());
                        url = "form.jsp";
                        break;
                    }

                    String regex = "^(0|\\+84)(3[2-9]|5[6|8|9]|7[06-9]|8[1-5]|9[0-9])[0-9]{7}$";
                    if (!phone.matches(regex)) {
                        request.setAttribute("ERROR", "⚠ Số điện thoại không hợp lệ!");
                        request.setAttribute("SERVICE_LIST", new ServiceDAO().getAllServices());
                        url = "form.jsp";
                        break;
                    }

                    BookingDTO booking = new BookingDTO(0, user.getUserId(), serviceId,
                            new java.sql.Timestamp(bookingDate.getTime()), phone, paymentMethod, "Pending");

                    if (dao.createBooking(booking)) {
                        request.setAttribute("SUCCESS", "✅ Đặt lịch thành công!");
                        request.setAttribute("SUCCESS_REDIRECT", true);
                    } else {
                        request.setAttribute("ERROR", "❌ Không thể đặt lịch.");
                    }

                    request.setAttribute("SERVICE_LIST", new ServiceDAO().getAllServices());
                    url = "form.jsp";
                    break;
                }

                case "listBooking": {
                    List<BookingDTO> list = user.getRole().equals("ROLE_ADMIN")
                            ? dao.getAllBookings()
                            : dao.getBookingsByUser(user.getUserId());

                    Map<Integer, String> serviceMap = new ServiceDAO().getServiceMap();
                    Map<Integer, String> userMap = new UserDAO().getUserMap();
                    request.setAttribute("BOOKING_LIST", list);
                    request.setAttribute("SERVICE_MAP", serviceMap);
                    request.setAttribute("USER_MAP", userMap);
                    url = "listBooking.jsp";
                    break;
                }

                case "editBooking": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Map<Integer, String> userMap = new UserDAO().getUserMap();
                    request.setAttribute("SERVICE_LIST", new ServiceDAO().getAllServices());
                    request.setAttribute("BOOKING", dao.getBookingById(id));
                    request.setAttribute("USER_MAP", userMap);
                    url = "editBooking.jsp";
                    break;
                }

                case "updateBooking": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    int serviceId = Integer.parseInt(request.getParameter("service_id"));
                    String bookingTimeStr = request.getParameter("booking_time");
                    String phone = request.getParameter("phone");
                    String paymentMethod = request.getParameter("payment_method");

                    String status = "ROLE_ADMIN".equals(user.getRole())
                            ? request.getParameter("status")
                            : dao.getBookingById(id).getStatus();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    Date bookingDate = sdf.parse(bookingTimeStr);

                    String regex = "^(0|\\+84)(3[2-9]|5[6|8|9]|7[06-9]|8[1-5]|9[0-9])[0-9]{7}$";
                    if (!phone.matches(regex)) {
                        request.setAttribute("ERROR", "⚠ Số điện thoại không hợp lệ!");
                        url = "editBooking.jsp";
                        break;
                    }

                    BookingDTO booking = new BookingDTO(id, user.getUserId(), serviceId,
                            new java.sql.Timestamp(bookingDate.getTime()), phone, paymentMethod, status);

                    if (dao.updateBooking(booking)) {
                        request.setAttribute("SUCCESS", "✅ Cập nhật thành công!");
                    } else {
                        request.setAttribute("ERROR", "❌ Không thể cập nhật.");
                    }

                    List<BookingDTO> list = user.getRole().equals("ROLE_ADMIN")
                            ? dao.getAllBookings()
                            : dao.getBookingsByUser(user.getUserId());

                    Map<Integer, String> serviceMap = new ServiceDAO().getServiceMap();
                    Map<Integer, String> userMap = new UserDAO().getUserMap();
                    request.setAttribute("SERVICE_MAP", serviceMap);
                    request.setAttribute("BOOKING_LIST", list);
                    request.setAttribute("USER_MAP", userMap);
                    url = "listBooking.jsp";
                    break;
                }

                case "updateStatus": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String status = request.getParameter("status");

                    boolean success = dao.updateBookingStatus(id, status);

                    if (success) {
                        request.setAttribute("SUCCESS", "✅ Cập nhật trạng thái thành công.");

                        if ("Done".equalsIgnoreCase(status)) {
                            BookingDTO booking = dao.getBookingById(id);
                            UserDTO bookingUser = new UserDAO().getUserById(booking.getUserId());

                            if (bookingUser != null && bookingUser.getEmail() != null) {
                                String to = bookingUser.getEmail();
                                String subject = "🎉 Lịch đặt của bạn đã hoàn tất!";
                                String htmlMessage = "<html>"
                                        + "<body style='font-family: Arial, sans-serif; background: linear-gradient(135deg, #f5f7fa, #c3cfe2); padding: 30px;'>"
                                        + "<div style='max-width: 600px; margin: auto; background-color: #fff; padding: 25px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1);'>"
                                        + "<h2 style='color: #e67e22; text-align: center; font-size: 24px;'>🎉 Hoàn tất lịch cắt tóc!</h2>"
                                        + "<p style='font-size: 16px;'>Xin chào <strong>" + bookingUser.getName() + "</strong>,</p>"
                                        + "<p style='font-size: 16px;'>Lịch cắt tóc của bạn đã được <strong style='color: green;'>thanh toán thành công</strong>.</p>"
                                        + "<h3 style='font-size: 18px;'>📌 Thông tin chi tiết:</h3>"
                                        + "<ul style='font-size: 16px; padding-left: 20px;'>"
                                        + "<li>⏰ <strong>Thời gian:</strong> " + booking.getBookingTime() + "</li>"
                                        + "<li>💇 <strong>Dịch vụ:</strong> " + new ServiceDAO().getServiceNameById(booking.getServiceId()) + "</li>"
                                        + "</ul>"
                                        + "<p style='font-size: 16px;'>✨ Cảm ơn bạn đã sử dụng <strong>3TL BarberBooking</strong>!</p>"
                                        + "<div style='text-align: center; margin: 30px 0;'>"
                                        + "<a href=' https://649d3c28c43c.ngrok-free.app/Assignment_PRJ301/ReturnController' "
                                        + "style='display: inline-block; padding: 12px 30px; background: #e67e22; color: white; text-decoration: none; border-radius: 8px; font-weight: bold; font-size: 16px;'>"
                                        + "🔗 Quay lại 3TL BarberBooking</a>"
                                        + "</div>"
                                        + "<p style='color: gray; font-size: 12px; text-align: center;'>3TL BarberBooking | Hotline: 1900 9999</p>"
                                        + "</div>"
                                        + "</body></html>";

                                MailUtil.sendMail(to, subject, htmlMessage);
                            }
                        }
                    } else {
                        request.setAttribute("ERROR", "❌ Cập nhật thất bại.");
                    }

                    List<BookingDTO> list = user.getRole().equals("ROLE_ADMIN")
                            ? dao.getAllBookings()
                            : dao.getBookingsByUser(user.getUserId());

                    Map<Integer, String> serviceMap = new ServiceDAO().getServiceMap();
                    Map<Integer, String> userMap = new UserDAO().getUserMap();
                    request.setAttribute("BOOKING_LIST", list);
                    request.setAttribute("SERVICE_MAP", serviceMap);
                    request.setAttribute("USER_MAP", userMap);
                    url = "listBooking.jsp";
                    break;
                }

                case "deleteBooking": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    if (dao.deleteBooking(id)) {
                        request.setAttribute("SUCCESS", "✅ Xoá lịch thành công!");
                    } else {
                        request.setAttribute("ERROR", "❌ Không thể xoá lịch.");
                    }

                    List<BookingDTO> list = user.getRole().equals("ROLE_ADMIN")
                            ? dao.getAllBookings()
                            : dao.getBookingsByUser(user.getUserId());

                    Map<Integer, String> serviceMap = new ServiceDAO().getServiceMap();
                    Map<Integer, String> userMap = new UserDAO().getUserMap();
                    request.setAttribute("BOOKING_LIST", list);
                    request.setAttribute("SERVICE_MAP", serviceMap);
                    request.setAttribute("USER_MAP", userMap);
                    url = "listBooking.jsp";
                    break;
                }

                default:
                    request.setAttribute("ERROR", "⚠ Không hỗ trợ action: " + action);
                    url = "error.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Lỗi BookingController: " + e.getMessage());
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
