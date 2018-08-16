package com.test.tdq.uitl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * 工具类
 * 
 * @author denghw
 */
public class CommUtil {

	public static final String YYYYMMDD = "yyyyMMdd"; 
	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static Integer page = 1;// 默认
	public static Integer pageSize = 20;

	/**
	 * 返回当前时间,格式HH:mm:ss
	 * 
	 * @return String 当前时间
	 * @author denghw
	 */
	public static String getCurrentTime() {
		String strTime = null;
		try {
			DateFormat objMyformat = new SimpleDateFormat(HH_MM_SS);
			strTime = objMyformat.format(new Date());
		} catch (Exception e) {
			System.out.println("CommUtil.getCurrentTime" + e);
		}
		return strTime;
	}

	/**
	 * 拼接日期时间字符串。 在时期与时间串之间加一空格； 时间输入省略了分或分秒时，补齐零； 日期为空时，按1970-01-01算。 例：
	 * dtJoin("1990-01-23", ""); // 返回值为"1990-01-23 00:00:00"
	 * dtJoin("1990-01-23", "1"); // 返回值为"1990-01-23 1:00:00"
	 * dtJoin("1990-01-23", "1:23"); // 返回值为"1990-01-23 1:23:00" dtJoin("",
	 * "1:23"); // 返回值为"1970-01-01 1:23:00"
	 * 
	 * @param dateStr
	 *            日期串，格式为YYYY-MM-DD
	 * @param timeStr
	 *            时间串，格式为hh:mm:ss
	 * @return
	 */
	public static String dtJoin(String dateStr, String timeStr) {
		String d = dateStr;
		String t = timeStr;
		if (CommUtil.isBlank(d)) {

			if (CommUtil.isBlank(t))
				return null;
			else {
				d = "1970-01-01";
			}
		}
		if (CommUtil.isBlank(t)) {
			t = "00:00:00";
		} else if (t.length() <= 2) { // hh
			t = t + ":00:00";
		} else if (t.length() <= 5) { // hh:mm
			t = t + ":00";
		}
		return d + " " + t;
	}

	/**
	 * 判断字符串是否为空或空串；其中空串是指经过trim后字符串长度为0。 为空或空串返回true，否则返回false。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || (str.trim()).equals(""))
			return true;
		return false;
	}

	/**
	 * 判断List是否为空。 list==null或长度为0返回true,其它则返回false.
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBlank(List list) {
		if (list == null || list.size() == 0)
			return true;
		return false;
	}

	/**
	 * 判断List是否为空。 list==null或长度为0返回true,其它则返回false.
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBlank(Map hashMap) {
		if (null == hashMap) {
			return true;
		} else {
			if (hashMap.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回当前时间,格式yyyy-MM-dd HH:mm:ss SSS
	 * 
	 * @return String 当前时间
	 * @author denghw
	 */
	public static String getCurrentDateTime() {
		String strTime = null;
		try {
			DateFormat objMyformat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
			strTime = objMyformat.format(new Date());
		} catch (Exception e) {
			System.out.println("CommUtil.getCurrentDateTime" + e);
		}
		return strTime;
	}

	/**
	 * 格式化String为int型，如果字符串为空，返回0.
	 * 
	 * @param String
	 *            s 双精度数字
	 * @return int 返回int类型数值
	 * @author denghw
	 */
	public static int parseInt(String s) {
		int i = 0;
		if (s != null) {
			try {
				i = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				i = 0;
				double t = parseDouble(s);
				if (t != 0) {
					i = (int) t;
				}
				// logger.error(e.getMessage());
			}
		}
		return i;
	}

	/**
	 * 格式化String为double型，如果字符串为空，返回0.
	 * 
	 * @param String
	 *            s 双精度数字
	 * @return double 返回double类型数值
	 * @author denghw
	 */
	public static double parseDouble(String s) {
		double d = 0;
		if (s != null) {
			try {
				d = Double.parseDouble(s);
			} catch (NumberFormatException e) {
				try {
					String t = s.replaceAll(",", "");
					d = Double.parseDouble(t);
				} catch (NumberFormatException e2) {
					d = 0;
					// logger.error(e.getMessage());
				}
			}
		}
		return d;
	}

	/**
	 * 格式化String为Character型，如果字符串为空，返回0.
	 * 
	 * @param String
	 *            s 字符串
	 * @return double 返回character
	 * @author denghw
	 */
	public static Character parseCharacter(String s) {
		Character d = new Character(' ');
		if (s != null) {
			char[] t = s.trim().toCharArray();
			d = t[0];
		}
		return d;
	}

	/**
	 * 返回两个Date对象的之间的秒数
	 * 
	 * @param Date
	 *            date1 作为被减数的Date对象
	 * @param Date
	 *            date2 作为减数的Date对象
	 * @return int 两个Date对象的之间的秒数
	 * @author denghw
	 */
	public static int getSecondTween(Date objDate1, Date objDate2) {
		long lgMill1 = objDate1.getTime();
		long lgMill2 = objDate2.getTime();
		return (int) ((lgMill1 - lgMill2) / (1000));
	}

