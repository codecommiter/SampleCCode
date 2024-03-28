@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
import groovyx.net.http.RESTClient
import groovyx.net.http.ContentType

abstract class BaseAPIWrapper {
    protected RESTClient restClient
    protected Map<String, String> defaultHeaders = [:]

    // Constructor to initialize RESTClient
    BaseAPIWrapper(String baseUrl) {
        restClient = new RESTClient(baseUrl)
    }

    // Method to set default headers
    void setDefaultHeaders(Map<String, String> headers) {
        defaultHeaders.putAll(headers)
    }

    // Method to perform GET request
    def get(String path, Map<String, String> headers = [:]) {
        withRestClient { 
            restClient.get(path: path, headers: combineHeaders(headers))
        }
    }

    // Method to perform POST request
    def post(String path, Map<String, Object> body, Map<String, String> headers = [:]) {
        withRestClient {
            restClient.post(path: path, body: body, headers: combineHeaders(headers), contentType: ContentType.JSON)
        }
    }

    // Method to handle error and response checking
    private def withRestClient(Closure closure) {
        try {
            def response = closure.call()
            if (response.status >= 200 && response.status < 300) {
                return response
            } else {
                throw new Exception("API Error: ${response.status} - ${response.statusText}")
            }
        } catch (Exception e) {
            throw new Exception("API Error: ${e.message}", e)
        }
    }

    // Method to combine default headers with method-specific headers
    private Map<String, String> combineHeaders(Map<String, String> methodHeaders) {
        def combinedHeaders = [:]
        combinedHeaders.putAll(defaultHeaders)
        combinedHeaders.putAll(methodHeaders)
        return combinedHeaders
    }
}

// Example usage of the BaseAPIWrapper
class MyAPIWrapper extends BaseAPIWrapper {
    MyAPIWrapper(String baseUrl) {
        super(baseUrl)
        defaultHeaders = [
            'Authorization': 'Bearer myAccessToken',
            'Content-Type': 'application/json'
        ]
    }

    // Example method to perform a custom API call
    def customAPICall(String parameter) {
        def path = "/custom_endpoint"
        def body = [param: parameter]
        post(path, body)
    }
}

// Example usage
def api = new MyAPIWrapper("https://api.example.com")
def response = api.customAPICall("exampleParameter")
println(response)
