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
				String params = (String) packet.getEntry("PARAMS");
				
				for (User user : table.getUsers()) {
					
					String vorname = user.getVorname();
					String nachname = user.getNachname();
					String telephon = user.getTelephon();
					String funktion = user.getFunctions().print();
					
					ResultSet result = Database.query("SELECT * FROM `USERS` WHERE `NACHNAME` = '"+nachname+"' AND `VORNAME` = '"+vorname+"'");
					
					try {
						if (result.next()) {
							
							int id = result.getInt("ID");
							
							Database.execute("UPDATE `USERS` SET `TELEPHON`='"+telephon+"' WHERE ID = '"+id+"'");
							Database.execute("UPDATE `DATA` SET FUNKTION`='"+funktion+"' WHERE `ID`='"+id+"'");
							
						} else {
							
							
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
				}
				
			}
			
		}
		
	}
	
}
