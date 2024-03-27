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

=======================================================================
    if (fieldValue instanceof Map) {
                // Validate nested map if it's not empty
                if (fieldValue.size() > 0) {
                    def nestedErrors = validateMap(fieldValue, rule.rules)
                    if (nestedErrors) {
                        nestedErrors.each { nestedError ->
                            errors << "$fieldName.${nestedError}"
                        }
                    }
                } else if (rule.minItems) {
                    errors << "$fieldName should have at least ${rule.minItems} item(s)"
                }
            } 
=================================================
    case "map":
    if (fieldValue instanceof Map) {
        // Validate nested map if it's not empty
        if (fieldValue.size() > 0) {
            def nestedErrors = validateMap(fieldValue, rule.rules)
            if (nestedErrors) {
                nestedErrors.each { nestedError ->
                    errors << "$fieldName.${nestedError}"
                }
            }
        } else if (rule.minItems) {
            errors << "$fieldName should have at least ${rule.minItems} item(s)"
        }
    } else {
        errors << "$fieldName should be a map"
    }
    break
=======================================

    def createUnixZipCommands(List zipConfigs) {
    def commands = []
    
    zipConfigs.each { config ->
        def src = config.src.split(',').collect { "'${it.trim()}'" } // Split and quote individual paths
        def destZip = config.destZip
        def includePattern = config.includePattern
        def excludePattern = config.excludePattern
        
        // Create destination folder if it doesn't exist
        sh "mkdir -p '${destZip.substring(0, destZip.lastIndexOf('/'))}'"
        
        // Construct Unix zip command
        def zipCommand = "zip -r '${destZip}' ${src.join(' ')} -x '${excludePattern.replaceAll(',', ' ')}'"
        if (!includePattern.isEmpty()) {
            zipCommand += " -i '${includePattern.replaceAll(',', ' ')}'"
        }
        
        commands.add(zipCommand)
    }
    
    return commands
}
===================================================
def lastCommit = sh(script: 'git log -1 --pretty=format:"%an|%ae|%s"', returnStdout: true).trim()

                    // Split the commit information into separate variables
                    def (committerName, committerEmail, commitMessage) = lastCommit.split('|')

                    // Print the extracted information
                    echo "Last Committer Name: ${committerName}"
                    echo "Last Committer Email: ${committerEmail}"
                    echo "Last Commit Message: ${commitMessage}"
==================================
def lastCommit = sh(script: 'git log -1 --pretty=format:"%H|%an|%ae|%s"', returnStdout: true).trim()

                    // Split the commit information into separate variables
                    def (commitHash, committerName, committerEmail, commitMessage) = lastCommit.split('|')

                    // Print the extracted information
                    echo "Commit Hash: ${commitHash}"
                    echo "Last Committer Name: ${committerName}"
                    echo "Last Committer Email: ${committerEmail}"
                    echo "Last Commit Message: ${commitMessage}"
=======================================================
