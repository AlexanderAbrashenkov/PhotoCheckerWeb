package com.photochecker.model;

import java.sql.SQLException;

/**
 * Created by market6 on 11.05.2017.
 */
public class PersistException extends Exception {

    public PersistException(Exception e) {
        super(e);
    }
}
