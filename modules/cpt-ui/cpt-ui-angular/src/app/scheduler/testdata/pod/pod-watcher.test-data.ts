//test data for cpt_pod_watcher
export const POD_WATCHER_TEST_DATA = [
  {
    uuid: 'podWatchHrishikeshRaghavanUuid1',
    user: { //PodWatcherDto
      uuid: 'HrishikeshRaghavanUuid',
      gpin: 'HrishikeshRaghavanGpi',
      name: 'Hrishikesh Raghavan',
      country: 'UK'
    },
    pods: [//PodDto
      {
        uuid: 'AURAPodUuid',
        podName: 'AURA',
        podDescription:'This pod is for AURA',
        podStyleClass: 'podStyleClass1'
      },
      {
        uuid: 'GIMPodUuid',
        podName: 'Global Identity Management',
        podDescription:'This pod is for Global Identity Management',
        podStyleClass: 'podStyleClass2'
      }
    ]
  },
  {
    uuid: 'podWatchNickTuffsUuid2',
    user: {
      uuid: 'podWatchNickTuffsUuid',
      gpin: 'NickTuffsGpi',
      name: 'Nick Tuffs',
      country: 'UK'
    },
    pods: [//PodDto
      {
        uuid: 'AURAPodUuid',
        podName: 'AURA',
        podDescription:'This pod is for AURA',
        podStyleClass: 'podStyleClass1'
      },
      {
        uuid: 'GIMPodUuid',
        podName: 'Global Identity Management',
        podDescription:'This pod is for Global Identity Management',
        podStyleClass: 'podStyleClass2'
      }
    ]
  },
  {
    uuid: 'podBartoszMarciniakUuid1',
    user: {
      uuid: 'podWatchBartoszMarciniakUuid',
      gpin: 'BartoszMarciniakGpin',
      name: 'Bartosz Marciniak',
      country: 'Switzerland'
    },
    pods: [//PodDto
      {
        uuid: 'GIMPodUuid',
        podName: 'Global Identity Management',
        podDescription:'This pod is for Global Identity Management',
        podStyleClass: 'podStyleClass2'
      }
    ]
  }
]
