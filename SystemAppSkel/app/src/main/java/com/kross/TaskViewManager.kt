package com.kross

import android.app.Activity
import com.android.wm.shell.ShellTaskOrganizer
import com.android.wm.shell.common.HandlerExecutor
import com.android.wm.shell.common.SyncTransactionQueue
import com.android.wm.shell.common.TransactionPool
import com.android.wm.shell.startingsurface.StartingWindowController
import com.android.wm.shell.startingsurface.phone.PhoneStartingWindowTypeAlgorithm
import com.android.wm.shell.sysui.ShellCommandHandler
import com.android.wm.shell.sysui.ShellController
import com.android.wm.shell.sysui.ShellInit

class TaskViewManager(
    private val activity: Activity
) {
    private val shellExecutor = HandlerExecutor(activity.mainThreadHandler)
    private val taskOrganizer = ShellTaskOrganizer(shellExecutor)
    private val syncQueue = SyncTransactionQueue(TransactionPool(), shellExecutor)
    private val shellCommandHandler = ShellCommandHandler()

    init {
        initTaskStackListener()
    }

    private fun initTaskStackListener() {
        val shellInit = ShellInit(shellExecutor)
        StartingWindowController(
            activity,
            shellInit,
            ShellController(shellInit, shellCommandHandler, shellExecutor),
            taskOrganizer,
            shellExecutor,
            PhoneStartingWindowTypeAlgorithm(),
            null,
            TransactionPool()
        )
        shellInit.init()
        val taskList = taskOrganizer.registerOrganizer()

    }
}