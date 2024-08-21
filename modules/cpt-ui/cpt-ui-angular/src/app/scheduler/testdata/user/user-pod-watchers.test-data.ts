//test data for cpt_pod_watcher

export const USER_POD_WATCHERS_TEST_DATA = [
    {
        user: { //UserDto
            uuid: 'HrishikeshRaghavanUuid',
            gpin: 'HrishikeshRaghavanGpi',
            name: 'Hrishikesh Raghavan',
            country: 'UK'
        },
        userPodWatcherDetails: [
            {
                podWatcherUuid: 'podWatcherHrishikeshRaghavanAndAURAPodUuid',
                pod: {////PodDto
                    uuid: 'AURAPodUuid',
                    podName: 'AURA',
                    podDescription: 'This pod is for AURA',
                    podStyleClass: 'podStyleClass1'
                }
            },
            {
                podWatcherUuid: 'podWatcherHrishikeshRaghavanAndGIMPodUuid',
                pod: {////PodDto
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
                }
            },
        ]
    },
    {
        user: { //UserDto
            uuid: 'podWatchNickTuffsUuid',
            gpin: 'NickTuffsGpi',
            name: 'Nick Tuffs',
            country: 'UK'
        },
        userPodWatcherDetails: [
            {
                podWatcherUuid: 'podWatcherHrishikeshRaghavanAndGIMPodUuid',
                pod: {////PodDto
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
                }
            },
        ]
    },

]
