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
import java.text.*;
import java.util.*;

/**
 * A concrete month of a concrete year.<br>
 * A months has value semantics, i.e. it is immutable.
 * 
 * Created 30.05.2003, 12:36:44.
 * 
 * @author conni
 * @version $Id$
 */
public class Month implements Serializable, Comparable
{

	private static final long serialVersionUID = 1L;

	public static final int JANUARY = Calendar.JANUARY;

	public static final int FEBRUARY = Calendar.FEBRUARY;

	public static final int MARCH = Calendar.MARCH;

	public static final int APRIL = Calendar.APRIL;

	public static final int MAY = Calendar.MAY;

	public static final int JUNE = Calendar.JUNE;

	public static final int JULY = Calendar.JULY;

	public static final int AUGUST = Calendar.AUGUST;

	public static final int SEPTEMBER = Calendar.SEPTEMBER;

	public static final int OCTOBER = Calendar.OCTOBER;

	public static final int NOVEMBER = Calendar.NOVEMBER;

	public static final int DECEMBER = Calendar.DECEMBER;

	private int year;

	private int month;

	/**
	 * Get the month of the current day.
	 * 
	 * @return Instance of current month.
	 */
	public static Month current()
	{
		Calendar cal = new GregorianCalendar();

		return Month.valueOf(cal);
	}

