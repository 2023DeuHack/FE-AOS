package com.example.deuHack.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deuHack.R
import com.example.deuHack.data.domain.model.PostModel
import com.example.deuHack.data.domain.model.UserModel
import com.example.deuHack.ui.content.ProfileTopBar
import com.example.deuHack.ui.compose.ui.Colors
import com.example.deuHack.ui.navigation.NavigationViewModel
import com.example.deuHack.ui.posting.PostingViewModel
import com.example.deuHack.ui.profile.ProfileViewModel
import com.example.deuHack.ui.search.SearchViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun ProfileBottomSheetDialog(
    onNavigateToProfileFix:()->Unit,
    navigationViewModel: NavigationViewModel,
    profileViewModel: ProfileViewModel,
    searchViewModel: SearchViewModel
){
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val userState by profileViewModel.userState.collectAsStateWithLifecycle()
    searchViewModel.getAllUser()

    ModalBottomSheetLayout(sheetContent = {
        Column(
            Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(top = 16.dp, start = 8.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "",
                    modifier = Modifier
                        .width(40.dp)
                        .height(3.dp)
                        .background(Colors.gray_deep, RoundedCornerShape(10.dp))
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    imageModel = userState.userProfileImage,
                    // Crop, Fit, Inside, FillHeight, FillWidth, None
                    contentScale = ContentScale.Crop,
                    // shows an image with a circular revealed animation.
                    circularReveal = CircularReveal(duration = 250),
                    // shows a placeholder ImageBitmap when loading.
                    placeHolder = ImageBitmap.imageResource(R.drawable.icon_profile),
                    // shows an error ImageBitmap when the request failed.
                    error = ImageBitmap.imageResource(R.drawable.icon_profile),
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = userState.userNickName, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.icon_circle_double),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.icon_plus_circle),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "계정 추가", fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            }
        }

    },
        sheetShape = RoundedCornerShape(10.dp),
        sheetState = modalBottomSheetState
    ) {
        ProfileView(
            onNavigateToProfileFix= onNavigateToProfileFix,
            navigationViewModel,
            modalBottomSheetState,
            userState = userState,
            searchViewModel,
            profileViewModel
        )
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class,
)
@Composable
fun ProfileView(
    onNavigateToProfileFix:()->Unit,
    navigationViewModel: NavigationViewModel,
    modalBottomSheetState:ModalBottomSheetState,
    userState: UserModel,
    searchViewModel:SearchViewModel,
    profileViewModel: ProfileViewModel
){
    navigationViewModel.setBottomNavigationState(true)
    val verticalScrollState = rememberScrollState()
    val findPeopleState = remember {
        mutableStateOf(true)
    }
    val myPostingState by profileViewModel.myPostingState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        ProfileTopBar(modalBottomSheetState,userState)
    }) {
        Column(
            Modifier
                .background(Color.White)
                .fillMaxSize()) {
            Column(
                Modifier
                    .padding(10.dp)
                    .verticalScroll(verticalScrollState)
            ) {
                ProfileMember(userState)
                ProfileEdit(onNavigateToProfileFix,userState,findPeopleState)
                Spacer(modifier = Modifier.height(10.dp))
                ProfileSearchFriends(searchViewModel,findPeopleState)
                ProfilePicture(myPostingState)
            }
        }
    }
}

