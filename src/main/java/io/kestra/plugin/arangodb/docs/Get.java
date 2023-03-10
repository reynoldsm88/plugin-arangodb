package io.kestra.plugin.arangodb.docs;

import com.arangodb.DbName;
import com.arangodb.async.ArangoCollectionAsync;
import com.arangodb.async.ArangoDBAsync;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.kestra.core.models.annotations.PluginProperty;
import io.kestra.core.models.tasks.RunnableTask;
import io.kestra.core.runners.RunContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;


@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Schema(
        title = "ArangoDb INSERT",
        description = "Inserts a single document to ArangoDb. See the `Bulk` if you are inserting many documents in a short period of time."
)
public class Get extends ArangoDbInputTask implements RunnableTask<Get.Output> {

    @PluginProperty( dynamic = true )
    private String key;

    @PluginProperty( dynamic = true )
    private Collection<String> keys;

    @Override
    public Get.Output run( RunContext context ) throws Exception {
        Logger logger = context.logger();

        ArangoDBAsync client = this.connection.client();
        ArangoCollectionAsync collection = client.db( DbName.of( context.render( this.database ) ) ).collection( context.render( this.collection ) );

        Object document = source( this.value, context );

        ObjectNode result = null;

        if ( StringUtils.isNotEmpty( this.key ) && !this.keys.isEmpty() ) {
            throw new Exception( "ambiguous operation input, only one of `key` or `keys` should be set" );
        } else if ( StringUtils.isNotEmpty( this.key ) ) {
            CompletableFuture<ObjectNode> response = collection.getDocument( this.key, ObjectNode.class );
            result = response.get();

        } else if ( !this.keys.isEmpty() ) {

        }

        return null;
    }

    /**
     * Input or Output can nested as you need
     */
    @Builder
    @Getter
    public static class Output implements io.kestra.core.models.tasks.Output {
        @Schema(
                title = "Returns the ArangoDB generated identifiers from the insert operation",
                description = "This returns the _id and _key fields for the document that has been inserted."
        )
        private final String row;
        private final Collection<String> rows;
    }
}
