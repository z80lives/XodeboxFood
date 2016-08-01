package xodebox.food.ui.managers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;
import android.widget.ViewSwitcher;

import java.util.HashMap;
import java.util.Map;

/**
 * A view manager using androids View switcher. Use this to for simple view management.
 * TODO Javadoc
 */
public class DefaultViewSwitcher implements XodeboxViewManager {
    ViewAnimator viewSwitcher;
    Context context;
    Map<View, Integer> viewMap = new HashMap<>();
    View readyView, loaderView;

    public DefaultViewSwitcher(Context context) {
        //super();
        this.context = context;
        viewSwitcher = new ViewSwitcher(context);
        setup();
   }

    public DefaultViewSwitcher(Context context, View... views) {
        //super();
        this.context = context;
        viewSwitcher = new ViewSwitcher(context);

        //Add all the views
        int key = 0;
        for(View v: views)
        {
            viewMap.put(v, key);
            viewSwitcher.addView(v, key);
            key++;
        }
        if(views.length > 0) {
            setReadyView(views[0]);
            setLoadView(views[0]);
        }
        if(views.length > 1)
            setLoadView(views[1]);
        setup();
    }

    public void addView(View v){
        viewSwitcher.addView(v);
        viewMap.put(v, viewMap.size());
    }


    private void setup(){
        viewSwitcher = new ViewAnimator(context);

        //set the animations
        Animation fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        viewSwitcher.setInAnimation(fadeIn);
        viewSwitcher.setOutAnimation(fadeOut);

    }

    @Override
    public boolean showView(View v) {
        if( viewMap.containsKey(v))
        {
            int key = viewMap.get(v);
            viewSwitcher.setDisplayedChild(key);
            return true;
        }
        return false;
    }

    @Override
    public View getActiveView() {
        return viewSwitcher.getCurrentView();
    }

    @Override
    public void setLoadView(View v) {
        loaderView = v;
    }

    @Override
    public void setReadyView(View v) {
        readyView = v;
    }

    @Override
    public void setLoading(boolean paramLoading) {
        if(paramLoading){
            showView(loaderView);
        }else
            showView(readyView);
    }

    @Override
    public ViewGroup getRootView() {
        return viewSwitcher;
    }

    //**Use only for debug purpose
    public View getReadyView() {
        return readyView;
    }

    public View getLoaderView() {
        return loaderView;
    }
}
