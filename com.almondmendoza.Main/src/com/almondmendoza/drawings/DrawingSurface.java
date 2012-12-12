package com.almondmendoza.drawings;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: almondmendoza
 * Date: 07/11/2010
 * Time: 2:15 AM
 * Link: http://www.tutorialforandroid.com/
 */
public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {
    private Boolean _run;
    protected DrawThread thread;

    private CommandManager commandManager;

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);


        commandManager = new CommandManager();
        thread = new DrawThread(getHolder());
    }


    class DrawThread extends  Thread{
        private SurfaceHolder mSurfaceHolder;


        public DrawThread(SurfaceHolder surfaceHolder){
            mSurfaceHolder = surfaceHolder;

        }

        public void setRunning(boolean run) {
            _run = run;
        }


        @Override
        public void run() {
            Canvas canvas = null;
            while (_run){
                try{
                    canvas = mSurfaceHolder.lockCanvas(null);
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);

                    commandManager.executeAll(canvas);
                } finally {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }


    }


    public void addDrawingPath (DrawingPath drawingPath){
        commandManager.addCommand(drawingPath);
    }

    public boolean hasMoreRedo(){
        return commandManager.hasMoreRedo();
    }

    public void redo(){
        commandManager.redo();
    }

    public void undo(){
        commandManager.undo();
    }

    public boolean hasMoreUndo(){
        return commandManager.hasMoreRedo();
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width,  int height) {
        // TODO Auto-generated method stub
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }

}
