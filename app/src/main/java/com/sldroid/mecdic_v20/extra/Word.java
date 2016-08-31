package com.sldroid.mecdic_v20.extra;

/**
 * Created by Dilanka on 2016-08-22.
 */
public class Word {

    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    private String word, definition;
}
