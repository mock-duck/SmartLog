package core

import android.util.Log
import org.junit.Test
import org.mock.duck.smartlog.core.Config
import org.mock.duck.smartlog.core.Data
import org.mock.duck.smartlog.core.LogMode
import org.mock.duck.smartlog.core.Printer
import org.mockito.Mockito.*


class DataTest {
    val dataWithTag: Data = Data("myMessage", "myTag")
    val dataWithoutTag: Data = Data("myMessage")

    @Test
    fun shouldNotLogInProd() {
        val printerMock = mock(Printer::class.java)
        val config: Config = Config("defaultTag", LogMode.PROD, printerMock, false)

        dataWithTag.log(Log.DEBUG, config)

        verifyNoMoreInteractions(printerMock)
    }

    @Test
    fun shouldOverrideDefaultTag() {
        val printerMock = mock(Printer::class.java)
        val config: Config = Config("defaultTag", LogMode.STAGE, printerMock, false)

        dataWithTag.log(Log.DEBUG, config)

        verify(printerMock).print(Log.DEBUG, dataWithTag, dataWithTag.tag!!)
    }

    @Test
    fun shouldUseDefaultTag() {
        val printerMock = mock(Printer::class.java)
        val config: Config = Config("defaultTag", LogMode.STAGE, printerMock, false)

        dataWithoutTag.log(Log.DEBUG, config)

        verify(printerMock).print(Log.DEBUG, dataWithoutTag, config.defaultTag)
    }
}