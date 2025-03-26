/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vl.utils;

import java.time.LocalDateTime;

/**
 *
 * @author VLitenko
 */
abstract public class CacheObj
{
    public static final int DEFAULT_VALID_TIME_SEC = 60;
    
    public static final String STATE_LOADED = "STATE_LOADED";
    public static final String STATE_INVALID = "STATE_INVALID";
    public static final String STATE_LOADING = "STATE_LOADING";

    protected String stateCache;
    protected LocalDateTime loadedAt;
    protected LocalDateTime validTill;
    protected int validTimeSec;

    protected CacheObj()
    {
        init(DEFAULT_VALID_TIME_SEC);
    }
    protected CacheObj(int validTimeSec)
    {
        init(validTimeSec);
    }

    private void init(int validTimeSec)
    {
        this.validTimeSec = validTimeSec;
        stateCache = STATE_INVALID;
        loadedAt = null;
        validTill = LocalDateTime.now(); // странный кусок
    }

    protected boolean isValid()
    {
        if(stateCache.equals(STATE_INVALID))
            return false;
        if(LocalDateTime.now().isAfter(validTill))
        {
            stateCache = STATE_INVALID;
            return false;
        }
        return true;
    }

    abstract protected void updateData() throws ExceptCache;

    protected void validate() throws ExceptCache
    {
        if(!isValid())
        {
            updateData();
            loadedAt = LocalDateTime.now();
            validTill = LocalDateTime.now().plusSeconds(validTimeSec);
            stateCache = STATE_LOADED;
        }
    }

}
