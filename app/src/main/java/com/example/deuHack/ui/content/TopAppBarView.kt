package com.example.deuHack.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.deuHack.R
import com.example.deuHack.data.domain.model.UserModel
import com.example.deuHack.ui.compose.ui.Colors
import com.example.deuHack.ui.compose.ui.HomeTopBarIcon
import com.example.deuHack.ui.posting.PostingViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeTopBar(onNavigateToPosting : () -> Unit){
    SmallTopAppBar(
        title = {
            Row {
                Image(painter = painterResource(id =  R.drawable.instagram_logo),
                    contentDescription = null)
                Spacer(modifier = Modifier.width(3.dp))
                PopupWindowDialog()

            }
        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.icon_add),
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onNavigateToPosting)
            )
            HomeTopBarIcon(id = R.drawable.icon_heart)
            HomeTopBarIcon(id = R.drawable.icon_dm)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.White)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileTopBar(
    modalBottomSheetState:ModalBottomSheetState,
    userState: UserModel
){
    val coroutineScope = rememberCoroutineScope()
    SmallTopAppBar(
        title = {
            Row {
                Text(text = userState.userNickName, fontSize = 20.sp, fontWeight = FontWeight.W900)
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                )
            }
        },
        actions = {
            HomeTopBarIcon(id = R.drawable.icon_plus)
            HomeTopBarIcon(id = R.drawable.icon_hamburger)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.White)
    )
}

@Composable
fun ReplyTopBar(onNavigatePopBackStack:()->Unit){
    SmallTopAppBar(
        title = {
            Text(text = "댓글", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = onNavigatePopBackStack) {
                Icon(painter = painterResource(id = com.example.deuHack.R.drawable.icon_left_arrow),
                    contentDescription = null)
            }
        },
        actions = {
            HomeTopBarIcon(id = com.example.deuHack.R.drawable.icon_dm)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.White)
    )
}

@Composable
fun PostingTopBar(
    onNavigatePopBackStack:()->Unit,
    postingViewModel: PostingViewModel
){
    SmallTopAppBar(
        title = {
            Text(text = "새 게시물", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = onNavigatePopBackStack) {
                Icon(painter = painterResource(id = com.example.deuHack.R.drawable.icon_left_arrow),
                    contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(painter = painterResource(id = R.drawable.icon_check),
                    contentDescription = null,
                    tint = Colors.Blue_1877F2
                )

            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.White)
    )
}


@Composable
fun PopupWindowDialog() {
    val openDialog = remember { mutableStateOf(false) }
    val buttonTitle = remember {
        mutableStateOf("Show Pop Up")
    }

    Column() {
        Image(
            painter = painterResource(
                id = R.drawable.ic_baseline_keyboard_arrow_down_24),
            contentDescription = null,
            modifier = Modifier.clickable {
                openDialog.value = !openDialog.value
            })

        Box {
            if (openDialog.value) {
                buttonTitle.value = "Hide Pop Up"
                Popup(
                    alignment = Alignment.TopEnd,
                    properties = PopupProperties()
                ) {
                    Box(
                        Modifier
                            .size(170.dp, 100.dp)
                            .padding(10.dp)
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,

                            modifier = Modifier.padding(10.dp)
                        )
                        {
                            Row(Modifier.weight(1f)) {
                                Text(text = "팔로잉", fontSize = 14.sp, modifier = Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.icon_following)
                                    , contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(Modifier.weight(1f)) {
                                Text(text = "즐겨찾기", fontSize = 14.sp , modifier = Modifier.weight(1f))
                                Image(painter = painterResource(id = R.drawable.icon_star),
                                    contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}