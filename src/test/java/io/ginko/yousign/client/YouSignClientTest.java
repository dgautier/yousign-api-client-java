package io.ginko.yousign.client;

import io.ginko.yousign.client.cosign.VisibleOptionsParams;
import io.ginko.yousign.client.logger.SLF4JLoggerProducer;
import io.ginko.yousign.client.model.Cosigner;
import io.ginko.yousign.client.model.ProofLevel;
import io.ginko.yousign.client.model.VisibleSignature;
import io.ginko.yousign.client.property.PropertyProducer;
import io.ginko.yousign.client.ws.YouSignWebServicesProducer;
import io.ginko.yousign.client.ws.handler.AuthenticationHandler;
import org.assertj.core.util.Lists;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
public class YouSignClientTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(SLF4JLoggerProducer.class)
                .addClass(PropertyProducer.class)
                .addClass(AuthenticationHandler.class)
                .addClass(YouSignWebServicesProducer.class)
                .addClass(YouSignClient.class)
                .addClass(YouSignWebServicesProducer.class)
                // Bean archive deployment descriptor
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private YouSignClient youSignClient;

    @Test
    public void cosign() throws Exception {

            String mail = "jean.michel@ginko.io";
        VisibleSignature visibleSignature = VisibleSignature.builder()
                .isVisibleSignature(true)
                .visibleSignaturePage(1)
                .visibleRectangleSignature("646,75,786,101")
                .mail(mail)
                .reason("No Reason")
                .build();

        Cosigner cosigner = Cosigner.builder()
                .firstName("jean")
                .lastName("michel")
                .phone("+33123456789")
                .mail(mail)
                .proofLevel(ProofLevel.LOW)
                .visibleSignature(visibleSignature)
                .build();

        List<Cosigner> cosigners = Lists.newArrayList();
        cosigners.add(cosigner);

        String file = YouSignClientTest.class.getClassLoader().getResource("1984.pdf").getFile();

        String title = "1984-orwell.pdf";
        youSignClient.createSignature(cosigners, file, title);
    }
}
