package xapex.base.data;
			// maja i alex <3

import java.time.LocalDateTime;

public class Calendar{
	
	private static LocalDateTime LDT = LocalDateTime.now();
			private static int YEAR = Calendar.LDT.getYear();
				public static int year(){return Calendar.YEAR;}
			private static int MONTH = Calendar.LDT.getMonthValue();
				public static int month(){return Calendar.MONTH;}
			private static int DAY = Calendar.LDT.getDayOfMonth();
				public static int day(){return Calendar.DAY;}
		private static Date NOW;
			public static Date now(){
				try{Calendar.NOW = Calendar.dateOf(Calendar.YEAR, Calendar.MONTH, Calendar.DAY);}
				catch(Calendar.DateDoesNotExistsException ddnee){throw new RuntimeException(ddnee);}
				return Calendar.NOW;}
	
	public static class DateDoesNotExistsException extends Exception{
		public final int DAY;
		public final int MONTH;
		public final int YEAR;
		public DateDoesNotExistsException(int d, int m, int y){
			this.DAY = d;
			this.MONTH = m;
			this.YEAR = y;
		}
		public String toString(){
			return "The date: " + this.YEAR + "/" + this.MONTH + "/" + this.DAY + " does not exist!";
		}
	}
	
	public static class Date{
		
		public final int DAY;
		public final int MONTH;
		public final int YEAR;
		public final int CENTRY;
		public final int FULL_YEAR;
		
		Date(int d, int m, int y){
			this.DAY = d;
			this.MONTH = m;
			this.YEAR = y % 100;
			this.CENTRY = y / 100;
			this.FULL_YEAR = y;
		}
		
		private String fill2(int d){
			if(d < 10){
				return "0" + d;
			}
			return String.valueOf(d);
		}
		private StringBuilder appendDatePart(StringBuilder sb, String data){
			switch(data){
				case "dd": case "DD":
					sb.append(this.fill2(this.DAY));
					break;
				case "mm": case "MM":
					sb.append(this.fill2(this.MONTH));
					break;
				case "yy": case "YY":
					sb.append(this.fill2(this.YEAR));
					break;
				case "cc": case "CC":
					sb.append(this.CENTRY);
					break;
				case "yyyy": case "YYYY":
					sb.append(this.FULL_YEAR);
					break;
			}
			return sb;
		}
		
		public String toString(String format){
			StringBuilder sb = new StringBuilder();
			String[] splitedFormat = format.split("/");
			for(int i = 0; i < splitedFormat.length - 1; i++){
				this.appendDatePart(sb, splitedFormat[i]);
				sb.append("/");
			}
			this.appendDatePart(sb, splitedFormat[splitedFormat.length - 1]);
			return sb.toString();
		}
		public String toString(){return this.toString("dd/mm/yyyy");}
	}
	
	public static Date dateOf(int y, int m, int d) throws DateDoesNotExistsException{
		if(y < Calendar.YEAR - 250 || y > Calendar.YEAR + 250) throw new DateDoesNotExistsException(d, m, y);
		switch(m){
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:{
					if(d > 31 || d < 1) throw new DateDoesNotExistsException(d, m, y);
				}
				break;
			case 4: case 6: case 9: case 11:{
					if(d > 30 || d < 1) throw new DateDoesNotExistsException(d, m, y);
				}
				break;
			case 2:{
					if(y % 4 != 0 && d > 28) throw new DateDoesNotExistsException(d, m, y);
					else{
						if(y % 400 == 0 && d > 29) throw new DateDoesNotExistsException(d, m, y);
						if(y % 100 == 0 && y % 400 != 0 && d > 28) throw new DateDoesNotExistsException(d, m, y);
						if(y % 4 == 0 && d > 29) throw new DateDoesNotExistsException(d, m, y);
					}
				}
				break;
			default: throw new DateDoesNotExistsException(d, m, y);
		}
		return new Date(d, m, y);
	}
	
}


// KOCHAM CIE MAJUUUU
