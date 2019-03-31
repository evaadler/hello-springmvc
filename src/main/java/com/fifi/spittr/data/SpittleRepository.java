package com.fifi.spittr.data;

import java.util.List;

/**
 * @author fifi
 */
public interface SpittleRepository {
    /**
     * find spittles by items number
     *
     * @param max
     * @param count
     * @return
     */
    List findSpittles(long max, int count);
}
