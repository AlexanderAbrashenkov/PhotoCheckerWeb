package com.photochecker.apache_poi;

/**
 * Created by market6 on 13.01.2017.
 */
public class ApachePoiManager {
    private static ApachePoi apachePoi;

    public static void createApachePoi (int reportNumber) {
        switch (reportNumber) {
            case 1: apachePoi = new ApachePoiLkaDmp(); break;
            case 2: apachePoi = new ApachePoiNkaMlka(); break;
            //case 2: apachePoi = new ApachePoiNkaDmp(); break;
            //case 3: apachePoi = new ApachePoiNst(); break;
            case 5: apachePoi = new ApachePoiLka(); break;
        }
    }

    public static ApachePoi getInstance() {
        return apachePoi;
    }
}
