package io.kestra.plugin.arangodb.docs;

import io.kestra.core.models.annotations.PluginProperty;
import io.kestra.plugin.arangodb.ArangoDbTask;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class ArangoDbInputTask extends ArangoDbTask {

    @PluginProperty( dynamic = true )
    protected String database;

    @PluginProperty( dynamic = true )
    protected String collection;

    @Schema(
            title = "URI for the source file in Kestra internal storage"
    )
    @PluginProperty( dynamic = true )
    @NotNull
    protected String from;

}
