package lanet.com.newlibrarydemos.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lanet.com.newlibrarydemos.R;
import lanet.com.newlibrarydemos.adapter.MyAdapter;
import lanet.com.newlibrarydemos.models.Persons;
import lanet.com.newlibrarydemos.views.DividerItemDecoration;

import static android.view.GestureDetector.SimpleOnGestureListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment implements RecyclerView.OnItemTouchListener, View.OnClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = SecondFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    GestureDetectorCompat gestureDetector;
    android.view.ActionMode actionMode;

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
        Log.d(TAG, "We are in Second Fragment on Create");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "We are in Second Fragment on Activity Create");
    }

    RecyclerView recyclerView;
    ArrayList<Persons> datas = new ArrayList<>();
    MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        datas = prepareData();
        Log.v(TAG, "Data's length:" + datas.size());
        adapter = new MyAdapter(datas);
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(getActivity(), new RecyclerViewDemoOnGestureListener());
        Log.d(TAG, "can we scroll ? " + recyclerView.getLayoutManager().canScrollVertically());
        return rootView;
    }

    public ArrayList<Persons> prepareData()
    {
        ArrayList<Persons> personList = new ArrayList<>();

        for (int i = 0; i < 15; i++)
        {
            Persons persons = new Persons();
            persons.name = "Name" + i;
            persons.address = "Address" + i;
            personList.add(persons);
        }
        return personList;
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent)
    {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent)
    {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b)
    {

    }

    @Override
    public void onClick(View v)
    {

    }

    android.view.ActionMode.Callback callBack = new android.view.ActionMode.Callback()
    {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_cab_recyclerviewdemoactivity, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.menu_delete:
                    List<Integer> selectedItemPositions = adapter.getSelectedItems();
                    int currPos;
                    for (int i = selectedItemPositions.size() - 1; i >= 0; i--)
                    {
                        currPos = selectedItemPositions.get(i);
//                RecyclerViewDemoApp.removeItemFromList(currPos);
                        adapter.removeData(currPos);
                    }
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {
            mode = null;
            actionMode = null;
            adapter.clearSelections();
        }
    };

    private class RecyclerViewDemoOnGestureListener extends SimpleOnGestureListener
    {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view, recyclerView.getChildLayoutPosition(view));
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e)
        {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null)
            {
                return;
            }
            // Start the CAB using the ActionMode.Callback defined above
            actionMode = getActivity().startActionMode(callBack);
            int idx = recyclerView.getChildAdapterPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }

    private void onClick(View view, int childLayoutPosition)
    {
        Log.d(TAG, "We click on :" + childLayoutPosition);
        int idx = recyclerView.getChildAdapterPosition(view);
        if (actionMode != null) {
            myToggleSelection(idx);
            return;
        }
    }

    private void myToggleSelection(int idx)
    {
        adapter.toggleSelection(idx);
        String title = getString(R.string.selected_count, adapter.getSelectedItemCount());
        actionMode.setTitle(title);
    }
}