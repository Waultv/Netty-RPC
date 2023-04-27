package com.xuzc.netty.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.xuzc.netty.util.Response;


public class DefaultFuture {
	public final static ConcurrentHashMap<Long, DefaultFuture>ALL_DEFAULT_FUTURE = new ConcurrentHashMap<Long,DefaultFuture>();
	final Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private Response response;
	
	public DefaultFuture(ClientRequest request) {
		ALL_DEFAULT_FUTURE.put(request.getId(), this);
	}
	public Response get() {
		lock.lock();
		try {
			while(!done()) {
				condition.await();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		return this.response;
	}
	public static void recive(Response response) {
		DefaultFuture df = ALL_DEFAULT_FUTURE.get(response.getId());
		if(df !=null) {
			Lock lock = df.lock;
			lock.lock();
			try {
				df.setResponse(response);
				df.condition.signal();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	private boolean done() {
		if(this.response == null) {
			return true;
		}
		return false;
	}

}
