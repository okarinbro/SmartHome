package devices

import Smart.CameraState
import Smart.IllegalArgument
import Smart.SmartThermalCamera
import com.zeroc.Ice.Current

class SmartThermalCameraI : SmartPTZCameraI(), SmartThermalCamera {
    var minTemperature: Float = -50.0F
    var maxTemperature: Float = 100.0F

    override fun setMaxTemp(temperature: Float, current: Current?) {
        validateState()
        if (temperature > 200) {
            throw IllegalArgument("Upper bound of temperature is 200")
        }
        this.maxTemperature = temperature
    }

    override fun getMaxTemp(current: Current?): Float {
        validateState()
        return this.maxTemperature
    }

    override fun setMinTemp(temperature: Float, current: Current?) {
        validateState()
        if (temperature < -200) {
            throw IllegalArgument("Lower bound of temperature is -200")
        }
        this.minTemperature = temperature
    }

    override fun getMinTemp(current: Current?): Float {
        validateState()
        return this.minTemperature
    }
}