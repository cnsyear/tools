package com.cnsyear;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock锁
 * 
 * @author jiebaobao
 *
 */
public class TestLock2 {
	public static void main(String[] args) {
		Ticket2 ticket = new Ticket2();
		for (int i = 0; i < 500; i++) {
			new Thread(ticket).start();
		}
	}
}

/**
 * 卖票
 * 
 * @author jiebaobao
 *
 */
class Ticket2 implements Runnable {
	private Lock lock = new ReentrantLock();// 创建锁

	private int tick = 100;

	@Override
	public void run() {
		lock.lock();//上锁
		try {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (tick > 0) {
				System.out.println("开始卖票，剩余" + --tick);
			}
		} finally {
			lock.unlock();//释放锁
		}

	}

}
