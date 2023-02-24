package fr.openclassrooms.mareu.utils.ui;

import android.text.TextWatcher;

/**
 * Simplifying TextWatcher by discarding never used beforeTextChanged and onTextChanged methods
 */
public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}