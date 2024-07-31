package com.ubs.tools.cpt.web.test;

import java.util.UUID;

public class AuraUuidGenerator {
    public static String generateAuraUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
