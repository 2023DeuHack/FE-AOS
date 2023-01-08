package com.example.deuHack.ui.navigation

import androidx.annotation.DrawableRes
import com.example.deuHack.R

sealed class BottomNavigationItem(
    val title:String,
    @DrawableRes val icon:Int,
    @DrawableRes val iconClicked:Int,
    val route:String)
{
    object Home: BottomNavigationItem("홈",R.drawable.icon_home,R.drawable.icon_home_clicked,"home")
    object Search: BottomNavigationItem("검색",R.drawable.icon_search,R.drawable.icon_search_clicked,"search")
    object Lils: BottomNavigationItem("릴스",R.drawable.icon_play,R.drawable.icon_play_clicked,"lils")
    object Shop: BottomNavigationItem("쇼핑",R.drawable.icon_shop,R.drawable.icon_shop_clicked,"shop")
    object Profile: BottomNavigationItem("프로필",R.drawable.icon_profile,R.drawable.icon_profile,"profile")
}