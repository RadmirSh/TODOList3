package com.example.sosialrec;

import java.util.List;

public interface NotesSource {

    NoteData getNoteData(int position);
    int size();

    void deleteNoteData(int position);
    void updateNoteData(int position, NoteData noteData);
    void addNoteData(NoteData noteData);
    void clearNoteData();
    void setNewData(List<NoteData> dataSource);
    List<NoteData> getNoteData();
}
