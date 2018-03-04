# Client Java de l'API WS Yousign (v1.2.6)

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
 
 