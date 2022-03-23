package com.kszamosy.util;

import com.kszamosy.JsonUtil;
import com.kszamosy.model.resource.NbaApiResponseResource;
import com.kszamosy.model.resource.NbaMatchResource;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeserializerUtilTest {

    @Test
    void deserializeSimple() {
        var match = new NbaMatchResource();
        match.setId(6L);

        var deserializedRes = DeserializerUtil.deserializeSimple(
                JsonUtil.serialize(match), NbaMatchResource.class);

        assertNotNull(deserializedRes.getId());
        assertEquals(match.getId(), deserializedRes.getId());
    }

    @Test
    void deserializeWithParam() {
        var res = new NbaApiResponseResource<>();
        var match = new NbaMatchResource();
        match.setId(6L);
        res.setData(List.of(match));

        var deserializedRes = DeserializerUtil.deserializeWithParam(
                JsonUtil.serialize(res), NbaMatchResource.class);

        assertNotNull(deserializedRes.getData().get(0).getId());
        assertFalse(deserializedRes.getData().isEmpty());
        assertEquals(match.getId(), deserializedRes.getData().get(0).getId());
    }
}