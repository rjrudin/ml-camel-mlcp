package com.rjrudin.marklogic.camel.component.mlcp;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Camel endpoint that has the job of instantiating an MlcpProducer.
 */
public class MlcpEndpoint extends DefaultEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(MlcpEndpoint.class);

    private String host;
    private int port;
    private Map<String, Object> mlcpParams;

    /**
     * @param uri
     * @param remaining
     *            Expected to be of the form "localhost:8003", for example.
     * @param component
     * @param params
     *            This map of parameters will be passed into Content Pump as a list of argument.
     */
    public MlcpEndpoint(String uri, String remaining, MlcpComponent component, Map<String, Object> params) {
        super(uri, component);
        setHostAndPort(remaining);
        
        // Need to make a copy of the map; Camel apparently clears out the params map that is passed in
        mlcpParams = new HashMap<String, Object>(params);
    }

    protected void setHostAndPort(String remaining) {
        String[] tokens = remaining.split(":");
        this.host = tokens[0];
        this.port = Integer.parseInt(tokens[1]);

        if (LOG.isInfoEnabled()) {
            LOG.info(String.format("Content Pump will connect to %s:%d", this.host, this.port));
        }
    }

    /**
     * Allow for the component URI querystring to specify any MLCP params without having to declare them on this
     * endpoint. That makes life easier when it's time to build the list of arguments to pass into MLCP.
     */
    @Override
    public boolean isLenientProperties() {
        return true;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new MlcpProducer(this);
    }

    /**
     * Content Pump can only be a producer (which in Camel means something on the "to" end of a route), and Camel seems
     * fine with null being returned here.
     */
    @Override
    public Consumer createConsumer(Processor p) throws Exception {
        return null;
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
