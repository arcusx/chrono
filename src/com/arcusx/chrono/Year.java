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
 * A date with year part only.
 * 
 * Created 02.10.2003, 16:34:42.
 * 
 * @author conni
 * @version $Id$
 */
public class Year implements Serializable, Comparable
{
	private static final long serialVersionUID = 1L;

	private int year;

	public static Year current()
	{
		GregorianCalendar cal = new GregorianCalendar();

		return Year.valueOf(cal);
	}

	public static Year valueOf(String s)
	{
		if (s.startsWith("Year{") && s.endsWith("}"))
			s = s.substring("Year{".length(), s.length() - 1);

		return new Year(Integer.parseInt(s));
	}

	public static Year valueOf(Date date)
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);

		return Year.valueOf(cal);
	}

	public static Year valueOf(Calendar cal)
	{
		return new Year(cal.get(Calendar.YEAR));
	}

	public Year(int year)
	{
		this.year = year;
	}

	public Month getFirstMonth()
	{
		return new Month(this.year, Month.JANUARY);
	}

	public Month getLastMonth()
	{
		return new Month(this.year, Month.DECEMBER);
	}

	public int getYearValue()
	{
		return this.year;
	}

	public int hashCode()
	{
		return this.year;
	}

	public int compareTo(Object other)
	{
		if (!(other instanceof Year))
			throw new IllegalArgumentException("Year cannot be compared to " + other);

		return this.year - ((Year) other).year;
	}

	public boolean equals(Object other)
	{
		if (other == null)
			return false;

		if (!Year.class.equals(other.getClass()))
			return false;

		Year otherYear = (Year) other;

		return otherYear.year == this.year;
	}

	public boolean before(Year other)
	{
		return this.year < other.year;
	}

	public boolean beforeOrEqual(Year other)
	{
		return this.year <= other.year;
	}

	public boolean after(Year other)
	{
		return this.year > other.year;
	}

	public boolean afterOrEqual(Year other)
	{
		return this.year >= other.year;
	}

	public Months toMonths()
	{
		return new Months(getFirstMonth(), getLastMonth());
	}

	public Year add(int years)
	{
		return new Year(this.year + years);
	}

	public Year subtract(int years)
	{
		return add(-years);
	}

	public String toString()
	{
		return "Year{" + this.year + "}";
	}
}