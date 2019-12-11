package com.traap.traapapp.utilities;

import android.text.Editable;
import android.text.TextWatcher;

import ru.kolotnev.formattedittext.MaskedEditText;

/**
 * Created by JavadAbadi on 7/25/2018.
 */
public class TextWatcherAdapter implements TextWatcher {

    public interface TextWatcherListener {

        void onTextChanged(MaskedEditText view, String text);

    }

    private final MaskedEditText view;
    private final TextWatcherListener listener;

    public TextWatcherAdapter(MaskedEditText editText, TextWatcherListener listener) {
        this.view = editText;
        this.listener = listener;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        listener.onTextChanged(view, s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // pass
    }

    @Override
    public void afterTextChanged(Editable s) {
        // pass
    }

}