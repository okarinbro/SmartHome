import Smart.DevicesProvider
import com.zeroc.Ice.Current

class DevicesNameProvider : DevicesProvider {
    override fun getDevices(current: Current?): Array<String> {
        return arrayOf("fridge1", "fridge2", "cooler1", "camera1", "camera2", "camera3",
                "artcamera1", "thermcamera1", "dishwasher1")
    }
}