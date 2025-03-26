package vl.thread;

/**
 *
 * @author vlitenko
 */
public abstract class ThreadInPool extends ThreadVl {
    
    private final ThreadPool_I pool;

    public ThreadInPool(ThreadPool_I pool) {
        this.pool = pool;
    }
    

    @Override
    protected void runVl() {
        try {
            runVl2();
        } catch (Throwable th) {
            throw th;
        } finally {
            this.pool.onFinished(this);
        }
    }

    protected abstract void runVl2();
    
}
