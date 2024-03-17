// Master list of tool configurations
def masterList = [
    [
        toolName: "junit",
        args: "default_arg1 default_arg2",
        resultPath: "/default/junit/result"
    ],
    [
        toolName: "munit",
        args: "default_arg1",
        resultPath: "/default/munit/result"
    ],
    [
        toolName: "mstest",
        args: "default_arg1 default_arg2 default_arg3",
        resultPath: "/default/mstest/result"
    ]
]

// User provided list of tool configurations
def userProvidedList = [
    [
        toolName: "junit",
        resultPath: "/custom/junit/result"
    ],
    [
        toolName: "munit",
        args: "custom_arg1 custom_arg2"
    ]
]

// Function to update missing keys in a tool configuration using the master configuration
def updateMissingKeys(userConfig, masterConfig) {
    masterConfig.each { key, value ->
        if (!userConfig.containsKey(key)) {
            userConfig[key] = value
        }
    }
    return userConfig
}

// Update user's tool configurations with missing keys from the master list
def updatedList = userProvidedList.collect { userConfig ->
    def toolName = userConfig.toolName
    def masterConfig = masterList.find { it.toolName == toolName }
    if (masterConfig) {
        updateMissingKeys(userConfig, masterConfig)
    } else {
        userConfig
    }
}

println updatedList
======================================================================

    def combinedMap = userGivenValues.collectEntries { key, userGivenValue ->
    [key, [userGivenValue: userGivenValue, processedValue: '']]
}

processMap.each { key, processedValue ->
    def mapToUpdate = listToUpdate.find { it.key == key }
    if (mapToUpdate) {
        mapToUpdate.processedValue = processedValue
    } else {
        listToUpdate << [key: key, userGivenValue: '', processedValue: processedValue]
    }
}

