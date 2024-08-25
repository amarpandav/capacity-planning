//test data for cpt_user_booked_capacity but grouped by pod_member
import {PodMemberRole} from "../../models/pod/pom-member-role.enum";
import {AvailabilityType} from "../../models/availability/availability.enum";

export const POD_ASSIGNMENT_VIEW_TEST_DATA = { //SchedulerViewDto
    currentPod: {
        uuid: 'AURAPodUuid',
        podName: 'AURA',
        podStyleClass: 'podStyleClass1',
        podDescription: 'This pod is for AURA'
    },
    podAssignmentWrappers: [//PodAssignmentWrapperDto
        {
            user: {//UserDto
                uuid: 'AmarPandavUuid',
                gpin: 'AmarPandavGPIN',
                name: 'Amar Pandav'
            },
            podMemberRole:PodMemberRole.SOLUTIONS_ARCHITECT, //FOR current POD
            podAssignments: [//PodAssignmentDto
                {
                    uuid: 'AmarPandavPodAssignmentUuid-01082024',
                    dayAsStr: '2024-08-01',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'AURAPodUuid',
                            podName: 'AURA',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for AURA'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'AURAPodUuid',
                            podName: 'AURA',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for AURA'
                        }
                    }
                },
                {
                    uuid: 'AmarPandavPodAssignmentUuid-02082024',
                    dayAsStr: '2024-08-02',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'AURAPodUuid',
                            podName: 'AURA',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for AURA'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'GIMPodUuid',
                            podName: 'Global Identity Management',
                            podStyleClass: 'podStyleClass2',
                            podDescription: 'This pod is for Global Identity Management'
                        }
                    }
                },
                {
                    uuid: 'AmarPandavPodAssignmentUuid-03082024',
                    dayAsStr: '2024-08-03',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'AURAPodUuid',
                            podName: 'AURA',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for AURA'
                        }
                    }
                },
                {
                    uuid: 'AmarPandavPodAssignmentUuid-13082024',
                    dayAsStr: '2024-08-14',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.ABSENT
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.ABSENT
                    }
                }
            ]
        },
        {
            user: {//UserDto
                uuid: 'ThomasDoblerUuid',
                gpin: 'ThomasDoblerGPIN',
                name: 'Thomas Dobler'
            },
            podMemberRole: PodMemberRole.JAVA_DEVELOPER, //FOR current POD
            podAssignments: [//PodAssignmentDto
                {
                    uuid: 'ThomasDoblerPodAssignmentUuid-09082024',
                    dayAsStr: '2024-08-09',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'AURAPodUuid',
                            podName: 'AURA',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for AURA'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.ABSENT
                    }
                }
            ]
        },
        {
            user: {//UserDto
                uuid: 'KamilLipinskiUuid',
                gpin: 'KamilLipinskiGPIN',
                name: 'Kamil Lipinski'
            },
            podMemberRole: PodMemberRole.JAVA_DEVELOPER, //FOR current POD
            podAssignments: [//PodAssignmentDto
                {
                    uuid: 'KamilLipinskiPodAssignmentUuid-20082024',
                    dayAsStr: '2024-08-20',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'AURAPodUuid',
                            podName: 'AURA',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for AURA'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.ABSENT
                    }
                }
            ]
        },
        {
            user: {//UserDto
                uuid: 'WiktorUuid',
                gpin: 'WiktorGPIN',
                name: 'Wiktor',
            },
            podMemberRole: PodMemberRole.JAVA_DEVELOPER, //FOR current POD
            podAssignments: [//PodAssignmentDto
                {
                    uuid: 'TWiktorPodAssignmentUuid-13082024',
                    dayAsStr: '2024-08-13',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'AURAPodUuid',
                            podName: 'AURA',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for AURA'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.ABSENT
                    }
                }
            ]
        }
    ]
}