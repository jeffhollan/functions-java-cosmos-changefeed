package com.hollan.function;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.google.gson.Gson;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTrigger-Java&code={your function key}
     * 2. curl "{your host}/api/HttpTrigger-Java?name=HTTP%20Query&code={your function key}"
     * Function Key is not needed when running locally, it is used to invoke function deployed to Azure.
     * More details: https://aka.ms/functions_authorization_keys
     */
    @FunctionName("HttpTrigger-Java")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            @CosmosDBOutput(name = "doc", databaseName = "inventory", collectionName = "data", connectionStringSetting = "CosmosDBConnectionString") OutputBinding<String> doc,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        Gson gson = new Gson();
        doc.setValue(gson.toJson(new Document("1", "Some Data")));
        return request.createResponseBuilder(HttpStatus.OK).build();
    }
}
