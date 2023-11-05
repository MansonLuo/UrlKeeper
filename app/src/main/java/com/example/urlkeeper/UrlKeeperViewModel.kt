package com.example.urlkeeper

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UrlKeeperViewModel : ViewModel() {

    // pages
    private val stateUrlsList = mutableStateListOf<Page>()
    val urls
        get() = stateUrlsList.toList()

    private fun addPage(url: String) {
        stateUrlsList.add(
            Page(
                "测试标题",
                "测试文本",
                url = url
            )
        )
    }

    // loading
    private val _loading = mutableStateOf(false)
    val loading
        get() = _loading.value
    fun startLoading() {
        _loading.value = true
    }
    fun finishLoading() {
        _loading.value = false
    }

    // dialog
    private val _urlAddingDialogExpanded = mutableStateOf(false)
    val urlAddingDialogExpanded
        get() = _urlAddingDialogExpanded.value

    fun showUrlAddingDialog() {
        _urlAddingDialogExpanded.value = true
    }

    fun dismissUrlAddingDialog() {
        _urlAddingDialogExpanded.value = false
    }

    fun comformUrlAddingDialog(url: String) {
        _urlAddingDialogExpanded.value = false
        startLoading()

       addPage(url)
        finishLoading()
    }

    init {
        stateUrlsList.add(
            Page(
                title = "被中国人抢购的水果，不是榴莲，泰国人不理解：中国人品味真奇怪",
                description = "现在人们生活的水平越来越高了，因为经济水平提高了，我们就更加会享受生活了，对吃的越来越重视，尽量选择农药残留少的瓜果蔬菜，穿衣上面也不再只注重美了，还注重保暖，有句话叫做蹦迪都要带菊花茶去火，啤酒里面也要放枸杞，养生",
                url = "https://pc.yiyouliao.com/microsoft/article/rivers/newsfeed/1531576099383816194/ID0059BVWJ6ADFI.html?channel=271d662cb2d74b49aebd5be43c6b2241"
            )
        )

        stateUrlsList.add(
            Page(
                title = "中超丨综合消息：大连人降级 浙江获亚冠资格",
                description = "新华社南京11月4日电（记者王恒志）2023赛季中超联赛4日落下帷幕，主场作战的大连人队在2:0领先的大好局势下，被已提前夺冠的上海海港队连进三球击败，与深圳队一起降级，同样落败的南通支云队幸运保级。浙江队2:1击败上海申花队后拿到亚冠资格。",
                url = "https://www.msn.cn/zh-cn/sports/other/%E4%B8%AD%E8%B6%85%E4%B8%A8%E7%BB%BC%E5%90%88%E6%B6%88%E6%81%AF-%E5%A4%A7%E8%BF%9E%E4%BA%BA%E9%99%8D%E7%BA%A7-%E6%B5%99%E6%B1%9F%E8%8E%B7%E4%BA%9A%E5%86%A0%E8%B5%84%E6%A0%BC/ar-AA1jnDf3?ocid=msedgntp&pc=DCTS&cvid=65471050f66c4d1e97dd27ea62f51560&ei=14"
            )
        )

        stateUrlsList.add(
            Page(
                title = "华为余承东：小鹏“一把手”被手下忽悠了？要么很无知？",
                description = "华为常务董事、终端BG CEO、智能汽车解决方案BU董事长余承东在个人朋友圈转发了AITO汽车于11月3日发布的“OTA升级”的新闻，并评论到：有的车企，整天忙着做智能驾驶，AEB主动安全测试结果非常差，一问才知道他们连AEB基本功能居然都没有做，这让我十分吃惊！要么让手下忽悠了，要么是对汽车行业的发展缺乏最基本的认知！",
                url = "https://www.msn.cn/zh-cn/news/other/%E5%8D%8E%E4%B8%BA%E4%BD%99%E6%89%BF%E4%B8%9C-%E5%B0%8F%E9%B9%8F-%E4%B8%80%E6%8A%8A%E6%89%8B-%E8%A2%AB%E6%89%8B%E4%B8%8B%E5%BF%BD%E6%82%A0%E4%BA%86-%E8%A6%81%E4%B9%88%E5%BE%88%E6%97%A0%E7%9F%A5/ar-AA1jnxpR?ocid=msedgntp&pc=DCTS&cvid=65471050f66c4d1e97dd27ea62f51560&ei=16"
            )
        )
    }
}