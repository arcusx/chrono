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

/**
 *
 * @author conni
 * @version $Id$
 */
public final class GregorianUtils
{
	public static final int MIN_GREGORIAN_YEAR = 1582;
	public static final int MIN_GREGORIAN_MOTNH1 = 10;
	public static final int MIN_GREGORIAN_DAY = 15;

	public static void checkValidGregorianDate(int year, int month1, int day)
	{
		if (year > MIN_GREGORIAN_YEAR)
			return;

		if (year == MIN_GREGORIAN_YEAR && month1 > MIN_GREGORIAN_MOTNH1)
			return;

		if (year == MIN_GREGORIAN_YEAR && month1 == MIN_GREGORIAN_MOTNH1 && day >= MIN_GREGORIAN_DAY)
			return;

		throw new IllegalArgumentException("Date before 1582-10-15 not allowed.");
	}
}
