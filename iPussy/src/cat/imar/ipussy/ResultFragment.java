package cat.imar.ipussy;

import java.io.FileOutputStream;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cat.imar.ipussy.game.GameActivity;
import cat.imar.ipussy.game.Result;
import cat.imar.ipussy.model.PussyModel;
import cat.imar.ipussy.utils.Utils;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ResultFragment extends SherlockActivity {

	private static final String SHARED_FILE_NAME = "shared.jpg";
	private TextView txtNameResult;
	private TextView txtDescResult;
	private TextView txtLabelResult;
	private TextView txtLabelResultFinal;
	private TextView txtLabelResultEnd;
	private ImageView imgViewResultat;
	private ImageView imgViewResultatPuntuacio;
	private ImageView imgViewCalca;
	private Button BtnMenu;
	private Button BtnShare;
	private Button BtnRestart;
	private Result result = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.composite_result);

		imgViewResultat = (ImageView) findViewById(R.id.imgViewModel);
		imgViewResultatPuntuacio = (ImageView) findViewById(R.id.imgViewModel_puntuacio);
		txtNameResult = (TextView) findViewById(R.id.txtNameResult);
		txtNameResult.setTypeface(getTypeFaceFontCookie());
		txtDescResult = (TextView) findViewById(R.id.txtDescResult);
		txtDescResult.setTypeface(getTypeFaceFont());
		txtLabelResult = (TextView) findViewById(R.id.txtLabelResult);
		txtLabelResult.setTypeface(getTypeFaceFont());
		txtLabelResultFinal = (TextView) findViewById(R.id.txtLabelResultFinal);
		txtLabelResultFinal.setTypeface(getTypeFaceFont());
		imgViewCalca = (ImageView) findViewById(R.id.imgViewCalca);
		txtLabelResultEnd = (TextView) findViewById(R.id.txtLabelResultEnd);
		txtLabelResultEnd.setTypeface(getTypeFaceFontCookie());

		BtnMenu = (Button) findViewById(R.id.btnMenu);
		BtnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ResultFragment.this.finish();
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));
			}
		});
		
		BtnShare = (Button) findViewById(R.id.btnShare);
		BtnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(Intent.createChooser(createShareIntent(), "Share via"));
			}
		});

		BtnRestart = (Button) findViewById(R.id.btnRestart);
		BtnRestart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ResultFragment.this.finish();
				final Intent intent = new Intent(getBaseContext(),
						GameActivity.class).putExtra(
						"bean",
						(PussyModel) getIntent().getSerializableExtra(
								"bean"));
				startActivity(intent);
			}
		});

		
		

		AdView adView = (AdView) this.findViewById(R.id.adViewResult);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

	}

	@Override
	protected void onStart() {
		super.onStart();
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// es recupera el resultat via itntent.
		result = (Result) getIntent().getSerializableExtra("result");
		// Es recuperen els valors, noms, imatge del objecet result que
		// cont� el bean amb el model.
		txtNameResult.setText(result.getNameModelPath());
		txtDescResult.setText(result.getResultDescriptionPath());
		txtLabelResultFinal.setText(result.getResultPath());
		txtLabelResultEnd.setText(result.getResultMessageLowerPath());
		// efecte de picar l'ull
		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] { android.R.attr.state_pressed },
				getResources().getDrawable(result.getImgModelPathClose()));
		states.addState(new int[] {},
				getResources().getDrawable(result.getImgModelPath()));
		imgViewResultat.setImageDrawable(states);
		imgViewResultat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		imgViewResultatPuntuacio.setImageResource(result.getPuntuacioPath());
		BtnMenu.setText(R.string.button_menu);
		BtnMenu.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());
		BtnShare.setText(R.string.button_share);
		BtnShare.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());
		BtnRestart.setText(R.string.button_resart);
		BtnRestart.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());

		copyPrivateRawResourceToPubliclyAccessibleFile();

		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String cargarIdioma = sharedPref.getString("language_list_preference",
				"");
		if (cargarIdioma.equals("")) {
			Locale current = getResources().getConfiguration().locale;
			if (current.getLanguage().equals("es")
					|| current.getLanguage().equals("en")
					|| current.getLanguage().equals("ca")) {
				cargarIdioma = current.getLanguage();
			} else {
				cargarIdioma = "en";
			}
		}
		Locale locale = new Locale(cargarIdioma);
		Locale.setDefault(locale);
		Configuration configuracion = new Configuration();
		configuracion.locale = locale;
		getBaseContext().getResources().updateConfiguration(configuracion,
				getBaseContext().getResources().getDisplayMetrics());

		int puntuacio = (Integer) getIntent().getSerializableExtra(
				"resultValue");
		if (puntuacio == 2) {
			imgViewCalca.setBackgroundResource(R.drawable.advance_calces);
		} else if (puntuacio == 3) {
			imgViewCalca.setBackgroundResource(R.drawable.sexmachine_calces);
		} else {
			imgViewCalca.setBackgroundResource(result.getImgCalcesResultPath());
		}

		if (puntuacio == 2 || puntuacio == 3) {
			imgViewCalca.post(new Runnable() {

				@Override
				public void run() {
					AnimationDrawable frameAnimation = (AnimationDrawable) imgViewCalca
							.getBackground();
					frameAnimation.start();

				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.share_action_provider, menu);

		MenuItem actionItem = menu
				.findItem(R.id.menu_item_share_action_provider_action_bar);
		ShareActionProvider actionProvider = (ShareActionProvider) actionItem
				.getActionProvider();
		actionProvider.setShareHistoryFileName(null);
		//actionProvider
		//		.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		actionProvider.setShareIntent(createShareIntent());

		// share();

		return true;
	}

	/**
	 * Creates a sharing {@link Intent}.
	 * 
	 * @return The sharing intent.
	 */
	private Intent createShareIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/jpeg");
		Uri uri = Uri.fromFile(getFileStreamPath(SHARED_FILE_NAME));
		// shareIntent.putExtra(Intent.EXTRA_TEXT, label);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		return shareIntent;
	}

	private Bitmap addTextToImageResult(int drawableId) {

		Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
				.copy(Bitmap.Config.ARGB_8888, true);

		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextAlign(Align.CENTER);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setTypeface(new Utils(getBaseContext()).getTypeFaceFontBold());
		paint.setTextSize(30);

		Canvas canvas = new Canvas(bm);

		Drawable image = getResources().getDrawable(result.getImgModelPath());
		// Store our image size as a constant
		final int IMAGE_WIDTH = image.getIntrinsicWidth();
		final int IMAGE_HEIGHT = image.getIntrinsicHeight();

		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String userName = sharedPref.getString("username_preference", "");
		Object[] arg = { userName,
				getResources().getString(result.getNameModelPath()),
				getResources().getString(result.getResultPath()) };

		String result = String.format(getResources().getString(R.string.result_share), arg);

		drawMultiLineText(result, IMAGE_WIDTH / 2, IMAGE_HEIGHT / 2, paint,
				canvas);

		return bm;
	}

	private void drawMultiLineText(String str, float x, float y, Paint paint,
			Canvas canvas) {
		String[] lines = str.split("\n");
		float txtSize = -paint.ascent() + paint.descent();

		if (paint.getStyle() == Style.FILL_AND_STROKE
				|| paint.getStyle() == Style.STROKE) {
			txtSize += paint.getStrokeWidth(); // add stroke width to the text
												// size
		}
		float lineSpace = txtSize * 0.1f; // default line spacing

		float firstTextY = y - txtSize + lineSpace;

		for (int i = 0; i < lines.length; ++i) {
			canvas.drawText(lines[i], x,
					firstTextY + (txtSize + lineSpace) * i, paint);
		}
	}

	public boolean saveImageToInternalStorage(Bitmap image) {

		try {
			// Use the compress method on the Bitmap object to write image to
			// the OutputStream
			FileOutputStream fos = openFileOutput(SHARED_FILE_NAME,
					Context.MODE_WORLD_READABLE);

			// Writing the bitmap to the output stream
			image.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();

			return true;
		} catch (Exception e) {
			Log.e("saveToInternalStorage()", e.getMessage());
			return false;
		}
	}

	/**
	 * Copies a private raw resource content to a publicly readable file such
	 * that the latter can be shared with other applications.
	 */
	private void copyPrivateRawResourceToPubliclyAccessibleFile() {
		boolean f = saveImageToInternalStorage(addTextToImageResult(result
				.getImgModelPath()));
	}

	@Override
	public void onBackPressed() {
		ResultFragment.this.finish();
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}

	private Typeface getTypeFaceFont() {
		return new Utils(getBaseContext()).getTypeFaceFont();
	}
	
	private Typeface getTypeFaceFontCookie() {
		return new Utils(getBaseContext()).getTypeFaceFontCookie();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			ResultFragment.this.finish();
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
