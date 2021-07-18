import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.sound.sampled.Clip;

public class SocketClient extends Thread {
	String ipAddress = "localhost";
	int port = 10000;
	Socket socket = null;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	PrintWriter out;
	Receiver receiver;
	Data data;

	//FIXME
	MultiGameFrame multiGameFrame;
	SceneController sceneController;
	MultiModeFrame multiModeFrame;
	InfiniteProgressPanel glassPane;
	Clip battle;
	Clip main;

	public void connectServer() {
		try {
			//socket = new Socket(ipAddress, port);
			socket = new Socket("127.0.0.1", port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			out = new PrintWriter(socket.getOutputStream(), true);

			Receiver receiver = new Receiver(socket);
			receiver.start();
			System.out.println("サーバと接続しました");
		}
		catch (UnknownHostException e) {
			System.err.println("ホストのIPアドレスが判定できません: " + e);
			System.exit(-1);
		}
		catch (IOException e) {
			System.err.println("サーバ接続時にエラーが発生しました: " + e);
			System.exit(-1);
		}
	}

	public void sendData(Data data) {
		try {
			if(data.getType() == Data.USER) {
				System.out.println("サーバにUserデータを送ります");
			}
			else {
				System.out.println("サーバにAnswerデータを送ります");
			}
			oos.writeObject(data);
			oos.flush();
			//送信したらリセット
			oos.reset();
		}
		//catch(EOFException eof) {
		//	System.out.println("ここ");
		//}
		catch(IOException ioe) {
			System.err.println(ioe);
		}
	}

	class Receiver extends Thread {
		private ObjectInputStream ois;
	//	Data data;

		Receiver (Socket socket) {
			try{
				ois = new ObjectInputStream(socket.getInputStream());
			}
			catch (IOException e) {
				System.err.println("データ受信時にエラーが発生しました: " + e);
			}
		}

		public void run() {
			try {
				while(true) {
					data = (Data)(ois.readObject());

					synchronized(SocketClient.this) {
						SocketClient.this.notify();
					}

					if(data.getType() == Data.KANJI) {
						if(data.getStatus() == KanjiData.MULTI_UPDATE) {
							KanjiData kanjiData = (KanjiData)data;
							multiGameFrame.updateBoard(kanjiData.getBoard());
							multiGameFrame.updateScore(kanjiData.getScore());
							multiGameFrame.updateBoardPosition(kanjiData.getBoardPosition());
						}
						else if(data.getStatus() == KanjiData.MULTI_START) {
							glassPane.stop();
							KanjiData kanjiData = (KanjiData)data;
		                	multiGameFrame.setBoard(kanjiData.getBoard());

							synchronized(SocketClient.this) {
								SocketClient.this.notify();
							}


						//	multiGameFrame.updateBoard(kanjiData.getBoard());
						//	multiGameFrame.updateScore(kanjiData.getScore());
						//	multiGameFrame.updateBoardPosition(kanjiData.getBoardPosition());


							//sceneController.moveGame(multiModeFrame, multiGameFrame);
							//multiGameFrame.startTimer();

							//音楽スタート
							// battle = GameFrame.createClip(new File("sounds/battle.wav"));
							// main.stop();
							// main.flush();
							// main.setFramePosition(0);
							// battle.loop(Clip.LOOP_CONTINUOUSLY);
						}
					}
				}
			}
			catch(EOFException eof) {
				System.out.println("サーバーが停止しました");
				System.exit(0);
			}
			catch(ClassNotFoundException e) {
				System.err.println(e);
			}
			catch(IOException ioe) {
				System.err.println(ioe);
			}
		}
	}
}
