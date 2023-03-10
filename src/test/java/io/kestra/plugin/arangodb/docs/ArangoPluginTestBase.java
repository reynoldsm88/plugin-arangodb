package io.kestra.plugin.arangodb.docs;

import com.arangodb.async.ArangoDBAsync;
import io.kestra.core.runners.RunContextFactory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;

public abstract class ArangoPluginTestBase {

    @Inject
    protected RunContextFactory runContextFactory;

    @Value( "${arangodb.host}" )
    protected String host;

    @Value( "${arangodb.port}" )
    protected Integer port;

    protected String database;
    protected String collection;

    protected ArangoDBAsync arango;

}
