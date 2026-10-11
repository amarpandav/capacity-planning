//test data for cpt_user_booked_capacity but grouped by pod_member
import {PodMemberRole} from "../../app/scheduler/models/pod/pom-member-role.enum";
import {AvailabilityType} from "../../app/scheduler/models/availability/availability.enum";

export var POD_ASSIGNMENT_VIEW_TEST_DATA = { //SchedulerViewDto
    currentPod: {
        uuid: 'ATLASPodUuid',
        podName: 'ATLAS',
        podShortName: 'ATLAS',
        podStyleClass: 'podStyleClass1',
        podDescription: 'This pod is for ATLAS'
    },
    userAssignments: [//UserAssignmentDto
        {
            user: {//UserDto
                entityId: {uuid: 'AmarPandavUuid'},
                userKey: {employeeId: '10000001'},
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
                            uuid: 'ATLASPodUuid',
                            podName: 'ATLAS',
                            podShortName: 'ATLAS',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for ATLAS'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'ATLASPodUuid',
                            podName: 'ATLAS',
                            podShortName: 'ATLAS',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for ATLAS'
                        }
                    }
                },
                {
                    uuid: 'AmarPandavPodAssignmentUuid-02082024',
                    dayAsStr: '2024-08-02',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'ATLASPodUuid',
                            podName: 'ATLAS',
                            podShortName: 'ATLAS',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for ATLAS'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'ORIONPodUuid',
                            podName: 'Orion Platform',
                            podShortName: 'ORION',
                            podStyleClass: 'podStyleClass2',
                            podDescription: 'This pod is for Orion Platform'
                        }
                    }
                },
                {
                    uuid: 'AmarPandavPodAssignmentUuid-03082024',
                    dayAsStr: '2024-08-03',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'ATLASPodUuid',
                            podName: 'ATLAS',
                            podShortName: 'ATLAS',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for ATLAS'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.AVAILABLE,
                    }
                },
                {
                    uuid: 'AmarPandavPodAssignmentUuid-14082024',
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
                entityId: {uuid: 'TobiasBergUuid'},
                userKey: {employeeId: '10000003'},
                name: 'Tobias Berg'
            },
            podMemberRole: PodMemberRole.JAVA_DEVELOPER, //FOR current POD
            podAssignments: [//PodAssignmentDto
                {
                    uuid: 'TobiasBergPodAssignmentUuid-09082024',
                    dayAsStr: '2024-08-02',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'ORIONPodUuid',
                            podName: 'Orion Platform',
                            podShortName: 'ORION',
                            podStyleClass: 'podStyleClass2',
                            podDescription: 'This pod is for Orion Platform'
                        }
                    },
                    afternoon: {//AssignmentDto
                        availabilityType: AvailabilityType.AVAILABLE
                    }
                },
                {
                    uuid: 'TobiasBergPodAssignmentUuid-09082024',
                    dayAsStr: '2024-08-09',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'ORIONPodUuid',
                            podName: 'Orion Platform',
                            podShortName: 'ORION',
                            podStyleClass: 'podStyleClass2',
                            podDescription: 'This pod is for Orion Platform'
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
                entityId: {uuid: 'KevinLangUuid'},
                userKey: {employeeId: '10000005'},
                name: 'Kevin Lang'
            },
            podMemberRole: PodMemberRole.JAVA_DEVELOPER, //FOR current POD
            podAssignments: [//PodAssignmentDto
                {
                    uuid: 'KevinLangPodAssignmentUuid-20082024',
                    dayAsStr: '2024-08-20',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'ATLASPodUuid',
                            podName: 'ATLAS',
                            podShortName: 'ATLAS',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for ATLAS'
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
                entityId: {uuid: 'OliverUuid'},
                userKey: {employeeId: '10000004'},
                name: 'Oliver Grant',
            },
            podMemberRole: PodMemberRole.JAVA_DEVELOPER, //FOR current POD
            podAssignments: [//PodAssignmentDto
                {
                    uuid: 'TOliverPodAssignmentUuid-13082024',
                    dayAsStr: '2024-08-13',
                    morning: { //AssignmentDto
                        availabilityType: AvailabilityType.POD_ASSIGNMENT,
                        pod: {//PodDto
                            uuid: 'ATLASPodUuid',
                            podName: 'ATLAS',
                            podShortName: 'ATLAS',
                            podStyleClass: 'podStyleClass1',
                            podDescription: 'This pod is for ATLAS'
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