/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vl.utils;

import java.util.ArrayList;

/**
 *
 * @author nblitenko
 */
public class ArrayListV<T> extends ArrayList<T>
{
    public T pop()
    {
        int iLastPos = size() - 1;
        if(iLastPos < 0)
            return null;
        T kRes = get(iLastPos);
        remove(iLastPos);
        return kRes;
    }
    
    public static <T> ArrayListV<T> newSingle(T p_kObj)
    {
        ArrayListV<T> kRes = new ArrayListV<T>();
        kRes.add(p_kObj);
        return kRes;
    }
}
