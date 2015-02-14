package com.marklogic.camel.component.mlcp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;

import com.marklogic.contentpump.ContentPump;
import com.marklogic.contentpump.utilities.OptionsFileUtil;

/**
 * Currently dependent on a File component being the consumer that feeds into this endpoint, as it's looking for a
 * message header set by the File component.
 */
public class MlcpProducer extends DefaultProducer {

    private MlcpEndpoint endpoint;

    public MlcpProducer(MlcpEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Message msg = exchange.getIn();
        
        log.info(msg.getHeaders().toString());

        /**
         * This approaches considers 3 sets of MLCP params - the host/port in the URI; the params following the
         * querystring (which are often static but could be dynamic); and the params based on the incoming Message.
         */
        List<String> argList = new ArrayList<String>();
        argList.add("IMPORT");
        argList.add("-host");
        argList.add(endpoint.getHost());
        argList.add("-port");
        argList.add(endpoint.getPort() + "");

        Map<String, Object> params = endpoint.getMlcpParams();
        for (String key : params.keySet()) {
            argList.add("-" + key);
            argList.add(params.get(key).toString());
        }
        argList.add("-input_file_path");
        argList.add(msg.getHeader("CamelFilePath").toString());

        String[] args = argList.toArray(new String[] {});
        log.info("args: " + argList);
        String[] expandedArgs = OptionsFileUtil.expandArguments(args);

        ContentPump.runCommand(expandedArgs);
    }

}
