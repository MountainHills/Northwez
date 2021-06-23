import com.exception.QueryException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {

    Connection con;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        try 
        {	
            Class.forName(config.getInitParameter("jdbcClassName"));
            String username = config.getInitParameter("dbUserName");
            String password = config.getInitParameter("dbPassword");
            StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL")).append("://")
                .append(config.getInitParameter("dbHostName"))
                .append(":")
                .append(config.getInitParameter("dbPort"))
                .append("/")
                .append(config.getInitParameter("databaseName"));
            con = DriverManager.getConnection(url.toString(),username,password);         
        } 
        catch (SQLException sqle)
        {

        } catch (ClassNotFoundException ex) {
        } 
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        try{
            if(session != null)
            {   
                int userId=0;
                int orderId=0;
                String updateProgress = "";
                String progress = "";
                String projectTitle = "";
                String status = "";
                String username = (String)session.getAttribute("user");
                
                //Retrieves the USER_ID of the user, which will be used for traversing the ORDERS table
                String userIDQuery = "Select USER_ID from USERS where USERNAME = ?";
                
                //Retrieves all records in the ORDERS table that has USER_ID = userId
                String orderIDRecordQuery = "Select ORDER_ID from ORDERS where USER_ID = ?";
                String orderRecordQuery = "Select * from ORDERS where ORDER_ID = ?";
                
                
                PreparedStatement pstmt1 = con.prepareStatement(userIDQuery);
                pstmt1.setString(1, username);
                ResultSet rs1 = pstmt1.executeQuery();
                
                if(rs1.next())
                {
                    userId = rs1.getInt("USER_ID");
                }
                
                PreparedStatement pstmt4 = con.prepareStatement(orderIDRecordQuery);
                pstmt4.setInt(1, userId);
                ResultSet rs4 = pstmt4.executeQuery();
                
                while(rs4.next())
                {
                    if(orderId<rs4.getInt("ORDER_ID"))
                    {
                        orderId = rs4.getInt("ORDER_ID");
                    }
                }
                
                PreparedStatement pstmt2 = con.prepareStatement(orderRecordQuery);
                pstmt2.setInt(1, orderId);
                ResultSet rs2 = pstmt2.executeQuery();
                
                PreparedStatement pstmt3 = con.prepareStatement(orderRecordQuery);
                pstmt3.setInt(1, orderId);
                ResultSet rs3 = pstmt3.executeQuery();
                
                //Checks if the given USER_ID contains a specific record from ORDERS table
                if(!(rs2.next()))
                {
                    progress = "Status";
                    projectTitle = "No current projects";
                    updateProgress = "Go to menu tab to book with us now!";
                }
                else
                {
                    while(rs3.next())
                    {
                        orderId = rs3.getInt("ORDER_ID");
                        projectTitle = rs3.getString("TITLE");
                        progress = rs3.getString("PROGRESS");
                        updateProgress = rs3.getString("UPDATEPROGRESS");
                        status = rs3.getString("STATUS_A");
                    }
                    
                    if(status.equalsIgnoreCase("FALSE") && progress == null)
                    {
                        progress = "PENDING";
                        updateProgress = "The details of your request are under review";
                    }
                    //Done
                    else if(status.equalsIgnoreCase("FALSE") && progress.equalsIgnoreCase("REJECTED"))
                    {
                        progress = "REJECTED";
                        updateProgress = "Your request has been rejected. Book again!";
                    }
                    //Done
                    else if(status.equalsIgnoreCase("TRUE") && progress.equalsIgnoreCase("FINISHED"))
                    {
                        progress ="FINISHED";
                        updateProgress = "Your request is finished! The admin will send the product through email.";
                    }
                    
                }
    
                session.setAttribute("loginTest", "loginTest");
                String role = (String)session.getAttribute("role");

                if(role.equalsIgnoreCase("ADMIN"))
                {
                    session.setAttribute("user", username);
                    request.getRequestDispatcher("AdminServlet").forward(request, response); 
                }
                else if(role.equalsIgnoreCase("GUEST"))
                {
                    System.out.println(progress);
                    session.setAttribute("status", status);
                    session.setAttribute("username",username);
                    session.setAttribute("userId",userId);
                    session.setAttribute("orderId",orderId);
                    session.setAttribute("title",projectTitle);
                    session.setAttribute("newProgress", progress);
                    session.setAttribute("progress",progress);
                    session.setAttribute("updateProgress",updateProgress);  
                    request.getRequestDispatcher("guest.jsp").forward(request, response); 
                }
                
                pstmt3.close();
                rs3.close();
                pstmt2.close();
                rs2.close();
                pstmt1.close();
                rs1.close();
            }
            else
            {
                response.sendRedirect("login-signup.jsp");
            }
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Error:" + sqle);
            throw new QueryException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
