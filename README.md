# Android kotlin协程 [![](https://jitpack.io/v/AnglePengCoding/android_mvvm.svg)](https://jitpack.io/#AnglePengCoding/android_mvvm)


<h3>kotlin协程+Rxhttp+ViewBinding，网络加载异常页面显示以及加载弹窗，支持dialog加载，同时也支持页面加载，使用不同网络请求即可对应不同的加载模式等</h3>



<h3>Android官网指导</h3>

Kotlin协程开发基础了解：
(https://developer.android.google.cn/kotlin/coroutines?hl=zh-cn)


<h3>添加依赖</h3>

```java

  implementation 'com.github.AnglePengCoding:android_picture:Tag'

```


<h3>录屏</h3>

<div align=start>
<img src="https://github.com/AnglePengCoding/android_mvvm/blob/main/GIF/mvvm_home.gif" width="250" height="300" />
<img src="https://github.com/AnglePengCoding/android_mvvm/blob/main/GIF/mvvm_login.gif" width="250" height="300" />
</div>


<div align=start>
<img src="https://github.com/AnglePengCoding/android_mvvm/blob/main/GIF/mvvm_not_net.gif" width="250" height="300" />
</div>

<h3>网络请求</h3>

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

<h3>分页模式</h3>

```java

    private fun initAdapter() {
        adapter = SearchEntForSamplingAdapter()
        helper = QuickAdapterHelper.Builder(adapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onFailRetry() {
                    HomeViewModel.searchEntForSamplingF()
                }

                override fun onLoad() {
                    HomeViewModel.searchEntForSamplingMore()
                }

                override fun isAllowLoading(): Boolean {
                    return true
                }


            }).build()

        binding.mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        binding.mRecyclerView.adapter = helper?.adapter
    }

    private fun initRefreshData() {
            HomeViewModel.refreshData.vmObserverLoading(this,
            onSuccess = {
            pageNumber = it.result.pager.pageNumber
            if (it.result.pager.pageNumber == 1) {
            adapter.submitList(it.result.list)
            } else {
            adapter.addAll(it.result.list)
            }

            if (it.result.pager.pageNumber >= it.result.pager.recordCount) {
            helper?.trailingLoadState = LoadState.NotLoading(true)
            } else {
            helper?.trailingLoadState = LoadState.NotLoading(false)
            }
            }, onComplete = {
            binding.mSmartRefreshLayout.finishRefresh()
            }
            )

         }


    class HomeViewModel : BaseViewModel() {

    private val mHomeRepository by lazy { HomeRepository() }

            val refreshData: VmLiveData<SearchEntForSamplingBean> = MutableLiveData()

            var pageNumber: Int = 1


            fun searchEntForSamplingF() {
            pageNumber = 1
            searchEntForSampling(this.pageNumber)
            }

            fun searchEntForSamplingMore() {
            this.pageNumber++
            searchEntForSampling(this.pageNumber)
            }

    private fun searchEntForSampling(pageNumber: Int) {
            launchVmRequest({
            mHomeRepository.searchEntForSampling(pageNumber)
            }, refreshData)
            }
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


***
<h3>欢迎帅哥美女打赏，在下感激不尽！！！</h3>

<div align=start>
<img src="https://github.com/AnglePengCoding/android_picture/blob/main/GIF/wx.jpg" width="250" height="300" />

<img src="https://github.com/AnglePengCoding/android_picture/blob/main/GIF/zfb.jpg" width="250" height="300" />
</div>


```java


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