import java.util.ArrayList;
import java.util.Random;
import java.sql.*;
import java.util.LinkedHashSet;

public class KanjiModel {
    class Kanji {
        String problem;
        String answer;
        Kanji(String problem, String answer){
            this.problem = problem;
            this.answer = answer;
        }
    }

    ArrayList<ArrayList<Kanji>> ls;
    ArrayList<Kanji> selected;
    Random rand;

    KanjiModel(DBAccesser accesser) {

    	ls = new ArrayList<>();
    	selected = new ArrayList<Kanji>();
    	rand = new Random();


		//TODO 引数にする
		//int level = 3;
		int level = 5;
		int record = 20;
    	ArrayList<Kanji> ls0 = new ArrayList<Kanji>();
    	ArrayList<Kanji> ls1 = new ArrayList<Kanji>();
    	ArrayList<Kanji> ls2 = new ArrayList<Kanji>();
    	ArrayList<Kanji> ls3 = new ArrayList<Kanji>();
    	ArrayList<Kanji> ls4 = new ArrayList<Kanji>();

		ls.add(ls0);
		ls.add(ls1);
		ls.add(ls2);
		ls.add(ls3);
		ls.add(ls4);

		//selectのところでエラーを解消するために一回nextしているのでrecord+1
		accesser.selectRecord(accesser.selectKanji(level, record));
		setKanjiList(accesser, 1);

		accesser.selectRecord(accesser.selectJukugo(level, record+1));
		setKanjiList(accesser, 2);

		accesser.selectRecord(accesser.selectSanjiJukugo(level, record));
		setKanjiList(accesser, 3);

		accesser.selectRecord(accesser.selectYojiJukugo(level, record+1));
		setKanjiList(accesser, 4);

		//重複する漢字を削除する
		ls0 = new ArrayList<Kanji>(new LinkedHashSet<>(ls0));
		ls1 = new ArrayList<Kanji>(new LinkedHashSet<>(ls1));
		ls2 = new ArrayList<Kanji>(new LinkedHashSet<>(ls2));
		ls3 = new ArrayList<Kanji>(new LinkedHashSet<>(ls3));
    }

	public void setKanjiList(DBAccesser accesser, int wordsLength) {
		try {

			DBAccesser db = new DBAccesser();
			while(accesser.result.next()) {
				//2文字以上ならば、別の読み方がないかどうかを調べる
				String kanji = accesser.getColumn("kanji");
				String furigana = accesser.getColumn("furigana");

				ls.get(wordsLength).add(new Kanji(kanji, furigana));

				System.out.println("kanji: " + kanji);
				System.out.println("furigana: " + furigana);

				int indexCurrentWord = 0;

				if(wordsLength == 1) {
					continue;
				}

				//漢字の中に一文字で読めるものがあるかどうか
				while(wordsLength - indexCurrentWord >= 1) {
					String str = String.valueOf(kanji.charAt(indexCurrentWord));
					db.selectRecord(db.selectKanji(str));

					String innerKanji = db.getColumn("kanji");
					String innerFurigana = db.getColumn("furigana");

					if(innerKanji.equals("")) {
						indexCurrentWord++;
						continue;
					}

					if(!innerFurigana.equals("*")) {
						ls.get(1).add(new Kanji(innerKanji, innerFurigana));
						System.out.println("innerKanji: " + innerKanji);
						System.out.println("innerFurigana: " + innerFurigana);
					}

					indexCurrentWord++;
				}

				//漢字の中に二文字で読めるものがあるかどうか
				indexCurrentWord = 0;

				if(wordsLength == 2) {
					continue;
				}

				while(wordsLength - indexCurrentWord >= 2) {
					String str = String.valueOf(kanji.charAt(indexCurrentWord)) + String.valueOf(kanji.charAt(indexCurrentWord + 1));
					db.selectRecord(db.selectJukugo(str));

					String innerKanji = db.getColumn("kanji");
					String innerFurigana = db.getColumn("furigana");

					if(innerKanji.equals("")) {
						indexCurrentWord++;
						continue;
					}

					if(!innerFurigana.equals("*")) {
						ls.get(2).add(new Kanji(innerKanji, innerFurigana));
						System.out.println("innerKanji: " + innerKanji);
						System.out.println("innerFurigana: " + innerFurigana);
					}

					indexCurrentWord++;
				}

				//漢字の中に三文字で読めるものがあるかどうか
				indexCurrentWord = 0;

				if(wordsLength == 3) {
					continue;
				}

				while(wordsLength - indexCurrentWord >= 3) {
					String str = String.valueOf(kanji.charAt(indexCurrentWord)) + String.valueOf(kanji.charAt(indexCurrentWord + 1)) + String.valueOf(kanji.charAt(indexCurrentWord + 2));
					db.selectRecord(db.selectSanjiJukugo(str));

					String innerKanji = db.getColumn("kanji");
					String innerFurigana = db.getColumn("furigana");

					if(innerKanji.equals("")) {
						indexCurrentWord++;
						continue;
					}

					if(!innerFurigana.equals("*")) {
						ls.get(3).add(new Kanji(innerKanji, innerFurigana));
						System.out.println("innerKanji: " + innerKanji);
						System.out.println("innerFurigana: " + innerFurigana);
					}

					indexCurrentWord++;
				}
			}
			System.out.println(ls.get(1).size());
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}

    public String getKanji(){
		int wordsLength = rand.nextInt(ls.size());

		if(wordsLength == 0) {
			wordsLength++;
		}
		int tmp = ls.get(wordsLength).size();
		//System.out.println("jukugoの長さ: " + wordsLength + "そのリストのサイズ: " + tmp);
        //int index = rand.nextInt(ls.get(wordsLength).size());
        int index = rand.nextInt(tmp);
		//System.out.println("index: " + index);
        Kanji j = ls.get(wordsLength).get(index);

        selected.add(j);
        ls.get(wordsLength).remove(index);

		for(int innerSelectLength = 1; innerSelectLength < wordsLength; innerSelectLength++){
			for(int beginIndex = 0; beginIndex + innerSelectLength <= wordsLength; beginIndex++){
				String innerJukugo = j.problem.substring(beginIndex, beginIndex + innerSelectLength);
			 	//innerJukugoがlsにあるか確かめる
			 	for(int jukugoSearchIndex = 0; jukugoSearchIndex < ls.get(innerSelectLength).size(); jukugoSearchIndex++){
			  		if(ls.get(innerSelectLength).get(jukugoSearchIndex).problem.equals(innerJukugo)){
			   			//→あればKanji ij = を生成、selected.add(ij)、lsからijを削除;
			   			Kanji ij = new Kanji(innerJukugo, ls.get(innerSelectLength).get(jukugoSearchIndex).answer);
			   			selected.add(ij);
			   			ls.get(innerSelectLength).remove(jukugoSearchIndex);
			  		}
			 	}
			}
		}

        return j.problem;
    }
}
