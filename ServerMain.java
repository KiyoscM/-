import java.sql.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
//import com.sun.jdi.event.AccessWatchpointEvent;
//import com.sun.jmx.snmp.UserAcl;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class ServerMain {
    public static void main(String[] args) {

		System.out.println("サーバーを起動します");

//        socketServer.start();
        while(true) {
			SocketServer socketServer = new SocketServer();

            socketServer.connectClient();

			DBAccesser accesser = new DBAccesser();

			ServerController serverController = new ServerController(socketServer, accesser);

            serverController.start();
        }
    }
}


//         User user = new User(0, "");
//         MultiModel multiModel = new MultiModel(0);

// //        KanjiModel kanjiModel;

// 		while(true) {
//             try {
//                 // Dataオブジェクトを受け取るまでwait
// 			    synchronized (socketServer) {
// 				    socketServer.wait();
//                 }
//             }
//             catch(InterruptedException e) {
//                 System.out.println(e);
//             }

//             Data data = socketServer.data;
//             int dataType = data.getType();

// 			if(dataType == Data.USER) {

// 				UserData userData = (UserData) data;
//                 int status = userData.getStatus();
//                 String name = userData.getName();
//                 String password = userData.getPassword();

//                 if(status == UserData.CREATE) {
//                     accesser.createRecord(accesser.createUser(name, password));
//                     int id = Integer.parseInt(accesser.getColumn("id"));
//                     user = new User(id, name);
//                 }
//                 else if(status == UserData.LOGIN) {
//                     if(accesser.existRecord(accesser.selectUser(name, password))) {
//                         int id = Integer.parseInt(accesser.getColumn("id"));
//                         user = new User(id, name);
//                         data.setStatus(UserData.EXIST);
//                     }
//                     else {
//                         data.setStatus(UserData.EMPTY);
//                     }
//                     socketServer.sendData(data);
//                 }
//             }
//             else if(dataType == Data.SINGLE_MODE_ANSWER) {

//                 // AnswerData answerData = (AnswerData) data;
//                 // int status = answerData.getStatus();

//                 // if(status == AnswerData.INIT) {
//                 //     kanjiModel = new KanjiModel();
//                 // }
//                 // else if(status == AnswerData.NOT_INIT) {
//                 //     int coordinate = answerData.getCoordinate();
//                 //     String furigana = answerData.getFurigana();

//                 //     if(kanjiModel.check(coordinate, furigana)) {
//                 //         int x;
//                 //         int y;
//                 //         String kanji;

//                 //         KanjiData kanjiData = new KanjiData(Data.KANJI, x, y, kanji, furigana);
//                 //     }
//                 // }
//                 // socketServer.sendData(data);
//             }
//             else if(dataType == Data.MULTI_MODE_ANSWER) {
//                 AnswerData answerData = (AnswerData) data;
//                 int status = answerData.getStatus();
//                 KanjiData kanjiData;
//                 int board_size = 10;

//                 if(status == AnswerData.INIT) {
//                     multiModel = new MultiModel(board_size);
//                     multiModel.boardInitialize();
//                     multiModel.boardPrint();
//                     kanjiData = new KanjiData(Data.KANJI, multiModel.getBoard());
//                     socketServer.sendData((Data)kanjiData);
//                 }
//                 else if(status == AnswerData.NOT_INIT) {
//                     String encodeAnswer = answerData.getCoordinate() + "," + answerData.getFurigana() + "," + "0";
//                     multiModel.wordsChoiceCheck(encodeAnswer);
//                     multiModel.boardPrint();
//                     kanjiData = new KanjiData(Data.KANJI, multiModel.getBoard());
//                     socketServer.sendData((Data)kanjiData);
//                 }

