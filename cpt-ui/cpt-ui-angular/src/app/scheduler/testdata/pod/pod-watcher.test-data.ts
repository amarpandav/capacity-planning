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
        uuid: 'InternationalSpaceStationPodUuid',
        podName: 'International Space Station',
        podDescription:'This pod is for International Space Station USA',
        podStyleClass: 'podStyleClass1'
      },
      {
        uuid: 'GotthardTunnelPodUuid',
        podName: 'Gotthard tunnel',
        podDescription:'This pod is for Gotthard tunnel Switzerland',
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
        uuid: 'InternationalSpaceStationPodUuid',
        podName: 'International Space Station',
        podDescription:'This pod is for International Space Station USA',
        podStyleClass: 'podStyleClass1'
      },
      {
        uuid: 'GotthardTunnelPodUuid',
        podName: 'Gotthard tunnel',
        podDescription:'This pod is for Gotthard tunnel Switzerland',
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
        uuid: 'GotthardTunnelPodUuid',
        podName: 'Gotthard tunnel',
        podDescription:'This pod is for Gotthard tunnel Switzerland',
        podStyleClass: 'podStyleClass2'
      }
    ]
  }
]
