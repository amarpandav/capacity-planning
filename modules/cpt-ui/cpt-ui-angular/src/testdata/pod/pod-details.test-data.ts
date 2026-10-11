//test data for cpt_pod
import {PodMemberRole} from "../../app/scheduler/models/pod/pom-member-role.enum";

export const POD_DETAILS_TEST_DATA = [
  {
    uuid:'ATLASPodUuid',
    podName: 'ATLAS',
    podShortName: 'ATLAS',
    podStyleClass: 'podStyleClass1',
    podDescription:'This pod is for ATLAS',
    podMembers: [//PodMemberDto
      {
        uuid: 'podMemberNoahShawUuid1',
        podMemberRole: PodMemberRole.POD_LEAD,
        user: {//UserDto
          entityId: {uuid: 'NoahShawUuid'},
          userKey: {employeeId: '10000012'},
          name: 'Noah Shaw'
        },
      },
      {
        uuid: 'podMemberAmarPandavUuid1',
        podMemberRole: PodMemberRole.SOLUTIONS_ARCHITECT,
        user: {//UserDto
          entityId: {uuid: 'AmarPandavUuid'},
          userKey: {employeeId: '10000001'},
          name: 'Amar Pandav'
        }
      },
      {
        uuid: 'podMemberTobiasBergUuid1',
        podMemberRole: PodMemberRole.JAVA_DEVELOPER,
        user: {//UserDto
          entityId: {uuid: 'TobiasBergUuid'},
          userKey: {employeeId: '10000003'},
          name: 'Tobias Berg'
        },
      }
    ],
    podWatchers: [//PodWatcherDto
      {
        uuid: 'podWatchHannahReedUuid1',
        user: { //UserDto
          entityId: {uuid: 'HannahReedUuid'},
          userKey: {employeeId: '10000008'},
          name: 'Hannah Reed',
          country: 'UK'
        }
      },
      {
        uuid: 'podWatchNinaTorresUuid2',
        user: {//UserDto
          entityId: {uuid: 'NinaTorresUuid'},
          userKey: {employeeId: '10000009'},
          name: 'Nina Torres',
          country: 'UK'
        }
      }
    ]
  },
  {
    uuid:'ORIONPodUuid',
    podName: 'Orion Platform',
    podShortName: 'ORION',
    podStyleClass: 'podStyleClass2',
    podDescription:'This pod is for Orion Platform',
    podMembers: [//PodMemberDto
      {
        uuid: 'podMemberTimUuid1',
        user: {//UserDto
          entityId: {uuid: 'TimUuid'},
          userKey: {employeeId: '10000007'},
          name: 'Tara Stone'
        },
        podMemberRole: PodMemberRole.POD_LEAD
      },
      {
        uuid: 'podMemberKevinUuid1',
        user: {//UserDto
          entityId: {uuid: 'KevinLangUuid'},
          userKey: {employeeId: '10000005'},
          name: 'Kevin Lang'
        },
        podMemberRole: PodMemberRole.JAVA_DEVELOPER
      },
      {
        uuid: 'podMemberOliverUuid1',
        user: {//UserDto
          entityId: {uuid: 'OliverUuid'},
          userKey: {employeeId: '10000004'},
          name: 'Oliver Grant'
        },
        podMemberRole: PodMemberRole.JAVA_DEVELOPER
      }
    ],
    podWatchers: [//PodWatcherDto
      {
        uuid: 'podBellaMartinUuid1',
        user: {//UserDto
          entityId: {uuid: 'podWatchBellaMartinUuid'},
          userKey: {employeeId: '10000011'},
          name: 'Bella Martin',
          country: 'Switzerland'
        }
      },
      {
        uuid: 'podWatchNinaTorresUuid2',
        user: {//UserDto
          entityId: {uuid: 'NinaTorresUuid'},
          userKey: {employeeId: '10000009'},
          name: 'Nina Torres',
          country: 'UK'
        }
      }
    ]
  },
];
