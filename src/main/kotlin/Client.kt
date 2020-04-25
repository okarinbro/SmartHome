import Smart.DeviceFull
import Smart.SmartFridgePrx
import com.zeroc.Ice.Communicator
import com.zeroc.Ice.LocalException
import com.zeroc.Ice.ObjectPrx
import com.zeroc.Ice.Util
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.system.exitProcess

class Client {
    private val objects = HashSet<ObjectPrx>()
    private val initializedPrx = HashMap<String, ObjectPrx>()

    private val names = mapOf(
            "fridge1" to "cooling/fridge1:tcp -h localhost -p 10000",
            "fridge2" to "cooling/fridge2:tcp -h localhost -p 10000",
            "cooler1" to "cooling/cooler1:tcp -h localhost -p 10000",
            "camera1" to "cam/camera1:tcp -h localhost -p 10000",
            "camera2" to "cam/camera2:tcp -h localhost -p 10000",
            "camera3" to "cam/camera3:tcp -h localhost -p 10000",
            "dishwasher1" to "wash/dishwasher1:tcp -h localhost -p 10000"
    )


    fun runClient(args: Array<String>) {
        var status = 0
        var communicator: Communicator? = null

        try {
            communicator = Util.initialize(args)

            var line: String? = null
            val reader = BufferedReader(InputStreamReader(System.`in`))
            do {
                try {
                    print("==> ")
                    System.out.flush()
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    if (line in names.keys) {
                        val prxName = names[line]
                        val prx = initializedPrx[prxName]


                        val prxObj = communicator.stringToProxy(prxName)
                        println(prxObj)
                        val x = SmartFridgePrx.checkedCast(prxObj)
                        println("dodawanie...")
                        try {
                            x.addElement("xd")
                        } catch (e: DeviceFull) {
                            println(e.reason)
                        }
                        println("doooone...")
                    }


                } catch (ex: IOException) {
                    System.err.println(ex)
                }
            } while (line != "x")

        } catch (e: LocalException) {
            e.printStackTrace()
            System.err.println(e.message + " dasdsadasdasd")
            status = 1
        } catch (e: Exception) {
            System.err.println(e.message + " co to sie ddzieje" + e)
            status = 1
        }
        status = cleanUp(communicator, status)
        exitProcess(status)
    }

    private fun cleanUp(communicator: Communicator?, status: Int): Int {
        var status1 = status
        if (communicator != null) {
            // Clean up
            //
            try {
                communicator.destroy()
            } catch (e: Exception) {
                System.err.println(e.message)
                status1 = 1
            }
        }
        return status1
    }
}

fun main(args: Array<String>) {
    val client = Client()
    client.runClient(args)
}