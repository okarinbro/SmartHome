package locators

import DevicesNameProvider
import com.zeroc.Ice.Current
import com.zeroc.Ice.Object
import com.zeroc.Ice.ServantLocator

class DeviceProviderLocatorImpl : ServantLocator {
    var provider: DevicesNameProvider? = null

    override fun finished(p0: Current?, p1: Object?, p2: Any?) {

    }

    override fun deactivate(p0: String?) {

    }

    override fun locate(p0: Current?): ServantLocator.LocateResult {
        if (provider == null) {
            provider = DevicesNameProvider()
        }
        return ServantLocator.LocateResult(provider, p0?.id)
    }
}