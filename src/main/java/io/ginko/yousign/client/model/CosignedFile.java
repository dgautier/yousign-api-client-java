package io.ginko.yousign.client.model;

import com.google.common.net.MediaType;
import io.ginko.yousign.client.cosign.CosignFileParams;
import io.ginko.yousign.client.cosign.VisibleOptionsParams;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CosignedFile {

    private CosignedFile(){}

    public static CosignedFile.Content name(String name){
        return new CosignedFile.Builder(name);
    }

    public interface Content {
        Password content(String file);
        Password content(File file);
        Password content(InputStream stream) throws IOException;
    }

    public interface Password {
        Build password(String password);
        CosignFileParams build();
    }

    public interface VisibleOptions {
        Build visibleOption(VisibleOptionsParams visibleOption);
        Build visibleOptions(List<VisibleOptionsParams> visibleOptions);
        CosignFileParams build();
    }


    public interface Build {
        CosignFileParams build();
    }

    private static class Builder implements Content, Password, VisibleOptions, Build {

        private final CosignFileParams cosignFileParams = new CosignFileParams();

        public Builder(String name) {
            cosignFileParams.setName(name);
        }

        @Override
        public Password content(String file) {
            final FileDataSource rawData = new FileDataSource(file);
            DataHandler dataHandler = new DataHandler(rawData);
            cosignFileParams.setContent(dataHandler);
            return this;
        }

        @Override
        public Password content(File file) {
            final FileDataSource rawData = new FileDataSource(file);
            DataHandler dataHandler = new DataHandler(rawData);
            cosignFileParams.setContent(dataHandler);
            return this;
        }

        @Override
        public Password content(InputStream stream) throws IOException {
            final ByteArrayDataSource rawData = new ByteArrayDataSource(stream, MediaType.PDF.type());
            DataHandler dataHandler = new DataHandler(rawData);
            cosignFileParams.setContent(dataHandler);
            return this;
        }

        @Override
        public Build password(String password) {
            cosignFileParams.setName(password);
            return this;
        }

        @Override
        public Build visibleOption(VisibleOptionsParams visibleOptionsParams) {
            cosignFileParams.getVisibleOptions().add(visibleOptionsParams);
            return this;
        }

        @Override
        public Build visibleOptions(List<VisibleOptionsParams> visibleOptionsParams) {
            cosignFileParams.getVisibleOptions().addAll(visibleOptionsParams);
            return this;
        }

        @Override
        public CosignFileParams build() {
            return cosignFileParams;
        }
    }
}
