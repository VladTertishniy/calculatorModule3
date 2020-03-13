package ua.edu.sumdu.nc.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
public class Operation {
    private double one;
    private double two;
    private String operation;

    @Override
    public String toString() {
        return "Operation{" +
                "one=" + one +
                ", two=" + two +
                ", operation='" + operation + '\'' +
                '}';
    }
}
