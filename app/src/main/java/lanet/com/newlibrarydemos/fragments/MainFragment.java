package lanet.com.newlibrarydemos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;

import lanet.com.newlibrarydemos.R;
import lanet.com.newlibrarydemos.views.NotifyingScrollView;

/*
Want to commit it again
 */
public class MainFragment extends Fragment
{

    private Drawable mActionBarBackgroundDrawable;
    public AppCompatActivity mActivity;
    View rootView;
    NotifyingScrollView scrollView;
    ImageView imageView;

    public static MainFragment newInstance()
    {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public MainFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.activity_scrollview, container, false);
        mActionBarBackgroundDrawable = getResources().getDrawable(R.drawable.ab_background);
        mActionBarBackgroundDrawable.setAlpha(0);
        mActivity.getSupportActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            mActionBarBackgroundDrawable.setCallback(mDrawableCallBack);
        }
        scrollView = (NotifyingScrollView) rootView.findViewById(R.id.scroll_view);
        scrollView.setOnScrollChangedListener(mOnSrollChangedListner);
        imageView = (ImageView) rootView.findViewById(R.id.image_header);
//        PorterDuffColorFilter colorFilter =new PorterDuffColorFilter(getResources().getColor(R.color.blacked),PorterDuff.Mode.MULTIPLY);
//        imageView.getDrawable().setColorFilter(colorFilter);
        return rootView;
    }

    private NotifyingScrollView.OnScrollChangedListener mOnSrollChangedListner = new NotifyingScrollView.OnScrollChangedListener()
    {
        @Override
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt)
        {
            final int headerHeight = rootView.findViewById(R.id.image_header).getHeight() - mActivity.getSupportActionBar().getHeight();
            final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
            mActionBarBackgroundDrawable.setAlpha(newAlpha);
        }
    };

    private Drawable.Callback mDrawableCallBack = new Drawable.Callback()
    {
        @Override
        public void invalidateDrawable(Drawable who)
        {
            mActivity.getSupportActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when)
        {

        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what)
        {

        }
    };

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
//        mArguments = getArguments();
//        int actionBarBg = mArguments != null ? mArguments.getInt(ARG_ACTION_BG_RES) : R.drawable.ab_background_light;
//
//        mFadingHelper = new FadingActionBarHelper()
//                .actionBarBackground(actionBarBg)
//                .headerLayout(R.layout.header_light)
//                .contentLayout(R.layout.activity_scrollview)
//                .lightActionBar(actionBarBg == R.drawable.ab_background_light);
//        mFadingHelper.initActionBar(activity);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

}
