package com.hyf.ActualCombat9;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/12
 */

public class TaskThreadPool {

    public static final TaskThreadPool INSTANCE  = new TaskThreadPool();
    private final ThreadPoolExecutor executor;
    private TaskThreadPool(){
        /*ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);*/

        this.executor = new ThreadPoolExecutor(10,
                                            20,
                                            60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),new ThreadPoolExecutor.CallerRunsPolicy());
    }
    public Future submit(Runnable task){
        Future future = executor.submit(task);
        return future;
    }
}
