package com.github.konifar.gradle.remover

import com.github.konifar.gradle.remover.remover.StringXmlValueRemover
import com.github.konifar.gradle.remover.remover.StyleXmlValueRemover
import com.github.konifar.gradle.remover.remover.ThemeXmlValueRemover
import org.gradle.api.Plugin
import org.gradle.api.Project

class UnusedResourcesRemoverPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("removeUnusedResources").doLast {

            [
                    new com.github.konifar.gradle.remover.remover.LayoutFileRemover(),
                    new com.github.konifar.gradle.remover.remover.MenuFileRemover(),
                    new com.github.konifar.gradle.remover.remover.MipmapFileRemover(),
                    new com.github.konifar.gradle.remover.remover.DrawableFileRemover(),
                    new com.github.konifar.gradle.remover.remover.AnimatorFileRemover(),
                    new com.github.konifar.gradle.remover.remover.AnimFileRemover(),
            ].forEach {
                it.remove(project)
            }

            [
                    new ThemeXmlValueRemover(),
                    new StyleXmlValueRemover(),
                    new StringXmlValueRemover(),
                    new StringXmlValueRemover(),
                    // new AttrXmlTagRemover(),
            ].forEach {
                it.remove(project)
            }

            [
                    new com.github.konifar.gradle.remover.remover.ColorFileRemover(),
            ].forEach {
                it.remove(project)
            }

        }
    }

}