package com.example.deuHack.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deuHack.ui.compose.ui.Colors
import com.example.deuHack.ui.compose.ui.HomeTopBarIcon
import com.example.deuHack.ui.content.ReplyTopBar
import com.example.deuHack.ui.navigation.NavigationViewModel

@Composable
fun ReplyView(
    onNavigatePopBackStack:()->Unit,
    navigationViewModel: NavigationViewModel
){
    navigationViewModel.setBottomNavigationState(false)
    Column(Modifier.background(Color.White)) {
        ReplyTopBar(onNavigatePopBackStack)
        ReplyItems()
    }
}

@Composable
fun ReplyItems(){
    val replyState = remember {
        mutableStateOf(false)
    }
    val isReplyExistState = remember {
        mutableStateOf(true)
    }
    LazyColumn(
        contentPadding = PaddingValues(
            start = 10.dp, end = 10.dp, bottom = 10.dp)
    )
    {
        items(2){
            ReplyItem(0.dp)
            if(isReplyExistState.value){
                ReplyInReplyItem(replyState)
            }
        }
        if(replyState.value){
            itemsIndexed(listOf(0..2)){item,index->
                ReplyItem(48.dp)
            }
        }
    }
}

@Composable
fun ReplyItem(startPadding:Dp){
    Row(
        Modifier.padding(top = 10.dp, start = startPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(
            id = com.example.deuHack.R.drawable.icon_profile),
            contentDescription = null,
            modifier = Modifier.size(35.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row() {
                Text(text = "아이디" , fontSize = 14.sp)
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "몇주",
                    color = Colors.gray_999b9e,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "텍스트", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "답글 달기",
                color = Colors.gray_999b9e,
                fontWeight = FontWeight.Bold
            )
        }
        Image(painter = painterResource(
            id = com.example.deuHack.R.drawable.icon_heart),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Colors.gray_DADDE1))
    }
}

@Composable
fun ReplyInReplyItem(replyState:MutableState<Boolean>){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            replyState.value = !replyState.value
        }) {
        Spacer(modifier = Modifier.width(48.dp))
        Text(
            text = "",
            modifier = Modifier
                .height(1.dp)
                .width(30.dp)
                .background(Colors.gray_DADDE1)
        )
        Text(text = "답글 2개 보기")
    }
}