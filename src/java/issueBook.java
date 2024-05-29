
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class issueBook extends HttpServlet {

    public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        rs.setContentType("text/html");
        PrintWriter out = rs.getWriter();

        String callno = rq.getParameter("t1");
        String sid = rq.getParameter("t2");
        String name = rq.getParameter("t3");
        String phone = rq.getParameter("t4");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "root");
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM bookDetails WHERE book_no=?");
            ps1.setString(1, callno);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                int q = Integer.parseInt(rs1.getString(5));
                if (q <= 0) {
                    out.println("<script>alert('Out of stock.');</script>");
                } else {
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO issuebook VALUES (?, ?, ?, ?)");
                    ps2.setString(1, callno);
                    ps2.setString(2, sid);
                    ps2.setString(3, name);
                    ps2.setString(4, phone);
                    ps2.executeUpdate();
                    out.println("<script>alert('Book issued successfully to: " + name + "');</script>");
                    PreparedStatement ps3 = con.prepareStatement("UPDATE bookDetails SET quantity=? WHERE book_no=?");
                    q = q - 1;
                    ps3.setString(1, String.valueOf(q));
                    ps3.setString(2, callno);
                    ps3.executeUpdate();
                    RequestDispatcher rd = rq.getRequestDispatcher("issuebook.html");
                    rd.include(rq, rs);
                }
            } else {
                out.println("<script>alert('Book not available.');</script>");
            }
        } catch (Exception e) {
            out.println("<script>alert('Error: " + e.getMessage() + "');</script>");
        }
        out.close();
    }

    public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException {

        doGet(rq, rs);
    }
}