	/**
	 * 返回两个string(格式是：yyyy-MM-dd HH:mm:ss)的之间的秒数
	 * 
	 * @param String
	 *            date1 作为被减数的String对象
	 * @param String
	 *            date2 作为减数的String对象
	 * @return int 两个Date对象的之间的秒数
	 * @author denghw
	 */
	public static int getSecondTween(String objDate1, String objDate2) {
		Calendar objCal1 = getTime(formatDate(objDate1, YYYY_MM_DD_HH_MM_SS));
		Calendar objCal2 = getTime(formatDate(objDate2, YYYY_MM_DD_HH_MM_SS));
		Date objD1 = objCal1.getTime();
		Date objD2 = objCal2.getTime();
		return getSecondTween(objD1, objD2);
	}

	/**
	 * 返回输入的字符串代表的Calendar对象.
	 * 
	 * @param String
	 *            str 输入的字符串,格式=yyyy-MM-dd HH:mm:ss.
	 * @return Calendar 返回代表输入字符串的Calendar对象.
	 * @author denghw
	 */
	public static Calendar getTime(String str) {
		Calendar objCal = Calendar.getInstance();
		try {
			DateFormat objMyformat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			Date date = objMyformat.parse(str);
			objCal.setTime(date);
		} catch (Exception e) {
			System.out.println("CommUtil.getTime" + e);
			e.printStackTrace();
		}
		return objCal;
	}

	/**
	 * 将原有的日期格式的字符串转换为特定的格式, 原有格式为yyyy-MM-dd.
	 * 
	 * @param String
	 *            value 日期格式的字符串
	 * @param String
	 *            toPattern 转换成的日期格式
	 * @return String 返回日期字符串
	 * @author denghw
	 */
	public static String formatDate(String strValue, String strToPattern) {
		return formatDate(strValue, strToPattern, strToPattern);
	}

	/**
	 * 将原有的日期格式的字符串转换为特定的格式, 如(原有和转换)格式为null, 则使用缺省格式yyyy-MM-dd.
	 * 
	 * @param String
	 *            value 日期格式的字符串
	 * @param String
	 *            fromPattern 原有的日期格式
	 * @param String
	 *            toPattern 转换成的日期格式
	 * @return String 返回日期字符串
	 * @author denghw
	 */
	public static String formatDate(String strValue, String strFromPattern, String strToPattern) {
		String strDate = null;
		if (strToPattern == null)
			strToPattern = "yyyy-MM-dd";
		if (strValue != null) {
			try {
				SimpleDateFormat objFormatter = null;
				if (strFromPattern != null)
					objFormatter = new SimpleDateFormat(strFromPattern);
				else
					objFormatter = new SimpleDateFormat("yyyy-MM-dd");
				Date day = objFormatter.parse(strValue);
				objFormatter.applyPattern(strToPattern);
				strDate = objFormatter.format(day);
			} catch (Exception e) {
				// logger.error(e);
				return strValue;
			}
			return strDate;
		} else
			return null;
	}

	/**
	 * 把日期字符串转化成日期对象 Title:
	 * 
	 * @param strValue
	 *            日期字符串 例如:"2007-1-18" or "2007-1-18 11:11:11"
	 * @param strToPattern
	 *            需要转换成的格式 默认 yyyy-MM-dd HH:mm:ss
	 * @return 日期:2013-7-29 author 唐登强
	 */

	public static Date strToDate(String strValue, String strToPattern) {
		if (strToPattern == null)
			strToPattern = YYYY_MM_DD_HH_MM_SS;
		if (strValue != null) {
			try {
				SimpleDateFormat objFormatter = new SimpleDateFormat(strToPattern);
				return objFormatter.parse(strValue);
			} catch (Exception e) {
				// logger.error(e);
				return null;
			}
		}
		return null;
	}

	/**
	 * 把日期字符串转化成日期对象 Title:
	 * 
	 * @param strValue
	 *            日期字符串 yyyy-MM-dd HH:mm:ss
	 * @return 日期:2013-10-15 author 唐登强
	 */

	public static Date strToDate(String strValue) {
		if (strValue != null) {
			try {
				SimpleDateFormat objFormatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
				return objFormatter.parse(strValue);
			} catch (Exception e) {
				// logger.error(e);
				return null;
			}
		}
		return null;
	}

	/**
	 * 返回当前年月日期,格式yyyy-MM-dd
	 * 
	 * @return String 年月日期
	 * @author denghw
	 */
	public static String getCurrentDate() {
		String strDate = null;
		try {
			DateFormat objMyformat = new SimpleDateFormat("yyyy-MM-dd");
			strDate = objMyformat.format(new Date());
		} catch (Exception e) {
			System.out.println("CommUtil.getCurrentDate" + e);
		}
		return strDate;
	}

