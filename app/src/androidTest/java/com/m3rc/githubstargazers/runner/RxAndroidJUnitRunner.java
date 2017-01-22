package com.m3rc.githubstargazers.runner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class RxAndroidJUnitRunner extends AndroidJUnitRunner {

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        RxJavaPlugins.setIoSchedulerHandler(handler ->
                Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxJavaPlugins.setIoSchedulerHandler(null);
    }
}
