package com.kszamosy.model.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class NbaApiStatResponseResource  {

    private List<NbaPlayerStatResource> data;

}
