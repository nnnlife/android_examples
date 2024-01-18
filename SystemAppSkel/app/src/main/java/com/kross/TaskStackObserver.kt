package com.kross


import android.app.ActivityManager
import android.app.ActivityTaskManager
import android.app.TaskStackListener
import android.os.Handler
import android.os.HandlerExecutor
import android.os.RemoteException
import com.android.wm.shell.sysui.ShellInit

object TaskStackObserver : TaskStackListener() {
    private var initialized = false
    private val mExecutor = HandlerExecutor(Handler.getMain())
    private val mListeners = ArrayList<TaskStackListener>()

    fun addListener(listener: TaskStackListener) {
        if (!initialized) {
            ActivityTaskManager.getService().registerTaskStackListener(this)
        }
    }

    @Throws(RemoteException::class)
    override fun onTaskStackChanged() {
        mExecutor.execute(Runnable {
            synchronized(mListeners) {
                mListeners.forEach {
                    it.onTaskStackChanged()
                }
            }
        })
    }

    @Throws(RemoteException::class)
    override fun onTaskRemoved(taskId: Int) {
        mExecutor.execute(Runnable {
            synchronized(mListeners) {
                mListeners.forEach {
                    it.onTaskRemoved(taskId)
                }
            }
        })
    }

    @Throws(RemoteException::class)
    override fun onTaskMovedToFront(taskInfo: ActivityManager.RunningTaskInfo?) {
        mExecutor.execute(Runnable {
            synchronized(mListeners) {
                mListeners.forEach {
                    it.onTaskMovedToFront(taskInfo)
                }
            }
        })
    }

    override fun onTaskFocusChanged(taskId: Int, focused: Boolean) {
        mExecutor.execute(Runnable {
            synchronized(mListeners) {
                mListeners.forEach {
                    it.onTaskFocusChanged(taskId, focused)
                }
            }
        })
    }

    override fun onActivityRestartAttempt(
        task: ActivityManager.RunningTaskInfo?,
        homeTaskVisible: Boolean, clearedTask: Boolean, wasVisible: Boolean
    ) {
        mExecutor.execute(Runnable {
            synchronized(mListeners) {
                mListeners.forEach {
                    it.onActivityRestartAttempt(task, homeTaskVisible, clearedTask, wasVisible)
                }
            }
        })
    }
}