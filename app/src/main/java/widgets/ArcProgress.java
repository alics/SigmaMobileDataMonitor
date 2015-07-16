package widgets;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;


public class ArcProgress extends ImageView {

    Context context;
    private Paint arcForegroundPaint;
    private Paint arcBackgroundPaint;
    private Paint textPaint;

    private static final int START_ANGLE = 140;
    private static final int SWEEP_ANGLE = 260;

    private int percent = 75;
    private int strokeWidth = 22;
    private int numberTextSize = 120;
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

    public void setProperties(int strokeWidth, int numberTextSize) {
        this.strokeWidth = strokeWidth;
        this.numberTextSize = numberTextSize;
        //this.percentTextSize = percentTextSize;
        postInvalidate();
    }

    public void setProgress(int value) {
        percent = value;
        postInvalidate();
    }

    private void initialize() {
        arcBackgroundPaint = new Paint();
        arcBackgroundPaint.setColor(context.getResources().getColor(R.color.progress_background));
        arcBackgroundPaint.setAntiAlias(true);
        arcBackgroundPaint.setStrokeWidth(strokeWidth);
        arcBackgroundPaint.setStyle(Style.STROKE);
        arcBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        arcForegroundPaint = new Paint();
        //arcForegroundPaint.setColor(Color.parseColor("#ff8f00"));
        arcForegroundPaint.setAntiAlias(true);
        arcForegroundPaint.setStrokeWidth(strokeWidth);
        arcForegroundPaint.setStyle(Style.STROKE);
        arcForegroundPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(Color.BLUE);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Align.CENTER);
        //textPaint.setTextSize(numberTextSize);
        //textPaint.setTypeface(App.appFont);
        textPaint.setStyle(Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect = new RectF();
        rect.left = strokeWidth;
        rect.right = getWidth() - strokeWidth;
        rect.top = strokeWidth;
        rect.bottom = getHeight() - strokeWidth;

        arcForegroundPaint.setStrokeWidth(strokeWidth);
        arcBackgroundPaint.setStrokeWidth(strokeWidth);

        canvas.drawArc(rect, START_ANGLE, SWEEP_ANGLE, false, arcBackgroundPaint);

        //int red = (int) (percent * 1.27f) + 127 ;
        //int green = (int) ((100 - percent) * 2.55f);
        //int color = Color.argb(127,red, green, 127);

        //int color = Color.argb(127,255, 255, 127);

        int color = Color.argb(230,255, 255, 255);

        arcForegroundPaint.setColor(color);
        int sweepAngle = percent * SWEEP_ANGLE / 100;
        canvas.drawArc(rect, START_ANGLE, sweepAngle, false, arcForegroundPaint);

        textPaint.setTextSize(numberTextSize);
        textPaint.setColor(color);
        canvas.drawText(percent + "%", getWidth() / 2, getHeight() / 2, textPaint);


        textPaint.setTextSize(40);
        canvas.drawText("1245/3065 MB", getWidth()/2, getHeight()-(getHeight()/6), textPaint);
    }
}
