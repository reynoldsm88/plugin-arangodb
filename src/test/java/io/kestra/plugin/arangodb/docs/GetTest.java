package io.kestra.plugin.arangodb.docs;

import com.arangodb.DbName;
import com.arangodb.async.ArangoDBAsync;
import com.arangodb.async.ArangoDatabaseAsync;
import com.arangodb.mapping.ArangoJack;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import io.kestra.core.runners.RunContext;
import io.kestra.core.serializers.JacksonMapper;
import io.kestra.core.utils.IdUtils;
import io.kestra.plugin.arangodb.ArangoDbConnection;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static io.kestra.plugin.arangodb.ArangoTestHelpers.docByKey;
import static io.kestra.plugin.arangodb.ArangoTestHelpers.getCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test will only test the main task, this allow you to send any input
 * parameters to your task and test the returning behaviour easily.
 */
@MicronautTest
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
class GetTest extends ArangoPluginTestBase {

    protected String database = "arango_test_db_" + IdUtils.create().toLowerCase( Locale.ROOT );
    protected String collection = "arang_test_collection_" + IdUtils.create().toLowerCase( Locale.ROOT );

    @BeforeAll
    void setUp() throws InterruptedException, ExecutionException {
        arango = new ArangoDBAsync.Builder().serializer( new ArangoJack() ).build();
        if ( arango.db( DbName.of( database ) ).exists().get() ) {
            arango.db( DbName.of( database ) ).drop().get();
        }

        arango.createDatabase( DbName.of( database ) ).get();
        ArangoDatabaseAsync db = arango.db( DbName.of( database ) );
        db.createCollection( collection ).get();
    }

    @AfterAll
    void tearDown() throws InterruptedException, ExecutionException {
        if ( arango.db( DbName.of( database ) ).exists().get() ) {
            arango.db( DbName.of( database ) ).drop().get();
        }
    }

    @Test
    void testGetSingleDocumentByKey() throws Exception {

    }


    @Test
    void testGetCollectionOfDocumentsByKey() {

    }

}
