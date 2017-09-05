package spittr;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

public class Spittle {
    private final Long id;
    private final String message;
    private final Date time;
    private Double latitute;
    private Double longitude;

    public Spittle(String message, Date time) {
        this(message,time,null,null);
    }

    public Spittle(String message, Date time, Double latitute, Double longitude) {
        this.id = null;
        this.message = message;
        this.time = time;
        this.latitute = latitute;
        this.longitude = longitude;
    }

    public Spittle(Long id, String message, Date time, Double latitute, Double longitude) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.latitute = latitute;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public Double getLatitute() {
        return latitute;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object that) {

        /*
        Assists in implementing Object.equals(Object) methods
        helps to built a good equals method, following a set of rules,
        makes sure, equals and hashcode are consistent
        Rule:
        "Two Objects that compare as equals must generate the same hash code,
        but two Objects with the same hash code do not have to be equal"

        as most of the times methods are private we use reflectionEquals,
        which will change the visibility of the fields for the comparison.

        lhs - this, rhs - that
        id, time are the fields to be excluded in the test
         */
        return EqualsBuilder.reflectionEquals(this, that, "id", "time");

    }
    /*
    same as the above method,
    we are doing this so that we can easily test controller handler methods.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id", "time");
    }
}
