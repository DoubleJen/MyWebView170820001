package tw.adouble.app.helloworld.mywebview170820;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText name;
    private LocationManager lmgr;
    private MyListener myListener;
    private MyJS myJS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lmgr = (LocationManager)getSystemService(LOCATION_SERVICE);
        myListener = new MyListener();
        myJS = new MyJS();

//https://developer.android.com/training/permissions/requesting.html
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

        }else{
            init();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }

    private void init(){
        webView = (WebView)findViewById(R.id.webview);
        name = (EditText)findViewById(R.id.name);
        initWebView();
        lmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,myListener);//一進來更新位置GPS


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private class MyListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            webView.loadUrl("javascript: gotoSomeWhere(" + lat + "," + lng + ")");

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lmgr.removeUpdates(myListener);//一離開移除位置GPS解除
    }

    private void initWebView(){
        webView.setWebViewClient(new WebViewClient());

        WebSettings setting = webView.getSettings();//相當瀏覽器按下設定動作
        setting.setJavaScriptEnabled(true);//開啟JS功能
        webView.addJavascriptInterface(myJS, "double");

        //webView.loadUrl("http://www.iii.org.tw");//一般情況外部JS無法載入
        webView.loadUrl("file:///android_asset/brad01.html");
            //第三根/指的是絕對路徑，此招也無須網際網路權限
            //瀏覽器的alert prompt confirm在手機上一般無法呈現
    }

    public void test1(View view){
        //webView.goBack();
        //String myname = name.getText().toString();
        webView.loadUrl("javascript: gotoSomeWhere(21.949037, 120.780106)");
    }

    public void test2(View view){
        webView.goForward();
    }

    public void test3(View view){
        webView.reload();
    }

    public void test4(View view){

    }

    private class MyJS{
        @JavascriptInterface//要給JS使用 必加
        public void m1(String name){
            Log.i("brad", "OK: " + name);

        }

        @JavascriptInterface
        public void alert(String mesg){
            AlertDialog dialog = null;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Mesg");
            builder.setMessage(mesg);
            dialog = builder.create();
            dialog.show();

        }
    }


}
