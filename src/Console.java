import java.util.Scanner;

public class Console {

	private static Scanner s;
	private static String line;

	public static void send(String msg) {
		System.out.println(msg);
	}

	public static void info(String msg) {
		System.out.println(CMDTool.INFO + msg);
	}

	public static void warn(String msg) {
		System.out.println(CMDTool.WARN + msg);
	}

	public static void error(String msg) {
		System.out.println(CMDTool.ERROR + msg);
	}

	/*
	 * listen to console commands
	 */
	public static void listen() {

		// create scanner
		s = new Scanner(System.in);

		// listen to user input
		while ((line = s.next()) != null) {
			
			System.out.println(line);
			
		}

		s.close();

	}

}