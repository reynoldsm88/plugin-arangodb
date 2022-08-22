package io.kestra.plugin.arangodb;


import com.arangodb.ArangoDB;
import com.arangodb.async.ArangoDBAsync;
import com.arangodb.mapping.ArangoJack;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.kestra.core.models.annotations.PluginProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@SuperBuilder
@NoArgsConstructor
@Getter
@Introspected
public class ArangoDbConnection {

    private static ArangoDBAsync DRIVER = null;

    @PluginProperty( dynamic = true )
    @NotNull
    @NotEmpty
    private String host;

    @PluginProperty( dynamic = true )
    @NotNull
    @NotEmpty
    private int port;


    public ArangoDBAsync client() {
        if ( DRIVER != null ) {
            return DRIVER;
        } else {
            DRIVER = new ArangoDBAsync.Builder()
                    .host( host, port )
                    .serializer( configureSerde() )
                    .build();

            return DRIVER;
        }
    }


    private static final ArangoJack configureSerde() {
        ArangoJack serde = new ArangoJack();

        serde.configure( ( mapper ) -> {
            mapper.registerModule( new JavaTimeModule() );
        } );

        return serde;
    }
}