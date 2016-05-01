package casco.project1;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Stefan on 4/30/2016.
 */
public class PollAdapter extends BaseAdapter {
    ArrayList<TempProfile> list;
    Context context;
    PollAdapter(Context c){
        context = c;
        list = new ArrayList<TempProfile>();
        Resources res = context.getResources();
        String[] polls = res.getStringArray(R.array.test_polls);
        String[] creators = res.getStringArray(R.array.test_participants);
        int[] images = {
                R.drawable.ic_person_red,
                R.drawable.ic_person_orange,
                R.drawable.ic_person_yellow,
                R.drawable.ic_person_green,
                R.drawable.ic_person_blue,
                R.drawable.ic_person_purple,
        };
        for (int i = 0; i < polls.length;i++){
            list.add(new TempProfile(i, polls[i],creators[i],
                    images[i % images.length]));
        }


    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return list.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // cannot use ID because each row is different
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_poll,parent, false);
        TextView poll = (TextView) row.findViewById(R.id.tv_row_poll);
        TextView creator = (TextView) row.findViewById(R.id.tv_row_poll_creator);
        ImageView image = (ImageView) row.findViewById(R.id.iv_profile_icon);
        poll.setText(list.get(position).getPoll());
        creator.setText(list.get(position).getCreator());
        image.setImageResource(list.get(position).getImageIndex());
        return row;
    }
}