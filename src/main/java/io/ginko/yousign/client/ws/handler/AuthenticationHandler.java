package io.ginko.yousign.client.ws.handler;


import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import io.ginko.yousign.client.property.Property;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

@Named
public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext> {

    @Inject
    private Logger LOGGER;

    @Inject
    @Property(value = "yousign.userName")
    private String userName;

    @Inject
    @Property(value = "yousign.password")
    private String password;

    @Inject
    @Property(value = "yousign.apiKey")
    private String apiKey;

    @PostConstruct
    public void validate(){
        if (userName == null) {
            throw new IllegalArgumentException("username can't be null");
        }
        if (password == null) {
            throw new IllegalArgumentException("password can't be null");
        }
        if (apiKey == null) {
            throw new IllegalArgumentException("apiKey can't be null");
        }
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
                userNameHeader.addTextNode(this.userName);

                Name passwordName = soapFactory.createName("password", "", "http://www.yousign.com");
                SOAPElement passwordElement = header.addChildElement(passwordName);
                passwordElement.addTextNode(encode(this.password));

                Name apikeyName = soapFactory.createName("apikey", "", "http://www.yousign.com");
                SOAPElement apiKeyElement = header.addChildElement(apikeyName);
                apiKeyElement.addTextNode(this.apiKey);

            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return outboundProperty;
    }


    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    /**
     * @see : http://developer.yousign.fr/
     * @param clearPassword
     * @return
     */
    //Here is the hash calculation : sha1(sha1(clearPassword)+sha1(clearPassword)).
    String encode(String clearPassword){
        String sha1Encoded = Hashing.sha1().hashString( clearPassword, Charsets.UTF_8).toString();
        String sumEncoded = sha1Encoded + sha1Encoded;
        String finalEncoded = Hashing.sha1().hashString( sumEncoded, Charsets.UTF_8).toString();
        return finalEncoded;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }
}
