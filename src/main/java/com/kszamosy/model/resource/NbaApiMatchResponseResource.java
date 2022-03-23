package com.kszamosy.model.resource;

import lombok.Data;

import java.util.List;

@Data
public class NbaApiMatchResponseResource  {

    private List<NbaMatchResource> data;

}
