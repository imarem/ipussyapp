package cat.imar.ipussy;

import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cat.imar.ipussy.model.PussyModel;
import cat.imar.ipussy.utils.MyService;
import cat.imar.ipussy.utils.Utils;

import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends BaseActivityDataBase {

	private ImageView imgVwUlls1;
	private ImageView imgVwUlls2;
	private ImageView imgVwUlls3;
	private ImageView imgVwUlls4;
	private ImageView imgVwUlls5;

	private ImageView imgVwPuntuacio1;
	private ImageView imgVwPuntuacio2;
	private ImageView imgVwPuntuacio3;
	private ImageView imgVwPuntuacio4;
	private ImageView imgVwPuntuacio5;

	private TextView txtVwUllsLocked3;
	private TextView txtVwUllsLocked4;
	private TextView txtVwUllsLocked5;

	private InterstitialAd mInterstitialAd;
	
	private int girlSelected = 1;
	
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.composite_activity_main);

		imgVwUlls1 = (ImageView) findViewById(R.id.ojos1);
		imgVwUlls2 = (ImageView) findViewById(R.id.ojos2);
		imgVwUlls3 = (ImageView) findViewById(R.id.ojos3);
		imgVwUlls4 = (ImageView) findViewById(R.id.ojos4);
		imgVwUlls5 = (ImageView) findViewById(R.id.ojos5);

		imgVwPuntuacio1 = (ImageView) findViewById(R.id.ojos1_puntuacio);
		imgVwPuntuacio2 = (ImageView) findViewById(R.id.ojos2_puntuacio);
		imgVwPuntuacio3 = (ImageView) findViewById(R.id.ojos3_puntuacio);
		imgVwPuntuacio4 = (ImageView) findViewById(R.id.ojos4_puntuacio);
		imgVwPuntuacio5 = (ImageView) findViewById(R.id.ojos5_puntuacio);

		txtVwUllsLocked3 = (TextView) findViewById(R.id.ojos3_locked_label);
		txtVwUllsLocked3.setTypeface(new Utils(getBaseContext())
				.getTypeFaceFont());
		txtVwUllsLocked4 = (TextView) findViewById(R.id.ojos4_locked_label);
		txtVwUllsLocked4.setTypeface(new Utils(getBaseContext())
				.getTypeFaceFont());
		txtVwUllsLocked5 = (TextView) findViewById(R.id.ojos5_locked_label);
		txtVwUllsLocked5.setTypeface(new Utils(getBaseContext())
				.getTypeFaceFont());
		
		mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6437586432045030/5533365705");
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

//        MediaPlayer mediaPlayer;
//        mediaPlayer = MediaPlayer.create(this,R.raw.ipussysong);
//        mediaPlayer.setLooping(true);
//        mediaPlayer.setVolume(100,100);
//        mediaPlayer.start();
        SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		Boolean show = sharedPref.getBoolean(
				"volumen_prefernce", true);
		if (show) {
			startService(new Intent(this, MyService.class));	
		}
        
        
		addListeners();
	}
	
	 public void displayInterstitial() {
	        if (mInterstitialAd.isLoaded()) {
	        	mInterstitialAd.show();
	        }
	    }

	/**
	 * M�tode que afageix els listeners als image buttons.
	 */
	private void addListeners() {
		
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				showConfirmActivity(girlSelected);
			}
		});
		
		imgVwUlls1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				girlSelected = 1;
				if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                	showConfirmActivity(1);	
                }
				
			}
		});

		imgVwUlls2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				girlSelected = 2;
				if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                	showConfirmActivity(2);	
                }
			}
		});

		imgVwUlls3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				girlSelected = 3;
				if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                	showConfirmActivity(3);	
                }
			}
		});

		imgVwUlls4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				girlSelected = 4;
				if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                	showConfirmActivity(4);	
                }
			}
		});

		imgVwUlls5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				girlSelected = 5;
				if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                	showConfirmActivity(5);	
                }
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		createSelectorFromDatabase(1, null, imgVwUlls1, imgVwPuntuacio1);
		createSelectorFromDatabase(2, null, imgVwUlls2, imgVwPuntuacio2);
		createSelectorFromDatabase(3, txtVwUllsLocked3, imgVwUlls3,
				imgVwPuntuacio3);
		createSelectorFromDatabase(4, txtVwUllsLocked4, imgVwUlls4,
				imgVwPuntuacio4);
		createSelectorFromDatabase(5, txtVwUllsLocked5, imgVwUlls5,
				imgVwPuntuacio5);

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
	}

	/**
	 * Funci� que permet crear el selector per les imatges a partir dels
	 * registres de la bbdd.
	 * 
	 * @param idPussyModel
	 *            l'id pussy.
	 * @param imgVwPuntuacio12
	 * @param imgVwUlls52
	 * @param txtVwUllsLocked42
	 * @return el selector StateListDrawable.
	 */
	private StateListDrawable createSelectorFromDatabase(
			final Integer idPussyModel, TextView txtVwUllsLocked,
			ImageView imgVwUlls, ImageView imgVwPuntuacio) {
		final PussyModel pussyModel = getHelper().getPussyModelById(
				idPussyModel);
		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] { android.R.attr.state_pressed },
				getResources().getDrawable(pussyModel.getPathImageEyesClose()));
		states.addState(new int[] {},
				getResources().getDrawable(pussyModel.getPathImageEyes()));
		imgVwUlls.setImageDrawable(states);

		if (pussyModel.getResult() == 1) {
			imgVwPuntuacio.setImageResource(R.drawable.img_puntuacio_11);
		} else if (pussyModel.getResult() == 2) {
			imgVwPuntuacio.setImageResource(R.drawable.img_puntuacio_21);
		} else if (pussyModel.getResult() == 3) {
			imgVwPuntuacio.setImageResource(R.drawable.img_puntuacio_31);
		} else {
			imgVwPuntuacio.setImageResource(R.drawable.img_puntuacio);
		}

		if (txtVwUllsLocked != null) {
			if (pussyModel.isEnabled()) {
				txtVwUllsLocked.setText("");
				imgVwUlls.setAlpha(255);
			} else {
				txtVwUllsLocked.setText(R.string.locked_label);
				imgVwUlls.setAlpha(90);
			}
		}
		return states;
	}

	@Override
	public void onBackPressed() {
		MainActivity.this.finish();
	}

	/**
	 * Funci� que permet navegar a la pantalla de confirmaci�-descripci�.
	 * 
	 * @param idPussyModel
	 *            l'id del model.
	 */
	private void showConfirmActivity(final Integer idPussyModel) {
		final PussyModel pussyModel = getHelper().getPussyModelById(
				idPussyModel);
		if (pussyModel.isEnabled()) {
			MainActivity.this.finish();
			startActivity(new Intent(getApplicationContext(),
					ConfirmStartFragment.class).putExtra("bean", pussyModel));
		} else {
			// TODO showdialog
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuItem buttonSettings = menu.add(R.string.preference);
		buttonSettings.setIcon(R.drawable.settings);
		buttonSettings.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		buttonSettings
				.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						MainActivity.this.finish();
						MainActivity.this.startActivity(new Intent(
								MainActivity.this, Preferences.class).putExtra(
								"mode", "MainActivity"));
						return false;
					}
				});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			MainActivity.this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
