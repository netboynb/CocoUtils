package com.coco.utils;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author wanglin/netboy
 * @version 创建时间：2016年08月15日 下午8:30:46
 * @func 单实例模式 根据线程池名字 获取全局同一个线程池
 */
public class ThreadPoolHolder {
    private static volatile Map<String, ExecutorService> map;
    private static volatile ThreadPoolHolder threadPoolHolder;
    private static ReentrantLock lock;

    private ThreadPoolHolder() {}

    /**
     * 创建定时执行的线程池
     */
    public static ScheduledExecutorService createScheduledPool(int corePoolSize, String threadName, boolean isDaemon) {
        ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").setDaemon(isDaemon).build();
        return Executors.newScheduledThreadPool(corePoolSize, threadFactory);
    }

    /**
     * 创建指定线程数目的线程池
     */
    public static ExecutorService createFixedPool(int corePoolSize, String threadName, boolean isDaemon) {
        ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").setDaemon(isDaemon).build();
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), threadFactory);
    }


    public static ExecutorService getFixedPool(String poolName, Integer min, Integer max) {
        if (threadPoolHolder == null) {
            synchronized (ThreadPoolHolder.class) {
                if (threadPoolHolder == null) {
                    threadPoolHolder = new ThreadPoolHolder();
                    map = Maps.newHashMap();
                    lock = new ReentrantLock();
                }
            }
        }
        ExecutorService service = map.get(poolName);
        if (service == null) {
            lock.lock();
            if (service == null) {
                try {
                    int min2 = Runtime.getRuntime().availableProcessors();
                    int max2 = min2 * 2 + 1;
                    if (min == null || min < min2) {
                        min = min2;
                    }
                    if (max == null || max < max2) {
                        max = max2;
                    }
                    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(poolName + "-%d").build();
                    service = new ThreadPoolExecutor(min, max, 100, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(100), threadFactory, new CallerRunsPolicy());
                    map.put(poolName, service);
                } finally {
                    lock.unlock();
                }
            }

        }
        return service;
    }

    public static ExecutorService getScheduledPool(String poolName, Integer corePoolSize, boolean isDaemon) {
        if (threadPoolHolder == null) {
            synchronized (ThreadPoolHolder.class) {
                if (threadPoolHolder == null) {
                    threadPoolHolder = new ThreadPoolHolder();
                    map = Maps.newHashMap();
                    lock = new ReentrantLock();
                }
            }
        }
        ExecutorService service = map.get(poolName);
        if (service == null) {
            lock.lock();
            if (service == null) {
                try {
                    int min = Runtime.getRuntime().availableProcessors();
                    corePoolSize = (corePoolSize == null || corePoolSize < min) ? min * 2 : corePoolSize;
                    ThreadFactory threadFactory =
                            new ThreadFactoryBuilder().setNameFormat(poolName + "-%d").setDaemon(isDaemon).build();
                    service = Executors.newScheduledThreadPool(corePoolSize, threadFactory);
                    map.put(poolName, service);
                } finally {
                    lock.unlock();
                }
            }
        }
        return service;
    }
}
