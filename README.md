# Client Java de l'API SOAP Yousign (v1.2.6)

## Description

Ce client permet d'utiliser l'[API Soap de Yousign](http://developer.yousign.fr) via le langage Java.

## Pré-Requis

JDK 8

## Installation

Ajoutez dans votre fichier pom.xml :

```xml
    <dependency>
        <groupId>io.ginko</groupId>
        <artifactId>yousign-api-client</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
```

## Configuration

Renommez le fichier `ysApiParameters.ini.dist` en `ysApiParameters.ini` présent dans le répertoire `YousignAPI` 
et placez le dans le répertoire souhaité.

Modifiez ensuite la configuration avec les paramètres ci-dessous:

 - `login` : Votre identifiant Yousign (adresse email)
 - `password` : Votre mot de passe
 - `api_key` : Votre clé d'API

## Exemples

Des exemples d'utilisation du client peuvent être trouvés au sein du répertoire `~/YousignAPI/examples`.
Lancez le fichier `connection.php` pour vérifier que vous pouvez correctement accéder à l'API Yousign.
Si tel est le cas, lancez le script `cosignature_init.php` pour créer une cosignature avec deux utilisateurs et deux fichiers.
Vous pouvez ensuite lancer les scripts suivants:

 - `cosignature_list.php` : Pour lister les cosignatures créées
 - `cosignature_details.php` : Pour afficher les détails de la dernière cosignature créée
 - `cosignature_downloadFile.php` : Pour télécharger les fichiers de la dernière cosignature créée
