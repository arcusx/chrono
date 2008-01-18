/**
 *
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
 * Fax.: +49 (0)40.333 102 93
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 *
 */

package com.arcusx.chrono;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author conni
 * @version $Id$
 */
public final class DayOfWeek implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final DayOfWeek SUNDAY = new DayOfWeek(0, "Sunday");
	public static final DayOfWeek MONDAY = new DayOfWeek(1, "Monday");
	public static final DayOfWeek TUESDAY = new DayOfWeek(2, "Tuesday");
	public static final DayOfWeek WEDNESDAY = new DayOfWeek(3, "Wednesday");
	public static final DayOfWeek THURSDAY = new DayOfWeek(4, "Thursday");
	public static final DayOfWeek FRIDAY = new DayOfWeek(5, "Friday");
	public static final DayOfWeek SATURDAY = new DayOfWeek(6, "Saturday");

	public static final List<DayOfWeek> ALL = Arrays.asList(new DayOfWeek[] { SUNDAY,
			MONDAY,
			TUESDAY,
			WEDNESDAY,
			THURSDAY,
			FRIDAY,
			SATURDAY});

	private int offset;

	private String name;

	public static DayOfWeek valueOf(int year, MonthOfYear monthOfYear, int day)
	{
		int offset = calcDayOfWeek(year, monthOfYear.getMonthValue(), day);

		return DayOfWeek.valueOf(offset);
	}

	public static DayOfWeek valueOf(int offset)
	{
		offset = offset % 7;

		if (offset < 0)
			throw new IllegalArgumentException("Invalid offset: " + offset);

		return ALL.get(offset);
	}

	private DayOfWeek(int id, String name)
	{
		this.offset = id;
		this.name = name;
	}

	/**
	 * @return the offset
	 */
	public int getOffset()
	{
		return this.offset;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		return this.offset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object otherObj)
	{
		if (this == otherObj)
			return true;

		if (null == otherObj)
			return false;

		if (!getClass().equals(otherObj.getClass()))
			return false;

		DayOfWeek other = (DayOfWeek) otherObj;

		return other.offset == this.offset;
	}

	@Override
	public String toString()
	{
		return "DayOfWeek{offset=" + this.offset + ";name=" + this.name + "}";
	}

	/**
	 * @param year
	 * @param month0
	 * @param day
	 * @return
	 */
	private static int calcDayOfWeek(int year, int month0, int day)
	{
		int month1 = month0+1;

		int a = (14 - month1) / 12;
		int y = year - a;
		int m = month1 + 12 * a - 2;
		int d = (day + y + y / 4 - y / 100 + y / 400 + 31 * m / 12) % 7;

		return d;
	}
}
