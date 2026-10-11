//test data for cpt_pod_watcher

export const USER_POD_WATCHERS_TEST_DATA = [
    {
        user: { //UserDto
            entityId: {uuid: 'HannahReedUuid'},
            userKey: {employeeId: '10000008'},
            name: 'Hannah Reed',
            country: 'UK'
        },
        userPodWatcherDetails: [
            {
                podWatcherUuid: 'podWatcherHannahReedAndATLASPodUuid',
                pod: {////PodDto
                    entityId: {uuid: 'ATLASPodUuid'},
                    podName: 'ATLAS',
                    podShortName: 'ATLAS',
                    podDescription: 'This pod is for ATLAS',
                    podStyleClass: 'podStyleClass1'
                }
            },
            {
                podWatcherUuid: 'podWatcherHannahReedAndORIONPodUuid',
                pod: {////PodDto
                    entityId: {uuid: 'ORIONPodUuid'},
                    podName: 'Orion Platform',
                    podShortName: 'ORION',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Orion Platform'
                }
            },
        ]
    },
    {
        user: { //UserDto
            entityId: {uuid: 'NinaTorresUuid'},
            userKey: {employeeId: '10000009'},
            name: 'Nina Torres',
            country: 'UK'
        },
        userPodWatcherDetails: [
            {
                podWatcherUuid: 'podWatcherNinaTorresAndORIONPodUuid',
                pod: {////PodDto
                    entityId: {uuid: 'ORIONPodUuid'},
                    podName: 'Orion Platform',
                    podShortName: 'ORION',
                    podStyleClass: 'podStyleClass2',
                    podDescription: 'This pod is for Orion Platform'
                }
            },
        ]
    },

]
