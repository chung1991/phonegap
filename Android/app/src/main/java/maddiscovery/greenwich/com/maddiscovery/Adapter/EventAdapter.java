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

public class EventAdapter extends BaseAdapter {
    private boolean editShow;
    public ArrayList<String> listPosition = new ArrayList();
    private ArrayList<TheEvent> listEvent;
    private LayoutInflater layoutInflater;
    private Context context;

    public EventAdapter(Context context, ArrayList<TheEvent> list) {
        this.context = context;
        this.listEvent = list;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listEvent != null ? listEvent.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listEvent.get(position);
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
            convertView = this.layoutInflater.inflate(R.layout.list_event_item, null);
            holder.setTextViewTitle((TextView) convertView.findViewById(R.id.lvTitle));
            holder.setTextViewTime((TextView) convertView.findViewById(R.id.lvTime));
            holder.setTextViewContent((TextView) convertView.findViewById(R.id.lvContent));
            holder.setCheckBoxEdit((CheckBox) convertView.findViewById(R.id.cBox));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TheEvent event = (TheEvent) getItem(position);
        holder.getTextViewTitle().setText(event.getTitle());
        holder.getTextViewTime().setText(event.getTime());
        holder.getTextViewContent().setText(event.getLocation());
        if (editShow) {
            holder.getCheckBoxEdit().setVisibility(View.VISIBLE);
            holder.getCheckBoxEdit().setChecked(event.getIscheck());
        } else {
            holder.getCheckBoxEdit().setVisibility(View.INVISIBLE);
        }
        holder.getCheckBoxEdit().setTag(position);
        holder.getCheckBoxEdit().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        return convertView;
    }

    private void removePosition(ArrayList<String> listPosition, int position) {
        for (int i = 0; i < listPosition.size(); i++) {
            if (listPosition.get(i).equals(String.valueOf(position))) {
                listPosition.remove(i);
            }
        }
    }
}
