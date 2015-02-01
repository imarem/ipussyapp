package cat.imar.ipussy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cat.imar.ipussy.game.Result;
import cat.imar.ipussy.utils.Utils;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

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
	private Result result = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.composite_result);

		imgViewResultat = (ImageView) findViewById(R.id.imgViewModel);
		imgViewResultatPuntuacio = (ImageView) findViewById(R.id.imgViewModel_puntuacio);
		txtNameResult = (TextView) findViewById(R.id.txtNameResult);
		txtNameResult.setTypeface(getTypeFaceFont());
		txtDescResult = (TextView) findViewById(R.id.txtDescResult);
		txtDescResult.setTypeface(getTypeFaceFont());
		txtLabelResult = (TextView) findViewById(R.id.txtLabelResult);
		txtLabelResult.setTypeface(getTypeFaceFont());
		txtLabelResultFinal = (TextView) findViewById(R.id.txtLabelResultFinal);
		txtLabelResultFinal.setTypeface(getTypeFaceFont());
		imgViewCalca = (ImageView) findViewById(R.id.imgViewCalca);
		txtLabelResultEnd = (TextView) findViewById(R.id.txtLabelResultEnd);
		txtLabelResultEnd.setTypeface(getTypeFaceFont());

		BtnMenu = (Button) findViewById(R.id.btnMenu);
		BtnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ResultFragment.this.finish();
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));	
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		result = (Result) getIntent().getSerializableExtra("result");
		txtNameResult.setText(result.getNameModelPath());
		txtDescResult.setText(result.getResultDescriptionPath());
		txtLabelResultFinal.setText(result.getResultPath());
		txtLabelResultEnd.setText(result.getResultMessageLowerPath());
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
		//imgViewCalca.setBackgroundResource(result.getImgCalcesResultPath());
		BtnMenu.setText(R.string.button_menu);
		BtnMenu.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());
		copyPrivateRawResourceToPubliclyAccessibleFile();
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String cargarIdioma = sharedPref.getString("language_list_preference",
				"");
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
		
		int puntuacio = (Integer) getIntent().getSerializableExtra("resultValue");
		if(puntuacio == 2){
			imgViewCalca.setBackgroundResource(R.drawable.advance_calces);
		}else if(puntuacio == 3){
			imgViewCalca.setBackgroundResource(R.drawable.sexmachine_calces);	
		}else{
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
		actionProvider
				.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);

		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String userName = sharedPref.getString("username_preference", "");
		Object[] arg = { userName,
				getResources().getString(result.getNameModelPath()),
				getResources().getString(result.getResultPath()) };

		String result = String.format(
				getResources().getString(R.string.result_share), arg);
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		Uri uri = Uri.fromFile(getFileStreamPath(SHARED_FILE_NAME));
		//shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.putExtra(Intent.EXTRA_TEXT, result);
		
		actionProvider.setShareIntent(shareIntent);
		
//		share();

		return true;
	}
	
	/**
	 * To share photo with text on facebook
	 * 
	 * @param nameApp
	 * @param imagePath
	 */
	private void share() {
		try {
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.girl1);
			List<Intent> targetedShareIntents = new ArrayList<Intent>();
			Intent share = new Intent(android.content.Intent.ACTION_SEND);
			share.setType("image/*");
			List<ResolveInfo> resInfo = getPackageManager()
					.queryIntentActivities(share, 0);
			if (!resInfo.isEmpty()) {
				for (ResolveInfo info : resInfo) {
					Intent targetedShare = new Intent(
							android.content.Intent.ACTION_SEND);
					targetedShare.setType("image/*"); // put here your mime type

						targetedShare.putExtra(Intent.EXTRA_SUBJECT,
								"Sample Photo");
						targetedShare.putExtra(Intent.EXTRA_TEXT,
								"This photo is created by App Name");
						targetedShare.putExtra(Intent.EXTRA_STREAM,
								Uri.fromFile(getFileStreamPath("girl1.png")));
						targetedShare.setPackage(info.activityInfo.packageName);
						targetedShareIntents.add(targetedShare);
					
				}
				Intent chooserIntent = Intent.createChooser(
						targetedShareIntents.remove(0), "Select app to share");
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
						targetedShareIntents.toArray(new Parcelable[] {}));
				startActivity(chooserIntent);
			}
		} catch (Exception e) {
//			Log.v("VM",
//					"Exception while sending image on" + nameApp + " "
//							+ e.getMessage());
		}
	}

	/**
	 * Creates a sharing {@link Intent}.
	 * 
	 * @return The sharing intent.
	 */
	private Intent createShareIntent(String label) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/jpeg");
		Uri uri = Uri.fromFile(getFileStreamPath(SHARED_FILE_NAME));
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		return shareIntent;
	}

	/**
	 * Copies a private raw resource content to a publicly readable file such
	 * that the latter can be shared with other applications.
	 */
	private void copyPrivateRawResourceToPubliclyAccessibleFile() {
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = getResources().openRawResource(R.drawable.girl1);
			outputStream = openFileOutput(SHARED_FILE_NAME,
					Context.MODE_WORLD_READABLE | Context.MODE_APPEND);
			byte[] buffer = new byte[1024];
			int length = 0;
			try {
				while ((length = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
			} catch (IOException ioe) {
				/* ignore */
			}
		} catch (FileNotFoundException fnfe) {
			/* ignore */
		} finally {
			try {
				inputStream.close();
			} catch (IOException ioe) {
				/* ignore */
			}
			try {
				outputStream.close();
			} catch (IOException ioe) {
				/* ignore */
			}
		}
	}

	@Override
	public void onBackPressed() {
		ResultFragment.this.finish();
		startActivity(new Intent(getApplicationContext(),
				MainActivity.class));	
	}

	private Typeface getTypeFaceFont() {
		return new Utils(getBaseContext()).getTypeFaceFont();
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
