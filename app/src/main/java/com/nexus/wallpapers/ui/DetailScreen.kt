package com.nexus.wallpapers.ui

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(imageUrl: String, navController: NavController) {
    val context = LocalContext.current
    val decodedUrl = android.net.Uri.decode(imageUrl)
    val scope = CoroutineScope(Dispatchers.IO)

    Box(Modifier.fillMaxSize().background(Color.Black)) {
        // الصورة
        AsyncImage(
            model = decodedUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // أزرار التحكم
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // زر التعيين
            Button(
                onClick = {
                    scope.launch {
                        try {
                            val request = ImageRequest.Builder(context).data(decodedUrl).allowHardware(false).build()
                            val result = coil.ImageLoader(context).execute(request)
                            val bitmap = (result.drawable as BitmapDrawable).bitmap
                            WallpaperManager.getInstance(context).setBitmap(bitmap)
                            // العودة للواجهة الرئيسية (يجب أن يتم في Main Thread)
                            CoroutineScope(Dispatchers.Main).launch { 
                                Toast.makeText(context, "تم تعيين الخلفية!", Toast.LENGTH_SHORT).show() 
                            }
                        } catch (e: Exception) {
                             CoroutineScope(Dispatchers.Main).launch { 
                                 Toast.makeText(context, "حدث خطأ أثناء التعيين", Toast.LENGTH_SHORT).show() 
                             }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A84FF)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("تعيين كخلفية", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            // زر التحميل
            Button(
                onClick = {
                    try {
                        val request = DownloadManager.Request(Uri.parse(decodedUrl))
                            .setTitle("Nexus Wallpaper")
                            .setDescription("Downloading...")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Nexus_\${System.currentTimeMillis()}.jpg")
                        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        dm.enqueue(request)
                        Toast.makeText(context, "بدأ التحميل...", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "فشل التحميل", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("تحميل", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            }
        }
    }
}