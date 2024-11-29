package com.example.firstplugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class MyToolWindowFactory : ToolWindowFactory {
    companion object {
        val ID = "MyFirstToolWindow"
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = MyPanel()
        val content = toolWindow.contentManager.factory.createContent(panel, "Statistics", false)
        toolWindow.contentManager.addContent(content)

    }
}
