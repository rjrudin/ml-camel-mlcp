package com.marklogic.camel.component.mlcp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

    /**
     * TODO Convert into an actual test case.
     */
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        final String mlcpUri = "mlcp:localhost:8003?username=admin&password=admin&output_collections=test-collection";
        final String inboxUri = "file:///c:/temp/camel-sandbox/inbox";

        // final String marklogicDocsUri =
        // "http4:localhost:8004/v1/documents?uri=${body.fileName}&authUsername=admin&authPassword=admin";

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from(inboxUri).to(mlcpUri);
            }
        });

        context.start();

        while (true) {
            Thread.sleep(5000);
        }
    }
}
