package com.eninja.ninjaclicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends Activity implements View.OnClickListener {

    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 3000; // время показа сплэш-картинки
    private ImageView splashscreen;

    private Handler splashHandler = new Handler() { // создаем новый хэндлер
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    // убираем картинку
                    splashscreen.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Button btnStart;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);

        splashscreen = (ImageView) findViewById(R.id.splashscreen);
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
    }

    /** Обработчик нажатий на кнопки */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnExit:
                finish();
                break;
            default:
                break;
        }
    }
}
