//test data for cpt_pod_watcher

export const USER_POD_WATCHERS_TEST_DATA = [
    {
        user: { //UserDto
            entityId: {uuid: 'HrishikeshRaghavanUuid'},
            userKey: {gpin: '43333447'},
            name: 'Hrishikesh Raghavan',
            country: 'UK'
        },
        userPodWatcherDetails: [
            {
                podWatcherUuid: 'podWatcherHrishikeshRaghavanAndAURAPodUuid',
                pod: {////PodDto
                    uuid: 'AURAPodUuid',
                    podName: 'AURA',
                    podShortName: 'AURA',
                    podDescription: 'This pod is for AURA',
                    podStyleClass: 'podStyleClass1'
                }
            },
            {
                podWatcherUuid: 'podWatcherHrishikeshRaghavanAndGIMPodUuid',
                pod: {////PodDto
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podShortName: 'GIM',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
                }
            },
        ]
    },
    {
        user: { //UserDto
            entityId: {uuid: 'NickTuffsUuid'},
            userKey: {gpin: '43568763'},
            name: 'Nick Tuffs',
            country: 'UK'
        },
        userPodWatcherDetails: [
            {
                podWatcherUuid: 'podWatcherNickTuffsAndGIMPodUuid',
                pod: {////PodDto
                    uuid: 'GIMPodUuid',
                    podName: 'Global Identity Management',
                    podShortName: 'GIM',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Global Identity Management'
                }
            },
        ]
    },

]
