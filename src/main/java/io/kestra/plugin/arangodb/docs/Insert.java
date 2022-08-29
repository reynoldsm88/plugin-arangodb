package io.kestra.plugin.arangodb.docs;

import com.arangodb.DbName;
import com.arangodb.async.ArangoCollectionAsync;
import com.arangodb.async.ArangoDBAsync;
import com.arangodb.entity.DocumentCreateEntity;
import io.kestra.core.models.annotations.PluginProperty;
import io.kestra.core.models.tasks.RunnableTask;
import io.kestra.core.runners.RunContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;

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
public class Insert extends ArangoDbInputTask implements RunnableTask<Insert.Output> {

    @PluginProperty( dynamic = true )
    private Object value;

    @Override
    public Insert.Output run( RunContext context ) throws Exception {
        Logger logger = context.logger();

        ArangoDBAsync client = this.connection.client();
        ArangoCollectionAsync collection = client.db( DbName.of( context.render( this.database ) ) ).collection( context.render( this.collection ) );

        Object document = source( this.value, context );
        CompletableFuture<DocumentCreateEntity<Object>> result = collection.insertDocument( document );
        DocumentCreateEntity<Object> response = result.get();

        return Output.builder()
                     .id( response.getId() )
                     .key( response.getKey() )
                     .revision( response.getRev() )
                     .build();
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
        private final String id;
        private final String key;
        private final String revision;
    }
}
