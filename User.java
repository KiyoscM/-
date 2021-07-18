public class User {
	static final int SINGLE = 0;
	static final int BATTLE = 1;
	static final int MULTI = 2;
	static final int WAIT = 3;
	static final int LOGOUT = 4;

	private int id;
	private String name;
	private int score;
	private int status;

	private int playerNumber;

	private SocketServer socketServer;

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public void setSocketServer(SocketServer socketServer) {
		this.socketServer = socketServer;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return this.name;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getPlayerNumber() {
		return this.playerNumber;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void updateStatus(String[][] board, int[][] boardPosition, int score) {
		KanjiData kanjiData = new KanjiData(Data.KANJI, board);
		kanjiData.setStatus(KanjiData.MULTI_START);
		kanjiData.setScore(score);
		kanjiData.setBoardPosition(boardPosition);

		System.out.println("ユーザー" + this.name + "にデータを送信します");

		socketServer.sendData((Data)kanjiData);
	}

	public void update(String[][] board, int[][] boardPosition, int score) {
		System.out.println("boardが更新されました");
		KanjiData kanjiData = new KanjiData(Data.KANJI, board);
		kanjiData.setStatus(KanjiData.MULTI_UPDATE);
		kanjiData.setScore(score);
		kanjiData.setBoardPosition(boardPosition);
		System.out.println("ユーザー名: " + name);
		socketServer.sendData((Data)kanjiData);
	}
}
