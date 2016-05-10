package casco.project1.Adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import casco.project1.Interfaces.ClickListener;
import casco.project1.R;
import casco.project1.dataBackend.RectangleView;
import casco.project1.dataBackend.Constants;

/**
 * Created by christianmello on 5/9/16.
 */
public class DragSelectRecyclerAdapterTimes
        extends DragSelectRecyclerViewAdapter<DragSelectRecyclerAdapterTimes.MainViewHolder>
{
    ClickListener clickListener;
    List<String> labelList = new ArrayList<String>();

    public DragSelectRecyclerAdapterTimes(ClickListener callback){
        super();
        clickListener = callback;
    }
<<<<<<< HEAD:Project1/app/src/main/java/casco/project1/Adapters/DragSelectRecyclerAdapterTimes.java
    public void loadTimes(String start, String end){
        Log.i("STEFAN", "START: "+start+" to END: "+end);
        int startTimeIndex = getTimeIndex(start);
        int endTimeIndex = getTimeIndex(end);
        load(startTimeIndex,endTimeIndex);
    }
    public void loadTimes(){
        int startTimeIndex = Constants.DefaultStartTimesHalfHour;//getTimeIndex(start);
        int endTimeIndex = Constants.DefaultEndTimesHalfHour;//getTimeIndex(end);
        load(startTimeIndex,endTimeIndex);
    }
    private void load(int start, int end){
        if (start < 0)
            start = 0;
        if (end > Constants.TimesHalfHour.length)
            end = Constants.TimesHalfHour.length;
        labelList = new ArrayList<String>();
        for(int i = start; i < end; i++){
=======
    public void load(int startTime, int endTime){
        labelList = new ArrayList<String>();
        for(int i = startTime; i < endTime + 1; i++)
>>>>>>> 250b708afe3d51aa686680b623ac2e7e3da3220e:Project1/app/src/main/java/casco/project1/Adapters/DragSelectRecyclerAdapter2.java
            labelList.add(Constants.TimesHalfHour[i]);
        }
    }
    private int getTimeIndex(String time){
        for(int i = 0; i < Constants.TimesHalfHour.length;i++){
            if(time.equals(Constants.TimesHalfHour[i])){
                Log.i("STEFAN", "FOUND TIME "+time+" INDEX: "+i);
                return i;
            }
        }
        Log.i("STEFAN", "TIME NOT FOUND");
        return 0;//-1
    }

    public String getItem(int index){
        if (labelList.get(index) == null)
            return "BLAAAAAHH";
        else
            return labelList.get(index);
    }
    @Override
    public int getItemCount() {
        return labelList.size();
    }


    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.griditem_poll_creation3,parent, false);
        return new MainViewHolder(v,clickListener);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.label.setText(getItem(position));

        final Drawable d;
        final Context c = holder.itemView.getContext();
        if (isIndexSelected(position))
        {
            d = new ColorDrawable(ContextCompat.getColor(c, R.color.grid_foreground_selected));
            holder.label.setTextColor(ContextCompat.getColor(c, R.color.grid_label_text_selected));
        }
        else
        {
            d = new ColorDrawable(ContextCompat.getColor(c, R.color.grid_foreground_normal));
            holder.label.setTextColor(ContextCompat.getColor(c, R.color.grid_label_text_normal));
        }
        holder.colorSquare.setBackground(d);
        //holder.colorSquare.setBackground(ContextCompat.getColor(c, R.color.grid_label_text_normal));

    }
    //MainViewHolder
    public class MainViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener
    {
        public final TextView label;
        public final RectangleView colorSquare;
        public final ClickListener clickListener;
        public MainViewHolder(View itemView, ClickListener callback) {
            super(itemView);
            clickListener = callback;
            label = (TextView) itemView.findViewById(R.id.label2);
            colorSquare = (RectangleView) itemView.findViewById(R.id.colorSquare2);
            this.itemView.setOnClickListener(this);
            this.itemView.setOnLongClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if( clickListener  != null){
                clickListener.onClick(getAdapterPosition());
            }
        }
        @Override
        public boolean onLongClick(View v) {
            if( clickListener  != null){
                clickListener.onLongClick(getAdapterPosition());
                return true;
            }
            return false;
        }
    }
}
