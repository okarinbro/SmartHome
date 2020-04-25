package devices

import Smart.DeviceCannotBeStarted
import Smart.SmartDishwasher
import com.zeroc.Ice.Current

class SmartDishwasherI : SmartDeviceI(), SmartDishwasher {
    var isFree = true

    override fun isFree(current: Current?): Boolean {
        validateState()
        return isFree
    }

    override fun start(time: Int, current: Current?) {
        validateState()
        if (!isFree) {
            throw DeviceCannotBeStarted("Dishwasher is already in use")
        }
        isFree = false;
    }

    override fun unload(current: Current?) {
        validateState()
        isFree = true
    }

}