@Composable
fun ProfileMember(
    userState: UserModel
){

    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = userState.userProfileImage,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows an image with a circular revealed animation.
            circularReveal = CircularReveal(duration = 250),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(R.drawable.icon_profile),
            // shows an error ImageBitmap when the request failed.
            error = ImageBitmap.imageResource(R.drawable.icon_profile),
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = "0",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "게시물",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = userState.followers?.size.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "팔로워",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = userState.following?.size.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "팔로잉",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ProfileEdit(
    onNavigateToProfileFix:()->Unit,
    userState: UserModel,
    findPeopleState: MutableState<Boolean>
){
    Column(
        Modifier
            .height(100.dp)
            .fillMaxWidth())
    {
        Text(text = userState.userName)
        Text(text = userState.userIntroduce)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .height(35.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically)
        {
            Button(
                onClick = onNavigateToProfileFix,
                colors = ButtonDefaults.buttonColors(Colors.gray_DADDE1),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(0.dp))
            {
                Text(text = "프로필 편집", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { findPeopleState.value=!findPeopleState.value },
                colors = ButtonDefaults.buttonColors(Colors.gray_DADDE1),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.size(35.dp),
                contentPadding = PaddingValues(0.dp)
            )
            {
                Image(
                    painter =  
                    if(findPeopleState.value)
                        painterResource(id = R.drawable.icon_add_person)
                    else
                        painterResource(id = R.drawable.ic_baseline_person_add_disabled_24),
                    contentDescription = null,
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProfileSearchFriends(searchViewModel:SearchViewModel,findPeopleState:MutableState<Boolean>){
    val userAllState by searchViewModel.userAllState.collectAsStateWithLifecycle()
    
    Column() {
        Row(
            Modifier
                .height(30.dp)
                .fillMaxWidth()) {
            Text(text = "사람 찾아보기",
                color = Color.Black,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.W400)
            Text(text = "모두 보기", color = Colors.Blue_1877F2)
        }
        LazyRow(){
            if(findPeopleState.value){
                items(userAllState,key = {item -> item.userId}){item->
                    ProfileSearchFriendsItems(item,searchViewModel)
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileSearchFriendsItems(
    item: UserModel,
    searchViewModel: SearchViewModel
){
    Column(modifier = Modifier
        .width(150.dp)
        .height(220.dp)
        .border(
            1.dp, Colors.gray_DADDE1, shape = RoundedCornerShape(5.dp)
        )){
        Spacer(modifier = Modifier.height(5.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(top = 5.dp),
            horizontalAlignment = Alignment.End
        ){
            IconButton(onClick = { /*TODO*/ }
            ){
                Icon(
                    painter = painterResource(id = R.drawable.icon_x),
                    contentDescription = null,
                    tint = Colors.gray_999b9e)
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp)
        ) {
            GlideImage(
                imageModel = item.userProfileImage,
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                // shows an image with a circular revealed animation.
                circularReveal = CircularReveal(duration = 250),
                // shows a placeholder ImageBitmap when loading.
                placeHolder = ImageBitmap.imageResource(R.drawable.icon_profile),
                // shows an error ImageBitmap when the request failed.
                error = ImageBitmap.imageResource(R.drawable.icon_profile),
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
            )
            Text(
                text = item.userNickName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp)
            Text(
                text = "회원님을 위한 추천",
                color = Colors.gray_999b9e,
                fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                searchViewModel.followUser(item.userNickName)
            },
                colors = ButtonDefaults.buttonColors(Colors.Blue_1877F2),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 3.dp)
            ) {
                Text(text = "팔로우", fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfilePicture(myPostingState:List<PostModel>){
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val list:List<MyProfile> = listOf(
        MyProfile("pic",R.drawable.icon_grid_box),
        MyProfile("tag",R.drawable.icon_profile_2)
    )


    Column() {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.White,
            indicator = {
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, it),
                    height = 2.dp,
                    color = Color.White
                )
            }) {
            list.forEachIndexed { index, s ->
                Tab(
                    icon = {
                        Icon(painter = painterResource(id = list[index].drawableId),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = Color.Black
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .align(Alignment.Start),
            count = list.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { page->
            when(page){
                0->{
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(80.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxSize().padding(top=3.dp, start = 3.dp)
                    ){
                       items(myPostingState,key={item->item.id}){item->
                           GlideImage(
                               imageModel = "http://113.198.235.148:8887"+item.image?.get(0)?.image,
                               // Crop, Fit, Inside, FillHeight, FillWidth, None
                               contentScale = ContentScale.Crop,
                               // shows an image with a circular revealed animation.
                               circularReveal = CircularReveal(duration = 250),
                               // shows a placeholder ImageBitmap when loading.
                               placeHolder = ImageBitmap.imageResource(R.drawable.icon_profile),
                               // shows an error ImageBitmap when the request failed.
                               error = ImageBitmap.imageResource(R.drawable.icon_profile),
                               modifier = Modifier.size(80.dp).padding(3.dp)
                           )
                       }
                    }
                }
                1->{
                    Image(
                        painter = painterResource(id = R.drawable.icon_report),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

data class MyProfile(val title:String,@DrawableRes val drawableId:Int)