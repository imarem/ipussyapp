package cat.imar.ipussy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.inputmethod.InputMethodManager;
import cat.imar.ipussy.R;

import com.actionbarsherlock.app.SherlockActivity;

public class Utils {
	
	private Context context;
	
	public Utils() {
		super();
	}
	
	public Utils(Context aContext) {
		super();
		context = aContext;
	}

	public static final int[] colorList = { R.color.insideGrad1,
			R.color.insideGrad2, R.color.insideGrad3, R.color.insideGrad4,
			R.color.insideGrad5, R.color.insideGrad6, R.color.insideGrad7,
			R.color.insideGrad8, R.color.insideGrad9, R.color.insideGrad10,R.color.insideGrad10,R.color.insideGrad10 };

	public static int getColorByDistance(final int distance,
			final int minDistance, final int maxDistance) {
		int fraccio = (maxDistance / 10);
		return colorList[Math.round(distance / fraccio)];
	}

	public static boolean isInRectangle(double centerX, double centerY, double radius,
			double x, double y) {
		return x >= centerX - radius && x <= centerX + radius
				&& y >= centerY - radius && y <= centerY + radius;
	}

	public static boolean isPointInCircle(double centerX, double centerY,
			double radius, double x, double y) {
		if (isInRectangle(centerX, centerY, radius, x, y)) {
			double dx = centerX - x;
			double dy = centerY - y;
			dx *= dx;
			dy *= dy;
			double distanceSquared = dx + dy;
			double radiusSquared = radius * radius;
			return distanceSquared <= radiusSquared;
		}
		return false;
	}
	
	public static Region createRegionByPath(Path path) {
		RectF rectF = new RectF();
		path.computeBounds(rectF, true);
		Region region = new Region();
		region.setPath(path, new Region((int) rectF.left,
				(int) rectF.top + 20, (int) rectF.right,
				(int) rectF.bottom + 20));
		return region;
	}
	
	public static Path createPath(Point[] points, boolean isPoly){
		if (points.length < 2) {
			return null;
		}
		Path polyPath = new Path();
		polyPath.moveTo(points[0].x, points[0].y);
		int i, len;
		len = points.length;
		for (i = 0; i < len; i++) {
			polyPath.lineTo(points[i].x, points[i].y);
		}
		if(isPoly){
			polyPath.lineTo(points[0].x, points[0].y);	
		}
		return polyPath;
	}
	
	public Typeface getTypeFaceFontBold() {
		return Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Bold.ttf"); 
	}
	
	public Typeface getTypeFaceFont() {
		return Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf"); 
	}
	
	public Typeface getTypeFaceFontCookie() {
		return Typeface.createFromAsset(context.getAssets(),"fonts/Cookie-Regular.ttf"); 
	}
	
	
	public BitmapDrawable writeTextOnDrawable(int drawableId, String text) {

	    Bitmap bm = BitmapFactory.decodeResource(context.getResources(), drawableId)
	            .copy(Bitmap.Config.ARGB_8888, true);

	    Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

	    Paint paint = new Paint();
	    paint.setStyle(Style.FILL);
	    paint.setColor(Color.WHITE);
	    paint.setTypeface(tf);
	    paint.setTextAlign(Align.CENTER);
	    paint.setTextSize(convertToPixels(context, 11));

	    Rect textRect = new Rect();
	    paint.getTextBounds(text, 0, text.length(), textRect);

	    Canvas canvas = new Canvas(bm);

	    //If the text is bigger than the canvas , reduce the font size
	    if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
	        paint.setTextSize(convertToPixels(context, 7));        //Scaling needs to be used for different dpi's

	    //Calculate the positions
	    int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

	    //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
	    int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;  

	    canvas.drawText(text, xPos, yPos, paint);

	    return new BitmapDrawable(context.getResources(), bm);
	}
	
	public static int convertToPixels(Context context, int nDP)
	{
	    final float conversionScale = context.getResources().getDisplayMetrics().density;

	    return (int) ((nDP * conversionScale) + 0.5f) ;

	}
	
	//Amga teclat x formularis.
	public static void hideSoftKeyboard(SherlockActivity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}


}
