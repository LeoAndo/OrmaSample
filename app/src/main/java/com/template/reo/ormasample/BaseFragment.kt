package com.template.reo.ormasample

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.template.reo.ormasample.di.Injectable

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), Injectable