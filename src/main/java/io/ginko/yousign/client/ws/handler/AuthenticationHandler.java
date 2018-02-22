package io.ginko.yousign.client.ws.handler;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext> {

    private final String username;
    private final String password;
    private final String apikey;

    public AuthenticationHandler(String username, String password, String apikey) {
        this.username = username;
        this.password = password;
        this.apikey = apikey;
    }


    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty) {

            try {

                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                SOAPFactory soapFactory = SOAPFactory.newInstance();
                if (header == null) {
                    header = envelope.addHeader();
                }

                Name usernameName = soapFactory.createName("username", "", "http://www.yousign.com");
                SOAPHeaderElement userNameHeader = header.addHeaderElement(usernameName);
                userNameHeader.addTextNode(this.username);

                Name passwordName = soapFactory.createName("password", "", "http://www.yousign.com");
                SOAPElement passwordElement = header.addChildElement(passwordName);
                passwordElement.addTextNode(this.password);

                Name apikeyName = soapFactory.createName("apikey", "", "http://www.yousign.com");
                SOAPElement apiKeyElement = header.addChildElement(apikeyName);
                apiKeyElement.addTextNode(this.apikey);

            } catch (Exception e) {
                System.err.println("Error security soap header addition " + e.toString());
            }
        }
        return outboundProperty;
    }


    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }
}
