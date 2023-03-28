# >kotlin协程 


<h3>kotlin协程+Rxhttp+ViewBinding，网络加载异常页面显示以及加载弹窗，支持dialog加载，同时也支持页面加载，使用不同网络请求即可对应不同的加载模式等</h3>



<h3>添加依赖</h3>

```java

  implementation 'com.github.AnglePengCoding:android_picture:Tag'

```


<h3>录屏</h3>

<div align=start>
<img src="https://github.com/AnglePengCoding/android_picture/blob/main/GIF/image.gif" width="250" height="300" />
<img src="https://github.com/AnglePengCoding/android_picture/blob/main/GIF/camera.gif" width="250" height="300" />
</div>


<div align=start>
<img src="https://github.com/AnglePengCoding/android_picture/blob/main/GIF/dtgif.gif" width="250" height="300" />
<img src="https://github.com/AnglePengCoding/android_picture/blob/main/GIF/dtgif2.gif" width="250" height="300" />
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
                val loginBean = RxHttp.get("http://120.221.72.37:8095/" + "version/getLatestOne")
                        .toAwait<LoginBean>()
                        .await()
                return BaseData("", loginBean)
            }

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