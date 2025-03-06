# How to install/run fimafeng-back

The first time you want to compile the project, please install locally `jdom-1.1.3.jar` to your Maven repository.
The JAR file can be found at fimafeng-back/libs.

**To do so, run the following command:**

```mvn install:install-file -Dfile=<path-to-file> -DgroupId=org -DartifactId=jdom -Dversion=1.1.3 -Dpackaging=jar```

*More info available at [Maven website](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html)*