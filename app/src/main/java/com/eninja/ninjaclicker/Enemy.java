package com.eninja.ninjaclicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Enemy {

    /** Объект класса */
    private GameView view;

    /** Спрайт */
    private Bitmap bmp;

    /** Координаты врага */
    public int x;

    public int y;

    /** Размеры */
    public int width;

    public int height;

    /** Скорость врага */
    private int speed;

    /** Конструктор */
    public Enemy(GameView view, Bitmap bmp) {
        Random rnd = new Random();
        this.view = view;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.x = view.getWidth() - width;
        this.y = rnd.nextInt(view.getHeight() - height);
        this.speed = rnd.nextInt(9) + 1;

    }

    protected void update() {
        x -= speed;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, x, y, null);
    }
}
