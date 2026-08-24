package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AffluenceLevel
import com.example.model.MapLocation
import com.example.ui.theme.*
import com.example.viewmodel.EventUiState
import com.example.viewmodel.EventViewModel
import com.example.viewmodel.ScreenRoute

@Composable
fun SmartMapScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  var searchQuery by remember { mutableStateOf("") }
  val selectedLoc = uiState.mapSelectedLocation ?: uiState.mapLocations.first()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    // Search and filter top bar
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Rechercher un stand, salle, VIP, café...", color = TextSecondaryLight) },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = CyanAccent) },
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = CyanAccent,
            unfocusedBorderColor = NavySurface,
            focusedContainerColor = NavySurface,
            unfocusedContainerColor = NavySurface
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("map_search_input"),
          shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Legend row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(AffluenceLevel.FAIBLE.colorHex)))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Faible", style = MaterialTheme.typography.labelSmall, color = TextSecondaryLight)
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(AffluenceLevel.NORMALE.colorHex)))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Normale", style = MaterialTheme.typography.labelSmall, color = TextSecondaryLight)
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(AffluenceLevel.FORTE.colorHex)))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Forte", style = MaterialTheme.typography.labelSmall, color = TextSecondaryLight)
          }

          Button(
            onClick = { viewModel.startArScan() },
            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            modifier = Modifier.height(30.dp).testTag("launch_ar_button")
          ) {
            Icon(Icons.Default.ViewInAr, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Mode AR", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Interactive Map Canvas Area
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(12.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(NavySurface)
        .border(1.dp, TechBlueContainer, RoundedCornerShape(16.dp))
    ) {
      Canvas(
        modifier = Modifier
          .fillMaxSize()
          .pointerInput(Unit) {
            detectTapGestures { offset ->
              // Find closest location
              val w = size.width
              val h = size.height
              val clicked = uiState.mapLocations.minByOrNull { loc ->
                val px = (loc.x / 100f) * w
                val py = (loc.y / 100f) * h
                val dx = px - offset.x
                val dy = py - offset.y
                dx * dx + dy * dy
              }
              if (clicked != null) {
                viewModel.selectMapLocation(clicked)
              }
            }
          }
      ) {
        val w = size.width
        val h = size.height

        // Draw Hall grid lines
        val gridColor = Color(0x1500A6C7)
        for (i in 1..8) {
          drawLine(
            color = gridColor,
            start = Offset(0f, (h / 8) * i),
            end = Offset(w, (h / 8) * i),
            strokeWidth = 1f
          )
          drawLine(
            color = gridColor,
            start = Offset((w / 8) * i, 0f),
            end = Offset((w / 8) * i, h),
            strokeWidth = 1f
          )
        }

        // Draw Navigation route line if active
        if (uiState.isNavigatingToStand && uiState.navigationTarget != null) {
          val startPos = Offset(w * 0.50f, h * 0.90f) // Current user at Entrance
          val targetPos = Offset((uiState.navigationTarget.x / 100f) * w, (uiState.navigationTarget.y / 100f) * h)

          // Mid-point walkway
          val midPos = Offset(startPos.x, targetPos.y)

          drawLine(
            color = Color(0xFF00E5FF),
            start = startPos,
            end = midPos,
            strokeWidth = 4.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
          )
          drawLine(
            color = Color(0xFF00E5FF),
            start = midPos,
            end = targetPos,
            strokeWidth = 4.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
          )

          // Start position pulse circle
          drawCircle(
            color = Color(0xFF16A36A),
            radius = 10.dp.toPx(),
            center = startPos
          )
          drawCircle(
            color = Color.White,
            radius = 4.dp.toPx(),
            center = startPos
          )
        }

        // Draw All Location Markers
        uiState.mapLocations.forEach { loc ->
          val px = (loc.x / 100f) * w
          val py = (loc.y / 100f) * h
          val isSelected = loc.id == selectedLoc.id
          val markerColor = Color(loc.affluence.colorHex)

          // Outer pulse ring if selected
          if (isSelected) {
            drawCircle(
              color = Color(0xFF00A6C7).copy(alpha = 0.35f),
              radius = 22.dp.toPx(),
              center = Offset(px, py)
            )
          }

          // Main Marker
          drawCircle(
            color = if (isSelected) Color.White else markerColor,
            radius = if (isSelected) 13.dp.toPx() else 9.dp.toPx(),
            center = Offset(px, py)
          )
          drawCircle(
            color = if (isSelected) Color(0xFF00A6C7) else NavyDeep,
            radius = if (isSelected) 8.dp.toPx() else 4.dp.toPx(),
            center = Offset(px, py)
          )
        }
      }

      // Compass & You are here overlay
      Surface(
        modifier = Modifier
          .align(Alignment.TopEnd)
          .padding(10.dp),
        shape = RoundedCornerShape(8.dp),
        color = NavyDeep.copy(alpha = 0.85f),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.4f))
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Navigation, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Nord", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }

      if (uiState.isNavigatingToStand) {
        Surface(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(10.dp),
          shape = RoundedCornerShape(8.dp),
          color = SuccessGreen,
          shadowElevation = 4.dp
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.DirectionsWalk, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Guidage actif : 45m (1 min)",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          }
        }
      }
    }

    // BOTTOM SELECTED LOCATION DETAILS CARD
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .shadow(4.dp, RoundedCornerShape(16.dp)),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = TechBlueContainer
            ) {
              Text(
                text = selectedLoc.code,
                style = MaterialTheme.typography.labelSmall,
                color = CyanGlow,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = selectedLoc.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = NavyDeep
              )
              Text(
                text = selectedLoc.type,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondaryDark
              )
            }
          }

          // Affluence pill
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(selectedLoc.affluence.colorHex).copy(alpha = 0.15f)
          ) {
            Text(
              text = "${selectedLoc.affluence.emoji} Affluence ${selectedLoc.affluence.label}",
              style = MaterialTheme.typography.labelSmall,
              color = Color(selectedLoc.affluence.colorHex),
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = selectedLoc.description,
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryDark
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          if (uiState.isNavigatingToStand) {
            Button(
              onClick = { viewModel.stopNavigation() },
              colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(44.dp)
            ) {
              Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Arrêter le guidage", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          } else {
            Button(
              onClick = { viewModel.startNavigationTo(selectedLoc) },
              colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(44.dp).testTag("start_guidance_button")
            ) {
              Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Me guider", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          }

          OutlinedButton(
            onClick = {
              val exh = uiState.exhibitors.firstOrNull { it.name.contains("SEN AURA") || it.standNumber.contains(selectedLoc.code) }
              viewModel.startArScan(exh)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f).height(44.dp)
          ) {
            Icon(Icons.Default.ViewInAr, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Scanner Stand", color = NavyDeep, fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }
      }
    }
  }
}

@Composable
fun AugmentedRealityScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  val exhibitor = uiState.arScannedExhibitor ?: uiState.exhibitors.first()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.Black)
  ) {
    // Camera feed simulation canvas
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Background camera viewfinder grid
      drawRect(
        brush = Brush.radialGradient(
          colors = listOf(Color(0xFF0F2B48), Color(0xFF050E18)),
          center = Offset(w / 2, h / 2),
          radius = w * 0.8f
        )
      )

      // Center AR Reticle
      val reticleColor = Color(0xFF00E5FF)
      val cx = w / 2
      val cy = h * 0.38f
      val boxSize = 140.dp.toPx()

      // Corner target brackets
      drawCircle(
        color = reticleColor.copy(alpha = 0.2f),
        radius = boxSize * 0.7f,
        center = Offset(cx, cy)
      )
    }

    // TOP HUD CONTROLS
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = { viewModel.navigateTo(ScreenRoute.SmartMap) },
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(NavyDeep.copy(alpha = 0.8f))
      ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = Color.White)
      }

      Surface(
        shape = RoundedCornerShape(20.dp),
        color = NavyDeep.copy(alpha = 0.85f),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(SuccessGreen))
          Spacer(modifier = Modifier.width(6.dp))
          Text("AR STAND TRACKER ACTIF", color = CyanGlow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }

      IconButton(
        onClick = { viewModel.showToast("Lampe torche AR activée") },
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(NavyDeep.copy(alpha = 0.8f))
      ) {
        Icon(Icons.Default.FlashlightOn, contentDescription = "Flash", tint = Color.White)
      }
    }

    // FLOATING 3D HOLOGRAPHIC PRODUCT CARD
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .padding(16.dp)
        .padding(bottom = 20.dp)
    ) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = NavyDeep.copy(alpha = 0.94f)),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, CyanAccent)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = TechBlueContainer
            ) {
              Text(
                text = exhibitor.standNumber,
                style = MaterialTheme.typography.labelSmall,
                color = CyanGlow,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }

            Text(
              text = "🎯 Stand Détecté",
              color = SuccessGreen,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = exhibitor.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = Color.White
          )

          Text(
            text = exhibitor.description,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryLight,
            maxLines = 2
          )

          Spacer(modifier = Modifier.height(12.dp))

          // 3D Product Highlight Pill
          val topProduct = exhibitor.products.firstOrNull()
          if (topProduct != null) {
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = NavySurface,
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.ViewInAr, contentDescription = null, tint = CyanAccent)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "Modèle 3D : ${topProduct.name}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                  Text(
                    text = topProduct.description,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondaryLight,
                    maxLines = 1
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { viewModel.navigateTo(ScreenRoute.ExhibitorDetail(exhibitor.id)) },
              colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(44.dp)
            ) {
              Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Fiche Stand", fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = {
                val prof = uiState.networkingProfiles.firstOrNull { it.company.contains(exhibitor.name) }
                    ?: uiState.networkingProfiles.first()
                viewModel.openBookingDialog(prof)
              },
              colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(44.dp)
            ) {
              Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Prendre RDV", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}
