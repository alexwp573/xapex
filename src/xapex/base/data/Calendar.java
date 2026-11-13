package xapex.base.data;
			// maja i alex <3

import java.time.LocalDateTime;

public class Calendar{
	
	public static LocalDateTime NOW = LocalDateTime.now();
	public static int YEAR = Calendar.NOW.getYear();
	public static int MONTH = Calendar.NOW.getMonthValue();
	public static int DAY = Calendar.NOW.getDayOfMonth();
	
	public static class Date{
		
	}
	
	public static boolean exists(int y, int m, int d){
		if(y < Calendar.YEAR - 250 || y > Calendar.YEAR + 250) return false;
		switch(m){
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:{
				if(d > 31 || d < 1) return false;
				return true;}
			case 4: case 6: case 9: case 11:{
				if(d > 30 || d < 1) return false;
				return true;}
			case 2:{
				if(y % 4 != 0 && d > 28) return false;
				else{
					
				}
				}
				break;
			default:{
				return false;}
		}
	}
	
}
