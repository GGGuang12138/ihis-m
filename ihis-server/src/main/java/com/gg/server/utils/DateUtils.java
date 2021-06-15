package com.gg.server.utils;

/**
 * @Description:
 *
 * @author pengwei
 *
 * @date 2015年12月9日
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.*;


public class DateUtils {

	private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * 一秒钟的毫秒数
	 */
	public static final int ONE_SECOND_MILLISECOND = 1000;

	/**
	 * 一分钟的毫秒数
	 */
	public static final int ONE_MINUTE_MILLISECOND = 60 * ONE_SECOND_MILLISECOND;

	/**
	 * 一小时的毫秒数
	 */
	public static final int ONE_HOUR_MILLISECOND = 60 * ONE_MINUTE_MILLISECOND;

	/**
	 * 一天的毫秒数
	 */
	public static final int ONE_DAY_MILLISECOND = 24 * ONE_HOUR_MILLISECOND;

	/**
	 * 一分钟的秒数
	 */
	public static final int ONE_MINUTE_SECOND = ONE_MINUTE_MILLISECOND / ONE_SECOND_MILLISECOND;

	/**
	 * 1小时的秒数
	 */
	public static final long ONE_HOUR_SECOND = ONE_HOUR_MILLISECOND / ONE_SECOND_MILLISECOND;

	/**
	 * 1天的秒数
	 */
	public static final long ONE_DAY_SECOND = ONE_DAY_MILLISECOND / ONE_SECOND_MILLISECOND;

	/**
	 * 星期---中文
	 */
	public static final String[] WEEK_CN = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };


	/**
	 * 比较两个时间是不是同一周
	 * @param time1
	 * @param time2
	 * @param firstDayOfWeek 周的第一天设置值，{@see Calendar#DAY_OF_WEEK}
	 * @return
	 */
	public static boolean isSameWeek(Date time1, Date time2, final int firstDayOfWeek){
		return calcWeekIntervals(time1, time2, firstDayOfWeek) == 0;
	}

	private static final List<Integer> DAY_IN_WEEK_LIST = new ArrayList<Integer>(Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY));


	/**
	 * 计算两个时间相隔的周数
	 * @param time1
	 * @param time2
	 * @param firstDayOfWeek firstDayOfWeek 周的第一天设置值，{@see Calendar#DAY_OF_WEEK}
	 * @return
	 */
	public static int calcWeekIntervals(final Date time1, final Date time2, final int firstDayOfWeek){
		int firstDayIndex = DAY_IN_WEEK_LIST.indexOf(firstDayOfWeek);

		Calendar compareCalendar = Calendar.getInstance();

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time1);

		compareCalendar.setTime(time1);

		int index1 = DAY_IN_WEEK_LIST.indexOf(calendar1.get(Calendar.DAY_OF_WEEK));

		int diff1 = index1 - firstDayIndex;
		if(diff1 < 0){
			compareCalendar.add(Calendar.DAY_OF_YEAR, DAY_IN_WEEK_LIST.size() - Math.abs(diff1));
		} else {
			compareCalendar.add(Calendar.DAY_OF_YEAR,  - Math.abs(diff1));
		}
		compareCalendar.set(Calendar.HOUR_OF_DAY, 0);
		compareCalendar.set(Calendar.MINUTE, 0);
		compareCalendar.set(Calendar.SECOND, 0);
		compareCalendar.set(Calendar.MILLISECOND, 0);

		calendar1.setTime(compareCalendar.getTime());
		if(calendar1.before(compareCalendar)){
			calendar1.add(Calendar.DAY_OF_YEAR, -DAY_IN_WEEK_LIST.size());
		}

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(time2);

		compareCalendar.setTime(time2);

		int index2 = DAY_IN_WEEK_LIST.indexOf(calendar2.get(Calendar.DAY_OF_WEEK));

		int diff2 = index2 - firstDayIndex;
		if(diff2 < 0){
			compareCalendar.add(Calendar.DAY_OF_YEAR, DAY_IN_WEEK_LIST.size() - Math.abs(diff2));
		} else {
			compareCalendar.add(Calendar.DAY_OF_YEAR,  - Math.abs(diff2));
		}
		compareCalendar.set(Calendar.HOUR_OF_DAY, 0);
		compareCalendar.set(Calendar.MINUTE, 0);
		compareCalendar.set(Calendar.SECOND, 0);
		compareCalendar.set(Calendar.MILLISECOND, 0);

		calendar2.setTime(compareCalendar.getTime());
		if(calendar2.before(compareCalendar)){
			calendar2.add(Calendar.DAY_OF_YEAR, -DAY_IN_WEEK_LIST.size());
		}

		return (int)Math.abs((calendar2.getTime().getTime() - calendar1.getTime().getTime()) / (DAY_IN_WEEK_LIST.size() * 24 * 3600 * 1000));
	}

	/**
	 *  startTime的毫秒数
	 * @param timeContext  通过解析时间串 eg 18:00:00得到的时间上下文
	 * @return
	 */

	/**
	 * 当前时间 距离  当前日期的特殊时间点 的毫秒数
	 * @param time
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static long getTodayOffsetMillis(Date time, int hour, int minute, int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);

		long miliis = cal.getTimeInMillis();

		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);

		return Math.max(miliis - cal.getTimeInMillis(), 0);
	}

	/**
	 * 把 时间点字符串   转化成为  当前日期(年月日)的  时间
	 * @param startTime  eg 18:00:00
	 * @return
	 */
	public static Date getTimeByString(String startTime){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		String[] timeArray = startTime.trim().split(":");
		if(timeArray.length == 3){
			int hour = Integer.valueOf(timeArray[0]);
			cal.set(Calendar.HOUR_OF_DAY, hour>24? 24:hour);

			int min= Integer.valueOf(timeArray[1]);
			cal.set(Calendar.MINUTE, min>60? 60:min);

			int sec = Integer.valueOf(timeArray[2]);
			cal.set(Calendar.SECOND, sec>60? 60:sec);
		}
		else{
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		}
		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 判断两个时间是不是同一个月
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameMonth(Date time1, Date time2){
		Calendar cal = Calendar.getInstance();
		cal.setTime(time1);
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH);

		cal.setTime(time2);
		int year2 = cal.get(Calendar.YEAR);
		int month2 = cal.get(Calendar.MONTH);

		return year1 == year2 && month1 == month2;
	}

	/**
	 * 获取下一个月
	 * @param date
	 * @return
	 */
	public static Date getNextMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		return cal.getTime() ;
	}

	/**
	 * 获取某个月最后一天
	 * @param date
	 * @return
	 */
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return cal.getTime() ;
	}

    /**
     * 获取某个月第一天
     * @param date
     * @return
     */
    public static Date firstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        return cal.getTime() ;
    }

	/**
	 * 获取周的第一天
	 * @param  firstDayOfWeek 	周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
	 * @param  time 			指定时间，为 null 代表当前时间
	 * @return {@link Date}		周的第一天
	 */
	public static Date firstTimeOfWeek(int firstDayOfWeek, Date time) {
		Calendar cal = Calendar.getInstance();
		if (time != null) {
			cal.setTime(time);
		}
		cal.setFirstDayOfWeek(firstDayOfWeek);
		int day = cal.get(DAY_OF_WEEK);

		if (day == firstDayOfWeek) {
			day = 0;
		} else if (day < firstDayOfWeek) {
			day = day + (7 - firstDayOfWeek);
		} else if (day > firstDayOfWeek) {
			day = day - firstDayOfWeek;
		}

		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);

		cal.add(DATE, -day);
		return cal.getTime();
	}

	/**
	 * 检查指定日期是否今天(使用系统时间)
	 * @param date 被检查的日期
	 * @return
	 */
	public static boolean isToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.add(DATE, 1);
		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);
		Date end = cal.getTime(); // 明天的开始
		cal.add(MILLISECOND, -1);
		cal.add(DATE, -1);
		Date start = cal.getTime(); // 昨天的结束
		return date.after(start) && date.before(end);
	}

	/**
	 * 判断两个时间是不是同一天
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameDate(Date time1, Date time2){
		Calendar cal = Calendar.getInstance();
		cal.setTime(time1);
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH);
		int date1 = cal.get(Calendar.DAY_OF_YEAR);

		cal.setTime(time2);
		int year2 = cal.get(Calendar.YEAR);
		int month2 = cal.get(Calendar.MONTH);
		int date2 = cal.get(Calendar.DAY_OF_YEAR);

		return year1 == year2 && month1 == month2 && date1 == date2;
	}

	public static String date2String(Date theDate) {
		return date2String(theDate, DatePattern.PATTERN_NORMAL) ;
	}

	/**
	 * 日期转换成字符串格式
	 * @param theDate 待转换的日期
	 * @param datePattern 日期格式
	 * @return 日期字符串
	 */
	public static String date2String(Date theDate, String datePattern) {
		if (theDate == null) {
			return "";
		}

		DateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.format(theDate);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 返回指定日期的数字，具体至天
	 * 如：20151226
	 * @param theDate
	 * @return
	 */
	public static int date2DayNumber(Date theDate) {
		String ss = date2String(theDate, "yyyyMMdd") ;
		return Integer.parseInt(ss) ;
	}

	/**
	 * 字符串转换成日期格式
	 *
	 * @param  dateString 		待转换的日期字符串
	 * @param  datePattern 		日期格式
	 * @return {@link Date}		转换后的日期
	 */
	public static Date string2Date(String dateString, String datePattern) {
		if (dateString == null || dateString.trim().isEmpty()) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			log.error("ParseException in Converting String to date: " + e.getMessage());
		}

		return null;

	}

	/**
	 * 把秒数转换成把毫秒数
	 *
	 * @param  seconds		秒数的数组
	 * @return {@link Long} 毫秒数
	 */
	public static long toMillisSecond(long...seconds) {
		long millis = 0L;
		if(seconds != null && seconds.length > 0) {
			for (long time : seconds) {
				millis += (time * 1000);
			}
		}
		return millis;
	}

	/**
	 * 把毫秒数转换成把秒数
	 *
	 * @param  millis		毫秒数的数组
	 * @return {@link Long} 毫秒数
	 */
	public static long toSecond(long...millis) {
		long second = 0L;
		if(millis != null && millis.length > 0) {
			for (long time : millis) {
				second += (time / ONE_SECOND_MILLISECOND);
			}
		}
		return second;
	}

	/**
	 * 修改日期
	 * @param theDate 待修改的日期
	 * @param addDays 加减的天数
	 * @param hour 设置的小时
	 * @param minute 设置的分
	 * @param second 设置的秒
	 * @return 修改后的日期
	 */
	public static Date changeDateTime(Date theDate, int addDays, int hour, int minute, int second) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(DAY_OF_MONTH, addDays);
		cal.set(Calendar.MILLISECOND, 0);

		if (hour >= 0 && hour <= 24) {
			cal.set(HOUR_OF_DAY, hour);
		}
		if (minute >= 0 && minute <= 60) {
			cal.set(MINUTE, minute);
		}
		if (second >= 0 && second <= 60) {
			cal.set(SECOND, second);
		}

		return cal.getTime();
	}

	/**
	 * 日期操作
	 * @param theDate
	 * @param count  时长
	 * @param timeUnit 时长单位
	 * @return
	 */
	public static Date add(Date theDate, int count , TimeUnit timeUnit) {
		if (theDate == null) {
			return null;
		}

		switch (timeUnit){
			case SECONDS:
				return add(theDate, 0 ,0 ,count);
			case MINUTES:
				return add(theDate,0, count, 0);
			case HOURS:
				return add(theDate, count, 0 ,0);
			case DAYS:
				return add(theDate, count);
			default:
				return null;
		}
	}

	public static Date add(Date theDate, int addHours, int addMinutes, int addSecond) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(HOUR_OF_DAY, addHours);
		cal.add(MINUTE, addMinutes);
		cal.add(SECOND, addSecond);

		return cal.getTime();
	}


	public static Date add(Date theDate, int days , int addHours, int addMinutes, int addSecond) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(Calendar.DATE, days);
		cal.add(HOUR_OF_DAY, addHours);
		cal.add(MINUTE, addMinutes);
		cal.add(SECOND, addSecond);

		return cal.getTime();
	}

	/**
	 * 取得星期几
	 * @param theDate
	 * @return
	 */
	public static int dayOfWeek(Date theDate) {
		if (theDate == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		return cal.get(DAY_OF_WEEK);
	}

	public static String dayofWeekCh(Date date){
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return WEEK_CN[cal.get(DAY_OF_WEEK)-1];
	}

	/**
	 * 获得某一时间的0点
	 * @param theDate 需要计算的时间
	 */
	public static Date getDate0AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH),	cal.get(DAY_OF_MONTH)).getTime();
	}

	/**
	 * 获得某一时间的整点
	 * @param theDate 需要计算的时间
	 */
	public static Date getDateHour(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH),	cal.get(DAY_OF_MONTH), cal.get(HOUR_OF_DAY),0).getTime();
	}

	/**
	 * 获得某一时间的4点
	 * @param theDate 需要计算的时间
	 */
	public static Date getDate4AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH),	cal.get(DAY_OF_MONTH), 4, 0).getTime();
	}

	/**
	 * 获得某一时间的4点
	 * @param theDate 需要计算的时间
	 */
	public static Date getDate12AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH),	cal.get(DAY_OF_MONTH), 12, 0).getTime();
	}


	/**
	 * 获得某一时间的下一个0点
	 * @param theDate 需要计算的时间
	 */
	public static Date getNextDay0AM(Date theDate) {
		if (theDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(theDate.getTime() + ONE_DAY_MILLISECOND);
		return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH),	cal.get(DAY_OF_MONTH)).getTime();
	}

	/**
	 * 获得指定日期的23点59分59秒的时间
	 * @param theDate 需要计算的时间
	 */
	public static Date getThisDay2359PM(Date theDate) {
		if (theDate == null) {
			return null;
		}
		theDate = getDate0AM(theDate) ;
		long millis = theDate.getTime() + ONE_DAY_MILLISECOND - ONE_SECOND_MILLISECOND;
		return new Date(millis);
	}

	/**
	 * 计算2个时间相差的天数,这个方法算的是2个零点时间的绝对时间(天数)
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 */
	public static int calc2DateTDOADays(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return 0;
		}
		Date startDate0AM = getDate0AM(startDate);
		Date endDate0AM = getDate0AM(endDate);
		long v1 = startDate0AM.getTime() - endDate0AM.getTime();

		BigDecimal bd1 = new BigDecimal(Math.abs(v1));
		BigDecimal bd2 = new BigDecimal(ONE_DAY_MILLISECOND);

		int days = (int)bd1.divide(bd2, 0, BigDecimal.ROUND_UP).doubleValue();
		return days;
	}

	/**
	 * 获得指定时间的下个周一的00:00:00的时间
	 * @param date 指定的时间
	 * @return {@link Date} 周一的00:00:00的时间
	 */
	public static Date getNextMonday(Date date) {
		if (date == null) {
			return null;
		}

		// 本周周一
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtils.getDate0AM(date));
		cal.set(DAY_OF_WEEK, MONDAY);

		Calendar nextMondayCal = Calendar.getInstance();
		nextMondayCal.setTimeInMillis(cal.getTimeInMillis() + ONE_DAY_MILLISECOND * 7);
		return nextMondayCal.getTime();
	}


	/**
	 * 获取本周一的00:00:00的时间(西方以周日为第一天)
	 * @param date
	 * @return
	 */
	public static Date getThisMonday(Date date){
		if (date == null) {
			return null;
		}

		// 本周周一
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtils.getDate0AM(date));
		cal.set(DAY_OF_WEEK, MONDAY);

		return cal.getTime();
	}

	/**
	 * 获取本周一的时间（中国时间，以周一为第一天）
	 * @param date
	 * @return
	 */
	public static Date getThisMondayWithChina(Date date){
		if (date == null) {
			return null;
		}

		// 本周周一
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(MONDAY);
		cal.setTime(DateUtils.getDate0AM(date));
		cal.set(DAY_OF_WEEK, MONDAY);

		return cal.getTime();
	}

	/**
	 * 获得获得改变后的时间
	 *
	 * @param 	addDay			增加的天数(减少天数, 则传负数)
	 * @param   to0AM			是否取0点时间
	 * @return
	 */
	public static Date add(int addDay, boolean to0AM) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(DATE, addDay);
		Date time = calendar.getTime();
		return to0AM ? getDate0AM(time) : time;
	}

	/**
	 * 获取某个时间改变后的时间
	 * @param date		日期
	 * @param addDay	增加的天数(减少天数, 则传负数)
	 * @return
	 */
	public static Date add(Date date,int addDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, addDay);
		Date time = c.getTime();
		return time;
	}

	/**
	 * 计算两个时间之间相差的天
	 * @param time1 时间1
	 * @param time2 时间2
	 * @param borderHour 以这个小时数区分天
	 * @param borderMinute 以这个分钟数区分天
	 * @param borderSecond 以这个秒数区分天
	 * @return
	 */
	public static int calcDiffTimezone(Date time1, Date time2, int borderHour, int borderMinute, int borderSecond){
		Date borderTime1 = DateUtils.changeDateTime(time1, 0, borderHour, borderMinute, borderSecond);
		if(time1.compareTo(borderTime1) < 0 ){
			borderTime1 = DateUtils.changeDateTime(time1, -1, borderHour, borderMinute, borderSecond);
		}

		Date borderTime2 =  DateUtils.changeDateTime(time2, 0, borderHour, borderMinute, borderSecond);
		if(time2.compareTo(borderTime2) < 0){
			borderTime2 = DateUtils.changeDateTime(time2, -1, borderHour, borderMinute, borderSecond);
		}

		return (int)Math.abs((borderTime2.getTime() - borderTime1.getTime()) / (24 * 3600 * 1000L)) ;
	}


	/**
	 * 获取边界时间的0点
	 * @param time
	 * @param borderHour 以这个小时数区分天
	 * @param borderMinute 以这个分钟数区分天
	 * @param borderSecond 以这个秒数区分天
	 * @return
	 */
	public static Date getBorderTime(Date time, int borderHour, int borderMinute, int borderSecond){
		Date borderTime = DateUtils.changeDateTime(time, 0, borderHour, borderMinute, borderSecond);
		if(time.compareTo(borderTime) < 0){
			borderTime = DateUtils.changeDateTime(time, -1, borderHour, borderMinute, borderSecond);
		}
		return borderTime;
	}

	/**
	 * 获取当月第一天的0点
	 * @param time
	 * @return
	 */
	public static Date getAMTimeOfMonth(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(HOUR_OF_DAY, 0);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取次月第一天的0点
	 * @param time
	 * @return
	 */
	public static Date getAMTimeOfNextMonth(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(HOUR_OF_DAY, 0);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期相差的月数 (从 time1 所在的月 开始计数 1, 每后移一月+1, 直到与time2 是同一个月, 这里假设time1<time2,方法内部会自动取小的时间开始移动)
	 * @param time1
	 * @param time2
	 * @return time1,time2 所跨越的月数
	 */
	public static int getDistanceOfMonth(Date time1, Date time2){
		Date startTime = time1.compareTo(time2) <= 0 ? time1 : time2;
		Date endTime = startTime == time1 ? time2 : time1;

		Calendar moveCal = Calendar.getInstance();
		moveCal.setTime(startTime);

		moveCal.set(Calendar.MILLISECOND, 0);
		moveCal.set(Calendar.HOUR_OF_DAY, 0);
		moveCal.set(Calendar.MINUTE, 0);
		moveCal.set(Calendar.SECOND, 0);
		moveCal.set(Calendar.DAY_OF_MONTH, 1);//设置到 当月的1号0点

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endTime);

		endCal.set(Calendar.MILLISECOND, 0);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.DAY_OF_MONTH, 1);//设置到 当月的1号0点

		int distances = 0;

		while(moveCal.compareTo(endCal) < 0){
			moveCal.add(Calendar.MONTH, 1);
			distances++;
		}

		return distances;
	}

	/**
	 * 获取utc时间相对0时区(本初子午线)的偏移毫秒数
	 * 例:
	 * 北京时间时区为东8区(时区标识:UTC+8)相对0时区快8小时(28800000毫秒),有的地区实行夏令制度时区标识有 UTC+9:30的
	 * @return
	 */
	public static int getUtcTimeoffsetMillis(){
		 return TimeZone.getDefault().getOffset(0);
	}

	 /**
     * 输入String类型的日期与格式代号，以String类型返回需要的格式
     *
     * @param date
     *            String 类型的日期
     * @param i
     *            格式类型 可以这样调用DateTool.yyyy_MM_dd
     * */
    public static String getStringDateFormats(String date, int i) throws Exception {
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        if (date == null || date.length() < 6) {
            return "";
        }
        switch (i) {
        case 1: // '\001'
            simpledateformat.applyPattern("yyyy-MM-dd");
            break;

        case 2: // '\002'
            simpledateformat.applyPattern("yyyy-M-d");
            break;

        case 3: // '\003'
            simpledateformat.applyPattern("yy-MM-dd");
            break;

        case 4: // '\004'
            simpledateformat.applyPattern("yy-M-d");
            break;

        case 5: // '\005'
            simpledateformat.applyPattern("yyyy-MM-dd HH:mm:ss");
            break;

        case 6: // '\006'
            simpledateformat.applyPattern("yyyy-M-d H:m:s");
            break;

        case 7: // '\007'
            simpledateformat.applyPattern("yy-MM-dd HH:mm:ss");
            break;

        case 8: // '\b'
            simpledateformat.applyPattern("yy-M-d H:m:s");
            break;

        case 9: //
            simpledateformat.applyPattern("yyyy");
            break;

        case 10: //
            simpledateformat.applyPattern("yyyy-MM");
            break;

        case 11: //
            simpledateformat.applyPattern("yyyyMMdd");
            break;

        case 12: //
            simpledateformat.applyPattern("yyyyMM");
            break;

        case 13:
            simpledateformat.applyPattern("yyyyMMddHHmmss");
            break;

        case 14:
            simpledateformat.applyPattern("yyMMddHH");
            break;

        case 15: //
            simpledateformat.applyPattern("yyyy-MM-dd'T'HH:mm:ss.0'Z'");
            break;

        case 16: //
            simpledateformat.applyPattern("HH:mm:ss");
            break;
        }
        return simpledateformat.format(simpledateformat.parse(date));
    }


    /**
     * 输入Date类型的日期与格式代号，以String类型返回需要的格式
     *
     * @param calendar
     *            Date 类型的日期
     * @param i
     *            格式类型 可以这样调用DateTool.yyyy_MM_dd
     * */

    public static String getStringDateFormat(Date calendar, int i)  {
    	if(calendar == null){
    		return null;
		}
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        switch (i) {
        case 1: // '\001'
            simpledateformat.applyPattern("yyyy-MM-dd");
            break;

        case 2: // '\002'
            simpledateformat.applyPattern("yyyy-M-d");
            break;

        case 3: // '\003'
            simpledateformat.applyPattern("yy-MM-dd");
            break;

        case 4: // '\004'
            simpledateformat.applyPattern("yy-M-d");
            break;

        case 5: // '\005'
            simpledateformat.applyPattern("yyyy-MM-dd HH:mm:ss");
            break;

        case 6: // '\006'
            simpledateformat.applyPattern("yyyy-M-d H:m:s");
            break;

        case 7: // '\007'
            simpledateformat.applyPattern("yy-MM-dd HH:mm:ss");
            break;

        case 8: // '\b'
            simpledateformat.applyPattern("yy-M-d H:m:s");
            break;

        case 9: //
            simpledateformat.applyPattern("yyyy");
            break;

        case 10: //
            simpledateformat.applyPattern("yyyy-MM");
            break;

        case 11: //
            simpledateformat.applyPattern("yyyyMMdd");
            break;

        case 12: //
            simpledateformat.applyPattern("yyyyMM");
            break;

        case 13: //
            simpledateformat.applyPattern("yyyyMMddHHmmss");
            break;

        case 14:
            simpledateformat.applyPattern("yyMMddHH");
            break;

        case 15: //
            simpledateformat.applyPattern("yyyy-MM-dd'T'HH:mm:ss.0'Z'");
            break;

        case 16: //
            simpledateformat.applyPattern("HH:mm:ss");
            break;
        case 17: // '\001'
            simpledateformat.applyPattern("MM-dd");
            break;
        case 18: // '\001'
            simpledateformat.applyPattern("yyyy年MM月dd日");
            break;
        case 19: // '\001'
            simpledateformat.applyPattern("yyyy年MM月dd日 HH:mm:ss");
            break;
        case 20: //
            simpledateformat.applyPattern("yyyyMMddHHmmssSSS");
            break;
        }
        return simpledateformat.format(calendar);
    }

    public static String getAge(Date birthday) {
		int gap = calc2DateTDOADays(birthday, new Date());
		if (gap <= 30) {
			return gap + "天";
		}

		int year = gap / 365;
		int day = gap % 365;
		int month = day /30;
		if(year < 3) {

			return year + "岁" + month + "月";
		}

		return year + "岁";
	}

