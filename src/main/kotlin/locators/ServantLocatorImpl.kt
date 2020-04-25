package locators

import DevicesNameProvider
import Smart.SmartDevice
import com.zeroc.Ice.*
import devices.*

class ServantLocatorImpl : ServantLocator {
    private val objects = HashMap<Identity, SmartDevice>()

    override fun finished(p0: Current?, p1: Object?, p2: Any?) {
    }

    override fun deactivate(p0: String?) {
    }

    override fun locate(p0: Current?): ServantLocator.LocateResult {
        var obj = objects.getOrDefault(p0?.id, null)
        if (obj == null) {
            obj = resolveDevice(p0?.id)
            if (obj != null) {
                objects[p0!!.id] = obj
            }
        }
        return ServantLocator.LocateResult(obj, p0?.id)
    }

    private fun resolveDevice(id: Identity?): SmartDevice? {
        val names = DevicesNameProvider().getDevices(null)
        val name = id?.name

        if (name !in names) {
            throw ObjectNotFoundException()
        }
        if (name != null) {
            when {
                name.startsWith("fridge") -> {
                    return SmartFridgeI()
                }
                name.startsWith("camera") -> {
                    return SmartPTZCameraI()
                }
                name.startsWith("art") -> {
                    return SmartArtCameraI()
                }
                name.startsWith("therm") -> {
                    return SmartThermalCameraI()
                }
                name.startsWith("cooler") -> {
                    return SmartCoolerI()
                }
                name.startsWith("dish") -> {
                    return SmartDishwasherI()
                }
            }
        }
        throw ObjectNotExistException()
    }
}