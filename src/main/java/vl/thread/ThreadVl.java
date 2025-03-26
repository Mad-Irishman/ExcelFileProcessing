/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vl.thread;

/**
 *
 * @author vlitenko
 */
public abstract class ThreadVl extends Thread
{
    private static long ID = 0;
    protected long idVl = ID++;
    
    public static final String STATE_NEW = "new";    
    public static final String STATE_RUNNINGH = "running";    
    public static final String STATE_FINISHED = "finished";    
    protected String stateVl = STATE_NEW;
    
    @Override
    public void run()
    {
        stateVl = STATE_RUNNINGH;
        runVl();
        stateVl = STATE_FINISHED;
    }
    
    protected abstract void runVl();

    public long getIdVl() {
        return idVl;
    }

    public boolean isRunning() {
        return STATE_RUNNINGH.equals(stateVl);
    }
    public boolean isFinished() {
        return STATE_FINISHED.equals(stateVl);
    }
    
    
    
}
