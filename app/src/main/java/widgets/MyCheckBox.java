package widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatCheckBox;

import com.zohaltech.app.mobiledatamonitor.classes.App;


public class MyCheckBox extends AppCompatCheckBox
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
