//test data for cpt_pod
import {PodMemberRole} from "../../app/scheduler/models/pod/pom-member-role.enum";

export const POD_DETAILS_TEST_DATA = [
  {
    uuid:'AURAPodUuid',
    podName: 'AURA',
    podShortName: 'AURA',
    podStyleClass: 'podStyleClass1',
    podDescription:'This pod is for AURA',
    podMembers: [//PodMemberDto
      {
        uuid: 'podMemberNiteshShriyanUuid1',
        podMemberRole: PodMemberRole.POD_LEAD,
        user: {//UserDto
          uuid: 'NiteshShriyanUuid',
          gpin: 'NiteshShriyanGPIN',
          name: 'Nitesh Shriyan'
        },
      },
      {
        uuid: 'podMemberAmarPandavUuid1',
        podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
        user: {//UserDto
          uuid: 'AmarPandavUuid',
          gpin: 'AmarPandavGPIN',
          name: 'Amar Pandav'
        }
      },
      {
        uuid: 'podMemberThomasDoblerUuid1',
        podMemberRole: PodMemberRole.JAVA_DEVELOPER,
        user: {//UserDto
          uuid: 'ThomasDoblerUuid',
          gpin: 'ThomasDoblerGPIN',
          name: 'Thomas Dobler'
        },
      }
    ],
    podWatchers: [//PodWatcherDto
      {
        uuid: 'podWatchHrishikeshRaghavanUuid1',
        user: { //UserDto
          uuid: 'HrishikeshRaghavanUuid',
          gpin: 'HrishikeshRaghavanGpi',
          name: 'Hrishikesh Raghavan',
          country: 'UK'
        }
      },
      {
        uuid: 'podWatchNickTuffsUuid2',
        user: {
          uuid: 'podWatchNickTuffsUuid',
          gpin: 'NickTuffsGpi',
          name: 'Nick Tuffs',
          country: 'UK'
        }
      }
    ]
  },
  {
    uuid:'GIMPodUuid',
    podName: 'Global Identity Management',
    podShortName: 'GIM',
    podStyleClass: 'podStyleClass2',
    podDescription:'This pod is for Global Identity Management',
    podMembers: [//PodMemberDto
      {
        uuid: 'podMemberTimUuid1',
        user: {//UserDto
          uuid: 'TimUuid',
          gpin: 'TimGPIN',
          name: 'Tim'
        },
        podMemberRole: PodMemberRole.POD_LEAD
      },
      {
        uuid: 'podMemberKamilUuid1',
        user: {//UserDto
          uuid: 'KamilLipinskiUuid',
          gpin: 'KamilLipinskiGPIN',
          name: 'Kamil Lipinski'
        },
        podMemberRole: PodMemberRole.JAVA_DEVELOPER
      },
      {
        uuid: 'podMemberWiktorUuid1',
        user: {//UserDto
          uuid: 'WiktorUuid',
          gpin: 'WiktorGPIN',
          name: 'Wiktor'
        },
        podMemberRole: PodMemberRole.JAVA_DEVELOPER
      }
    ],
    podWatchers: [//PodWatcherDto
      {
        uuid: 'podBartoszMarciniakUuid1',
        user: {//UserDto
          uuid: 'podWatchBartoszMarciniakUuid',
          gpin: 'BartoszMarciniakGpin',
          name: 'Bartosz Marciniak',
          country: 'Switzerland'
        }
      },
      {
        uuid: 'podWatchNickTuffsUuid2',
        user: {//UserDto
          uuid: 'podWatchNickTuffsUuid',
          gpin: 'NickTuffsGpi',
          name: 'Nick Tuffs',
          country: 'UK'
        }
      }
    ]
  },
];
