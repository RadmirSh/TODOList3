package com.example.sosialrec;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListFragmentV2 extends Fragment {

    private NotesSource data;
    private ListAdapterV2 adapter;
    private RecyclerView recyclerView;
    private static final int DURATION = 1000;
    private static final String KEY = "KEY";
    private SharedPreferences sharedPreferences;

    public static ListFragmentV2 newInstance() {
        return new ListFragmentV2();
    }

    public ListFragmentV2() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.action_add:
                //добавление нового элемента
                data.addNoteData(new NoteData("Заметка", "Описание", R.drawable.first, false));
                Toast.makeText(getContext(), "Добавлена новая заметка", Toast.LENGTH_LONG).show();
                // нотификация добавления нового элемента
                adapter.notifyItemInserted(data.size() - 1);
                // прокручиваем список на элемент по индексу
                recyclerView.scrollToPosition(data.size() - 1);

                recyclerView.smoothScrollToPosition(data.size() - 1);

                String jsonNoteDataAfterAdd = new GsonBuilder().create().toJson(data.getNoteData());
                sharedPreferences.edit().putString(KEY, jsonNoteDataAfterAdd).apply();
                return true;
            case R.id.action_clear:
                // чистка элементов
                data.clearNoteData();
                // нотификация изменения элементов
                //adapter.notifyDataSetChanged()
                new AlertDialog.Builder(getContext())
                        .setTitle("Внимание!").setMessage("Вы действительно желаете очистить всю историю?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "История очищена", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Нет", null)
                        .show();
                String jsonNoteDataAfterClear = new GsonBuilder().create().toJson(data.getNoteData());
                sharedPreferences.edit().putString(KEY, jsonNoteDataAfterClear).apply();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        //RecyclerView recyclerView = view.findViewById(R.id.recycle_view_lines);

        //CardsSource data = new CardSourceImpl(getResources()).init();
        //initRecyclerView(recyclerView, data);

        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view_lines);
        //data = new NoteSourceImpl(getResources()).init();
        data = new NoteSourceImpl();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //ListAdapterV2 listAdapter = new ListAdapterV2(data);
        adapter = new ListAdapterV2(data, this);

        recyclerView.setAdapter(adapter);


        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,
                null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(DURATION);
        defaultItemAnimator.setRemoveDuration(DURATION);
        recyclerView.setItemAnimator(defaultItemAnimator);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String saveData = sharedPreferences.getString(KEY, null);
        if (saveData == null) {
            Toast.makeText(getContext(), "Пусто", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Type type = new TypeToken<List<NoteData>>() {
                }.getType();
                adapter.setNewData(new GsonBuilder().create().fromJson(saveData, type));
            } catch (Exception e) {
                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                ;
            }
        }

        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getContext(), String.format("%s - %d",
                //        ((TextView) view).getText(), position), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), String.format("Позиция - %d",
                        position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                NoteData noteData = data.getNoteData(position);

                data.updateNoteData(position, new NoteData("Элемент " + position,
                        noteData.getDescription(),
                        noteData.getPicture(),
                        noteData.isLike()));
                adapter.notifyItemChanged(position);

                String jsonNoteDataAfterUpdate = new GsonBuilder().create().toJson(data.getNoteData());
                sharedPreferences.edit().putString(KEY, jsonNoteDataAfterUpdate).apply();

                return true;
            case R.id.action_delete:
                data.deleteNoteData(position);
                //adapter.notifyItemRemoved(position);
                new AlertDialog.Builder(getContext())
                        .setTitle("Внимание!").setMessage("Вы действительно желаете удалить заметку?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.notifyItemRemoved(position);
                                Toast.makeText(getContext(), "Заметка удалена", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Нет", null)
                        .show();
                String jsonNoteDataAfterDelete = new GsonBuilder().create().toJson(data.getNoteData());
                sharedPreferences.edit().putString(KEY, jsonNoteDataAfterDelete).apply();
                //Toast.makeText(getContext(), "Заметка удалена", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}