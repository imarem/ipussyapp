package cat.imar.ipussy;

import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cat.imar.ipussy.utils.MyService;
import cat.imar.ipussy.utils.Utils;

import com.actionbarsherlock.app.SherlockActivity;

public class SplashActivity extends SherlockActivity {

	private EditText editTextName;
	private Button btnPlay;
	private ImageView imgAudio;
	private Button btnRmvAds;
	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.splash_activity);
		editTextName = (EditText) findViewById(R.id.edittext_name);
		imgAudio = (ImageView) findViewById(R.id.btnAudio);
		editTextName.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());
		editTextName.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					String strUserName = v.getText().toString();
					return startGame(strUserName);
				}

				return handled;
			}
		});

		btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startGame(editTextName.getText().toString());
			}
		});

		btnRmvAds = (Button) findViewById(R.id.btnRmvAds);
		btnRmvAds.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void configureAudio() {
		sharedPref = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		Boolean show = sharedPref.getBoolean(
				"volumen_prefernce", true);
		if (show) {
			startService(new Intent(this, MyService.class));	
			imgAudio.setImageResource(R.drawable.audio_on);
		}else{
			imgAudio.setImageResource(R.drawable.audio_off);
		}
		
		imgAudio.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Boolean isOn = sharedPref.getBoolean("volumen_prefernce", true);
				if (isOn) {
					imgAudio.setImageResource(R.drawable.audio_off);
					stopMusic();
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
					SharedPreferences.Editor editor = preferences.edit();
					editor.putBoolean("volumen_prefernce", false);
					editor.commit();
				} else {
					imgAudio.setImageResource(R.drawable.audio_on);
					playMusic();
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
					SharedPreferences.Editor editor = preferences.edit();
					editor.putBoolean("volumen_prefernce", true);
					editor.commit();
				}
			}
		});
		
	}

	private void stopMusic() {
		stopService(new Intent(this, MyService.class));
	}

	private void playMusic() {
		startService(new Intent(this, MyService.class));
	}

	private boolean startGame(String strUserName) {
		boolean valid = false;
		if (!strUserName.equals("")) {
			int message = 0;
			valid = true;
			if (strUserName.length() == 1) {
				message = R.string.dialog_validate_name_message_short;
				valid = false;
			} else if (!strUserName.toUpperCase().contains("A")
					&& !strUserName.toUpperCase().contains("E")
					&& !strUserName.toUpperCase().contains("I")
					&& !strUserName.toUpperCase().contains("O")
					&& !strUserName.toUpperCase().contains("U")
					&& !strUserName.toUpperCase().contains("Y")) {
				message = R.string.dialog_validate_name_message_not_vocals;
				valid = false;
			}

			if (valid) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext());
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString("username_preference", strUserName);
				editor.commit();
				startActivity(new Intent(getBaseContext(), MainActivity.class));
			} else {
				new AlertDialog.Builder(SplashActivity.this)
						.setTitle(R.string.dialog_validate_name_title)
						.setMessage(message)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).show();
			}

		} else {
			int message;
			message = R.string.dialog_validate_name_message;

			new AlertDialog.Builder(SplashActivity.this)
					.setTitle(R.string.dialog_validate_name_title)
					.setMessage(message)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();
		}
		return valid;
	}

	@Override
	protected void onStart() {
		super.onStart();
		getSupportActionBar().hide();
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String userName = sharedPref.getString("username_preference", "");
		editTextName.setText(userName);

		btnPlay.setText(R.string.BtnPlay);
		btnPlay.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());

		btnRmvAds.setText(R.string.BtnRmsAds);
		btnRmvAds.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());

		findViewById(android.R.id.content).setOnTouchListener(
				new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						Utils.hideSoftKeyboard(SplashActivity.this);
						return false;
					}
				});

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

		configureAudio();

	}

	@Override
	public void onBackPressed() {
		SplashActivity.this.finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopService(new Intent(this, MyService.class));
	}
}
