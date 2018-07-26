# Release Hints

See http://central.sonatype.org/pages/apache-maven.html for more infos about the sonatype release process.
See https://maven.apache.org/plugins/maven-gpg-plugin/usage.html for more infos.

## Setup
* Generate encrypted version of your password
```
mvn --encrypt-password <password>
```

* Add encrypted password to <HOME>/.m2/settings.xml
```
  <servers>
    ...
    <server>
      <id>sonatype-com.arcusx</id>
      <username>YOURUSERNAME</username>
      <password>OUTPUTFROMABOVE</password>
    </server>
    ...
  </servers>
```

## release artifact into staging repo
```
mvn release:prepare release:perform -Darguments='-Dgpg.keyname=YOURSUBKEY -Dgpg.passphrase=YOURPASSWORD'
```

## list staging repos
```
mvn nexus-staging:rc-list | grep comarcusxchrono
```

## close staging repo
```
mvn nexus-staging:rc-close -DstagingRepositoryId=comarcusxchrono-xxxx
```

## abort staging process
```
mvn nexus-staging:drop -DstagingRepositoryId=comarcusxchrono-xxxx
```

## release artifact from staging repo to public
```
mvn nexus-staging:release -DstagingRepositoryId=comarcusxchrono-xxxx
```
