# sbt-bower-simple

Run "bower install" as part of sbt resource generation. 
The plugin will invoke `bower install` everytime resource generation is necessary.

## Installation

The package is not published publicly available. You need to checkout the repository and publish the artifact localy by running `sbt publish-local`.

Add the following line to your `project/plugins.sbt`:

```
addSbtPlugin("com.scalableminds" % "sbt-bower-simple" % "1.0.0")
```

## Settings

Set the following setting to define the location of your `bower` executable:

```
bowerPath := "/usr/local/share/npm/bin/bower"
```
