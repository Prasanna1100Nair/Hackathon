


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Generate_Session {
	
	 public static String genSession()
	    {

	    	Calendar calendar=Calendar.getInstance();

			Date date= new Date();
		 long time = date.getTime();
		 
		
	        String time_in_milli=String.valueOf(time);
	        
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date current_date = new Date();
		
		String a=dateFormat.format(date);

		StringBuilder sb=new StringBuilder();
		
		
		char[] b= a.toCharArray();
		for(int i=1; i<10;i++)
		{
		sb.append(b[i]);
		
		if(i==3||i==6||i==9||i==12||i==15)
			i++;
			
			try {
				new Thread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		sb.append(time_in_milli.substring(0, 13));
		
		
			String return_Session_ID=sb.toString();
			
			
			return return_Session_ID ;
			// TODO Auto-generated method stub
	    
	    
	    }
}
