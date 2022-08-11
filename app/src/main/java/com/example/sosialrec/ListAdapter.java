package com.example.sosialrec;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private String[] dataSource;

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ListAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        //dataSource[position]
        holder.getTextView().setText(dataSource[position]);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = (TextView) itemView;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, position);
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
