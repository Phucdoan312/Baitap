/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author admin
 */




public class ChatBotLogic {

    public static String getReply(String input) {
        input = input.toLowerCase();

        if (input.contains("giờ") || input.contains("mở cửa")) {
            return "Tiệm mở cửa từ <strong>8:00</strong> đến <strong>20:00</strong> hàng ngày.";
        } else if (input.contains("giá") || input.contains("bao nhiêu")) {
            return "Bạn có thể xem <a href='MainController?action=viewServices'>bảng giá dịch vụ</a> tại đây.";
        } else if (input.contains("đặt lịch")) {
            return "Bạn có thể <a href='MainController?action=form'>đặt lịch cắt tóc</a> tại mục 'Đặt Lịch'.";
        } else if (input.contains("liên hệ") || input.contains("hỗ trợ")) {
            return "Bạn có thể <a href='contact.jsp'>gửi liên hệ</a> để được hỗ trợ thêm.";
        } else if (input.contains("chatbot") || input.contains("bot")) {
            return "🤖 Mình là BarberBot! Bạn cần hỏi gì thì cứ gõ vào nhé.";
        } else {
            return "Xin lỗi, mình chưa hiểu. Bạn có thể hỏi lại nhé!";
        }
    }
}
