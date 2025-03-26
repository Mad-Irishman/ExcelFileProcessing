/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vl.utils;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author nblitenko
 */
public class MapList<K, V> extends HashMap<K, ArrayListV<V>>
{
    public void addToList(K key, V value)
    {
        ArrayListV<V> aArrayListV = getOrCreateList(key);
        aArrayListV.add(value);
    }

    public void addToList(K key, ArrayListV<V> values)
    {
        ArrayListV<V> aArrayListV = getOrCreateList(key);
        aArrayListV.addAll(values);
    }
    public ArrayListV<V> getAllValues()
    {
        ArrayListV<V> aRes = new ArrayListV<>();
        Collection<ArrayListV<V>> aValues = this.values();
        for(ArrayListV<V> aArrayListV : aValues)
            aRes.addAll(aArrayListV);
        return aRes;
    }

    public ArrayListV<V> getList(K key)
    {
        return get(key);
    }
    public ArrayListV<V> getOrCreateList(K key)
    {
        ArrayListV<V> aRes = get(key);
        if (aRes == null)
        {
            aRes = new ArrayListV<>();
            put(key, aRes);
        }
        return aRes;
    }
    
}
