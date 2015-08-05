package widgets;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.classes.App;


public class MySwitch extends SwitchCompat {
    public MySwitch(Context context) {
        super(context);
        initialize();
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public void initialize() {
        if (isInEditMode()) {
            return;
        }
        setFont();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        setFont();
    }

    private void setFont() {
        setTypeface(App.persianFont);
    }
}
