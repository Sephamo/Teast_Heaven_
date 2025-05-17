package com.example.thegardenofeatn;

import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.Button;

public class PartyButtonManager {
    private final Button[] buttons;
    private final int defaultColor;
    private final int clickedColor;

    public PartyButtonManager(Context context, Button... buttons) {
        this.buttons = buttons;
        this.defaultColor = context.getResources().getColor(R.color.whiteColor);
        this.clickedColor = context.getResources().getColor(R.color.clickedColor);
    }

    public void setButtonClickHandlers(final OnPartySelectedListener listener) {
        for (int i = 0; i < buttons.length; i++) {
            final int index = i + 1;
            buttons[i].setOnClickListener(v -> {
                resetColors();
                buttons[index - 1].setBackgroundTintList(ColorStateList.valueOf(clickedColor));
                listener.onPartySelected(index);
            });
        }
    }

    private void resetColors() {
        for (Button button : buttons) {
            button.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
        }
    }

    public interface OnPartySelectedListener {
        void onPartySelected(int partySize);
    }
}

