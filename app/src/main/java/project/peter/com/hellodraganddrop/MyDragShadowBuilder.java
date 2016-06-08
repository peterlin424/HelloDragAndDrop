package project.peter.com.hellodraganddrop;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by linweijie on 6/8/16.
 */
public class MyDragShadowBuilder extends View.DragShadowBuilder {

    private final WeakReference<View> mView;

    public MyDragShadowBuilder(View view) {
        super(view);
        mView = new WeakReference<View>(view);
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        canvas.scale(1.5F, 1.5F);
        super.onDrawShadow(canvas);
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize,
                                       Point shadowTouchPoint) {
        // super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);

        final View view = mView.get();
        if (view != null) {
            shadowSize.set((int) (view.getWidth() * 1.5F),
                    (int) (view.getHeight() * 1.5F));
            shadowTouchPoint.set(shadowSize.x / 2, shadowSize.y / 2);
        } else {
            // Log.e(View.VIEW_LOG_TAG,
            // "Asked for drag thumb metrics but no view");
        }
    }
}