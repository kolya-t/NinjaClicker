package com.eninja.ninjaclicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameView extends SurfaceView {

    /** Список спрайтов */
    private ArrayList<Sprite> sprites = new ArrayList<>();

    /** Поле рисования */
    private SurfaceHolder holder;

    /** Объект класса для рисования */
    private GameManager gameLoopThread;
    private long lastClick;

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

    /** Передвижение всех объектов */
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

    /** Создание спрайтов на игровом поле */
    private void createSprites() {
        for (int i = 0; i < 10; i++) {
            sprites.add(createSprite(R.drawable.player));
        }
    }

    /** Обработка касания по экрану */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 300) {
            lastClick = System.currentTimeMillis();

            // Точка касания
            float x = event.getX();
            float y = event.getY();

            synchronized (getHolder()) {
                Iterator<Sprite> iterator = sprites.iterator();
                while (iterator.hasNext()) {
                    Sprite sprite = iterator.next();
                    if (sprite.isCollision(x, y)) {
                        iterator.remove();
                        break; // удаляется только один персонаж под пальцем
                    }
                }
            }

        }
        return true;
    }
}
