package fr.openclassrooms.mareu.utils.ui;

import android.graphics.drawable.Drawable;
import android.text.Editable;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SimpleTextWatcherFactory {

    private static final int MIN_TEXT_LEN = 0;

    public SimpleTextWatcher getDefault(
        TextInputLayout textInput,
        Drawable drawableStartNotEmpty,
        Drawable drawableEndNotEmpty,
        Drawable drawableStartEmpty,
        Drawable drawableEndEmpty
    ){

        // Use our internal interface instead of TextWatcher, to not be too much verbose
        return new SimpleTextWatcher() {

            // When the text changed on the input text ..
            @Override
            public void afterTextChanged(Editable s) {
                // .. we check if the text is empty or not
                int len = Objects.requireNonNull(textInput.getEditText()).getText().length();

                // if the text is not empty
                if (len > MIN_TEXT_LEN) {
                    // change the icon to the one we want
                    textInput
                        .getEditText()
                        .setCompoundDrawablesRelativeWithIntrinsicBounds(
                                drawableStartNotEmpty, null, drawableEndNotEmpty, null);
                } else {
                    // if the text is empty
                    textInput
                        .getEditText()
                        // change the icon to the one we want
                        .setCompoundDrawablesRelativeWithIntrinsicBounds(
                                drawableStartEmpty, null, drawableEndEmpty, null);
                }
            }
        };

    }

}

