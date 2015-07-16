package widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.zohaltech.app.mobiledatamonitor.classes.App;


public class MyCheckBox extends CheckBox
{
    public MyCheckBox(Context context)
    {
        super(context);
        initialize();
    }

    public MyCheckBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();
    }

    public MyCheckBox(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initialize();
    }

    public void initialize()
    {
        if (isInEditMode())
        {
            return;
        }
        setFont();
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        super.setText(text, type);
        setFont();
    }

    private void setFont()
    {
        setTypeface(App.persianFont);
    }
}
