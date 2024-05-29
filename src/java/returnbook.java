
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class returnbook extends HttpServlet {

    public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        rs.setContentType("text/html");
        PrintWriter out = rs.getWriter();

        String callno = rq.getParameter("t1");
        String sid = rq.getParameter("t2");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "root");
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM issuebook WHERE book_no=? AND s_id=?");
            ps1.setString(1, callno);
            ps1.setString(2, sid);
            int i = ps1.executeUpdate();
            if (i > 0) {
                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM bookDetails WHERE book_no=?");
                ps2.setString(1, callno);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    int q = Integer.parseInt(rs2.getString(5));
                    q = q + 1;
                    PreparedStatement ps3 = con.prepareStatement("UPDATE bookDetails SET quantity=? WHERE book_no=?");
                    ps3.setString(1, String.valueOf(q));
                    ps3.setString(2, callno);
                    ps3.executeUpdate();
                    out.println("<script>alert('Book returned successfully.');</script>");
                    RequestDispatcher rd = rq.getRequestDispatcher("returnbook.html");
                    rd.include(rq, rs);
                }
            } else {
                out.println("<script>alert('Invalid Book No or Student ID.');</script>");
                RequestDispatcher rd = rq.getRequestDispatcher("returnbook.html");
                rd.include(rq, rs);
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
