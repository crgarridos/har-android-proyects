package cl.at.view;

import android.app.Application;
import android.content.Context;

public class AlertTsunamiApplication extends Application{
	
	private static Context context;

    public void onCreate(){
        super.onCreate();
        AlertTsunamiApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AlertTsunamiApplication.context;
    }

}
