package com.example.deuHack.ui.posting

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.deuHack.R
import com.example.deuHack.data.domain.model.PostImage
import com.example.deuHack.ui.Utils.addFocusCleaner
import com.example.deuHack.ui.absolutelyPath
import com.example.deuHack.ui.compose.ui.Colors
import com.example.deuHack.ui.content.PostingTopBar
import com.example.deuHack.ui.navigation.NavigationViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostingView(
    onNavigatePopBackStack : () -> Unit,
    onNavigatePopBackAndPostData : ()->Unit,
    navigationViewModel:NavigationViewModel,
    postingViewModel: PostingViewModel
){
    val focusManager = LocalFocusManager.current
    navigationViewModel.setBottomNavigationState(false)
    val text = remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {PostingTopBar(onNavigatePopBackStack,onNavigatePopBackAndPostData)},
        containerColor = Color.White
    ) {
        Column(
            Modifier
                .padding(10.dp)
                .fillMaxSize()
                .addFocusCleaner(focusManager)) {
            PostingImage(text,postingViewModel = postingViewModel)
        }
    }
}

@Composable
fun PostingImage(
    text:MutableState<String>,
    postingViewModel: PostingViewModel
){
    val context = LocalContext.current

    val postImage = remember {
        mutableStateOf("")
    }

    val photoFromPictureLauncer =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                postingViewModel.mutablePostModel.value?.image = listOf(PostImage(absolutelyPath(result.data?.data,context)))
                Log.d("test","img: " +listOf(PostImage(absolutelyPath(result.data?.data,context))).toString())
                postImage.value = absolutelyPath(result.data?.data,context)
            }
            else if (result.resultCode != Activity.RESULT_CANCELED) {
                Toast.makeText(context,"이미지 불러오기 실패", Toast.LENGTH_SHORT).show()
            }
        }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            photoFromPictureLauncer.launch(intent)
        }) {
            GlideImage(
                imageModel = postImage.value,
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                // shows an image with a circular revealed animation.
                circularReveal = CircularReveal(duration = 250),
                // shows a placeholder ImageBitmap when loading.
                placeHolder = ImageBitmap.imageResource(R.drawable.icon_profile),
                // shows an error ImageBitmap when the request failed.
                error = ImageBitmap.imageResource(R.drawable.icon_profile),
                modifier = Modifier
                    .size(70.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = text.value,
            onValueChange = {
                text.value=it
                postingViewModel.postModel.value?.content=it
                            },
            decorationBox = {innerTextField ->
                if(text.value.isEmpty())
                    Text(text = "문구 입력...", color = Colors.gray_DADDE1, fontWeight = FontWeight.Bold)
                innerTextField()
                }
        )
    }
}