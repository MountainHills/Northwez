import java.io.*;
import com.exception.ClassException;
import com.exception.NullFormException;
import com.exception.QueryException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig(maxFileSize = 16177215)
public class FormServlet extends HttpServlet {

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
            throw new QueryException();
        } 
        catch (ClassNotFoundException nfe)
        {
            throw new ClassException();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
        
        String userID = "1";
        
        ResultSet rs = null;
        PreparedStatement pstmtForm = null;
        PreparedStatement pstmtUsername = null;
        
        HttpSession session = request.getSession(false);
        
        if(session != null)
        {  
            String username = (String)session.getAttribute("user");
            String title = request.getParameter("titleForm");
            String start = request.getParameter("startForm");
            String deadline = request.getParameter("deadlineForm");
            String description = request.getParameter("descriptionForm");
            String appointment = request.getParameter("appointmentForm");
            String priceST = request.getParameter("priceForm");
            BigDecimal priceBD = new BigDecimal(priceST);

            try
            {
                pstmtForm = con.prepareStatement("INSERT INTO ORDERS (ORDER_ID, STATUS_A, USER_ID, TITLE, START, DEADLINE, DESCRIPTION, APPOINTMENT, "
                    + "PRICE, PROGRESS, UPDATEPROGRESS) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                pstmtUsername = con.prepareStatement("SELECT USER_ID FROM USERS WHERE USERNAME = ?");
                pstmtUsername.setString(1, username);
                rs = pstmtUsername.executeQuery();
                
                while(rs.next())
                {
                    userID = rs.getString(1);
                }
                
            }
            catch(SQLException sqle)
            {
                throw new NullFormException();
            }

            if(username.equals("") || title.equals("") || start.equals("") || deadline.equals("") 
                || description.equals("") || appointment.equals("") || priceST.equals(""))
            {
                throw new NullFormException();
            }

            if(con != null)
            {
                try
                {
                    pstmtForm.setInt(1, Methods.orderRecordCounter());
                    pstmtForm.setBoolean(2, false);
                    pstmtForm.setInt(3, Integer.parseInt(userID));
                    pstmtForm.setString(4, title);
                    pstmtForm.setString(5, start);
                    pstmtForm.setString(6, deadline);
                    pstmtForm.setString(7, description);
                    pstmtForm.setString(8, appointment);
                    pstmtForm.setBigDecimal(9, priceBD);
                    pstmtForm.setString(10, null);
                    pstmtForm.setString(11, "COMMENTS GOES HERE");

                    pstmtForm.executeUpdate();
                    
                    response.sendRedirect(request.getContextPath() + "/ProfileServlet");
                }
                catch(SQLException sqle)
                {
                    throw new QueryException();
                }
            }
        }
        
        else
        {
            response.sendRedirect(request.getContextPath() + "/login-signup.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
