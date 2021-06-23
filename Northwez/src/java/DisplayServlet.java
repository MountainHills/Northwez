/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import com.exception.*;
import java.util.ArrayList;
import java.util.List;
import model.SectionRecords;
/**
 *
 * @author a
 */
public class DisplayServlet extends HttpServlet {

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
        catch (SQLException|ClassNotFoundException sqle)
        {
        } 
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("image/jpeg");
        HttpSession session = request.getSession(false);
        ServletOutputStream os = response.getOutputStream();
        try{
            if(session != null)
            {
                if(con!=null)   
                {
                    String role = (String)session.getAttribute("role");
                    //Note: Change USERNAME to ORDER_ID/USER_ID or BOTH 
                    
                    if(role.equalsIgnoreCase("Guest"))
                    {
                        int orderId = (int)session.getAttribute("orderId");
                        //Search for thumbnail for specific user_id
                        
                        String thumnailQuery = "Select THUMBNAIL from ORDERS where ORDER_ID =?";
                        //Search Thumbnail in ORDERS table
                        
                        PreparedStatement pstmt1 = con.prepareStatement(thumnailQuery);
                        ResultSet rs1 = null;
                        Blob imgBlob = null;
                        byte[] imgData = null;

                        //Gets Blob
                        if(orderId != 0)
                        {
                            pstmt1.setInt(1,orderId);
                            rs1 = pstmt1.executeQuery();
                            if(rs1.next())
                            {
                                imgBlob = rs1.getBlob(1);
                            }
                        }
                        if(imgBlob != null)
                        {
                            imgData = imgBlob.getBytes(1, (int)imgBlob.length());
                            os.write(imgData);
                        }

                        os.close();
                    }
                    else
                    {
                        String orderId = (String)request.getParameter("orderId");
                        String sectionType = (String)session.getAttribute("sectionType");
                        String thumnailQuery = "Select THUMBNAIL from ORDERS where ORDER_ID = ?";
                        //Search Thumbnail in ORDERS table
                        PreparedStatement pstmt2 = con.prepareStatement(thumnailQuery);
                        ResultSet rs2 = null;
                        byte[] imgData = null;
                        
                        System.out.println("This is the image id: " + orderId);
                        if(sectionType.equalsIgnoreCase("ONGOING"))
                        {
                            pstmt2.setString(1, orderId);
                            rs2 = pstmt2.executeQuery();
                            
                            while(rs2.next())
                            {
                                imgData = rs2.getBytes("THUMBNAIL");
                            }
                            
                           
                            os.write(imgData);
                            
                            os.flush();
                            os.close();
                        
                        }
                        else
                        {
                            pstmt2.setString(1, orderId);
                            rs2 = pstmt2.executeQuery();
                            
                            while(rs2.next())
                            {
                                imgData = rs2.getBytes("THUMBNAIL");
                            }
                            
                           
                            os.write(imgData);
                            
                            os.flush();
                            os.close();
                        }
                        
                    }
                }
            }
            
        }catch(SQLException e){throw new QueryException();}
         
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
       
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
