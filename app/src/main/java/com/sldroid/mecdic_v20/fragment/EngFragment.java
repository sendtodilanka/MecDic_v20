package com.sldroid.mecdic_v20.fragment;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sldroid.mecdic_v20.R;
import com.sldroid.mecdic_v20.dbms.TestAdapter;
import com.sldroid.mecdic_v20.extra.Word;
import com.sldroid.mecdic_v20.extra.WordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EngFragment extends Fragment {

    private static View view;
    private static RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private WordAdapter wordAdapter;
    private TestAdapter dbHelper;
    private Typeface siUnicode;
    private SharedPreferences sPrefer;
    private static SharedPreferences.Editor editor;
    private ArrayList<Word> words;

    public EngFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new TestAdapter(getActivity().getApplicationContext());
        dbHelper.createDatabase();
        dbHelper.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_eng, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        words = dbHelper.getAllWord("enDic");
        wordAdapter = new WordAdapter(words, getContext());
        recyclerView.setAdapter(wordAdapter);
        wordAdapter.notifyDataSetChanged();
        return view;
    }

    public void textSearch(String inputTxt){
        ArrayList<Word> wordList = filter(words,inputTxt);
        wordAdapter = new WordAdapter(wordList, getContext());
        recyclerView.setAdapter(wordAdapter);
        wordAdapter.notifyDataSetChanged();
    }

    public ArrayList<Word> filter(List<Word> wordList, String query) {
        query = query.toLowerCase();

        final ArrayList<Word> filteredWordList = new ArrayList<>();
        for (Word word : wordList) {
            final String text = word.getWord().toLowerCase();
            if (text.contains(query)) {
                filteredWordList.add(word);
            }
        }
        return filteredWordList;
    }
}
