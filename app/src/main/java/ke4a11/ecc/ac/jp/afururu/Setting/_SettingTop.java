package ke4a11.ecc.ac.jp.afururu.Setting;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ke4a11.ecc.ac.jp.afururu.R;

/**FrameLayout
 * A simple {@link Fragment} subclass.
 */

public class _SettingTop extends Fragment {

    public void onStart() {
        super.onStart();

        MyOpenHelper helper = new MyOpenHelper(getContext());
        final SQLiteDatabase db = helper.getWritableDatabase();
        final EditText nameText = (EditText) getActivity().findViewById(R.id.editName);
        final EditText ageText = (EditText) getActivity().findViewById(R.id.editAge);
        Button entryButton = (Button) getActivity().findViewById(R.id.insert);
        entryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();
                ContentValues insertValues = new ContentValues();
                insertValues.put("name", name);
                insertValues.put("age", age);
                long id = db.insert("person", name, insertValues);
            }
        });
        Button updateButton = (Button) getActivity().findViewById(R.id.update);
        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();
                if (name.equals("")) {

                    Toast.makeText(getActivity(), "名前を入力してください。", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues updateValues = new ContentValues();
                    updateValues.put("age", age);
                    db.update("person", updateValues, "name=?", new String[]{name});
                }
            }
        });

        Button deleteButton = (Button) getActivity().findViewById(R.id.delete);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(getActivity(), "名前を入力してください。",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.delete("person", "name=?", new String[]{name});
                }
            }
        });

        Button deleteAllButton = (Button) getActivity().findViewById(R.id.deleteAll);
        deleteAllButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();
                db.delete("person", null, null);
            }
        });

        Button detaBaseButton = (Button) getActivity().findViewById(R.id.dataBase);
        detaBaseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbIntent = new Intent(getActivity(),
                        ShowDataBase.class);
                startActivity(dbIntent);
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_top, container, false);
    }

}