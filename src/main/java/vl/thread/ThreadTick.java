/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vl.thread;

/**
 *
 * @author nblitenko
 */
public abstract class ThreadTick extends ThreadVl
{
    private boolean makeTicks = true;
    private long ticks = 0;   // how many milliseconds thread must sleep
    private final int sleepMillis;   // how many milliseconds thread must sleep

    public ThreadTick(int sleepMillis) {
        this.sleepMillis = sleepMillis;
    }
    
    @Override
    protected void runVl()
    {
        while(makeTicks)
        {
            tick();
            ticks++;
            try{Thread.sleep(sleepMillis);}catch (InterruptedException ex){}
        }
    }
    
    public void stopTicks(){makeTicks = false;}
    
    /**
     * any automatic verifications
     */
    protected abstract void tick();    
}
