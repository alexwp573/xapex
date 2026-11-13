package xapex.base.data;
			// maja i alex <3

import java.time.LocalDateTime;

public class Calendar{
	
	public static LocalDateTime NOW = LocalDateTime.now();
		public static int year = Calendar.NOW.getYear();
		public static int month = Calendar.NOW.getMonthValue();
		public static int day = Calendar.NOW.getDayOfMonth();
	
	public static final Boolean YEAR = false;
	public static final Boolean MONTH = true;
	public static final Boolean DAY = null;
	
	public static class DateDoesNotExistsException extends Exception{
		
	}
	
	public static class Date{
		
	}
	
	public static Date of(int y, int m, int d) throws DateDoesNotExistsException{
		if(y < Calendar.year - 250 || y > Calendar.year + 250) return null;
		switch(m){
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:{
				if(d > 31 || d < 1) return null;
				return true;}
			case 4: case 6: case 9: case 11:{
				if(d > 30 || d < 1) return null;
				return true;}
			case 2:{
				if(y % 4 != 0 && d > 28) return null;
				else{
					
				}
				}
				break;
			default:{
				return null;}
		}
	}
	
}
