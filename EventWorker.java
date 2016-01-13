import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class EventWorker {
	private static Calendar cal = Calendar.getInstance(); // GregorianCalendar
	private static int timeZone = cal.getTimeZone().getRawOffset() / 36000;

	public static String getName(String input) {
		String eventName = "";
		char[] str2ch = input.toCharArray();
		for (char str : str2ch) {
			if (str != ';' && str != ':' && (str < '0' || str > '9')) {
				eventName += str;
			}
		}
		return eventName;
	}

	public static int getTime(String input) {
		int time = 0;
		int tens = 1000;
		char[] str2ch = input.toCharArray();
		for (char str : str2ch) {
			if (str >= '0' && str <= '9' && tens != 0) {
				time = time + tens * Character.getNumericValue(str);
				tens /= 10;
			}
		}
		time -= timeZone;
		return time;
	}

	public static void getICS(String eventName, int time, String dir) throws IOException {
		File directory = new File(dir);
		int numFiles = directory.list().length + 1;
		int mounth = cal.get(Calendar.MONTH) + 1;
		int now = Calendar.HOUR_OF_DAY * 100 + Calendar.MINUTE;
		int day_of_mounth = cal.get(Calendar.DATE);
		StringBuilder date = new StringBuilder();
		date.append(cal.get(Calendar.YEAR));
		if (mounth < 10) {
			date.append("0" + mounth);
		} else
			date.append(mounth);
		if (day_of_mounth < 10) {
			date.append("0" + day_of_mounth);
		} else
			date.append(day_of_mounth);
		date.append("T");
		PrintWriter out = new PrintWriter(dir + numFiles + ".ics");
		out.println("BEGIN:VCALENDAR");
		out.println("BEGIN:VEVENT");
		out.println("VERSION:2.0");
		out.println("PRODID:-//github.com/alexmozzhakov/EventWorker//EventWorker v1.0//EN");
		out.println("DTSTAMP:" + date + (now) + "00Z");
		out.println("DTSTART:" + date + time + "00Z");
		if (event.getName().contains("eventtype1"))
			out.println("DTEND:" + date + (time + 25) + "00Z");
		else if (event.getName().contains("eventtype2"))
			if (time != 1930) //difference in time of event example
				out.println("DTEND:" + date + (time + 30) + "00Z"); // will take 30 min usual
			else
				out.println("DTEND:" + date + (time + 5) + "00Z"); // or 5 in if happend on 19:30
		else if (event.getName().contains("eventtype3"))
			out.println("DTEND:" + date + (time + 45) + "00Z");
		else
			out.println("DTEND:" + date + (time + 30) + "00Z"); // default event time

		out.println("SUMMARY:" + eventName);
		out.println("END:VEVENT");
		out.println("END:VCALENDAR");
		out.close();
		File myFile = new File(dir + numFiles + ".ics"); 
		//  Uncomment if you want to open file instantly
		//	Desktop.getDesktop().open(myFile);
	}
}
