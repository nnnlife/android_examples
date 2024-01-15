package com.kross

import android.view.KeyEvent

class TaskViewManager : KeyEvent.Callback {

    override fun onKeyDown(var1: Int, var2: KeyEvent?): Boolean {
        return false
    }

    override fun onKeyLongPress(var1: Int, var2: KeyEvent?): Boolean {
        return false
    }

    override fun onKeyUp(var1: Int, var2: KeyEvent?): Boolean {
        return false
    }

    override fun onKeyMultiple(var1: Int, var2: Int, var3: KeyEvent?): Boolean {
        return false
    }
}