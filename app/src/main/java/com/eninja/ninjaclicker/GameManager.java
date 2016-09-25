package com.eninja.ninjaclicker;

import android.graphics.Canvas;

public class GameManager extends Thread {

    /** Скорость игры */
    static final double GAME_SPEED = 1.5;

    /** Кадров в секунду */
    static final long FPS = 60;

    /** Обект класса */
    private GameView view;

    /** Переменная задания состояния потока рисования */
    private boolean running = false;

    /** Конструктор класса */
    public GameManager(GameView view) {
        this.view = view;
    }

    /** Задание состояния потока */
    public void setRunning(boolean run) {
        this.running = run;
    }

    /** Действия, выполняемые в потоке */
    @Override
    public void run() {
        double ticksPS = 1000f / FPS;
        long sleepTime = (long) (100 / GAME_SPEED);
        long startTime = System.currentTimeMillis();
        while (running) {
            Canvas canvas = null;
            view.update();
            if (System.currentTimeMillis() - startTime >= ticksPS) {
                try {
                    canvas = view.getHolder().lockCanvas();
                    synchronized (view.getHolder()) {
                        view.onDraw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
                startTime = System.currentTimeMillis();
            }

            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
