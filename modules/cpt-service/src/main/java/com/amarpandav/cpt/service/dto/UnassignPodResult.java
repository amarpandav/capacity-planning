package com.amarpandav.cpt.service.dto;

import java.util.List;

/**
 * Outcome of unassigning a pod: the other pods found in the same slots, which were not touched.
 */
public record UnassignPodResult(List<SkippedPodDto> skippedPods) {
}
