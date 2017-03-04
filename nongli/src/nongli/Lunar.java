package nongli;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Lunar {
	/**
	 * 转换的结果集.year .month .day .isLeap .yearCyl .dayCyl .monCyl
	 */
	private int result[];
	private Calendar calendar;
	private static int[] lunarInfo = { 0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0,
			0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970,
			0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50,
			0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4,
			0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0,
			0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57,
			0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0,
			0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0,
			0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552,
			0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0,
			0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65,
			0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0,
			0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 };
	private final static int[] solarTermInfo = { 0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551,
			218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532,
			504758 };
	public final static String[] solarTerm = { "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
			"小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };
	private static int[] solarMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static String[] Gan = { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
	private static String[] Zhi = { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };
	private static String[] Animals = { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
	private static int[] sTermInfo = { 0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072,
			240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758 };
	private static String[] nStr1 = { "日", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
	private static String[] nStr2 = { "初", "十", "廿", "卅", "　" };
	private static String[] monthNong = { "正", "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" };
	private static String[] yearName = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	
	public Lunar() {
		this.calendar = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
		convert();// 转换日期
	}

	public Lunar(int year, int month, int day) {
		calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		convert();// 转换日期
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @param hourOfDay
	 *            24小时制(0-23)
	 * @param minute
	 */
	public Lunar(int year, int month, int day, int hourOfDay, int minute) {
		calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, hourOfDay, minute);
		convert();// 转换日期
	}

	public Lunar(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		convert();// 转换日期
	}

	public Lunar(Calendar calendar) {
		this.calendar = calendar;
		convert();// 转换日期
	}

	/**
	 * 农历年份的总天数
	 * 
	 * @param year
	 *            农历年份
	 * @return
	 */
	private static int totalDaysOfYear(int year) {
		int sum = 348; // 29*12
		for (int i = 0x8000; i > 0x8; i >>= 1) {
			sum += (lunarInfo[year - 1900] & i) == 0 ? 0 : 1; // 大月+1天
		}
		return (sum + leapDays(year)); // +闰月的天数
	}

	/**
	 * 农历 year年闰月的天数
	 * 
	 * @param year
	 * @return
	 */
	private static int leapDays(int year) {
		int result = 0;
		if (leapMonth(year) != 0) {
			result = (lunarInfo[year - 1900] & 0x10000) == 0 ? 29 : 30;
		}
		return result;
	}

	/**
	 * 传农历 year年闰哪个月 1-12 , 没闰传回 0
	 * 
	 * @param 农历年份
	 * @return
	 */
	private static int leapMonth(int year) {
		return (lunarInfo[year - 1900] & 0xf);
	}

	/**
	 * 农历 y年m月的总天数
	 * 
	 * @param y
	 * @param m
	 * @return
	 */
	private static int monthDays(int y, int m) {
		return ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0 ? 29 : 30);
	}

	/**
	 * 将传入的日期转换为农历
	 * 
	 * @param date
	 *            公历日期
	 */
	private void convert() {
		// 基准时间 1900-01-31是农历1900年正月初一
		Calendar baseCalendar = Calendar.getInstance();
		baseCalendar.set(1900, 0, 31, 0, 0, 0); // 1900-01-31是农历1900年正月初一
		Date baseDate = baseCalendar.getTime();
		// 偏移量（天）
		int offset = (int) ((calendar.getTimeInMillis() - baseDate.getTime()) / 86400000); // 天数(86400000=24*60*60*1000)
		// 基准时间在天干地支纪年法中的位置
		int monCyl = 14; // 1898-10-01是农历甲子月
		int dayCyl = offset + 40; // 1899-12-21是农历1899年腊月甲子日

		// 得到年数
		int i;
		int temp = 0;
		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = totalDaysOfYear(i); // 农历每年天数
			offset -= temp;
			monCyl += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			monCyl -= 12;
		}

		int year = i; // 农历年份
		int yearCyl = i - 1864; // 1864年是甲子年

		int leap = leapMonth(i); // 闰哪个月
		boolean isLeap = false;
		int j;
		for (j = 1; j < 13 && offset > 0; j++) {
			// 闰月
			if (leap > 0 && j == (leap + 1) && isLeap == false) {
				--j;
				isLeap = true;
				temp = leapDays(year);
			} else {
				temp = monthDays(year, j);
			}
			// 解除闰月
			if (isLeap == true && j == (leap + 1))
				isLeap = false;
			offset -= temp;
			if (isLeap == false)
				monCyl++;
		}
		if (offset == 0 && leap > 0 && j == leap + 1)
			if (isLeap) {
				isLeap = false;
			} else {
				isLeap = true;
				--j;
				--monCyl;
			}
		if (offset < 0) {
			offset += temp;
			--j;
			--monCyl;
		}
		int month = j; // 农历月份
		int day = offset + 1; // 农历天
		result = new int[] { year, month, day, isLeap ? 1 : 0, yearCyl, monCyl, dayCyl };
	}

	/**
	 * 获取偏移量对应的干支, 0=甲子
	 * 
	 * @param num
	 *            偏移量（年or月or日）
	 * @return
	 */
	private static String cyclical(int num) {
		return (Gan[num % 10] + Zhi[num % 12]);
	}

	/**
	 * 中文日期
	 * 
	 * @param d
	 * @return
	 */
	private static String chineseDay(int day) {
		String result;
		switch (day) {
		case 10:
			result = "初十";
			break;
		case 20:
			result = "二十";
			break;
		case 30:
			result = "三十";
			break;
		default:
			result = nStr2[(int) (day / 10)];// 取商
			result += nStr1[day % 10];// 取余
		}
		return (result);
	}

	/**
	 * 大写年份
	 * 
	 * @param y
	 * @return
	 */
	private static String chineseYear(int y) {
		String s = " ";
		int d;
		while (y > 0) {
			d = y % 10;
			y = (y - d) / 10;
			s = yearName[d] + s;
		}
		return (s);
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.M.d EEEEE");

	/**
	 * 输出格式：2015.07.04 周六 乙未[羊]年 壬午月 辛巳日
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public String getLunarDate() {
		String s = sdf.format(calendar.getTime()) + " ";
		s += cyclical(result[4]) + "[" + Animals[(result[0] - 4) % 12] + "]年 ";
		s += cyclical(result[5]) + "月 ";
		s += cyclical(result[6]) + "日";
		return s;
	}
	
	/**
	 * 输出格式：乙未年
	*/
	public String getLunarYear() {
		String s = cyclical(result[4]) + "年";
		return s;
	}
	
	
	private static SimpleDateFormat gongli = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 输出格式：2015-07-04
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public String getGongLiDate() {
		String s = gongli.format(calendar.getTime());
		return s;
	}

	/**
	 * 输出格式：五月十九
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public String getLunarDay() {
		return (result[3] == 1 ? "闰" : "") + monthNong[result[1]] + "月" + chineseDay(result[2]);
	}

	/**
	 * 获取时辰，输出格式：戊子时
	 * 
	 * @return
	 */
	public String getLunarTime() {
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int timeOffset = (result[6] % 10) * 24 + hour;
		return Gan[((timeOffset + 1) / 2) % 10] + Zhi[((hour + 1) / 2) % 12] + "时";
	}

	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * 后一天
	 */
	public void nextDay() {
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		convert();
	}

	/**
	 * 前一天
	 */
	public void preDay() {
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		convert();
	}

	/**
	 * 取 Date 对象中用全球标准时间 (UTC) 表示的日期
	 * 
	 * @param date
	 *            指定日期
	 * @return UTC 全球标准时间 (UTC) 表示的日期
	 */
	public static synchronized int getUTCDay(Date date) {
		Lunar.makeUTCCalendar();
		synchronized (utcCal) {
			utcCal.clear();
			utcCal.setTimeInMillis(date.getTime());
			return utcCal.get(Calendar.DAY_OF_MONTH);
		}
	}

	private static GregorianCalendar utcCal = null;

	private static synchronized void makeUTCCalendar() {
		if (Lunar.utcCal == null) {
			Lunar.utcCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		}
	}

	/**
	 * 返回全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数。
	 * 
	 * @param y
	 *            指定年份
	 * @param m
	 *            指定月份
	 * @param d
	 *            指定日期
	 * @param h
	 *            指定小时
	 * @param min
	 *            指定分钟
	 * @param sec
	 *            指定秒数
	 * @return 全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数
	 */
	public static synchronized long UTC(int y, int m, int d, int h, int min, int sec) {
		Lunar.makeUTCCalendar();
		synchronized (utcCal) {
			utcCal.clear();
			utcCal.set(y, m, d, h, min, sec);
			return utcCal.getTimeInMillis();
		}
	}

	/**
	 * 返回公历年节气的日期
	 * 
	 * @param solarYear
	 *            指定公历年份(数字)
	 * @param index
	 *            指定节气序号(数字,0从小寒算起)
	 * @return 日期(数字,所在月份的第几天)
	 */
	public static Date getSolarTermCalendar(int solarYear, int index) {
		long l = (long) 31556925974.7 * (solarYear - 1900) + solarTermInfo[index] * 60000L;
		l = l + Lunar.UTC(1900, 0, 6, 2, 5, 0);
		return new Date(l);
	}

	/**
	 * 返回公历年节气的日期
	 * 
	 * @param solarYear
	 *            指定公历年份(数字)
	 * @param index
	 *            指定节气序号(数字,0从小寒算起)
	 * @return 日期(数字,所在月份的第几天)
	 */
	private static int getSolarTermDay(int solarYear, int index) {

		return Lunar.getUTCDay(getSolarTermCalendar(solarYear, index));
	}
	
	public int getlunarInfoSize(){
		return lunarInfo.length;
	}

	public String getTermString() {
		// 二十四节气
		String termString = "";
		int solarYear = calendar.get(Calendar.YEAR);
		int solarMonth = calendar.get(Calendar.MONTH);
		int solarDay = calendar.get(Calendar.DAY_OF_MONTH);

		if (Lunar.getSolarTermDay(solarYear, solarMonth * 2) == solarDay) {
			termString = Lunar.solarTerm[solarMonth * 2];
		} else if (Lunar.getSolarTermDay(solarYear, solarMonth * 2 + 1) == solarDay) {
			termString = Lunar.solarTerm[solarMonth * 2 + 1];
		}
		return termString;
	}
	
	//节气不为空
	public String getMyTermString() {
		// 二十四节气
		String termString = "";
		int solarYear = calendar.get(Calendar.YEAR);
		int solarMonth = calendar.get(Calendar.MONTH);
		int solarDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		//将自然月按节气节点分割成三段，判断日期是落在哪一段时间内
		if (Lunar.getSolarTermDay(solarYear, solarMonth * 2 + 1) <= solarDay) {
			termString = Lunar.solarTerm[solarMonth * 2 + 1];
		} else {
			if (Lunar.getSolarTermDay(solarYear, solarMonth * 2) <= solarDay) {
				termString = Lunar.solarTerm[solarMonth * 2];
			} else {
				if (solarMonth * 2 - 1 < 0){
					termString = Lunar.solarTerm[solarMonth * 2 - 1 + Lunar.solarTerm.length];
				} else{
					termString = Lunar.solarTerm[solarMonth * 2 - 1];
				}
			}
		} 
		return termString;
	}
	
	
    /*
	//计算节气起始时间点，若当天日期内不存在两个节气，则返回时间点为空
	public String getMyTermBeginTime() {
		// 二十四节气
		String timeString = "";
		int n = 0;
		
		int solarYear = calendar.get(Calendar.YEAR);
		int solarMonth = calendar.get(Calendar.MONTH);
		int solarDay = calendar.get(Calendar.DAY_OF_MONTH);

		if (Lunar.getSolarTermDay(solarYear, solarMonth * 2) == solarDay) {
			//termString = Lunar.solarTerm[solarMonth * 2];
			n = solarMonth * 2;
		} else if (Lunar.getSolarTermDay(solarYear, solarMonth * 2 + 1) == solarDay) {
			//termString = Lunar.solarTerm[solarMonth * 2 + 1];
			n = solarMonth * 2 + 1;
		} else {
			return "";
		}
		
		Date offDate = getSolarTermCalendar(solarYear, n);
		
		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		timeString = tf.format(offDate);
		
		return timeString;
	}
	*/
	
	public static void main(String[] args) {
		//Lunar Lunar = new Lunar(new Date());
		Lunar Lunar = new Lunar(2016, 4, 4);
		//System.out.println(Lunar.getLunarDate());
		System.out.println(Lunar.getGongLiDate());
		System.out.println(Lunar.getLunarYear() + " " +Lunar.getLunarDay());
		//System.out.println(Lunar.getLunarDay());
		//System.out.println(Lunar.getLunarTime());
		System.out.println(Lunar.getTermString());
	}

}
