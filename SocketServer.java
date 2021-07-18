import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class SocketServer extends Thread {
	//FIXME 一旦ポート番号を固定
    int port = 10000;
	//TODO dataに対してgetterが必要か
//	Data data;
	ArrayList<ObjectOutputStream> oosList = new ArrayList<ObjectOutputStream>();
	ArrayList<ObjectInputStream> oisList = new ArrayList<ObjectInputStream>();
	ObjectOutputStream oos;
	ObjectInputStream ois;

	public void connectClient() {
//	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			Socket sock = null;

//			while(true) {
			sock = server.accept();
			System.out.println("クライアントと接続しました");
			ois = new ObjectInputStream(sock.getInputStream());
			oos = new ObjectOutputStream(sock.getOutputStream());
			oisList.add(ois);
			oosList.add(oos);
			server.close();
//			}
				//if((data = (Data)(ois.readObject())) != null) {


				// while((data = (Data)(ois.readObject())) != null) {
				// 	synchronized(this) {
				// 		this.notify();
				// 	}
				// }
//			}
		}
		catch(EOFException eofe) {
			System.out.println(eofe);
		}
		catch(IOException e) {
			System.err.println(e);
		}
		// catch(ClassNotFoundException e) {
		// 	System.err.println(e);
		// }
    }

	public void sendData(Data data) {
		try {
			if(data.getType() == Data.USER) {
				System.out.println("クライアントにUserデータを送ります");
			}
			else if(data.getType() == Data.KANJI) {
				System.out.println("クライアントにKanjiデータを送ります");
			}
			oos.writeObject(data);
			oos.flush();
			//送信したらリセット
			oos.reset();
		}
		catch(EOFException eof) {
			System.out.println("ここ");
		}
		catch(IOException ioe) {
			System.err.println(ioe);
		}
	}
}
