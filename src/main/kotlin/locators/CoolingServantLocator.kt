package locators

import Smart.SmartDevice
import com.zeroc.Ice.Current
import com.zeroc.Ice.Identity
import com.zeroc.Ice.Object
import com.zeroc.Ice.ServantLocator
import devices.SmartCoolerI
import devices.SmartFridgeI
import java.lang.IllegalArgumentException

class CoolingServantLocator : ServantLocator {
    private val objects = HashMap<Identity, SmartDevice>()


    override fun finished(p0: Current?, p1: Object?, p2: Any?) {
    }

    override fun deactivate(p0: String?) {
    }

    override fun locate(p0: Current?): ServantLocator.LocateResult {
        val id = p0!!.id
        val name = id.name
        return if (id in objects.keys) {
            ServantLocator.LocateResult(objects[id], id)
        } else {
            val obj = locateNewInstance(name)
            objects[id] = obj
            ServantLocator.LocateResult(objects[id], id)
        }
    }

    private fun locateNewInstance(name: String): SmartDevice {
        return when {
            name.startsWith("fridge") -> {
                SmartFridgeI()
            }
            name.startsWith("cooler") -> {
                SmartCoolerI()
            }
            else -> {
                println("unknown?")
                throw IllegalArgumentException("Object unknown")
            }
        }
    }

}