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
    private Paint arcForegroundPaint;
    private Paint arcBackgroundPaint;
    private Paint textPaint;
    private int progress = 0;
    private String traffic = "";

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

    public void setProgress(int progress, String traffic) {
        this.progress = progress;
        this.traffic = traffic;
        postInvalidate();
    }

    private void initialize() {
        int color = Color.argb(230, 255, 255, 255);

        arcBackgroundPaint = new Paint();
        arcBackgroundPaint.setColor(context.getResources().getColor(R.color.primary_dark));
        arcBackgroundPaint.setAntiAlias(true);
        arcBackgroundPaint.setStyle(Style.STROKE);

        arcForegroundPaint = new Paint();
        arcForegroundPaint.setColor(color);
        arcForegroundPaint.setAntiAlias(true);
        arcForegroundPaint.setStyle(Style.STROKE);
        arcForegroundPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(color);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTypeface(App.englishFont);
        textPaint.setStyle(Style.FILL_AND_STROKE);
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
        arcBackgroundPaint.setStrokeWidth(strokeWidth);

        canvas.drawArc(rect, START_ANGLE, SWEEP_ANGLE, false, arcBackgroundPaint);


        int sweepAngle = progress * SWEEP_ANGLE / 100;
        canvas.drawArc(rect, START_ANGLE, sweepAngle, false, arcForegroundPaint);

        textPaint.setTextSize(textSize);
        textPaint.setShadowLayer(strokeWidth / 2, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        canvas.drawText(progress + "%", baseSize / 2, getHeight() / 2, textPaint);

        textPaint.setTextSize(textSize / 3);
        textPaint.setShadowLayer(strokeWidth / 3, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        canvas.drawText(traffic, baseSize / 2, getHeight() - (getHeight() / 6), textPaint);
    }
}
