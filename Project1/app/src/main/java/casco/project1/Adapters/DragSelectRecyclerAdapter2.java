package casco.project1.Adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
public class DragSelectRecyclerAdapter2
        extends DragSelectRecyclerViewAdapter<DragSelectRecyclerAdapter2.MainViewHolder>
{
    ClickListener clickListener;
    List<String> labelList = new ArrayList<String>();

    public DragSelectRecyclerAdapter2(ClickListener callback){
        super();
        clickListener = callback;
    }
    public void load(){
        labelList = new ArrayList<String>();
        for(int i = 0; i< Constants.TimesHalfHour.length; i++)
            labelList.add(Constants.TimesHalfHour[i]);
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
