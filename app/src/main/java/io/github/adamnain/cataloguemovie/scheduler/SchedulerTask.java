package io.github.adamnain.cataloguemovie.scheduler;


import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedulerTask {
    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(io.github.adamnain.cataloguemovie.scheduler.SchedulerService.class)
                .setPeriod(60*1000)
                .setFlex(20)
                .setTag(io.github.adamnain.cataloguemovie.scheduler.SchedulerService.TAG_RELEASED_TODAY)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask() {
        if (mGcmNetworkManager != null) {
            mGcmNetworkManager.cancelTask(io.github.adamnain.cataloguemovie.scheduler.SchedulerService.TAG_RELEASED_TODAY, io.github.adamnain.cataloguemovie.scheduler.SchedulerService.class);
        }
    }
}

