package com.xushiwei.kotlintest.mvp.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.InflateException
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.jess.arms.base.delegate.IActivity
import com.jess.arms.integration.cache.Cache
import com.jess.arms.integration.cache.CacheType
import com.jess.arms.integration.lifecycle.ActivityLifecycleable
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.ArmsUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

import com.jess.arms.utils.ThirdViewUtil.convertAutoView
import java.lang.Exception
import javax.inject.Inject
import com.gyf.barlibrary.ImmersionBar
import android.view.WindowManager
import android.R


abstract class BaseActivity<P : IPresenter?> : AppCompatActivity(), IActivity, ActivityLifecycleable {

    protected val TAG = this.javaClass.simpleName
    private val mLifecycleSubject = BehaviorSubject.create<ActivityEvent>()
    private var mCache: Cache<String, Any>? = null
    private var mUnbinder: Unbinder? = null
    protected var mImmersionBar: ImmersionBar? = null


    @Inject
    @JvmField
    protected var mPresenter: P? = null//如果当前页面逻辑简单, Presenter 可以为 null

    @Suppress("UNCHECKED_CAST")
    @NonNull
    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache =
                ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE) as Cache<String, Any>
        }
        return mCache as Cache<String, Any>
    }


    override fun provideLifecycleSubject(): Subject<ActivityEvent> {
        return mLifecycleSubject
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        return convertAutoView(name, context, attrs) ?: super.onCreateView(name, context, attrs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val layoutResId = initView(savedInstanceState)
            if (layoutResId != 0) {
                setContentView(layoutResId)
                mUnbinder = ButterKnife.bind(this)
            }
        } catch (e: Exception) {
            if (e is InflateException) {
                throw e
            }
            e.printStackTrace()
        }
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder?.unbind()
        }
        this.mUnbinder = null
        if (mPresenter != null) {
            mPresenter?.onDestroy()//释放资源
        }
        this.mPresenter = null
        if (mImmersionBar != null) {
            mImmersionBar?.destroy()
        }  //在BaseActivity里销毁
    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun useFragment(): Boolean {
        return true
    }

    protected fun initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.statusBarColor(R.color.white)?.statusBarDarkFont(true, 0.2f)?.keyboardEnable(true)
            ?.keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)?.init()
    }
}