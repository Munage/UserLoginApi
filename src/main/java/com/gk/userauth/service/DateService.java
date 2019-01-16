package com.gk.userauth.service;

import org.joda.time.DateTime;

/**
 * A service to provide the current date
 */
public interface DateService {

    /**
     * @return current date at the moment of the call
     */
    DateTime now();
}
