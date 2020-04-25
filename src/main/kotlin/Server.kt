import com.zeroc.Ice.Communicator
import com.zeroc.Ice.Util
import locators.CamServantLocator
import locators.CoolingServantLocator
import locators.WashServantLocator
import kotlin.system.exitProcess

class Server(val args: Array<String>) {
    fun runServer() {
        var communicator: Communicator? = null
        var status = 0
        try {
            communicator = Util.initialize(args)
            val adapter = communicator.createObjectAdapter("Adapter1");
            adapter.addServantLocator(CoolingServantLocator(), "cooling")
            adapter.addServantLocator(WashServantLocator(), "wash")
            adapter.addServantLocator(CamServantLocator(), "cam")
            adapter.activate()
            communicator.waitForShutdown()
        } catch (e: Exception) {
            System.err.println(e)
            status = 1
        }

        status = cleanUp(communicator, status)
        exitProcess(status)
    }

    private fun cleanUp(communicator: Communicator?, status: Int): Int {
        var stat = status
        if (communicator != null) {
            try {
                communicator.destroy()
            } catch (e: java.lang.Exception) {
                System.err.println(e)
                stat = 1
            }
        }
        return stat
    }
}

fun main(args: Array<String>) {
    val server = Server(args)
    server.runServer()
}