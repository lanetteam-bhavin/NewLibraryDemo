package lanet.com.newlibrarydemos.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import lanet.com.newlibrarydemos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2)
    {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SecondFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        WebView wv1 = (WebView) rootView.findViewById(R.id.wv1);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setAllowFileAccess(true);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 16)
        {
            wv1.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
        wv1.setWebChromeClient(new WebChromeClient()
        {
            @SuppressWarnings("unused")
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon)
            {
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 90)
                {
//                    pbarSearch.setVisibility(view.GONE);
                }
            }
        });

        wv1.loadDataWithBaseURL("",
                "<Html><head><style type=\"text/css\">table td.iframe iframe{width:100% !important; height:100% !important; }</style></head><body><table width=\"100%\"height=\"100%\"><tr><td class=\"iframe\" style=\"height:100%\"><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ZfkB4vltQTU?autoplay=1&controls=0&frameborder=0\" allowfullscreen></iframe></td></tr></table></body></html>",
                "text/html", "utf-8", "");
        return rootView;
    }


}
