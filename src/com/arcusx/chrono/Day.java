/**
 * This software is written by arcus(x) GmbH and subject 
 * to a contract between arcus(x) and its customer.
 *
 * This software stays property of arcus(x) unless differing
 * arrangements between arcus(x) and its customer apply.
 *
 * arcus(x) GmbH
 * Bergiusstrasse 27
 * D-22765 Hamburg, Germany
 * 
 * Tel.: +49 (0)40.333 102 92  
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 */

package com.arcusx.chrono;

import java.io.*;
import java.util.*;

/**
 * A day is a date describing a day by
 * its day of month, a month of year and a year.<br>
 * A day has value semantics, i.e. it is immutable.
 * 
 * Created 02.10.2003, 16:06:03.
 * 
 * @author conni
 * @version $Id$
 */
public class Day implements Serializable, Comparable
{

	private static final long serialVersionUID = 1L;

	private int year;

	private int month;

	private int day;

	/**
	 * Get the smaller one of two days.
	 * 
	 * @param one One day, not null.
	 * @param one Othe day, not null.
	 * @return The smaller day.
	 */
	public static Day minOf(Day one, Day other)
	{
		return minOf(new Day[]
		{ one, other }, false);
	}

	public static Day minOf(Day one, Day second, Day third)
	{
		return minOf(new Day[]
		{ one, second, third }, false);
	}

	public static Day minOf(Day[] months)
	{
		return minOf(months, false);
	}

	/**
	 * Get minimum of multiple days.
	 * 
	 * @param days Array of days to get minimum from.
	 * @return ignoreNulls If false null leads to an error.
	 * @return The minimum or null if array is of length 0 or contains nulls only.
	 * @throws IllegalArgumentException if ignoreNulls is false and an array element is null.
	 */
	public static Day minOf(Day[] days, boolean nullAllowed)
	{
		Day min = null;
		for (int i = 0; i < days.length; ++i)
		{
			if (!nullAllowed && days[i] == null)
				throw new IllegalArgumentException("Day may not be null.");

			if (min == null || (days[i] != null && days[i].before(min)))
				min = days[i];
		}

		return min;
	}

	/**
	 * Get the later one of two days.
	 * 
	 * @param one One day, not null.
	 * @param one Othe day, not null.
	 * @return The later day.
	 */
	public static Day maxOf(Day one, Day other)
	{
		return maxOf(new Day[]
		{ one, other }, false);
	}

	public static Day maxOf(Day one, Day second, Day third)
	{
		return maxOf(new Day[]
		{ one, second, third }, false);
	}

	public static Day maxOf(Day[] days)
	{
		return maxOf(days, false);
	}

	/**
	 * Get maximum of multiple days.
	 * 
	 * @param days Array of days to get maximum from.
	 * @return ignoreNulls If false null leads to an error.
	 * @return The maximum or null if array is of length 0 or contains nulls only.
	 * @throws IllegalArgumentException if ignoreNulls is false and an array element is null.
	 */
	public static Day maxOf(Day[] days, boolean nullAllowed)
	{
		Day max = null;
		for (int i = 0; i < days.length; ++i)
		{
			if (!nullAllowed && days[i] == null)
				throw new IllegalArgumentException("Day may not be null.");

			if (max == null || (days[i] != null && days[i].after(max)))
				max = days[i];
		}

		return max;
	}

	public static Day today()
	{
		return Day.current();
	}

	public static Day current()
	{
		Calendar cal = new GregorianCalendar();

		return Day.valueOf(cal);
	}

	public static Day valueOf(Date date)
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		return Day.valueOf(cal);
	}

	public static Day valueOf(Date date, Day defaultDay)
	{
		if (date == null)
			return defaultDay;

		return Day.valueOf(date);
	}

	public static Day valueOf(Calendar cal, Day defaultDay)
	{
		if (cal == null)
			return defaultDay;

		return Day.valueOf(cal);
	}

	public static Day valueOf(Calendar cal)
	{
		return new Day(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
	}

	/**
	 * Create a day instance.
	 * 
	 * @param year The year. 1970 is the year 1970.
	 * @param month The month. 0 ist January, 1 February, 2 ...
	 * @param day The day of month, the first is 1.
	 * @throws IllegalArgumentException if the given values are no valid date.
	 */
	public Day(int year, int month, int day)
	{
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);

		int calYear = cal.get(Calendar.YEAR);
		int calMonth = cal.get(Calendar.MONTH);
		int calDay = cal.get(Calendar.DATE);
		if (calYear != year || calMonth != month || calDay != day)
			throw new IllegalArgumentException(year + "/" + month + "/" + day + " is not a valid date.");

		this.year = year;
		this.month = month;
		this.day = day;
	}

	public int getYearValue()
	{
		return this.year;
	}

	public int getMonthValue()
	{
		return this.month;
	}

	public int getDayValue()
	{
		return this.day;
	}

	public Year getYear()
	{
		return new Year(this.year);
	}

	public Month getMonth()
	{
		return new Month(this.year, this.month);
	}

	public boolean isLastOfMonth()
	{
		Calendar cal = toCalendar();
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH) == this.day;
	}

	/**
	 * Test if the day is the first day of its month.
	 * 
	 * @return True is it is the first of month.
	 */
	public boolean isFirstOfMonth()
	{
		return this.day == 1;
	}

	//
	// comparison
	//

	public boolean before(Day other)
	{
		if (this.year < other.year)
			return true;
		if (this.year > other.year)
			return false;
		if (this.month < other.month)
			return true;
		if (this.month > other.month)
			return false;

		return this.day < other.day;
	}

	public boolean beforeOrEqual(Day other)
	{
		return before(other) || equals(other);
	}

	public boolean after(Day other)
	{
		if (this.year > other.year)
			return true;
		if (this.year < other.year)
			return false;
		if (this.month > other.month)
			return true;
		if (this.month < other.month)
			return false;

		return this.day > other.day;
	}

	public boolean afterOrEqual(Day other)
	{
		return after(other) || equals(other);
	}

	public boolean equals(Object other)
	{
		if (other == null)
			return false;

		if (!Day.class.equals(other.getClass()))
			return false;

		Day otherDay = (Day) other;

		return this.year == otherDay.year && this.month == otherDay.month && this.day == otherDay.day;
	}

	public int hashCode()
	{
		return this.year * 100 + this.month * 100 + this.day * 100;
	}

	public int compareTo(Object other)
	{
		if (!(other instanceof Day))
			throw new IllegalArgumentException("Can compare day only to other day.");

		Day otherDay = (Day) other;

		return before(otherDay) ? -1 : equals(otherDay) ? 0 : 1;
	}

	//
	// aritmetics
	//

	public Day subtract(int days)
	{
		return add(-days);
	}

	public Day add(int days)
	{
		Calendar cal = toCalendar();
		cal.add(Calendar.DAY_OF_MONTH, days);

		return Day.valueOf(cal);
	}

	//
	// conversions
	//

	public long toMillis()
	{
		return toCalendar().getTimeInMillis();
	}

	public Date toDate()
	{
		return toCalendar().getTime();
	}

	public Calendar toCalendar()
	{
		Calendar cal = new GregorianCalendar();
		cal.set(this.year, this.month, this.day, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	/**
	 * A helper to simply convert a day to a java.sql.Date
	 * 
	 * @return The day as java.sql.Date
	 */
	public java.sql.Date toJavaSqlDate()
	{
		return new java.sql.Date(toCalendar().getTimeInMillis());
	}

	public String toString()
	{
		return "Day{" + this.year + ", " + MonthOfYear.valueOf(this.month).getShortName() + ", " + this.day + "}";
	}
}