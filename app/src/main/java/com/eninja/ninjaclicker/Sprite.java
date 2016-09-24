package com.eninja.ninjaclicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Sprite {

    /** Рядов в спрайте */
    private static final int BMP_ROWS = 4;

    /** Колонок в спрайте */
    private static final int BMP_COLUMNS = 3;

    /** Объект класса */
    private GameView view;

    /** Картинка */
    private Bitmap bmp;

    /** Текущее положение */
    private int x;

    private int y;

    /** Скорость передвижения */
    private int xSpeed;

    private int ySpeed;

    /** Текущий кадр */
    private int currentFrame = 0;

    /** Ширина */
    private int width;

    /** Высота */
    private int height;

    /** Конструктор */
    public Sprite(GameView view, Bitmap bmp) {
        this.view = view;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;

        Random rnd = new Random();

        this.x = rnd.nextInt(view.getWidth() - width);
        this.y = rnd.nextInt(view.getHeight() - height);
        this.xSpeed = rnd.nextInt(10) - 5;
        this.ySpeed = rnd.nextInt(10) - 5;
    }

    /** Передвижение объекта */
    private void update() {
        if (x > view.getWidth() - width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;
        }
        x += xSpeed;

        if (y > view.getHeight() - height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
        }
        y += ySpeed;

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    /** Отрисовка спрайтов */
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private int getAnimationRow() {
        // direction 0 = up, 1 = left, 2 = down, 3 = right
        // animation 3 = up, 1 = left, 0 = down, 2 = right
        int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};
        double dirDouble = Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2;
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }
}
