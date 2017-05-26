/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.apache.log4j;

import java.io.IOException;
//import java.io.File;
//import java.io.InterruptedIOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Calendar;
//import java.util.TimeZone;
//import java.util.Locale;

import org.apache.log4j.helpers.LogLog;
//import org.apache.log4j.spi.LoggingEvent;

/**
   DailyRollingFileAppender extends {@link FileAppender} so that the
   underlying file is rolled over at a user chosen frequency.
   
   DailyRollingFileAppender has been observed to exhibit 
   synchronization issues and data loss.  The log4j extras
   companion includes alternatives which should be considered
   for new deployments and which are discussed in the documentation
   for org.apache.log4j.rolling.RollingFileAppender.

   <p>The rolling schedule is specified by the <b>DatePattern</b>
   option. This pattern should follow the {@link SimpleDateFormat}
   conventions. In particular, you <em>must</em> escape literal text
   within a pair of single quotes. A formatted version of the date
   pattern is used as the suffix for the rolled file name.

   <p>For example, if the <b>File</b> option is set to
   <code>/foo/bar.log</code> and the <b>DatePattern</b> set to
   <code>'.'yyyy-MM-dd</code>, on 2001-02-16 at midnight, the logging
   file <code>/foo/bar.log</code> will be copied to
   <code>/foo/bar.log.2001-02-16</code> and logging for 2001-02-17
   will continue in <code>/foo/bar.log</code> until it rolls over
   the next day.

   <p>Is is possible to specify monthly, weekly, half-daily, daily,
   hourly, or minutely rollover schedules.

   <p><table border="1" cellpadding="2">
   <tr>
   <th>DatePattern</th>
   <th>Rollover schedule</th>
   <th>Example</th>

   <tr>
   <td><code>'.'yyyy-MM</code>
   <td>Rollover at the beginning of each month</td>

   <td>At midnight of May 31st, 2002 <code>/foo/bar.log</code> will be
   copied to <code>/foo/bar.log.2002-05</code>. Logging for the month
   of June will be output to <code>/foo/bar.log</code> until it is
   also rolled over the next month.

   <tr>
   <td><code>'.'yyyy-ww</code>

   <td>Rollover at the first day of each week. The first day of the
   week depends on the locale.</td>

   <td>Assuming the first day of the week is Sunday, on Saturday
   midnight, June 9th 2002, the file <i>/foo/bar.log</i> will be
   copied to <i>/foo/bar.log.2002-23</i>.  Logging for the 24th week
   of 2002 will be output to <code>/foo/bar.log</code> until it is
   rolled over the next week.

   <tr>
   <td><code>'.'yyyy-MM-dd</code>

   <td>Rollover at midnight each day.</td>

   <td>At midnight, on March 8th, 2002, <code>/foo/bar.log</code> will
   be copied to <code>/foo/bar.log.2002-03-08</code>. Logging for the
   9th day of March will be output to <code>/foo/bar.log</code> until
   it is rolled over the next day.

   <tr>
   <td><code>'.'yyyy-MM-dd-a</code>

   <td>Rollover at midnight and midday of each day.</td>

   <td>At noon, on March 9th, 2002, <code>/foo/bar.log</code> will be
   copied to <code>/foo/bar.log.2002-03-09-AM</code>. Logging for the
   afternoon of the 9th will be output to <code>/foo/bar.log</code>
   until it is rolled over at midnight.

   <tr>
   <td><code>'.'yyyy-MM-dd-HH</code>

   <td>Rollover at the top of every hour.</td>

   <td>At approximately 11:00.000 o'clock on March 9th, 2002,
   <code>/foo/bar.log</code> will be copied to
   <code>/foo/bar.log.2002-03-09-10</code>. Logging for the 11th hour
   of the 9th of March will be output to <code>/foo/bar.log</code>
   until it is rolled over at the beginning of the next hour.


   <tr>
   <td><code>'.'yyyy-MM-dd-HH-mm</code>

   <td>Rollover at the beginning of every minute.</td>

   <td>At approximately 11:23,000, on March 9th, 2001,
   <code>/foo/bar.log</code> will be copied to
   <code>/foo/bar.log.2001-03-09-10-22</code>. Logging for the minute
   of 11:23 (9th of March) will be output to
   <code>/foo/bar.log</code> until it is rolled over the next minute.

   </table>

   <p>Do not use the colon ":" character in anywhere in the
   <b>DatePattern</b> option. The text before the colon is interpeted
   as the protocol specificaion of a URL which is probably not what
   you want.


   @author Eirik Lygre
   @author Ceki G&uuml;lc&uuml;*/
public class DailyFileAppender extends FileAppender {


  // The code assumes that the following constants are in a increasing
  // sequence.
  static final int TOP_OF_TROUBLE=-1;
  static final int TOP_OF_MINUTE = 0;
  static final int TOP_OF_HOUR   = 1;
  static final int HALF_DAY      = 2;
  static final int TOP_OF_DAY    = 3;
  static final int TOP_OF_WEEK   = 4;
  static final int TOP_OF_MONTH  = 5;


  /**
     The date pattern. By default, the pattern is set to
     "'.'yyyy-MM-dd" meaning daily rollover.
   */
  private String datePattern = "'.'yyyy-MM-dd";
  
  SimpleDateFormat sdf;


  /**
     The default constructor does nothing. */
  public DailyFileAppender() {
  }

  /**
    Instantiate a <code>DailyRollingFileAppender</code> and open the
    file designated by <code>filename</code>. The opened filename will
    become the ouput destination for this appender.

    */
  public DailyFileAppender (Layout layout, String filename,
				   String datePattern) throws IOException {
	super(layout, filename, true);
    this.datePattern = datePattern;
    activateOptions();
  }

  /**
     The <b>DatePattern</b> takes a string in the same format as
     expected by {@link SimpleDateFormat}. This options determines the
     rollover schedule.
   */
  public void setDatePattern(String pattern) {
    datePattern = pattern;
  }

  /** Returns the value of the <b>DatePattern</b> option. */
  public String getDatePattern() {
    return datePattern;
  }
  
//  public static String getNewName(String name, String Pattern) {
////	  LogLog.error("name: " + name + System.lineSeparator() + "Pattern: " + Pattern);
//		if (Pattern != null && name != null) {
//			SimpleDateFormat tsdf = new SimpleDateFormat(Pattern);
//			return name + tsdf.format(new Date());
//		} else {
//			return name;
//		}
//  }
  
  public void activateOptions() {
    
    if(datePattern != null && fileName != null) {
      sdf = new SimpleDateFormat(datePattern);
//      fileName = fileName+sdf.format(new Date());
      super.setFile(fileName+sdf.format(new Date()));
    } else {
      LogLog.error("Either File or DatePattern options are not set for appender ["
		   +name+"].");
    }
    super.activateOptions();
  }

}