	/**
	 * Get month from Calendar.
	 * 
	 * @param cal Calendar to get month from.
	 * @return The month.
	 */
	public static Month valueOf(Calendar cal)
	{
		if (cal == null)
			throw new IllegalArgumentException("Calendar may not be null.");

		return new Month(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}

	/**
	 * Get month from a date or default value if date is null.
	 * 
	 * @param date Date to get month from.
	 * @param defaultValue The default vakleu to use if date is null.
	 * @return Month or default value.
	 */
	public static Month valueOf(Date date, Month defaultValue)
	{
		if (date == null)
			return defaultValue;

		return Month.valueOf(date);
	}

	public static Month valueOf(Date date)
	{
		if (date == null)
			throw new IllegalArgumentException("Date may not be null.");

		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		return Month.valueOf(cal);
	}

	/**
	 * A factory method to build this type via refelction
	 * from string.
	 * 
	 * @param s The string.
	 * @return The month.
	 * @throws ParseException if the string could not be parsed.
	 */
	public static Month valueOf(String s) throws ParseException
	{
		if (s.startsWith("Month{") && s.endsWith("}"))
			s = s.substring("Month{".length(), s.length() - 1);

		return SimpleMonthFormat.INSTANCE.parse(s);
	}

	public Month(int year, MonthOfYear month)
	{
		this(year, month.getMonthValue());
	}

	public Month(int year, int month)
	{
		if (month < JANUARY || month > DECEMBER)
			throw new IllegalArgumentException("Must be between 1 and 11 (is " + month + ")");

		this.year = year;
		this.month = month;
	}

	public Year getYear()
	{
		return new Year(this.year);
	}

	public int getYearValue()
	{
		return this.year;
	}

	public int getMonthValue()
	{
		return this.month;
	}

	public MonthOfYear getMonthOfYear()
	{
		return MonthOfYear.valueOf(this.month);
	}

	public Day getFirstDay()
	{
		Calendar cal = new GregorianCalendar();
		cal.set(this.year, this.month, 1, 0, 0, 0);

		return Day.valueOf(cal);
	}

	public Day getLastDay()
	{
		Calendar cal = new GregorianCalendar();
		cal.set(this.year, this.month, 1, 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		return Day.valueOf(cal);
	}

	//
	// comparison
	//

	public int hashCode()
	{
		return this.year * 12 + this.month;
	}

	public boolean before(Month other)
	{
		return (this.year < other.year) || (this.year == other.year && this.month < other.month);
	}

	public boolean beforeOrEqual(Month other)
	{
		return before(other) || equals(other);
	}

	public boolean after(Month other)
	{
		return (this.year > other.year) || (this.year == other.year && this.month > other.month);
	}

	public boolean afterOrEqual(Month other)
	{
		return after(other) || equals(other);
	}

	public int compareTo(Object other)
	{
		if (!(other instanceof Month))
			throw new IllegalArgumentException("Can compare month only to other month.");

		Month otherMonth = (Month) other;

		return before(otherMonth) ? -1 : equals(otherMonth) ? 0 : 1;
	}

	/**
	 * Month is equal to any other month of same year and month value.
	 * 
	 * @param other The other object.
	 * @return True if other is month of same year and month value.
	 */
	public boolean equals(Object other)
	{
		if (this == other)
			return true;

		if (!(other instanceof Month))
			return false;

		Month otherMonth = (Month) other;
		return otherMonth.year == this.year && otherMonth.month == this.month;
	}

	//
	// aritmetics
	//

	public Month subtract(int months)
	{
		return add(-months);
	}

	public Month add(int months)
	{
		Calendar cal = getFirstDay().toCalendar();
		cal.add(Calendar.MONTH, months);

		return Month.valueOf(cal);
	}

	//
	// conversions
	//

	public Days toDays()
	{
		return new Days(getFirstDay(), getLastDay());
	}

	//
	// misc
	//

	/**
	 * Get the smaller one of two months.
	 * 
	 * @param one One month, not null.
	 * @param one Othe month, not null.
	 * @return The smaller month.
	 */
	public static Month minOf(Month one, Month other)
	{
		return minOf(new Month[]
		{ one, other }, false);
	}

	/**
	 * Get the smallest one of three months.
	 * 
	 * @param one One month, not null.
	 * @param second Othe month, not null.
	 * @param third Othe month, not null.
	 * @return The smallest month.
	 */
	public static Month minOf(Month one, Month second, Month third)
	{
		return minOf(new Month[]
		{ one, second, third }, false);
	}

	public static Month minOf(Month[] months)
	{
		return minOf(months, false);
	}

	/**
	 * Get minimum of multiple months.
	 * 
	 * @param months Array of months to get minimum from.
	 * @return ignoreNulls If false null leads to an error.
	 * @return The minimum or null if array is of length 0 or contains nulls only.
	 * @throws IllegalArgumentException if ignoreNulls is false and an array element is null.
	 */
	public static Month minOf(Month[] months, boolean ignoreNulls)
	{
		Month min = null;
		for (int i = 0; i < months.length; ++i)
		{
			if (!ignoreNulls && months[i] == null)
				throw new IllegalArgumentException("Month may not be null.");

			if (min == null || (months[i] != null && months[i].before(min)))
				min = months[i];
		}

		return min;
	}

	/**
	 * Get the later one of two months.
	 * 
	 * @param one One month, not null.
	 * @param one Othe month, not null.
	 * @return The latest month.
	 */
	public static Month maxOf(Month one, Month other)
	{
		return maxOf(new Month[]
		{ one, other }, false);
	}

	public static Month maxOf(Month one, Month second, Month third)
	{
		return maxOf(new Month[]
		{ one, second, third }, false);
	}

	public static Month maxOf(Month[] months)
	{
		return maxOf(months, false);
	}

	/**
	 * Get maximum of multiple months.
	 * 
	 * @param months Array of months to get maximum from.
	 * @return ignoreNulls If false null leads to an error.
	 * @return The maximum or null if array is of length 0 or contains nulls only.
	 * @throws IllegalArgumentException if ignoreNulls is false and an array element is null.
	 */
	public static Month maxOf(Month[] months, boolean nullAllowed)
	{
		Month max = null;
		for (int i = 0; i < months.length; ++i)
		{
			if (!nullAllowed && months[i] == null)
				throw new IllegalArgumentException("Month may not be null.");

			if (max == null || (months[i] != null && months[i].after(max)))
				max = months[i];
		}

		return max;
	}

	/**
	 * Calculate a sequence of n months that are first of a period splitting
	 * the year into pieces of equal months count.
	 * 
	 * @param periodLenInMonths Length in months of a period. Must split the year into equal pieces.
	 * @param offMonth The month offset.
	 * @return Sequence of months.
	 * @deprecated Is in wrong place.
	 */
	public static int[] getPeriodStartMonths(int periodLenInMonths, int offMonth)
	{
		if (12 % periodLenInMonths != 0)
			throw new IllegalArgumentException("Period len in months must divide the year into equal pieces.");

		int[] months = new int[12 / periodLenInMonths];
		int monthsI = 0;
		for (int i = 0; i < 12; ++i)
		{
			if (((i + offMonth) % periodLenInMonths) == (offMonth % periodLenInMonths))
			{
				months[monthsI++] = (i + offMonth) % 12;
			}
		}

		return months;
	}

	public String toString()
	{
		return "Month{" + SimpleMonthFormat.INSTANCE.format(this) + "}";
	}
}