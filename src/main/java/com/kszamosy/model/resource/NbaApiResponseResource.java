package com.kszamosy.model.resource;

import lombok.Data;

import java.util.List;

@Data
public class NbaApiResponseResource<T extends NbaApiResponseBody> {

    private List<T> data;
    private NbaApiResponseMetaResource meta;
}