//             }
//         }
//     }
// }
/*
					//if (userData.getStatus() == UserData.CREATE) {
                    // if (model.userData.getStatus() == UserData.CREATE) {
					// 	System.out.println("新規ユーザーを作成します");
                    //     //st.executeUpdate(userModel.createUser());
                    //     st.executeUpdate(model.userModel.createUser());
                    // }
                    // else if (model.userData.getStatus() == UserData.LOGIN) {
					// 	System.out.println("ユーザーをデータベースから取得します");
					// 	ResultSet result = st.executeQuery(model.userModel.selectUser());

					// 	Data data;

					// 	if (result.next()) {
					// 		data = new Data(Data.USER, UserData.EXIST);
					// 	}
					// 	else {
					// 		data = new Data(Data.USER, UserData.EMPTY);
					// 	}
					// 	socketServer.sendData(data);
					// }
				}
				else if(dataType == Data.ANSWER) {

					AnswerData answerData = (AnswerData)socketServer.data;
					KanjiModel kanjiModel = new KanjiModel(socketServer, answerData);

					MultiModel multiModel;

					if(answerData.getStatus() == AnswerData.INIT) {
						System.out.println("初期データをクライアントに送信します");
						int record = 50;
						int level = 3;

						// ResultSet result = st.executeQuery(kanjiModel.initSetBoardQuery(level, record));
						// //FIXME
						// kanjiModel.kanjiBoard = new String[record*2/10][record*2/10];
						// kanjiModel.furiganaBoard = new String[record];

						// int x=-1;
						// int y=0;
						// KanjiData kanjiData;
						// int i=0;

						// while(result.next()) {
						// 	/*getString()メソッドは、引数に指定されたフィールド名(列)の値をStringとして取得する*/
						// 	String kanji = result.getString("kanji");
						// 	//FIXME
						// 	if(x==record*2/10-1) {
						// 		x=-1;
						// 		y++;
						// 	}
						// 	kanjiModel.kanjiBoard[++x][y] = String.valueOf(kanji.charAt(0));
						// 	kanjiData = new KanjiData(Data.KANJI, x, y, kanjiModel.kanjiBoard[x][y], "");
						// 	//sleepをかけないと早すぎてクライアントが受け取れない
						// 	socketServer.sleep(100);
						// 	socketServer.sendData((Data)kanjiData);
						// 	System.out.println(++i + "一文字目：" + kanjiModel.kanjiBoard[x][y]);

						// 	kanjiModel.kanjiBoard[++x][y] = String.valueOf(kanji.charAt(1));
						// 	kanjiData = new KanjiData(Data.KANJI, x, y, kanjiModel.kanjiBoard[x][y], "");
						// 	socketServer.sleep(100);
						// 	socketServer.sendData((Data)kanjiData);
						// 	System.out.println(++i + "二文字目：" + kanjiModel.kanjiBoard[x][y]);
						// }
//					}
// 					else if(answerData.getStatus() == AnswerData.NOT_INIT) {
// 						int board_size;
// 						board_size = 10;
// 						multiModel = new MultiModel(board_size, kanjiModel);
// 						if(first_call) {

// 							//盤面初期化
// 							multiModel.boardinitialize();
// 							first_call = false;
// 							for(int i=0;i<board_size;i++) {
// 								for(int j=0;j<board_size;j++) {
// 									System.out.print(multiModel.boardoutput()[i][j]+" ");
// 								}
// 								System.out.println();
// 							}
// 							continue;
// 						}

// 						String str = answerData.getCoordinate() + "," + answerData.getFurigana() + "," + "1";
// 						System.out.println(str);
// 						//盤面更新
// 						multiModel.wordschoicecheck(str);

// 						//盤面表示
// 						for(int i=0;i<board_size;i++) {
// 				        	for(int j=0;j<board_size;j++) {
// 	        					System.out.print(multiModel.boardoutput()[i][j]+" ");
// 	        				}
// 	        				System.out.println();
// 	        			}
// 					}
// 				}
// 			}
// 		}
// 		// catch(NoSuchMethodException nsme) {
// 		// 	System.out.println(nsme);
// 		// }
// 		// catch(InstantiationException ie) {
// 		// 	System.out.println(ie);
// 		// }
// 		// catch(InterruptedException ie) {
// 		// 	System.out.println(ie);
// 		// }
// 		// catch(IllegalAccessException iae) {
// 		// 	System.out.println(iae);
// 		// }
// 		// catch(InvocationTargetException ite) {
// 		// 	System.out.println(ite);
// 		// }
// 		// catch (SQLException e) {
// 		// 	System.out.println("Connection Failed. : " + e.toString());
// 		// }
// 		// catch (ClassNotFoundException e) {
// 		// 	System.out.println("ドライバを読み込めませんでした " + e);
// 		// }
// 		catch (Exception e) {
// 			System.err.println(e);
// 		}
// 		// finally {
// 		// 	try {
// 		// 		if (con != null) {
// 		// 			con.close();
// 		// 		}
// 		// 	}
// 		// 	catch (Exception e) {
// 		// 		System.out.println("Exception2! :" + e.toString());
// 		// 	}
// 		// }
// 	}
// }
