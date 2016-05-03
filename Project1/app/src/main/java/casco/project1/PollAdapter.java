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
import java.util.List;

import casco.project1.dataBackend.Participant;
import casco.project1.dataBackend.Poll;
import casco.project1.dataBackend.TestPopulator;
import casco.project1.dataBackend.User;

/**
 * Created by Stefan on 4/30/2016.
 */
public class PollAdapter extends BaseAdapter {
    List<User> users;
    List<Participant> participants;
    List<Poll> polls;
    Context context;
    PollAdapter(Context c, TestPopulator data){
        context = c;
//        list = new ArrayList<TempProfile>();
//        Resources res = context.getResources();
//        String[] polls = res.getStringArray(R.array.test_polls);
//        String[] creators = res.getStringArray(R.array.test_participants);
//        int[] images = {
//                R.drawable.ic_person_red,
//                R.drawable.ic_person_orange,
//                R.drawable.ic_person_yellow,
//                R.drawable.ic_person_green,
//                R.drawable.ic_person_blue,
//                R.drawable.ic_person_purple,
//        };
//        for (int i = 0; i < polls.length;i++){
//            list.add(new TempProfile(i, polls[i],creators[i], images[i % images.length]));
//        }
            polls = data.polls;

    }
    @Override
    public int getCount() {

        return polls.size();
    }

    @Override
    public Object getItem(int position) {

        return polls.get(position);
    }

    @Override
    public long getItemId(int position) {

        return polls.get(position).getShortCode();
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
        poll.setText(polls.get(position).getTitle());
        creator.setText(polls.get(position).getCreator().getName());
        image.setImageResource(polls.get(position).getCreator().getImage());
        return row;
    }
}