package com.photochecker.service.common;

import java.io.BufferedReader;

public interface UploadService {
    public String uploadDatas(BufferedReader reader, String date);
}
