package u.can.i.up.expert;

import android.os.Binder;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class MyLocalBinder extends Binder
{
    MyService myService;
    MyLocalBinder(MyService myService){
        this.myService = myService;
    }
    MyService getService()
    {
        return myService;
    }
}