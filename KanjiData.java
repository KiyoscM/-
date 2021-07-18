public class KanjiData extends Data {
	private static final long serialVersionUID = 1L;

	public static final int GOOD = 1;
	public static final int BAD = 0;

	public static final int MULTI_UPDATE = 3;
	public static final int MULTI_WAIT = 4;
	public static final int MULTI_START = 5;
	public static final int MULTI_STARTER = 6;


	private int x;
	private int y;
	private String kanji;
	private String furigana;
	private String[][] board;
	private int[][] boardPosition;

	private int score;

	private int[] resultScore;

	private int[] rest;

	public KanjiData(int type, int x, int y, String kanji, String furigana) {
		super(type);
		this.x = x;
		this.y = y;
		this.kanji = kanji;
		this.furigana = furigana;
	}

	public KanjiData(int type, String[][] board) {
		super(type);
		this.board = board;
	}

	public void setBoard(String[][] board) {
		this.board = board;
	}

	public String[][] getBoard() {
		return this.board;
	}

	public void setResultScore(int[] score) {
		this.resultScore = score;
	}

	public int[] getResultScore() {
		return this.resultScore;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}

	public void setBoardPosition(int[][] boardPosition) {
		this.boardPosition = boardPosition;
	}

	public int[][] getBoardPosition() {
		return this.boardPosition;
	}

	public void setX(int X) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int Y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}

	public String getKanji() {
		return kanji;
	}

	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}

	public String getFurigana() {
		return furigana;
	}

	public void setRest(int[] rest){
		this.rest = rest;
	}

	public int[] getRest(){
		return rest;
	}
}
