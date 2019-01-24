package com.hollan.function;

import com.microsoft.azure.functions.annotation.*;
import com.google.gson.Gson;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Cosmos DB trigger.
 */
public class CosmosTrigger {
    /**
     * This function will be invoked when there are inserts or updates in the specified database and collection.
     */
    @FunctionName("CosmosTrigger")
    public void run(
        @CosmosDBTrigger(
            name = "items",
            databaseName = "inventory",
            collectionName = "data",
            leaseCollectionName="lease",
            connectionStringSetting = "CosmosDBConnectionString",
            createLeaseCollectionIfNotExists = true
        )
        String[] items,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Cosmos DB trigger function executed.");
        context.getLogger().info("Documents count: " + items.length);
        
        // Enumerate over all changed documents
        for(String item : items) {
            Gson gson = new Gson();
            Document doc = gson.fromJson(item, Document.class);
            context.getLogger().info(doc.store);
        }
    }
}
