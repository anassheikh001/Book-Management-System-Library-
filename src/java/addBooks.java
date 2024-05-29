import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class addBooks extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String book_callno = request.getParameter("t1");
        String name = request.getParameter("t2");
        String author = request.getParameter("t3");
         String publisher = request.getParameter("t4"); 
        String quantity = request.getParameter("t5"); 
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elibrary?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false", "root", "root");
            PreparedStatement ps = con.prepareStatement("INSERT INTO bookDetails (book_no, name, author, publisher, quantity) VALUES (?, ?, ?, ? , ?)");
            ps.setString(1, book_callno);
            ps.setString(2, name);
            ps.setString(3, author);
            ps.setString(4, publisher); 
            ps.setString(5, quantity);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>alert('Book added successfully');</script>");
                RequestDispatcher rd = request.getRequestDispatcher("addbook.html");
                rd.include(request, response);
            } else {
                out.println("<script>alert('Failed to add Book');</script>");
                RequestDispatcher rd = request.getRequestDispatcher("addbook.html");
                rd.include(request, response);
            }
            
            ps.close();
            con.close();
        } catch(Exception e) {
            out.println("<script>alert('Error: " + e.getMessage() + "');</script>"); // Display error message
        }
        
        out.close();
    }
        public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException {

        doGet(rq, rs);
    }
}
