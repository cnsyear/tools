package com.cnsyear;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁==乐观锁
 * --- 写一次只能有一个，读可以有多个
 * 
 * 写写/读写“需要互斥---一次只能有一个”
 * 
 * 读读 不需要互斥
 * 
 * 
 * 
 * @author jiebaobao
 *
 */
public class TestReadWriteLock {
	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 td.set((int)(Math.random()*101));
				
			}
		},"Write:").start();
		
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 td.get();
					
				}
			},"Read:").start();
		}
		
	}
}

class ThreadDemo{
	
	private int num = 0;
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();//读写锁
	
	//读
	public void get() {
		lock.readLock().lock();//上锁
		try {
			System.out.println(Thread.currentThread().getName()+":"+num);
		} finally {
			lock.readLock().unlock();//解锁
		}
		
	}
	//写
	public void set(int num) {
		 lock.writeLock().lock();
		 try {
			 this.num = num;
			 System.out.println(Thread.currentThread().getName()+":"+num);
		} finally {
			lock.writeLock().unlock();
		}
	}

	
}

//输出
/*
Read::0
Read::0
Read::0
Write::2
Read::2
Read::2
Read::2
Read::2
Read::2
Read::2
Read::2
*/
