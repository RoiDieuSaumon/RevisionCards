package com.saumon.revisioncards.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class FillDatabaseTask extends AsyncTask<Context, Void, Void> {
    public interface Listeners {
        void onPreExecute();
        void doInBackground();
        void onPostExecute(Void aVoid);
    }

    private final WeakReference<Listeners> callback;

    public FillDatabaseTask(Listeners callback) {
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.get().onPreExecute();
    }

    @Override
    protected Void doInBackground(Context... contexts) {
        callback.get().doInBackground();
        DatabaseUtils.fillDatabase(contexts[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.get().onPostExecute(aVoid);
    }
}
