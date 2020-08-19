package foodtruckfinder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UserInformation {
    public int dayUserAccessedProgram;
    public String userLastInput;
    public String timeUserAccessedProgram;

    public UserInformation() {
        setCurrentDay();
        setCurrentTime();
    }

    public void setCurrentDay() {
        DayOfWeek day = LocalDate.now().getDayOfWeek();

        /*
         DayOfWeek class starts indexing on Monday(1) and ends on Sunday(7).
         SF Data Socrata API starts indexing on Sunday (0) and ends on  Saturday(6).

         This conditional statement will prevent submitting the wrong day integer value
         to the API.
        */
        if(day.equals(DayOfWeek.SUNDAY)) {
            this.dayUserAccessedProgram = 0;
        } else {
            this.dayUserAccessedProgram = LocalDate.now().getDayOfWeek().getValue();
        }
    }

    public void setCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        this.timeUserAccessedProgram = LocalTime.now().format(formatter);
    }
}
