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
 * Fax.: +49 (0)40.333 102 93 
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 *
 */

package com.arcusx.chrono;

import java.text.*;

/**
 * 
 * Created on 22.02.2005, 17:13:16.
 *
 * @author conni
 * @version $Id$
 */
public abstract class MonthSequenceFormat
{
	protected MonthSequenceFormat()
	{
	}

	public abstract MonthSequence parse( String s ) throws ParseException;
	
	public String format( MonthSequence seq )
	{
		StringBuffer buf = new StringBuffer();
		format( seq, buf );
		
		return buf.toString();
	}

	public abstract void format( MonthSequence seq, StringBuffer buf );
}	
