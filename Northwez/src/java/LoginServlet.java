import com.exception.ClassException;
import com.exception.LoginException;
import com.exception.NullLoginException;
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

public class LoginServlet extends HttpServlet {

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
        
        ResultSet recordsUsername = null;
        ResultSet recordsPassword = null;
        ResultSet recordsRole = null;
                
        String username = request.getParameter("usernameLogin");
        String password = request.getParameter("passwordLogin");
        String role = "";
        
        String[] usernameArray = new String[100];
        String[] passwordArray = new String[100];
        String[] roleArray = new String[100];
                
        try
        {
            PreparedStatement pstmtUsername = con.prepareStatement("SELECT USERNAME FROM USERS");
            PreparedStatement pstmtPassword = con.prepareStatement("SELECT PASSWORD FROM USERS");
            PreparedStatement pstmtROLE = con.prepareStatement("SELECT ROLE FROM USERS");
            
            recordsUsername = pstmtUsername.executeQuery();
            recordsPassword = pstmtPassword.executeQuery();
            recordsRole = pstmtROLE.executeQuery();
            
            //usernameArray = Methods.recordToList(recordsUsername);
            //passwordArray = Methods.recordToList(recordsPassword); 
            //roleArray = Methods.recordToList(recordsRole);
        }
        
        catch(SQLException sqle)
        {
            throw new QueryException();
        }
            
        boolean checkerUsername = false;
        boolean checkerPassword = false;
        int counterPassword = 0;
        int counterRole = 0;
        int checkerRecord = 0;

        if(con != null)
        {
            if(username.equals("") || password.equals(""))
            {
                throw new NullLoginException();
            }

            else
            {
                password = Security.encrypt(password);
                
                try
                {
                    while(recordsUsername.next())
                    {
                        checkerRecord++;
                        if(username.equals(recordsUsername.getString(1)))
                        {
                            checkerUsername = true;
                            username = recordsUsername.getString(1);
                            break;
                        }
                        //checkerRecord++;
                    }
                    
                    while(recordsPassword.next())
                    {
                        counterPassword++;
                        if(password.equals(recordsPassword.getString(1)) && checkerRecord == counterPassword)
                        {
                            checkerPassword = true;
                        }
                        //counter++;
                    }
                    
                    while(recordsRole.next())
                    {
                        counterRole++;
                        if(checkerRecord == counterRole)
                        {
                            role = recordsRole.getString(1);
                        }
                    }
                }
                catch(SQLException sqle)
                {
                    throw new NullLoginException();
                }

                HttpSession session = request.getSession();

                if(checkerUsername == true && checkerPassword == true)  //checkerUsername == true && checkerPassword == true)
                {
                    session.setAttribute("user", username);
                    session.setAttribute("role", role);

                    response.sendRedirect("ProfileServlet");
                }

                else
                {
                    throw new LoginException();
                }                   
            }
        }

        else
        {
            // no connection is established in here 
        }           
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
