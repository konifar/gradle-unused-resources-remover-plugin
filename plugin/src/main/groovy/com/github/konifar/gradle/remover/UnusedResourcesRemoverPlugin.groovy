package com.github.konifar.gradle.remover

import com.github.konifar.gradle.remover.file.*
import com.github.konifar.gradle.remover.tag.StringXmlTagRemover
import com.github.konifar.gradle.remover.tag.StyleXmlTagRemover
import org.gradle.api.Plugin
import org.gradle.api.Project

class UnusedResourcesRemoverPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("removeUnusedResources").doLast {

            [
                    new LayoutFileRemover(),
                    new MenuFileRemover(),
                    new MipmapFileRemover(),
                    new DrawableFileRemover(),
                    new AnimatorFileRemover(),
                    new AnimFileRemover(),
            ].forEach {
                it.remove(project)
            }

            [
                    // new AttrXmlTagRemover(),
                    // new ThemeXmlTagRemover(),
                    new StyleXmlTagRemover(),
                    new StringXmlTagRemover(),
                    new StringXmlTagRemover(),
            ].forEach {
                it.remove(project)
            }

            [
                    new ColorFileRemover(),
            ].forEach {
                it.remove(project)
            }

        }
    }

}