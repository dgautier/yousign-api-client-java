package io.ginko.yousign.client;

import com.byteslounge.cdi.annotation.Property;
import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import io.ginko.yousign.client.cosign.*;
import io.ginko.yousign.client.model.Cosigner;
import io.ginko.yousign.client.model.User;
import org.slf4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.inject.Inject;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class YouSignClient {

    @Inject
    private Logger LOGGER;

    @Inject
    private CosignWS cosign;

    @Inject
    @Property(value = "https://demo.yousign.fr/public/ext/cosignature/")
    private String urlIFrame;

    final String MODE_AUTH = "MASS";

    public int createSignature(List<Cosigner> cosigners, String file, String title) throws Exception {
        final List<CosignerInfosParams> lcoCosignerInfosParams = cosigners
                .stream()
                .map(c -> c.toCosignerInfosParams())
                .collect(Collectors.toList());

        final CosignFileParams fileParameters = createFileToSign(title, file);
        fileParameters.getVisibleOptions().add(createVisibleSignature("fake@fake.com", "646,75,786,101"));
        fileParameters.getVisibleOptions().add(createVisibleSignature("fake@fake.com", "55,94,195,117"));

        final List<CosignFileParams> cosignedFiles = new ArrayList<>();
        cosignedFiles.add(fileParameters);
        return cosign(lcoCosignerInfosParams, cosignedFiles);
    }

    private int cosign(List<CosignerInfosParams> cosignerInfosParams, final List<CosignFileParams> cosignFileParams) throws Exception {

        InitCosignResp cosignResponse = cosign.initCosign(cosignFileParams, cosignerInfosParams, "", "", "", "", "", "", "", "IFRAME", false);
        /*
        TokenInfosResp tokenLast = null;
        for (TokenInfosResp token : initCosignResp.getTokens()) {
            LOGGER.debug(token.getToken());
            if (token.getMail().replaceAll(" ", "").toLowerCase().equals(user.getMail().toLowerCase()) && token.getPhone().replaceAll(" ", "").equals(user.getPhone())) {// on est sur le token du client
                final YousignIFrameURL ysIframeURL = new YousignIFrameURL(token.getToken());
                // to add callback url : ysIframeURL.setUrlcallback("http://your.hostname.com/yousigncallback");
                // your can add your urls  to receive sign event notitications and your template
                return ysIframeURL.getIFrameURL(urlIFrameYouSign);
            }
        }
        throw new IllegalArgumentException("No Response tokens found");
        */
        return cosignResponse.getIdDemand();
    }

    private CosignFileParams createFileToSign(String title, String file) {
        final CosignFileParams fileParameters = new CosignFileParams();
        fileParameters.setName(title);
        final DataHandler fileDataHandler = getDataHandler(file);
        fileParameters.setContent(fileDataHandler);
        return fileParameters;
    }


    private VisibleOptionsParams createVisibleSignature(String mailClient, String s) {
        final VisibleOptionsParams file1VisibleOptionsSignClient = new VisibleOptionsParams();
        file1VisibleOptionsSignClient.setIsVisibleSignature(true);
        file1VisibleOptionsSignClient.setVisibleSignaturePage(1);
        file1VisibleOptionsSignClient.setVisibleRectangleSignature(s);
        file1VisibleOptionsSignClient.setMail(mailClient);
        return file1VisibleOptionsSignClient;
    }

    private DataHandler getDataHandler(InputStream stream) throws IOException {
        final ByteArrayDataSource rawData = new ByteArrayDataSource(stream, MediaType.PDF.type());
        return new DataHandler(rawData);
    }

    private DataHandler getDataHandler(String file)   {
        final FileDataSource rawData = new FileDataSource(file);
        return new DataHandler(rawData);
    }

}
