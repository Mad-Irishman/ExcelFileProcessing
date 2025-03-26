/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vl.utils;

import com.ss.ExceptT;
import java.time.LocalDateTime;
import java.time.Month;

/**
 *
 * @author vlitenko
 */
public class UtilsPartByDate 
{
    private final static LocalDateTime VALID_TILL_MIN_D = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0, 0, 0);

    public static final int MONTHES_CODE_LENGTH = 2;
    public static String getString(LocalDateTime p_dDateLocal)
    {
        int iMonthes = 12*(p_dDateLocal.getYear() - VALID_TILL_MIN_D.getYear())
                + (p_dDateLocal.getMonthValue() - VALID_TILL_MIN_D.getMonthValue());
        String sCodeMonth = UtilsNum.getCode(iMonthes);
        while(sCodeMonth.length() < MONTHES_CODE_LENGTH)
            sCodeMonth = '0' + sCodeMonth;
        return sCodeMonth;

    }

    public static LocalDateTime getDateLocal(String p_sCode) throws ExceptT
    {
        if (p_sCode.length() < MONTHES_CODE_LENGTH) {
            throw new ExceptT("ErrC2D01", "Ссылка недействительна", String.format("'%s' has length %d, excepted at least %d",
                    p_sCode, p_sCode.length(), MONTHES_CODE_LENGTH));
        }
        return VALID_TILL_MIN_D.plusMonths((int) UtilsNum.getNum(p_sCode.substring(0, MONTHES_CODE_LENGTH)));
    }

//    private static Calendar VALID_TILL_MIN;
//    static
//    {
//        Calendar xCal = Calendar.getInstance();
//        xCal.set(2024, 1, 1);   // 01.02.2024 - start date
//        VALID_TILL_MIN = (Calendar) xCal.clone();
//    }
//    
//    public static String getString(Date p_dDate)
//    {
//        Calendar xCal = Calendar.getInstance();
//        xCal.setTime(p_dDate);
//        int iMonth = 12*(xCal.get(Calendar.YEAR) - VALID_TILL_MIN.get(Calendar.YEAR))
//                + (xCal.get(Calendar.MONTH) - VALID_TILL_MIN.get(Calendar.MONTH));
//        String sCodeMonth = UtilsNum.getCode(iMonth);
//        if(sCodeMonth.length() < MONTHES_CODE_LENGTH)
//            sCodeMonth = '0' + sCodeMonth;
//        return sCodeMonth;
//    }
//    
//    public static Date getDate(String p_sCode) throws ExceptT
//    {        
//        if (p_sCode.length() < MONTHES_CODE_LENGTH) {
//            throw new ExceptT("ErrC2D01", "Ссылка недействительна", String.format("'%s' has length %d, excepted at least %d",
//                    p_sCode, p_sCode.length(), MONTHES_CODE_LENGTH));
//        }
//        Calendar xCal = (Calendar) VALID_TILL_MIN.clone();
//        int iMonthes = (int) UtilsNum.getNum(p_sCode.substring(0, MONTHES_CODE_LENGTH));
//        xCal.add(Calendar.MONTH, iMonthes);
//        return xCal.getTime();
//    }

        
}
