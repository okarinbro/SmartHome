import com.zeroc.Ice.Current
import com.zeroc.Ice.Identity
import com.zeroc.Ice.Object
import com.zeroc.Ice.ServantLocator
import devices.SmartFridgeI

class ServantLocatorImpl : ServantLocator {
    override fun finished(p0: Current?, p1: Object?, p2: Any?) {
        println("finished")
    }

    override fun deactivate(p0: String?) {
        println("deactivate")
    }

    override fun locate(p0: Current?): ServantLocator.LocateResult {
        val name = p0?.id?.name
        val category = p0?.id?.category
        println(name)
        println(category)
        println("locator")
        return ServantLocator.LocateResult(SmartFridgeI(), p0?.id)
    }
}