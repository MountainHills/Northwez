/*TO DO: The process of taking the information needs to optimized.
  The execution for large number of request would interfere
  with the speed.
*/

package model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionRecords {
    
    // These variables are where records will be stored.
    private static int order_id;
    
    private static String[][] clientNames;          // Dome
    private static String[][] requestSection;       // Done
    private static String[][] ongoingSection;       // Done
    private static String[][] finishedSection;      // Done
 
    
    // This places the records from the result set (containing the FIRSTNAME and LASTNAME) to a 2D array.
    @SuppressWarnings("empty-statement")
    public static String[][] processClientNames(ResultSet rs) throws SQLException
    {
        List<String> firstNameList = new ArrayList<>();
        List<String> lastNameList = new ArrayList<>();
        
        // Place the values of the records to their respective column.
        while(rs.next())
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
                    
            for (int i = 1; i <= columnsNumber; i++) 
            {
                String columnName = rsmd.getColumnName(i);
                
                switch (columnName) {
                    case "FIRSTNAME":
                        firstNameList.add(rs.getString(i));
                        break;
                    case "LASTNAME":
                        lastNameList.add(rs.getString(i));
                        break;
                }
            }
        }
        
        // Turns the ArrayLists into regular String arrays.
        String[] firstNameArr = firstNameList.toArray(new String[0]);
        String[] lastNameArr = lastNameList.toArray(new String[0]); 
        
        String[][] namesList = {firstNameArr, lastNameArr};
        
        return namesList;
    }
    
    // This places the records from the result set (containing the FIRSTNAME and LASTNAME) to a 2D array.
    @SuppressWarnings("empty-statement")
    public static String[][] processRequestSection(ResultSet rs) throws SQLException
    {
        // ORDER_ID is needed because it will allow us to identify which request was accepted or rejected.
        List<String> orderIDList = new ArrayList<>();
        
        // USER_ID is needed to get the names of the clients from the clientNames 2D Array.
        List<String> userIDList = new ArrayList<>();
        
        // These are the basic information needed for each request
        List<String> titleList = new ArrayList<>();
        List<String> descriptionList = new ArrayList<>();
        List<String> startList = new ArrayList<>();
        List<String> deadlineList = new ArrayList<>();
        List<String> appointmentList = new ArrayList<>();
        List<String> priceList = new ArrayList<>();
        
        // ORDER_ID, USER_ID, TITLE, START, APPOINTMENT, PRICE
        // Place the values of the records to their respective column.
        while(rs.next())
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
                    
            for (int i = 1; i <= columnsNumber; i++) 
            {
                String columnName = rsmd.getColumnName(i);
                
                switch (columnName) {
                    case "ORDER_ID":
                        orderIDList.add(rs.getString(i));
                        break;
                    case "USER_ID":
                        userIDList.add(rs.getString(i));
                        break;
                    case "TITLE":
                        titleList.add(rs.getString(i));
                        break;
                    case "DESCRIPTION":
                        descriptionList.add(rs.getString(i));
                        break;
                    case "START":
                        startList.add(rs.getString(i));
                        break;
                    case "DEADLINE":
                        deadlineList.add(rs.getString(i));
                        break;
                    case "APPOINTMENT":
                        appointmentList.add(rs.getString(i));
                        break;
                    case "PRICE":
                        priceList.add(rs.getString(i));
                        break;
                }
            }
        }
        
        // Turns the ArrayLists into regular String array.
        String[] orderIDArr = orderIDList.toArray(new String[0]);
        String[] userIDArr = userIDList.toArray(new String[0]); 
        String[] titleArr = titleList.toArray(new String[0]);
        String[] descriptionArr = descriptionList.toArray(new String[0]);
        String[] startArr = startList.toArray(new String[0]);
        String[] deadlineArr = deadlineList.toArray(new String[0]);
        String[] appointmentArr = appointmentList.toArray(new String[0]);
        String[] priceArr = priceList.toArray(new String[0]);
        
        // Places the String Arrays in to a 2D Array.
        String[][] requestList = {orderIDArr, userIDArr, titleArr, descriptionArr, startArr, deadlineArr, appointmentArr, priceArr};
        
        return requestList;
    }
    
    
    
 // This places the records from the result set (containing the FIRSTNAME and LASTNAME) to a 2D array.
    @SuppressWarnings("empty-statement")
    public static String[][] processOngoingSection(ResultSet rs) throws SQLException
    {
        
        // TO DO: Put in thumbnail here. Hindi ko pa siya linagay kasi hindi ko alam pano gamitin yung BLOB.
        
        List<String> orderIDList = new ArrayList<>();
        List<String> updateProgressList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        // TITLE, PROGRESS, UPDATEPROGRESS, and THUMBNAIL
        // Place the values of the records to their respective column.
        while(rs.next())
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
                    
            for (int i = 1; i <= columnsNumber; i++) 
            {
                String columnName = rsmd.getColumnName(i);
                
                switch (columnName) {
                    case "ORDER_ID":
                        orderIDList.add(rs.getString(i));
                        break;
                    case "UPDATEPROGRESS":
                        updateProgressList.add(rs.getString(i));
                        break;
                    case "TITLE":
                        titleList.add(rs.getString(i));
                        break;
                }
            }
        }
        
        // Turns the ArrayLists into regular String arrays.
        String[] orderIDArr = orderIDList.toArray(new String[0]);
        String[] progressUpdateArr = updateProgressList.toArray(new String[0]);
        String[] titleArr = titleList.toArray(new String[0]);
        
        // Puts the String arrays in to a 2D String array.
        String[][] ongoingList = {orderIDArr, progressUpdateArr, titleArr};
        
        return ongoingList;
    }
    
    
    // This places the records from the result set (containing the FIRSTNAME and LASTNAME) to a 2D array.
    
    
    
    // This places the records from the result set (containing the FIRSTNAME and LASTNAME) to a 2D array.
    @SuppressWarnings("empty-statement")
    public static String[][] processFinishedSection(ResultSet rs) throws SQLException
    {
        
        // TO DO: Put in thumbnail here. Hindi ko pa siya linagay kasi hindi ko alam pano gamitin yung BLOB.
        List<String> titleList = new ArrayList<>();
        List<String> orderIDList = new ArrayList<>();
        // TITLE, PROGRESS, UPDATEPROGRESS, and THUMBNAIL
        // Place the values of the records to their respective column.
        while(rs.next())
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
                    
            for (int i = 1; i <= columnsNumber; i++) 
            {
                String columnName = rsmd.getColumnName(i);
                
                switch (columnName) {
                    case "TITLE":
                        titleList.add(rs.getString(i));
                        break;
                    case "ORDER_ID":
                        orderIDList.add(rs.getString(i));
                        break;
                }
            }
        }
        
        // Turns the ArrayLists into regular String arrays.
        String[] titleArr = titleList.toArray(new String[0]);
        String[] orderIDArr = orderIDList.toArray(new String[0]); 
//        String[] progressUpdateArr = updateProgressList.toArray(new String[0]);
        
        // Puts the String arrays in to a 2D String array.
        // TO DO: Actually, hindi na kailangan ng 2D Array to. Pero tinatake into account ko yung thumbnail which is wala pa.
        
        String[][] ongoingList = {titleArr, orderIDArr};
        
        return ongoingList;
    }
    
    // Setters
    public static void setClientNames(String[][] clients) {
        clientNames = clients;
    }
    
    public static void setRequestSection(String[][] requests) {
        requestSection = requests;
    }
    
    public static void setOngoingSection(String[][] ongoing) {
        ongoingSection = ongoing;
    }
    
    public static void setFinishedSection(String[][] finished) {
        finishedSection = finished;
    }
    
    public static void setOrderID(int id) {
        order_id = id;
    }
    
     
    
    
    // Getters
    public static String[][] getClientNames() {
        return clientNames;
    }
    
    public static String[][] getRequestSection() {
        return requestSection;
    }
    
    public static String[][] getOngoingSection() {
        return ongoingSection;
    }
    
    public static String[][] getFinishedSection() {
        return finishedSection;
    }
    
    public static int getOrderID() {
        return order_id;
    }
    
   
    
    
}