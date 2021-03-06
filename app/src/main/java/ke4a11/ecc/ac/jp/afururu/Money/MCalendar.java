package ke4a11.ecc.ac.jp.afururu.Money;

import java.util.Calendar;


/*
    *****簡単な説明*****
    MCalendarのインスタンスを生成する時に自分自身を引数にする。
    それをコンストラクタでフィールドに代入して使えるようにする。
    makeCalendar()メソッドでカレンダーの値を持った
    calendarMatrix[][] ができる、そして
    最後に throwcal()メソッドで呼び出し元に カレンダーの変数を投げる。
    **************終わり
    *
    * 呼び出し元の説明
    * 受け取った変数を自分（MCalendarではない）のフィールドに代入して
    * それを使ってテキストビューに表示させている。


*/
public class MCalendar{

    //フィールド
    private Money_Calendar money_calendar; //

    //コンストラクタ
    public MCalendar(Money_Calendar fragment){
        money_calendar = fragment;
    }

    //カレンダーを二次元配列 calendarMatrix[][] に入れる
    public void makeCalendar(int year,int month){

        int showyear = year;
        int showmonth = month;
        int startDay;
        int lastDate1;
        int dayCount;
        int lastDate2;
        boolean isStart;
        int[][] calendarMatrix = new int [6][7];

        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        //月の初めの曜日を求める
        calendar.set(showyear, showmonth -1,1); // 引数: 1月: 0, 2月: 1, ...
        startDay = calendar.get(Calendar.DAY_OF_WEEK);//曜日を取得

        //今月末の日付を求める
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DATE,-1);
        lastDate1 = calendar.get(Calendar.DATE);//日を取得
        //lastDate1 = calendar.get(Calendar.DAY_OF_MONTH); でも同じ結果になる
        dayCount=1;

        //前月末の日付を求める
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE,-1);
        lastDate2 = calendar.get(Calendar.DATE);//日を取得

        //初期値ｾｯﾄ
        isStart = false;

        //前月末の日付用
        int x = 0;
        int y = 0;

        //今月の日にちを挿入
        for(int i=0 ; i < 6; i++){
            for(int j = 0; j < 7; j++){
                //先頭曜日確認
                //startDay:日曜日=1, 月曜日=2...
                if(isStart == false && (startDay -1) ==j){
                    //日にちのセット開始
                    isStart = true;
                    //今月のカレンダーに前月末表示用
                    y = i;
                    x = j;
                    lastDate2 = lastDate2 - (startDay -2);
                }
                if(isStart){
                    //終了日までいったか
                    calendarMatrix[i][j] = dayCount;
                    //カウント+1
                    dayCount++;
                    //終了確認
                    if(dayCount > lastDate1){
                        //来月初をセット
                        dayCount = 1;
                    }
                }
            }
        }
        //前月末の日にちを挿入
        for(int i = 0; i <= y; i++){
            for(int j = 0; j < x; j++){
                calendarMatrix[i][j] = lastDate2;
                lastDate2++;
            }
        }


        throwcal(calendarMatrix);

    }


    //Money_Calendarにカレンダーの値を持った calendarMatrix[][] を持っていく
    protected void throwcal(int[][] result){
        money_calendar.setcal(result);
    }
}
