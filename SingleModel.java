import java.util.Random;
//import java.util.Scanner;
//import java.io.*;

public class SingleModel {
    private final int HEIGHT = 6;
	private final int WIDTH = 4;
    private final int VISIBLE_HEIGHT = 4;
    private final int HORIZONTAL_RATIO = 4;//これ回に一回縦

	private final int GOOD = 1;
	private final int BAD = 2;

    private String[][] board;
    private String[][] sendBoard;
    private Random rand = new Random();
    //private JukugoTest kanjiModel = new JukugoTest();
    private KanjiModel kanjiModel;
//	private ArrayList<User> userList;
    private int score;//1文字消すと10点、全消しで100点

    //private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    SingleModel(KanjiModel kanjiModel){
		this.kanjiModel = kanjiModel;
        sendBoard = new String[4][4];
        score = 0;

        boardInitialize();
        boardGenerate();
        showAllBoard();
//        System.out.println();

//        Scanner scan = null;
/*        while(boardHasBlock()){
            System.out.println();
            showPartOfBoard();
            System.out.println("回答を入力してください");
            scan = new Scanner(System.in);
            String str = scan.nextLine();
            check(str);
        }
        scan.close();
*/
    }

    // SingleModel(KanjiModel kanjiModel, User user){
	// 	this.kanjiModel = kanjiModel;
	// 	userList = new ArrayList<User>();
	// 	this.userList.add(user);

    //     sendBoard = new String[4][4];

    //     boardInitialize();
    //     boardGenerate();
    //     showAllBoard();
//        System.out.println();

