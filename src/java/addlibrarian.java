
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class addlibrarian extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("t1");
        String email = request.getParameter("t2");
        String password = request.getParameter("t3");
        String mobileNumber = request.getParameter("t4");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "root");
            PreparedStatement ps = con.prepareStatement("INSERT INTO librarian VALUES (?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, mobileNumber);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>alert('Librarian added successfully');</script>");
                RequestDispatcher rd = request.getRequestDispatcher("addlibrarian.html");
                rd.include(request, response);
            } else {
                out.println("<script>alert('Failed to add librarian');</script>");
                RequestDispatcher rd = request.getRequestDispatcher("addlibrarian.html");
                rd.include(request, response);
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            out.println(e);
        }

        out.close();
    }

    public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException {

        doGet(rq, rs);
    }
}
