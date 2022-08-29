package io.kestra.plugin.arangodb.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.kestra.core.exceptions.IllegalVariableEvaluationException;
import io.kestra.core.models.annotations.PluginProperty;
import io.kestra.core.models.tasks.Task;
import io.kestra.core.runners.RunContext;
import io.kestra.core.serializers.JacksonMapper;
import io.kestra.plugin.arangodb.ArangoDbConnection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Map;

@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public abstract class ArangoDbTask extends Task {

    @Schema(
            title = "ArangoDb Connection Info",
            description = "Properties for configuring the ArangoDb client"
    )
    @PluginProperty( dynamic = false )
    @NotNull
    protected ArangoDbConnection connection;

    protected static Object source( Object data, RunContext context ) throws IllegalVariableEvaluationException {
        try {
            if ( data instanceof String ) {
                return JacksonMapper.ofJson().readValue( context.render( (String) data ), ObjectNode.class );
            } else if ( data instanceof Map ) {
                return context.render( (Map<String, Object>) data );
            } else {
                return JacksonMapper.toMap( data );
            }
        } catch ( JsonProcessingException jpe ) {
            throw new IllegalVariableEvaluationException( jpe );
        }

    }

}
