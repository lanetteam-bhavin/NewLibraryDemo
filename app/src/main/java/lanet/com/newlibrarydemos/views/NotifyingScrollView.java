package lanet.com.newlibrarydemos.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by lcom15 on 3/6/15.
 */
public class NotifyingScrollView extends ScrollView
{
    private OnScrollChangedListener listener;
    private boolean overScrollEnabled = true;

    public NotifyingScrollView(Context context)
    {
        super(context);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public OnScrollChangedListener getListener()
    {
        return listener;
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener)
    {
        this.listener = listener != null ? listener : null;
    }

    public void setOverScrollEnabled(boolean enabled)
    {
        overScrollEnabled = enabled;
    }

    public boolean isOverScrollEnabled()
    {
        return overScrollEnabled;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
    {
        return super.overScrollBy(
                deltaX,
                deltaY,
                scrollX,
                scrollY,
                scrollRangeX,
                scrollRangeY,
                overScrollEnabled ? maxOverScrollX : 0,
                overScrollEnabled ? maxOverScrollY : 0,
                isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null)
        {
            listener.onScrollChanged(this,l, t, oldl, oldt);
        }
    }

    public interface OnScrollChangedListener
    {
        void onScrollChanged(ScrollView who,int l, int t, int oldl, int oldt);
    }

}
