package widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;

public class MyViewPagerIndicator extends ImageView {

    private Paint fillPaint;
    private Paint strokePaint;
    private int   count;
    private float offsetX;
    private int   currentPageIndex;
    private float percent;

    public MyViewPagerIndicator(Context context) {
        super(context);
        initialize();
    }

    public MyViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MyViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(App.context.getResources().getColor(R.color.white));
        fillPaint.setAntiAlias(true);

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        strokePaint.setColor(App.context.getResources().getColor(R.color.primary_dark));
        strokePaint.setAntiAlias(true);
    }

    public void setIndicatorsCount(int value) {
        count = value;
        computeIndicatorWidth();
    }

    public void setCurrentPage(int value) {
        currentPageIndex = value;
    }

    public void setPercent(float percent) {
        this.percent = percent;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < count; i++) {
            float radius = (float) App.screenWidth / 40;

            boolean canDrawFill = false;
            if (i == currentPageIndex) {
                fillPaint.setAlpha((int) ((1.0f - percent) * 255));
                canDrawFill = true;
            }

            if (percent > 0) {
                if (i == currentPageIndex + 1) {
                    fillPaint.setAlpha((int) (percent * 255));
                    canDrawFill = true;
                }
            }
            canvas.drawCircle(offsetX + (i * radius * 2), getHeight() / 2, radius / 2.0f, strokePaint);

            if (canDrawFill) {
                canvas.drawCircle(offsetX + (i * radius * 2), getHeight() / 2, radius / 2.0f, fillPaint);
            }
        }
    }

    private void computeIndicatorWidth() {
        float radius = App.screenWidth / 40;
        float indicatorWidth = (count * radius * 2) - (radius * 2);
        offsetX = (App.screenWidth - indicatorWidth) / 2;
    }
}
