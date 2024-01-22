package com.adot.home

import android.app.Activity
import android.app.ActivityManager.RunningTaskInfo
import android.app.WindowConfiguration.WINDOWING_MODE_FULLSCREEN
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.util.Log
import android.view.Display.DEFAULT_DISPLAY
import android.view.SurfaceControl
import com.android.launcher3.icons.IconProvider
import com.android.wm.shell.ShellTaskOrganizer
import com.android.wm.shell.ShellTaskOrganizer.TaskListener
import com.android.wm.shell.common.HandlerExecutor
import com.android.wm.shell.common.SyncTransactionQueue
import com.android.wm.shell.common.TransactionPool
import com.android.wm.shell.startingsurface.StartingWindowController
import com.android.wm.shell.startingsurface.phone.PhoneStartingWindowTypeAlgorithm
import com.android.wm.shell.sysui.ShellCommandHandler
import com.android.wm.shell.sysui.ShellController
import com.android.wm.shell.sysui.ShellInit

private const val TAG = "TaskViewManager"

class TaskViewManager(
    private val activity: Activity
) {
    private val shellExecutor = HandlerExecutor(activity.mainThreadHandler)
    private val taskOrganizer = ShellTaskOrganizer(shellExecutor)
    private val syncQueue = SyncTransactionQueue(TransactionPool(), shellExecutor)
    private val shellCommandHandler = ShellCommandHandler()
    private var mNaviTaskId: Int = INVALID_TASK_ID
    private var mAppMapsResolveInfo: ResolveInfo? = null
    private var mRootTaskAppeared = false
    private var mRootLeash: SurfaceControl? = null

    init {
        initTaskOrganizer()

        taskOrganizer.createRootTask(
            DEFAULT_DISPLAY,
            WINDOWING_MODE_FULLSCREEN,
            object: ShellTaskOrganizer.TaskListener {
                override fun onTaskAppeared(
                    taskInfo: RunningTaskInfo,
                    leash: SurfaceControl
                ) {
                    Log.d(TAG, "onTaskAppeared: ${taskInfo.taskId}")
                    if (!mRootTaskAppeared) {
                        mRootTaskAppeared = true
                        mRootLeash = leash
                        launchDefaultApps(activity)
                    }
                }
            })
    }

    private fun initTaskOrganizer() {
        val shellInit = ShellInit(shellExecutor)
        StartingWindowController(
            activity,
            shellInit,
            ShellController(shellInit, shellCommandHandler, shellExecutor),
            taskOrganizer,
            shellExecutor,
            PhoneStartingWindowTypeAlgorithm(),
            IconProvider(activity),
            TransactionPool()
        )
        shellInit.init()
        val taskList = taskOrganizer.registerOrganizer()
        Log.d(TAG, "initTaskStackListener: OK")
        taskOrganizer.addListenerForType(object: TaskListener {
            override fun onTaskAppeared(
                taskInfo: RunningTaskInfo,
                leash: SurfaceControl
            ) {
                Log.d(TAG, "onTaskAppeared: ${taskInfo.taskId}")
            }
        }, ShellTaskOrganizer.TASK_LISTENER_TYPE_MULTI_WINDOW)
    }

    fun pause() {
        Log.d(TAG, "pause: ")
    }

    private fun launchAppMaps(context: Context) {
        if (mNaviTaskId != INVALID_TASK_ID) return

        val intent = Intent().apply {
            addCategory(Intent.CATEGORY_APP_MAPS)
            addCategory(Intent.CATEGORY_DEFAULT)
            action = Intent.ACTION_MAIN
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        mAppMapsResolveInfo = context.packageManager.resolveActivity(
            intent,
            PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
        )
        if (mAppMapsResolveInfo == null) {
            Log.w(TAG, "launchAppMaps: failed to launch map")
            return
        }

        context.startActivity(intent)
    }

    private fun launchDefaultApps(context: Context) {
        launchAppMaps(context)
    }

    companion object {
        private const val METADATA_KEY_LAUNCH_SIDE = "launchSide"
        private const val DRIVING_VIEW_PACKAGE = "ai.umos.drivingview"
        private const val DRIVING_VIEW_ACTIVITY = "ai.umos.drivingview.DrivingViewActivity"
        private const val INVALID_TASK_ID = -1
    }
}