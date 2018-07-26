# Release Hints

See http://central.sonatype.org/pages/apache-maven.html for more infos about the sonatype release process.
See https://maven.apache.org/plugins/maven-gpg-plugin/usage.html for more infos.

## release artifact into staging repo
```
mvn release:prepare release:perform -Darguments='-Dgpg.keyname=7F598BBE -Dgpg.passphrase=thepassphrase'
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
