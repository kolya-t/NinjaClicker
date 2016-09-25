package com.eninja.ninjaclicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bullet {

    /** Объект класса */
    private GameView view;

    /** Спрайт объекта */
    private Bitmap bmp;

    /** Координаты объекта */
    public int x;

    public int y;

    /** Скорость по X */
    private int speed = 25;

    /** Угол полета пули */
    private double angle;

    /** Размеры пули */
    private int width;

    private int height;

    /** Конструктор */
    public Bullet(GameView view, Bitmap bmp) {
        this.view = view;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.x = 0;
        this.y = (view.getHeight() - height) / 2;
        this.angle = Math.atan((double) (y - view.shotY) / (x - view.shotX));
    }

    protected void update() {
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, x, y, null);
    }
}
