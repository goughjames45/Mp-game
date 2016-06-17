import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	int portNumber = 22222;
	ArrayList<ClientInstance> clientList;
	
	public Server(){
		
		clientList = new ArrayList<ClientInstance>();
		
		try {
			ServerSocket ss = new ServerSocket(portNumber);
			
			while(!ss.isClosed()){
				
				Socket sock = ss.accept();
				ClientInstance ci = new ClientInstance(sock, clientList.size());
				clientList.add(ci);
				Thread t = new Thread(ci);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}

