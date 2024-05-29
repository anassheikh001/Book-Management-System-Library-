import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class viewissuedBooks extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.println("<body><center><h1>List of Books</h1><table border='2'><br><br><br>");
            out.println("<thead><td>Book_no.</td><td>Student Id</td><td>Student name</td><td>Student Mobile no.</td></thead>");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "root");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM issuebook");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                out.println("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(4) + "</td></tr>");
            }
            out.println("</table>");
            
            // Add anchor tag for home page
            out.println("<br><br><a href=\"librariansection.html\"><button>Go Back to Home</button></a>");
            
            ps.close();
            con.close();
        } catch(Exception e) {
            out.println(e);
        }
        out.close();
    }
}
