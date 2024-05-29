import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class viewLibrarian extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.println("<body><center><h1>List of Librarian</h1><table border='2'><br><br><br>");
            out.println("<thead><td>Name</td><td>Email</td></thead>");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "root");
            PreparedStatement ps = con.prepareStatement("SELECT* FROM librarian");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                out.println("<tr><td>" +rs.getString(1) + "</td><td>" + rs.getString(2) + "</td></tr>");
            }
            out.println("</table>");
            out.println("<br><br><a href=\"adminsection.html\"><button>Go Back to Home</button></a>");
            ps.close();
            con.close();
        }
        catch(Exception e) {
            out.println(e);
        }
        out.close();
    }
}
