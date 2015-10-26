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

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;


public class ArcProgress extends ImageView {

    private static final int START_ANGLE = 150;
    private static final int SWEEP_ANGLE = 240;
    Context context;

    RectF rect;

    private Paint arcForegroundPaint;
    private Paint arcBackgroundPaint;
    private Paint textPaint;
    private int    progress = 0;
    private String traffic  = "";

    public ArcProgress(Context context) {
        super(context);
        this.context = context;
        initialize();
    }


    public ArcProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }


    public ArcProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initialize();
    }

    public void setProgress(int progress, String traffic) {
        this.progress = progress;
        //this.progress = 85;
        this.traffic = traffic;
        //this.traffic = "2.5GB/3GB";
        postInvalidate();
    }

    private void initialize() {

        rect = new RectF();

        int color = Color.argb(230, 255, 255, 255);

        arcBackgroundPaint = new Paint();
        arcBackgroundPaint.setColor(context.getResources().getColor(R.color.primary_dark));
        arcBackgroundPaint.setAntiAlias(true);
        arcBackgroundPaint.setStyle(Style.STROKE);
        arcBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        arcForegroundPaint = new Paint();
        arcForegroundPaint.setColor(color);
        arcForegroundPaint.setAntiAlias(true);
        arcForegroundPaint.setStyle(Style.STROKE);
        arcForegroundPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(color);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setStyle(Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int baseSize = getWidth() < getHeight() ? getWidth() : getHeight();

        int textSize = baseSize / 3;
        float strokeWidth = (float)baseSize / 25;

        rect.set(strokeWidth, strokeWidth, baseSize - strokeWidth, baseSize - strokeWidth);

        arcForegroundPaint.setStrokeWidth(strokeWidth);
        arcBackgroundPaint.setStrokeWidth(strokeWidth);

        canvas.drawArc(rect, START_ANGLE, SWEEP_ANGLE, false, arcBackgroundPaint);


        int sweepAngle = progress * SWEEP_ANGLE / 100;
        canvas.drawArc(rect, START_ANGLE, sweepAngle, false, arcForegroundPaint);

        textPaint.setTypeface(App.englishFontBold);
        textPaint.setTextSize(textSize - (textSize/4));
        textPaint.setShadowLayer(strokeWidth / 2, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        canvas.drawText(progress + "%", baseSize / 2, getHeight() / 2, textPaint);

        textPaint.setTypeface(App.englishFont);
        textPaint.setTextSize(textSize / 3);
        textPaint.setShadowLayer(strokeWidth / 3, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        canvas.drawText(traffic, baseSize / 2, getHeight() - (getHeight() / 7), textPaint);
    }
}
