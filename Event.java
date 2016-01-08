import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class Event {
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
		int numFiles = directory.list().length;
		int now = Calendar.HOUR * 10 + Calendar.MINUTE;
		String date = cal.get(Calendar.YEAR) + "" + cal.get(Calendar.MONTH) + "" + cal.get(Calendar.DAY_OF_MONTH) + "T";
		if (!eventName.contains("טורנירים")) {
			PrintWriter out = new PrintWriter(dir + numFiles + ".ics");
			out.println("BEGIN:VCALENDAR");
			out.println("BEGIN:VEVENT");
			out.println("VERSION:2.0");
			out.println("PRODID:-//hacksw/handcal//NONSGML v1.0//EN");
			out.println("DTSTAMP:" + date + "0" + (now) + "00Z");
			out.println("DTSTART:" + date + time + "00Z");
			if (eventName.contains("ארוחת"))
				out.println("DTEND:" + date + (time + 25) + "00Z");
			else if (eventName.contains("מועדון"))
				if (time != 1930)
					out.println("DTEND:" + date + (time + 30) + "00Z");
				else
					out.println("DTEND:" + date + (time + 5) + "00Z");
			else if (eventName.contains("פעולה"))
				out.println("DTEND:" + date + (time + 45) + "00Z");
			else
				out.println("DTEND:" + date + (time + 30) + "00Z");

			out.println("SUMMARY:" + eventName);
			out.println("END:VEVENT");
			out.println("END:VCALENDAR");
			out.close();
		//  Uncomment if you want to open file instantly
		//	File myFile = new File(dir + numFiles + ".ics"); 
		//	Desktop.getDesktop().open(myFile);
		}
	}
}
