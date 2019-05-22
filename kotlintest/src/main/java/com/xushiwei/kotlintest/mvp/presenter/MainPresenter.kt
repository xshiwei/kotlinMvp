package com.xushiwei.kotlintest.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.xushiwei.kotlintest.mvp.contract.MainContract
import android.view.Gravity
import android.widget.FrameLayout
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.xushiwei.kotlintest.app.application.MyApplication


@ActivityScope
class MainPresenter
@Inject
constructor(model: MainContract.Model, rootView: MainContract.View) :
    BasePresenter<MainContract.Model, MainContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    /*
     * @param bottomNavigationBar，需要修改的 BottomNavigationBar
     * @param space 图片与文字之间的间距
     * @param imgLen 单位：dp，图片大小，应 <= 36dp
     * @param textSize 单位：dp，文字大小，应 <= 20dp
     *
     *  使用方法：直接调用setBottomNavigationItem(bottomNavigationBar, 6, 26, 10);
     *  代表将bottomNavigationBar的文字大小设置为10dp，图片大小为26dp，二者间间距为6dp
     *
     * */
    fun setBottomNavigationItem(bottomNavigationBar: BottomNavigationBar, space: Int, imgLen: Int, textSize: Int) {
        val barClass = bottomNavigationBar.javaClass
        val fields = barClass.declaredFields
        for (field in fields) {
            field.isAccessible = true
            if ("mTabContainer" == field.name) {
                try {
                    //反射得到 mTabContainer
                    val mTabContainer = field.get(bottomNavigationBar) as LinearLayout
                    for (j in 0 until mTabContainer.childCount) {
                        //获取到容器内的各个Tab
                        val view = mTabContainer.getChildAt(j)
                        //获取到Tab内的各个显示控件
                        var params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(56))
                        val container =
                            view.findViewById<View>(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_container)
                        container.layoutParams = params
                        container.setPadding(dip2px(12), dip2px(0), dip2px(12), dip2px(0))

                        //获取到Tab内的文字控件
                        val labelView =
                            view.findViewById<TextView>(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title)
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize.toFloat())
                        labelView.includeFontPadding = false
                        labelView.setPadding(0, 0, 0, dip2px(20 - textSize - space / 2))

                        //获取到Tab内的图像控件
                        val iconView =
                            view.findViewById<ImageView>(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon)
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = FrameLayout.LayoutParams(dip2px(imgLen), dip2px(imgLen))
                        params.setMargins(0, 0, 0, space / 2)
                        params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                        iconView.layoutParams = params
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun dip2px(dpValue: Int): Int {
        val scale = MyApplication.instance.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
