package io.ginko.yousign.client.cosign;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.ws.WebServiceRef;

@RunWith(Arquillian.class)
public class CosignClientTest {


    @Deployment(testable = false)
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClasses(CosignWS.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @WebServiceRef
    private CosignWS cosignWS;

    @Test
    public void cosign(){
cosignWS.initCosign()
    }

}