	/**
	 * 返回两个Date对象的之间的天数
	 * 
	 * @param Date
	 *            date1 作为被减数的Date对象
	 * @param Date
	 *            date2 作为减数的Date对象
	 * @return int 两个Date对象的之间的天数
	 * @author denghw
	 */
	public static int getDaysTween(Date objDate1, Date objDate2) {
		// return toJulian(date1) - toJulian(date2);
		long lgMill1 = objDate1.getTime();
		long lgMill2 = objDate2.getTime();
		return (int) ((lgMill1 - lgMill2) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 返回两个String(格式是：yyyy-MM-dd HH:mm:ss)对象的之间的天数
	 * 
	 * @param String
	 *            date1 作为被减数的String对象
	 * @param String
	 *            date2 作为减数的String对象
	 * @return int 两个String对象的之间的天数
	 * @author denghw
	 */
	public static int getDaysTween(String strDate1, String strDate2) {
		String str1 = strDate1;
		String str2 = strDate2;
		str1 = formatDate(str1, "yyyy-MM-dd");
		str2 = formatDate(str2, "yyyy-MM-dd");
		if (str1.length() < 11) {
			str1 = str1 + " 00:00:00";
		}
		if (str2.length() < 11) {
			str2 = str2 + " 00:00:00";
		}
		Calendar cal1 = getTime(formatDate(str1, YYYY_MM_DD_HH_MM_SS));
		Calendar cal2 = getTime(formatDate(str2, YYYY_MM_DD_HH_MM_SS));
		Date objD1 = cal1.getTime();
		Date objD2 = cal2.getTime();
		return getDaysTween(objD1, objD2);
	}

	/**
	 * 返回两个Date对象的之间的小时数
	 * 
	 * @param Date
	 *            date1 作为被减数的Date对象
	 * @param Date
	 *            date2 作为减数的Date对象
	 * @return int 两个Date对象的之间的小时数
	 * @author denghw
	 */
	public static int getHoursTween(Date objDate1, Date objDate2) {
		long lgMill1 = objDate1.getTime();
		long lgMill2 = objDate2.getTime();
		return (int) ((lgMill1 - lgMill2) / (1000 * 60 * 60));
	}

	/**
	 * 返回两个string(格式是：yyyy-MM-dd HH:mm:ss)的之间的小时数
	 * 
	 * @param String
	 *            date1 作为被减数的String对象
	 * @param String
	 *            date2 作为减数的String对象
	 * @return int 两个Date对象的之间的小时数
	 * @author denghw
	 */
	public static int getHoursTween(String strDate1, String strDate2) {
		Calendar objCal1 = getTime(formatDate(strDate1, YYYY_MM_DD_HH_MM_SS));
		Calendar objCal2 = getTime(formatDate(strDate2, YYYY_MM_DD_HH_MM_SS));
		Date objD1 = objCal1.getTime();
		Date objD2 = objCal2.getTime();
		return getHoursTween(objD1, objD2);
	}

	/**
	 * 返回两个Date对象的之间的分钟数
	 * 
	 * @param Date
	 *            date1 作为被减数的Date对象
	 * @param Date
	 *            date2 作为减数的Date对象
	 * @return int 两个Date对象的之间的分钟数
	 * @author denghw
	 */
	public static int getMinutesTween(Date objDate1, Date objDate2) {
		long lgMill1 = objDate1.getTime();
		long lgMill2 = objDate2.getTime();
		return (int) ((lgMill1 - lgMill2) / (1000 * 60));
	}

	/**
	 * 获得两个时间点之间的分钟差
	 * 
	 * @param date1
	 *            被减数
	 * @param date2
	 *            减数
	 * @return
	 * @author denghw
	 */
	public static int getMinutesTween(String strDate1, String strDate2) {
		Calendar objCal1 = getTime(formatDate(strDate1, YYYY_MM_DD_HH_MM_SS));
		Calendar objCal2 = getTime(formatDate(strDate2, YYYY_MM_DD_HH_MM_SS));
		Date objD1 = objCal1.getTime();
		Date objD2 = objCal2.getTime();
		return getMinutesTween(objD1, objD2);
	}

	/**
	 * 获得两个时间点之间的月份差
	 * 
	 * @param date1
	 *            被减数
	 * @param date2
	 *            减数
	 * @return
	 * @author denghw
	 */
	public static int getMonthsTween(String strDate1, String strDate2) {
		int intY1 = getYear(strDate1);
		int intM1 = getMonth(strDate1);
		int intY2 = getYear(strDate2);
		int intM2 = getMonth(strDate2);
		int intM = 0;
		intM += (intY1 - intY2) * 12;
		intM += (intM1 - intM2);
		return intM;
	}

	/**
	 * 返回日期的年
	 * 
	 * @param dateStr
	 *            输入的字符串 格式YYYY-MM-DD
	 * @return int 年
	 * @author denghw
	 */
	public static int getYear(String strDate) {
		if (strDate == null || strDate.length() < 4)
			return 0;
		String strYear = strDate.substring(0, 4);
		return parseInt(strYear);
	}

	/**
	 * 返回日期的月
	 * 
	 * @param dateStr
	 *            输入的字符串 格式YYYY-MM-DD
	 * @return int 月
	 * @author denghw
	 */
	public static int getMonth(String strDate) {
		if (strDate == null || strDate.length() < 7)
			return 0;
		String strMonth = strDate.substring(5, 7);
		return parseInt(strMonth);
	}

	/**
	 * 返回日期的日
	 * 
	 * @param dateStr
	 *            输入的字符串 格式YYYY-MM-DD
	 * @return int 日
	 * @author denghw
	 */
	public static int getDay(String strDate) {
		if (strDate == null || strDate.length() < 10)
			return 0;
		String strDay = strDate.substring(8, 10);
		return parseInt(strDay);
	}

	/**
	 * 返回时间的小时
	 * 
	 * @param timeStr
	 *            输入的字符串 格式HH:mm:ss
	 * @return int 小时
	 * @author denghw
	 */
	public static int getHour(String strTime) {
		if (strTime == null || strTime.length() < 2)
			return 0;
		String strHour = strTime.substring(0, 2);
		return parseInt(strHour);
	}

	/**
	 * 返回时间的分钟
	 * 
	 * @param timeStr
	 *            输入的字符串 格式HH:mm:ss
	 * @return int 分钟
	 * @author denghw
	 */
	public static int getMinute(String strTime) {
		if (strTime == null || strTime.length() < 5)
			return 0;
		String strMinute = strTime.substring(3, 5);
		return parseInt(strMinute);
	}

	/**
	 * 返回时间的分钟
	 * 
	 * @param timeStr
	 *            输入的字符串 格式HH:mm:ss
	 * @return int 分钟
	 * @author denghw
	 */
	public static int getSecond(String strTime) {
		if (strTime == null || strTime.length() < 8)
			return 0;
		String strSecond = strTime.substring(6, 8);
		return parseInt(strSecond);
	}

	/**
	 * 获得随机数
	 * 
	 * @param size
	 *            随机数的位数
	 * @return
	 * @author denghw
	 */
	public static int getRandom(int intSize) {
		double dlRandom = Math.random();
		String strRand = "" + dlRandom * Math.pow(10, intSize);
		int index = strRand.indexOf(".");
		String strPrefix = strRand.substring(0, index);
		return Integer.parseInt(strPrefix);
	}

	/**
	 * 获得定长的字符串(前补)
	 * 
	 * @param strInput
	 *            输入字符串
	 * @param strAddChar
	 *            前补的字符
	 * @param intSize
	 *            字符的长度
	 * @return
	 * @author denghw
	 */
	public static String getFixString(String strInput, String strAddChar, int intSize) {
		if (strInput.length() > intSize)
			strInput = strInput.substring(0, intSize);
		while (strInput.length() < intSize) {
			strInput = strAddChar + strInput;
		}
		return strInput;
	}

	/**
	 * 获得定长的字符串(后补)
	 * 
	 * @param strInput
	 *            输入字符串
	 * @param strAppendChar
	 *            后补的字符
	 * @param intSize
	 *            字符的长度
	 * @return
	 * @author denghw
	 */
	public static String getFixStringAppend(String strInput, String strAddChar, int intSize) {
		if (strInput.length() > intSize)
			strInput = strInput.substring(0, intSize);
		while (strInput.length() < intSize) {
			strInput = strInput + strAddChar;
		}
		return strInput;
	}

	/**
	 * 截取字符串,不足位 则不补位
	 * 
	 * @param strInput
	 *            输入字符串
	 * @param intSize
	 *            字符的长度
	 * @return
	 * @author denghw
	 */
	public static String getFixStringNotAdd(String strInput, int intSize) {
		if (isBlank(strInput)) {// 若为空，则返回空串
			return "";
		}
		if (strInput.length() > intSize) {
			strInput = strInput.substring(0, intSize);
		}
		return strInput;
	}

	/**
	 * 获得流水号
	 * 
	 * @param InfoCode
	 *            信息码
	 * @return
	 * @author denghw
	 */
	public static String getFluNo(String infoCode) {
		String strCurrentDateTime = CommUtil.getCurrentDate();
		int intYear = CommUtil.getYear(strCurrentDateTime);
		int intMonth = CommUtil.getMonth(strCurrentDateTime);
		int intDay = CommUtil.getDay(strCurrentDateTime);
		String strTime = CommUtil.getCurrentTime();
		int intHour = CommUtil.getHour(strTime);
		int intMin = CommUtil.getMinute(strTime);
		int intSend = CommUtil.getSecond(strTime);
		int radom = CommUtil.getRandom(2);
		String postfix = intYear + CommUtil.getFixString(intMonth + "", "0", 2) + CommUtil.getFixString(intDay + "", "0", 2) + CommUtil.getFixString(intHour + "", "0", 2)
				+ CommUtil.getFixString(intMin + "", "0", 2) + CommUtil.getFixString(intSend + "", "0", 2) + CommUtil.getFixString(radom + "", "0", 2);
		String strReturnCode = infoCode + postfix;
		return strReturnCode;
	}

	/**
	 * 取日期在周中的位置，周一为1，周日为7
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(String strDate) {
		int intYear = CommUtil.getYear(strDate);
		int intMonth = CommUtil.getMonth(strDate);
		int intDay = CommUtil.getDay(strDate);
		// 取出该周起止日期.
		GregorianCalendar objCal = new GregorianCalendar(intYear, intMonth - 1, intDay);
		int intDw = objCal.get(GregorianCalendar.DAY_OF_WEEK);
		if (intDw == GregorianCalendar.SUNDAY) {
			return 7;
		}
		return intDw - 1;
	}

	/**
	 * 转换英文格式日期至中文格式
	 * 
	 * @param strEngDate
	 *            (04 May 1998)
	 * @return strCNDate (1998-05-04)
	 */
	public static String trfEngToCNDate(String strEngDate) {
		strEngDate = strEngDate.replaceAll(" ", "");
		String d = strEngDate.substring(0, 2); // 日
		String m = strEngDate.substring(2, 5); // 月
		String y = strEngDate.substring(5, 9); // 年
		if (m.equals("Jan")) {
			m = "01";
		} else if (m.equals("Feb")) {
			m = "02";
		} else if (m.equals("Mar")) {
			m = "03";
		} else if (m.equals("Apr")) {
			m = "04";
		} else if (m.equals("May")) {
			m = "05";
		} else if (m.equals("Jun")) {
			m = "06";
		} else if (m.equals("Jul")) {
			m = "07";
		} else if (m.equals("Aug")) {
			m = "08";
		} else if (m.equals("Sep")) {
			m = "09";
		} else if (m.equals("Oct")) {
			m = "10";
		} else if (m.equals("Nov")) {
			m = "11";
		} else if (m.equals("Dec")) {
			m = "12";
		}
		String strCNDate = y + "-" + m + "-" + d;
		return strCNDate;
	}

	/**
	 * 将Date数据类型转换为特定的格式, 如格式为null, 则使用缺省格式yyyy-MM-dd.
	 * 
	 * @param Date
	 *            day 日期
	 * @param String
	 *            toPattern 要转换成的日期格式
	 * @return String 返回日期字符串
	 */
	public static String formatDates(Date day, String toPattern) {
		String date = null;
		if (day != null) {
			try {
				SimpleDateFormat formatter = null;
				if (toPattern != null)
					formatter = new SimpleDateFormat(toPattern);
				else
					formatter = new SimpleDateFormat("yyyy-MM-dd");
				date = formatter.format(day);
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
			return date;
		} else
			return null;
	}

	/**
	 * 日期时间加上一个时间.
	 * 
	 * @param dateStr
	 *            日期时间 "YYYY-MM-DD HH:mm:ss"
	 * @param timeStr
	 *            时间 "HH:mm:ss" 或者 "HH:mm" 或者 "HH"
	 * @return 时间日期 YYYY-MM-DD HH:mm:ss
	 */
	public static String addTime(String dateStr, String timeStr) {
		try {
			Calendar cal = Calendar.getInstance();
			StringTokenizer strTk = new StringTokenizer(timeStr, ":");
			DateFormat myformat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			Date date = myformat.parse(dateStr);
			cal.setTime(date);
			cal.add(Calendar.HOUR, Integer.parseInt(strTk.nextToken()));
			if (strTk.hasMoreElements()) {
				cal.add(Calendar.MINUTE, Integer.parseInt(strTk.nextToken()));
			}
			if (strTk.hasMoreElements()) {
				cal.add(Calendar.SECOND, Integer.parseInt(strTk.nextToken()));
			}
			date = cal.getTime();
			return CommUtil.formatDates(date, YYYY_MM_DD_HH_MM_SS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 日期时间减去一个时间.
	 * 
	 * @param dateStr
	 *            日期时间 "YYYY-MM-DD HH:mm:ss"
	 * @param timeStr
	 *            时间 "HH:mm:ss" 或者 "HH:mm" 或者 "HH"
	 * @return 时间日期 YYYY-MM-DD HH:mm:ss
	 */
	public static String subTime(String dateStr, String timeStr) {
		try {
			Calendar cal = Calendar.getInstance();
			StringTokenizer strTk = new StringTokenizer(timeStr, ":");
			DateFormat myformat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			Date date = myformat.parse(dateStr);
			cal.setTime(date);
			cal.add(Calendar.HOUR, -Integer.parseInt(strTk.nextToken()));
			if (strTk.hasMoreElements()) {
				cal.add(Calendar.MINUTE, -Integer.parseInt(strTk.nextToken()));
			}
			if (strTk.hasMoreElements()) {
				cal.add(Calendar.SECOND, -Integer.parseInt(strTk.nextToken()));
			}
			date = cal.getTime();
			return CommUtil.formatDates(date, YYYY_MM_DD_HH_MM_SS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将Document生成字符串
	 * 
	 * @param myDocument
	 * @return
	 */
	/*
	 * public static String getXMLStr(Document myDocument) { XMLOutputter outter
	 * = new XMLOutputter(" ", false, "GBK"); String strXmlStr =
	 * outter.outputString(myDocument); return strXmlStr; }
	 *//**
	 * 将字符串生成Element
	 * 
	 * @param xmlPath
	 * @return 顶层Element
	 */
	/*
	 * public static Element getRootElement(String xmlString) { Document
	 * myDocument = null; if (xmlString == null) return null; try { SAXBuilder
	 * builder = new SAXBuilder(); try { myDocument = builder.build(xmlString);
	 * } catch (IOException e) { System.out.println(e.toString()); return null;
	 * } } catch (JDOMException jde) { System.out.println(jde.toString());
	 * return null; } return (myDocument.getRootElement()); }
	 *//**
	 * 将字符串生成Document
	 * 
	 * @param xml的路径
	 * @return
	 */
	/*
	 * public static Document getDocument(String xmlString) { Document
	 * myDocument = null; if (xmlString == null) return null; try { SAXBuilder
	 * builder = new SAXBuilder(); try { myDocument = builder.build(xmlString);
	 * } catch (IOException e) { System.out.println(e.toString()); return null;
	 * } } catch (JDOMException jde) { System.out.println(jde.toString());
	 * return null; } return myDocument; }
	 *//**
	 * 将字符串生成Document
	 * 
	 * @param xmlString
	 * @return
	 */
	/*
	 * public static Document getDocumentByXMLStr(String xmlString) { Document
	 * myDocument = null; if (xmlString == null) return null; try { SAXBuilder
	 * builder = new SAXBuilder(); try { StringReader read = new
	 * StringReader(xmlString); InputSource source = new InputSource(read);
	 * myDocument = builder.build(source); } catch (IOException e) {
	 * System.out.println(e.toString()); return null; } } catch (JDOMException
	 * jde) { System.out.println(jde.toString()); return null; } return
	 * myDocument; }
	 *//**
	 * 将字符串生成Element
	 * 
	 * @param xmlString
	 * @return 顶层Element
	 */
	/*
	 * public static Element getRootElementByXMLStr(String xmlString) { Document
	 * myDocument = null; if (xmlString == null) return null; try { StringReader
	 * read = new StringReader(xmlString); InputSource source = new
	 * InputSource(read); SAXBuilder builder = new SAXBuilder(); try {
	 * myDocument = builder.build(source); } catch (IOException e) {
	 * System.out.println(e.toString()); return null; } } catch (JDOMException
	 * jde) { System.out.println(jde.toString()); return null; } return
	 * (myDocument.getRootElement()); }
	 */
	/**
	 * java实体转XML
	 * 
	 * @param t
	 * @return
	 */
	// public static String java2XML(Object t) {
	// try {
	// ByteArrayOutputStream byteArrayOutputStream = new
	// ByteArrayOutputStream();
	// JAXBContext context = JAXBContext.newInstance(t.getClass());
	//
	// Marshaller marshaller = context.createMarshaller();
	// marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");// 编码格式
	// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);//
	// 是否格式化生成的xml串
	// marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);//
	// 是否省略xml头信息（<?xml
	// // version="1.0"
	// // encoding="GBK"
	// // standalone="yes"?>）
	//
	// marshaller.marshal(t, byteArrayOutputStream);
	//
	// // 转化为字符串返回
	// String xmlContent;
	//
	// xmlContent = new String(byteArrayOutputStream.toByteArray(), "GBK");
	// return xmlContent;
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }
	/**
	 * 验证移动电话合法性
	 * 
	 * @param strPhoneNumber
	 * @return
	 */
	public static boolean isMatcherPhone(String strPhoneNumber) {

		// 电话号码匹配
		// 商号段
		// 中国移动号段
		// 134、135、136、137、138、139、150、151、152、157、158、159、147、182、183、184[1]、187、188
		// 中国联通号段
		// 130、131、132、145、155、156、185、186、145(属于联通无线上网卡号段)
		// 中国电信号段 133 、153 、180 、181 、189
		String regExp = "^[1]([3][0-9]{1}|[5][012356789]{1}|[4][57]{1}|[8][0-9]{1})[0-9]{8}$";
		try {
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(strPhoneNumber);
			if (m.find()) {
				return true;
			}
			return false;
		} catch (Exception ety) {
			return false;
		}

	}

	/**
	 * Title: 返回传入日期的前一天 Description:
	 * 
	 * @param aa
	 *            日期字符串 ,格式yyyy-MM-dd
	 * @return 返回一个新的字符日期,格式yyyy-MM-dd
	 */

	public static String getYesterDay(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, -1); // 减1天
		return df.format(cal.getTime());
	}

	/**
	 * Title: 返回传入日期的前一天 Description:
	 * 
	 * @param aa
	 *            日期字符串 ,格式yyyy-MM-dd
	 * @return 返回一个新的字符日期,格式yyyy-MM-dd
	 */

	public static String getNextMonthDay(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat(YYYYMMDD);
		Date d = null;
		try {
			d = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, 1); // 加1月
		return df.format(cal.getTime());
	}

	/**
	 * 获取日期的前几天
	 * 
	 * @param dateStr
	 *            传入的时间 格式“yyyy-MM-dd”
	 * @param DayNum
	 *            减去的天数
	 * @return 减去后的日期，格式“yyyy-MM-dd”
	 */
	public static String subDay(String dateStr, int DayNum) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, -DayNum); // 减 DayNum 天
		return df.format(cal.getTime());
	}

	/**
	 * Title: 返回传入日期的前一天 Description:
	 * 
	 * @param aa
	 *            日期字符串 ,格式yyyy-MM-dd
	 * @return 返回一个新的字符日期,格式yyyy-MM-dd
	 */

	public static Date dateStrTDate(String dateStr, String toPattern) {
		SimpleDateFormat df = null;
		if (toPattern != null)
			df = new SimpleDateFormat(toPattern);
		else
			df = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除的文件路径+文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取时间的秒总数 Title:
	 * 
	 * @param time
	 *            例如: 01:22:12
	 * @return 日期:2013-8-20 author 唐登强
	 */

	public static int getCountSecond(String time) {
		String[] my = time.split(":");
		int hour = Integer.parseInt(my[0]);
		int min = Integer.parseInt(my[1]);
		int sec = Integer.parseInt(my[2]);

		int zong = hour * 3600 + min * 60 + sec;
		return zong;
	}

	/**
	 * 把秒数转换为时分秒 格式 HH:mm:ss Title:
	 * 
	 * @param sec
	 * @return 日期:2013-8-20 author 唐登强
	 */

	public static String getSecondToTime(String secStr) {
		if (null == secStr) {
			return "00:00:00";
		}
		long sec = Long.parseLong(secStr);
		long hour = sec / 3600; // 小时
		long minute = sec % 3600 / 60; // 分钟
		long second = sec % 60; // 秒
		String b = "";
		String c = "";
		String d = "";
		if (second < 10) {
			b = "0";
		}

		if (minute < 10) {
			c = "0";
		}
		if (hour < 10) {
			d = "0";
		}

		return d + hour + ":" + c + minute + ":" + b + second;
	}

	/**
	 * 字节数组解压缩后返回字符串 Title:
	 * 
	 * @param b
	 * @return 日期:2013-8-23 author 唐登强
	 */

	public static String uncompressToString(byte[] b) {
		if (b == null || b.length == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(b);
		try {
			GZIPInputStream gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
		} catch (IOException e) {
			// TODO 需要处理异常
			e.printStackTrace();
		}
		return out.toString();
	}

	/**
	 * 获取替换后的字符串
	 * 
	 * @param strInput
	 *            替换的名称和内容数组.
	 * @param replaceParamters
	 *            .参数格式：paramter x=-=x value
	 * @return
	 * @author denghw 20130828
	 */
	public static String ReplaceSplitStr = "x=-=x";// 待替换键值对的分隔符

	public static String getReplaceString(String strInput, List<String> replaceParamters) {
		String tarString = strInput;
		try {
			if (!isBlank(strInput)) {

				for (String replaceOne : replaceParamters) {//
					if (!isBlank(replaceOne)) {
						if (replaceOne.indexOf(ReplaceSplitStr) != -1) {// 找到关键字符=
							String paramter = replaceOne.substring(0, replaceOne.indexOf(ReplaceSplitStr));
							String value = replaceOne.substring(replaceOne.indexOf(ReplaceSplitStr) + ReplaceSplitStr.length(), replaceOne.length());
							tarString = tarString.replaceAll(paramter, value);
						}
					}
				}
				return tarString;
			}
		} catch (Exception e) {
			return strInput;
		}
		return strInput;
	}

	/**
	 * 取得Document 节点的键值对 . key=nodeName,value=nodeValue
	 * 
	 * @param Document
	 * @param ParentNodeName
	 *            需要转换键值对的父节点名
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked") public static HashMap<String, String>
	 * getNodeKeyValueByDocument( Document Document, String ParentNodeName) {
	 * HashMap<String, String> rtnMap = new HashMap<String, String>(); if
	 * (isBlank(ParentNodeName)) return rtnMap; Element rootElement =
	 * Document.getRootElement(); List<Element> tr =
	 * rootElement.getChildren(ParentNodeName); if (!isBlank(tr)) { Element
	 * trFirst = tr.get(0);// 若有存在多个节点,仅取第一个节点 List<Element> trFirstChirs =
	 * trFirst.getChildren(); for (Element rkOne : trFirstChirs) {
	 * rtnMap.put(rkOne.getName(), rkOne.getTextTrim()); } } return rtnMap; }
	 *//**
	 * 对Document某节点下的 子节点重新赋值
	 * 
	 * @param Document
	 * @param ParentNodeName
	 *            需要转换键值对的父节点名
	 * @param ParentNodeName
	 *            需转换的键值对
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked") public static Document
	 * setDocumentByNodeKeyValue(Document Document, String ParentNodeName,
	 * List<String> setValues) { if (CommUtil.isBlank(ParentNodeName)) return
	 * Document; Element rootElement = Document.getRootElement(); List<Element>
	 * tr = rootElement.getChildren(ParentNodeName); if (!isBlank(tr)) { Element
	 * trFirst = tr.get(0);// 若有存在多个节点,仅取第一个节点 for (String replaceOne :
	 * setValues) {// if (!isBlank(replaceOne)) { if
	 * (replaceOne.indexOf(ReplaceSplitStr) != -1) {// 找到关键字符= String paramter =
	 * replaceOne.substring(0, replaceOne .indexOf(ReplaceSplitStr)); String
	 * value = replaceOne.substring(replaceOne .indexOf(ReplaceSplitStr),
	 * replaceOne.length()); List<Element> paramters =
	 * trFirst.getChildren(paramter); if (!isBlank(paramters)) { Element
	 * findParamterOne = paramters.get(0);// 若有存在多个节点,仅取第一个节点
	 * findParamterOne.setText(value); } } } } } return Document; }
	 */

	/**
	 * 将格林时间转为中国时区的时间
	 * 
	 * @param inputDate
	 *            格式:yyyy-MM-dd hh:mm:ss
	 * @return 转换后的时间 ,
	 */
	public static String getStardTime(String inputDate) {
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		df.setTimeZone(TimeZone.getTimeZone("GMT+8"));

		return addTime(df.format(strToDate(inputDate, null)), "08");
	}

	/**
	 * GMT时间，转化为UTC时间
	 * 
	 * @param gmtDatetime
	 * @return
	 */
	public static String getCSTDate(String gmtDatetime) {
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzzz yyyy", Locale.ENGLISH);
		Date dd;
		try {
			dd = df.parse(gmtDatetime);
			SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			// sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			return sdf.format(dd);
		} catch (ParseException e) {
			e.printStackTrace();
			return gmtDatetime;
		}
	}

	/**
	 * 全角转半角的 转换函数
	 * 
	 * @Methods Name full2HalfChange
	 * @Create In 2012-8-24 By v-jiangwei
	 * @param QJstr
	 * @return String
	 */
	public static final String full2HalfChange(String QJstr) {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			// 全角空格转换成半角空格
			if (Tstr.equals("　")) {
				outStrBuf.append(" ");
				continue;
			}
			try {
				b = Tstr.getBytes("unicode");
				// 得到 unicode 字节数据
				if (b[2] == -1) {
					// 表示全角
					b[3] = (byte) (b[3] + 32);
					b[2] = 0;
					outStrBuf.append(new String(b, "unicode"));
				} else {
					outStrBuf.append(Tstr);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // end for.
		return outStrBuf.toString();
	}

	/**
	 * 判断字符串是否为全半角
	 * 
	 * @param str
	 * @return 0-都是半角,1-都是全角,2-全角半角都有
	 */
	public static byte checkString(String str) {
		if (isBlank(str)) {
			return 0;
		}
		int length = str.length();
		int bytLength = str.getBytes().length;
		// 都是半角的情况
		if (bytLength == length) {
			return 0;
		}
		// 都是全角的情况
		else if (bytLength == 3 * length) {
			return 1;
		}
		// 有全角有半角
		else {
			return 2;
		}
	}

	public static boolean getEmail(String line) {
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(line);
		return m.find();
	}

	/**
	 * 天数相减
	 */
	public static String subtractionDay(int size) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - size);
		String endDate = dft.format(date.getTime());
		return endDate;
	}

	public static String getWeek(String  dateStr) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = dft.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1 ;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];

	}

}
