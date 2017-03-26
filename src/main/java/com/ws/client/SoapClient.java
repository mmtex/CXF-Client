package com.ws.client;

import java.util.Date;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.webservicex.Currency;
import net.webservicex.CurrencyConvertor;
import net.webservicex.CurrencyConvertorSoap;

public class SoapClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapClient.class);

    public static void main(String[] args) {

        LOGGER.info("Starting...");

        // Initialize SOAP service interface.
        long start = new Date().getTime();

        // Initiate SOAP service and log CXF SOAP request/response
        JaxWsProxyFactoryBean factory = new org.apache.cxf.jaxws.JaxWsProxyFactoryBean();
        factory.setServiceClass(CurrencyConvertorSoap.class);
        factory.setAddress("http://www.webservicex.com/CurrencyConvertor.asmx");
        factory.getInInterceptors().add(new org.apache.cxf.interceptor.LoggingInInterceptor());
        factory.getOutInterceptors().add(new org.apache.cxf.interceptor.LoggingOutInterceptor());
        CurrencyConvertorSoap currencyConvertorSoap = (CurrencyConvertorSoap) factory.create();

        //Alternate(without logging interception)
        //CurrencyConvertor currencyConvertorService = new CurrencyConvertor();
        //CurrencyConvertorSoap currencyConvertorSoap = currencyConvertorService.getCurrencyConvertorSoap();

        long end = new Date().getTime();
        LOGGER.debug("Time to initialize CurrencyConvertor service: {} seconds", (end - start) / 1000f);

        // Send a SOAP request to convert USD to CAD.
        Currency from = Currency.USD;
        Currency to = Currency.CAD;

        start = new Date().getTime();
        double rate = currencyConvertorSoap.conversionRate(from, to);
        end = new Date().getTime();
        
        LOGGER.debug("Time to call 'conversionRate': {} seconds", (end - start) / 1000f);
        LOGGER.info("conversionRate from: " + from + " to " + to + ", rate is " + rate);

    }


}
