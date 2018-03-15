package day13;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * 
 * 二、线程池的体系结构：
 * 	java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * 		|--**ExecutorService 子接口: 线程池的主要接口
 * 			|--ThreadPoolExecutor 线程池的实现类
 * 			|--ScheduledExecutorService 子接口：负责线程的调度
 * 				|--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 * 
 * 三、工具类 : Executors 
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 *
 * @author jiebaobao
 *
 */
public class TestThreadPool {
	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();	
		//1.创建线程池
		ExecutorService pool  = Executors.newFixedThreadPool(5);
		//2.为线程池中的线程分配任务
		for (int i = 0; i < 10; i++) {
			pool.submit(td);
		}
		//3.关闭线程池
		pool.shutdown();
		
		
	}
}

/**
 * 线程
 * 
 * @author jiebaobao
 *
 */
class ThreadDemo implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "执行。。");
	}

}


//输出
/*
pool-1-thread-2执行。。
pool-1-thread-5执行。。
pool-1-thread-2执行。。
pool-1-thread-3执行。。
pool-1-thread-3执行。。
pool-1-thread-3执行。。
pool-1-thread-4执行。。
pool-1-thread-1执行。。
pool-1-thread-2执行。。
pool-1-thread-5执行。。*/
