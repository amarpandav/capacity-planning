/**
 * A pod whose assignments were inside the unassign range but were left untouched; only its pod leads may remove them.
 */
export interface SkippedPodDto {
    podId: string;
    podName: string;
    podShortName: string;
    podLeadNames: string[];
}

/**
 * Response of unassigning a pod: other pods found in the same slots, which were not touched.
 */
export interface UnassignPodResultDto {
    skippedPods: SkippedPodDto[];
}

/**
 * 403 body returned when the acting user is not a pod lead of the pod they tried to change.
 */
export interface NotPodLeadDto {
    code: 'NOT_POD_LEAD';
    message: string;
    podShortName: string;
    podName: string;
    podLeadNames: string[];
}

export function isNotPodLead(error: unknown): error is NotPodLeadDto {
    return (error as NotPodLeadDto)?.code === 'NOT_POD_LEAD';
}
