package com.mediaplayer.tv.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen() {
    var isMenuOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0D14))
    ) {
        // Main Content Area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            // Top Bar: โลโก้ และปุ่มเมนูอยู่ใต้โลโก้
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "LENNA.TV",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF00F2FE)
                )
                Button(
                    onClick = { isMenuOpen = true },
                    colors = ButtonDefaults.colors(
                        containerColor = Color(0xFF161923),
                        focusedContainerColor = Color(0xFF00F2FE)
                    )
                ) {
                    Text(text = "⚡ เมนูหลัก (Menu)", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "🔥 ช่องรายการสตรีมมิ่งแนะนำ (Live Streams)",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF8C9BAE)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                StreamCard(title = "Ultra Sport 4K", category = "Live Broadcast")
                StreamCard(title = "Cinema HD Plus", category = "Movies & Series")
                StreamCard(title = "Global News 24/7", category = "Documentary")
            }
        }

        // Left-Side Popup Menu Drawer (มีโลโก้แอปอยู่ด้านบนสุดของป๊อปอัพ)
        AnimatedVisibility(
            visible = isMenuOpen,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it }),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xB3050608)),
                contentAlignment = Alignment.CenterStart
            ) {
                Surface(
                    modifier = Modifier
                        .width(380.dp)
                        .fillMaxHeight(),
                    shape = ClickableSurfaceDefaults.shape(shape = MaterialTheme.shapes.none),
                    colors = ClickableSurfaceDefaults.colors(
                        containerColor = Color(0xFF12151D)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(40.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "LENNA.TV",
                                fontSize = 22.sp,
                                color = Color(0xFF00F2FE),
                                letterSpacing = 2.sp
                            )
                            Text(
                                text = "SYSTEM NAVIGATION",
                                fontSize = 11.sp,
                                color = Color(0xFF8C9BAE),
                                letterSpacing = 2.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            PopupMenuItem(text = "🏠 หน้าแรก (Home Dashboard)", isSelected = true) {
                                isMenuOpen = false
                            }
                            PopupMenuItem(text = "📂 เพิ่มเพลย์ลิสต์ M3U / W3U") {
                                isMenuOpen = false
                            }
                            PopupMenuItem(text = "⭐ ช่องรายการโปรด (Favorites)") {
                                isMenuOpen = false
                            }
                            PopupMenuItem(text = "⚙️ ตั้งค่าเครื่องเล่น (Settings)") {
                                isMenuOpen = false
                            }
                        }

                        Button(
                            onClick = { isMenuOpen = false },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.colors(
                                containerColor = Color(0xFF161923),
                                focusedContainerColor = Color(0xFF00F2FE)
                            )
                        ) {
                            Text(text = "ปิดหน้าต่าง (Close)")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun StreamCard(title: String, category: String) {
    Card(
        onClick = { /* Handle stream click */ },
        modifier = Modifier
            .width(220.dp)
            .height(140.dp),
        colors = CardDefaults.colors(
            containerColor = Color(0xFF161923),
            focusedContainerColor = Color(0xFF161923)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = category, style = MaterialTheme.typography.bodySmall, color = Color(0xFF8C9BAE))
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PopupMenuItem(text: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = ClickableSurfaceDefaults.shape(shape = MaterialTheme.shapes.medium),
        colors = ClickableSurfaceDefaults.colors(
            containerColor = if (isSelected) Color(0xFF00F2FE) else Color(0xFF161923),
            focusedContainerColor = Color(0xFF00F2FE)
        )
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = text,
                color = if (isSelected) Color(0xFF050608) else Color.White,
                fontSize = 15.sp
            )
        }
    }
}