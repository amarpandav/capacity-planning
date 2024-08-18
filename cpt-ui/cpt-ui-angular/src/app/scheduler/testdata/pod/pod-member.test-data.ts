//test data for cpt_pod_member
import {PodMemberRole} from "../../models/pod/pom-member-role.enum";

export const POD_MEMBER_TEST_DATA = [
  {
    uuid: 'podMemberNiteshShriyanAndInternationalSpaceStationPodUuid',
    user: {//UserDto
      uuid: 'NiteshShriyanUuid',
      gpin: 'NiteshShriyanGPIN',
      name: 'Nitesh Shriyan'
    },
    podMemberRole: PodMemberRole.POD_LEAD,
    pod:{//PodDto
      uuid: 'InternationalSpaceStationPodUuid',
      podName: 'International Space Station',
      podStyleClass: 'podStyleClass1',
      podDescription: 'This pod is for International Space Station USA'
    }
  },
  {
    uuid: 'podMemberAmarPandavAndInternationalSpaceStationPodUuid',
    user: {//UserDto
      uuid: 'AmarPandavUuid',
      gpin: 'AmarPandavGPIN',
      name: 'Amar Pandav'
    },
    podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
    pod: {//PodDto
      uuid: 'InternationalSpaceStationPodUuid',
      podName: 'International Space Station',
      podStyleClass: 'podStyleClass1',
      podDescription: 'This pod is for International Space Station USA'
    }
  },
  {
    uuid: 'podMemberThomasDoblerAndInternationalSpaceStationPodUuid',
    user: {//UserDto
      uuid: 'ThomasDoblerUuid',
      gpin: 'ThomasDoblerGPIN',
      name: 'Thomas Dobler'
    },
    podMemberRole: PodMemberRole.JAVA_DEVELOPER,
    pod: {//PodDto
      uuid: 'InternationalSpaceStationPodUuid',
      podName: 'International Space Station',
      podStyleClass: 'podStyleClass1',
      podDescription: 'This pod is for International Space Station USA'
    }
  },
  {
    uuid: 'podMemberAmarPandavAndGotthardTunnelPodUuid',
    user: {//UserDto
      uuid: 'AmarPandavUuid',
      gpin: 'AmarPandavGPIN',
      name: 'Amar Pandav'
    },
    podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
    pod: {//PodDto
      uuid: 'GotthardTunnelPodUuid',
      podName: 'Gotthard tunnel',
      podStyleClass: 'podStyleClass2',
      podDescription: 'This pod is for Gotthard tunnel Switzerland'
    }
  },
  {
    uuid: 'podMemberTimAndGotthardTunnelPodUuid',
    user: {//UserDto
      uuid: 'TimUuid',
      gpin: 'TimGPIN',
      name: 'Tim'
    },
    podMemberRole: PodMemberRole.POD_LEAD,
    pod: {//PodDto
      uuid: 'GotthardTunnelPodUuid',
      podName: 'Gotthard tunnel',
      podStyleClass: 'podStyleClass2',
      podDescription: 'This pod is for Gotthard tunnel Switzerland'
    }
  },
  {
    uuid: 'podMemberKamilLipinskiAndGotthardTunnelPodUuid',
    user: {//UserDto
      uuid: 'KamilLipinskiUuid',
      gpin: 'KamilLipinskiGPIN',
      name: 'Kamil Lipinski'
    },
    podMemberRole: PodMemberRole.JAVA_DEVELOPER,
    pod: {//PodDto
      uuid: 'GotthardTunnelPodUuid',
      podName: 'Gotthard tunnel',
      podStyleClass: 'podStyleClass2',
      podDescription: 'This pod is for Gotthard tunnel Switzerland'
    }
  },
  {
    uuid: 'podMemberWiktorAndGotthardTunnelPodUuid',
    user: {//UserDto
      uuid: 'WiktorUuid',
      gpin: 'WiktorGPIN',
      name: 'Wiktor'
    },
    podMemberRole: PodMemberRole.JAVA_DEVELOPER,
    pod: {//PodDto
      uuid: 'GotthardTunnelPodUuid',
      podName: 'Gotthard tunnel',
      podStyleClass: 'podStyleClass2',
      podDescription: 'This pod is for Gotthard tunnel Switzerland'
    }
  }

]