//	public static Integer getAge(Date birthday, AgePattern pattern){
//		int gap = calc2DateTDOADays(birthday, new Date());
//		switch (pattern){
//			case YEAR:
//				int year = gap / 365;
////				if((gap % 365) > 0){
////					return year + 1;
////				}
//				return year;
//			case MONTH:
//				int month = gap /30;
//				if((gap % 30) > 0){
//					return month + 1;
//				}
//				return month;
//			case WEEK:
//				int week = gap / 7;
//				if((gap % 7) > 0){
//					return week + 1;
//				}
//				return week;
//			case DAY:
//				return gap;
//			default:
//				return null;
//		}
//
//	}

    public static String getAge(Date birthday, Date date) {
		int gap = calc2DateTDOADays(birthday, date);
		if (gap <= 30) {
			return gap + "天";
		}

		int year = gap / 365;
		int day = gap % 365;
		int month = day /30;
		if(year < 3) {

			return year + "岁" + month + "月";
		}

		return year + "岁";
	}

    /**
     * 输入String类型的日期与格式代号，以Date类型返回需要的格式
     *
     * @param date
     *            String 类型的日期
     * @param i
     *            格式类型 可以这样调用DateTool.yyyy_MM_dd
     * */
    public static Date getDateFormat(String date, int i) throws Exception {
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        switch (i) {
        case 1: // '\001'
            simpledateformat.applyPattern("yyyy-MM-dd");
            break;

        case 2: // '\002'
            simpledateformat.applyPattern("yyyy-M-d");
            break;

        case 3: // '\003'
            simpledateformat.applyPattern("yy-MM-dd");
            break;

        case 4: // '\004'
            simpledateformat.applyPattern("yy-M-d");
            break;

        case 5: // '\005'
            simpledateformat.applyPattern("yyyy-MM-dd HH:mm:ss");
            break;

        case 6: // '\006'
            simpledateformat.applyPattern("yyyy-M-d H:m:s");
            break;

        case 7: // '\007'
            simpledateformat.applyPattern("yy-MM-dd HH:mm:ss");
            break;

        case 8: // '\b'
            simpledateformat.applyPattern("yy-M-d H:m:s");
            break;

        case 9: //
            simpledateformat.applyPattern("yyyy");
            break;

        case 10: //
            simpledateformat.applyPattern("yyyy-MM");
            break;

        case 11: //
            simpledateformat.applyPattern("yyyyMMdd");
            break;

        case 12: //
            simpledateformat.applyPattern("yyyyMM");
            break;

        case 13:
            simpledateformat.applyPattern("yyyyMMdd HH:mm:ss");
            break;

        case 14:
            simpledateformat.applyPattern("yyMMddHH");
            break;

        case 15: //
            simpledateformat.applyPattern("yyyy-MM-dd'T'HH:mm:ss.0'Z'");
            break;

        case 16: //
            simpledateformat.applyPattern("HH:mm:ss");
            break;

        case 17: // '\005'
            simpledateformat.applyPattern("yyyy-MM-dd HH:mm");
            break;

        case 18:
            simpledateformat.applyPattern("yyyyMMddHHmmss");
            break;

        }

        return simpledateformat.parse(date);
    }

    /**
     * 取得一天的开始时间
     * @param date
     * @return
     * @throws Exception
     * @
     */
    public static Date getFristTimeOfDate(Date date) throws Exception {
        String s=DateUtils.getStringDateFormat(date, 1);
        return DateUtils.getDateFormat(s + " 00:00:00", 7);
    }

    /**
     * 取得一天的最后时间
     * @param date
     * @return
     * @throws Exception
     * @
     * @
     */
    public static Date getLastTimeOfDate(Date date) throws Exception {
    	String s=DateUtils.getStringDateFormat(date, 1);
        return DateUtils.getDateFormat(s + " 23:59:59", 7);
    }

	/**
	 * 获取某一天是星期几
	 * @param date
	 * @return
	 */
	public static String getWeekDayName(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getWeek(c.get(Calendar.DAY_OF_WEEK)-1);
    }

	/**
	 * 返回昨天0点的时间
	 * @param date
	 * @return
	 */
	public static Date getYesterday0AM(Date date){
		date = add(date,-1);
		return getDate0AM(date);
	}

    private static String getWeek(int day) {
        String w = "";
        switch (day) {
        case 0:
            w = "星期日";
            break;
        case 1:
            w = "星期一";
            break;
        case 2:
            w = "星期二";
            break;
        case 3:
            w = "星期三";
            break;
        case 4:
            w = "星期四";
            break;
        case 5:
            w = "星期五";
            break;
        case 6:
            w = "星期六";
            break;

        default:
            break;
        }
        return w;

    }

    public static List<List<String>> getMonthView(String date) throws Exception {
    	Date firstDay = DateUtils.getDateFormat(date, 10);
		//首先计算这个月有多少个星期，得出月表格
		List<List<String>> month = new ArrayList<List<String>>();
		Calendar c = Calendar.getInstance();
	    c.setTime(firstDay);
	    //星期一为第一天
	    int start = 2 - c.get(Calendar.DAY_OF_WEEK);

	    Date lastDay = DateUtils.lastDayOfMonth(firstDay);
	    c.setTime(lastDay);
	    int end =  c.get(Calendar.DAY_OF_MONTH);

	    //如果不是以星期天为结束，需要补齐7天
	    if (c.get(Calendar.DAY_OF_WEEK) != 1) {
	    	end = end + 8 - c.get(Calendar.DAY_OF_WEEK);
	    }

	    //得出二维数组月表格
	    for(; start < end; ) {
	    	List<String> week = new ArrayList<String>();
	    	for(int i = 0; i < 7; i++) {
	    		String dateStr = DateUtils.getStringDateFormat(DateUtils.add(firstDay, start), 1);
	    		week.add(dateStr);
	    		start++;
	    	}
	    	month.add(week);
	    }
	    return month;

    }


	/**
	 * 比较两个时间段是否有重叠
	 * @param startTime1
	 * @param endTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
    @SuppressWarnings("deprecation")
	public static Boolean timeContain(String startTime1,String endTime1, String startTime2, String endTime2){
		String[] split1 = startTime1.split(":");
		String[] split2 = endTime1.split(":");
		String[] split3 = startTime2.split(":");
		String[] split4 = endTime2.split(":");
		Time startT1 = new Time(Integer.valueOf(split1[0]),Integer.valueOf(split1[1]),0);
		Time endT1 = new Time(Integer.valueOf(split2[0]),Integer.valueOf(split2[1]),0);
		Time startT2 = new Time(Integer.valueOf(split3[0]),Integer.valueOf(split3[1]),0);
		Time endT2 = new Time(Integer.valueOf(split4[0]),Integer.valueOf(split4[1]),0);
		if(endT1.before(startT2) || startT1.after(endT2) || endT1.getTime() == startT2.getTime() || startT1.getTime() == endT2.getTime()){
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static Time string2Time(String time){
		String[] split = time.split(":");
		try {
			if(split.length == 2){
				return new Time(Integer.valueOf(split[0]),Integer.valueOf(split[1]),0);
			} else if (split.length == 3){
				return new Time(Integer.valueOf(split[0]),Integer.valueOf(split[1]),Integer.valueOf(split[2]));
			}
		} catch (NumberFormatException e) {
			log.error("时间转换异常", e);
		}
		return null;
	}

//    public static void main(String[] args) {
//        System.out.println(getAge(string2Date("2002-08-05", "yyyy-MM-dd"), AgePattern.YEAR));
//	}

	/**
	 * 比较两个时间的大小
	 * @param time1
	 * @param time2
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int timeCompareWithString(String time1, String time2) {
		String[] split1 = time1.split(":");
		String[] split2 = time2.split(":");
		Time t1 =null;
		Time t2 = null;
		if(split1.length == 2){
			t1 = new Time(Integer.valueOf(split1[0]),Integer.valueOf(split1[1]),0);
		}else {
			t1 = new Time(Integer.valueOf(split1[0]),Integer.valueOf(split1[1]),Integer.valueOf(split1[2]));
		}

		if(split1.length == 2){
			t2 = new Time(Integer.valueOf(split2[0]),Integer.valueOf(split2[1]),0);
		}else {
			t2 = new Time(Integer.valueOf(split2[0]),Integer.valueOf(split2[1]),Integer.valueOf(split2[2]));
		}

		if(t1.before(t2)){
			return -1;
		} else {
			return 1;
		}

	}

	/**
	 * 比较时间是否在某个区间之间
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Boolean between(Date date, Date startDate, Date endDate){
		if(date.after(startDate) && date.before(endDate)){
			return true;
		}
		return false;
	}

	/**
	 * 比较时间是否在某个区间之间
	 * @param time
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static Boolean between(Time time, Time startTime, Time endTime){
		if(time.after(startTime) && time.before(endTime)){
			return true;
		}
		return false;
	}
}


