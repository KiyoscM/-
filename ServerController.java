import java.io.*;
import java.util.ArrayList;

public class ServerController extends Thread {
    SocketServer socketServer;
    DBAccesser accesser;
    User user;
	KanjiModel kanjiModel;
	SingleModel singleModel;
    MultiModel multiModel;
	ManageMulti manageMulti;
//	ManageVs manageVs;
    Data data;

	//static ArrayList<MultiModel> multiList = new ArrayList<MultiModel>();
	static ArrayList<ManageMulti> multiList = new ArrayList<ManageMulti>();

    public ServerController(SocketServer socketServer, DBAccesser accesser) {
        this.socketServer = socketServer;
        this.accesser = accesser;
    }

    public void run() {
        try {
            while((data = (Data)(socketServer.ois.readObject())) != null) {
                int dataType = data.getType();

			    if(dataType == Data.USER) {

    				UserData userData = (UserData)data;
                    int status = userData.getStatus();
                    String name = userData.getName();
                    String password = userData.getPassword();

                    if(status == UserData.CREATE) {
                        //TODO もう一度クエリを投げている
                        //作成できたなら(不必要)
                        if(accesser.existRecord(accesser.selectUser(name))) {
                            data.setStatus(UserData.ALREADY_EXIST);
                        }
                        else {
                            accesser.createRecord(accesser.createUser(name, password));
                            //createの後にselectをしないとresultに入らない
                            accesser.selectRecord (accesser.selectUser(name));
                            int id = Integer.parseInt(accesser.getColumn("id"));
                            user = new User(id, name);
							user.setSocketServer(socketServer);

                            data.setStatus(UserData.CREATE_SUCCESS);
                        }
                        socketServer.sendData(data);
                    }
                    else if(status == UserData.LOGIN) {
                        if(accesser.existRecord(accesser.selectUser(name, password))) {
                            int id = Integer.parseInt(accesser.getColumn("id"));
                            user = new User(id, name);
							user.setSocketServer(socketServer);
                            data.setStatus(UserData.EXIST);
                        }
                        else {
                            data.setStatus(UserData.EMPTY);
                        }
                        socketServer.sendData(data);

                    }
                }
                else if(dataType == Data.SINGLE_MODE_ANSWER) {

					AnswerData answerData = (AnswerData) data;
					int status = answerData.getStatus();
                    KanjiData kanjiData;

					if(status == AnswerData.INIT) {
						kanjiModel = new KanjiModel(accesser);
						//singleModel = new SingleModel();
						singleModel = new SingleModel(kanjiModel);
						kanjiData = new KanjiData(Data.KANJI, singleModel.getBoard());
						kanjiData.setRest(singleModel.getRest());
						socketServer.sendData((Data)kanjiData);
					}
					else if(status == AnswerData.NOT_INIT) {
                        String coordinate = answerData.getCoordinate();
                        String furigana = answerData.getFurigana();

                        String katakana;

						//ひらがなをカタカナに変換
                        StringBuffer buf = new StringBuffer();
                        for (int i = 0; i < answerData.getFurigana().length(); i++) {
                            char code = answerData.getFurigana().charAt(i);
                            if ((code >= 0x3041) && (code <= 0x3093)) {
                                buf.append((char) (code + 0x60));
                            } else {
                                buf.append(code);
                            }
                        }

                        katakana = buf.toString();

						int tmpStatus = singleModel.check(coordinate + "," + katakana);

						System.out.println(coordinate + "," + katakana);
						kanjiData = new KanjiData(Data.KANJI, singleModel.getBoard());
						kanjiData.setRest(singleModel.getRest());

						if(tmpStatus == KanjiData.GOOD) {
							kanjiData.setStatus(KanjiData.GOOD);
						}
						else {
							kanjiData.setStatus(KanjiData.BAD);
						}
						kanjiData.setScore(singleModel.getScore());
						socketServer.sendData((Data)kanjiData);
					}
					else if(status == AnswerData.REQUIRE_RESULT) {
						kanjiData = new KanjiData(Data.KANJI, singleModel.getBoard());
						//singleの点数を送信
						socketServer.sendData((Data)kanjiData);
					}
                }

				// else if(dataType == Data.VS_MODE_ANSWER) {
				// 	AnswerData answerData = (AnswerData) data;
                //     int status = answerData.getStatus();
                //     KanjiData kanjiData;
                //     int playerNumber;

				// 	if(status == AnswerData.INIT) {
                //         System.out.println("ユーザID:" + this.user.getId());

				// 		if(vsList.size() == 0 || vsList.get(vsList.size() - 1).getStatus() == ManageVs.NOW_ON_PLAY) {

				// 			kanjiModel = new KanjiModel(accesser);
				// 			//singleModel = new SingleModel();
				// 			singleModel = new SingleModel(kanjiModel, user);
				// 			kanjiData = new KanjiData(Data.KANJI, singleModel.getBoard());
				// 			socketServer.sendData((Data)kanjiData);

				// 			manageSingle = new ManageMulti(multiModel, ManageMulti.WAIT);
				// 			user.setPlayerNumber(multiModel.getUserListSize());

				// 			kanjiData = new KanjiData(Data.KANJI, multiModel.getBoard());
				// 			kanjiData.setStatus(KanjiData.MULTI_WAIT);

                //         	socketServer.sendData((Data)kanjiData);

				// 			multiList.add(manageMulti);

				// 		}
				// 	}
				//}

                else if(dataType == Data.MULTI_MODE_ANSWER) {
                    AnswerData answerData = (AnswerData) data;
                    int status = answerData.getStatus();
                    KanjiData kanjiData;
                    int board_size = 10;
                    int playerNumber;

                    if(status == AnswerData.INIT) {
                        System.out.println("ユーザID:" + this.user.getId());
						//user.setStatus(WAIT);
						//multiListが0または、最後の要素がPLAY中なら新しくmultiModelを作成
						if(multiList.size() == 0 || multiList.get(multiList.size() - 1).getStatus() == ManageMulti.NOW_ON_PLAY) {

							kanjiModel = new KanjiModel(accesser);
							System.out.println("user名:" + user.getName());
							System.out.println("新しくmultiModelを作成します");
							multiModel = new MultiModel(board_size, kanjiModel, user);
							multiModel.boardInitialize();
							multiModel.boardPrint();

							//他のユーザーがくるまでwait
							manageMulti = new ManageMulti(multiModel, ManageMulti.WAIT);
							user.setPlayerNumber(multiModel.getUserListSize());

							kanjiData = new KanjiData(Data.KANJI, multiModel.getBoard());
							kanjiData.setStatus(KanjiData.MULTI_WAIT);

                        	socketServer.sendData((Data)kanjiData);

							multiList.add(manageMulti);
						}
						//そうでなければmultiListの最後の要素に追加
						else {
							multiModel = multiList.get(multiList.size() - 1).getMultiModel();
							System.out.println("user名:" + user.getName());
							System.out.println("既存のmultiModelに参加します");
							//マルチモデルのユーザーを追加
							multiModel.setUser(user);

							user.setPlayerNumber(multiModel.getUserListSize());

							//Userを追加した後に他の人にstatusを送信
							multiModel.notifyAddUser(user.getPlayerNumber());

							kanjiData = new KanjiData(Data.KANJI, multiModel.getBoard());
							kanjiData.setStatus(KanjiData.MULTI_WAIT);


							//4人揃ったらNOW_ON_PLAYに変更
							if(multiModel.getUserListSize() == 4) {
								multiList.get(multiList.size() - 1).setStatus(ManageMulti.NOW_ON_PLAY);

								kanjiData.setStatus(KanjiData.MULTI_STARTER);
							}

                        	socketServer.sendData((Data)kanjiData);
						}

                    }
                    else if(status == AnswerData.NOT_INIT) {
                        String katakana;

						//ひらがなをカタカナに変換
                        StringBuffer buf = new StringBuffer();
                        for (int i = 0; i < answerData.getFurigana().length(); i++) {
                            char code = answerData.getFurigana().charAt(i);
                            if ((code >= 0x3041) && (code <= 0x3093)) {
                                buf.append((char) (code + 0x60));
                            } else {
                                buf.append(code);
                            }
                        }
                        katakana = buf.toString();

                        String encodeAnswer = answerData.getCoordinate() + "," + katakana + "," + String.valueOf(user.getPlayerNumber());
                        int tmpStatus = multiModel.wordsChoiceCheck(encodeAnswer);

						//multiModel.boardPositionOutput();
                        kanjiData = new KanjiData(Data.KANJI, multiModel.getBoard());

						//multiModel.printPositionOutput();
						kanjiData.setBoardPosition(multiModel.getBoardPosition());
						kanjiData.setScore(multiModel.getPlayerBaseScore(user.getPlayerNumber()));

						multiModel.printPositionOutput();

						if(tmpStatus == KanjiData.GOOD) {
							kanjiData.setStatus(KanjiData.GOOD);
						}
						else {
							kanjiData.setStatus(KanjiData.BAD);
						}
						System.out.println("user名:" + user.getName() + "が更新しました");
						System.out.println("Player" + user.getPlayerNumber() + "user名:" + user.getName() + "のスコア:" + multiModel.getPlayerBaseScore(user.getPlayerNumber()));
                        socketServer.sendData((Data)kanjiData);
                    }
					else if(status == AnswerData.REQUIRE_RESULT) {
                        kanjiData = new KanjiData(Data.KANJI, multiModel.getBoard());
						System.out.println("ボーナス点を加えたスコアを送ります");
						kanjiData.setResultScore(multiModel.getPlayerScore());
                        socketServer.sendData((Data)kanjiData);
					}
                }
            }
        }
        catch(EOFException eofe) {
			if(user != null) {
				System.out.println(user.getName() + "の接続が切断されました");
			}
			else {
				System.out.println("クライアントがホーム画面で切断しました");
			}
        }
        catch(IOException e) {
            System.out.println(e);
        }
        catch(ClassNotFoundException e) {
			System.err.println(e);
		}
    }
}
