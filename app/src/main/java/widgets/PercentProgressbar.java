package widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zohaltech.app.sigma.R;

public class PercentProgressbar extends View {

    Context context;
    RectF   rectBackground;
    RectF   rectForeground;
    RectF   rectText;
    Paint   arcBackgroundPaint;
    Paint   arcForegroundPaint;
    Paint   textPaint;
    private int progress;

    public PercentProgressbar(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public PercentProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    public PercentProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initialize();
    }

    private void initialize() {
        rectBackground = new RectF();
        rectForeground = new RectF();
        rectText = new RectF();

        //int color = Color.argb(230, 255, 255, 255);

        arcBackgroundPaint = new Paint();
        //arcBackgroundPaint.setColor(context.getResources().getColor(R.color.primary_dark));
        arcBackgroundPaint.setColor(context.getResources().getColor(R.color.gray));
        arcBackgroundPaint.setAntiAlias(true);
        arcBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arcBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        arcForegroundPaint = new Paint();
        arcForegroundPaint.setAntiAlias(true);
        arcForegroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arcForegroundPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        //textPaint.setTextAlign(Paint.Align.CENTER);
        //textPaint.setTypeface()
        //int textSize = getResources().getDisplayMetrics().widthPixels < getResources().getDisplayMetrics().heightPixels ?
        //               getResources().getDisplayMetrics().widthPixels / 25 :
        //               getResources().getDisplayMetrics().heightPixels / 25;
        //textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        //this.progress = 85;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float eachPercentWith = (float) getWidth() / 100;

        //rectBackground.set(0, 5, getWidth(), getHeight() - 5);
        rectBackground.set(0, getHeight() / 10, getWidth(), getHeight() - (getHeight() / 10));

        if (progress <= 25) {
            arcForegroundPaint.setColor(context.getResources().getColor(R.color.blue));
        } else if (progress > 25 && progress <= 50) {
            arcForegroundPaint.setColor(context.getResources().getColor(R.color.green));
        } else if (progress > 50 && progress <= 75) {
            arcForegroundPaint.setColor(context.getResources().getColor(R.color.orange));
        } else if (progress > 75) {
            arcForegroundPaint.setColor(context.getResources().getColor(R.color.red));
        }
        rectForeground.set(0, 0, progress * eachPercentWith, getHeight() + 10);

        canvas.drawRect(rectBackground, arcBackgroundPaint);
        canvas.drawRect(rectForeground, arcForegroundPaint);

        String text = " " + progress + "% ";
        int textSize = canvas.getHeight() / 2;
        textPaint.setTextSize(textSize);
        int textWidth = (int) textPaint.measureText(text);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        if (progress <= 50) {
            textPaint.setColor(context.getResources().getColor(R.color.gray_dark));
            textPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(text, (progress * eachPercentWith) + textWidth, yPos, textPaint);
        } else {
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(text, (progress * eachPercentWith) - textWidth, yPos, textPaint);
        }
    }
}
