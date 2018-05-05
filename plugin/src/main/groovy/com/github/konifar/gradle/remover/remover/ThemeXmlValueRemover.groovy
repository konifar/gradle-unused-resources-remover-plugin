package com.github.konifar.gradle.remover.remover

class ThemeXmlValueRemover extends StyleXmlValueRemover {

    @Override
    String getFileType() {
        return "theme"
    }

    @Override
    String getResourceName() {
        // theme is actually style
        return "style"
    }

}