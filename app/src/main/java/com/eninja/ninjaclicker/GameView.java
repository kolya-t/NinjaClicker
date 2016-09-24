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

    /** Координата движения картинки по x */
    private int x = 0;

    /** Скорость движения картинки по x */
    private int xSpeed = 1;

    /** Конструктор */
    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameManager(this);
        holder = getHolder();
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
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }

    /** Рисуем картинку на черном фоне */
    @Override
    protected void onDraw(Canvas canvas) {
        if (x == getWidth() - bmp.getWidth()) {
            xSpeed = -1;
        }
        if (x == 0) {
            xSpeed = 1;
        }
        x += xSpeed;
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, x, 10, null);
    }
}
