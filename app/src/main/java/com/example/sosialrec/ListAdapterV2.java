package com.example.sosialrec;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapterV2 extends RecyclerView.Adapter<ListAdapterV2.ViewHolder> {

    NotesSource dataSource;
    private OnItemClickListener itemClickListener;
    private Fragment fragment;
    private int menuPosition;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ListAdapterV2(NotesSource dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    private void registerContextMenu(View itemView) {
        if (fragment != null) {
            fragment.registerForContextMenu(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterV2.ViewHolder holder, int position) {
        //dataSource[position]
        holder.setData(dataSource.getNoteData(position));
    }

    @NonNull
    @Override
    public ListAdapterV2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_v2, viewGroup, false);
        return new ListAdapterV2.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private AppCompatImageView image;
        private CheckBox like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
            like = itemView.findViewById(R.id.like);

            registerContextMenu(itemView);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, position);
                }
            });

            image.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu(15, 15);
                    return false;
                }
            });
        }

        public void setData(NoteData noteData) {
            title.setText(noteData.getTitle());
            description.setText(noteData.getDescription());
            like.setChecked(noteData.isLike());
            image.setImageResource(noteData.getPicture());
        }

    }
}
