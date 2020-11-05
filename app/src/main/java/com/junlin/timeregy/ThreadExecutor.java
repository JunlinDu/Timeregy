package com.junlin.timeregy;


import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Note: this class heavily referenced from https://www.youtube.com/watch?v=c43ruIIZAMg&feature=emb_logo
 * This class exists for performance reasons: A disk IO is very slow in comparison to CPU cycles, thus
 * it is preferable to have the database query running on another thread to avoid UI components froze.
 *
 * This class uses a singleton pattern so the database access is ensured to be performed in order.
 * If singleton pattern is not used, a possible consequence would be that two threads running at the
 * same time to access the database resources, which could potentially cause problem.
 * */
public class ThreadExecutor {

    public static final Object OBJECT = new Object();
    private static  ThreadExecutor tInstance;
    private final Executor diskIO;

    private ThreadExecutor(Executor diskIO) { this.diskIO = diskIO;}

    public static ThreadExecutor getInstance() {
        if (tInstance != null) return tInstance;
        synchronized (OBJECT) { tInstance = new ThreadExecutor(Executors.newSingleThreadExecutor());}
        return tInstance;
    }

    public Executor getDiskIO() { return diskIO; }
}
