package com.github.konifar.gradle.remover

class LayoutFileRemover extends FileRemover {

    @Override
    String getType() {
        return "layout"
    }

    @Override
    GString createPattern(File file) {
        String fileName = file.name.take(file.name.lastIndexOf('.'))

        //        // Considered 9patch
//        if (type.startsWith("drawable")) {
//            fileName = fileName - ".9"
//        }

        def pattern = /(${type}\/${fileName})|(R\.${type}\.${fileName})/

//        // Considered data binding
//        if (type.startsWith("layout")) {
//            pattern = /(${type}\/${fileName})|(R\.${type}\.${fileName})|(${toCamelCase(fileName, true)}Binding)/
//        }

        return pattern

    }

}