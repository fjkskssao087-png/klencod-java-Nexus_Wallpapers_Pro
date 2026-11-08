package com.nexus.wallpapers.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

// توليد روابط لا نهائية للصور (أنمي وطبيعة وكرتون)
val generateImages = { category: String ->
    (1..50).map { i -> "https://picsum.photos/seed/\${category}\${i}/1080/1920" }
}

@Composable
fun HomeScreen(navController: NavController) {
    val images = generateImages("anime")

    Column(Modifier.fillMaxSize().background(Color.Black)) {
        // الهيدر
        Column(Modifier.padding(24.dp, top = 60.dp, bottom = 20.dp)) {
            Text("اكتشف", fontSize = 20.sp, color = Color(0xFF86868B))
            Text("خلفيات 4K", fontSize = 44.sp, fontWeight = FontWeight.Black, color = Color.White)
            
            Spacer(Modifier.height(20.dp))
            
            // أزرار التصنيفات
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CategoryBadge("أنمي", true)
                CategoryBadge("كرتون", false)
                CategoryBadge("طبيعة", false)
            }
        }

        // شبكة الصور
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(images) { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { navController.navigate("detail/\${android.net.Uri.encode(url)}") },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun CategoryBadge(text: String, isSelected: Boolean) {
    val bg = if (isSelected) Color(0xFF0A84FF) else Color(0xFF1A1A1A)
    val txt = if (isSelected) Color.White else Color(0xFF86868B)
    Box(Modifier.background(bg, RoundedCornerShape(20.dp)).padding(horizontal = 20.dp, vertical = 10.dp)) {
        Text(text, color = txt, fontWeight = FontWeight.Bold)
    }
}