package com.cnsyear;

import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 闭锁： 当完成某种运算时，只有当所有的线程都结束才执行；
 * 
 * @author jiebaobao
 *
 */
public class CountDownLatchTest {
	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(500);//线程数必须和初始的数一致
		MyLatch myLatch = new MyLatch(latch);
		long start = System.currentTimeMillis();

		for (int i = 0; i < 500; i++) {
			new Thread(myLatch).start();
		}

		try {
			latch.await();// 等待
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println("总耗时：" + (end - start));

	}
}

/**
 * 子线程
 * 
 * @author jiebaobao
 *
 */
class MyLatch implements Runnable {
	private CountDownLatch latch;

	public MyLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		synchronized (this) {// 防止并发操作
			try {
				Thread.sleep(20);
				System.out.println(Thread.currentThread().getName() + ">>>> 运算完成");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// 没执行完一个就-1
				latch.countDown();
			}
		}

	}

}
