package io.ginko.yousign.client.model;

import io.ginko.yousign.client.cosign.VisibleOptionsParams;
import lombok.Builder;

public class VisibleSignature extends VisibleOptionsParams {

    @Builder
    private VisibleSignature(
            int visibleSignaturePage,
            boolean isVisibleSignature,
            String visibleRectangleSignature,
            String visibleSignatureFieldName,
            String mail,
            String reason){

        super();

        this.visibleSignaturePage = visibleSignaturePage;
        this.isVisibleSignature = isVisibleSignature;
        this.visibleRectangleSignature = visibleRectangleSignature;
        this.visibleSignatureFieldName = visibleSignatureFieldName;
        this.mail = mail;
        this.reason = reason;

    }
}
