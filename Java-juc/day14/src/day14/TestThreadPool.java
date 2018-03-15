package day14;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程的调度
 * 
 * --ScheduledExecutorService 子接口：负责线程的调度 
 * |--ScheduledThreadPoolExecutor ：继承
 * ThreadPoolExecutor， 实现 ScheduledExecutorService
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 *
 * @author jiebaobao
 *
 */
public class TestThreadPool {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

		for (int i = 0; i < 10; i++) {
			Future<Integer> result = scheduledExecutorService.schedule(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					int num = new Random().nextInt(100);
					System.out.println(Thread.currentThread().getName() + "执行。。");
					return num;
				}

			}, 3, TimeUnit.SECONDS);// 每三秒执行

			System.out.println(result.get());
		}

		scheduledExecutorService.shutdown();
	}
}
