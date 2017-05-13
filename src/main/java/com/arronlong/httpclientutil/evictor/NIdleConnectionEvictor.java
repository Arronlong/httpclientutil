package com.arronlong.httpclientutil.evictor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.http.nio.conn.NHttpClientConnectionManager;
import org.apache.http.util.Args;

/**
 * This class maintains a background thread to enforce an eviction policy for expired / idle
 * persistent connections kept alive in the connection pool.
 *
 */
public final class NIdleConnectionEvictor {

    private NHttpClientConnectionManager connMgr;
    private final ThreadFactory threadFactory;
    private final Thread thread;
    private final long sleepTimeMs;
    private final long maxIdleTimeMs;

    private volatile Exception exception;

    public NIdleConnectionEvictor(
            final NHttpClientConnectionManager connMgr,
            final ThreadFactory threadFactory,
            final long sleepTime, final TimeUnit sleepTimeUnit,
            final long maxIdleTime, final TimeUnit maxIdleTimeUnit) {
        //this.connMgr = Args.notNull(connMgr, "Connection manager");
    	this.connMgr = connMgr;
        this.threadFactory = threadFactory != null ? threadFactory : new DefaultThreadFactory();
        this.sleepTimeMs = sleepTimeUnit != null ? sleepTimeUnit.toMillis(sleepTime) : sleepTime;
        this.maxIdleTimeMs = maxIdleTimeUnit != null ? maxIdleTimeUnit.toMillis(maxIdleTime) : maxIdleTime;
        this.thread = this.threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(sleepTimeMs);
                        connMgr.closeExpiredConnections();
                        if (maxIdleTimeMs > 0) {
                            connMgr.closeIdleConnections(maxIdleTimeMs, TimeUnit.MILLISECONDS);
                        }
                    }
                } catch (Exception ex) {
                    exception = ex;
                }

            }
        });
    }

    public NIdleConnectionEvictor(
            final NHttpClientConnectionManager connMgr,
            final long sleepTime, final TimeUnit sleepTimeUnit,
            final long maxIdleTime, final TimeUnit maxIdleTimeUnit) {
        this(connMgr, null, sleepTime, sleepTimeUnit, maxIdleTime, maxIdleTimeUnit);
    }

    public NIdleConnectionEvictor(
            final NHttpClientConnectionManager connMgr,
            final long maxIdleTime, final TimeUnit maxIdleTimeUnit) {
        this(connMgr, null,
                maxIdleTime > 0 ? maxIdleTime : 5, maxIdleTimeUnit != null ? maxIdleTimeUnit : TimeUnit.SECONDS,
                maxIdleTime, maxIdleTimeUnit);
    }
    
    public NIdleConnectionEvictor(
    		final long maxIdleTime, final TimeUnit maxIdleTimeUnit) {
    	this(null, null,
    			maxIdleTime > 0 ? maxIdleTime : 5, maxIdleTimeUnit != null ? maxIdleTimeUnit : TimeUnit.SECONDS,
    					maxIdleTime, maxIdleTimeUnit);
    }
    
    public NIdleConnectionEvictor setConnMgr(final NHttpClientConnectionManager connMgr){
        this.connMgr = Args.notNull(connMgr, "Connection manager");    	
        return this;
    }

    public void start() {
    	Args.notNull(connMgr, "Connection manager");
        thread.start();
    }

    public void shutdown() {
        thread.interrupt();
    }

    public boolean isRunning() {
        return thread.isAlive();
    }

    public void awaitTermination(final long time, final TimeUnit tunit) throws InterruptedException {
        thread.join((tunit != null ? tunit : TimeUnit.MILLISECONDS).toMillis(time));
    }
    
    public void await() {
    	try {
    		shutdown();
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    static class DefaultThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(r, "Connection evictor");
            t.setDaemon(true);
            return t;
        }

    };


}
