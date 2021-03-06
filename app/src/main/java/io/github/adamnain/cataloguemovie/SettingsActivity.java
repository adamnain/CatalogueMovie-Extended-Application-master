package io.github.adamnain.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.ButterKnife;
import io.github.adamnain.cataloguemovie.scheduler.AlarmReceiver;


/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */

public class SettingsActivity extends AppCompatActivity {

    private AlarmReceiver alarmReceiver = new AlarmReceiver();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

        public MyPreferenceFragment(){

        }


        @BindString(R.string.key_daily_reminder)
        String reminder_daily;

        @BindString(R.string.key_released_today)
        String released_today;


        @BindString(R.string.key_settings_locale)
        String setting_locale;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            ButterKnife.bind(this, getActivity());

            findPreference(reminder_daily).setOnPreferenceChangeListener(this);
            findPreference(released_today).setOnPreferenceChangeListener(this);
            findPreference(setting_locale).setOnPreferenceClickListener(this);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String key = preference.getKey();
            boolean isOn = (boolean) o;

            if (key.equals(reminder_daily)) {
                if (isOn) {
                    alarmReceiver.setRepeatingAlarm(getActivity(), alarmReceiver.TYPE_REPEATING, "07:00", getString(R.string.label_alarm_daily_reminder));
                } else {
                    alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.TYPE_REPEATING);
                }

                Toast.makeText(SettingsActivity.this, getString(R.string.label_daily_reminder) + " " + (isOn ? getString(R.string.label_activated) : getString(R.string.label_deactivated)), Toast.LENGTH_SHORT).show();
                return true;
            }

            else if (key.equals(released_today)) {
                if (isOn) {
                    alarmReceiver.setRepeatingAlarm(getActivity(), alarmReceiver.TYPE_RELEASED, "08:00", getString(R.string.label_alarm_released_today));
                }
                else{
                    alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.TYPE_RELEASED);
                }

                Toast.makeText(SettingsActivity.this, getString(R.string.label_daily_reminder) + " " + (isOn ? getString(R.string.label_activated) : getString(R.string.label_deactivated)), Toast.LENGTH_SHORT).show();
                return true;
            }


            return false;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();

            if (key.equals(setting_locale)) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                finish();

                return true;
            }

            return false;
        }
    }

}