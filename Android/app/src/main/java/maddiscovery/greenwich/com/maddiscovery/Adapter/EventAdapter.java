package maddiscovery.greenwich.com.maddiscovery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import maddiscovery.greenwich.com.maddiscovery.Entity.TheEvent;
import maddiscovery.greenwich.com.maddiscovery.R;

/**
 * Created by Himura on 2016/03/16.
 */
public class EventAdapter extends BaseAdapter {
    boolean isShow;
    public ArrayList<String> listPosition = new ArrayList();
    private ArrayList<TheEvent> mNoteList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public EventAdapter(Context context, ArrayList<TheEvent> list) {
        this.mContext = context;
        this.mNoteList = list;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mNoteList != null ? mNoteList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mNoteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_event_item, null);
            holder.mTitle = (TextView) convertView.findViewById(R.id.lvTitle);
            holder.mTime = (TextView) convertView.findViewById(R.id.lvTime);
            holder.mContent = (TextView) convertView.findViewById(R.id.lvContent);
            holder.cBox = (CheckBox) convertView.findViewById(R.id.cBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TheEvent event = (TheEvent) getItem(position);
        holder.mTitle.setText(event.getTitle());
        holder.mTime.setText(event.getTime());
        holder.mContent.setText(event.getLocation());
        if (isShow) {
            holder.cBox.setVisibility(View.VISIBLE);
            holder.cBox.setChecked(event.getIscheck());
        } else {
            holder.cBox.setVisibility(View.INVISIBLE);
        }
        holder.cBox.setTag(position);
        holder.cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int position = Integer.valueOf(buttonView.getTag().toString());
                    listPosition.add(String.valueOf(position));
                } else {
                    int position = Integer.valueOf(buttonView.getTag().toString());
                    removePosition(listPosition, position);
                }
            }
        });
//        if(event.getIscheck() == true){
//            listPosition.add(position);
//        }
        return convertView;
    }

    private void removePosition(ArrayList<String> listPosition, int position) {
        for (int i = 0; i < listPosition.size(); i++) {
            if (listPosition.get(i).equals(String.valueOf(position))) {
                listPosition.remove(i);
            }
        }
    }

    public class ViewHolder {

        private TextView mTitle;
        private TextView mTime;
        private TextView mContent;
        private CheckBox cBox;
    }
}
