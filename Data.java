import java.io.Serializable;

public class Data implements Serializable {
	private static final long serialVersionUID = 1L;

	static final int USER = 1;
	static final int KANJI = 2;
	static final int SINGLE_MODE_ANSWER = 3;
	static final int MULTI_MODE_ANSWER = 4;

	private int type;
	// TODO Singleモード、Multiモードのモード
	// TODO 初回ログインかそうでないか

	// 漢字があっているかどうかのstatus
	// ユーザがログインかどうか
	private int status;

	public Data(int type) {
		this.type = type;
	}

	public Data(int type, int status) {
		this.type = type;
		this.status = status;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
