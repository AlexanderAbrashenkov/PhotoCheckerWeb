package com.photochecker.service.nst;

import java.io.BufferedReader;

public interface NstUploadService {
    public String uploadDatas(BufferedReader reader, String dateFrom, String dateTo);
}
