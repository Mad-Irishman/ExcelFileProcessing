/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vl.utils;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author nblitenko
 */
public class MapMap<K, K2, V> extends HashMap<K, HashMap<K2, V>>
{
    public void add(K key, K2 key2, V value)
    {
        HashMap<K2, V> mMap = getOrCreate(key);
        mMap.put(key2, value);

    }

    public V get(K key, K2 key2)
    {
        HashMap<K2, V> mMap = get(key);
        if(mMap == null)
            return null;
        return mMap.get(key2);

    }

    public HashMap<K2, V> getMap(K key)
    {
        return get(key);
    }
    public HashMap<K2, V> getOrCreate(K key)
    {
        HashMap<K2, V> mRes = get(key);
        if (mRes == null)
        {
            mRes = new HashMap<>();
            put(key, mRes);
        }
        return mRes;
    }
    
    public MapMap<K, K2, V> newCopy()
    {
        MapMap<K, K2, V> kRes = new MapMap<> ();
        for(K key : keySet())
        {
            HashMap<K2, V> mMapFrom = get(key);
            HashMap<K2, V> mMapTo = new HashMap<>();
            mMapTo.putAll(mMapFrom);
            kRes.put(key, mMapTo);
        }
        return kRes;
    }
    
    public HashSet<K2> getKeys2()
    {
        HashSet<K2> xRes = new HashSet<> ();
        for(K key : keySet())
        {
            HashMap<K2, V> mMap = get(key);
            if(mMap != null)
                xRes.addAll(mMap.keySet());
        }
        return xRes;
    }
}
