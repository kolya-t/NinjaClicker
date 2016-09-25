package com.eninja.ninjaclicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player {

    /** Объект класса */
    private GameView view;

    /** Спрайт игрока */
    private Bitmap bmp;

    /** Положение игрока в пространстве */
    private int x ;

    private int y;

    /** Размеры игрока */
    private int width;

    private int height;

    /** Конструктор */
    public Player(GameView view, Bitmap bmp) {
        this.view = view;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.x = 0;
        this.y = (view.getHeight() - height) / 2;
    }

    /** Обновление положения игрока
     * (он не двигается) */
    protected void update() {
        // nothing
    }

     /** Отрисовка игрока */
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, x, y, null);
    }
}
