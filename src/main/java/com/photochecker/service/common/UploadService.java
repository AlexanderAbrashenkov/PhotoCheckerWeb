package com.photochecker.service.common;

import java.io.BufferedReader;

/**
 * Created by market6 on 23.05.2017.
 */
public interface UploadService {
    public int uploadDatas(BufferedReader reader, String date);
}
