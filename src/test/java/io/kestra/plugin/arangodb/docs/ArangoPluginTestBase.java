package io.kestra.plugin.arangodb.docs;

import com.arangodb.DbName;
import com.arangodb.async.ArangoDBAsync;
import com.arangodb.async.ArangoDatabaseAsync;
import com.arangodb.mapping.ArangoJack;
import io.kestra.core.runners.RunContextFactory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.ExecutionException;

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
