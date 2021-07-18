public class AnswerData extends Data {
	private static final long serialVersionUID = 1L;
	static final int INIT = 0;
	static final int NOT_INIT = 1;
	static final int REQUIRE_RESULT = 2;

	private String coordinate;
	private String furigana;

	public AnswerData(int type, String coordinate, String furigana) {
		super(type);
		setCoordinate(coordinate);
		setFurigana(furigana);
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;;
	}

	public String getFurigana() {
		return furigana;
	}

	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}
}
