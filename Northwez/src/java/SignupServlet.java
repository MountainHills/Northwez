import com.exception.ClassException;
import com.exception.NullSignupException;
import com.exception.QueryException;
import com.exception.SignupException;
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
import nl.captcha.Captcha;

public class SignupServlet extends HttpServlet {

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
        
        ResultSet rs = null;
        PreparedStatement pstmtSignup = null;
        HttpSession session = request.getSession(false);
        
        String firstName = request.getParameter("Fname");
        String lastName = request.getParameter("Lname");
        String username = request.getParameter("usernameSignUp");
        String password = request.getParameter("passwordSignUp");
        String email = request.getParameter("email");
        String contactNumber = request.getParameter("conNumber");
        String address = request.getParameter("address");
        String tin = request.getParameter("tin");
        String company = request.getParameter("company");
        
        String[] usernameArray = new String[100];
        
        boolean checkerUsername = true;
        
        Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);  
        request.setCharacterEncoding("UTF-8");  
        String captchaAnswer = request.getParameter("captcha");  

        try
        {
            pstmtSignup = con.prepareStatement("INSERT INTO USERS (USER_ID, FIRSTNAME, LASTNAME, EMAIL, ADDRESS, CONTACTNUMBER, TIN, COMPANY, USERNAME, PASSWORD, ROLE) "
                    + "VALUES(?, ?, ?, ?, ?, ? ,? ,?, ?, ?, 'GUEST')");
  
            //Subject to Change
            PreparedStatement pstmtRecord = con.prepareStatement("SELECT USERNAME FROM USERS");
            rs = pstmtRecord.executeQuery();
            //usernameArray = Methods.recordToList(rs);
        }   
        
        catch(SQLException sqle)
        {
            throw new QueryException();
        }
        
        if(firstName.equals("") || lastName.equals("") || username.equals("") 
            || password.equals("") || email.equals("") || contactNumber.equals("") 
            || address.equals("") || captchaAnswer.equals(""))
        {
            throw new NullSignupException();
        }   

        else
        {
            try
            {
                while(rs.next())
                {
                    if(username.equals(rs.getString(1)))
                    {
                        checkerUsername = false;
                    }
                }
            }
            catch(SQLException sqle)
            {
                throw new QueryException();
            }

            if(checkerUsername == true && captcha.isCorrect(captchaAnswer) == true)
            {
                try
                {
                    pstmtSignup.setInt(1, Methods.userRecordCounter());
                    pstmtSignup.setString(2, firstName);
                    pstmtSignup.setString(3, lastName);
                    pstmtSignup.setString(4, email);
                    pstmtSignup.setString(5, address);
                    pstmtSignup.setString(6, contactNumber);
                    pstmtSignup.setString(7, tin);
                    pstmtSignup.setString(8, company);
                    pstmtSignup.setString(9, username);
                    pstmtSignup.setString(10, Security.encrypt(password));
                    pstmtSignup.executeUpdate();
                }
                catch(SQLException sqle)
                {
                    throw new QueryException();
                }
               
                response.sendRedirect(request.getContextPath() + "/login-signup.jsp");
            } 

            else
            {
                throw new SignupException();
            }   
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
