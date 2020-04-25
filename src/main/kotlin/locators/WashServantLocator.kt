package locators

import Smart.SmartDevice
import com.zeroc.Ice.Current
import com.zeroc.Ice.Identity
import com.zeroc.Ice.Object
import com.zeroc.Ice.ServantLocator
import devices.SmartDishwasherI
import devices.SmartPTZCameraI

class WashServantLocator : ServantLocator {
    private val objects = HashMap<Identity, SmartDevice>()

    override fun finished(p0: Current?, p1: Object?, p2: Any?) {
    }

    override fun deactivate(p0: String?) {
    }

    override fun locate(p0: Current?): ServantLocator.LocateResult {
        val id = p0!!.id
        var obj = objects[id]
        if (obj == null) {
            obj = SmartDishwasherI()
            objects[id] = obj
        }
        return ServantLocator.LocateResult(obj, id)
    }

}