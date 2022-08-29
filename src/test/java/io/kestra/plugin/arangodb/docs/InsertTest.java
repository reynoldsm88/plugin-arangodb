package io.kestra.plugin.arangodb.docs;

import com.arangodb.DbName;
import com.arangodb.async.ArangoDBAsync;
import com.arangodb.async.ArangoDatabaseAsync;
import com.arangodb.mapping.ArangoJack;
import com.google.common.collect.ImmutableMap;
import io.kestra.core.runners.RunContext;
import io.kestra.core.runners.RunContextFactory;
import io.kestra.core.utils.IdUtils;
import io.kestra.plugin.arangodb.ArangoDbConnection;
import io.micronaut.context.annotation.Value;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * This test will only test the main task, this allow you to send any input
 * parameters to your task and test the returning behaviour easily.
 */
@MicronautTest
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
class InsertTest {

    @Inject
    private RunContextFactory runContextFactory;

    @Value( "${arangodb.host}" )
    private String host;

    @Value( "${arangodb.port}" )
    private Integer port;

    private static final String database = "arango_test_db_" + IdUtils.create().toLowerCase( Locale.ROOT );
    private static final String collection = "arang_test_collection_" + IdUtils.create().toLowerCase( Locale.ROOT );


    @BeforeAll
    void setUp() throws InterruptedException, ExecutionException {
        System.out.println( "creating database " + host + " : " + port + " - " + database + " - " + collection );
        ArangoDBAsync arangoDB = new ArangoDBAsync.Builder().serializer( new ArangoJack() ).build();

        if ( arangoDB.db( DbName.of( database ) ).exists().get() ) {
            arangoDB.db( DbName.of( database ) ).drop().get();
        }

        arangoDB.createDatabase( DbName.of( database ) ).get();
        ArangoDatabaseAsync db = arangoDB.db( DbName.of( database ) );
        db.createCollection( collection ).get();

        boolean dbExists = arangoDB.db( DbName.of( database ) ).exists().get();
        boolean collectionExists = arangoDB.db( DbName.of( database ) ).collection( collection ).exists().get();

        System.out.println( MessageFormat.format( "{0} = {1}, {2} = {3}", database, dbExists, collection, collectionExists ) );
    }

    @AfterAll
    void tearDown() throws InterruptedException, ExecutionException {
        ArangoDBAsync arangoDB = new ArangoDBAsync.Builder().serializer( new ArangoJack() ).build();
        if ( arangoDB.db( DbName.of( database ) ).exists().get() ) {
            arangoDB.db( DbName.of( database ) ).drop().get();
        }
    }

    @Test
    void runInsertWithStringValue() throws Exception {
        System.out.println( "starting test... with " + database + " : " + collection );
        //@formatter:off
        String json =
                "{\n"
                + "  \"name\": \"Michael\",\n"
                + "  \"project\": \"Kestra\"\n"
                + "}";

        //@formatter:on

        RunContext runContext = runContextFactory.of( ImmutableMap.of( "variable", json ) );

        ArangoDbConnection connection = ArangoDbConnection.builder().host( host ).port( port ).build();

        System.out.println( connection.client().db( DbName.of( database ) ).exists().get() );

        Insert task = Insert.builder()
                            .connection( connection )
                            .database( database )
                            .collection( collection )
                            .value( "{{ variable }}" )
                            .build();

        Insert.Output runOutput = task.run( runContext );

        System.out.println( MessageFormat.format( "{0} {1}, {2}", runOutput.getId(), runOutput.getKey(), runOutput.getRevision() ) );

    }
}
