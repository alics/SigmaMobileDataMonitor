package widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatCheckBox;

import com.zohaltech.app.sigma.classes.App;


public class MyCheckBox extends AppCompatCheckBox
{
    public MyCheckBox(Context context)
    {
        super(context);
    }

    public MyCheckBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyCheckBox(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(App.persianFont);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        if (style == Typeface.BOLD) {
            super.setTypeface(App.persianFontBold);
        } else {
            super.setTypeface(App.persianFont);
        }
    }
}
