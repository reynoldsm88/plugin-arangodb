package io.kestra.plugin.arangodb.docs;

import io.kestra.core.models.annotations.PluginProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class ArangoDbLoadInputTask extends ArangoDbInputTask {

    @PluginProperty( dynamic = true )
    protected String database;

    @PluginProperty( dynamic = true )
    protected String collection;

}
