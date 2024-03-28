@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
import groovyx.net.http.RESTClient
import groovy.json.JsonOutput

abstract class RestApiWrapper {
    protected RESTClient restClient
    protected Map<String, String> defaultHeaders

    RestApiWrapper(String baseUrl, Map<String, String> defaultHeaders) {
        restClient = new RESTClient(baseUrl)
        this.defaultHeaders = defaultHeaders
    }

    protected def get(String path, Map headers = [:]) {
        makeRequest('GET', path, headers)
    }

    protected def post(String path, Map body, Map headers = [:]) {
        makeRequest('POST', path, headers, body)
    }

    protected def put(String path, Map body, Map headers = [:]) {
        makeRequest('PUT', path, headers, body)
    }

    protected def delete(String path, Map headers = [:]) {
        makeRequest('DELETE', path, headers)
    }

    protected def customMethod(String method, String path, Map body = [:], Map headers = [:]) {
        makeRequest(method.toUpperCase(), path, headers, body)
    }

    private def makeRequest(String method, String path, Map headers = [:], Map body = [:]) {
        try {
            def response = restClient.request(method: method, path: path, headers: combineHeaders(headers), body: JsonOutput.toJson(body))

            handleResponse(response)
        } catch (Exception ex) {
            handleError(ex)
        }
    }

    private Map combineHeaders(Map additionalHeaders) {
        defaultHeaders.plus(additionalHeaders)
    }

    private void handleResponse(response) {
        println "Response status: ${response.statusLine}"
        println "Response body: ${response.getData()}"
        // Additional processing based on response if needed
    }

    private void handleError(Exception ex) {
        println "Error occurred: ${ex.message}"
        // Additional error handling logic if needed
    }





private void handleResponse(response) {
    println "Response status: ${response.statusLine}"
    
    if (response.status.code >= 400) {
        println "Error: ${response.statusLine}"
        return
    }
    
    if (response.contentType?.contains("application/json")) {
        try {
            def jsonResponse = JsonOutput.prettyPrint(response.getData())
            println "Response body (JSON):"
            println jsonResponse
        } catch (Exception e) {
            println "Failed to parse response as JSON: ${e.message}"
            println "Response body: ${response.getData()}"
        }
    } else {
        println "Response body: ${response.getData()}"
    }
}




    
}
