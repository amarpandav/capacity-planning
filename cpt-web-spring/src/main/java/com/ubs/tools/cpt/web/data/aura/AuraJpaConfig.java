package com.ubs.tools.cpt.web.data.aura;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@ConfigurationProperties("aura.jpa")
@Data
public class AuraJpaConfig {
    private String ddlAuto;
    private String[] mappingResources;

    public Map<String, ?> emfProperties() {
        return Stream
            .of(
                Pair.of("jakarta.persistence.schema-generation.database.action", ddlAuto)
            )
            .filter(p -> p.getValue() != null)
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
