package ke4a11.ecc.ac.jp.afururu.Money;

import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import ke4a11.ecc.ac.jp.afururu.R;

/*
TODO

一番下のnavigateUpTo(new Intent(this, MoneyActiviy_ListorCal.class));
の中身いじった。

 */

/*
******簡単な説明*****
* リストのアイテムを選択した後に表示される詳細画面のフラグメントをもたせているactivity
*
*
*
 */

public class MoneyActivity_ListorCal_Detail extends AppCompatActivity {

    String ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_listorcal_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        //DB用
        MoneyOpenHelper helper = new MoneyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        //final型でないとid登録できなかったのでStringがたに変換
       // final String ids = String.valueOf(id);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intentの準備
                Intent i = getIntent();
                int id =i.getIntExtra("position", 1);

                if(id!=0){
                    //数合わせ
                    id += 1;
                }

                final String ids = String.valueOf(id);
                db.delete("ecc","id=?",new String[]{ids});

                Intent ik = new Intent(getApplicationContext(),MoneyActiviy_ListorCal.class);
                startActivity(ik);
                Toast.makeText(MoneyActivity_ListorCal_Detail.this, "削除しました", Toast.LENGTH_SHORT).show();
            }
        });

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            //押した場所のIDを持っている サンプルの場合だと Bundle[{item_id=1}] など
            arguments.putString(Money_ListorCal_Detail.ARG_ITEM_ID,
                    getIntent().getStringExtra(Money_ListorCal_Detail.ARG_ITEM_ID));

            //Money_ListorCal_Detailにデータを渡す準備
            Money_ListorCal_Detail fragment = new Money_ListorCal_Detail();
            fragment.setArguments(arguments);

            //
            ids = getIntent().getStringExtra(Money_ListorCal_Detail.ARG_ITEM_ID);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //←ボタンを押した時に戻ると同じ動作をする
            navigateUpTo(new Intent(this, MoneyActiviy_ListorCal.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
