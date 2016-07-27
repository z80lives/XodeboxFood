package xodebox.food.common.threads;

import android.os.AsyncTask;
import android.util.Log;

import xodebox.food.common.DataLoadCallback;
import xodebox.food.common.models.BaseModel;

/**
 * Created by shath on 7/27/2016.
 * TODO JavaDoc; Not implemented yet
 */
public class DataLoader extends AsyncTask<BaseModel, Integer, Boolean> {
    private static final String TAG = "DataLoader";

    DataLoadCallback dataLoadCallback;

    public DataLoader(DataLoadCallback callback) {
        super();
        dataLoadCallback = callback;
    }

    @Override
    protected Boolean doInBackground(BaseModel... params) {
        try{
            Thread.sleep(5);
        }catch (Exception ex){
            Log.e(TAG, "doInBackground: ", ex);
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        //dataLoadCallback.onDataLoadSucess();
        if(result)
            dataLoadCallback.onDataLoadSucess();
        dataLoadCallback.onDataLoadFailure();
    }
}
