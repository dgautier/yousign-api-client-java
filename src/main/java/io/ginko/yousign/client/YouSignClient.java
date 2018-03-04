package io.ginko.yousign.client;

import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import io.ginko.yousign.client.cosign.*;
import io.ginko.yousign.client.model.Cosigner;
import io.ginko.yousign.client.model.VisibleSignature;
import io.ginko.yousign.client.property.Property;
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
    @Property(value = "yousign.urlIFrame")
    private String urlIFrame;

    final String MODE_AUTH = "MASS";

    public int createSignature(List<Cosigner> cosigners, String file, String title) throws Exception {
        final CosignFileParams fileParameters = createFileToSign(title, file);

        final List<CosignerInfosParams> lcoCosignerInfosParams = cosigners
                .stream()
                .map(c -> c.toCosignerInfosParams())
                .collect(Collectors.toList());

        final List<VisibleSignature> visibleSignatures = cosigners
                .stream()
                .map(c -> c.getVisibleSignature())
                .collect(Collectors.toList());

        fileParameters.getVisibleOptions().addAll(visibleSignatures);

        final List<CosignFileParams> cosignedFiles = new ArrayList<>();
        cosignedFiles.add(fileParameters);
        return cosign(lcoCosignerInfosParams, cosignedFiles);
    }

    private int cosign(List<CosignerInfosParams> cosignerInfosParams, final List<CosignFileParams> cosignFileParams) throws Exception {

        InitCosignResp cosignResponse = cosign.initCosign(cosignFileParams, cosignerInfosParams, "", "", "", "", "", "", "", "IFRAME", false);
        return cosignResponse.getIdDemand();
    }

    private CosignFileParams createFileToSign(String title, String file) {
        final CosignFileParams fileParameters = new CosignFileParams();
        fileParameters.setName(title);
        final DataHandler fileDataHandler = getDataHandler(file);
        fileParameters.setContent(fileDataHandler);
        return fileParameters;
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
