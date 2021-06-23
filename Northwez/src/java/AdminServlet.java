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
import model.SectionRecords;
import com.exception.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@MultipartConfig
public class AdminServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        
        // In this part. The servlet would send the models to the JSPs.
        if(con != null && session != null)
        {
            // These actions are the ones that the admin selected. 
            // These sets what section the request came from and the ORDER_ID of the request.
            try 
            {
                String sectionOrigin = request.getParameter("section");
                int order_ID;

                // This runs if the action came from the REQUEST SECTION.
                switch (sectionOrigin) {
                    case "request":
                        {
                            order_ID = Integer.parseInt(request.getParameter("order_id"));
                            String decision = request.getParameter("decision");
                            boolean status_a = false;
                            String progress = "REJECTED"; 
                            double price = 0.00;
                            String start = request.getParameter("decision");
                            String deadline = request.getParameter("decision");
                            
                            if (decision.equalsIgnoreCase("Accept"))
                            {
                                System.out.println("I have accepted it!");
                                status_a = true;
                                price = Double.parseDouble(request.getParameter("final-price"));
                                progress = "ONGOING";
                                start = request.getParameter("final-start");
                                deadline = request.getParameter("final-deadline");
                            }   
                            else{
                                progress = "REJECTED";
                            }
                            PreparedStatement pstmtRequestUpdate = con.prepareStatement("UPDATE ORDERS SET STATUS_A = ?, "
                                                                                + "PROGRESS = ?, PRICE = ?, START = ?, DEADLINE = ? "
                                                                                + "WHERE ORDER_ID = ?");
                            pstmtRequestUpdate.setBoolean(1, status_a);
                            pstmtRequestUpdate.setString(2, progress);
                            pstmtRequestUpdate.setDouble(3, price);
                            pstmtRequestUpdate.setString(4, start);
                            pstmtRequestUpdate.setString(5, deadline);
                            pstmtRequestUpdate.setInt(6, order_ID);
                            
                            pstmtRequestUpdate.executeUpdate();
                            break;
                        }
                    case "ongoing":
                        {
                            InputStream imgStream =null;
                            order_ID = Integer.parseInt(request.getParameter("order_id"));
                            String progress = request.getParameter("progress");
                            String updateProgress = request.getParameter("updateDescription");
                            System.out.println(updateProgress);
                            ByteArrayOutputStream os = null;
                            byte[] buffer = new byte[1024];
                            byte[] imgData = null;
                            
                            Part thumbnailBlob = (Part)request.getPart("file");
                            
                            switch (progress.toLowerCase()) 
                            {
                                case "finished": 
                                {
                                    progress = "FINISHED";
                                    break;
                                }
                                case "cancelled": 
                                {
                                    progress = "CANCELLED";
                                    break;
                                }
                                default:
                                    progress = "ONGOING";
                            }
                            
                            PreparedStatement pstmtRequestUpdate;
                            if(thumbnailBlob.getSize() > 0)
                            {
                                
                                pstmtRequestUpdate = con.prepareStatement("UPDATE ORDERS SET PROGRESS = ?, "
                                                                            + "UPDATEPROGRESS = ?, THUMBNAIL = ? WHERE ORDER_ID = ?");
                                thumbnailBlob.getContentType();
                                imgStream = thumbnailBlob.getInputStream();
                                os = new ByteArrayOutputStream();
                                for (int len; (len = imgStream.read(buffer)) != -1;) {
                                    os.write(buffer, 0, len);
                                }
                                pstmtRequestUpdate.setString(1, progress);
                                pstmtRequestUpdate.setString(2, updateProgress);
                                pstmtRequestUpdate.setBytes(3, os.toByteArray());
                                pstmtRequestUpdate.setInt(4, order_ID);
                                
                            }
                            else
                            {
                                
                                pstmtRequestUpdate = con.prepareStatement("UPDATE ORDERS SET PROGRESS = ?, "
                                                                            + "UPDATEPROGRESS = ? WHERE ORDER_ID = ?");
                                pstmtRequestUpdate.setString(1, progress);
                                pstmtRequestUpdate.setString(2, updateProgress);
                                pstmtRequestUpdate.setInt(3, order_ID);
                            }
                            
                            pstmtRequestUpdate.executeUpdate();
                            
                            System.out.println("I finished the ongoing update.");
                            break;
                        }
                    
                    default:
                        break;
                }
            }
            // I will add exceptions here.
            catch (NumberFormatException nfe)
            {
                throw new PriceException();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
                    
            // Gets the records and puts them into their respective sections.
            try
            {
                
                // The query to get the names of the clients. To be used for the REQUEST SECTION
                PreparedStatement pstmtClientNames = con.prepareStatement("SELECT FIRSTNAME, LASTNAME FROM USERS");
                System.out.println("I printed this.");

                // The query that gets the records that are only suitable for the REQUEST SECTION.
                PreparedStatement pstmtRequestSection = con.prepareStatement("SELECT ORDER_ID, USER_ID, TITLE, DESCRIPTION, START, DEADLINE, APPOINTMENT, PRICE FROM ORDERS "
                                                                            + "WHERE (STATUS_A = false AND PROGRESS IS null)");

                // The query that gets the records that are only suitable for the ONGOING SECTION.
                PreparedStatement pstmtOngoingSection = con.prepareStatement("SELECT ORDER_ID, UPDATEPROGRESS, TITLE FROM ORDERS "
                                                                            + "WHERE (STATUS_A = true AND PROGRESS = 'ONGOING')");


                // The query that gets the records that are only suitable for the FINISHED SECTION.
                PreparedStatement pstmtFinishedSection = con.prepareStatement("SELECT TITLE, ORDER_ID FROM ORDERS "
                                                                            + "WHERE (STATUS_A = true AND PROGRESS = 'FINISHED')");
                
                ResultSet recordsClientNames = pstmtClientNames.executeQuery();
                ResultSet recordsRequestSection = pstmtRequestSection.executeQuery();
                ResultSet recordsOngoingSection = pstmtOngoingSection.executeQuery();
                ResultSet recordsFinishedSection = pstmtFinishedSection.executeQuery();

                // TO DO: The RESULT SET would then be transformed into a MODEL to allow easier access for the JSP.
                String[][] clientNames = SectionRecords.processClientNames(recordsClientNames);
                String[][] requestSection = SectionRecords.processRequestSection(recordsRequestSection);
                String[][] ongoingSection = SectionRecords.processOngoingSection(recordsOngoingSection);
                String[][] finishedSection = SectionRecords.processFinishedSection(recordsFinishedSection);

                // Place the information to the model.
                SectionRecords.setClientNames(clientNames);
                SectionRecords.setRequestSection(requestSection);
                SectionRecords.setOngoingSection(ongoingSection);
                SectionRecords.setFinishedSection(finishedSection);
                
                // Closing RESULTSETS and PSTMT
                recordsClientNames.close();
                recordsRequestSection.close();
                recordsOngoingSection.close();
                recordsFinishedSection.close();

                pstmtClientNames.close();
                pstmtRequestSection.close();
                pstmtOngoingSection.close();
                pstmtFinishedSection.close();
                
                response.sendRedirect(request.getContextPath() + "/admin.jsp");
               
            }
            catch(SQLException sqle)
            {
                throw new QueryException();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            // no connection is established in here 
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
        return "This servlet handles the back-end of the admin page.";
    }

}
