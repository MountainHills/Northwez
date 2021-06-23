import com.exception.ClassException;
import com.exception.FileException;
import com.exception.QueryException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RecordServlet extends HttpServlet {

    Connection con;
    
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
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
        
        response.setContentType("application/pdf");

        ResultSet rs = null;
        ResultSet order = null;
        PreparedStatement pstmtRecord = null;
        PreparedStatement pstmtOrder = null;
        HttpSession session = request.getSession(false);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        String query = "";
        //String highestOrder = "";
        String role = (String)session.getAttribute("role");
        String username = (String)session.getAttribute("user");
        
        /*try
        {
            pstmtOrder = con.prepareStatement("SELECT MAX(ORDER_ID) FROM ORDERS, USERS WHERE USERS.USERNAME = '" + username + "'");
            order = pstmtOrder.executeQuery();
            //Subject to Change
            while(order.next())
            {
                highestOrder = order.getString(1);
            }
        }
        catch(SQLException sqle)
        {
            throw new ClassException();
        }*/
        
        if(role.equalsIgnoreCase("ADMIN"))
        {
            query = "SELECT TIN, COMPANY, FIRSTNAME, LASTNAME, EMAIL, ADDRESS, PROGRESS, PRICE FROM USERS, ORDERS "
                    + "WHERE PRICE IS NOT NULL AND ORDERS.USER_ID = USERS.USER_ID";
        }
        else
        {
            query = "SELECT FIRSTNAME, LASTNAME, ADDRESS, ORDER_ID, TITLE, PRICE FROM USERS, ORDERS "
                    + "WHERE PRICE IS NOT NULL AND ORDERS.USER_ID = USERS.USER_ID AND USERS.USERNAME = '" 
                    + username + "' AND ORDERS.PROGRESS = 'FINISHED'";
        }
              
        try
        {
            pstmtRecord = con.prepareStatement(query);         
            rs = pstmtRecord.executeQuery(); 
        }
        catch(SQLException sqle)
        {
            throw new QueryException();
        }
        
        if(con != null)
        {
            response.setHeader("Content-disposition", "attachment; filename=" + timeFormat.format(timestamp) + ".pdf");

            String header = getServletContext().getInitParameter("HEADER");
            String footer = getServletContext().getInitParameter("FOOTER");
            
            try
            {
                if(role.equalsIgnoreCase("ADMIN"))
                { 
                    Methods.setHEADER(header);
                    Methods.setFOOTER(footer);
                    Methods.setTS(timeFormat.format(timestamp) + "");

                    Document documentAdmin = new Document();
                    PdfWriter writer = PdfWriter.getInstance(documentAdmin, response.getOutputStream());
                    AdminHeaderFooterPageEvent event = new AdminHeaderFooterPageEvent();
                    writer.setPageEvent(event);
                    Rectangle rectangle = new Rectangle(PageSize.LEGAL.rotate());
                    documentAdmin.setPageSize(rectangle);
                    documentAdmin.open();

                    PdfPTable table = new PdfPTable(8);
                    table.setWidthPercentage(100);
                    PdfPCell c1 = new PdfPCell(new Phrase("TIN"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("COMPANY"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("FIRSTNAME"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("LASTNAME"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    
                    c1 = new PdfPCell(new Phrase("EMAIL ADDRESS"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    
                    c1 = new PdfPCell(new Phrase("ADDRESS"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    
                    c1 = new PdfPCell(new Phrase("PROGRESS"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    
                    c1 = new PdfPCell(new Phrase("PAYMENT"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
                    table.setHeaderRows(1);

                    float counter = 0;  // Count the number of records. Float is used for ceiling function to work.

                    while(rs.next())
                    {          
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();

                        for (int i = 1; i <= columnsNumber; i++) 
                        {
                            String columnName = rsmd.getColumnName(i);
                            String result = rs.getString(i);
                            if(columnName.equals("TIN"))
                            {
                                table.addCell(result);
                            }
                            else if(columnName.equals("COMPANY"))
                            {
                                table.addCell(result);
                            }
                            else if(columnName.equals("FIRSTNAME"))
                            {
                                table.addCell(result);
                            }
                            else if(columnName.equals("LASTNAME"))
                            {
                                table.addCell(result);
                            }
                            else if(columnName.equals("EMAIL"))
                            {
                                table.addCell(result);
                            }
                            else if(columnName.equals("ADDRESS"))
                            {
                                table.addCell(result);
                            }
                            else if(columnName.equals("PROGRESS"))
                            {
                                table.addCell(result);
                            }
                            else
                            {
                                table.addCell(result);
                            }
                        }

                        counter++;
                    }

                    // Ceiling Function is used to get the number of pages for the document.
                    // Floats or Doubles are necessary data types for this to work.
                    // There is only a capacity of 32 rows for each page.
                    // Ex. 100 Records / 32 Cells = 3.15625
                    // Math.ceil(3.15625) = 4.0 pages. Need to cast to int since setPn() only accepts integers.
                    Methods.setPN((int)Math.ceil(counter/32));

                    float[] columnWidths = new float[]{30f, 30f, 30f, 30f, 60f, 60f, 30f, 20f};
                    table.setWidths(columnWidths);

                    // Place the cells in the document.
                    documentAdmin.add(table);             
                    documentAdmin.close();
                }    
                
                else
                {
                    Methods.setHEADER(header);
                    Methods.setFOOTER(footer);
                    
                    Document documentGuest = new Document();
                    PdfWriter writer = PdfWriter.getInstance(documentGuest, response.getOutputStream());
                    GuestHeaderFooterPageEvent event = new GuestHeaderFooterPageEvent();
                    writer.setPageEvent(event);
                    Rectangle rectangle = new Rectangle(PageSize.A5);
                    documentGuest.setPageSize(rectangle);
                    documentGuest.open();

                    while(rs.next())
                    {          
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();

                        documentGuest.add(new Paragraph("Brgy. Telabastagan, City of San Fernando Pampanga"));
                        documentGuest.add(new Paragraph("Contact Number: 09123470313"));
                        documentGuest.add(new Paragraph("TIN: 123-456-789-012"));
                        documentGuest.add(new Paragraph("\n"));
                        
                        for (int i = 1; i <= columnsNumber; i++) 
                        {
                            String columnName = rsmd.getColumnName(i);
                            String result = rs.getString(i);
                            
                            if(columnName.equals("FIRSTNAME"))
                            {
                                documentGuest.add(new Paragraph("First Name: " + result));
                            }
                            else if(columnName.equals("LASTNAME"))
                            {
                                documentGuest.add(new Paragraph("Last Name: " + result));
                            }
                            else if(columnName.equals("ADDRESS"))
                            {
                                documentGuest.add(new Paragraph("Client Address: " + result));
                            }
                            else if(columnName.equals("ORDER_ID"))
                            {
                                documentGuest.add(new Paragraph("Order Number: " + result));
                            }
                            else if(columnName.equals("TITLE"))
                            {
                                documentGuest.add(new Paragraph("Project Title: " + result));
                            }
                            else if(columnName.equals("PRICE"))
                            {
                                documentGuest.add(new Paragraph("Price: " + result));
                            }
                        }
                        
                        documentGuest.newPage();
                    }
                    
                    documentGuest.close();
                }
            }
            
            catch(DocumentException de)
            {
                throw new FileException();
            }
            
            catch(SQLException sqle)
            {
                throw new QueryException();
            }
        }
        
        else
        {
            response.sendRedirect(request.getContextPath() + "/login-signup.jsp");
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
