package xodebox.food.common.threads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import xodebox.food.R;

/**
 * Todo: Properly display the progress graphics.
 * Created by shath on 7/16/2016.
 */
public class DownloadImageForView extends AsyncTask<String, Integer, HashMap<ImageView, Bitmap> > {
    private static final String TAG = "DownloadImageForView";
    ProgressBar progressBar;
    private ArrayList<ImageView> imagelst;
    ViewGroup parent = null;
    private Context context;

    public DownloadImageForView(ArrayList<ImageView> imagelst) {
        super();
        this.imagelst = imagelst;
    }

    public DownloadImageForView(ImageView imageview){
        super();
        imagelst = new ArrayList<>();
        imagelst.add(imageview);
    }

    private void init(){
        if (parent == null) {
            return;
        }
        context = parent.getContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ImageView firstItem = imagelst.get(0);

        //Add progress bar only to the first item
        //TODO add progress bar for every item on the download list
        if(firstItem != null) {
            LinearLayout progressContainer;
            parent = (ViewGroup)firstItem.getParent();
            init();
            int index = parent.indexOfChild(firstItem);

            //parent.addView(progressBar = new ProgressBar(firstItem.getContext()) , index);
            parent.addView(progressContainer = new LinearLayout(firstItem.getContext()), index);

            progressContainer.setLayoutParams(firstItem.getLayoutParams());
            //progressContainer.setGravity(RelativeLayout.CENTER_IN_PARENT);
            //progressBar.drawableHotspotChanged(firstItem.getX() + (firstItem.getWidth()/2), firstItem.getY()+ (firstItem.getHeight()/2 ));
            progressContainer.addView(progressBar = new ProgressBar(progressContainer.getContext()));
            progressContainer.setBackgroundResource(R.color.cardview_dark_background);
            progressContainer.setGravity(Gravity.CENTER);
        }
        //Hide all views
        for(ImageView imageView: imagelst)
        {
            imageView.setVisibility(View.INVISIBLE);
           // imageView.setBackgroundResource(R.color.cardview_dark_background);
        }
    }

    @Override
    protected HashMap<ImageView, Bitmap> doInBackground(String... params) {
        int i=0;
        HashMap<ImageView, Bitmap> retMap = new HashMap<>();
        for(ImageView imageview : imagelst)
        {
            try{
                String imageUrl = params[i];
                Bitmap bmp = ImageDownloader.bitmapFromUrl(imageUrl);
                retMap.put(imageview, bmp);
            }catch (Exception ex)
            {
                Log.e(TAG, "doInBackground: "+ ex.getMessage() );
                //Add fallback image
                //Drawable fallbackDrawable = context.getResources().getDrawable(R.drawable.image_placeholder);
                Bitmap fallback = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_placeholder);
                retMap.put(imageview, fallback);
            }
            if(i <= params.length) i++; //Read next parameter, if there is an imageview pending
        }
        return retMap;
    }

    @Override
    protected void onPostExecute(HashMap<ImageView, Bitmap> imageViewBitmapHashMap) {
        super.onPostExecute(imageViewBitmapHashMap);
        //Remove progress bar
        ViewGroup container = (ViewGroup) progressBar.getParent();
        //parent.removeView(progressBar);
        parent.removeView(container);

        //Show images
        for(ImageView imageview : imageViewBitmapHashMap.keySet())
        {
            Bitmap bmp = imageViewBitmapHashMap.get(imageview);
            imageview.setImageBitmap(bmp);
            imageview.setVisibility(View.VISIBLE);
        }
    }
}

class ImageDownloader{
    /**
     * Gets a Bitmap object from URL
     * @param url URL String
     * @return Downloaded image.
     * @throws IOException
     */
    public static Bitmap bitmapFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return x;
    }

    /**
     * Gets a drawable object from URL
     * @param url URL String
     * @return Downloaded image
     * @throws IOException
     */
    public static Drawable drawableFromUrl(String url) throws IOException {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
    }
}