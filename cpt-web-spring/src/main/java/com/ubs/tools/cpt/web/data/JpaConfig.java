package com.ubs.tools.cpt.web.data;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class JpaConfig {
    private String ddlAuto;
    private String[] mappingResources;

    public Map<String, ?> dataSourceProperties() {
        return Stream
            .of(
                Pair.of("jakarta.persistence.schema-generation.database.action", ddlAuto)
            )
            .filter(p -> p.getValue() != null)
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
