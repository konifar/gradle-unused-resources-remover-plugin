# Unused Resources Remover for Android
Gradle Plugin that removes unused resources in Android projects.

## Simple usage

In `build.gradle`

```gradle
buildscript {
  repositories {
    maven { url "https://plugins.gradle.org/m2/" }
  }
  
  dependencies {
    classpath "gradle.plugin.com.github.konifar.gradle:plugin:0.1.0"
  }
}
```

NOTE: If your gradle version is 2.1 and later, you can use new snippet for plugins DSL. If you want to know more details, see [plugin](https://plugins.gradle.org/plugin/com.github.konifar.gradle.unused-resources-remover) page.

In `app/build.gradle`

```gradle
apply plugin: "com.github.konifar.gradle.unused-resources-remover"
```

Run 
```shell
$ ./gradlew removeUnusedResources
```

## Advanced usage

This plugin checks some basic resource files below.

```shell
|--res
   |--anim
   |  |--*.xml
   |--animator
   |  |--*.xml
   |--drawable*
   |  |--*.xml
   |  |--*.png
   |  |--*.jpg
   |  |--*.9.png // 9-patch
   |--layout*
   |  |--*.xml
   |--menu
   |  |--*.xml
   |--mipmap*
   |  |--*.xml
   |  |--*.png
   |--values
      |--bools*.xml
      |--colors*.xml
      |--dimens*.xml
      |--floats*.xml
      |--ids*.xml
      |--integers*.xml
      |--strings*.xml
      |--styles*.xml
      |--themes*.xml
```

If you want to check other files, you can add custom remover settings in `app/build.gradle`.

Here is two example.

- `fonts.xml` (actually same format with strings.xml)
- `text_appearance.xml` (actually same format with styles.xml) 

```gradle
unusedResourcesRemover {
  // You can add custom setting. 
  extraRemovers = [
    createXmlValueRemover("font", "string", "string"), // fonts.xml
    createXmlValueRemover("text_appearance", "style", "style", "style") // text_appearance.xml
  ]
  ...
}
```



```aidl

  // Write file or directory names
  excludeNames = [
    "strings.xml", // strings.xml is never checked
    "res/drawable" // drawable* dirs are never checked
  ]
  
  // When dryRun option is true, unused files are not removed.
  dryRun = true // default false
```