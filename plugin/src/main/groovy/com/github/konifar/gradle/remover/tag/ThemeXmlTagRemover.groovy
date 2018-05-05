package com.github.konifar.gradle.remover.tag

class ThemeXmlTagRemover extends StyleXmlTagRemover {

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