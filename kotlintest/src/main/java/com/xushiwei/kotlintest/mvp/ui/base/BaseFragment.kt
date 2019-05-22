package com.xushiwei.kotlintest.mvp.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.Unbinder
import com.jess.arms.base.delegate.IFragment
import com.jess.arms.integration.cache.Cache
import com.jess.arms.integration.cache.CacheType
import com.jess.arms.integration.lifecycle.FragmentLifecycleable
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.ArmsUtils
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

import com.trello.rxlifecycle2.android.FragmentEvent
import javax.inject.Inject


abstract class BaseFragment<P : IPresenter> : Fragment(), IFragment, FragmentLifecycleable {

    protected val TAG = this.javaClass.simpleName
    private val mLifecycleSubject = BehaviorSubject.create<FragmentEvent>()
    private var mCache: Cache<String, Any>? = null
    private var mUnbinder: Unbinder? = null
    private var mContext: Context? = null
    @Inject
    @JvmField
    protected var mPresenter: P? = null//如果当前页面逻辑简单, Presenter 可以为 null

    @Suppress("UNCHECKED_CAST")
    @NonNull
    override fun provideCache(): Cache<String, Any> {
        if (this.mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(activity)
                .cacheFactory().build(CacheType.ACTIVITY_CACHE) as Cache<String, Any>
        }
        return mCache as Cache<String, Any>
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent> {
        return mLifecycleSubject
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder?.unbind()
        }
        mPresenter?.onDestroy()//释放资源
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun useEventBus(): Boolean {
        return true
    }

}