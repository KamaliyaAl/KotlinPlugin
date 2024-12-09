package com.example.firstplugin

import com.intellij.ide.todo.TodoIndexPatternProvider
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.cache.TodoCacheManager
import com.intellij.psi.util.PsiTreeUtil


class ShowStatisticsAction : AnAction("Show Kotlin Statistics") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = FileEditorManager.getInstance(project).selectedTextEditor
        val document = editor?.document

        if (document != null) {
            val file = FileDocumentManager.getInstance().getFile(document)
            if (file != null && file.extension == "kt") {
                val window = ToolWindowManager.getInstance(project).getToolWindow(MyToolWindowFactory.ID) ?: return

                val statistics = getKotlinFileStatistics(document.text, window.project, file)

                window.contentManager.contents.forEach {
                    val component = it.component
                    if (component is MyPanel) {
                        component.updateStatistics(statistics)
                    }
                }

            }
        }
    }

    fun getTodoCount(project: Project, file: VirtualFile): Int {
        var todoLines = 0
        val todoPatterns = TodoIndexPatternProvider.getInstance().indexPatterns
        val manager = TodoCacheManager.getInstance(project)
        for (pattern in todoPatterns){
            todoLines += manager.getTodoCount(file, pattern)
        }
        return todoLines
    }

    fun getLongestFunction(project: Project, file: VirtualFile): Int{
        val psiFile = PsiManager.getInstance(project).findFile(file) ?: return 0
        var longestFunctionLength = 0
        val document = psiFile.viewProvider.document ?: return 0

        val functions = PsiTreeUtil.findChildrenOfType(psiFile, PsiElement::class.java)
        for (function in functions) {
            if (function.text.startsWith("fun")) {
                // Получаем диапазон текста функции
                val textRange = function.textRange
                val startLine = document.getLineNumber(textRange.startOffset)
                val endLine = document.getLineNumber(textRange.endOffset)
                val functionLength = endLine - startLine + 1

                // Сравниваем длину функции с самой длинной
                if (functionLength > longestFunctionLength) {
                    longestFunctionLength = functionLength
                }
            }
        }
        return longestFunctionLength
    }

    fun getKotlinFileStatistics(fileText: String, project: Project, file: VirtualFile): List<String> {
        val lines = fileText.lines()
        val nonEmptyLines = lines.filter { it.isNotBlank() }

        val totalLines = lines.size
        val nonEmptyLineCount = nonEmptyLines.size

        val todoLines = getTodoCount(project, file)
        val longestFunctionLength = getLongestFunction(project, file)

        return listOf(
        "Total lines: $totalLines",
        "Non-empty lines: $nonEmptyLineCount",
        "TODO lines: $todoLines",
        "Longest function: $longestFunctionLength lines")
    }
}
