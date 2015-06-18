package lanet.com.newlibrarydemos.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lanet.com.newlibrarydemos.R;
import lanet.com.newlibrarydemos.models.Persons;

/**
 * Created by lcom15 on 18/6/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ListItemHolder>
{
    List<Persons> personses;
    private SparseBooleanArray selectedItems;

    public MyAdapter(ArrayList<Persons> personses)
    {
        if (personses == null)
        {
            throw new IllegalArgumentException("data should not be null");
        }
        this.personses = personses;
        selectedItems = new SparseBooleanArray();
        Log.d("MyAdapter", "we are in constructor");
    }

    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        Log.d("MyAdapter", "we are in itemView");
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemHolder listItemHolder, int i)
    {
        Persons persons = personses.get(i);
        listItemHolder.firstName.setText(persons.name);
        listItemHolder.Address.setText(persons.address);
        listItemHolder.itemView.setActivated(selectedItems.get(i, false));
        Log.d("MyAdapter", "we are in bind view");
    }

    @Override
    public int getItemCount()
    {
        return personses.size();
    }

    public void addData(Persons newModelData, int position)
    {
        personses.add(position, newModelData);
        notifyItemInserted(position);
    }

    public void removeData(int position)
    {
        personses.remove(position);
        notifyItemRemoved(position);
    }

    public void toggleSelection(int pos)
    {
        if (selectedItems.get(pos, false))
        {
            selectedItems.delete(pos);
        }
        else
        {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections()
    {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount()
    {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems()
    {
        List<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++)
        {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public class ListItemHolder extends RecyclerView.ViewHolder
    {

        public TextView firstName, Address;

        public ListItemHolder(View itemView)
        {
            super(itemView);
            firstName = (TextView) itemView.findViewById(R.id.tv_name);
            Address = (TextView) itemView.findViewById(R.id.tv_addr);
            Log.d("MyAdapter", "we are in holder");
        }
    }
}
