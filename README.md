# Android kotlin MVVM Coroutine [![](https://jitpack.io/v/AnglePengCoding/android_mvvm.svg)](https://jitpack.io/#AnglePengCoding/android_mvvm)

<div align=start>
<img src="https://github.com/AnglePengCoding/android_mvvm/blob/main/GIF/android.png"  />
</div>

<h3> Sample Android Components Architecture on a modular word focused on the scalability, testability and maintainability written in Kotlin, following best practices using Jetpack.</h3>



<h3>Android Official website guidance</h3>

Basic understanding of Kotlin collaborative development：
(https://developer.android.google.cn/kotlin/coroutines?hl=zh-cn)


<h3>Add Dependency</h3>

```java

    implementation 'com.github.AnglePengCoding:android_mvvm:Tag'

```


<h3>Screen recording</h3>

<div align=start>
<img src="https://github.com/AnglePengCoding/android_mvvm/blob/main/GIF/mvvm_home.gif" width="250" height="300" />
<img src="https://github.com/AnglePengCoding/android_mvvm/blob/main/GIF/mvvm_login.gif" width="250" height="300" />
</div>


<div align=start>
<img src="https://github.com/AnglePengCoding/android_mvvm/blob/main/GIF/mvvm_not_net.gif" width="250" height="300" />
</div>

<h3>network request</h3>

```java


        override fun setListeners() {
              super.setListeners()
             baseToolbarBinding.mLlToolbar.setOnClickListener {
             mainViewModel.login()
              }
        }



        class MainViewModel : BaseViewModel() {

        private val mainRepository by lazy { MainRepository() }

                val refreshData: VmLiveData<LoginBean> = MutableLiveData()

                fun login() {
                launchVmRequest({
                mainRepository.login()
              }, refreshData)
        }
    }


        class MainRepository {

            suspend fun login(): BaseData<LoginBean> {
                val loginBean = RxHttp.get("http://*****/" + "version/getLatestOne")
                        .toAwait<LoginBean>()
                        .await()
                return BaseData("", loginBean)
            }

        }

```

<h3>paging mode</h3>

```java
    private var helper: QuickAdapterHelper? = null

    private fun initAdapter() {
    adapter = PestForecastAdapter()
    helper = QuickAdapterHelper.Builder(adapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onFailRetry() {
                    baseModel.searchPestListByDayF(deviceId, startTime, endTime, category)
                }

                override fun onLoad() {
                    if (pagerNumber >= pagerCount) {
                        pagerNumber = pagerCount
                        helper?.trailingLoadState = LoadState.NotLoading(true)
                    } else {

                        if (pagerNumber == pagerIndex) {
                            ++pagerNumber
                        }

                        baseModel.searchPestListByDay(
                            deviceId,
                            startTime,
                            endTime,
                            category,
                            pagerNumber
                        )

                    }


                }

                override fun isAllowLoading(): Boolean {
                    return !binding.mSmartRefreshLayout.isRefreshing
                }
            }).build()

         binding.mRecyclerView.layoutManager = LinearLayoutManager(context)
         binding.mRecyclerView.adapter = helper?.adapter


        baseModel.searchPestListByDaytBean.vmObserverDefault(this,
            onSuccess = {
                adapter.setEmptyViewLayout(mContext, R.layout.empty_view)
                adapter.isEmptyViewEnable = true

                //总页数
                pagerCount = it.result.pager.pageCount
                //当前页数
                pagerIndex = it.result.pager.pageSize

                if (pagerIndex == 1) {
                    adapter.submitList(it.result.list)
                } else {
                    adapter.addAll(it.result.list)
                }


                if (it.result.list.isEmpty()) {
                    helper?.trailingLoadState = LoadState.None
                } else {
                    if (pagerIndex >= pagerCount) {
                        helper?.trailingLoadState = LoadState.NotLoading(true)
                    } else {
                        helper?.trailingLoadState = LoadState.NotLoading(false)
                    }
                }

            },

            onComplete = {
                binding.mSmartRefreshLayout.finishRefresh()
            }
        )


    fun searchPestListByDayF(
        deviceId: String,
        startDate: String,
        endDate: String,
        category: String,
    ) {
        searchPestListByDay(deviceId, startDate, endDate, category, 1)
    }


    fun searchPestListByDay(
        deviceId: String,
        startDate: String,
        endDate: String,
        category: String,
        pageSize: Int
    ) {
        launchVmRequest({
            baseRepository.searchPestListByDay(deviceId, category, startDate, endDate,pageSize)
        }, searchPestListByDaytBean)
    }


        suspend fun searchEntForSampling(pageNumber: Int): BaseData<SearchEntForSamplingBean> {
            val mSearchEntForSamplingBean =
            RxHttp.get("http://****/ent/searchEntForSampling")
            .setHeader(
            "token",
            "app:2178a819ad3d4ab49c3bedc2a73c76d0:0200ca99534a42aaa909464b8f15e4c9"
            )
            .addQuery("pageNumber", pageNumber)
            .addQuery("pageSize", "10")
            .toAwait<SearchEntForSamplingBean>()
            .await()

            return BaseData("", mSearchEntForSamplingBean)
            }
        
         
```

<h3>preferencesDataStore Data storage</h3>

```java

        DataStoreHelper.putData("bean", "1")
        baseToolbarBinding.toolbarTitle.text = DataStoreHelper.getData("bean","")

```

***


MIT License

Copyright (c) 2023 Yuang

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
