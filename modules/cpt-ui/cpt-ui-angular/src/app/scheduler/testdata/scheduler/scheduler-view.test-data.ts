//test data for cpt_user_booked_capacity but grouped by pod_member
import { PodMemberRole } from "../../models/pod/pom-member-role.enum";
import { AvailabilityType } from "../../models/availability/availability.enum";

//Assumption: All users added in this file are from same pod because you are looking at a scheduler view of a single POD to know availability of all these users.
export const SCHEDULER_VIEW_TEST_DATA = [
  {
    user: {//UserDto
      uuid: 'AmarPandavUuid',
      gpin: 'AmarPandavGPIN',
      name: 'Amar Pandav'
    },
    podMemberRoles: [
      PodMemberRole.SOLUTIONS_ARCHITECT
    ],
    userCapacities: [
      {
        dayAsStr: '2024-08-01',
        userBookedCapacity: //UserBookedCapacityDto
          {
            uuid: 'AmarPandavBookedCapacityUuid-01082024',
            morningPod: {//PodDto
              uuid: 'AURAPodUuid',
              podName: 'AURA',
              podStyleClass: 'podStyleClass1',
              podDescription: 'This pod is for AURA'
            },
            afternoonPod: {//PodDto
              uuid: 'AURAPodUuid',
              podName: 'AURA',
              podStyleClass: 'podStyleClass1',
              podDescription: 'This pod is for AURA'
            }
          }

      },
      {
        dayAsStr: '2024-08-02',
        userBookedCapacity: {//UserBookedCapacityDto
          uuid: 'AmarPandavBookedCapacityUuid-02082024',
          morningPod: {//PodDto
            uuid: 'AURAPodUuid',
            podName: 'AURA',
            podStyleClass: 'podStyleClass1',
            podDescription: 'This pod is for AURA'
          },
          afternoonPod: {//PodDto
            uuid: 'GIMPodUuid',
            podName: 'Global Identity Management',
            podStyleClass: 'podStyleClass2',
            podDescription: 'This pod is for Global Identity Management'
          }
        }

      },
      {
        dayAsStr: '2024-08-03',
        userBookedCapacity: {//UserBookedCapacityDto
          uuid: 'AmarPandavBookedCapacityUuid-03082024',
          afternoonPod: {//PodDto
            uuid: 'AURAPodUuid',
            podName: 'AURA',
            podStyleClass: 'podStyleClass1',
            podDescription: 'This pod is for AURA'
          }
        }

      },
      {
        dayAsStr: '2024-08-13',
        userAvailableCapacity: {
          uuid: 'AmarPandavAvailableCapacityUuid-120824',
          morningAvailability: {//AvailabilityDto
            uuid: 'availabilityAbsentUuid',
            availabilityType: AvailabilityType.ABSENT,
            availabilityDescription: 'As per WAY member is not working, reason could be anything for e.g. vacations, sickness, attending conference etc',
            availabilityTypeStyleClass: 'absent'
          },
          afternoonAvailability: {//AvailabilityDto
            uuid: 'availabilityAbsentUuid',
            availabilityType: AvailabilityType.ABSENT,
            availabilityDescription: 'As per WAY member is not working, reason could be anything for e.g. vacations, sickness, attending conference etc',
            availabilityTypeStyleClass: 'absent'
          }
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
    podMemberRoles: [
      PodMemberRole.SOLUTIONS_ARCHITECT
    ],
    userCapacities: [
      {
        dayAsStr: '2024-08-09',
        userBookedCapacity: { //UserBookedCapacityDto
          uuid: 'ThomasDoblerBookedCapacityUuid-09082024',
          morningPod: {//PodDto
            uuid: 'GIMPodUuid',
            podName: 'Global Identity Management',
            podStyleClass: 'podStyleClass2',
            podDescription: 'This pod is for Global Identity Management'
          }
        },
        userAvailableCapacity: {
          uuid: 'ThomasDoblerAvailableCapacityUuid-090824',
          afternoonAvailability: {//AvailabilityDto
            uuid: 'availabilityAbsentUuid',
            availabilityType: AvailabilityType.ABSENT,
            availabilityDescription: 'As per WAY member is not working, reason could be anything for e.g. vacations, sickness, attending conference etc',
            availabilityTypeStyleClass: 'absent'
          }
        }
      }
    ]
  }
]
