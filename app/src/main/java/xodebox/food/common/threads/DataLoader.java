package xodebox.food.common.threads;

import android.os.AsyncTask;

import xodebox.food.common.DataLoadCallback;
import xodebox.food.common.models.BaseModel;

/**
 * Created by shath on 7/27/2016.
 * TODO JavaDoc; Not implemented yet
 */
public class DataLoader extends AsyncTask<BaseModel, Integer, DataLoadCallback> {
    DataLoadCallback dataLoadCallback;
    public DataLoader(DataLoadCallback callback) {
        super();
        dataLoadCallback = callback;
    }

    @Override
    protected DataLoadCallback doInBackground(BaseModel... params) {

        return null;
    }

    @Override
    protected void onPostExecute(DataLoadCallback callback) {
        super.onPostExecute(callback);
        callback.onDataLoadSucess();
    }
}
