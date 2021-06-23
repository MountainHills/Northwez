import java.io.File;
import java.sql.*;

public class Methods {
   
    private static int pageNumber = 1;
    private static String header = "";
    private static String footer = "";
    private static String timeStamp = "";
    
    public static int userRecordCounter()
    {   
        Connection connection;
        int userCounter = 1;
        
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver"); 
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/NORTHWEZ_DB", "app", "app");     
            
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM USERS");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next())
            {
                userCounter++;
            }
            
            rs.close();
            pstmt.close();
            connection.close();
        }
        
        catch(ClassNotFoundException | SQLException e )
        {
            e.printStackTrace();
        }
        
        return userCounter;
    }
    
    public static int orderRecordCounter()
    {   
        Connection connection;
        int orderCounter = 1;
        
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver"); 
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/NORTHWEZ_DB", "app", "app");     
            
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM ORDERS");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next())
            {
                orderCounter++;
            }
            
            rs.close();
            pstmt.close();
            connection.close();
        }
        
        catch(ClassNotFoundException | SQLException e )
        {
            e.printStackTrace();
        }
        
        return orderCounter;
    }
    
    public static void setPN(int pn)
    {
        pageNumber = pn;
    }
    
    public static int getPN()
    {
        return pageNumber;
    }
    
    public static void setHEADER(String h)
    {
        header = h;
    }
    
    public static String getHEADER()
    {
        return header;
    }
    
    public static void setFOOTER(String f)
    {
        footer = f;
    }
    
    public static String getFOOTER()
    {
        return footer;
    }
    
    public static void setTS(String tS)
    {
        timeStamp = tS;
    }
    
    public static String getTS()
    {
        return timeStamp;
    }
}
