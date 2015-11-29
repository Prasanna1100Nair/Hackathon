

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject json      = new JSONObject();
		
		 
		
		String username = null;
			String password = null;
			String balance = null;
			String name=null;
			Boolean valid=true;
		String sender =request.getParameter("Sender");
		String receiver =request.getParameter("Receiver");
		String amount = request.getParameter("Amount");
		String transaction_id = Generate_Session.genSession();
        
		
		if(updateAmount(transaction_id, sender,receiver,amount))
		{
			try
		{
		Map value =	new DatabaseAccess().readuserdata(sender);

		if(value.containsKey("Username"))
		{
	System.out.println(value.get("Username")+"  "+value.get("Password")+"  "+value.get("Balance"));
			username= (String) value.get("Username");
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

			if(valid)
			{
				json.put("status", "transfered successfully");
				json.put("balance", balance);
				json.put("name", name);
			
			}
		}
		else
		{
			
			json.put("status", "Error in transfer");
		
		}
		
		response.setContentType("text/json");
		response.getWriter().write(json.toString());
		
 	}

	
	public boolean  updateAmount(String transaction_id, String sender, String receiver, String amount)
	{
		Boolean resultimplemented= new DatabaseAccess().insertTransactionToDatabase(transaction_id, sender, receiver, amount);
		if(resultimplemented)
		{
return true;
		
		}
		else
		{
			return false;
			}
		
	}
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
}
