package casco.project1.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import casco.project1.R;
import casco.project1.dataBackend.Response;
import casco.project1.dataBackend.User;

/**
 * Created by Stefan on 4/30/2016.
 */
public class ResponseAdapter extends BaseAdapter {
    List<Response> list;
    Context context;
    public ResponseAdapter(Context c, List<Response> current){
        context = c;
        list = current;
    }
    public ResponseAdapter(Context c){
        context = c;
        list = new ArrayList<Response>();
        list.add(new Response(new User(), new Date()));
    }
    @Override
    public int getCount() {return list.size();}
    @Override
    public Object getItem(int position) {return list.get(position);}
    @Override
    public long getItemId(int position) {return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // cannot use ID because each row is different
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_response,parent, false);
        //TextView poll = (TextView) row.findViewById(R.id.tv_row_poll);

        TextView responder = (TextView) row.findViewById(R.id.tv_row_responder);
        TextView responseDate = (TextView) row.findViewById(R.id.tv_date_responded);
        ImageView responderImage = (ImageView) row.findViewById(R.id.iv_profile_icon);
        //poll.setText(list.get(position).getPoll());
        responder.setText(list.get(position).getCreator().getName());
        SimpleDateFormat format = new SimpleDateFormat("hh:mm, dd MMMM, yyyy", Locale.ENGLISH);
        String dateString = format.format(list.get(position).getDate());
        System.out.println(dateString);
        responseDate.setText(dateString);
        //responderImage.setImageResource(/*get the image used as profile avatar*/);
        return row;
    }
}
