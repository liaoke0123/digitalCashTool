package com.bishijie.alert.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 各种日期获取
 * 
 * @author liaoke
 *
 */
public class TimeUtils {
	// 用来全局控制 上一周，本周，下一周的周数变化
	private static int weeks = 0;
	private int MaxDate;// 一月最大天数
	private static int MaxYear;// 一年最大天数

	public static String TIME_FULL = "yyyy-MM-dd HH:mm:ss";
	public static String TIME_DATE = "yyyy-MM-dd";
	public static String TIME_MONTH = "yyyy-MM";
	public static String TIME_YEAR = "yyyy";
	public static String TIME_TIME = "HH:mm:ss";

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// System.out.println("获取当天日期:" + TimeUtils.getNowTime("yyyy-MM-dd"));
	// System.out.println("获取本周一日期:" + TimeUtils.getMondayOFWeek());
	// System.out.println("获取本周日的日期~:" + TimeUtils.getCurrentWeekday());
	// System.out.println("获取上周一日期:" + TimeUtils.getPreviousWeekday());
	// System.out.println("获取上周日日期:" + TimeUtils.getPreviousWeekSunday());
	// System.out.println("获取下周一日期:" + TimeUtils.getNextMonday());
	// System.out.println("获取下周日日期:" + TimeUtils.getNextSunday());
	// System.out.println("获得相应周的周六:" + TimeUtils.getNowTime("yyyy-MM-dd"));
	// System.out.println("获取本月第一天日期:" + TimeUtils.getFirstDayOfMonth());
	// System.out.println("获取本月最后一天日期:" + TimeUtils.getDefaultDay());
	// System.out.println("获取上月第一天日期:" + TimeUtils.getPreviousMonthFirst());
	// System.out.println("获取上月最后一天的日期:" + TimeUtils.getPreviousMonthEnd());
	// System.out.println("获取下月第一天日期:" + TimeUtils.getNextMonthFirst());
	// System.out.println("获取下月最后一天日期:" + TimeUtils.getNextMonthEnd());
	// System.out.println("获取本年的第一天日期:" + TimeUtils.getCurrentYearFirst());
	// System.out.println("获取本年最后一天日期:" + TimeUtils.getCurrentYearEnd());
	// System.out.println("获取去年的第一天日期:" + TimeUtils.getPreviousYearFirst());
	// System.out.println("获取去年的最后一天日期:" + TimeUtils.getPreviousYearEnd());
	// System.out.println("获取明年第一天日期:" + TimeUtils.getNextYearFirst());
	// System.out.println("获取明年最后一天日期:" + TimeUtils.getNextYearEnd());
	// System.out.println("获取本季度第一天到最后一天:" + TimeUtils.getThisSeasonTime(11));
	// System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:" +
	// TimeUtils.getTwoDay("2008-12-1", "2008-9-29"));
	// TimeUtils.convertWeekByDate("2014-10-21");
	// System.out.println(TimeUtils.Stingmillistoformat("1463285231691",
	// "yyyy-MM-dd HH:mm:ss"));
	// System.out.println(TimeUtils.Stingformattomillis("2016-05-15 12:07:11",
	// "yyyy-MM-dd HH:mm:ss"));
	// }

	/**
	 * 时间段
	 * @author liaoke
	 *
	 */
	public static class TimeSlot{
		private Date starttime;
		private Date endtime;
		public Date getStarttime() {
			return starttime;
		}
		public void setStarttime(Date starttime) {
			this.starttime = starttime;
		}
		public TimeSlot(Date starttime, Date endtime) {
			super();
			this.starttime = starttime;
			this.endtime = endtime;
		}
		public Date getEndtime() {
			return endtime;
		}
		public void setEndtime(Date endtime) {
			this.endtime = endtime;
		}
		
	}
	
	
	
	
	
	/**
	 * 时间毫秒字符串转换格式字符串
	 * 
	 * @param mills
	 * @param format
	 * @return
	 */
	public static String Stringmillistoformat(String mills, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date d = new Date(Long.parseLong(mills));
		String date = dateFormat.format(d);

		return date;
	}

	/**
	 * 格式字符转换成时间毫秒字符串
	 * 
	 * @param formatString
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static Long Stringformattomillis(String formatString, String format) throws ParseException {

		Calendar c = Calendar.getInstance();

		
			c.setTime(new SimpleDateFormat(format).parse(formatString));
			return c.getTimeInMillis() ;
	

	
	}
	
	/**
	 * 設定時間是否到期
	 * 到期返回true
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public  static boolean isTimeOut(String formarStringTime,String formart) throws NumberFormatException, ParseException{
		long stringTime=Long.valueOf(Stringformattomillis(formarStringTime,formart));
		if(System.currentTimeMillis() - stringTime>0 ){
			return true;
		}
		return false;
	}
	/**
	 * 设定时间是否过期
	 * @param time
	 * @return
	 */
	public static boolean isTimeOut(Date time){
		if(System.currentTimeMillis() - time.getTime()>0 ){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * 是否超時
	 * @param time
	 * @param timeout
	 * @return
	 */
	public static boolean isTimeOut(long time, long timeout) {
		if (System.currentTimeMillis() - time > timeout) {
			return true;
		}
		return false;
	}

	public static String[] convertWeekByDate(String date) {
		Date time = string2Date(date, "yyyy-MM-dd");
		String res[] = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String imptimeBegin = sdf.format(cal.getTime());
		System.out.println("所在周星期一的日期：" + imptimeBegin);
		res[0] = imptimeBegin;
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		res[1] = imptimeEnd;
		System.out.println("所在周星期日的日期：" + imptimeEnd);
		return res;
	}

	/**
	 * 字符串转日期
	 * 
	 * @param date
	 * @param format
	 *            "yyyy-MM-dd"
	 * @return
	 */
	public static Date string2Date(String date, String format) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(format);
		try {
			return myFormatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串日期格式
	 * 
	 * @param date
	 *            "yyyy-MM-dd"
	 * @return
	 */
	public static String stringFormat(String date, String format1, String format2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(format1);
		try {
			Date time = myFormatter.parse(date);
			myFormatter = new SimpleDateFormat(format2);
			return myFormatter.format(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	public enum DateType{
		Year,Month,Day
	}
	/**
	 * 获取今天 的 年 月 日 时间错
	 * @param time
	 * @param type
	 * @return
	 */
	public static Date getYearOrMonthOrDay(Date time, DateType type) {
		String format2 =null;
		switch (type) {
		case Year:
			format2="yyyy";
			break;
		case Month:
			format2="yyyy-MM";
			break;
		case Day:
			format2="yyyy-MM-dd";
			break;
		}
		
		String format1= "yyyy-MM-dd";
		SimpleDateFormat myFormatter = new SimpleDateFormat(format1);
		myFormatter = new SimpleDateFormat(format2);
		return string2Date( myFormatter.format(time), format2);
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = TimeUtils.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour 中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 两个时间之间的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals("")) {
			return 0;
		}
		if (date2 == null || date2.equals("")) {
			return 0;
		}
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1 号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1 号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期
	public static String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取当天时间
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public static String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得相应周的周六的日期
	public String getSaturday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public static String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public static String getPreviousWeekday() {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public static String getNextMonday() {
		weeks++;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public static String getNextSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	// 获得上月最后一天的日期
	public static String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月第一天的日期
	public static String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public static String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public static String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public static String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本年有多少天
	public int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public static String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public static String getPreviousYearEnd() {
		weeks--;
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	// 获得本季度
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month
				+ "-" + end_days;
		return seasonDate;
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
}
