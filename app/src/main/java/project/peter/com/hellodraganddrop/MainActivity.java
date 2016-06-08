package project.peter.com.hellodraganddrop;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    /**
     * LinearDrop
     * */
    private LinearLayout ll_list1, ll_main;
    private GestureDetector mGestureDetector, mGestureDetector2;
    private View mDrapView, mDrapView2;

    /**
     * RecycleView
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLinearDrop();
        initRecycleView();
    }

    /**
     * RecycleView
     * */
    private ItemTouchHelper mItemTouchHelper;

    private void initRecycleView(){

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // this is data fro recycler view
        final ArrayList<ItemData> itemData = new ArrayList<>();
        itemData.add(new ItemData("ONE", R.drawable.head_image1));
        itemData.add(new ItemData("TWO", R.drawable.head_image2));
        itemData.add(new ItemData("THREE", R.drawable.head_image3));
        itemData.add(new ItemData("FOUR", R.drawable.head_image4));
        itemData.add(new ItemData("FIVE", R.drawable.head_image5));

        // 2. set layoutManger
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. create an adapter
        final RecycleViewAdapter mAdapter = new RecycleViewAdapter(itemData, R.layout.item_card_layout, new RecycleViewAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 6. set ItemTouchHelper to control item move and dismiss
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(new SimpleItemTouchHelperCallback.ItemTouchHelperListener() {
            @Override
            public void onItemMove(int fromPosition, int toPosition) {
                Log.d("RecycleView", "onItemMove \n fromPosition : " + fromPosition + "\n toPosition : " + toPosition);
                Collections.swap(itemData, fromPosition, toPosition);
                mAdapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemDismiss(int position) {
                Log.d("RecycleView", "onItemDismiss \n position : " + position);
                itemData.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * LinearDrop
     * */
    private void initLinearDrop(){

        ll_list1 = (LinearLayout) findViewById(R.id.ll_list1);
        bindDrapListener(R.id.iv_image1);
        bindDrapListener(R.id.iv_image2);
        bindDrapListener(R.id.iv_image3);
        bindDrapListener(R.id.iv_image4);
        bindDrapListener(R.id.iv_image5);

        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        bindDrapListener2(R.id.cardview1);
        bindDrapListener2(R.id.cardview2);

        mGestureDetector = new GestureDetector(this, onGestureListener);
        mGestureDetector2 = new GestureDetector(this, onGestureListener2);
    }

    private void bindDrapListener(int id) {
        View v = findViewById(id);
        v.setOnTouchListener(mOnTouchListener);
        v.setOnDragListener(mOnDragListener);
    }
    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mDrapView = v;

            if (mGestureDetector.onTouchEvent(event))
                return true;

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:

                    break;
            }

            return false;
        }
    };
    private View.OnDragListener mOnDragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setAlpha(0.5F);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setAlpha(1F);
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    for (int i = 0, j = ll_list1.getChildCount(); i < j; i++) {
                        if (ll_list1.getChildAt(i) == v) {
                            // 当前位置
                            ll_list1.removeView(view);
                            ll_list1.addView(view, i);
                            break;
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setAlpha(1F);
                default:
                    break;
            }
            return true;
        }
    };
    private GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            ClipData data = ClipData.newPlainText("", "");
            MyDragShadowBuilder shadowBuilder = new MyDragShadowBuilder(
                    mDrapView);
            mDrapView.startDrag(data, shadowBuilder, mDrapView, 0);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    };





    private void bindDrapListener2(int id) {
        View v = findViewById(id);
        v.setOnTouchListener(mOnTouchListener2);
        v.setOnDragListener(mOnDragListener2);
    }
    private View.OnTouchListener mOnTouchListener2 = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mDrapView2 = v;

            if (mGestureDetector2.onTouchEvent(event))
                return true;

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:

                    break;
            }

            return false;
        }
    };
    private View.OnDragListener mOnDragListener2 = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setAlpha(1F);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setAlpha(1F);
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    for (int i = 0, j = ll_main.getChildCount(); i < j; i++) {
                        if (ll_main.getChildAt(i) == v) {
                            // 当前位置
                            ll_main.removeView(view);
                            ll_main.addView(view, i);
                            break;
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setAlpha(1F);
                default:
                    break;
            }
            return true;
        }
    };
    private GestureDetector.SimpleOnGestureListener onGestureListener2 = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            ClipData data = ClipData.newPlainText("", "");
            MyDragShadowBuilder shadowBuilder = new MyDragShadowBuilder(
                    mDrapView2);
            mDrapView2.startDrag(data, shadowBuilder, mDrapView2, 0);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    };
}
