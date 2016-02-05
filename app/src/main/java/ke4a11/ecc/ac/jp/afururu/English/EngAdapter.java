package ke4a11.ecc.ac.jp.afururu.English;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import ke4a11.ecc.ac.jp.afururu.R;

/**
 * Created by it on 2016/01/28.
 */

public class EngAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private LinkedHashMap<String, List<String>> eng_category;
    private List<String> eng_list;


    public EngAdapter(Context ctx, LinkedHashMap<String, List<String>> eng_category, List<String> eng_list)
    {
        this.ctx = ctx;
        this.eng_category = eng_category;
        this.eng_list = eng_list;

    }

    @Override
    public int getGroupCount() {
        return eng_list.size();
    }

    @Override
    //カスタマイズ
    public int getChildrenCount(int groupPosition) {
        return eng_category.get(eng_list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return eng_list.get(groupPosition);
    }

    @Override
    public Object getChild(int parent, int child) {
        return eng_category.get(eng_list.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parnet, boolean isExpanded, View convertView, ViewGroup parentview) {

        String group_title = (String) getGroup(parnet);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_layout, parentview,false);
        }
        //ExpandableViewList:親カテゴリテキスト
        TextView parnet_textview = (TextView) convertView.findViewById(R.id.textView);
        parnet_textview.setTypeface(null, Typeface.BOLD);
        parnet_textview.setTextColor(Color.BLUE);
        parnet_textview.setText(group_title);

        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastchild, View convertView, ViewGroup parentview) {

        String child_title = (String) getChild(parent,child);
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.child_layout, parentview,false);
        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.textView2);
        child_textview.setText(child_title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {

        return true;
    }
}
