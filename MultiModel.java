import java.util.Random;

//import JukugoTest.Jukugo;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class MultiModel {

	private String[][] board;
	private int boardSize;
	private int playerBaseScore[]=new int[5];
	private int playerScore[]=new int[5];
	private int[][] boardPosition;

	private ArrayList<User> userList;
	private KanjiModel kanjiModel;
	
	
	
	
	private String[] boardUseKanji = new String[1000];
	private int boardUseKanjiIndex=0;

	
	
	public MultiModel(int boardSize, KanjiModel kanjiModel, User user) {

		userList = new ArrayList<User>();
		this.kanjiModel = kanjiModel;
		userList.add(user);

		this.board=new String[boardSize][boardSize];
		boardPosition=new int[boardSize][boardSize];
		this.boardSize=boardSize;
		for(int i=0;i<boardSize;i++) {
        	for(int j=0;j<boardSize;j++) {
        		board[i][j]="0";
        	}
		}
        for(int i=0;i<boardSize;i++) {
            for(int j=0;j<boardSize;j++) {
            	boardPosition[i][j]=0;
            }

		}

        for(int i=1;i<5;i++) {
        	playerScore[i]=0;
        	playerBaseScore[i]=0;
		}
	}

	public void setUser(User user) {
		userList.add(user);
	}

	public String[][] getBoard() {
		return this.board;
	}

	public int getUserListSize() {
		return userList.size();
	}

	public int[] getPlayerScore() {
		return this.playerScore;
	}

	public void boardPrint() {
		for(int i=0;i<boardSize;i++) {
        	for(int j=0;j<boardSize;j++) {
        		System.out.print(board[i][j]+" ");
        	}
        	System.out.println();
        }

	}

	//盤面に漢字を配置する
	public void boardInitialize() {
		int allCount=0;
		while(true) {
			allCount=allCount+boardInitializeSingleWords(allCount);
			
			for(int i=0;i<boardUseKanjiIndex;i++) {
			
				System.out.print(boardUseKanji[i]+" ");
				
			}
			System.out.println();
			boardPrint();
			
			if(allCount>=boardSize*boardSize) {
				break;	
			}
		}
	}

	private int boardInitializeSingleWords(int allCount) {
		ReturnValues startPosition=new ReturnValues();
		Random random=new Random();

		//盤面が漢字で半分以上埋まったら、上から熟語を入れる
		if((allCount>(int)((boardSize*boardSize)*7/8))){
			startPosition=startPosition();
		//半分以下なら、ランダムな位置に熟語を入れる
		}else {
			ReturnValues randomChoice=new ReturnValues();
			int tateRandom;
			int yokoRandom;
			while(true) {
				//スタート位置をランダムで選択
				tateRandom=random.nextInt(boardSize);
				yokoRandom=random.nextInt(boardSize);
				if(board[tateRandom][yokoRandom]=="0") {
					break;
				}
			}
			randomChoice.boardTate=tateRandom;
			randomChoice.boardYoko=yokoRandom;
			startPosition=randomChoice;
		}

		int tateyoko,plusminus,jukugoSize;
		String jukugo;

		if(allCount<(int)((boardSize*boardSize)/2)) {

			//0='tate',1='yoko'：縦か横かランダム選択
			tateyoko=random.nextInt(2);
			//0="plus",1="minus"：上か下か、左か右かランダム選択
			plusminus=random.nextInt(2);
			//何マス空いてるか調べる
			int jukugoSizeMax=checkEmpty(startPosition.boardTate,startPosition.boardYoko,tateyoko,plusminus);
			//入れる熟語のサイズ決定
			//jukugoSize=random.nextInt(jukugoSizeMax);
			jukugoSize=jukugoSizeMax;
			//熟語ランダム選択
			jukugo=wordsGet(jukugoSize);
		}else {
			tateyoko=random.nextInt(2);
			ReturnValuesStart jukugoKari=wordsSearch(tateyoko,startPosition.boardTate,startPosition.boardYoko);
			plusminus=jukugoKari.plusminus;
			jukugo=jukugoKari.result;
			jukugoSize=jukugo.length();
			//System.out.println(jukugo);
			//System.out.println(jukugoSize);
			//System.out.println(tateyoko);
			startPosition.boardTate=jukugoKari.startNewTate;
			startPosition.boardYoko=jukugoKari.startNewYoko;

			tateyoko=jukugoKari.tateyoko;
			//if(tateyoko==0) {
				//tateyoko=1;
			//}else {
				//tateyoko=0;
			//}
			//System.out.println(tateyoko);

			if(plusminus==1) {
				if(tateyoko==0) {
					startPosition.boardTate=startPosition.boardTate+jukugoSize-1;
				}else {
					startPosition.boardYoko=startPosition.boardYoko+jukugoSize-1;
				}
			}

			//System.out.println(startPosition.boardTate);
			//System.out.println(startPosition.boardYoko);

		}


		int jukugoSizeResult=jukugoSize;
		
		
		
		boardUseKanji[boardUseKanjiIndex]=jukugo;
		boardUseKanjiIndex++;
		
		

		//盤面に熟語入れる
		if(tateyoko==0) {
			if(plusminus==0) {
				for(int i=0;i<jukugoSize;i++) {
					if(!(board[startPosition.boardTate+i][startPosition.boardYoko].equals("0"))) {
						jukugoSizeResult=jukugoSizeResult-1;
					}
					board[startPosition.boardTate+i][startPosition.boardYoko]=jukugo.substring(i,i+1);
				}
			}else {
				//tate,下から上
				for(int i=0;i<jukugoSize;i++) {
					if(!(board[startPosition.boardTate-i][startPosition.boardYoko].equals("0"))) {
						jukugoSizeResult=jukugoSizeResult-1;
					}
					board[startPosition.boardTate-i][startPosition.boardYoko]=jukugo.substring(i,i+1);
				}
			}
		}else {
			if(plusminus==0) {
				for(int j=0;j<jukugoSize;j++) {
					if(!(board[startPosition.boardTate][startPosition.boardYoko+j].equals("0"))) {
						jukugoSizeResult=jukugoSizeResult-1;
					}
					board[startPosition.boardTate][startPosition.boardYoko+j]=jukugo.substring(j,j+1);
				}
			}else {
				//yoko,右から左
				for(int j=0;j<jukugoSize;j++) {
					if(!(board[startPosition.boardTate][startPosition.boardYoko-j].equals("0"))) {
						jukugoSizeResult=jukugoSizeResult-1;
					}
					board[startPosition.boardTate][startPosition.boardYoko-j]=jukugo.substring(j,j+1);
				}
			}
		}
		//System.out.println(jukugoSize);
		return jukugoSizeResult;
	}

	//盤面の上から調べて空いているマスの位置を返す関数
	private ReturnValues startPosition() {
		int tate,yoko;
		ReturnValues value=new ReturnValues();
		value.boardTate=boardSize;
		value.boardYoko=boardSize;
		out_of_loop:
		for(tate=0;tate<boardSize;tate++) {
			for(yoko=0;yoko<boardSize;yoko++) {
				if(board[tate][yoko]=="0") {
					value.boardTate=tate;
					value.boardYoko=yoko;
					break out_of_loop;
				}
			}
		}
		return value;
	}

	//指定されたスタート位置から方向に何マス空いているか調べて返す
	private int checkEmpty(int tate,int yoko,int tateyoko,int plusminus) {

		int emptyCount=0;
		int i,j;
		i=tate;
		j=yoko;
		//縦方向に調べる
		if(tateyoko==0) {
			if(plusminus==0) {
				while(board[i][j]=="0") {
					i++;
					emptyCount++;
					if(i>=boardSize) {
						break;
					}
				}
			}else {
				//下から上
				while(board[i][j]=="0") {
					i--;
					emptyCount++;
					if(i<0) {
						break;
					}
				}
			}
		//横方向に調べる
		}else {
			if(plusminus==0) {
				while(board[i][j]=="0") {
					j++;
					emptyCount++;
					if(j>=boardSize) {
						break;
					}
				}
			}else {
				//右から左
				while(board[i][j]=="0") {
					j--;
					emptyCount++;
					if(j<0) {
						break;
					}
				}
			}
		}
		if(emptyCount>=4) {
			emptyCount=4;
		}
		return emptyCount;
	}

	//指定されたサイズの熟語をランダムに選択して返す
	private String wordsGet(int wordsSize) {
		//JukugoTest kanjiModel=new JukugoTest();
		//Data data=new Data();
		Random random=new Random();

		/*

		if(wordsSize==1) {
			return kanjiModel.ls.get().get(random.nextInt(kanjiModel.ls.get(1).size())).problem;
		}else if(wordsSize==2) {
			return data.wordsList[2][random.nextInt(data.wordsList[2].length)];
		}else if(wordsSize==3) {
			return data.wordsList[3][random.nextInt(data.wordsList[3].length)];
		}else {
			return data.wordsList[4][random.nextInt(data.wordsList[4].length)];
		}

		*/

		return kanjiModel.ls.get(wordsSize).get(random.nextInt(kanjiModel.ls.get(wordsSize).size())).problem;

	}

	//choice="001020,はてな,1"
	public int wordsChoiceCheck(String choice) {

		String choiceWords="";
		String yomikata="";
		int playerNumber;

		//choices=["001020","はてな","1"]
		String[] choices = choice.split(",", 0);

		System.out.println(choices[0]);
		System.out.println(choices[1]);

		yomikata=choices[1];
		playerNumber=Integer.parseInt(choices[2]);

		//選択位置から熟語取得
		for(int i=0;i<choices[0].length()/2;i++) {
			int tate,yoko;
			tate=Integer.parseInt(choices[0].substring(i*2,i*2+1));
			yoko=Integer.parseInt(choices[0].substring(i*2+1,i*2+1+1));
			choiceWords=choiceWords+board[tate][yoko];
		}
		//熟語、よみかたをチェック
		int checkResult=checkwords(choiceWords,yomikata);

		System.out.println(choiceWords+yomikata);
		System.out.println(checkResult);
		if(checkResult==1) {
			boardPositionUpdate(choices[0],playerNumber);

			scoreCalculate(choiceWords,playerNumber);

			System.out.println();
			System.out.println("good");
			System.out.println();
			wordschange(choices[0], playerNumber);
		}else {
			System.out.println();
			System.out.println("bad");
			System.out.println();
		}

		return checkResult;
	}

	private void boardPositionUpdate(String choice,int playerNumber) {
		for(int i=0;i<choice.length()/2;i++) {
			int tate,yoko;
			tate=Integer.parseInt(choice.substring(i*2,i*2+1));
			yoko=Integer.parseInt(choice.substring(i*2+1,i*2+1+1));
			boardPosition[tate][yoko]=playerNumber;
		}

	}

	public int[][] boardPositionOutput(){
		return boardPosition;
	}

	public int[][] getBoardPosition() {
		return this.boardPosition;
	}

	public int getPlayerBaseScore(int playerNumber) {
		return this.playerBaseScore[playerNumber];
	}


	//デバッグ用
	public void printPositionOutput() {
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				System.out.print(boardPositionOutput()[i][j] + " ");
			}
		}
		System.out.println();
	}


	private void scoreCalculate(String choiceWords,int playerNumber) {

		//int[] playerScore=new int[5];

		playerBaseScore[playerNumber]=playerBaseScore[playerNumber]+choiceWords.length()*10;

		playerScore[1]=playerBaseScore[1]+scoreCalculateBoardPosition(1);
		playerScore[2]=playerBaseScore[2]+scoreCalculateBoardPosition(2);
		playerScore[3]=playerBaseScore[3]+scoreCalculateBoardPosition(3);
		playerScore[4]=playerBaseScore[4]+scoreCalculateBoardPosition(4);

		//return score;


	}

	private int scoreCalculateBoardPosition(int playerNumber) {
		int score=0;
		for(int i=0;i<boardSize;i++) {
        	for(int j=0;j<boardSize;j++) {
        		if(boardPosition[i][j]==playerNumber) {
        			score=score+10;
        		}
        	}
		}

		return score;


	}




	public int scoreOutput(int playerNumber) {
		return playerScore[playerNumber];
	}


	//選択された熟語、よみかたが正しいか確認する関数
	private int checkwords(String words,String yomikata) {
		//Data data=new Data();
		//JukugoTest kanjiModel=new JukugoTest();
		System.out.println("length"+words.length());
		for(int i=0;i<kanjiModel.ls.get(words.length()).size();i++) {
			System.out.println(kanjiModel.ls.get(words.length()).get(i).problem);
			System.out.println(kanjiModel.ls.get(words.length()).get(i).answer);
			if(kanjiModel.ls.get(words.length()).get(i).problem.equals(words)) {
				if(kanjiModel.ls.get(words.length()).get(i).answer.equals(yomikata)) {
					return 1;
				}
			}
		}

		return 0;


		/*

		if(words.length()==1) {
			if(Arrays.asList(data.wordsList[1]).contains(words)) {
				if(data.yomikataList[1][(Arrays.asList(data.wordsList[1]).indexOf(words))].equals(yomikata)) {
					return 1;
				}else {
					return 0;
				}

			}else {
				return 0;
			}

		}else if(words.length()==2) {
			if(Arrays.asList(data.wordsList[2]).contains(words)) {
				if(data.yomikataList[2][(Arrays.asList(data.wordsList[2]).indexOf(words))].equals(yomikata)) {
					return 1;
				}else {
					return 0;
				}

			}else {
				return 0;
			}

		}else if(words.length()==3) {
			if(Arrays.asList(data.wordsList[3]).contains(words)) {
				if(data.yomikataList[3][(Arrays.asList(data.wordsList[3]).indexOf(words))].equals(yomikata)) {
					return 1;
				}else {
					return 0;
				}
			}else {
				return 0;
			}

		}else {
			if(Arrays.asList(data.wordsList[4]).contains(words)) {
				if(data.yomikataList[4][(Arrays.asList(data.wordsList[4]).indexOf(words))].equals(yomikata)) {
					return 1;
				}else {
					return 0;
				}
			}else {
				return 0;
			}

		}

		*/

	}


	private void wordschange(String choice, int playerNumber) {
		for(int i=0;i<choice.length()/2;i++) {
			int tate,yoko;
			tate=Integer.parseInt(choice.substring(i*2,i*2+1));
			yoko=Integer.parseInt(choice.substring(i*2+1,i*2+1+1));
			board[tate][yoko]="0";
		}

		boardPrint();
		System.out.println();

		ReturnValues startPosition=new ReturnValues();

		int newWordsLengthPre=0;

		while(true) {

			startPosition=startPosition();
			if(startPosition.boardTate==boardSize) {
				break;
			}
			//System.out.println(startPosition.boardTate);
			//System.out.println(startPosition.boardYoko);

			int tateyoko;

			if(choice.length()>=4) {

				tateyoko=Integer.parseInt(choice.substring(3,4))-Integer.parseInt(choice.substring(1,2))%2;
			}else {
				tateyoko=0;
			}

			ReturnValuesStart newwords=wordsSearch(tateyoko,startPosition.boardTate,startPosition.boardYoko);
			
			
			boardUseKanji[boardUseKanjiIndex]=newwords.result;
			boardUseKanjiIndex++;
			
			
			
			
			newWordsLengthPre+=newwords.result.length();
			for(int i=0;i<newwords.result.length();i++) {
				if(newwords.tateyoko==0) {
					if(newwords.plusminus==0) {
						board[newwords.startNewTate+i][newwords.startNewYoko]=newwords.result.substring(i, i+1);
					}else {
						//tate,minus
						board[newwords.startNewTate+i][newwords.startNewYoko]=newwords.result.substring(newwords.result.length()-1-i,newwords.result.length()-1-i+1);
					}
				}else {
					if(newwords.plusminus==0) {

						board[newwords.startNewTate][newwords.startNewYoko+i]=newwords.result.substring(i, i+1);
					}else {
						//yoko,minus
						board[newwords.startNewTate][newwords.startNewYoko+i]=newwords.result.substring(newwords.result.length()-1-i,newwords.result.length()-1-i+1);
					}
				}
			}
		}

		//boardが更新されたので他のユーザーにも知らせる
		userNotify(playerNumber);
	}

	public void notifyAddUser(int playerNumber) {
		if(userList.size() == 4) {
			for(User user : userList) {
				if(user.getPlayerNumber() != playerNumber) {
					user.updateStatus(this.board, this.boardPosition, this.playerBaseScore[user.getPlayerNumber()]);
				}
			}
		}
	}

	public void userNotify(int playerNumber) {
		//自分以外のユーザーに知らせる
		System.out.println("変更したプレイヤ番号" + playerNumber);
		for(User user : userList) {
			System.out.println("user番号" + user.getPlayerNumber() + "ユーザー名" + user.getName());
			if(user.getPlayerNumber() != playerNumber) {
				user.update(this.board, this.boardPosition, this.playerBaseScore[user.getPlayerNumber()]);
			}
		}
	}


	//tate=0,yoko=1
	private ReturnValuesStart wordsSearch(int tateyoko,int tate,int yoko) {

		ReturnValuesStart resultLast = new ReturnValuesStart();


		//System.out.println(wordsSize);

		if(tateyoko==0) {

			resultLast.tateyoko=1;
			resultLast.plusminus=0;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(yoko-3+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-3+i]+board[tate][yoko-2+i]+board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(4,wordsKari);
		//				System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-3+i;
								return resultLast;
							}
						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(yoko-3+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i]+board[tate][yoko-2+i]+board[tate][yoko-3+i];
						String result=wordsSearchList(4,wordsKari);
		//				System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-3+i;
								return resultLast;
							}
						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(yoko-2+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-2+i]+board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(3,wordsKari);
			//			System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-2+i;
								return resultLast;
							}

						}

					}
				}
			}

			resultLast.plusminus=1;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(yoko-2+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i]+board[tate][yoko-2+i];
						String result=wordsSearchList(3,wordsKari);
			//			System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-2+i;
								return resultLast;
							}

						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(yoko-1+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(2,wordsKari);
				//		System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-1+i;
								return resultLast;
							}

						}

					}
				}
			}

			resultLast.plusminus=1;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(yoko-1+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i];
						String result=wordsSearchList(2,wordsKari);
				//		System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-1+i;
								return resultLast;
							}
						}

					}
				}
			}


			resultLast.tateyoko=0;
			resultLast.plusminus=0;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tate-3+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-3+i][yoko]+board[tate-2+i][yoko]+board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(4,wordsKari);
					//	System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("0000"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate-3+i;
									resultLast.startNewYoko=yoko;
									return resultLast;
								}

							}

						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tate-3+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko]+board[tate-2+i][yoko]+board[tate-3+i][yoko];
						String result=wordsSearchList(4,wordsKari);
					//	System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("0000"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate-3+i;
									resultLast.startNewYoko=yoko;
									return resultLast;
								}

							}

						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(tate-2+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-2+i][yoko]+board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(3,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("000"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate-2+i;
									resultLast.startNewYoko=yoko;
									return resultLast;
								}
							}

						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(tate-2+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko]+board[tate-2+i][yoko];
						String result=wordsSearchList(3,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("000"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate-2+i;
									resultLast.startNewYoko=yoko;
									return resultLast;
								}
							}

						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(tate-1+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(2,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("00"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate-1+i;
									resultLast.startNewYoko=yoko;
									return resultLast;
								}
							}

						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(tate-1+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko];
						String result=wordsSearchList(2,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("00"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate-1+i;
									resultLast.startNewYoko=yoko;
									return resultLast;
								}
							}

						}

					}
				}
			}



			resultLast.tateyoko=0;
			resultLast.plusminus=0;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tate-3+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-3+i][yoko]+board[tate-2+i][yoko]+board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(4,wordsKari);
					//	System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("0000"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-3+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}

							//}

						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tate-3+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko]+board[tate-2+i][yoko]+board[tate-3+i][yoko];
						String result=wordsSearchList(4,wordsKari);
					//	System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
							//if(!(wordsKari.equals("0000"))){
								resultLast.result=result;
								resultLast.startNewTate=tate-3+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}

							//}

						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(tate-2+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-2+i][yoko]+board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(3,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("000"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-2+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
							//}

						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(tate-2+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko]+board[tate-2+i][yoko];
						String result=wordsSearchList(3,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("000"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-2+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
							//}

						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(tate-1+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(2,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
							//if(!(wordsKari.equals("00"))){
								resultLast.result=result;
								resultLast.startNewTate=tate-1+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
							//}

						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(tate-1+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko];
						String result=wordsSearchList(2,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("00"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-1+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
							//}

						}

					}
				}
			}




			Random random=new Random();
			int wordsSizeNew=1;
			//System.out.println(wordsSize);
			//System.out.println(wordsSizeNew);
			//Data data=new Data();
			//JukugoTest kanjiModel=new JukugoTest();
			int wordsListChoice=random.nextInt(kanjiModel.ls.get(wordsSizeNew).size());
			//System.out.println(data.wordsList[wordsSizeNew][wordsListChoice]);
			resultLast.result=kanjiModel.ls.get(wordsSizeNew).get(wordsListChoice).problem;
			resultLast.startNewTate=tate;
			resultLast.startNewYoko=yoko;
			return resultLast;





			//return "0notfound";


		}else {

			resultLast.tateyoko=0;
			resultLast.plusminus=0;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tate-3+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-3+i][yoko]+board[tate-2+i][yoko]+board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(4,wordsKari);
				//		System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-3+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(tate-3+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko]+board[tate-2+i][yoko]+board[tate-3+i][yoko];
						String result=wordsSearchList(4,wordsKari);
				//		System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-3+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(tate-2+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-2+i][yoko]+board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(3,wordsKari);
			//			System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-2+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(tate-2+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko]+board[tate-2+i][yoko];
						String result=wordsSearchList(3,wordsKari);
			//			System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-2+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(tate-1+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate-1+i][yoko]+board[tate+i][yoko];
						String result=wordsSearchList(2,wordsKari);
					//	System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-1+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
						}

					}
				}
			}

			resultLast.plusminus=1;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(tate-1+i>=0 && tate+i<boardSize) {
						String wordsKari=board[tate+i][yoko]+board[tate-1+i][yoko];
						String result=wordsSearchList(2,wordsKari);
					//	System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate-1+i;
								resultLast.startNewYoko=yoko;
								return resultLast;
							}
						}

					}
				}
			}


			resultLast.tateyoko=1;
			resultLast.plusminus=0;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(yoko-3+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-3+i]+board[tate][yoko-2+i]+board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(4,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("0000"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate;
									resultLast.startNewYoko=yoko-3+i;
									return resultLast;
								}
							}

						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(yoko-3+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i]+board[tate][yoko-2+i]+board[tate][yoko-3+i];
						String result=wordsSearchList(4,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("0000"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate;
									resultLast.startNewYoko=yoko-3+i;
									return resultLast;
								}
							}

						}

					}
				}
			}


			resultLast.plusminus=0;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(yoko-2+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-2+i]+board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(3,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("000"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate;
									resultLast.startNewYoko=yoko-2+i;
									return resultLast;
								}
							}
						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(yoko-2+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i]+board[tate][yoko-2+i];
						String result=wordsSearchList(3,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("000"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate;
									resultLast.startNewYoko=yoko-2+i;
									return resultLast;
								}
							}
						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(yoko-1+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(2,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("00"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate;
									resultLast.startNewYoko=yoko-1+i;
									return resultLast;
								}
							}
						}

					}
				}
			}

			resultLast.plusminus=1;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(yoko-1+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i];
						String result=wordsSearchList(2,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(wordsKari.equals("00"))){
								if(!(Arrays.asList(boardUseKanji).contains(result))){
									resultLast.result=result;
									resultLast.startNewTate=tate;
									resultLast.startNewYoko=yoko-1+i;
									return resultLast;
								}
							}
						}

					}
				}
			}



			resultLast.tateyoko=1;
			resultLast.plusminus=0;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(yoko-3+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-3+i]+board[tate][yoko-2+i]+board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(4,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("0000"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-3+i;
								return resultLast;
							}
							//}

						}

					}
				}
			}

			resultLast.plusminus=1;

			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(yoko-3+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i]+board[tate][yoko-2+i]+board[tate][yoko-3+i];
						String result=wordsSearchList(4,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
							//if(!(wordsKari.equals("0000"))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-3+i;
								return resultLast;
							}
							//}

						}

					}
				}
			}

			resultLast.plusminus=0;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(yoko-2+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-2+i]+board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(3,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("000"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-2+i;
								return resultLast;
							}
							//}
						}

					}
				}
			}


			resultLast.plusminus=1;

			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(yoko-2+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i]+board[tate][yoko-2+i];
						String result=wordsSearchList(3,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("000"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-2+i;
								return resultLast;
							}
							//}
						}

					}
				}
			}


			resultLast.plusminus=0;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(yoko-1+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko-1+i]+board[tate][yoko+i];
						String result=wordsSearchList(2,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("00"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-1+i;
								return resultLast;
							}
							//}
						}

					}
				}
			}

			resultLast.plusminus=1;

			for(int i=0;i<2;i++) {
				for(int j=0;j<2;j++) {
					if(yoko-1+i>=0 && yoko+i<boardSize) {
						String wordsKari=board[tate][yoko+i]+board[tate][yoko-1+i];
						String result=wordsSearchList(2,wordsKari);
						//System.out.println(wordsKari);
						if(!(result.contains("0"))){
							//if(!(wordsKari.equals("00"))){
							if(!(Arrays.asList(boardUseKanji).contains(result))){
								resultLast.result=result;
								resultLast.startNewTate=tate;
								resultLast.startNewYoko=yoko-1+i;
								return resultLast;
							}
							//}
						}

					}
				}
			}




			Random random=new Random();
			//System.out.println(wordsSize);
			int wordsSizeNew=1;
			//System.out.println(wordsSizeNew);
			//Data data=new Data();
			//int wordsListChoice=random.nextInt(data.wordsList[wordsSizeNew].length);
			//System.out.println(data.wordsList[wordsSizeNew][wordsListChoice]);
			//resultLast.result=data.wordsList[wordsSizeNew][wordsListChoice];


			//JukugoTest kanjiModel=new JukugoTest();
			int wordsListChoice=random.nextInt(kanjiModel.ls.get(wordsSizeNew).size());
			//System.out.println(data.wordsList[wordsSizeNew][wordsListChoice]);
			resultLast.result=kanjiModel.ls.get(wordsSizeNew).get(wordsListChoice).problem;



			resultLast.startNewTate=tate;
			resultLast.startNewYoko=yoko;
			return resultLast;


			//return "0notfound";

		}

	}



	private String wordsSearchList(int wordsLength,String searchWords) {

		//Data data=new Data();
		//JukugoTest kanjiModel=new JukugoTest();
		int[] lengthWordsData=new int[5];
		lengthWordsData[1]=kanjiModel.ls.get(1).size();
		lengthWordsData[2]=kanjiModel.ls.get(2).size();
		lengthWordsData[3]=kanjiModel.ls.get(3).size();
		lengthWordsData[4]=kanjiModel.ls.get(4).size();

		int i=0;
		for(int j=0;j<lengthWordsData[wordsLength];j++){
			for(i=0;i<wordsLength;i++) {
				if(!(searchWords.substring(i, i+1).equals(kanjiModel.ls.get(wordsLength).get(j).problem.substring(i, i+1)))) {

					if(!(searchWords.substring(i, i+1).equals("0"))){

							break;
					}
				}
			}
			//System.out.println(i);
			if(i==wordsLength) {
				return kanjiModel.ls.get(wordsLength).get(j).problem;
			}
		}

		return "0notfound";

	}

	public String[][] boardOutput(){
		return board;
	}

}

