package cat.imar.ipussy;

import java.util.Locale;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cat.imar.ipussy.game.GameActivity;
import cat.imar.ipussy.model.PussyModel;
import cat.imar.ipussy.utils.Utils;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class ConfirmStartFragment extends SherlockActivity {

	private Button btnStart;
	private ImageView imgSelectedConfrim;
	private TextView txtName, txtDesc, txtDetail, txtLabelConfirm;
	private ImageView imgHow;
	private Button btnNext, btnBack;
	private Integer counterHow = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.composite_confirm);

		btnStart = (Button) findViewById(R.id.btnConfirmStart);
		txtName = (TextView) findViewById(R.id.txtNameConfirm);
		txtName.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());
		txtDesc = (TextView) findViewById(R.id.txtDescConfirm);
		txtDesc.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());
		txtDetail = (TextView) findViewById(R.id.txtDetailConfirm);
		txtDetail.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());
		txtLabelConfirm = (TextView) findViewById(R.id.txtLabelConfirm);
		txtLabelConfirm.setTypeface(new Utils(getBaseContext())
				.getTypeFaceFont());
		imgSelectedConfrim = (ImageView) findViewById(R.id.imgSelectedConfrim);

	}

	private void openDialog() {
		final Dialog dialog = new Dialog(ConfirmStartFragment.this);
		dialog.setTitle("How To Play");
		TextView title = ((TextView) dialog.findViewById(android.R.id.title));
		title.setBackgroundColor(getResources().getColor(R.color.masterColor));
		title.setPadding(10, 10, 10, 10);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		title.setTextSize(20);
		title.setTypeface(Typeface.createFromAsset(
				getBaseContext().getAssets(), "fonts/Avantgrade.ttf"));
		dialog.setContentView(R.layout.dialog_how_to_play);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		imgHow = (ImageView) dialog.getWindow().findViewById(R.id.imgHow);
		imgHow.setBackgroundResource(R.drawable.how_1);
		btnBack = (Button) dialog.getWindow().findViewById(R.id.btnHowBack);
		btnBack.setVisibility(View.INVISIBLE);
		btnNext = (Button) dialog.getWindow().findViewById(R.id.btnHowNext);
		counterHow = 0;

		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (counterHow == 0) {
					counterHow = counterHow + 1;
					imgHow.setBackgroundResource(R.drawable.how_2);
					btnBack.setVisibility(View.VISIBLE);
				} else if (counterHow == 1) {
					counterHow = counterHow + 1;
					imgHow.setBackgroundResource(R.drawable.how_to_play);
					btnNext.setText("PLAY");
					imgHow.post(new Runnable() {

						@Override
						public void run() {
							AnimationDrawable frameAnimation = (AnimationDrawable) imgHow
									.getBackground();
							frameAnimation.start();

						}
					});
				} else {
					imgHow.postInvalidate();
					SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
					sharedPref.edit().putBoolean("show_how_to_play_prefernce", false);
					sharedPref.edit().commit();
					dialog.dismiss();
					final Intent intent = new Intent(getBaseContext(),
							GameActivity.class).putExtra(
							"bean",
							(PussyModel) getIntent().getSerializableExtra(
									"bean"));
					startActivity(intent);
					ConfirmStartFragment.this.finish();
				}

			}
		});

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (counterHow == 1) {
					counterHow = counterHow - 1;
					imgHow.setBackgroundResource(R.drawable.how_1);
					btnBack.setVisibility(View.INVISIBLE);
				} else if (counterHow == 2) {
					counterHow = counterHow - 1;
					imgHow.postInvalidate();
					imgHow.setBackgroundResource(R.drawable.how_2);
					btnNext.setText("NEXT");
				}

			}
		});

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				imgHow.postInvalidate();

			}
		});

		dialog.show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setValuesFromPussyModel();

		imgSelectedConfrim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext());
				Boolean show = sharedPref.getBoolean(
						"show_how_to_play_prefernce", true);
				if (show) {
					openDialog();
				} else {
					final Intent intent = new Intent(getBaseContext(),
							GameActivity.class).putExtra(
							"bean",
							(PussyModel) getIntent().getSerializableExtra(
									"bean"));
					startActivity(intent);
					ConfirmStartFragment.this.finish();
				}
			}
		});
		btnStart.setText(R.string.button_start);
		btnStart.setTypeface(new Utils(getBaseContext()).getTypeFaceFont());

	}

	private void setValuesFromPussyModel() {
		// recupetrem l'objecte passat per parametre
		PussyModel model = (PussyModel) getIntent()
				.getSerializableExtra("bean");

		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] { android.R.attr.state_pressed },
				getResources().getDrawable(model.getPathImageEyesClose()));
		states.addState(new int[] {},
				getResources().getDrawable(model.getPathImageEyes()));
		imgSelectedConfrim.setImageDrawable(states);

		txtName.setText(model.getName());
		txtDesc.setText(model.getDescription());
		txtDetail.setText(model.getDetallPussyModelList().size() + "seg.");
		txtLabelConfirm.setText(R.string.select_label);

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

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuItem buttonSettings = menu.add("Settings");
		buttonSettings.setIcon(R.drawable.settings);
		buttonSettings.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		buttonSettings
				.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						ConfirmStartFragment.this.finish();
						startActivity(new Intent(ConfirmStartFragment.this,
								Preferences.class).putExtra("mode",
								"ConfirmStartFragment").putExtra("bean",
								getIntent().getSerializableExtra("bean")));
						return false;
					}
				});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			ConfirmStartFragment.this.finish();
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		ConfirmStartFragment.this.finish();
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}

}
