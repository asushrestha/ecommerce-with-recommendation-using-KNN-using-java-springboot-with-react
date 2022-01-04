package nce.majorproject.util;

import nce.majorproject.exception.RestException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static LocalDate stringToDate(String inputDate){

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


            //String dateString = format.format(new Date());
            Date date = format.parse(inputDate);
            return   date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }catch(ParseException pe) {
            pe.printStackTrace();
        }
        return LocalDate.now();
    }
    public static LocalDateTime stringToDateTime(String stringDate){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return LocalDateTime.parse(stringDate, formatter);
        } catch (Exception e) {
            throw new RestException("Wrong Date Format");
        }
    }
    public static Long getAge(LocalDate dob){
        return  Long.valueOf(Period.between(  dob,LocalDate.now()  ).getYears());

    }


}
