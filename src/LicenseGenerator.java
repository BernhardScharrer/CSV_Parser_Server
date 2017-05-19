

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class LicenseGenerator {

	private static Random rand = new Random();

	public static String generateLicense(String table, String column) {

		String license = "";
		int r;
		for (int n = 0; n < 4; n++) {
			for (int i = 0; i < 4; i++) {
				r = rand.nextInt(36);

				if (r >= 10) {
					license = license + ((char) ('A' + (r - 10)));
				} else {
					license = license + ((char) ('0' + (r)));
				}
			}
			license = license + "-";
		}
		license = license.substring(0, license.length() - 1);

		ResultSet result = Database.query("SELECT * FROM `"+table+"`");
		boolean unique = true;
		try {
			while (result.next()) {
				if (result.getString(column).equalsIgnoreCase(license))
					unique = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return unique ? license : generateLicense(table, column);

	}

}
