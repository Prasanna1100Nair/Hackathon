import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;
import com.mysql.jdbc.Statement;
import com.sun.accessibility.internal.resources.accessibility;


public class DatabaseAccess {

	public static void main(String[] args) 
	{

	}
	
	
public JSONObject readTransactions(String username)
{
	JSONArray json_array= new JSONArray();
  	
	JSONObject jobj=new JSONObject();
	String query = "SELECT transaction_id,sender,receiver,amount,timestamp,withdrawn FROM banking.transaction where sender='"+username+"' or receiver= '"+username+"'  order by timestamp DESC limit 25";

	Statement stmt_select;
	try {
		stmt_select = (Statement) DbConnect.getConn().createStatement();
	
	     ResultSet resultset = stmt_select.executeQuery(query);
	resultset.last();
	int row_count=resultset.getRow();
	resultset.beforeFirst();
	
	if(!resultset.next())
	{
	      // out.write("Y"); 
	}
	else
	{
		
		do
      	{
      		JSONObject data =new JSONObject();
      		
      		data.put("TransactionID", resultset.getString("transaction_id"));
      		data.put("Sender", resultset.getString("sender"));
      		data.put("Amount", resultset.getString("amount"));
      		data.put("Receiver", resultset.getString("receiver"));
      		data.put("Timpestamp", resultset.getString("timestamp"));
      	   json_array.add(data)	;
   	  	}
   	  	while(resultset.next());
	
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	jobj.put("transactions", json_array);
	
	return jobj;
}

	
	public Map readuserdata(String received_username)
	{
		boolean error_found=false;
		Map userdata= new HashMap<>();
		String query="SELECT username,password,account_balance,name FROM banking.customer_account where username='"+received_username+"'";
		// TODO Auto-generated method stub
		try
		{

			int row_count;
		
			Statement stmt_select = (Statement) DbConnect.getConn().createStatement();
			ResultSet resultset = stmt_select.executeQuery(query);
			resultset.last();
			row_count=resultset.getRow();
			resultset.beforeFirst();
		if(resultset.next())
		{
			String username= resultset.getString("Username");
			String password= resultset.getString("Password");
			String balance= resultset.getString("Account_Balance");
			String name = resultset.getString("name");
			System.out.println(username+" "+password+"  "+balance+"   "+name);
	
		userdata.put("Username", username);
		userdata.put("Password", password);
		userdata.put("Balance", balance);
		userdata.put("Name", name);
		}
		else
		{
			error_found=true;
					
		}
		resultset.close();
		}catch(Exception e)
		{
			error_found=true;
	System.out.println(e);
	e.printStackTrace();
		}
		
		
		if(error_found)
		{
	return null;		
		}else
		{
		return userdata;
}}
		
	
	
  public boolean insertTransactionToDatabase(String transaction_id,String sender,String receiver,String amount)
  {
	  
	  int insertStatement = 0;
	  String insert_query="INSERT INTO banking.transaction (`transaction_id`, `sender`, `amount`, `receiver`, `timestamp`, `withdrawn`) VALUES ('"+transaction_id+"', '"+sender+"', '"+amount+"', '"+receiver+"', CURRENT_TIMESTAMP(),'"+0+"')";

	  System.out.println(insert_query);

	  
	    Statement stmt_insert=null;
			try
			{
		
		    stmt_insert =(Statement) DbConnect.getConn().createStatement();

			System.out.println(insert_query);
			insertStatement =   stmt_insert.executeUpdate(insert_query);
			System.out.println("insert in to transaction table  --- "+insertStatement);

			
			
			}
			catch(Exception e )
			{
		
				System.out.print(e.toString());
			}finally{

				
				if(stmt_insert!=null){
					DbConnect.Close_Stetement(stmt_insert);
				}

				}	

			if(insertStatement==0)
			{
				return false;
				  		
			}
			else
			{
  if(updateBalance(Double.parseDouble(amount), sender))
  {
				return true;
  }
  else
  {
	  return false;
  }
			}
			
		
  }
	
  public boolean updateBalance(Double amount, String username)
  {
	
	  
	  String update_query="UPDATE banking.customer_account SET Account_Balance=Account_Balance-"+amount+" WHERE `Username`='"+username+"'";
	  
		int rs_update =0;
		
		Statement stmt_update_amount=null;
		try
		{
	
	   stmt_update_amount =(Statement) DbConnect.getConn().createStatement();

		 rs_update = stmt_update_amount.executeUpdate(update_query);
		}catch(Exception e)
		{
			System.out.print(e.toString());
	
		}finally{

			
			//check for prepared statement before closing
			if(stmt_update_amount!=null){
				DbConnect.Close_Stetement(stmt_update_amount);
			}

			}
		
		if(rs_update == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
		 

	  
	  
  }
  
  
	

}
