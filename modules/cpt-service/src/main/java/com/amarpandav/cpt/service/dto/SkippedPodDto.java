package com.amarpandav.cpt.service.dto;

import java.util.List;

/**
 * A pod whose assignments were inside an unassign range but were left untouched, because only the pod's own
 * pod leads may remove them.
 */
public record SkippedPodDto(
        String podId,
        String podName,
        String podShortName,
        List<String> podLeadNames
) {
}
