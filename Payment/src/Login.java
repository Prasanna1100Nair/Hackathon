

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;



/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Boolean valid=true;
		 String username = null;
			String password = null;
			String balance = null;
			String name=null;
			String received_username=request.getParameter("UserName");
			String received_password=request.getParameter("PassWord");
		System.out.println(received_password+"   "+received_username);
			
			 
			try
		{
		Map value =	new DatabaseAccess().readuserdata(received_username);

		if(value.containsKey("Username"))
		{
	System.out.println(value.get("Username")+"  "+value.get("Password")+"  "+value.get("Balance"));
			username= (String) value.get("Username");
			password=(String) value.get("Password");
			balance= (String) value.get("Balance");
			name = (String) value.get("Name");
		}
		else
		{
			valid=false;
		}
		}
		catch(Exception e)
		{
			valid=false;
		}

		
		
		    // put some value pairs into the JSON object .
		   // finally output the json string       
		
		if(received_username.equals(username))
		{
			System.out.println("Match 1");
			if(received_password.equals(password))
			{
				System.out.println("Match 2");
					
			}
			else
			{
		    valid= false;		
			}
		}
		else
		{
			valid =false;
		}
		
		
		
		
		JSONObject json      = new JSONObject();
	
		
		/*	
	  try
		{
		   int count = 15;

		   for (int i=0 ; i<count ; i++)
		   {
		       address = new JSONObject();
		       address.put("CustomerName"     , "Decepticons" + i);
		       address.put("AccountId"        , "1999" + i);
		       address.put("SiteId"           , "1888" + i);
		       address.put("Number"            , "7" + i);
		       address.put("Building"          , "StarScream Skyscraper" + i);
		       address.put("Street"            , "Devestator Avenue" + i);
		       address.put("City"              , "Megatron City" + i);
		       address.put("ZipCode"          , "ZZ00 XX1" + i);
		       address.put("Country"           , "CyberTron" + i);
		       addresses.add(address);
		   }
		   json.put("Addresses", addresses);
		}
		catch (Exception jse)
		{ 
System.out.println("Exception Occured");
		}
*/
	
		json.put("username", received_username);
		json.put("password", received_password);
		if(valid)
		{
			json.put("access", "granted");
			json.put("balance", balance);
			json.put("name", name);
			}
		else
		{
			json.put("access", "deny");
			}
		response.setContentType("application/json");
		response.getWriter().write(json.toString());
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
@Override
public void destroy() {
	// TODO Auto-generated method stub
	super.destroy();



}
}
