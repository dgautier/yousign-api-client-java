package io.ginko.yousign.client.property;


import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;

public class PropertyProducer {

    private Properties properties;

    @Property
    @Produces
    public String produceString(final InjectionPoint ip) {
        return getProperty(ip);
    }

    private String getProperty(InjectionPoint ip) {
        String propertyName = getKey(ip);
        String value = this.properties.getProperty(propertyName);
        if (isNullOrEmpty(value)) {
            throw new IllegalArgumentException("No Value defined for property " + propertyName);
        } else {
            return value;
        }
    }

    @Property
    @Produces
    public int produceInt(final InjectionPoint ip) {
        return Integer.valueOf(getProperty(ip));
    }

    @Property
    @Produces
    public boolean produceBoolean(final InjectionPoint ip) {
        return Boolean.valueOf(getProperty(ip));
    }

    private String getKey(final InjectionPoint ip) {
        return (ip.getAnnotated().isAnnotationPresent(Property.class)
                &&
                !ip.getAnnotated().getAnnotation(Property.class).value().isEmpty())

                ? ip.getAnnotated().getAnnotation(Property.class).value()
                : ip.getMember().getName();
    }

    @PostConstruct
    public void init() {
        this.properties = new Properties();
        final InputStream stream = PropertyProducer.class
                .getResourceAsStream("/application.properties");
        if (stream == null) {
            throw new RuntimeException("No properties!!!");
        }
        try {
            this.properties.load(stream);
        } catch (final IOException e) {
            throw new RuntimeException("Configuration could not be loaded!");
        }
    }
}

