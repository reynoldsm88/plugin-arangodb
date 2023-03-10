package io.kestra.plugin.arangodb;

import com.arangodb.DbName;
import com.arangodb.async.ArangoCollectionAsync;
import com.arangodb.async.ArangoDBAsync;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class ArangoTestHelpers {

    public static final ObjectNode docByKey( String key, ArangoCollectionAsync collection ) throws Exception {
        return collection.getDocument( key, ObjectNode.class ).get();
    }

    public static final ArangoCollectionAsync getCollection( String db, String collection, ArangoDBAsync arango ) {
        return arango.db( DbName.of( db ) ).collection( collection );
    }

}
