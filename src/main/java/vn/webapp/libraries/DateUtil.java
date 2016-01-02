package vn.webapp.libraries;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static String _dateFormatType1 = "dd/MM/yyyy";
	public static String _dateFormatType2 = "yyyy-MM-dd";
	
	/**
	 * Get Format input dd/MM/yyyy : 01/02/2016 
	 * @param sDate
	 * @return
	 */
	public static LocalDate o_fFormatDateByFormatType1(String sDate){
		try{
			DateTimeFormatter oFormatter = DateTimeFormatter.ofPattern(DateUtil._dateFormatType1, Locale.ENGLISH);
			LocalDate oFormatedDate = LocalDate.parse(sDate, oFormatter);
			return oFormatedDate;
		}catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Get Format input yyyy-MM-dd : 2016-01-02 
	 * @param sDate  
	 * @return
	 */
	public static LocalDate o_fFormatDateByFormatType2(String sDate){
		try{
			DateTimeFormatter oFormatter = DateTimeFormatter.ofPattern(DateUtil._dateFormatType2, Locale.ENGLISH);
			LocalDate oFormatedDate = LocalDate.parse(sDate, oFormatter);
			return oFormatedDate;
		}catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param sDate
	 * @return
	 */
	public static String s_fConvertDateFormatType1(String sDate){
		try{
			DateTimeFormatter oFormatter = DateTimeFormatter.ofPattern(DateUtil._dateFormatType1, Locale.ENGLISH);
			LocalDate oFormatedDate = LocalDate.parse(sDate, oFormatter);
			String sYear = Integer.toString(oFormatedDate.getYear());
			String sMonth = oFormatedDate.getMonthValue() > 9 ? Integer.toString(oFormatedDate.getMonthValue()) : "0"+oFormatedDate.getMonthValue();
			String sDay = Integer.toString(oFormatedDate.getDayOfMonth());
			
			return sYear+"-"+sMonth+"-"+sDay;
		}catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static String s_fGetCurrentDate()
	{
		Date currentDate = new Date();
		SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("HHmmssddMMyyyy");
		return dateformatyyyyMMdd.format(currentDate);
	}
}
