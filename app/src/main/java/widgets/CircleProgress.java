package widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;


public class CircleProgress extends ImageView {

    private static final int START_ANGLE = 90;
    private static final int SWEEP_ANGLE = 360;
    Context context;
    private Paint arcBackgroundPaint;
    private Paint arcForegroundPaint;
    private Paint textPaintValue;
    private Paint textPaintCaption;
    private String value   = "";
    private String caption = "";

    public CircleProgress(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initialize();
    }

    public void setProgress(String value, String caption) {
        this.value = value;
        this.caption = caption;
        postInvalidate();
    }

    private void initialize() {

        int color = Color.argb(230, 255, 255, 255);

        arcForegroundPaint = new Paint();
        arcForegroundPaint.setColor(color);
        arcForegroundPaint.setAntiAlias(true);
        arcForegroundPaint.setStyle(Style.STROKE);

        textPaintValue = new Paint();
        textPaintValue.setColor(color);
        textPaintValue.setAntiAlias(true);
        textPaintValue.setTextAlign(Align.CENTER);
        textPaintValue.setTypeface(App.englishFont);
        textPaintValue.setStyle(Style.FILL_AND_STROKE);

        textPaintCaption = new Paint();
        textPaintCaption.setColor(color);
        textPaintCaption.setAntiAlias(true);
        textPaintCaption.setTextAlign(Align.CENTER);
        textPaintCaption.setTypeface(App.persianFont);
        textPaintCaption.setStyle(Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int baseSize = getWidth() < getHeight() ? getWidth() : getHeight();

        int textSize = baseSize / 4;
        int strokeWidth = baseSize / 25;

        RectF rect = new RectF();
        rect.left = strokeWidth;
        rect.right = baseSize - strokeWidth;
        rect.top = strokeWidth;
        rect.bottom = baseSize - strokeWidth;

        arcForegroundPaint.setStrokeWidth(strokeWidth);

        canvas.drawArc(rect, START_ANGLE, SWEEP_ANGLE, false, arcForegroundPaint);

        textPaintValue.setTextSize(textSize);
        textPaintValue.setShadowLayer(strokeWidth / 2, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        canvas.drawText(value, baseSize / 2, getHeight() / 2, textPaintValue);

        textPaintCaption.setTextSize(textSize / 3);
        textPaintCaption.setShadowLayer(strokeWidth / 3, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        canvas.drawText(caption, baseSize / 2, getHeight() - (getHeight() / 6), textPaintCaption);
    }
}