//        Scanner scan = null;
/*        while(boardHasBlock()){
            System.out.println();
            showPartOfBoard();
            System.out.println("回答を入力してください");
            scan = new Scanner(System.in);
            String str = scan.nextLine();
            check(str);
        }
        scan.close();
*/
    //}



    public String[][] getBoard() {
        for(int x=0; x<WIDTH; x++){
            for(int y=0; y<VISIBLE_HEIGHT; y++){
                sendBoard[VISIBLE_HEIGHT-y-1][x] = board[x][y];
            }
        }
        return this.sendBoard;
    }

    public int getScore(){
        return score;
    }

    //現在VISIBLE_HEIGHTより上に積みあがって不可視なブロック数を配列で返します
    public int[] getRest(){
        int[] rest = new int[WIDTH];
        for(int x=0; x<WIDTH; x++){
            switch(verticalBlockNumber(x)){
                case 6: rest[x] = 2; break;
                case 5: rest[x] = 1; break;
                default: rest[x] = 0; break;
            }
        }
        return rest;
    }

    int check(String inputStr){
        String[] splittedList = inputStr.split(",", 0);
        String choiceWords="";
        for(int i=0; i<splittedList[0].length()/2; i++){
            int x, y;
            x = Integer.parseInt(String.valueOf(splittedList[0].charAt(2*i+1)));
            System.out.println("列番号は" + x + "です");
            //y座標上下逆のため(左下が00)
            y = VISIBLE_HEIGHT -1 -Integer.parseInt(String.valueOf(splittedList[0].charAt(2*i)));
            System.out.println("行番号は" + y + "です");
            //y = Integer.parseInt(String.valueOf(splittedList[0].charAt(2*i+1)));
            System.out.println("選択された文字は" + board[x][y] + "です");
            choiceWords = choiceWords + board[x][y];
            System.out.println("現在のchoiseWordsは" + choiceWords + "です");
        }
        boolean downToUp = false;
        if(splittedList[0].length()>2){
            if(splittedList[0].charAt(0) > splittedList[0].charAt(2)){
                downToUp = true;
                System.out.println("下から上に読みます");
            }
        }
        boolean noJukugo=true;
        for(int index=0; index<kanjiModel.selected.size(); index++){
            if(choiceWords.equals(kanjiModel.selected.get(index).problem)) {
                noJukugo=false;
                if(splittedList[1].equals(kanjiModel.selected.get(index).answer)){
                    if(downToUp){
                        for(int i=0; i<splittedList[0].length()/2; i++){
                            int x, y;
                            x = Integer.parseInt(String.valueOf(splittedList[0].charAt(1)));
                            //y座標上下逆のため(左下が00)
                            y = VISIBLE_HEIGHT -1 -Integer.parseInt(String.valueOf(splittedList[0].charAt(0)));
                            deleteBlock(x, y);
                        }
                    }
                    else{
                        for(int i=0; i<splittedList[0].length()/2; i++){
                            int x, y;
                            x = Integer.parseInt(String.valueOf(splittedList[0].charAt(2*i+1)));
                            //y座標上下逆のため(左下が00)
                            y = VISIBLE_HEIGHT -1 -Integer.parseInt(String.valueOf(splittedList[0].charAt(2*i)));
                            deleteBlock(x, y);
                        }
                    }
                    if(!boardHasBlock()){
                        score += 100;
                    }
					return GOOD;
                }
                else{
                    System.out.println("読みが間違っています");
                }
            }
        }
        if(noJukugo){
            System.out.println("漢字がありません");
        }
		return BAD;
    }

    //指定した座標の1文字を削除し、スコアに10点を加算します
    void deleteBlock(int x, int y){
        for(int yi=y+1; yi<HEIGHT; yi++){
            board[x][yi-1] = board[x][yi];
        }
        board[x][HEIGHT-1] = "0";
        score += 10;
    }

    //盤面を生成し0で初期化します
    void boardInitialize(){
        board = new String[WIDTH][HEIGHT];
        for(int x=0; x<WIDTH; x++){
            for(int y=0; y<HEIGHT; y++){
                board[x][y] = "0";
            }
        }
    }

    //盤面に少なくとも一文字存在するかを返します
    boolean boardHasBlock(){
        for(int x=0; x<WIDTH; x++){
            if(!board[x][0].equals("0")){
                return true;
            }
        }
        return false;
    }

    //その縦列の文字数を返します
    int verticalBlockNumber(int x){
        int y;
        for(y=0; y<HEIGHT; y++){
            if(board[x][y].equals("0"))break;
        }
        return y;
    }

    //盤面内に長さlengthの文字列を挿入可能な箇所があるかを返します
    boolean nBlocksInsertable(int length){
        if(nBlocksVerticallyInsertable(length)){
            return true;
        }
        else if(nBlocksHorizontallyInsertable(length)){
            return true;
        }
        else{
            return false;
        }
    }

    //座標xの縦列に長さlengthの文字列を挿入可能かを返します
    boolean nBlocksVerticallyInsertable(int length, int x){
        if(verticalBlockNumber(x) + length <= HEIGHT){
            return true;
        }else{
            return false;
        }
    }

    //x座標を省略する場合、盤面内に縦向き挿入可能な箇所があるかを返します
    boolean nBlocksVerticallyInsertable(int length){
        for(int x=0; x<WIDTH; x++){
            if(nBlocksVerticallyInsertable(length, x)){
                return true;
            }
        }
        return false;
    }

    //現在の盤面状況で指定座標から右向きに長さlengthの文字列を挿入可能かを返します
    boolean nBlocksHorizontallyInsertable(int length, int x, int y){
        if(x + length > WIDTH)return false;
        for(int xi=0; xi<length; xi++){
            if(!board[x+xi][HEIGHT-1].equals("0"))return false;
        }
        if(y==0)return true;
        for(int xi=0; xi<length; xi++){
            if(board[x+xi][y-1].equals("0"))return false;
        }
        return true;
    }

    //y座標を省略する場合、挿入可能なy座標が最悪1つは存在するかどうかを返します
    boolean nBlocksHorizontallyInsertable(int length, int x){
        return nBlocksHorizontallyInsertable(length, x, 0);
    }

    //座標を省略する場合、盤面内に横向き挿入可能な箇所があるかを返します
    boolean nBlocksHorizontallyInsertable(int length){
        for(int x=0; x<WIDTH; x++){
            if(nBlocksHorizontallyInsertable(length, x)){
                return true;
            }
        }
        return false;
    }

    //盤面内に空欄が存在するかを返します
    boolean boardHasBlank(){
        return nBlocksInsertable(1);
    }

    //盤面をすべて表示します
    void showAllBoard(){
        for(int y=HEIGHT-1; y>=0; y--){
            for(int x=0; x<WIDTH; x++){
                System.out.print(board[x][y] + ", ");
            }
            System.out.println();
        }
    }

    //盤面を実際見せる範囲で表示します
    void showPartOfBoard(){
        for(int y=VISIBLE_HEIGHT-1; y>=0; y--){
            for(int x=0; x<WIDTH; x++){
                System.out.print(board[x][y] + ", ");
            }
            System.out.println();
        }
    }

    //指定座標に一文字blockを挿入します
    void oneBlockInsert(String block, int x, int y){
        for(int yi=HEIGHT-1; yi>y; yi--){
            board[x][yi] = board[x][yi-1];
        }
        board[x][y] = block;
    }

    //与えられた文字列を指定された座標から上向き縦方向に挿入します
    void multiBlocksVerticalInsert(String receivedStr, int x, int y){
        boolean direction = rand.nextBoolean();//熟語の向き。trueで正順、falseで逆順
        if(direction){
            for(int index=0; index<receivedStr.length(); index++){
                oneBlockInsert(String.valueOf(receivedStr.charAt(index)), x, y);
            }
        }
        else{
            for(int index=receivedStr.length()-1; index>=0; index--){
                oneBlockInsert(String.valueOf(receivedStr.charAt(index)), x, y);
            }
        }

    }

    //与えられた文字列を指定された座標から右向き横方向に挿入します
    void multiBlocksHorizontalInsert(String receivedStr, int x, int y){
        boolean direction = rand.nextBoolean();//熟語の向き。trueで正順、falseで逆順
        if(direction){
            for(int index=0; index<receivedStr.length(); index++){
                oneBlockInsert(String.valueOf(receivedStr.charAt(index)), x+index, y);
            }
        }
        else{
            for(int index=receivedStr.length()-1; index>=0; index--){
                oneBlockInsert(String.valueOf(receivedStr.charAt(index)), x+receivedStr.length()-index-1, y);
            }
        }
    }

    //与えられた文字列を挿入可能な箇所から無作為に選択し挿入します
    void oneJukugoInsert(String receivedStr){
        if(receivedStr.length()==1){
            int x;
            while(true){
                x = rand.nextInt(WIDTH);
                if(nBlocksVerticallyInsertable(1, x))break;
            }
            int y = rand.nextInt(verticalBlockNumber(x)+1);
            oneBlockInsert(receivedStr, x, y);
        }
        else{
            if(nBlocksInsertable(receivedStr.length())){
                int horizontality = 0;
                if(nBlocksVerticallyInsertable(receivedStr.length())){
                    if(nBlocksHorizontallyInsertable(receivedStr.length())){
                        horizontality = rand.nextInt(HORIZONTAL_RATIO);
                    }
                }
                else{
                    if(nBlocksHorizontallyInsertable(receivedStr.length())){
                        horizontality = 1;
                    }
                }
                int x;
                if(horizontality==0){
                    while(true){
                        x = rand.nextInt(WIDTH);
                        if(nBlocksVerticallyInsertable(receivedStr.length(), x))break;
                    }
                }
                else{
                    while(true){
                        x = rand.nextInt(WIDTH);
                        if(nBlocksHorizontallyInsertable(receivedStr.length(), x))break;
                    }
                }
                int y;
                if(horizontality==0){
                    y = rand.nextInt(VISIBLE_HEIGHT+1-receivedStr.length());
                    multiBlocksVerticalInsert(receivedStr, x, y);
                }
                else{
                    while(true){
                        y = rand.nextInt(VISIBLE_HEIGHT);
                        if(nBlocksHorizontallyInsertable(receivedStr.length(), x, y))break;
                    }
                    multiBlocksHorizontalInsert(receivedStr, x, y);
                }
            }
            //盤面上に置けない場合、何もしない
        }
    }

    //盤面上に全消し可能な一人用漢字パズルを生成します
    void boardGenerate() {
        String nextJukugo;
        while(boardHasBlank()) {
            nextJukugo = kanjiModel.getKanji();
            oneJukugoInsert(nextJukugo);
        }
    }
}
