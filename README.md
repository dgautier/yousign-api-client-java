# Client Java de l'API WS Yousign (v1.2.6)

[![Build Status][Travis badge]][Travis build] [![Coverage Status][Coveralls badge]][Coveralls build] [![Maven Central][Maven Central badge]][Maven Central build]

[Travis badge]: https://travis-ci.org/dgautier/yousign-api-client-java.svg
[Travis build]: https://travis-ci.org/dgautier/yousign-api-client-java
[Coveralls badge]: https://coveralls.io/repos/dgautier/yousign-api-client-java/badge.svg
[Coveralls build]: https://coveralls.io/github/dgautier/yousign-api-client-java
[Maven Central badge]: http://img.shields.io/maven-central/v/io.ginko/yousign-api-client-java.svg
[Maven Central build]: http://repo1.maven.org/maven2/io/ginko/yousign-api-client-java/1.0.0-SNAPSHOT/

# TODO 

- Update with latest api version :
https://developers.yousign.com/openapi

## Description

Ce client permet d'utiliser l'[API Soap de Yousign](http://developer.yousign.fr) via le langage Java.

## Pré-Requis

JDK 8

## Installation

Ajoutez dans votre pom.xml :

```xml
    <dependency>
        <groupId>io.ginko</groupId>
        <artifactId>yousign-api-client</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
```

## Configuration

Créer le fichier application.properties et placez le dans le classpath

Modifiez ensuite la configuration avec les paramètres ci-dessous:
 - `yousign.userName` : Votre identifiant Yousign (adresse email)
 - `yousign.password` : Votre mot de passe Yousign
 - `yousign.apiKey` : Votre clé d'api
 - `yousign.ws.authentication.url` : L'url du service d'authentification
 - `yousign.ws.cosign.url` : L'url du service de cosignature
 
 