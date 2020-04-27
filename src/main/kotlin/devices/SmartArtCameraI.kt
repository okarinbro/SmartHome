package devices

import Smart.IllegalArgument
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
        if (currentStyle == null)
            throw IllegalArgument("style cannot be null")
        this.style = currentStyle
    }
}