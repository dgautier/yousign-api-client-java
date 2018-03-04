package io.ginko.yousign.client.ws;

import io.ginko.yousign.client.cosign.AuthenticationWS;
import io.ginko.yousign.client.cosign.AuthenticationWS_Service;
import io.ginko.yousign.client.property.Property;
import io.ginko.yousign.client.cosign.CosignWS;
import io.ginko.yousign.client.cosign.CosignWS_Service;
import io.ginko.yousign.client.ws.handler.AuthenticationHandler;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import java.util.ArrayList;
import java.util.List;

public class YouSignWebServicesProducer {

    @Inject
    private Logger LOGGER;

    @WebServiceRef
    private CosignWS_Service cosignWSService;

    @Inject
    @Property(value = "yousign.ws.cosign.url")
    private String cosignUrl;

    @WebServiceRef
    private AuthenticationWS_Service authenticationWSService;

    @Inject
    @Property(value = "yousign.ws.authentication.url")
    private String authenticationUrl;

    @Inject
    private AuthenticationHandler authenticationHandler;

    @Produces
    @RequestScoped
    public CosignWS createCoSignService() {

        LOGGER.debug("cosignUrl=" + cosignUrl);

        if (cosignWSService == null) {
            cosignWSService = new CosignWS_Service();
        }

        ((BindingProvider) cosignWSService.getCosignWSPort()).getRequestContext().
                put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, cosignUrl);


        cosignWSService.setHandlerResolver(createYouSignHandler());
        return cosignWSService.getCosignWSPort();
    }

    @Produces
    @RequestScoped
    public AuthenticationWS createAuthenticationService() {

        LOGGER.debug("authenticationUrl=" + authenticationUrl);

        if (authenticationWSService == null) {
            authenticationWSService = new AuthenticationWS_Service();
        }

        ((BindingProvider) authenticationWSService.getAuthenticationWSPort()).getRequestContext().
                put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, authenticationUrl);


        authenticationWSService.setHandlerResolver(createYouSignHandler());
        return authenticationWSService.getAuthenticationWSPort();
    }


    private HandlerResolver createYouSignHandler() {
        return portInfo -> {
            List<Handler> handlerChain = new ArrayList<>();
            handlerChain.add(authenticationHandler);
            return handlerChain;
        };
    }
}
