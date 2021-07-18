import java.util.ArrayList;
import java.util.Random;

public class JukugoTest{
    class Jukugo{
        String problem;
        String answer;
        Jukugo(String problem, String answer){
            this.problem = problem;
            this.answer = answer;
        }
    }
    ArrayList<Jukugo> ls = new ArrayList<Jukugo>();
    ArrayList<Jukugo> selected = new ArrayList<Jukugo>();
    Random rand = new Random();

    JukugoTest(){
        ls.add(new Jukugo("観光", "かんこう"));
        ls.add(new Jukugo("着物", "きもの"));
        ls.add(new Jukugo("最古", "さいこ"));
        ls.add(new Jukugo("産物", "さんぶつ"));
        ls.add(new Jukugo("都市", "とし"));
        ls.add(new Jukugo("家屋", "かおく"));
        ls.add(new Jukugo("人相", "にんそう"));
        ls.add(new Jukugo("別人", "べつじん"));
        ls.add(new Jukugo("名案", "めいあん"));
        ls.add(new Jukugo("行進", "こうしん"));
        ls.add(new Jukugo("自然", "しぜん"));
        ls.add(new Jukugo("近所", "きんじょ"));
        ls.add(new Jukugo("静脈", "じょうみゃく"));
        ls.add(new Jukugo("水気", "みずけ"));
        ls.add(new Jukugo("学問", "がくもん"));
        ls.add(new Jukugo("見学", "けんがく"));
        ls.add(new Jukugo("新型", "しんがた"));
        ls.add(new Jukugo("野原", "のはら"));
        ls.add(new Jukugo("弱気", "よわき"));
        ls.add(new Jukugo("連日", "れんじつ"));
        ls.add(new Jukugo("手間", "てま"));
        ls.add(new Jukugo("大漁", "たいりょう"));
        ls.add(new Jukugo("正体", "しょうたい"));
        ls.add(new Jukugo("入手", "にゅうしゅ"));
        ls.add(new Jukugo("遠足", "えんそく"));
        ls.add(new Jukugo("水田", "すいでん"));
        ls.add(new Jukugo("積雪", "せきせつ"));
        ls.add(new Jukugo("走者", "そうしゃ"));
        ls.add(new Jukugo("足音", "あしおと"));
        ls.add(new Jukugo("調合", "ちょうごう"));
        ls.add(new Jukugo("無言", "むごん"));
        ls.add(new Jukugo("悪気", "わるぎ"));
        ls.add(new Jukugo("札束", "さつたば"));
        ls.add(new Jukugo("標本", "ひょうほん"));
        ls.add(new Jukugo("役場", "やくば"));
        ls.add(new Jukugo("付近", "ふきん"));
        ls.add(new Jukugo("徒歩", "とほ"));
        ls.add(new Jukugo("利口", "りこう"));
        ls.add(new Jukugo("区画", "くかく"));
        ls.add(new Jukugo("合言葉", "あいことば"));
        ls.add(new Jukugo("青天井", "あおてんじょう"));
        ls.add(new Jukugo("青二才", "あおにさい"));
        ls.add(new Jukugo("悪循環", "あくじゅんかん"));
        ls.add(new Jukugo("悪知恵", "わるぢえ"));
        ls.add(new Jukugo("朝寝坊", "あさねぼう"));
        ls.add(new Jukugo("阿修羅", "あしゅら"));
        ls.add(new Jukugo("天邪鬼", "あまのじゃく"));
        ls.add(new Jukugo("雨模様", "あめもよう"));
        ls.add(new Jukugo("意気地", "いくじ"));
        ls.add(new Jukugo("居心地", "いごこち"));
        ls.add(new Jukugo("十六夜", "いざよい"));
        ls.add(new Jukugo("韋駄天", "いだてん"));
        ls.add(new Jukugo("猪鹿蝶", "いのしかちょう"));
        ls.add(new Jukugo("月", "つき"));
        ls.add(new Jukugo("右", "みぎ"));
        ls.add(new Jukugo("左", "ひだり"));
        ls.add(new Jukugo("糸", "いと"));
        ls.add(new Jukugo("耳", "みみ"));
        ls.add(new Jukugo("花", "はな"));
        ls.add(new Jukugo("草", "くさ"));
        ls.add(new Jukugo("音", "おと"));
        ls.add(new Jukugo("森", "もり"));
        ls.add(new Jukugo("親", "おや"));
        ls.add(new Jukugo("刀", "かたな"));
        ls.add(new Jukugo("弓", "ゆみ"));
        ls.add(new Jukugo("雪", "ゆき"));
        ls.add(new Jukugo("雨", "あめ"));
        ls.add(new Jukugo("魚", "さかな"));
        ls.add(new Jukugo("雲", "くも"));
        ls.add(new Jukugo("羊", "ひつじ"));
        ls.add(new Jukugo("牛", "うし"));
        ls.add(new Jukugo("兎", "うさぎ"));
        ls.add(new Jukugo("水", "みず"));
        ls.add(new Jukugo("衣", "ころも"));
        ls.add(new Jukugo("竹", "たけ"));
        ls.add(new Jukugo("空", "そら"));
        ls.add(new Jukugo("林", "はやし"));
        ls.add(new Jukugo("鼻", "はな"));
        ls.add(new Jukugo("髪", "かみ"));
        ls.add(new Jukugo("爪", "つめ"));
    }

    String getJukugo(){
        int index = rand.nextInt(ls.size());
        Jukugo j = ls.get(index);
        selected.add(j);
        ls.remove(index);
        return j.problem;
    }
}

