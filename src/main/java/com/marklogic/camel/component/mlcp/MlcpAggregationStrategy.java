package com.marklogic.camel.component.mlcp;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * This is a simple aggregation strategy that sets the CamelFilePath header so that a Splitter can feed directly into
 * the mlcp Camel component, as the mlcp Camel component needs the CamelFilePath header set. An easy way to use this is
 * to add it to your Spring config file and set the camelFilePath attribute, and then reference it as the strategy for
 * your Splitter.
 */
public class MlcpAggregationStrategy implements AggregationStrategy {

    private String camelFilePath;

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        newExchange.getIn().setHeader("CamelFilePath", camelFilePath);
        return newExchange;
    }

    public String getCamelFilePath() {
        return camelFilePath;
    }

    public void setCamelFilePath(String camelFilePath) {
        this.camelFilePath = camelFilePath;
    }
}
