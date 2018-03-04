package io.ginko.yousign.client.authentication;

import io.ginko.yousign.client.cosign.AuthenticationWS;
import io.ginko.yousign.client.cosign.CosignWS;
import io.ginko.yousign.client.logger.SLF4JLoggerProducer;
import io.ginko.yousign.client.property.PropertyProducer;
import io.ginko.yousign.client.ws.YouSignWebServicesProducer;
import io.ginko.yousign.client.ws.handler.AuthenticationHandler;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class AuthenticationTest {

        @Deployment
        public static Archive<?> createTestArchive() {
            return ShrinkWrap.create(JavaArchive.class)
                    .addClass(SLF4JLoggerProducer.class)
                    .addClass(PropertyProducer.class)
                    .addClass(AuthenticationHandler.class)
                    .addClass(YouSignWebServicesProducer.class)
                    // Bean archive deployment descriptor
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        }

        @Inject
        private AuthenticationWS authentication;

        @Test
        public void authenticate() throws Exception {
            assertThat(authentication)
                    .isNotNull();

            boolean isAuthenticated = authentication.connect();
            assertThat(isAuthenticated)
                    .isTrue();
        }
    }

