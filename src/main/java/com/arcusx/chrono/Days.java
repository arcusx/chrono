
package com.arcusx.chrono;

public class Days
{
	public static int between(Day one, Day other)
	{
		if (one.after(other))
		{
			return betweenInternal(other, one);
		}
		else
		{
			return betweenInternal(one, other);
		}
	}

	private static int betweenInternal(Day a, Day b)
	{
		if (a.getMonth().equals(b.getMonth()))
		{
			return b.getDayValue() - a.getDayValue();
		}
		else
		{
			int sum = 0;
			while (!a.getMonth().equals(b.getMonth()))
			{
				Day lastDayOfAMonth = a.getMonth().getLastDay();
				sum += betweenInternal(a, lastDayOfAMonth) + 1;
				a = a.getMonth().add(1).getFirstDay();
			}
			sum += betweenInternal(a, b);
			return sum;
		}
	}
}
