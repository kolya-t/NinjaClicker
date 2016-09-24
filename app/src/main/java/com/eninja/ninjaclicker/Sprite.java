package com.eninja.ninjaclicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {

    /** Объект класса */
    private GameView view;

    /** Картинка */
    private Bitmap bmp;

    /** Текущее положение */
    private int x = 5;

    private int y = 5;

    /** Скорость передвижения */
    private int xSpeed = 5;

    private int ySpeed = 5;

    /** Текущий кадр */
    private int currentFrame = 0;

    /** Ширина */
    private int width;

    /** Высота */
    private int height;

    /** Рядов в спрайте */
    private static final int BMP_ROWS = 4;

    /** Колонок в спрайте */
    private static final int BMP_COLUMNS = 3;

    /** Конструктор */
    public Sprite(GameView view, Bitmap bmp) {
        this.view = view;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
    }

    /** Передвижение объекта */
    private void update() {
        if (x > view.getWidth() - width - xSpeed) {
            xSpeed = -xSpeed;
        }
        if (x + xSpeed < 0) {
            xSpeed = -xSpeed;
        }
        x += xSpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    /** Отрисовка спрайтов */
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = 1 * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }
}
