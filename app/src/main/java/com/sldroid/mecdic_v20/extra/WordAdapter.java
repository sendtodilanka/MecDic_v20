package com.sldroid.mecdic_v20.extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sldroid.mecdic_v20.R;

import java.util.ArrayList;

/**
 * Created by Dilanka on 2016-08-22.
 */
public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>{

    private ArrayList<Word> mDataSet;
    private static SharedPreferences sPrefer;
    private static SharedPreferences.Editor editor;
    private Context context;
    private String word;

    public WordAdapter(ArrayList<Word> mDataSet, Context mContext) {
        this.mDataSet = mDataSet;
        this.context = mContext;

        sPrefer = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sPrefer.edit();
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_row, parent, false);
        WordViewHolder wordViewHolder = new WordViewHolder(v);
        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, final int position) {
        holder.txtId.setText(String.valueOf(mDataSet.get(position).get_id()));
        holder.txtWord.setText(mDataSet.get(position).getWord());

        int charLimit = 40;
        if (mDataSet.get(position).getDefinition().length() > charLimit)
            holder.txtDef.setText(mDataSet.get(position).getDefinition().substring(0,charLimit)+"...");
        else
            holder.txtDef.setText(mDataSet.get(position).getDefinition());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtId, txtWord, txtDef;
        ImageView imgFav;

        public WordViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtId = (TextView) itemView.findViewById(R.id.txt_id);
            txtWord = (TextView) itemView.findViewById(R.id.txt_word);
            txtDef = (TextView) itemView.findViewById(R.id.txt_def);
            imgFav = (ImageView) itemView.findViewById(R.id.img_fav);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
