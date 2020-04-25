package devices

import Smart.CameraState
import Smart.IllegalArgument
import Smart.SmartPTZCamera
import com.zeroc.Ice.Current

open class SmartPTZCameraI : SmartDeviceI(), SmartPTZCamera {
    var state = CameraState(0, 0, 0)

    override fun setPTZ(state: CameraState?, current: Current?) {
        validateState()
        if (state!!.h < 0 || state.r < 0 || state.zoom < 0) {
            throw IllegalArgument("Settings must not be negative numbers")
        }
        this.state = state
    }

    override fun getPTZ(current: Current?): CameraState {
        validateState()
        return state
    }

}