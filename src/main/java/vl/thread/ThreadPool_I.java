/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vl.thread;

/**
 *
 * @author vlitenko
 */
public interface ThreadPool_I 
{
    void onFinished(ThreadInPool finished);
    void submit(ThreadInPool thread);
}
