package com.coco.utils.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author wanglin/netboy
 * @version 创建时间：2016年10月7日 上午10:38:08
 * @func
 */
public class CocoThreadUtils {
    private ExecutorService cocoLogicPool;
    private ScheduledExecutorService pingService;

    private CocoThreadUtils() {
        cocoLogicPool= createFixedPool(2,16,15,TimeUnit.MINUTES,1024,"coco-pool");
        pingService = createScheduledPool(4, "ping", false);
    }

    public static CocoThreadUtils getInstance() {
        return ThreadPoolManagerHolder.instance;
    }

    private static class ThreadPoolManagerHolder {
        public static CocoThreadUtils instance = new CocoThreadUtils();
    }

    public void submitLogicTask(Runnable task) {
        cocoLogicPool.submit(task);
    }

    public void submitPingTask(Runnable task, int initDelay, int delay, TimeUnit uTimeUnit) {
        pingService.scheduleAtFixedRate(task, initDelay, delay, uTimeUnit);
    }
    /**
     * 创建定时执行的线程池
     */
    private ScheduledExecutorService createScheduledPool(int corePoolSize, String threadName, boolean isDaemon) {
        ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").setDaemon(isDaemon).build();
        return Executors.newScheduledThreadPool(corePoolSize, threadFactory);
    }

    /**
     * 创建指定线程数目的线程池
     */
    private ExecutorService createFixedPool(int corePoolSize, String threadName, boolean isDaemon) {
        ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").setDaemon(isDaemon).build();
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), threadFactory);
    }

    private ExecutorService createFixedPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, int queueSize, String threadName) {
        ThreadFactory threadFactory1 =
                new ThreadFactoryBuilder().setNameFormat("threadName" + "-%d").setDaemon(false).build();
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                new ArrayBlockingQueue<Runnable>(queueSize), threadFactory1, new CallerRunsPolicy());
    }
}
