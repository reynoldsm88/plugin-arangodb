package io.kestra.plugin.arangodb.docs;

import com.arangodb.mapping.ArangoJack;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.kestra.core.serializers.JacksonMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PlaygroundTest {

    private static final Logger LOG = LoggerFactory.getLogger( PlaygroundTest.class );

    @Test
    public void test() {
        ArangoJack serde = new ArangoJack();

        serde.configure( ( mapper ) -> {
            mapper.registerModule( new JavaTimeModule() );
        } );

        Map<String, Object> data = new HashMap<>();
        data.put( "key", "value" );
        data.put( "int", 1 );

        LOG.info( serde.serialize( data ) + "" );

        ObjectMapper mapper = JacksonMapper.ofJson();

        ObjectNode json = mapper.createObjectNode();
        json.put( "key", "value" );
        json.put( "ing", 1 );

        LOG.info( serde.serialize( json ) + "" );


    }
}
