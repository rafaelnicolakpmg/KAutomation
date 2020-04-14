package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private Date date;

    public DateUtils(){
        this.date = new Date();
    }

    // Setters and Getters

    public Date getDate(String pattern) {
        return this.date;
    }

    public String getDateAsString(String pattern) {
        // Generates new SimpleDateFormat instance
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        // Format the Date with the SimpleDateFormat and stores in a String
        return formatter.format(this.date);
    }

}
