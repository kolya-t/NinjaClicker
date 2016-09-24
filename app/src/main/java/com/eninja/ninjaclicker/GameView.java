package com.eninja.ninjaclicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

    /** Загружаемая картинка */
    private Bitmap bmp;

    /** Поле рисования */
    private SurfaceHolder holder;

    /** Объект класса для рисования */
    private GameManager gameLoopThread;

    /** Объект класса Sprite */
    private Sprite sprite;

    /** Конструктор */
    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameManager(this);
        holder = getHolder();

        /** Рисуем все объекты и все все */
        holder.addCallback(new SurfaceHolder.Callback() {
            /** Создание области рисования */
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            /** Изменение области рисования */
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

            /** Уничтожение области рисования */
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        sprite = new Sprite(this, bmp);
    }

    /** Рисуем картинку на черном фоне */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        sprite.onDraw(canvas);
    }
}
