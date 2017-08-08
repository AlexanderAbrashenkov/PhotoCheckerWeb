package com.photochecker.apache_poi;

public class ApachePoiManager {
    private static ApachePoi apachePoi;

    public static void createApachePoi (int reportNumber) {
        switch (reportNumber) {
            case 1: apachePoi = new ApachePoiLkaDmp(); break;
            case 2: apachePoi = new ApachePoiNkaMlka(); break;
        }
    }

    public static ApachePoi getInstance() {
        return apachePoi;
    }
}
