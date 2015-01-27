package u.can.i.up.expert.framework;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2014/10/8.
 */
public class MyLocationListener implements BDLocationListener {

    String str = "";
    int flag = 1;
    double lastLongitude = -1;
    double lastLatitude = -1;

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        DataSet.getInstance().location = bdLocation;
        Log.i("lczLocate", "城市：" + bdLocation.getCity());
        Log.i("lczLocate", "地址：" + bdLocation.getAddrStr());
        Log.i("lczLocate", "城市编码：" + bdLocation.getCityCode());
        Log.i("lczLocate", "区：" + bdLocation.getDistrict());
        Log.i("lczLocate", "街道：" + bdLocation.getStreet());
        Log.i("lczLocate", "街道号码：" + bdLocation.getStreetNumber());
        double currentLongitude = bdLocation.getLongitude();
        double currentLatitude = bdLocation.getLatitude();
        double currentRadius = bdLocation.getRadius();
        Log.i("lczLocate", "经度：" + currentLongitude);
        Log.i("lczLocate", "纬度：" + currentLatitude);
        Log.i("lczLocate", "精度：" + currentRadius);
        int locResult = 0;
        //int locResult = (lastLongitude != -1 && lastLatitude != -1)? locationChangedStatus(currentLongitude, currentLatitude, lastLongitude, lastLatitude) :0;
        switch (locResult){
            case 0:
                lastLongitude = currentLongitude;
                lastLatitude = currentLatitude;
                Factory.updateWithNewLocation(bdLocation);
                break;
            case 1:
                //str += "移动距离过小\n";
                Log.e("lczLocate", "移动距离过小");
                //MyActivity.getInstant().setTextViewContents(str);
                lastLongitude = currentLongitude;
                lastLatitude = currentLatitude;
                break;
            case 2:
                str += "移动距离过大，位置获取失败\n";
                Log.e("lczLocate", "移动距离过大，位置获取失败");
                lastLongitude = currentLongitude;
                lastLatitude = currentLatitude;
                break;
            default:
                break;
        }
    }
    /**@return status
     *  0 -- 位置判定为移动；
     *  1 -- 移动距离过小，属于失败的一种；
     *  2 -- 移动距离过大，属于位置获取失败；
     *      21 -- 经度移动距离过大；
     *      22 -- 纬度移动距离过大
     * */
    private int locationChangedStatus(double cLongitude, double cLatitude, double lLongitude, double lLatitude){
        if(Math.abs(Math.abs(cLongitude) - Math.abs(lLongitude)) > 0.5 || Math.abs(Math.abs(cLatitude) - Math.abs(lLatitude)) > 1){
            return 2;
        }
        if(Math.abs(Math.abs(cLongitude) - Math.abs(lLongitude)) < 0.000005 && Math.abs(Math.abs(cLatitude) - Math.abs(lLatitude)) < 0.000003){
            return 1;
        }
        return  0;
    }
}
