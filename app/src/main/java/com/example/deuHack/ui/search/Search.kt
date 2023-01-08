package com.example.deuHack.ui.search

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deuHack.R
import com.example.deuHack.data.domain.model.UserModel
import com.example.deuHack.ui.Utils.addFocusCleaner
import com.example.deuHack.ui.navigation.NavigationViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchView(
    searchViewModel: SearchViewModel,
    navigationViewModel: NavigationViewModel
){
    var searchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }
    val userState by searchViewModel.userState.collectAsStateWithLifecycle()
    navigationViewModel.setBottomNavigationState(true)

    BackHandler(searchMode) {
        searchMode = false
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
        topBar = {
            SearchableTopBar(
                searchMode = searchMode,
                searchText = searchText,
                onSearchTextChanged = {
                    searchText = it
                    if(!it.isNullOrEmpty()){
                        searchViewModel.searchUser(searchText)
                    }
                },
                onSearchButtonClicked = {
                    searchMode = true
                    focusRequester.requestFocus()
                },
                onSearchButtonUnClicked = {
                    searchMode = false
                },
                focusRequester
            )
        }
    ) {
        Column(Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Row() {
                Text(
                    text = "최근 검색 항목",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if(searchText.isNullOrEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "최근 검색 내역 없음.",
                        color=com.example.deuHack.ui.compose.ui.Colors.gray_999b9e
                    )
                }
            }
            else
                SearchUserInfo(userState)
        }
    }
}
@Composable
fun SearchUserInfo(userState:List<UserModel>){
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ){
        itemsIndexed(userState){index,item->
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.icon_profile),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(Modifier.weight(1f)) {
                    Text(text = item.userNickName)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = item.userName)
                }
            }
        }
    }
}


@Composable
fun SearchableTopBar(
    searchMode: Boolean,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit,
    onSearchButtonUnClicked: ()->Unit,
    focusRequester:FocusRequester
) {
    SmallTopAppBar(
        title={
            Row() {
                SearchInputTextLayout(
                    searchText,
                    onSearchTextChanged,
                    onSearchButtonClicked,
                    searchMode,
                    focusRequester)
            }
        },
        actions = {
            if(!searchMode){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_add_person),
                        contentDescription = null
                    )
                }
            }
        },
        navigationIcon = {
            if(searchMode){
                androidx.compose.material3.IconButton(onClick = onSearchButtonUnClicked) {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.icon_left_arrow),
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.White)
    )
}

@Composable
fun SearchInputTextLayout(
    searchText: String,
    onSearchTextChanged:(String) -> Unit,
    onSearchButtonClicked :()->Unit,
    searchMode: Boolean,
    focusRequester:FocusRequester
){
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = com.example.deuHack.ui.compose.ui.Colors.gray_DADDE1,
                shape = RoundedCornerShape(10.dp)
            )
            .focusRequester(focusRequester),
        value = searchText,
        onValueChange = onSearchTextChanged,
        singleLine = true,
        enabled = searchMode,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface,
            fontSize = MaterialTheme.typography.body2.fontSize
        ),
        decorationBox = { innerTextField ->
            Column(Modifier.padding(8.dp)) {
                Row(
                    modifier =
                    if (searchMode)
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(
                                color = com.example.deuHack.ui.compose.ui.Colors.gray_DADDE1,
                                shape = RoundedCornerShape(10.dp)
                            )
                    else
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(
                                color = com.example.deuHack.ui.compose.ui.Colors.gray_DADDE1,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable(onClick = onSearchButtonClicked),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = com.example.deuHack.ui.compose.ui.Colors.gray_999b9e
                        )
                    }
                    Box(
                        Modifier.weight(1f)
                    ) {
                        if (searchText.isEmpty())
                            Text(
                                text = "검색",
                                color = com.example.deuHack.ui.compose.ui.Colors.gray_999b9e,
                                fontSize = 18.sp
                            )
                        innerTextField()
                    }
                }
            }
            
        }
    )
}