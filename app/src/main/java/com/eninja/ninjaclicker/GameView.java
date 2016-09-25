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
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    /** Поток появления врагов */
    Thread enemiesThread = new Thread(this);

    /** BMP's */
    public Bitmap playerBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ninja);

    public Bitmap bulletBmp = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);

    public Bitmap enemyBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);

    /** Спрайты */
    private Player player;

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Bullet> bullets = new ArrayList<>();

    /** Поле рисования */
    private SurfaceHolder holder;

    /** Объект класса для рисования */
    private GameManager gameLoopThread;

    /** Когда был сделан последний клик */
    private long lastClick;

    /** Промежуток между кликами. Кикать не чаще чем указано */
    private static final long CLICK_DELAY = 100;

    /** Место касания */
    public int shotX;

    public int shotY;

    /** Конструктор */
    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameManager(this);
        enemiesThread.start();
        holder = getHolder();

        /** Рисуем все объекты и все все */
        holder.addCallback(new SurfaceHolder.Callback() {
            /** Создание области рисования */
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                createPlayer();
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
//        player = new Player(this, BitmapFactory.decodeResource(getResources(), R.drawable.ninja));
    }

    /** Передвижение всех объектов */
    protected void update() {
        player.update();

        for (Enemy enemy : enemies) {
            enemy.update();
            // TODO: 025 25.09.16 Collisions
        }

        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (bullet.x < 0 || bullet.x > getWidth() || bullet.y < 0 || bullet.y > getHeight()) {
                iterator.remove();
            }
        }
    }

    /** Рисуем картинку на черном фоне */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        player.onDraw(canvas);

        for (Enemy enemy : enemies) {
            enemy.onDraw(canvas);
        }

        for (Bullet bullet : bullets) {
            bullet.onDraw(canvas);
        }
    }

    /** Создание спрайта игрока на игровом поле */
    private void createPlayer() {
        player = new Player(this, playerBmp);
    }

    /** Обработка касания по экрану */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > CLICK_DELAY) {
            lastClick = System.currentTimeMillis();

            synchronized (getHolder()) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Точка касания
                    shotX = (int) event.getX();
                    shotY = (int) event.getY();

                    bullets.add(new Bullet(this, bulletBmp));
                }
            }
        }
        return true;
    }

    /** Появление врагов */
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(2000));
                enemies.add(new Enemy(this, enemyBmp));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /** Проверка столкновений пуль и врагов */
    public void testCollisions() {
        Iterator<Bullet> bIterator = bullets.iterator();
        while (bIterator.hasNext()) {
            Bullet bullet = bIterator.next();

            Iterator<Enemy> eIterator = enemies.iterator();
            while (eIterator.hasNext()) {
                Enemy enemy = eIterator.next();

                if ((Math.abs(bullet.x - enemy.x) <= (bullet.width / 2 + enemy.width))
                        && (Math.abs(bullet.y - enemy.y) <= (bullet.height / 2 + enemy.height)))
                {
                    eIterator.remove();
                    bIterator.remove();
                }
            }
        }
    }
}
