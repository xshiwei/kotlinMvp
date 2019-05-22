package com.xushiwei.kotlintest.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.gyf.barlibrary.ImmersionBar

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.xushiwei.kotlintest.di.component.DaggerMainComponent
import com.xushiwei.kotlintest.di.module.MainModule
import com.xushiwei.kotlintest.mvp.contract.MainContract
import com.xushiwei.kotlintest.mvp.presenter.MainPresenter

import com.xushiwei.kotlintest.R
import com.xushiwei.kotlintest.mvp.ui.base.BaseActivity
import com.xushiwei.kotlintest.mvp.ui.fragment.DiscoveryFragment
import com.xushiwei.kotlintest.mvp.ui.fragment.HomeFragment
import com.xushiwei.kotlintest.mvp.ui.fragment.HotFragment
import com.xushiwei.kotlintest.mvp.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter>(), MainContract.View, BottomNavigationBar.OnTabSelectedListener {

    @Inject
    lateinit var homeFragment: HomeFragment
    @Inject
    lateinit var discoveryFragment: DiscoveryFragment
    @Inject
    lateinit var hotFragment: HotFragment
    @Inject
    lateinit var mineFragment: MineFragment

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .mainModule(MainModule(this))
            .build()
            .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        bottom_navigation_bar.apply {
            activeColor = R.color.color_gray_translucent
            inActiveColor = R.color.color_gray
            setBarBackgroundColor(R.color.white)
            setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            setMode(BottomNavigationBar.MODE_FIXED)
        }
        val lastSelectedPosition = 0
        bottom_navigation_bar.apply {
            addItem(
                BottomNavigationItem(R.mipmap.ic_home_selected, "每日精选").setInactiveIcon(
                    ContextCompat.getDrawable(
                        context,
                        R.mipmap.ic_home_normal
                    )
                )
            )
            addItem(
                BottomNavigationItem(
                    R.mipmap.ic_discovery_selected,
                    "发现"
                ).setInactiveIcon(ContextCompat.getDrawable(context, R.mipmap.ic_discovery_normal))
            )
            addItem(
                BottomNavigationItem(R.mipmap.ic_hot_selected, "热门").setInactiveIcon(
                    ContextCompat.getDrawable(
                        context,
                        R.mipmap.ic_hot_normal
                    )
                )
            )
            addItem(
                BottomNavigationItem(R.mipmap.ic_mine_selected, "我的").setInactiveIcon(
                    ContextCompat.getDrawable(
                        context,
                        R.mipmap.ic_mine_normal
                    )
                )
            )
            setFirstSelectedPosition(lastSelectedPosition)
            initialise()
        }
        bottom_navigation_bar.setTabSelectedListener(this)
        mPresenter?.setBottomNavigationItem(bottom_navigation_bar, 8, 22, 11)
        if (savedInstanceState != null) {
            homeFragment = supportFragmentManager.getFragment(savedInstanceState, HOME_FRAGMENT_KEY) as HomeFragment
            discoveryFragment =
                supportFragmentManager.getFragment(savedInstanceState, DISCOVERY_FRAGMENT_KEY) as DiscoveryFragment
            hotFragment = supportFragmentManager.getFragment(savedInstanceState, HOT_FRAGMENT_KEY) as HotFragment
            mineFragment = supportFragmentManager.getFragment(savedInstanceState, MINE_FRAGMENT_KEY) as MineFragment
            addToList(homeFragment)
            addToList(discoveryFragment)
            addToList(hotFragment)
            addToList(mineFragment)
        } else {
            initFragment()
        }
    }

    private fun addToList(fragment: Fragment) {
        fragments.add(fragment)
    }

    private fun initFragment() {
        homeFragment = HomeFragment.newInstance("home")
        addFragment(homeFragment)
        showFragment(homeFragment)
        ImmersionBar.with(this).transparentStatusBar()?.fullScreen(true)?.statusBarDarkFont(false)?.init()
    }

    private fun showFragment(fragment: Fragment) {
        fragments.forEach {
            if (it != fragment) {
                supportFragmentManager.beginTransaction().hide(fragment).commit()
            }
        }
        supportFragmentManager.beginTransaction().show(fragment).commit()
    }

    private fun addFragment(fragment: Fragment) {
        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.home_container, fragment).commit()
            fragments.add(fragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (homeFragment.isAdded) {
            supportFragmentManager.putFragment(outState!!, HOME_FRAGMENT_KEY, homeFragment)
        }
        if (discoveryFragment.isAdded) {
            supportFragmentManager.putFragment(outState!!, DISCOVERY_FRAGMENT_KEY, discoveryFragment)
        }
        if (hotFragment.isAdded) {
            supportFragmentManager.putFragment(outState!!, HOT_FRAGMENT_KEY, hotFragment)
        }
        if (mineFragment.isAdded) {
            supportFragmentManager.putFragment(outState!!, MINE_FRAGMENT_KEY, mineFragment)
        }
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    override fun onTabReselected(position: Int) {
        when (position) {
            0 -> {
                addFragment(homeFragment)
                showFragment(homeFragment)
            }
            1 -> {
                addFragment(discoveryFragment)
                showFragment(discoveryFragment)
            }
            2 -> {
                addFragment(hotFragment)
                showFragment(hotFragment)
            }
            3 -> {
                addFragment(mineFragment)
                showFragment(mineFragment)
            }
            else -> {
            }
        }
    }

    override fun onTabUnselected(position: Int) {
    }

    override fun onTabSelected(position: Int) {
    }

    private var firstTime: Long = 0

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                var secondTime = System.currentTimeMillis()
                if (secondTime - firstTime > 2000) {
                    ArmsUtils.makeText(this, "再按一次退出应用")
                    firstTime = secondTime
                    return true
                } else {
                    ArmsUtils.exitApp()
                }
            }
            else -> {
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    companion object {
        private const val HOME_FRAGMENT_KEY = "homeFragment"
        private const val DISCOVERY_FRAGMENT_KEY = "discoveryFragment"
        private const val HOT_FRAGMENT_KEY = "hotFragment"
        private const val MINE_FRAGMENT_KEY = "mineFragment"

        private val fragments = arrayListOf<Fragment>()
    }
}
