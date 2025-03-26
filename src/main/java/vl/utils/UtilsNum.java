/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vl.utils;

/**
 *
 * @author vlitenko
 */
public class UtilsNum {
    private static final char[] LETTERS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_', '@'};

    public static String getCode(long id)
    {
        StringBuilder sRes = new StringBuilder();
        while(true)
        {
            int iLetter = (int) (id & 63);    // 
            id = id >> 6;
            sRes.append(LETTERS[iLetter]);
            if(id <= 0)
                break;
        }
        return sRes.toString();
    }
    
    private static int getLetterPos(char p_cChar)
    {
        for(int i=0; i<LETTERS.length; i++)
            if(LETTERS[i] == p_cChar)
                return i;
        return -1;
    }
    public static long getNum(String code)
    {
        long lRes = 0L;
        for(char c : code.toCharArray())
            lRes = lRes*LETTERS.length + getLetterPos(c);
        return lRes;
    }
    
    
}
