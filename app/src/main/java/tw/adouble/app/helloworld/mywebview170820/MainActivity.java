package tw.adouble.app.helloworld.mywebview170820;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webview);

    }

    public void test1(View view){

    }

    public void test2(View view){

    }

    public void test3(View view){

    }

    public void test4(View view){

    }
}
