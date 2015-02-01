package cat.imar.ipussy.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathDashPathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract.Helpers;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import cat.imar.ipussy.BaseActivityDataBase;
import cat.imar.ipussy.MainActivity;
import cat.imar.ipussy.R;
import cat.imar.ipussy.ResultFragment;
import cat.imar.ipussy.model.DBHelper;
import cat.imar.ipussy.model.DetailPussyModel;
import cat.imar.ipussy.model.PussyModel;
import cat.imar.ipussy.utils.AnimateDrawable;
import cat.imar.ipussy.utils.Point;
import cat.imar.ipussy.utils.Utils;

import com.actionbarsherlock.view.MenuItem;

public class GameActivity extends BaseActivityDataBase {

	public static Vibrator v = null;
	public SampleView sampleView;
	private DBHelper mDBHelper = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		PussyModel p = (PussyModel) getIntent().getSerializableExtra("bean");
		
		sampleView = new SampleView(this,
				p.getDetallPussyModelList().size() * 1000, p);
		
		setContentView(sampleView);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		sampleView.countDownTimer.cancel();
		GameActivity.this.finish();
		startActivity(new Intent(getApplicationContext(),
				MainActivity.class));	
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (item.getItemId() == android.R.id.home) {
	    	sampleView.countDownTimer.cancel();
	    	GameActivity.this.finish();
	    	startActivity(new Intent(getApplicationContext(),
					MainActivity.class));	
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		Boolean activeVibrator = sharedPref.getBoolean("vibrate_prefernce", true);
		if(activeVibrator){
			v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);	
		}else{
			v = null;
		}
		
//		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.combo);
//		mp.start();
		
		
		String cargarIdioma = sharedPref.getString("language_list_preference","");
		if (cargarIdioma.equals("")) {
			Locale current = getResources().getConfiguration().locale;
			if (current.getLanguage().equals("es")
					|| current.getLanguage().equals("en")
					|| current.getLanguage().equals("ca")) {
				cargarIdioma = current.getLanguage();
			}else{
				cargarIdioma = "en";
			}
		}
		Locale locale = new Locale(cargarIdioma);
		Locale.setDefault(locale);
		Configuration configuracion = new Configuration();
		configuracion.locale = locale;
		getBaseContext().getResources().updateConfiguration(configuracion,
				getBaseContext().getResources().getDisplayMetrics());
		
	}

	
	/**
	 * You'll need this in your class to get the helper from the manager once per class.
	 */
	public DBHelper getHelper() {
		if (mDBHelper == null) {
			mDBHelper = DBHelper.getHelper(this);
		}
		return mDBHelper;
	}
 
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
        	mDBHelper.close();
            mDBHelper = null;
        }
    }

	private static class SampleView extends View {
		// variables q indican las coord en donde se toco la pantalla
		public float touched_x, touched_y;
		public boolean touched, mutlitouched = false;

		private float height, width;
		private float mitadW, mitadH;
		private float offset = 0;
		private float offsetUser = 0;
		private float unitatWith, unitatHeight;
		private float unitatRadi;

		private int dreta, esquerra = 0;
		private int coordDitDret, coordDitEsquerra = -1;

		public CountDownTimer countDownTimer = null;
		private int timeNow = 0;
		private int timeStart = 0;
		private int totalTime;

		List<Integer> mPts;
		int modeUsuari = 1;

		private VelocityTracker mVelocityTracker = null;
		private Float vy;
		private boolean flagInit = true;
		private boolean flagInitMove= true;
		private Region vibrateRegion;

		private Integer counterVelocity = 0;
		private boolean isDownDirection;
		boolean flag = false;

		private Integer counterPowerBar = 0;
		private boolean counterHalf = true;

		private Clitoris clitoris;
		private PussyModel pussy;

		private boolean flagGoteo = false;
		private int velocityGoteo = 200;
		private int widthGoteo = 6;
		private float mPhase;

		private int ok = 0;
		private int ko = 0;
		private int valorFinal = 0;
		
		private String messageHelp;

		private Paint paintGameSequenceOut;
		private Paint paintGrocGameSequence;
		private Paint paintUserSequence;
		private Paint paintTimer;
		private Paint paintHelp;
		private Paint paintOuside;
		private Paint paintInside;
		private Paint paintGoteo;
		private Paint paintBarraOut;
		private Paint paintStart;
		private Paint paintPower;
		private Paint paintPowerLevel;

		private AnimateDrawable calcaAnimateDrawable;
		private Animation calcaAnimation;
		private Drawable calcaDrawable;

		private AnimateDrawable calcaTimerAnimateDrawable;
		private Animation calcaTimerAnimation;
		private Drawable calcaTimerDrawable;
		
		private boolean flagI = true;
		
		private GameActivity context;

		public SampleView(Context context) {
			super(context);
		}

		public SampleView(Context aContext, int time, PussyModel p) {
			super(aContext);
			context = (GameActivity) aContext;
			initVariables(time, p);
		}

		private void initVariables(int time, PussyModel p) {
			// asignem el temps total
			totalTime = time;
			// asignem objecte
			pussy = p;
			// creem els paint
			createPaint();
			
			messageHelp = "";
			// inicialitzem variables globals
			clitoris = new Clitoris();
			clitoris.setColor(getResources().getColor(R.color.xoxo2));

			mPts = new ArrayList<Integer>();
			flagInit = true;
			// sumem 1 segon perque el contador descendent funcioni
			// correctament.
			timeStart = (totalTime / 1000) + 1;
			countDownTimer = new CountDownTimer(totalTime + 1300, 500) {
				@Override
				public void onTick(long millisUntilFinished) {
					if (counterHalf) {
						// cada mitat de segon realitzem una predicció del
						// resultat per a poder pintar la barra de potencia
						prediccio();
					} else {
						// un cop realitzat un segon (dos mitjos) evaluem,
						// puntuem, i reiniciem contadors a 0.
						timeNow = timeStart - 1;
						//Log.e("timetable", String.valueOf(timeNow));
						evaluateCanvis();
						mPts.add(modeUsuari);
						puntation();

					}
					counterHalf = !counterHalf;
				}

				@Override
				public void onFinish() {
					endingGame();
					timeNow = 0;
				}
			};
		}
		
		private void endingGame(){
			int puntuacio = 1;
			if(valorFinal == 100){
				puntuacio = 3;
			}else if(valorFinal > 75){
				puntuacio = 2;
			}
			
			Result result = new Result(pussy, puntuacio);
			context.getHelper().updatePussyModelByIdAndResult(puntuacio, pussy);
			context.finish();
			getContext().startActivity(new Intent(getContext(),
					ResultFragment.class).putExtra("result", result).putExtra("resultValue", puntuacio));
			
		}

		private void createPaint() {
			// barra game sequence
			paintGameSequenceOut = new Paint();
			paintGameSequenceOut.setStyle(Style.STROKE);
			paintGameSequenceOut.setStrokeJoin(Paint.Join.ROUND); 
			paintGameSequenceOut.setStrokeCap(Paint.Cap.ROUND);
			paintGameSequenceOut.setPathEffect(new CornerPathEffect(20));
			paintGameSequenceOut.setShadowLayer(2f, 1, 1, getResources()
					.getColor(R.color.sombra));
			paintGameSequenceOut.setColor(getResources().getColor(
					R.color.grisBarra));
			paintGameSequenceOut.setAntiAlias(true);

			// game sequence colors
			paintGrocGameSequence = new Paint();
			paintGrocGameSequence.setStyle(Style.FILL);
			paintGrocGameSequence.setStrokeJoin(Paint.Join.ROUND);
			paintGrocGameSequence.setShadowLayer(0.3f, 0, -1, getResources()
					.getColor(R.color.sombra));
			paintGrocGameSequence.setColor(getResources().getColor(
					R.color.grocBarra));

			// user sequence
			paintUserSequence = new Paint();
			paintUserSequence.setStyle(Style.FILL);
			paintUserSequence.setPathEffect(new CornerPathEffect(30));
			paintUserSequence.setShadowLayer(5.0f, 0.0f, 1.0f, Color.BLACK);

			// timer
			paintTimer = new Paint();
			paintTimer.setColor(getResources().getColor(R.color.masterColor));
			paintTimer.setTextSize(22);
			paintTimer.setStyle(Style.FILL);
			paintTimer.setTextAlign(Align.CENTER);
			paintTimer.setFlags(Paint.ANTI_ALIAS_FLAG);
			
			paintHelp = new Paint();
			paintHelp.setColor(getResources().getColor(R.color.masterColor));
			paintHelp.setTextSize(26);
			paintHelp.setTextAlign(Align.CENTER);
			paintHelp.setTypeface(new Utils(context).getTypeFaceFont());
			paintHelp.setFlags(Paint.ANTI_ALIAS_FLAG);
			
			paintPower = new Paint();
			paintPower.setColor(getResources().getColor(R.color.masterColor));
			paintPower.setTextAlign(Align.CENTER);
			paintPower.setTypeface(new Utils(context).getTypeFaceFontBold());
			paintPower.setFlags(Paint.ANTI_ALIAS_FLAG);
			
			paintPowerLevel = new Paint();
			paintPowerLevel.setColor(getResources().getColor(R.color.White));
			paintPowerLevel.setTextAlign(Align.CENTER);
			paintPowerLevel.setTypeface(new Utils(context).getTypeFaceFont() );
			paintPowerLevel.setFlags(Paint.ANTI_ALIAS_FLAG);
			// ouside
			paintOuside = new Paint();
			paintOuside.setStyle(Style.FILL);
			paintOuside.setColor(getResources().getColor(R.color.xoxo2));

			// inside
			paintInside = new Paint();
			paintInside.setStyle(Style.FILL);
			paintInside.setColor(getResources().getColor(R.color.masterColor));

			// goteig
			paintGoteo = new Paint(Paint.ANTI_ALIAS_FLAG);
			paintGoteo.setStyle(Paint.Style.FILL);
			paintGoteo.setColor(Color.WHITE);
			paintGoteo.setTextAlign(Align.CENTER);
			
			paintStart = new Paint();
			paintStart.setTextAlign(Align.CENTER);
			paintStart.setColor(Color.WHITE);
			paintStart.setTypeface(new Utils(context).getTypeFaceFont());
			paintStart.setFlags(Paint.ANTI_ALIAS_FLAG);

			// barra exterior
			paintBarraOut = new Paint();
			paintBarraOut.setStyle(Style.STROKE);
			paintBarraOut.setShadowLayer(2.0f, 1, 1,
					getResources().getColor(R.color.sombra));
			paintBarraOut.setStrokeJoin(Paint.Join.ROUND); // set the join to
															// round you want
			paintBarraOut.setStrokeCap(Paint.Cap.ROUND);
			paintBarraOut.setColor(getResources().getColor(R.color.grisBarra));
			paintBarraOut.setAntiAlias(true);

			calcaDrawable = getResources().getDrawable(R.drawable.calces);
			calcaDrawable.setBounds(0, 0, calcaDrawable.getIntrinsicWidth(),
					calcaDrawable.getIntrinsicHeight());

			calcaTimerDrawable = getResources().getDrawable(R.drawable.calces);
			calcaTimerDrawable.setBounds(0, 0,
					calcaTimerDrawable.getIntrinsicWidth(),
					calcaTimerDrawable.getIntrinsicHeight());
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// tamany canvas.
			if(flagI){
				width = canvas.getClipBounds().width();
				height = canvas.getClipBounds().height();
				flagI=false;
	
			}
			
			// mitats canvas
			mitadW = width / 2;
			mitadH = height / 2;

			// unitats proporcionals
			unitatWith = width / 11;
			unitatHeight = height / 18;
			paintTimer.setTextSize(unitatHeight *2/3);
			paintHelp.setTextSize(unitatHeight * 2/3);
			paintPower.setTextSize(unitatHeight * 2/3);
			paintPowerLevel.setTextSize(unitatHeight * 1/3);
			
			if(flagInitMove){
				unitatRadi = (float) (unitatHeight*0.3);
				clitoris.setMove(((unitatRadi)/10));
				clitoris.setRadiMin(unitatRadi);
				clitoris.setRadi(unitatRadi * 3/2);
				clitoris.setRadiMax(unitatRadi * 2);
				flagInitMove = false;
			}
			
			canvas.drawColor(getResources().getColor(R.color.xoxoBackground));
			
			// metode que pinta el contador
			drawTimeCounter(canvas);
			// metode que pinta la sequencia ideal
			drawGameSequence(canvas);

			// metode que pinta la sequencia de l'usuari
			drawUserSequence(canvas);

			drawTextHelper(canvas);
			drawTextPowerBar(canvas);


			// metode que dibuixa la part exterior
			Region regionVulva = drawOutsideZone(canvas);

			// metode que pinta la zona vibratoria
			drawVibrateZone(canvas);

			// metode que pinta la prt interior
			Region regionXox = drawInsideZone(canvas, regionVulva);

			// MOVIMENT DEL DIT
			drawClitoris(canvas, regionXox);

			if (clitoris.isFlagAnimate()) {
				clitoris.setRadi(clitoris.getRadi() + clitoris.getMove());
				if (clitoris.getRadi() > clitoris.getRadiMax()
						|| clitoris.getRadi() < clitoris.getRadiMin()) {
					clitoris.setMove(clitoris.getMove() * -1);
				}
			}
			if(flagInit){
				paintStart.setTextSize(clitoris.getRadi()*2);
				drawMessajeStart(canvas);
			}

			drawVelocityBar(canvas);

			if (calcaAnimateDrawable != null)
				calcaAnimateDrawable.draw(canvas);

			if (calcaTimerAnimateDrawable != null)
				calcaTimerAnimateDrawable.draw(canvas);

			invalidate();
		}

		private void drawVelocityBar(Canvas canvas) {

			Paint paintVelocity = new Paint();
			paintVelocity.setStyle(Style.FILL);

			// liniaGroga
			RectF rectGroc = new RectF();
			rectGroc.set(unitatWith, height - unitatHeight*3/2- unitatHeight/2, (unitatWith * 4) - 2, 
					height - unitatHeight- unitatHeight/4);
			paintVelocity.setColor(getResources().getColor(R.color.grocBarra));
			canvas.drawRect(rectGroc, paintVelocity);
			
			canvas.drawText("LOW",
					rectGroc.centerX(), (float) (rectGroc.centerY() + (float)paintPowerLevel.getTextSize()*0.4), paintPowerLevel);

			// liniaTaronja
			RectF rectTaronja = new RectF();
			rectTaronja.set((unitatWith * 4) + 2, height
					- unitatHeight*3/2- unitatHeight/2, (unitatWith * 7) - 2, height - unitatHeight - unitatHeight/4);
			paintVelocity
					.setColor(getResources().getColor(R.color.tronjaBarra));
			canvas.drawRect(rectTaronja, paintVelocity);
			
			canvas.drawText("NORMAL",
					rectTaronja.centerX(), (float) (rectGroc.centerY() + (float)paintPowerLevel.getTextSize()*0.40), paintPowerLevel);

			// liniaRosa
			RectF rectRosa = new RectF();
			rectRosa.set((unitatWith * 7) + 2, height - unitatHeight*3/2- unitatHeight/2, 
					unitatWith * 10, height - unitatHeight- unitatHeight/4);
			paintVelocity
					.setColor(getResources().getColor(R.color.masterColor));
			canvas.drawRect(rectRosa, paintVelocity);
			
			canvas.drawText("HIGH",
					rectRosa.centerX(), (float) (rectGroc.centerY() + (float)paintPowerLevel.getTextSize()*0.40), paintPowerLevel);

			
		}

		public void drawGameSequence(Canvas canvas) {
			float size = (width - unitatWith * 2)
					/ (pussy.getDetallPussyModelList().size());

			// colors interns en funció de la moel.
			offset = 0;
			for (int i = 0; i < pussy.getDetallPussyModelList().size(); i++) {
				DetailPussyModel detail = pussy.getDetallPussyModelList()
						.get(i);
				RectF rect = new RectF();
				
				if(i < pussy.getDetallPussyModelList().size() - 1){
					if(detail.getMode().equals(pussy.getDetallPussyModelList()
						.get(i +1).getMode())){
						offset = 0;
					}else{
						offset = 2;
					}		
				}
				float left = (i * size) + unitatWith;
				float right = (i * size) + size + unitatWith - offset;
				float top = unitatHeight;// + unitatHeight / 8;
				float bottom = unitatHeight  + (unitatHeight / 2) -2;// - unitatHeight / 8;
				rect.set(left, top, right, bottom);
				Paint paint = new Paint();
				if (detail.getMode() == 1) {
					paint.setColor(getResources().getColor(R.color.grocBarra));
				} else if (detail.getMode() == 2) {
					paint.setColor(getResources().getColor(R.color.tronjaBarra));
				} else if (detail.getMode() == 3) {
					paint.setColor(getResources().getColor(R.color.masterColor));
				}
				canvas.drawRect(rect, paint);
			}
		}

		/**
		 * Mètode que dibuixa la sequencia de velocitats realitzada per
		 * l'usuari-
		 * 
		 * @param canvas
		 *            el canvas
		 */
		private void drawUserSequence(Canvas canvas) {
			float size = ((width - unitatWith * 2)
					/ (pussy.getDetallPussyModelList().size()));
			for (int i = 0; i < mPts.size(); i++) {
				RectF rect = new RectF();
				float left = (i * size) + unitatWith + offsetUser;
				float right = (i * size) + size + unitatWith ;
				float top = unitatHeight + (unitatHeight / 2);
				float bottom = unitatHeight * 2 -2;
				rect.set(left, top, right, bottom);
				Paint paintUserSequence = new Paint();
				if (mPts.get(i) == 1) {
					paintUserSequence.setColor(getResources().getColor(
							R.color.grocBarra));
				} else if (mPts.get(i) == 2) {
					paintUserSequence.setColor(getResources().getColor(
							R.color.tronjaBarra));
				} else if (mPts.get(i) == 3) {
					paintUserSequence.setColor(getResources().getColor(
							R.color.masterColor));
				}else{
					paintUserSequence.setColor(getResources().getColor(
							R.color.xoxoBackground));
				}
				canvas.drawRect(rect, paintUserSequence);
			}
		}

		private void drawClitoris(Canvas canvas, Region regionXox) {
			clitoris.changeValues(mitadW, mitadH - (mitadH / 3), 0, null,
					regionXox);
			if (!mutlitouched) {
				if (touched) {// si tocamos Y ESTAMOS EN LA REGION
					if (regionXox.contains((int)touched_x, (int)touched_y)) {
						clitoris.changeValues(touched_x, touched_y, 0, null,
								null);
					}
				}
			}
			canvas.drawCircle(clitoris.getX(), clitoris.getY(),
					clitoris.getRadi(), clitoris.getPaint());
		}
		
		private void drawMessajeStart(Canvas canvas) {
			canvas.drawText(getResources().getString(R.string.label_game_start),
					clitoris.getX(), clitoris.getY(), paintStart);
		}

		/**
		 * Mètode que pinta la zona interna, el moviment amb dos dits i el
		 * goteix.
		 * 
		 * @param canvas
		 *            el canvas
		 * @param regionVulva
		 *            la regio exterior (limit d'obertura)
		 * @return la regió interior.
		 */
		private Region drawInsideZone(Canvas canvas, Region regionVulva) {
			// XOX INTERIOR
			// creem xox interior en funció de les coordenandes dels dit dret i
			// esquerra.
			Path pathXox = null;
			if (coordDitDret > 0 && coordDitEsquerra > 0) {
				float widthleft = regionVulva.contains(coordDitDret, (int)mitadH) ? coordDitDret
						: width - unitatWith - 5;

				float widhtRight = regionVulva.contains(coordDitEsquerra, (int)mitadH) ? coordDitEsquerra
						: unitatWith + 5;

				paintInside.setColor(getResources().getColor(
						Utils.getColorByDistance((int)(widthleft - widhtRight),
								(int)((mitadW / 5) * 2), (int) (width - unitatWith * 2))));

				pathXox = drawPoly(canvas, paintInside, new Point[] {
						new Point(widthleft, mitadH),
						new Point(mitadW, unitatHeight * 3),
						new Point(widhtRight, mitadH),
						new Point(mitadW, height - unitatHeight * 3) });

				if (flagGoteo) {

					paintGoteo.setStrokeWidth(widthGoteo);
					paintGoteo.setPathEffect(new PathDashPathEffect(
							makePathGota(), velocityGoteo, mPhase,
							PathDashPathEffect.Style.TRANSLATE));
					mPhase += 1;
					canvas.drawPath(Utils.createPath(new Point[] {
							new Point(mitadW, height - unitatHeight * 3),
							new Point(widhtRight, mitadH),
							new Point(mitadW, unitatHeight * 3) }, false),
							paintGoteo);

					canvas.drawPath(Utils.createPath(new Point[] {
							new Point(mitadW, height - unitatHeight * 3),
							new Point(widthleft, mitadH),
							new Point(mitadW, unitatHeight * 3), }, false),
							paintGoteo);
				}
			} else {
				// estat de repos
				paintInside.setColor(getResources().getColor(
						Utils.getColorByDistance((int)(width - unitatWith * 6),
								(int)((mitadW / 5) * 2), (int)(width - unitatWith * 2))));

				pathXox = drawPoly(canvas, paintInside, new Point[] {
						new Point(unitatWith * 3, mitadH),
						new Point(mitadW, unitatHeight * 3),
						new Point(width - unitatWith * 3, mitadH),
						new Point(mitadW, height - unitatHeight * 3) });
				
				if (flagGoteo) {

					paintGoteo.setStrokeWidth(widthGoteo);
					paintGoteo.setPathEffect(new PathDashPathEffect(
							makePathGota(), velocityGoteo, mPhase,
							PathDashPathEffect.Style.TRANSLATE));
					mPhase += 1;
					canvas.drawPath(Utils.createPath(new Point[] {
							new Point(mitadW, height - unitatHeight * 3),
							new Point(width - unitatWith * 3, mitadH),
							new Point(mitadW, unitatHeight * 3) }, false),
							paintGoteo);

					canvas.drawPath(Utils.createPath(new Point[] {
							new Point(mitadW, height - unitatHeight * 3),
							new Point(unitatWith * 3, mitadH),
							new Point(mitadW, unitatHeight * 3), }, false),
							paintGoteo);
				}
				
			}
			return Utils.createRegionByPath(pathXox);
		}

		/**
		 * Mètode que crea una gota.
		 * 
		 * @return el path.
		 */
		private Path makePathGota() {
			Path p = new Path();
			RectF rectGota = new RectF(0, 0, 0 + unitatHeight / 3,
					0 + unitatHeight / 2);
			p.addArc(rectGota, 0, 180);
			p.moveTo(rectGota.left, rectGota.top + rectGota.height() / 2);
			p.lineTo(rectGota.left, rectGota.top + rectGota.height() / 2);
			p.lineTo(rectGota.left + rectGota.width() / 2, rectGota.top);
			p.lineTo(rectGota.right, rectGota.top + rectGota.height() / 2);
			return p;
		}

		/**
		 * Mètode que dibuixa la zona de fora.
		 * 
		 * @param canvas
		 *            el canvas.
		 * @return la regio de fora .
		 */
		private Region drawOutsideZone(Canvas canvas) {
			// ZONA EXTERIOR creem quadrat exterior (Vulva)
			Path pathVulva = drawPoly(canvas, paintOuside, new Point[] {
					new Point(unitatWith, mitadH),
					new Point(mitadW, unitatHeight * 3),
					new Point(width - unitatWith, mitadH),
					new Point(mitadW, height - unitatHeight * 3) });

			// creem regió per al path per a limitar la obertura del pussy
			return Utils.createRegionByPath(pathVulva);
		}

		/**
		 * Mètode que crea i limita la regio de vibració
		 * 
		 * @param canvas
		 *            el canvas.
		 */
		private void drawVibrateZone(Canvas canvas) {
			Paint paintTransparent = new Paint();
			paintTransparent.setAlpha(0);

			Path pathVibrate = drawPoly(canvas, paintTransparent, new Point[] {
					new Point(mitadW + 50, mitadH - (mitadH / 2)),
					new Point(mitadW, mitadH - (mitadH / 2) + 50),
					new Point(mitadW - 50, mitadH - (mitadH / 2)),
					new Point(mitadW, mitadH - (mitadH / 2) - 50) });
			vibrateRegion = Utils.createRegionByPath(pathVibrate);
		}

		/**
		 * Metode que dibuixa el contador del joc.
		 * 
		 * @param canvas
		 *            el canvas
		 */
		private void drawTimeCounter(Canvas canvas) {
			Paint paint = new Paint();
			paint.setTextAlign(Align.CENTER);
			canvas.drawText(String.valueOf(timeNow),  width - unitatWith* 2/ 3, unitatHeight
					+ (unitatHeight /2) + (unitatHeight/4), paintTimer);			
		}
		
		/**
		 * Metode que dibuixa el contador del joc.
		 * 
		 * @param canvas
		 *            el canvas
		 */
		private void drawTextHelper(Canvas canvas) {
			canvas.drawText(messageHelp,mitadW, unitatHeight
					*3 - 7, paintHelp);			
		}
		
		/**
		 * Metode que dibuixa el contador del joc.
		 * 
		 * @param canvas
		 *            el canvas
		 */
		private void drawTextPowerBar(Canvas canvas) {
			canvas.drawText("POWER BAR",mitadW, height - unitatHeight
					/3, paintPower);			
		}
		
		
	

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			touched_x = (int) event.getX();
			touched_y = (int) event.getY();

			float inici = (((unitatWith * 3) / 8) * 1);
			float fi = (((unitatWith * 3) / 8) * 1);
			// leemos codigo de accion
			int index2 = event.getActionIndex();
			int pointerId = event.getPointerId(index2);

			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = 0;
			switch (action) {
			case MotionEvent.ACTION_DOWN:// Cuando se toca la pantalla
				isInitGame((int)touched_x, (int)touched_y);
				if (!flagInit) {
					touched = true;
				}
				menageVeloctyTracker(event);
				break;
			case MotionEvent.ACTION_MOVE:// Cuando se desplaza el dedo por la
				if (!flagInit) {
					touched = true;
					evaluateVelocity(event, pointerId);
				}

				if (mutlitouched) {
					int xDreta = (int) event.getX(dreta);
					int xEsquerra = (int) event.getX(esquerra);
					coordDitDret = xDreta > mitadW + (mitadW / 6) ? (int)xDreta
							: (int)(mitadW + (mitadW / 6));
					coordDitEsquerra = xEsquerra < mitadW - (mitadW / 6) ? xEsquerra
							: (int)(mitadW - (mitadW / 6));
				}
				break;
			case MotionEvent.ACTION_UP:
				touched = false;
				makeCalcaBarraAnimation(inici, fi);
				touched_x = touched_y = 0;
				break;
			case MotionEvent.ACTION_CANCEL:
				touched = false;
				touched_x = touched_y = 0;
				break;
			case MotionEvent.ACTION_OUTSIDE:
				touched = false;
				mVelocityTracker.recycle();
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				mutlitouched = true;
				for (int index = 0; index < event.getPointerCount(); ++index) {
					pointerIndex = event.getPointerId(index);
					if ((int) event.getX(pointerIndex) > mitadW) {
						dreta = pointerIndex;
					} else {
						esquerra = pointerIndex;
					}
				}
				break;
			case MotionEvent.ACTION_POINTER_UP:
				mutlitouched = false;
				touched_x = touched_y = 0;
				break;

			default:
			}
			return true;
		}

		/**
		 * Mètode que a partir d'un coordenanda permet actvar el joc si toques
		 * el clitoris.
		 * 
		 * @param x
		 *            la x.
		 * @param y
		 *            la y.
		 */
		private void isInitGame(int x, int y) {
			if (flagInit
					&& Utils.isPointInCircle(clitoris.getX(), clitoris.getY(),
							clitoris.getRadi() + 15, x, y)) {
				// activem contador i moviment
				countDownTimer.start();
				flagInit = false;

				// primer moviment de calça temps
				makeCalcaTimerAnimation(1, 2);
			}
		}

		/**
		 * Mètode que inicialitza l'objecte velocity traker.
		 * 
		 * @param event
		 *            l'event touch.
		 */
		private void menageVeloctyTracker(MotionEvent event) {
			if (mVelocityTracker == null) {
				// Retrieve a new VelocityTracker object to watch the velocity
				// of a motion.
				mVelocityTracker = VelocityTracker.obtain();
			} else {
				// Reset the velocity tracker back to its initial state.
				mVelocityTracker.clear();
			}
			// Add a user's movement to the tracker.
			mVelocityTracker.addMovement(event);
		}

		/**
		 * Mètode on s'avalua la velocitat (pixels/segon) i es realitza el
		 * comptatge. Es suma la velocitat en un periode de un segon, i es
		 * reinicia un cop ha passat.
		 * 
		 * @param event
		 *            l'event touch.
		 * @param pointerId
		 */
		private void evaluateVelocity(MotionEvent event, int pointerId) {
			mVelocityTracker.addMovement(event);
			mVelocityTracker.computeCurrentVelocity(1000, 200000);
			// recuperem la velocitat en l'eix y
			vy = VelocityTrackerCompat
					.getYVelocity(mVelocityTracker, pointerId);
			// recuperem la velocitat en l'eix x
			// vx = VelocityTrackerCompat.getXVelocity(mVelocityTracker,
			// pointerId);

			if (vibrateRegion.contains((int) event.getX(), (int) event.getY())) {
				//virate(100);
			}

			if (!flag) {
				if (vy < 0) {
					isDownDirection = true;
				} else {
					isDownDirection = false;
				}
				flag = true;
			}

			if (isDownDirection && vy > 0) {
				counterVelocity = counterVelocity + 1;
				isDownDirection = !isDownDirection;
			} else if (!isDownDirection && vy < 0) {
				counterVelocity = counterVelocity + 1;
				isDownDirection = !isDownDirection;
			}
		}

		/**
		 * Mètode que permet evaluar la velocitat de l'usuari durant cada segon,
		 * i reinici el contador de velocitats.
		 */
		private void evaluateCanvis() {
			// sumem les velocitats en un interval de un segon.
			Float inici = (float) (((unitatWith * 3) / 8) * counterPowerBar);
			if (timeStart != timeNow) {
				timeStart = timeNow;
				if (counterVelocity == 0) {
					modeUsuari = 0;
					counterPowerBar = 5;
				} else if (counterVelocity < 5) {
					modeUsuari = 1;
					counterPowerBar = 5;
				} else if (5 <= counterVelocity && counterVelocity < 9) {
					modeUsuari = 2;
					counterPowerBar = 13;
				} else {
					modeUsuari = 3;
					counterPowerBar = 20;
				}
				counterVelocity = 0;
			}
			
			int idetificador = (totalTime / 1000 - timeStart) + 2;
			makeCalcaTimerAnimation(idetificador, idetificador + 1);

			Float fi = (float) (((unitatWith * 3) / 8) * counterPowerBar);

			if (!touched) {
				inici = (float) (((unitatWith * 3) / 8) * 1);
				fi = (float) (((unitatWith * 3) / 8) * 1);
			}

			makeCalcaBarraAnimation(inici, fi);

		}

		protected void prediccio() {

			Float inici = (float) (((unitatWith * 3) / 8) * counterPowerBar);
			if (counterVelocity < 3) {
				counterPowerBar = 3;
				// lent
			} else if (counterVelocity >= 3 && counterVelocity < 5) {
				// normal
				counterPowerBar = 11;
			} else {
				// rapid
				counterPowerBar = 18;
			}

			Float fi = (float) (((unitatWith * 3) / 8) * counterPowerBar);

			if (!touched) {
				inici = (float) (((unitatWith * 3) / 8) * 1);
				fi = (float) (((unitatWith * 3) / 8) * 1);
			}

			makeCalcaBarraAnimation(inici, fi);
		}

		/**
		 * Métode que crea i mou la calça dins la barra de potencia.
		 * 
		 * @param inici
		 *            l'inici.
		 * @param fi
		 *            la distancia fi.
		 */
		private void makeCalcaBarraAnimation(float inici, float fi) {
			calcaAnimation = new TranslateAnimation(inici, fi, height
					- unitatHeight*3/2- unitatHeight/2 -calcaDrawable.getIntrinsicHeight(), height
					- unitatHeight*3/2- unitatHeight/2 - calcaDrawable.getIntrinsicHeight());
			calcaAnimation.setDuration(500);
			calcaAnimation.setRepeatCount(0);
			calcaAnimation.initialize(10, 10, (int)(height
					- unitatHeight*3/2- unitatHeight/2 - calcaDrawable.getIntrinsicHeight()),
					(int)(height
					- unitatHeight*3/2- unitatHeight/2 - calcaDrawable.getIntrinsicHeight()));

			calcaAnimateDrawable = new AnimateDrawable(calcaDrawable,
					calcaAnimation);
			calcaAnimation.startNow();
		}

		/**
		 * Métode que crea i mou la calça a la barra de temps.
		 * 
		 * @param xFromDelta
		 *            l'inici.
		 * @param xToDelta
		 *            la distancia fi.
		 */
		private void makeCalcaTimerAnimation(float xFromDelta, float xToDelta) {
			float size = (width - unitatWith * 2)
					/ pussy.getDetallPussyModelList().size();

			
			calcaTimerAnimation = new TranslateAnimation(size * xFromDelta,
					size * xToDelta, unitatHeight - calcaDrawable.getIntrinsicHeight(), 
					unitatHeight - calcaDrawable.getIntrinsicHeight());
			calcaTimerAnimation.setDuration(1000);
			calcaTimerAnimation.setRepeatCount(0);
			calcaTimerAnimation.initialize(10, 10, (int)(unitatHeight - calcaDrawable.getIntrinsicHeight()),
					(int)(unitatHeight - calcaDrawable.getIntrinsicHeight()));

			calcaTimerAnimateDrawable = new AnimateDrawable(calcaTimerDrawable,
					calcaTimerAnimation);
			calcaTimerAnimation.startNow();
		}

		/**
		 * Mètode que compara la velocitat esperada, amb la del usuari i permet
		 * evaluar.
		 */
		private void puntation() {
			// recuperem el segon actual i comprarem el resultat amb l'esperat
			int idetificador = totalTime / 1000 - timeStart;
			int mode = pussy.getDetallPussyModelList().get(idetificador)
					.getMode();
			if (mPts.get(idetificador)
					.equals(mode)) {
				ok = ok + 1;
				messageHelp = getResources().getString(R.string.label_game_ok);
			} else {
				ko = ko + 1;
				if(mode > mPts.get(idetificador)){
					messageHelp = getResources().getString(R.string.label_game_lower);	
				}else{
					messageHelp =getResources().getString(R.string.label_game_greater);
				}
				
				virate(30);
			}
			valorFinal = ((ok * 100) / pussy.getDetallPussyModelList().size());
			int color = getResources().getColor(R.color.xoxo2);
			if (valorFinal >= 85) {
				color = getResources().getColor(R.color.xoxo3);
				widthGoteo = 12;
			} else if (valorFinal >= 70) {
				widthGoteo = 8;
				velocityGoteo = 100;
			} else if (valorFinal >= 50) {
				clitoris.setFlagAnimate(true);
				flagGoteo = true;
				color = getResources().getColor(R.color.xoxo2);
			}
			
			if (mode == 1) {
				paintTimer.setColor(getResources().getColor(R.color.grocBarra));
			} else if (mode== 2) {
				paintTimer.setColor(getResources().getColor(R.color.tronjaBarra));
			} else if (mode == 3) {
				paintTimer.setColor(getResources().getColor(R.color.masterColor));
			}
			
			clitoris.changeValues(null, null, 0, color, null);
		}

		private Path drawPoly(Canvas canvas, Paint paint, Point[] points) {
			Path polyPath = Utils.createPath(points, true);
			canvas.drawPath(polyPath, paint);
			return polyPath;
		}

		private void virate(final int duration) {
			if(GameActivity.v != null)
			GameActivity.v.vibrate(duration);
		}
	}
}
