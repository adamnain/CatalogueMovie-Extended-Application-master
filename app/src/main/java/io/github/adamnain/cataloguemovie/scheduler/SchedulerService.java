package io.github.adamnain.cataloguemovie.scheduler;
import android.text.format.Time;
import android.widget.Toast;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import java.util.List;
import io.github.adamnain.cataloguemovie.BuildConfig;
import io.github.adamnain.cataloguemovie.R;
import io.github.adamnain.cataloguemovie.model.ResponseMovies;
import io.github.adamnain.cataloguemovie.model.Result;
import io.github.adamnain.cataloguemovie.service.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulerService extends GcmTaskService {
    public static String TAG_RELEASED_TODAY = "released today";
    private AlarmReceiver alarmReceiver = new AlarmReceiver();

    String dateNow;


    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_RELEASED_TODAY)) {
            getUpcomingMovie();
            //Toast.makeText(this,"backround running", Toast.LENGTH_SHORT).show();

            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        else
            Toast.makeText(this,"failed fletch data", Toast.LENGTH_SHORT).show();

        return result;
    }


    public void getUpcomingMovie(){
        final String language = "en-US";
        final String sort_by = "popularity.desc";
        final String include_adult = "false";
        final String include_video = "false";
        final String page = "1";

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        int bulan = today.month+1;
        int hari = today.monthDay;

        if (bulan<10 && hari>9)
            dateNow = today.year + "-0" + bulan + "-" + hari;
        else if (hari<10 && bulan>9)
            dateNow = today.year + "-" + bulan + "-0" + hari;
        else if (bulan<10 && hari<10)
            dateNow = today.year + "-0" + bulan + "-0" + hari;
        else dateNow = today.year + "-" + bulan + "-" + hari;

        //String timeNow = today.format("%k:%M:%S");

        Call<ResponseMovies> call = UtilsApi.getAPIService().getUpcoming(BuildConfig.MOVIE_DB_API, language, sort_by, include_adult, include_video, page);
        call.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()) {
                    List<Result> listMovies = response.body().getResults();
                    for (int i = 0; i < listMovies.size(); i++) {
                        String releaseDate = listMovies.get(i).getReleaseDate();
                        String title = listMovies.get(i).getTitle();
                        if (releaseDate.equals(dateNow))
                            alarmReceiver.setRepeatingAlarm(getApplicationContext(), alarmReceiver.TYPE_REPEATING, "10:00", title + getString(R.string.label_alarm_released_today));
                    }
                }
                else Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
