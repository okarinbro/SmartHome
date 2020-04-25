package devices

import Smart.DeviceTurnedOff
import Smart.SmartDevice
import com.zeroc.Ice.Current

abstract class SmartDeviceI : SmartDevice {
    var turnedOn = true

    override fun turnOff(current: Current?) {
        print("Turned off")
        turnedOn = false
    }

    override fun turnOn(current: Current?) {
        print("Turned on")
        turnedOn = true
    }

    fun validateState() {
        if (!turnedOn) {
            throw DeviceTurnedOff("You should turn it on before use")
        }
    }
}