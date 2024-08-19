package com.ubs.cpt.infra.domain;

import java.util.UUID;

/**
 * @author Amar Pandav
 */
public class UUIDGenerator {

    public static String next() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(); // replace "-" 36 -> 32 char
    }
}
