package devices

import Smart.SmartArtisticCamera
import Smart.style
import com.zeroc.Ice.Current

class SmartArtCameraI : SmartPTZCameraI(), SmartArtisticCamera {
    private var style = Smart.style.POLAROID

    override fun getStyle(current: Current?): style {
        validateState()
        return this.style;
    }

    override fun setStyle(currentStyle: style?, current: Current?) {
        validateState()
        this.style = currentStyle!!
    }
}