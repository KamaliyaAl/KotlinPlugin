package com.example.firstplugin

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import javax.swing.DefaultListModel


class MyPanel : JBPanel<MyPanel>(BorderLayout()) {
    private val statisticsListModel = DefaultListModel<String>()
    private val statisticsList = JBList(statisticsListModel)

    init {
        val actionGroup = DefaultActionGroup()
        val action = ActionManager.getInstance().getAction("ShowKotlinStatisticsAction")
        action?.let { actionGroup.add(it) }


        val actionToolbar: ActionToolbar = ActionManager.getInstance().createActionToolbar(
            "MyToolWindowToolbar",
            actionGroup,
            true
        )
        add(actionToolbar.component, BorderLayout.NORTH)

        // Настраиваем JBList для отображения статистики
        statisticsList.emptyText.text = "No statistics available"
        statisticsList.isFocusable = false

        val scrollPane = JBScrollPane(statisticsList)
        add(scrollPane, BorderLayout.CENTER)
    }

    fun updateStatistics(statistics: List<String>) {
        statisticsListModel.clear()
        statistics.forEach { statisticsListModel.addElement(it) }
    }
}
