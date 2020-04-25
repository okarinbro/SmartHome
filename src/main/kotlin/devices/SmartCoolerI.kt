package devices

import Smart.IllegalArgument
import Smart.SmartCooler
import com.zeroc.Ice.Current

class SmartCoolerI : SmartFridgeI(), SmartCooler {
    var temperature = -5

    override fun setTemperature(temperature: Int, current: Current?) {
        validateState()
        if (temperature > 20 && temperature < -50) {
            throw IllegalArgument("Temperature should be from range (-50; 20)")
        }
        this.temperature = temperature
    }

    override fun getTemperature(current: Current?): Int {
        validateState()
        return temperature
    }
}