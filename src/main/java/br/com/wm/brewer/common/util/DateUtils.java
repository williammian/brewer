package br.com.wm.brewer.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {
	public static long dateDiff(LocalTime ini, LocalTime fim, ChronoUnit unit) {
		return dateDiff(LocalDateTime.of(LocalDate.now(), ini), LocalDateTime.of(LocalDate.now(), fim), unit);
	}
	
	public static long dateDiff(LocalDate ini, LocalDate fim, ChronoUnit unit) {
		return dateDiff(LocalDateTime.of(ini, LocalTime.of(1, 0, 0)), LocalDateTime.of(fim, LocalTime.of(1, 0, 0)), unit);
	}
	
	public static long dateDiff(LocalDateTime ini, LocalDateTime fim, ChronoUnit unit) { 
		switch (unit) {
		case SECONDS:
			return unit.between(
						LocalDateTime.of(ini.getYear(), ini.getMonth(), ini.getDayOfMonth(), ini.getHour(), ini.getMinute(), ini.getSecond(), 0), 
						LocalDateTime.of(fim.getYear(), fim.getMonth(), fim.getDayOfMonth(), fim.getHour(), fim.getMinute(), fim.getSecond(), 0)
					);
		case MINUTES:
			return unit.between(
					LocalDateTime.of(ini.getYear(), ini.getMonth(), ini.getDayOfMonth(), ini.getHour(), ini.getMinute(), 0, 0), 
					LocalDateTime.of(fim.getYear(), fim.getMonth(), fim.getDayOfMonth(), fim.getHour(), fim.getMinute(), 0, 0)
				  );
		case HOURS:
			return unit.between(
					LocalDateTime.of(ini.getYear(), ini.getMonth(), ini.getDayOfMonth(), ini.getHour(), 0, 0, 0), 
					LocalDateTime.of(fim.getYear(), fim.getMonth(), fim.getDayOfMonth(), fim.getHour(), 0, 0, 0)
				  );
		case DAYS:
			return unit.between(
					LocalDateTime.of(ini.getYear(), ini.getMonth(), ini.getDayOfMonth(), 1, 0, 0, 0), 
					LocalDateTime.of(fim.getYear(), fim.getMonth(), fim.getDayOfMonth(), 1, 0, 0, 0)
				  );
		case MONTHS:
			return unit.between(
					LocalDateTime.of(ini.getYear(), ini.getMonth(), 1, 1, 0, 0, 0), 
					LocalDateTime.of(fim.getYear(), fim.getMonth(), 1, 1, 0, 0, 0)
				  );
		case YEARS:
			return unit.between(
					LocalDateTime.of(ini.getYear(), 1, 1, 1, 0, 0, 0), 
					LocalDateTime.of(fim.getYear(), 1, 1, 1, 0, 0, 0)
				  );
		default:
			return unit.between(ini, fim);
		}
	}
	public static long dateDiffWorkMinutes(LocalDateTime ini, LocalDateTime fim) {
		return dateDiff(ini, fim, ChronoUnit.MINUTES) + 1;
	}
	public static long dateDiffWorkMinutes(LocalTime ini, LocalTime fim) {
		return dateDiff(ini, fim, ChronoUnit.MINUTES) + 1;
	}
	public static LocalDate[] getStarAndEndMonth(LocalDate base) {
		return new LocalDate[] {base.withDayOfMonth(1), base.withDayOfMonth(base.lengthOfMonth())};
	}
	
	
	//PaseUtils
	public static LocalDate parseDate(String value)  {
		return parseDate(value, "dd/MM/yyyy");
	}
	public static LocalDate parseDate(String value, String pattern)  {
		return LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern));
	}
	public static LocalDateTime parseDateTime(String value)  {
		return parseDateTime(value, "dd/MM/yyyy HH:mm");
	}
	public static LocalDateTime parseDateTime(String value, String pattern)  {
		return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
	}
	public static LocalTime parseTime(String value)  {
		return parseTime(value, "HH:mm");
	}
	public static LocalTime parseTime(String value, String pattern)  {
		return LocalTime.parse(value, DateTimeFormatter.ofPattern(pattern));
	}
	
	//FormatUtils
	public static String formatDate(LocalDate value)  {
		return formatDate(value, "dd/MM/yyyy");
	}
	public static String formatDate(LocalDate value, String pattern)  {
		return value.format(DateTimeFormatter.ofPattern(pattern));
	}
	public static String formatDateTime(LocalDateTime value)  {
		return formatDateTime(value, "dd/MM/yyyy HH:mm");
	}
	public static String formatDateTime(LocalDateTime value, String pattern)  {
		return value.format(DateTimeFormatter.ofPattern(pattern));
	}
	
	public static String formatTime(LocalDate value)  {
		return formatDate(value, "HH:mm");
	}
	public static String formatTime(LocalTime value, String pattern)  {
		return value.format(DateTimeFormatter.ofPattern(pattern));
	}
}

