

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject json      = new JSONObject();
		JSONArray  addresses = new JSONArray();
		JSONObject address;
		try
		{
               address = new JSONObject();
		       address.put("CustomerName"     , "Decepticons");
		       address.put("AccountId"        , "1999");
		       address.put("SiteId"           , "1888");
		       address.put("Number"            , "7");
		       address.put("Building"          , "StarScream Skyscraper" );
		       address.put("Street"            , "Devestator Avenue");
		       address.put("City"              , "Megatron City" );
		       address.put("ZipCode"          , "ZZ00 XX1" );
		       address.put("Country"           , "CyberTron");
		       addresses.add(address);
		   json.put("Addresses", addresses);
		}
		catch (Exception jse)
		{ 
       System.out.println("Exception");
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

}
