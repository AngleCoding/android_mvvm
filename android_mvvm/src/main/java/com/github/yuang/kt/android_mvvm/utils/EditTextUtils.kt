import android.view.MotionEvent
import android.view.View
import android.widget.EditText

/**
 * @author AnglePenCoding
 * Created by on 2023/2/7 0007
 * @website https://github.com/AnglePengCoding
 */
object EditTextUtils{

    fun hideKeyboard(view: View?, event: MotionEvent?): Boolean {
        if (view is EditText) {
            val location = intArrayOf(0, 0)
            view.getLocationInWindow(location)
            //获取现在拥有焦点的控件view的位置，即EditText
            //获取现在拥有焦点的控件view的位置，即EditText
            val left = location[0]
            val top = location[1]
            val bottom = top + view.getHeight()
            val right = left + view.getWidth()
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            val isInEt = event!!.x > left && event.x < right && event.y > top && event.y < bottom
            return !isInEt
        }
        return false
    }
}