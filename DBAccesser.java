import java.sql.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;

public class DBAccesser {
	PreparedStatement pstmt;
	ResultSet result;
	Connection con;

	public DBAccesser() {
		/* 接続を表すConnectionオブジェクトを初期化 */
		con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			//ローカルmysql
			con = DriverManager.getConnection("jdbc:mysql://localhost/kanji", "root", "password");//url, user, password
			//クラウドmysql
			//con = DriverManager.getConnection("jdbc:mysql://instance.chbtu176qvyb.us-east-2.rds.amazonaws.com:3306/kanji?user=root&password=password");
			System.out.println("Mysql Connected....");
		}
		catch(ClassNotFoundException e) {
			System.out.println(e);
		}
		catch(NoSuchMethodException e) {
			System.out.println(e);
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		catch(InstantiationException e) {
			System.out.println(e);
		}
		catch(IllegalAccessException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(InvocationTargetException e) {
			System.out.println(e);
		}
	}

	public void selectRecord(String sql) {
		this.result = null;
		try {
			pstmt = con.prepareStatement(sql);
			this.result = pstmt.executeQuery();
			//TODO ここnextしてるところどうにかしたい
			result.next();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}

	public int countRecord(String sql) {
		this.result = null;
		int count = 0;
		try {
			pstmt = con.prepareStatement(sql);
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return count;
	}

	public boolean existRecord(String sql) {
		this.result = null;
		boolean existFlag = false;
		try {
			pstmt = con.prepareStatement(sql);
			this.result = pstmt.executeQuery();
			//recordが存在する
			if(result.next()) {
				existFlag = true;
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return existFlag;
	}

	public void createRecord(String sql) {
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}

	public void updateRecord(String sql) {
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}

	public String getColumn(String column) {
		String col = "";
		try {
			 col = result.getString(column);
		}
		catch(SQLException e) {
			//何も表示しない（EMPTY）
		}
		return col;
	}

	public String countUser(int state) {
		return String.format("SELECT ");
	}

	public String selectUser(String name, String password) {
		return String.format("SELECT * from user where name = \"%s\" and password = \"%s\";", name, password);
	}

	public String selectUser(String name) {
		return String.format("SELECT * from user where name = \"%s\";", name);
	}

	public String selectKanji(int level, int record) {
		return String.format("SELECT * FROM joyo_kanji JOIN kanji ON joyo_kanji.kanji = kanji.kanji WHERE kanji.level = %d AND joyo_kanji.furigana != \'*\' ORDER BY RAND() limit %d;", level, record);
	}

	public String selectJukugo(int level, int record) {
		return String.format("SELECT * FROM jukugo JOIN kanji ON jukugo.kanji like CONCAT(kanji.kanji, \'%s\') OR CONCAT(\'%s\', kanji.kanji) WHERE kanji.level = %d ORDER BY RAND() limit %d;", "%", "%", level, record);
		//return String.format("SELECT * FROM jukugo JOIN kanji ON jukugo.kanji like CONCAT(kanji.kanji, kanji.kanji) WHERE kanji.level = %d ORDER BY RAND() limit %d;", level, record);
	}

	public String selectSanjiJukugo(int level, int record) {
		return String.format("SELECT * FROM sanji_jukugo JOIN kanji ON sanji_jukugo.kanji like CONCAT(kanji.kanji, \'%s\') OR CONCAT(\'%s\', kanji.kanji) OR CONCAT(\'%s\', kanji.kanji, \'%s\') WHERE kanji.level = %d ORDER BY RAND() limit %d;", "%", "%", "%", "%", level, record);
		//return String.format("SELECT * FROM sanji_jukugo JOIN kanji ON sanji_jukugo.kanji like CONCAT(kanji.kanji, kanji.kanji, kanji.kanji) WHERE kanji.level = %d ORDER BY RAND() limit %d;", level, record);
	}

	public String selectYojiJukugo(int level, int record) {
		return String.format("SELECT * FROM yoji_jukugo JOIN kanji ON yoji_jukugo.kanji like CONCAT(kanji.kanji, \'%s\') OR CONCAT(\'%s\', kanji.kanji) OR CONCAT(\'%s\', kanji.kanji, \'%s\') WHERE kanji.level = %d ORDER BY RAND() limit %d;", "%", "%", "%", "%", level, record);
		//return String.format("SELECT * FROM yoji_jukugo JOIN kanji ON yoji_jukugo.kanji like CONCAT(kanji.kanji, kanji.kanji, kanji.kanji, kanji.kanji) WHERE kanji.level = %d ORDER BY RAND() limit %d;", level, record);
	}

	public String selectKanji(String str) {
		return String.format("SELECT * FROM joyo_kanji where kanji = \'%s\';", str);
	}

	public String selectJukugo(String str) {
		return String.format("SELECT * FROM jukugo where kanji = \'%s\';", str);
	}

	public String selectSanjiJukugo(String str) {
		return String.format("SELECT * FROM sanji_jukugo where kanji = \'%s\';", str);
	}

	public String createUser(String name, String password) {
		return String.format("INSERT INTO user (name, password) VALUES (\"%s\", \"%s\");", name, password);
	}
}
