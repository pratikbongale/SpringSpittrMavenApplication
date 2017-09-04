package spittr.data;

import spittr.Spittle;

import java.util.List;

public interface SpittleRepository {

    /*
    maxId is the maximum Id of any spittle that is returned
    (used just to make sure we are within bounds)
    Usage:
    List<Spittle> recent =
                spittleRepository.findSpittles(Long.MAX_VALUE, 20);
     */
    List<Spittle> findSpittles(long maxId, int count);
    Spittle findOne(long spittleId);


}
