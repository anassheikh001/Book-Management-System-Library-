import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class viewBooks extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html><head><style>");
            out.println("body { font-family: Arial, sans-serif; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("table, th, td { border: 1px solid #ddd; }");
            out.println("th, td { padding: 10px; text-align: left; }");
            out.println("thead { background-color: #f2f2f2; }");
            out.println("button { background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin-top: 20px; border: none; cursor: pointer; }");
            out.println("button:hover { background-color: #45a049; }");
            out.println("</style></head><body><center>");
            out.println("<h1>List of Books</h1>");
            out.println("<table border='2'>");
            out.println("<thead><tr><th>Book Call No.</th><th>Book Name</th><th>Author</th><th>Publication</th><th>Quantity</th></tr></thead>");
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "root");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bookDetails");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                out.println("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td></tr>");
            }
            
            out.println("</table>");
            
            // Add anchor tag for home page
            out.println("<br><br><a href=\"librariansection.html\"><button>Go Back to Home</button></a>");
            
            ps.close();
            con.close();
        } catch(Exception e) {
            out.println(e);
        }
        out.println("</center></body></html>");
        out.close();
    }
}
