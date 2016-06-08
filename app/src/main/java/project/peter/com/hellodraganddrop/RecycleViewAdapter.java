package project.peter.com.hellodraganddrop;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by linweijie on 6/8/16.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter {

    /**
     * Listener for manual initiation of a drag.
     */
    public interface OnStartDragListener {

        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    private ArrayList<ItemData> itemsData = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    private int layoutId;

    public RecycleViewAdapter(ArrayList<ItemData> itemsData, int layoutId, OnStartDragListener listener) {
        this.itemsData = itemsData;
        this.layoutId = layoutId;
        this.mDragStartListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);

        // create ViewHolder
        MyViewHolder viewHolder = new MyViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ((MyViewHolder)holder).txtViewTitle.setText(itemsData.get(position).getTitle());
        ((MyViewHolder)holder).imgViewIcon.setImageResource(itemsData.get(position).getimageId());
        ((MyViewHolder)holder).handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;
        public ImageView handleView;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
            handleView = (ImageView) itemLayoutView.findViewById(R.id.item_menu);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}