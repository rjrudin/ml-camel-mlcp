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
 * This does the actual work of building a list of arguments and then invoking Content Pump with them.
 * <p>
 * Since the input to Content Pump is a file or a directory, this producer assumes that the consumer at the other end of
 * the route is a Camel File component.
 */
public class MlcpProducer extends DefaultProducer {

    private MlcpEndpoint endpoint;

    public MlcpProducer(MlcpEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        List<String> argList = buildListOfCommonArgs();
        addQuerystringParamsToArgList(argList);
        addInputFilePathToArgList(argList, exchange);
        invokeContentPump(argList);
    }

    protected List<String> buildListOfCommonArgs() {
        List<String> l = new ArrayList<String>();
        l.add("IMPORT");
        l.add("-host");
        l.add(endpoint.getHost());
        l.add("-port");
        l.add(endpoint.getPort() + "");
        return l;
    }

    protected void addQuerystringParamsToArgList(List<String> argList) {
        Map<String, Object> params = endpoint.getMlcpParams();
        for (String key : params.keySet()) {
            argList.add("-" + key);
            argList.add(params.get(key).toString());
        }
    }

    protected void addInputFilePathToArgList(List<String> argList, Exchange exchange) {
        Message msg = exchange.getIn();
        argList.add("-input_file_path");
        argList.add(msg.getHeader("CamelFilePath").toString());
    }

    protected void invokeContentPump(List<String> argList) throws Exception {
        String[] args = argList.toArray(new String[] {});
        String[] expandedArgs = OptionsFileUtil.expandArguments(args);
        ContentPump.runCommand(expandedArgs);
    }
}
