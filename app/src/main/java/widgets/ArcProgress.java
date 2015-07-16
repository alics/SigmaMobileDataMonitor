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


public class ArcProgress extends ImageView {

    private static final int START_ANGLE = 140;
    private static final int SWEEP_ANGLE = 260;
    Context context;
    private Paint arcForegroundPaint;
    private Paint arcBackgroundPaint;
    private Paint textPaint;
    private int    percent = 0;
    private String traffic = "";
    //private int strokeWidth = 22;
    //private int numberTextSize = 120;
    //private int percentTextSize = 60;

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

    //public void setProperties(int strokeWidth, int numberTextSize) {
    //    this.strokeWidth = getWidth()/5;
    //    this.numberTextSize = numberTextSize;
    //    //this.percentTextSize = percentTextSize;
    //    postInvalidate();
    //}

    public void setProgress(int percent, String traffic) {
        this.percent = percent;
        this.traffic = traffic;
        postInvalidate();
    }

    private void initialize() {
        arcBackgroundPaint = new Paint();
        arcBackgroundPaint.setColor(context.getResources().getColor(R.color.progress_background));
        arcBackgroundPaint.setAntiAlias(true);
        //arcBackgroundPaint.setStrokeWidth(strokeWidth);
        arcBackgroundPaint.setStyle(Style.STROKE);
        arcBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        arcForegroundPaint = new Paint();
        //arcForegroundPaint.setColor(Color.parseColor("#ff8f00"));
        arcForegroundPaint.setAntiAlias(true);
        //arcForegroundPaint.setStrokeWidth(strokeWidth);
        arcForegroundPaint.setStyle(Style.STROKE);
        arcForegroundPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        //textPaint.setColor(Color.BLUE);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Align.CENTER);
        //textPaint.setTextSize(numberTextSize);
        textPaint.setTypeface(App.englishFont);
        textPaint.setStyle(Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textSize = getWidth() / 4;
        int strokeWidth = getWidth() / 25;

        RectF rect = new RectF();
        rect.left = strokeWidth;
        rect.right = getWidth() - strokeWidth;
        rect.top = strokeWidth;
        rect.bottom = getHeight() - strokeWidth;

        arcForegroundPaint.setStrokeWidth(strokeWidth);
        arcBackgroundPaint.setStrokeWidth(strokeWidth);

        canvas.drawArc(rect, START_ANGLE, SWEEP_ANGLE, false, arcBackgroundPaint);

        int color = Color.argb(230, 255, 255, 255);

        arcForegroundPaint.setColor(color);
        int sweepAngle = percent * SWEEP_ANGLE / 100;
        canvas.drawArc(rect, START_ANGLE, sweepAngle, false, arcForegroundPaint);

        textPaint.setTextSize(textSize);
        textPaint.setShadowLayer(strokeWidth / 2, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        textPaint.setColor(color);
        canvas.drawText(percent + "%", getWidth() / 2, getHeight() / 2, textPaint);

        textPaint.setTextSize(textSize / 3);
        textPaint.setShadowLayer(strokeWidth / 3, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        canvas.drawText(traffic, getWidth() / 2, getHeight() - (getHeight() / 6), textPaint);

        //textPaint.setTypeface(App.persianFont);
        //textPaint.setTextSize(textSize / 2);
        //textPaint.setShadowLayer(strokeWidth / 3, strokeWidth / 4, strokeWidth / 4, context.getResources().getColor(R.color.shadow));
        //canvas.drawText(caption, getWidth() / 2, getHeight() , textPaint);

    }
}
