
package com.arcusx.chrono.tests;

import org.junit.Test;

import com.arcusx.chrono.Day;
import com.arcusx.chrono.Days;

import static org.junit.Assert.assertEquals;

public class DaysTest
{
	@Test
	public void sameDay()
	{
		Day aDay = Day.valueOf("2014-01-01");
		assertEquals(0, Days.between(aDay, aDay));
	}

	@Test
	public void subsequentDays()
	{
		Day aDay = Day.valueOf("2014-01-01");
		assertEquals(1, Days.between(aDay, aDay.add(1)));
	}

	@Test
	public void over2Month()
	{
		Day aDay = Day.valueOf("2014-03-01");
		assertEquals(61, Days.between(aDay, aDay.getMonth().add(2).getFirstDay()));
	}

	@Test
	public void over2MonthSwapped()
	{
		Day aDay = Day.valueOf("2014-03-01");
		assertEquals(61, Days.between(aDay.getMonth().add(2).getFirstDay(), aDay));
	}
}
