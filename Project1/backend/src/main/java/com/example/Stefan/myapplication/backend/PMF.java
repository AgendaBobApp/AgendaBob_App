package com.example.Stefan.myapplication.backend;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Created by Stefan on 5/24/2016.
 */
public class PMF {
    private static final PersistenceManagerFactory pmfInstance = JDOHelper
            .getPersistenceManagerFactory("transactions-optional");

    public static PersistenceManagerFactory getPMF() {
        return pmfInstance;
    }

    private PMF() {
    }
}
