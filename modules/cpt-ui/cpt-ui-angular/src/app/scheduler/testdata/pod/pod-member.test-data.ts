//test data for cpt_pod_member
import { PodMemberRole } from "../../models/pod/pom-member-role.enum";

export const POD_MEMBER_TEST_DATA = [
  {
    uuid: 'podMemberNiteshShriyanAndAURAPodUuid',
    user: {//UserDto
      uuid: 'NiteshShriyanUuid',
      gpin: 'NiteshShriyanGPIN',
      name: 'Nitesh Shriyan'
    },
    podMemberRole: PodMemberRole.POD_LEAD,
    pod:{//PodDto
      uuid: 'AURAPodUuid',
      podName: 'AURA',
      podStyleClass: 'podStyleClass1',
      podDescription: 'This pod is for AURA'
    }
  },
  {
    uuid: 'podMemberAmarPandavAndAURAPodUuid',
    user: {//UserDto
      uuid: 'AmarPandavUuid',
      gpin: 'AmarPandavGPIN',
      name: 'Amar Pandav'
    },
    podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
    pod: {//PodDto
      uuid: 'AURAPodUuid',
      podName: 'AURA',
      podStyleClass: 'podStyleClass1',
      podDescription: 'This pod is for AURA'
    }
  },
  {
    uuid: 'podMemberThomasDoblerAndAURAPodUuid',
    user: {//UserDto
      uuid: 'ThomasDoblerUuid',
      gpin: 'ThomasDoblerGPIN',
      name: 'Thomas Dobler'
    },
    podMemberRole: PodMemberRole.JAVA_DEVELOPER,
    pod: {//PodDto
      uuid: 'AURAPodUuid',
      podName: 'AURA',
      podStyleClass: 'podStyleClass1',
      podDescription: 'This pod is for AURA'
    }
  },
  {
    uuid: 'podMemberAmarPandavAndGIMPodUuid',
    user: {//UserDto
      uuid: 'AmarPandavUuid',
      gpin: 'AmarPandavGPIN',
      name: 'Amar Pandav'
    },
    podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
    pod: {//PodDto
      uuid: 'GIMPodUuid',
      podName: 'Global Identity Management',
      podStyleClass: 'podStyleClass2',
      podDescription: 'This pod is for Global Identity Management'
    }
  },
  {
    uuid: 'podMemberTimAndGIMPodUuid',
    user: {//UserDto
      uuid: 'TimUuid',
      gpin: 'TimGPIN',
      name: 'Tim'
    },
    podMemberRole: PodMemberRole.POD_LEAD,
    pod: {//PodDto
      uuid: 'GIMPodUuid',
      podName: 'Global Identity Management',
      podStyleClass: 'podStyleClass2',
      podDescription: 'This pod is for Global Identity Management'
    }
  },
  {
    uuid: 'podMemberKamilLipinskiAndGIMPodUuid',
    user: {//UserDto
      uuid: 'KamilLipinskiUuid',
      gpin: 'KamilLipinskiGPIN',
      name: 'Kamil Lipinski'
    },
    podMemberRole: PodMemberRole.JAVA_DEVELOPER,
    pod: {//PodDto
      uuid: 'GIMPodUuid',
      podName: 'Global Identity Management',
      podStyleClass: 'podStyleClass2',
      podDescription: 'This pod is for Global Identity Management'
    }
  },
  {
    uuid: 'podMemberWiktorAndGIMPodUuid',
    user: {//UserDto
      uuid: 'WiktorUuid',
      gpin: 'WiktorGPIN',
      name: 'Wiktor'
    },
    podMemberRole: PodMemberRole.JAVA_DEVELOPER,
    pod: {//PodDto
      uuid: 'GIMPodUuid',
      podName: 'Global Identity Management',
      podStyleClass: 'podStyleClass2',
      podDescription: 'This pod is for Global Identity Management'
    }
  }

]
