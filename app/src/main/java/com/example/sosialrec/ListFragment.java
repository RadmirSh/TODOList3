package com.example.sosialrec;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ListFragment extends Fragment {


    public static ListFragment newInstance(){
        return new ListFragment();
    }
    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_lines);
        String[] data = getResources().getStringArray(R.array.titles);
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, String[] data) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ListAdapter listAdapter = new ListAdapter(data);
        recyclerView.setAdapter(listAdapter);

        listAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("%s - %d",
                        ((TextView)view).getText(), position), Toast.LENGTH_SHORT).show();
            }
        });

    }


}