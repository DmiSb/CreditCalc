package com.dmisb.creditcalc.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * FontUtil
 */

public class FontUtil {

    private static Typeface sCondensedFont, sMediumFont, sRegilarFont;

    /**
     * Initializing custom fonts
     *
     * @param context
     */
    public static void initFont(Context context) {

        sCondensedFont = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + ConstantManager.FONT_CONDENSED + ".ttf");
        sMediumFont = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + ConstantManager.FONT_MEDIUM + ".ttf");
        sRegilarFont = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + ConstantManager.FONT_REGULAR + ".ttf");
    }

    /**
     * Font Binding
     * by example:  app:font="@{`Roboto_Condensed`}"
     *
     * @param textView - view
     * @param fontName - short name font
     *
     */
    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        Typeface typeface = getFont(fontName);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }

    @BindingAdapter({"bind:font"})
    public static void setFont(RadioButton radioButton, String fontName) {
        Typeface typeface = getFont(fontName);
        if (typeface != null) {
            radioButton.setTypeface(typeface);
        }
    }

    @BindingAdapter({"bind:font"})
    public static void setFont(CheckBox checkBox, String fontName) {
        Typeface typeface = getFont(fontName);
        if (typeface != null) {
            checkBox.setTypeface(typeface);
        }
    }

    /**
     * Returns custom font
     *
     * @param fontName - name font
     * @return - font
     */
    private static Typeface getFont(String fontName) {
        switch (fontName) {
            case ConstantManager.FONT_CONDENSED:
                return sCondensedFont;
            case ConstantManager.FONT_MEDIUM:
                return sMediumFont;
            case ConstantManager.FONT_REGULAR:
                return sRegilarFont;
            default:
                return null;
        }
    }
}
