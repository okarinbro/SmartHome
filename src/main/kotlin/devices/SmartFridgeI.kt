package devices

import Smart.DeviceFull
import Smart.ElementNotFound
import Smart.SmartFridge
import com.zeroc.Ice.Current
import java.util.*

open class SmartFridgeI : SmartDeviceI(), SmartFridge {
    var products = LinkedList<String>()
    var maxSize = 10

    override fun addElement(name: String?, current: Current?) {
        validateState()
        if (products.size == maxSize) {
            throw DeviceFull("Maximum size of device ($maxSize) has been reached.")
        } else {
            products.add(name!!)
        }
    }

    override fun containsElement(name: String?, current: Current?): Boolean {
        validateState()
        return products.contains(name);
    }

    override fun removeElement(name: String?, current: Current?) {
        if (!products.contains(name)) {
            throw ElementNotFound("$name not found in this fridge")
        }
        products.remove(name)
    }

}