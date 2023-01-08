package com.example.deuHack.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.deuHack.ui.compose.ui.Colors
import com.example.deuHack.ui.navigation.NavigationViewModel
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavHostController
import com.example.deuHack.ui.absolutelyPath
import com.example.deuHack.ui.navigation.BottomNavigationItem
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun ProfileFixView(
    onNavigatePopBackStack : ()->Unit,
    navigationViewModel: NavigationViewModel,
    profileViewModel: ProfileViewModel,
    navController:NavHostController
){
    val focusManager = LocalFocusManager.current
    navigationViewModel.setBottomNavigationState(false)
    val userState by profileViewModel.userState.collectAsStateWithLifecycle()

    val textName = remember {
        mutableStateOf(userState.userName)
    }
    val textNickName = remember {
        mutableStateOf(userState.userNickName)
    }
    val textIntroduce = remember {
        mutableStateOf(userState.userIntroduce)
    }

    val imgUri:MutableState<String> = remember {
        mutableStateOf(userState.userProfileImage?:"")
    }

    Scaffold(
        topBar = {ProfileFixTopBar(
            onNavigatePopBackStack,
            profileViewModel,
            textName,
            textNickName,
            textIntroduce,
            imgUri,
            navController
        )},
        containerColor = Color.White
    ) {
        Column(Modifier.addFocusCleaner(focusManager)) {
            ProfileFixImage(userState,imgUri)
            ProfileFixInfo(
                textName,
                textNickName,
                textIntroduce
            )
        }
    }
}

@Composable
fun ProfileFixTopBar(
    onNavigatePopBackStack:()->Unit,
    profileViewModel: ProfileViewModel,
    textName:MutableState<String>,
    textNickName:MutableState<String>,
    textIntroduce:MutableState<String>,
    imgUri:MutableState<String>,
    navController:NavHostController
){
    SmallTopAppBar(
        title = {
            Text(
                text = "프로필 편집",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = {
                profileViewModel.modifyUserInfo(
                    UserModel(
                        0,
                        textName.value,
                        textNickName.value,
                        textIntroduce.value,
                        imgUri.value,
                        null,
                        null
                    )
                )
                navController.popBackStack(BottomNavigationItem.Profile.route,true)
            }) {
                Icon(painter = painterResource(id = R.drawable.icon_check),
                    contentDescription = null,
                    tint = Colors.Blue_1877F2
                )

            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigatePopBackStack) {
                Icon(painter = painterResource(id = R.drawable.icon_x),
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.White)
    )
}

@Composable
fun ProfileFixImage(
    userState: UserModel,
    imgUri:MutableState<String>
) {
    val context = LocalContext.current

    val photoFromPictureLauncer =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imgUri.value = absolutelyPath(result.data?.data,context)
            userState.userProfileImage = absolutelyPath(result.data?.data,context)
        }
        else if (result.resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(context,"이미지 불러오기 실패",Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){

            GlideImage(
                imageModel = imgUri.value,
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                // shows an image with a circular revealed animation.
                circularReveal = CircularReveal(duration = 250),
                // shows a placeholder ImageBitmap when loading.
                placeHolder = ImageBitmap.imageResource(R.drawable.icon_profile),
                // shows an error ImageBitmap when the request failed.
                error = ImageBitmap.imageResource(R.drawable.icon_profile),
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.icon_profile),
                contentDescription = null,
                Modifier.size(64.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material.TextButton(onClick = {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            photoFromPictureLauncer.launch(intent)
             }) {
            Text(
                text = "사진 또는 아바타 수정",
                color = Colors.Blue_1877F2,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ProfileFixInfo(
    textName:MutableState<String>,
    textNickName:MutableState<String>,
    textIntroduce:MutableState<String>
){
    Column(Modifier.padding(16.dp)) {
        Text(text = "이름", color = Color.Gray , fontSize = 14.sp)
        Spacer(modifier = Modifier.height(5.dp))
        BasicTextField(
            value = textName.value,
            onValueChange = {textName.value = it},
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            textStyle = androidx.compose.material.LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = MaterialTheme.typography.body2.fontSize)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "사용자 이름",color = Color.Gray , fontSize = 14.sp)
        Spacer(modifier = Modifier.height(5.dp))
        BasicTextField(
            value = textNickName.value,
            onValueChange = {textNickName.value = it},
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            textStyle = androidx.compose.material.LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = MaterialTheme.typography.body2.fontSize)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "소개",color = Color.Gray , fontSize = 14.sp)
        Spacer(modifier = Modifier.height(5.dp))
        BasicTextField(
            value = textIntroduce.value,
            onValueChange = {textIntroduce.value = it},
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            textStyle = androidx.compose.material.LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = MaterialTheme.typography.body2.fontSize)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "링크 추가", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.gray_DADDE1)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "프로페셔널 계정으로 전환",
            fontSize = 18.sp,
            color = Colors.Blue_1877F2
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.gray_DADDE1)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "개인정보 설정",
            fontSize = 18.sp,
            color = Colors.Blue_1877F2
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.gray_DADDE1)
        )
    }
}