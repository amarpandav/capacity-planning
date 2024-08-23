package com.ubs.cpt.service.dto;

public record PodDto (
        String uuid,
        String podName,
        String podDescription,
        String podStyleClass
) {
}
