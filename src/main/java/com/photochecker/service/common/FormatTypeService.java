package com.photochecker.service.common;

import com.photochecker.model.common.FormatType;
import com.photochecker.model.common.Lka;

import java.time.LocalDate;
import java.util.List;


public interface FormatTypeService {

    List<FormatType> getAllFormats();
}
