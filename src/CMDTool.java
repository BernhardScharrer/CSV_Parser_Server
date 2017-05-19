public class CMDTool {
	
	public static final String BOLD = "\u001B[1m";
	public static final String RESET = "\u001B[0m";
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";
	public static final String WHITE_BG = "\u001B[47m";
	public static final String SEPERATOR = "|";
	
	public static final String INFO = RESET+GREEN+"[INFO] " + RESET;
	public static final String ERROR = RESET+RED+"[ERROR] " + RESET;
	public static final String WARN = RESET+YELLOW+"[WARN] " + RESET;
	public static final String GNET = RESET+BLUE+"[Server] " + RESET;
	public static final String SQL = RESET+CYAN+"[SQL] " + RESET;
	
}
