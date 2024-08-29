//test data for cpt_pod_member
import {PodMemberRole} from "../../app/scheduler/models/pod/pom-member-role.enum";

export const USER_PODS_TEST_DATA = [
    {
        user: {//UserDto
            uuid: 'NiteshShriyanUuid',
            gpin: 'NiteshShriyanGPIN',
            name: 'Nitesh Shriyan'
        },
        userPodDetails: [
            {
                podMemberUuid: 'podMemberNiteshShriyanAndAURAPodUuid',
                podMemberRole: PodMemberRole.POD_LEAD,
                pod: {
                    uuid: 'AURAPodUuid',
                    podName: 'AURA',
                    podShortName: 'AURA',
                    podStyleClass: 'podStyleClass1',
                    podDescription: 'This pod is for AURA'
                }
            },
            {
                podMemberUuid: 'podMemberNiteshShriyanAndGIMPodUuid',
                podMemberRole: PodMemberRole.POD_LEAD,
                pod: {
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podShortName: 'GIM',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
                }
            }
        ]
    },
    {
        user: {//UserDto
            uuid: 'AmarPandavUuid',
            gpin: 'AmarPandavGPIN',
            name: 'Amar Pandav'
        },
        userPodDetails: [
            {
                podMemberUuid: 'podMemberAmarPandavAndAURAPodUuid',
                podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
                pod: {
                    uuid: 'AURAPodUuid',
                    podName: 'AURA',
                    podShortName: 'AURA',
                    podStyleClass: 'podStyleClass1',
                    podDescription: 'This pod is for AURA'
                }
            },
            {
                podMemberUuid: 'podMemberAmarPandavAndGIMPodUuid',
                podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
                pod: {
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podShortName: 'GIM',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
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
        userPodDetails: [
            {
                podMemberUuid: 'podMemberThomasDoblerAndAURAPodUuid',
                podMemberRole: PodMemberRole.JAVA_DEVELOPER,
                pod: {
                    uuid: 'AURAPodUuid',
                    podName: 'AURA',
                    podShortName: 'AURA',
                    podStyleClass: 'podStyleClass1',
                    podDescription: 'This pod is for AURA'
                }
            }
        ]
    },
    {
        user: {//UserDto
            uuid: 'TimUuid',
            gpin: 'TimGPIN',
            name: 'Tim'
        },
        userPodDetails: [
            {
                podMemberUuid: 'podMemberTimAndGIMPodUuid',
                podMemberRole: PodMemberRole.POD_LEAD,
                pod: {
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podShortName: 'GIM',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
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
        userPodDetails: [
            {
                podMemberUuid: 'podMemberKamilLipinskiAndGIMPodUuid',
                podMemberRole: PodMemberRole.NET_DEVELOPER,
                pod: {
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podShortName: 'GIM',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
                }
            }
        ]
    },
    {
        user: {//UserDto
            uuid: 'WiktorUuid',
            gpin: 'WiktorGPIN',
            name: 'Wiktor'
        },
        userPodDetails: [
            {
                podMemberUuid: 'podMemberWiktorAndGIMPodUuid',
                podMemberRole: PodMemberRole.JAVA_DEVELOPER,
                pod: {
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podShortName: 'GIM',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
                }
            }
        ]
    },
]
