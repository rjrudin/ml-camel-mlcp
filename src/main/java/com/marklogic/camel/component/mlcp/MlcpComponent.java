package com.marklogic.camel.component.mlcp;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

public class MlcpComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> params) throws Exception {
        MlcpEndpoint e = new MlcpEndpoint(uri, remaining, this, params);
        //setProperties(e, params);
        return e;
    }

}
