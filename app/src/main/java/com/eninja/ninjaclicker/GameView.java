package com.eninja.ninjaclicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView {

    /** Список спрайтов */
    private ArrayList<Sprite> sprites = new ArrayList<>();

    /** Поле рисования */
    private SurfaceHolder holder;

    /** Объект класса для рисования */
    private GameManager gameLoopThread;

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
                createSprites();
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
    }

    protected void update() {
        for (Sprite sprite : sprites) {
            sprite.update();
        }
    }

    /** Рисуем картинку на черном фоне */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
    }

    /** Метод создания спрайта */
    private Sprite createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp);
    }

    private void createSprites() {
        sprites.add(createSprite(R.drawable.player));
        sprites.add(createSprite(R.drawable.player));
        sprites.add(createSprite(R.drawable.player));
        sprites.add(createSprite(R.drawable.player));
        sprites.add(createSprite(R.drawable.player));
    }
}
