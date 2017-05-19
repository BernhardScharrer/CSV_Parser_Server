import java.sql.ResultSet;
import java.sql.SQLException;

import org.gnet.packet.Packet;
import org.gnet.server.ClientModel;
import org.gnet.server.GNetServer;
import org.gnet.server.ServerEventListener;

import data.Table;
import data.User;
import packets.TableData;

public class CSVParser extends GNetServer {
	
	public static void main(String[] args) {
		new CSVParser();
	}
	
	public CSVParser() {
		super("84.200.106.98", 2884);
		super.bind();
		super.start();
		super.addEventListener(new Listener());
		
		Database.setup();
		
	}
	
	private class Listener extends ServerEventListener {

		@Override
		protected void clientConnected(ClientModel client) {
			System.out.println("Client connected to server!");
		}

		@Override
		protected void clientDisconnected(ClientModel client) {
			System.out.println("Client disconnected!");
		}

		@Override
		protected void debugMessage(String msg) {
			System.out.println(msg);
		}

		@Override
		protected void errorMessage(String msg) {
			System.out.println(msg);
		}

		@Override
		protected void packetReceived(ClientModel client, Packet packet) {
			
			System.out.println("Packet comes in!");
			
			if (packet instanceof TableData) {
				
				Table table = (Table) packet.getEntry("TABLE");
				
				for (User user : table.getUsers()) {
					
					String vorname = user.getVorname();
					String nachname = user.getNachname();
					String telephon = user.getTelephon();
					String funktion = user.getFunctions().print();
					
					ResultSet result = Database.query("SELECT * FROM `USERS` WHERE `NACHNAME` = '"+nachname+"' AND `VORNAME` = '"+vorname+"'");
					
					int new_users=0, updated_users=0;
					
					try {
						if (result.next()) {
							
							int id = result.getInt("ID");
							
							Database.execute("UPDATE `USERS` SET `TELEPHON`='"+telephon+"' WHERE ID = '"+id+"'");
							Database.execute("UPDATE `DATA` SET FUNKTION`='"+funktion+"' WHERE `ID`='"+id+"'");
							
							updated_users++;
							
						} else {
							
							Database.execute("INSERT INTO `USERS`(`LIZENZ`, `VORNAME`, `NACHNAME`, `TELEPHON`) VALUES ('"+LicenseGenerator.generateLicense("USERS", "LIZENZ")+"','"+vorname+"','"+nachname+"','"+telephon+"')");
							
							ResultSet generated_row = Database.query("SELECT * FROM `USERS` WHERE `NACHNAME` = '"+nachname+"' AND `VORNAME` = '"+vorname+"'");
							
							int id = generated_row.getInt("ID");
							
							Database.execute("INSERT INTO `DATA`(`ID`, `FUNKTION`) VALUES ('"+id+"','"+funktion+"')");
							
							new_users++;
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					Console.info("Updated database succesfully! (NEW:" + new_users + "|UPDATED:" + updated_users + ")");
					
					
				}
				
			}
			
		}
		
	}
	
}
