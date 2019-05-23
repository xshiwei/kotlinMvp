package com.xushiwei.kotlintest.di.module

import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.xushiwei.kotlintest.mvp.contract.CategoryContract
import com.xushiwei.kotlintest.mvp.model.CategoryModel
import com.xushiwei.kotlintest.mvp.ui.adapter.CategoryAdapter


@Module
//构建CategoryModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CategoryModule(private val view: CategoryContract.View) {
    @FragmentScope
    @Provides
    fun provideCategoryView(): CategoryContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideCategoryModel(model: CategoryModel): CategoryContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideCategoryList(): ArrayList<CategoryBean> {
        return arrayListOf()
    }

    @FragmentScope
    @Provides
    fun provideCategoryAdapter(list: ArrayList<CategoryBean>): CategoryAdapter {
        return CategoryAdapter(list)
    }
}
