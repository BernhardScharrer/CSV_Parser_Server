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
					System.out.println(user.getVorname());
					System.out.println(user.getNachname());
					System.out.println(user.getTelephon());
					System.out.println(user.getFunctions().print());
				}
				
			}
			
		}
		
	}
	
}
