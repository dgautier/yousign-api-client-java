package io.ginko.yousign.client.model;

import io.ginko.yousign.client.cosign.VisibleOptionsParams;

public class VisibleOption {

    private VisibleOption(){}

    public static VisibleOption.SignatureType isVisibleSignature(Boolean isVisibleSignature){
        return new VisibleOption.Builder(isVisibleSignature);
    }

    public interface SignatureType {
        Mail visibleSignatureFieldName(String visibleSignatureFieldName);
        Mail visibleSignaturePage(int page);
    }

    public interface Mail {
        // Format is : "llx,lly,urx,ury".
        // llx=left lower x coordinate, lly=left lower y coordinate, urx=upper right x coordinate, ury = upper right y coordinate.
        // Example : "400,700,500,800".
        Build rectangle(int leftLowerX, int leftLowerY, int upperRightX, int upperRightY);
        Build mail(String mail);
    }

    public interface Reason {
        Build reason(String reason);
    }

    public interface Build {
        VisibleOptionsParams build();
    }

    private static class Builder implements SignatureType, Mail, Reason, Build {

        private VisibleOptionsParams instance = new VisibleOptionsParams();

        public Builder(Boolean isVisibleSignature) {
            this.instance.setIsVisibleSignature(isVisibleSignature);
        }

        @Override
        public Mail visibleSignatureFieldName(String visibleSignatureFieldName) {
            this.instance.setVisibleSignatureFieldName(visibleSignatureFieldName);
            return this;
        }

        @Override
        public Mail visibleSignaturePage(int page) {
            this.instance.setVisibleSignaturePage(page);
            return this;
        }

        @Override
        public Build rectangle(int leftLowerX, int leftLowerY, int upperRightX, int upperRightY) {
            StringBuilder rectangle = new StringBuilder()
                    .append(leftLowerX).append(",")
                    .append(leftLowerY).append(",")
                    .append(upperRightX).append(",")
                    .append(upperRightY);

            this.instance.setVisibleRectangleSignature(rectangle.toString());

            return this;
        }

        @Override
        public Build mail(String mail) {
            this.instance.setMail(mail);
            return this;
        }

        @Override
        public Build reason(String reason) {
            this.instance.setReason(reason);
            return this;
        }

        @Override
        public VisibleOptionsParams build() {
            return instance;
        }

    }
}
