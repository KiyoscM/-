public class UserData extends Data {
	private static final long serialVersionUID = 1L;
	static final int LOGIN = 1;
	static final int CREATE = 2;
	static final int EXIST = 3;
	static final int EMPTY = 4;
	static final int ALREADY_EXIST = 5;
	static final int CREATE_SUCCESS = 6;

	private String name;
	private String password;

	public UserData() {
		super(Data.USER);
	}

	public UserData(int type, int status, String name, String password) {
		super(type, status);
		setName(name);
		setPassword(password);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return (name);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
