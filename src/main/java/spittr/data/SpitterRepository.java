package spittr.data;


import spittr.Spitter;

public interface SpitterRepository {

    // returns a spitter with an assigned id
    Spitter save(Spitter spitter);

    // use to find user to display his profile
    Spitter findByUsername(String user);
}
