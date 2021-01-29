package com.template.reo.ormasample.di

import com.template.reo.ormasample.ui.dashboard.DashboardFragment
import com.template.reo.ormasample.ui.home.HomeFragment
import com.template.reo.ormasample.ui.notifications.NotificationsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {
    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    fun contributeNotificationsFragment(): NotificationsFragment
}