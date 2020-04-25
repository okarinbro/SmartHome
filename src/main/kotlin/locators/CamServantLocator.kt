package locators

import Smart.SmartDevice
import com.zeroc.Ice.Current
import com.zeroc.Ice.Identity
import com.zeroc.Ice.Object
import com.zeroc.Ice.ServantLocator
import devices.SmartArtCameraI
import devices.SmartPTZCameraI
import devices.SmartThermalCameraI

class CamServantLocator : ServantLocator {
    private val objects = HashMap<Identity, SmartDevice>()

    override fun finished(p0: Current?, p1: Object?, p2: Any?) {
    }

    override fun deactivate(p0: String?) {
    }

    override fun locate(p0: Current?): ServantLocator.LocateResult {
        val id = p0!!.id
        var obj = objects[id]
        if (obj == null) {
            obj = resolveDevice(id)
            objects[id] = obj
        }
        return ServantLocator.LocateResult(obj, id)
    }

    private fun resolveDevice(id: Identity): SmartDevice {
        return when {
            id.name.startsWith("art") -> {
                SmartArtCameraI()
            }
            id.name.startsWith("therm") -> {
                SmartThermalCameraI()
            }
            else -> {
                SmartPTZCameraI()
            }
        }
    }


}