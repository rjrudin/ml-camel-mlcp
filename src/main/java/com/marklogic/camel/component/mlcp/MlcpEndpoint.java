package com.marklogic.camel.component.mlcp;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;

public class MlcpEndpoint extends DefaultEndpoint {

    private String host;
    private int port;
    private Map<String, Object> mlcpParams;

    public MlcpEndpoint(String uri, String remaining, MlcpComponent component, Map<String, Object> params) {
        super(uri, component);

        String[] tokens = remaining.split(":");
        this.host = tokens[0];
        this.port = Integer.parseInt(tokens[1]);

        mlcpParams = new HashMap<String, Object>(params);
    }

    @Override
    public boolean isLenientProperties() {
        return true;
    }

    @Override
    public Producer createProducer() throws Exception {
        System.out.println("CreateProducer: " + mlcpParams);
        return new MlcpProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor p) throws Exception {
        return new DefaultConsumer(this, p);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Map<String, Object> getMlcpParams() {
        return mlcpParams;
    }
}
