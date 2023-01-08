package com.example.deuHack.ui.compose.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.deuHack.R
import com.example.deuHack.data.domain.model.PostModel
import com.example.deuHack.ui.navigation.InstagramBottomNavigation
import com.example.deuHack.ui.navigation.NavigationGraph
import com.example.deuHack.ui.navigation.NavigationViewModel
import com.example.deuHack.ui.content.HomeTopBar
import com.example.deuHack.ui.home.*
import com.example.deuHack.ui.search.SearchViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainView()
            }
        }
    }
}

object Colors{
    val gray_DADDE1 = Color(0xFFDADDE1)
    val gray_999b9e = Color(0xFF999b9e)
    val Blue_1877F2 = Color(0xFF0095F6)
    val gray_deep = Color(0xFF686868)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    homeViewModel: HomeViewModel = viewModel(),
    searchViewModel: SearchViewModel = viewModel(),
    navigationViewModel: NavigationViewModel = viewModel()
) {
    val navBottomController = rememberNavController()
    val state by navigationViewModel.bottomBarState.observeAsState()
    Column {
        Scaffold(bottomBar = {
            if (state == true)
                InstagramBottomNavigation(navController = navBottomController)
        }) {
            Box(modifier = Modifier.padding(it)) {
                NavigationGraph(
                    navController = navBottomController,
                    homeViewModel,
                    searchViewModel,
                    navigationViewModel
                )

            }
        }
    }
}

@Composable
fun HomeTopBarIcon(@DrawableRes id:Int,){
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun HomeTopLayout(){
    Column(
        Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        LazyRow(){
            items(2){
                HomeMyStory()
            }
        }

        Text(
            text = "",
            Modifier
                .background(Colors.gray_DADDE1)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Composable
fun HomeMyStory(){
    Column(
        Modifier
            .height(90.dp)
            .padding(5.dp)) {
        Image(painter = painterResource(id = R.drawable.img_profile ),
            contentDescription = null,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "내 스토리",
            modifier = Modifier.height(20.dp),
            fontSize = 12.sp
        )
        Text(
            text = "",
            Modifier
                .background(Colors.gray_DADDE1)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeStory(
    onNavigateToReply:()-> Unit,
    homeViewModel: HomeViewModel,
    navigationViewModel: NavigationViewModel,
    onNavigateToPosting : ()-> Unit
){
    val homePostingList by homeViewModel.positngListState.collectAsStateWithLifecycle()
    navigationViewModel.setBottomNavigationState(true)

    Scaffold(topBar = {
        HomeTopBar(onNavigateToPosting)
    }, containerColor = Color.White
    ) {
        LazyColumn() {
            item {
                HomeTopLayout()
            }
            items(items = homePostingList, key = {item-> item.id }){item->
                HomeStoryMediaItem(onNavigateToReply,item)
            }
        }
    }
}


@Composable
fun HomeStoryMediaItem(
    onNavigateToReply: () -> Unit,
    item: PostModel
){
    var content by remember{
        mutableStateOf(
            if(item.content.isNotEmpty())
                item.content.slice(0..4)
            else
                ""
        )
    }

    Column() {
        Row(
            Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Row(Modifier.weight(1f)) {
                Text(text = "회원님이 ", fontSize = 12.sp)
                Text(text = "xxxx", fontWeight = FontWeight.W900,fontSize = 12.sp)
                Text(text = "님의 릴스를 시청했습니다",fontSize = 12.sp)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.icon_x),
                    contentDescription = null,
                    modifier = Modifier.size(10.dp)
                )
            }
        }
        GlideImage(
            imageModel = item.profile,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows an image with a circular revealed animation.
            circularReveal = CircularReveal(duration = 250),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(R.drawable.icon_profile),
            // shows an error ImageBitmap when the request failed.
            error = ImageBitmap.imageResource(R.drawable.icon_profile),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Row() {
            HomeIconButton(id = R.drawable.icon_heart)
            IconButton(onClick = onNavigateToReply) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_message),
                    contentDescription = null
                )
            }
            HomeIconButton(id = R.drawable.icon_dm)
            Spacer(modifier = Modifier.weight(1f))
            HomeIconButton(id = R.drawable.icon_bookmark)
        }
        Row(Modifier.padding(3.dp)) {
            Text(
                text = "좋아요 ${item.heartNumber}개",
                fontWeight = FontWeight.W900,
                fontSize = 14.sp
            )
        }
        Row(Modifier.padding(3.dp)) {
            Text(text = "${item.user} $content",
                Modifier.fillMaxHeight(),
                fontWeight = FontWeight.W500,
                fontSize = 12.sp
            )
            Text(
                text = "...더보기",
                color = Colors.gray_999b9e,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        content = item.content
                    }
            )
        }
        Row(Modifier.padding(3.dp)) {
            Text(
                text = "댓글 200개 모두 보기",
                color=Colors.gray_999b9e,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable(onClick = onNavigateToReply)
            )
        }
        Row(Modifier.padding(3.dp)) {
            Text(
                text = item.created_at,
                fontSize = 9.sp,
                color = Colors.gray_999b9e
            )
        }
        
    }
}

@Composable
fun HomeIconButton(@DrawableRes id:Int){
    IconButton(onClick = { /*TODO*/ }) {
        Icon(painter = painterResource(id = id),
            contentDescription = null)
    }
}
