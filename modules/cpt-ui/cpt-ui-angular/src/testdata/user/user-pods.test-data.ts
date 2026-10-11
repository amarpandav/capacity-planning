//test data for cpt_pod_member
import {PodMemberRole} from "../../app/scheduler/models/pod/pom-member-role.enum";

export const USER_PODS_TEST_DATA = [
    {
        user: {//UserDto
            entityId: {uuid: 'NoahShawUuid'},
            userKey: {employeeId: '10000012'},
            name: 'Noah Shaw'
        },
        userPodDetails: [
            {
                podMemberUuid: 'podMemberNoahShawAndATLASPodUuid',
                podMemberRole: PodMemberRole.POD_LEAD,
                pod: {
                    entityId: {uuid: 'ATLASPodUuid'},
                    podName: 'ATLAS',
                    podShortName: 'ATLAS',
                    podStyleClass: 'podStyleClass1',
                    podDescription: 'This pod is for ATLAS'
                }
            },
            {
                podMemberUuid: 'podMemberNoahShawAndORIONPodUuid',
                podMemberRole: PodMemberRole.POD_LEAD,
                pod: {
                    entityId: {uuid: 'ORIONPodUuid'},
                    podName: 'Orion Platform',
                    podShortName: 'ORION',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Orion Platform'
                }
            }
        ]
    },
    {
        user: {//UserDto
            entityId: {uuid: 'AmarPandavUuid'},
            userKey: {employeeId: '10000001'},
            name: 'Amar Pandav'
        },
        userPodDetails: [
            {
                podMemberUuid: 'podMemberAmarPandavAndATLASPodUuid',
                podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
                pod: {
                    entityId: {uuid: 'ATLASPodUuid'},
                    podName: 'ATLAS',
                    podShortName: 'ATLAS',
                    podStyleClass: 'podStyleClass1',
                    podDescription: 'This pod is for ATLAS'
                }
            },
            {
                podMemberUuid: 'podMemberAmarPandavAndORIONPodUuid',
                podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
                pod: {
                    entityId: {uuid: 'ORIONPodUuid'},
                    podName: 'Orion Platform',
                    podShortName: 'ORION',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Orion Platform'
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
        userPodDetails: [
            {
                podMemberUuid: 'podMemberTobiasBergAndATLASPodUuid',
                podMemberRole: PodMemberRole.JAVA_DEVELOPER,
                pod: {
                    entityId: {uuid: 'ATLASPodUuid'},
                    podName: 'ATLAS',
                    podShortName: 'ATLAS',
                    podStyleClass: 'podStyleClass1',
                    podDescription: 'This pod is for ATLAS'
                }
            }
        ]
    },
    {
        user: {//UserDto
            entityId: {uuid: 'TimUuid'},
            userKey: {employeeId: '10000007'},
            name: 'Tara Stone'
        },
        userPodDetails: [
            {
                podMemberUuid: 'podMemberTaraAndORIONPodUuid',
                podMemberRole: PodMemberRole.POD_LEAD,
                pod: {
                    entityId: {uuid: 'ORIONPodUuid'},
                    podName: 'Orion Platform',
                    podShortName: 'ORION',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Orion Platform'
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
        userPodDetails: [
            {
                podMemberUuid: 'podMemberKevinLangAndORIONPodUuid',
                podMemberRole: PodMemberRole.NET_DEVELOPER,
                pod: {
                    entityId: {uuid: 'ORIONPodUuid'},
                    podName: 'Orion Platform',
                    podShortName: 'ORION',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Orion Platform'
                }
            }
        ]
    },
    {
        user: {//UserDto
            entityId: {uuid: 'OliverUuid'},
            userKey: {employeeId: '10000004'},
            name: 'Oliver Grant'
        },
        userPodDetails: [
            {
                podMemberUuid: 'podMemberOliverAndORIONPodUuid',
                podMemberRole: PodMemberRole.JAVA_DEVELOPER,
                pod: {
                    entityId: {uuid: 'ORIONPodUuid'},
                    podName: 'Orion Platform',
                    podShortName: 'ORION',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Orion Platform'
                }
            }
        ]
    },
]
