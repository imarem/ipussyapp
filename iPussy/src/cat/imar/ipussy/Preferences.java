package cat.imar.ipussy;

import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;

import cat.imar.ipussy.model.PussyModel;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class Preferences extends SherlockPreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	

	@Override
	protected void onStart() {
		super.onStart();
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
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
		
		getPreferenceManager().findPreference("volumen_prefernce")
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						if (((CheckBoxPreference) preference).isChecked()) {

						} else {
							
						}
						return true;
					}
				});
		getPreferenceManager().findPreference("language_list_preference")
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
					
					@Override
					public boolean onPreferenceChange(Preference preference, Object newValue) {
						String cargarIdioma  = (String) newValue;
						Locale locale = new Locale(cargarIdioma);
						Locale.setDefault(locale);
						Configuration configuracion = new Configuration();
						configuracion.locale = locale;
						getBaseContext().getResources().updateConfiguration(configuracion, getBaseContext().getResources().getDisplayMetrics());
						return true;
					}
				});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			String mode = (String) getIntent()
			.getSerializableExtra("mode");
			if(mode.equals("MainActivity")){
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));	
			}else{
				startActivity(new Intent(getApplicationContext(),
						ConfirmStartFragment.class).putExtra("bean", getIntent()
								.getSerializableExtra("bean")));	
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		finish();
		String mode = (String) getIntent()
		.getSerializableExtra("mode");
		if(mode.equals("MainActivity")){
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));	
		}else{
			startActivity(new Intent(getApplicationContext(),
					ConfirmStartFragment.class).putExtra("bean", getIntent()
							.getSerializableExtra("bean")));	
		}
			
	}
}
