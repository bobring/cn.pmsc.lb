package nongli;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GregorianCalendar begin = new GregorianCalendar(2017, 0, 23);
		GregorianCalendar end = new GregorianCalendar(2017, 1, 7);
		
		SimpleDateFormat gongli = new SimpleDateFormat("yyyy-MM-dd");
		
		while(begin.before(end)) {
			LunarCalendar MyLunar = new LunarCalendar(begin.getTime());
			
			System.out.println(gongli.format(begin.getTime()) + 
					" " + MyLunar.getGanZhiDateString() + 
					" " + MyLunar.getMycnEraYearString());
			begin.add(Calendar.DAY_OF_YEAR, 1);
		}
	}

}
