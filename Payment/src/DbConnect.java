
import java.sql.*;

/*
 * class comment?
 */
public class DbConnect {
	
    public static Connection getConn()
    {
    	 Connection c=null;  
    try
    {
   
       /* Class.forName("com.mysql.jdbc.Driver");//.newInstance()
        c=DriverManager.getConnection("jdbc:mysql://54.179.140.139:3306/smartgini","YoungGuns","wX6Uab95jrBUJK4ud2VW6cQXEwwHxvD5WrKUQZ5VbuSe7Dr7");
 */
    	
    	 
       
   /* Class.forName("com.mysql.jdbc.Driver").newInstance();
    c=DriverManager.getConnection("jdbc:mysql://localhost:3306/panchii","root","");*/
    	//Class.forName("com.mysql.jdbc.Driver");	
     	// c=DriverManager.getConnection("jdbc:mysql://54.179.182.252:3306/smartgini","root","resagit123456");
      	
    	
    	Class.forName("com.mysql.jdbc.Driver");	
    	c=DriverManager.getConnection("jdbc:mysql://192.168.43.23:3306/banking","hacker","hacker");
     	
        }catch(Exception e){return null;}
    return c;
    }
    
    public static  boolean Close_Connection(Connection c){
    	Connection conn;
    	try {
    		System.out.print("connection close call"+"\n");
    		c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
    	
    }
    public static boolean Close_Stetement(Statement statement){
    	try {
    		statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
    	
    }
    public static boolean Close_ResultSet(ResultSet resultset){
    	try {
    		resultset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
    	
    }
}


