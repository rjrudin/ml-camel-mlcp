package com.marklogic.camel.component.mlcp;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Simple Camel component (i.e. endpoint factory) that just creates instances of MlcpEndpoint.
 */
public class MlcpComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> params) throws Exception {
        return new MlcpEndpoint(uri, remaining, this, params);
    }

}