//熟語のスタート位置情報保持
class ReturnValues{
	public int boardTate;
	public int boardYoko;
}

class ReturnValuesStart{
	public String result;
	public int startNewTate;
	public int startNewYoko;
	public int tateyoko;
	public int plusminus;
}

/*
//漢字データ、ArrayListの2次元配列
class JukugoTest{

	ArrayList<ArrayList<Jukugo>> ls =new ArrayList<>();

	class Jukugo{
		String problem;
		String answer;
		Jukugo(String problem, String answer){
			this.problem = problem;
			this.answer = answer;
		}
	}

	JukugoTest(){

		ArrayList<Jukugo> ls0 = new ArrayList<Jukugo>();

		ArrayList<Jukugo> ls1 = new ArrayList<Jukugo>();
		ls1.add(new Jukugo("鳥","とり"));
		ls1.add(new Jukugo("肉","にく"));

		ArrayList<Jukugo> ls2 = new ArrayList<Jukugo>();
		ls2.add(new Jukugo("海老","えび"));
		ls2.add(new Jukugo("英語","えいご"));

		ArrayList<Jukugo> ls3 = new ArrayList<Jukugo>();
		ls3.add(new Jukugo("天麩羅","てんぷら"));
		ls3.add(new Jukugo("大車輪","だいしゃりん"));

		ArrayList<Jukugo> ls4 = new ArrayList<Jukugo>();
		ls4.add(new Jukugo("焼肉定食","やきにくていしょく"));
		ls4.add(new Jukugo("臥薪嘗胆","がしんしょうたん"));

		ls.add(ls0);
		ls.add(ls1);
		ls.add(ls2);
		ls.add(ls3);
		ls.add(ls4);

		//System.out.println(ls.get(1).size());
		//System.out.println(ls.get(2).size());
		//System.out.println(ls.get(3).size());
		//System.out.println(ls.get(4).size());
	}
}
*/
