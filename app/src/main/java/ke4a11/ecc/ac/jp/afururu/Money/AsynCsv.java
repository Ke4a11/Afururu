package ke4a11.ecc.ac.jp.afururu.Money;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;



public class AsynCsv extends AsyncTask<String, Integer, String> {

    //表示する金額の種類
    private final String currencies[] = {"CAD","EUR","GBP","JPY"};

    private HttpClient hClient;
    private HttpGet hGetMethod;
    private _MoneyTop moneyTop;
    private Money_Exchange money_exchange;
    private int i=-1;

    //private ServiceMoneyExchange serviceMoneyExchange;
/*
    public AsynCsv(ServiceMoneyExchange service){
        serviceMoneyExchange = service;
        hClient = new DefaultHttpClient();
        hGetMethod = new HttpGet();
    }
*/
    // コンストラクタ
    public AsynCsv(_MoneyTop fragment,int ii){

        //呼ばれた元がMoneyExchangeかMoneyTopか分ける処理
        i=ii;
        moneyTop = fragment;
        hClient = new DefaultHttpClient();
        hGetMethod = new HttpGet();
    }

    //コンスト楽た
    public AsynCsv(Money_Exchange fragment,int ii){

        //呼ばれた元がMoneyExchangeかMoneyTopか分ける処理
        i=ii;
        money_exchange = fragment;
        hClient = new DefaultHttpClient();
        hGetMethod = new HttpGet();
    }


    // バックグラウンドで処理する（重い処理）
    @Override
    protected String doInBackground(String... params) {
        String uri = params[0];
        return httpGet(uri); //onPostExecute に渡されるString型の変数uri
    }

    // バックグラウンド処理が終了した後にメインスレッドに渡す処理
    @Override
    protected void onPostExecute(String result) {

        if (i==0){
        moneyTop.setCurrency(result); //MoneyTopのsetCurrencyメソッド
    }else{
        money_exchange.setCurrency(result);

        }

    }

    private String httpGet(String uri){

        try{
            hGetMethod.setURI(new URI(uri));
            HttpResponse resp = hClient.execute(hGetMethod);

            //動作を入力に設定
            InputStream stream = resp.getEntity().getContent();
            BufferedReader input = new BufferedReader(new InputStreamReader(stream));

            //データ取得
            String data = "";
            String tmp = "";
            //カンマで分けたものを一時的に保持する変数
            String splited[] = {};

            while((tmp = input.readLine())!=null){
                //splitedにカンマ区切りで文字を入れる
                splited = tmp.split(",");
                //カンマで分けた文だけ回す
                for (int i = 0; i < splited.length; i++){
                    //日本円だけ取り出し
                    if (splited[i].equals("JPY")){
                        //data += splited[i] + splited[i+1] + "\n";
                        data += splited[i+1];
                    }
                }
            }
/*
//フィールドcurrenciesにあるものだけ表示
for(int j = 0; j < currencies.length; j++){
                        //わざわざ回さなくてもいいが、だとするとサイトの順番を確認しながら配列を指定する必要がある
                        //変わらないと思うが、すこし表示が遅くなった？
                        if (splited[i].equals("JPY")){
                            //data += splited[i] + splited[i+1] + "\n";
                            data += splited[i+1];
                        }
                    }
 */
            //終了
            stream.close();
            input.close();
            return data;

        }catch(Exception e){
            return e.toString();
        }
    }
}
