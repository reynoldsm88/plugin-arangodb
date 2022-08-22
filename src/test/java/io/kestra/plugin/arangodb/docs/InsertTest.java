package io.kestra.plugin.arangodb.docs;

import io.kestra.core.runners.RunContextFactory;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * This test will only test the main task, this allow you to send any input
 * parameters to your task and test the returning behaviour easily.
 */
@MicronautTest
@Disabled
class InsertTest {
    @Inject
    private RunContextFactory runContextFactory;

    @Test
    void run() throws Exception {
//        RunContext runContext = runContextFactory.of(ImmutableMap.of("variable", "John Doe"));
//
//        Insert task = Insert.builder()
//                            .client( "Hello {{ variable }}")
//                            .build();
//
//        Insert.Output runOutput = task.run( runContext);
//
//        assertThat(runOutput.getChild().getValue(), is(StringUtils.reverse("Hello John Doe")));
    }
}
