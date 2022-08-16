package com.example.sosialrec;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NotesSource {


    private List<NoteData> dataSource;
   // private Resources resources;

    public NoteSourceImpl(/*Resources resources*/) {
       // this.resources = resources;
        dataSource = new ArrayList<>(5);
    }

    /*public NoteSourceImpl init() {

        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        int[] pictures = getImageArray();

        for (int i = 0; i < 5; i++) {
            dataSource.add(new NoteData(titles[i], descriptions[i], pictures[i], false));
        }

        return this;

    }*/

    /*private int[] getImageArray() {
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for (int i = 0; i < length; i++) {
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }*/

    @Override
    public NoteData getNoteData(int position) {
        return dataSource.get(position);
    }


    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteNoteData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateNoteData(int position, NoteData noteData) {
        dataSource.set(position, noteData);
    }

    @Override
    public void addNoteData(NoteData noteData) {
        dataSource.add(noteData);
    }

    @Override
    public void clearNoteData() {
        dataSource.clear();
    }

    @Override
    public void setNewData(List<NoteData> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<NoteData> getNoteData() {
        return dataSource;
    }
}
