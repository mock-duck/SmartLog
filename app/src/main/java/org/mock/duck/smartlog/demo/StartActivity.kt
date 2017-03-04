package org.mock.duck.smartlog.demo

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import org.mock.duck.smartlog.R
import org.mock.duck.smartlog.RawPrinter
import org.mock.duck.smartlog.core.*
import org.mock.duck.smartlog.printer.PrettyPrinter

class StartActivity : AppCompatActivity() {
    val log: SmartLog = SmartLog()

    init {
        //log.configure("WASD", LogMode.STAGE, RawPrinter(), false)
        log.configure("WASD", LogMode.STAGE, PrettyPrinter(), false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            try {
                val i : Int = 277 / 0;
            } catch (e: Exception) {
                val extras: MutableSet<LogExtras> = mutableSetOf()
                extras.add(LogExtras("blub", "{\"entries\":[{\"folderInfo\":{\"name\":\"ReallyLongFolderName\",\"type\":\"USER\",\"id\": \"longfoldername\"},\"folderStatus\":{\"totalCount\":0,\"unreadCount\":0,\"contentTag\": \"5\"},\"entries\":[]}]}", Type.JSON))
                extras.add(LogExtras("error", e, Type.THROWABLE))
                extras.add(LogExtras("id", 213123, Type.LONG))
                log.d(Data("myMessage", null, extras))
                //log.d(Data("myMessage"))

            }
        }

        // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //         .setAction("Action", null).show()
    }
}

