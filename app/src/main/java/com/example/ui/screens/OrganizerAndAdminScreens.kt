package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.*
import com.example.ui.components.SectionHeader
import com.example.ui.components.SenAuraWatermark
import com.example.ui.components.StatCard
import com.example.ui.theme.*
import com.example.viewmodel.EventUiState
import com.example.viewmodel.EventViewModel
import com.example.viewmodel.ScreenRoute

@Composable
fun OrganizerDashboardScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // HERO EVENT CONTROL
    item {
      Surface(
        color = NavyDeep,
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
        shadowElevation = 4.dp
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "SEN AURA EVENT CONTROL",
                style = MaterialTheme.typography.labelSmall,
                color = CyanAccent,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
              )
              Text(
                text = "Supervision Globale",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = Color.White
              )
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = SuccessGreen.copy(alpha = 0.2f),
              border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(SuccessGreen))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Système Nominal", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Quick Management Actions
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = { viewModel.navigateTo(ScreenRoute.SmartFlow) },
              colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(40.dp)
            ) {
              Icon(Icons.Default.Waves, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Flux", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Button(
              onClick = { viewModel.navigateTo(ScreenRoute.DigitalSignage) },
              colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(40.dp)
            ) {
              Icon(Icons.Default.Tv, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Écrans", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Button(
              onClick = { viewModel.navigateTo(ScreenRoute.Analytics) },
              colors = ButtonDefaults.buttonColors(containerColor = NavySurface),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).height(40.dp)
            ) {
              Icon(Icons.Default.QueryStats, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Stats", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        }
      }
    }

    // REAL-TIME KPIS
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        SectionHeader(
          title = "Métriques en direct",
          subtitle = "Actualisation automatique chaque seconde"
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            title = "Visiteurs uniques",
            value = SampleData.TOTAL_VISITORS,
            subtitle = "+842 dans la dernière heure",
            icon = Icons.Default.People,
            accentColor = CyanAccent,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            title = "Stands Actifs",
            value = "${SampleData.TOTAL_EXHIBITORS} / 186",
            subtitle = "Taux d'ouverture 100%",
            icon = Icons.Default.Storefront,
            accentColor = TechBlueLight,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            title = "Rendez-vous B2B",
            value = SampleData.TOTAL_MEETINGS,
            subtitle = "89% de réalisation",
            icon = Icons.Default.Handshake,
            accentColor = SuccessGreen,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            title = "Interactions IA",
            value = "4 820",
            subtitle = "98.4% satisfaction",
            icon = Icons.Default.SmartToy,
            accentColor = AlertOrange,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // HOURLY ATTENDANCE CHART
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
          .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Courbe de Fréquentation par Heure",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = NavyDeep
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = TechBlueContainer
            ) {
              Text(
                text = "Pic : 14h00 (2 840 p/h)",
                style = MaterialTheme.typography.labelSmall,
                color = CyanGlow,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Attendance Chart Canvas
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(140.dp)
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height

              val points = listOf(
                0.15f, 0.35f, 0.65f, 0.85f, 0.70f, 0.95f, 0.80f, 0.50f, 0.20f
              )
              val stepX = w / (points.size - 1)

              val path = Path()
              val fillPath = Path()

              points.forEachIndexed { i, normY ->
                val x = i * stepX
                val y = h - (normY * (h * 0.8f)) - (h * 0.1f)
                if (i == 0) {
                  path.moveTo(x, y)
                  fillPath.moveTo(x, h)
                  fillPath.lineTo(x, y)
                } else {
                  path.lineTo(x, y)
                  fillPath.lineTo(x, y)
                }
              }

              fillPath.lineTo(w, h)
              fillPath.close()

              // Draw Gradient Area
              drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                  colors = listOf(Color(0xFF00A6C7).copy(alpha = 0.35f), Color(0xFF00A6C7).copy(alpha = 0.02f))
                )
              )

              // Draw Line
              drawPath(
                path = path,
                color = Color(0xFF1769AA),
                style = Stroke(width = 3.dp.toPx())
              )

              // Draw dots
              points.forEachIndexed { i, normY ->
                val x = i * stepX
                val y = h - (normY * (h * 0.8f)) - (h * 0.1f)
                drawCircle(color = Color(0xFF00A6C7), radius = 4.dp.toPx(), center = Offset(x, y))
                drawCircle(color = Color.White, radius = 2.dp.toPx(), center = Offset(x, y))
              }
            }
          }

          // Hours axis
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            listOf("08h", "10h", "12h", "14h", "16h", "18h").forEach { hour ->
              Text(hour, style = MaterialTheme.typography.labelSmall, color = TextSecondaryDark, fontSize = 10.sp)
            }
          }
        }
      }
    }

    // ACTIVE ZONE DENSITY
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        SectionHeader(
          title = "Densité par Zone Pavillon",
          subtitle = "Capteurs thermiques et caméras IA",
          actionText = "Smart Flow",
          onActionClick = { viewModel.navigateTo(ScreenRoute.SmartFlow) }
        )

        val zones = listOf(
          Triple("Pavillon A — Conférences & Keynotes", 0.45f, AffluenceLevel.FAIBLE),
          Triple("Pavillon B — Innovation & Tech Hub", 0.85f, AffluenceLevel.FORTE),
          Triple("Pavillon C — Startups & Démo Day", 0.62f, AffluenceLevel.NORMALE),
          Triple("Pavillon D — VIP & Partenaires", 0.38f, AffluenceLevel.FAIBLE)
        )

        zones.forEach { (name, progress, affluence) ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
                Text("${(progress * 100).toInt()}% (${affluence.label})", color = Color(affluence.colorHex), fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
              Spacer(modifier = Modifier.height(6.dp))
              LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(6.dp)
                  .clip(RoundedCornerShape(3.dp)),
                color = Color(affluence.colorHex),
                trackColor = LightBackground
              )
            }
          }
        }
      }
    }

    item {
      SenAuraWatermark()
    }
  }
}

@Composable
fun SmartFlowScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text(
          text = "SMART FLOW & GESTION DES FLUX",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
        Text(
          text = "Surveillance de la densité des foules et régulation en temps réel",
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryLight
        )
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3CD)),
          border = androidx.compose.foundation.BorderStroke(1.dp, AlertOrange)
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = AlertOrange)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text("Alerte de régulation automatique", fontWeight = FontWeight.Bold, color = NavyDeep, fontSize = 13.sp)
              Text("Zone B (Stand B24 & Pôle IA) atteint 85% de sa capacité nominale. Les panneaux d'affichage recommandent l'accès par l'Allée Sud.", fontSize = 12.sp, color = NavyDeep)
            }
          }
        }
      }

      items(uiState.mapLocations) { loc ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(12.dp)
                  .clip(CircleShape)
                  .background(Color(loc.affluence.colorHex))
              )
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(loc.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
                Text("${loc.code} • ${loc.type}", style = MaterialTheme.typography.labelSmall, color = TextSecondaryDark)
              }
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(loc.affluence.colorHex).copy(alpha = 0.15f)
            ) {
              Text(
                text = "${loc.affluence.emoji} Affluence ${loc.affluence.label}",
                color = Color(loc.affluence.colorHex),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun DigitalSignageScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  var selectedScreenId by remember { mutableStateOf(uiState.digitalScreens.first().id) }
  var newBannerText by remember { mutableStateOf("🔴 FLASH : Keynote SEN AURA TECH en Salle Plénière Teranga dans 10 minutes !") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text(
          text = "GESTION DE L'AFFICHAGE DYNAMIQUE",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
        Text(
          text = "Diffusez des annonces et alertes en direct sur les écrans physiques du salon",
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryLight
        )
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Broadcaster Box
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("Diffuser une annonce prioritaire", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = newBannerText,
              onValueChange = { newBannerText = it },
              label = { Text("Texte du bandeau défilant") },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Button(
                onClick = {
                  viewModel.updateDigitalScreenContent(selectedScreenId, newBannerText, isAlert = true)
                },
                colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f).height(44.dp)
              ) {
                Icon(Icons.Default.BroadcastOnHome, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Pousser sur l'écran", fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }

      // Physical Screen List
      item {
        Text("Écrans connectés au réseau SEN AURA :", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
      }

      items(uiState.digitalScreens) { screen ->
        val isSelected = screen.id == selectedScreenId
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { selectedScreenId = screen.id }
            .shadow(2.dp, RoundedCornerShape(14.dp)),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, TechBlue) else null
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Tv, contentDescription = null, tint = TechBlue)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(screen.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
                  Text(screen.location, style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
                }
              }

              Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (screen.isOnline) SuccessGreen.copy(alpha = 0.15f) else ErrorRed.copy(alpha = 0.15f)
              ) {
                Text(
                  text = if (screen.isOnline) "🟢 EN LIGNE" else "🔴 HORS LIGNE",
                  color = if (screen.isOnline) SuccessGreen else ErrorRed,
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = NavyDeep,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "Bandeau actif : ${screen.activeBanner}",
                style = MaterialTheme.typography.labelSmall,
                color = CyanGlow,
                modifier = Modifier.padding(10.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun GamificationScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  var selectedTab by remember { mutableIntStateOf(0) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text("EVENT CHALLENGE 🏆", style = MaterialTheme.typography.labelSmall, color = CyanAccent, fontWeight = FontWeight.Bold)
            Text("Vos Points & Classement", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = Color.White)
          }

          Surface(
            shape = RoundedCornerShape(16.dp),
            color = TechBlueContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent)
          ) {
            Column(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text("${uiState.userPoints}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = CyanGlow)
              Text("Points", style = MaterialTheme.typography.labelSmall, color = Color.White, fontSize = 10.sp)
            }
          }
        }
      }
    }

    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = Color.White,
      contentColor = TechBlue
    ) {
      Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Missions à Débloquer") })
      Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Classement en Direct") })
    }

    if (selectedTab == 0) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(uiState.challengeQuests) { quest ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                if (!quest.isCompleted) {
                  viewModel.addPoints(quest.points, quest.title)
                }
              }
              .shadow(2.dp, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (quest.isCompleted) LightSurfaceVariant else Color.White
            )
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = if (quest.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                  contentDescription = null,
                  tint = if (quest.isCompleted) SuccessGreen else TechBlue,
                  modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = quest.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (quest.isCompleted) TextSecondaryDark else NavyDeep
                  )
                  Text(
                    text = quest.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryDark
                  )
                }
              }

              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (quest.isCompleted) SuccessGreen.copy(alpha = 0.15f) else TechBlueContainer
              ) {
                Text(
                  text = "+${quest.points} pts",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = if (quest.isCompleted) SuccessGreen else CyanGlow,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(uiState.leaderboard) { entry ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .shadow(2.dp, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (entry.isCurrentUser) TechBlueContainer else Color.White
            ),
            border = if (entry.isCurrentUser) androidx.compose.foundation.BorderStroke(1.5.dp, CyanAccent) else null
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = CircleShape,
                  color = when (entry.rank) {
                    1 -> Color(0xFFFFD700)
                    2 -> Color(0xFFC0C0C0)
                    3 -> Color(0xFFCD7F32)
                    else -> NavyDeep
                  },
                  modifier = Modifier.size(32.dp)
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Text(
                      text = "${entry.rank}",
                      color = if (entry.rank <= 3) NavyDeep else Color.White,
                      fontWeight = FontWeight.Black,
                      fontSize = 13.sp
                    )
                  }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = if (entry.isCurrentUser) "${entry.name} (Vous)" else entry.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (entry.isCurrentUser) Color.White else NavyDeep
                  )
                  Text(
                    text = entry.company,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (entry.isCurrentUser) CyanAccent else TextSecondaryDark
                  )
                }
              }

              Text(
                text = "${entry.points} pts",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Black,
                color = if (entry.isCurrentUser) CyanGlow else TechBlue
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun LivePollsScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text(
          text = "SONDAGES & INTERACTIONS EN DIRECT",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
        Text(
          text = "Participez aux votes interactifs des conférences et keynotes",
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryLight
        )
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      items(uiState.livePolls) { poll ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(16.dp)),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = AlertOrange.copy(alpha = 0.15f)
              ) {
                Text(
                  text = "🔴 EN DIRECT",
                  style = MaterialTheme.typography.labelSmall,
                  color = AlertOrange,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
              Text(
                text = "${poll.totalVotes} votes",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondaryDark
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = poll.question,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = NavyDeep
            )

            Spacer(modifier = Modifier.height(14.dp))

            poll.options.forEach { option ->
              val isVoted = poll.userVotedOptionId == option.id
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .background(if (isVoted) TechBlue.copy(alpha = 0.1f) else LightBackground)
                  .clickable(enabled = poll.userVotedOptionId == null) {
                    viewModel.voteOnPoll(poll.id, option.id)
                  }
                  .padding(12.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = option.text,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isVoted) FontWeight.Bold else FontWeight.Normal,
                    color = NavyDeep
                  )
                  Text(
                    text = "${option.percentage}%",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isVoted) TechBlue else TextSecondaryDark
                  )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                  progress = { option.percentage / 100f },
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                  color = if (isVoted) CyanAccent else TechBlue,
                  trackColor = Color.White
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun QrConnectScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  var selectedTab by remember { mutableIntStateOf(0) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text("QR CONNECT & BADGE ÉLECTRONIQUE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Partagez vos coordonnées ou scannez un contact en 1 seconde", style = MaterialTheme.typography.bodySmall, color = TextSecondaryLight)
      }
    }

    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = Color.White,
      contentColor = TechBlue
    ) {
      Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Mon Badge QR") })
      Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Scanner un Badge") })
    }

    if (selectedTab == 0) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(24.dp)),
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = TechBlueContainer
            ) {
              Text(
                text = "🇸🇳 SEN AURA EVENT 2026 — PASS OFFICIEL",
                style = MaterialTheme.typography.labelSmall,
                color = CyanGlow,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Simulated High-Res QR Matrix Canvas
            Box(
              modifier = Modifier
                .size(190.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(2.dp, NavyDeep, RoundedCornerShape(16.dp))
                .padding(12.dp)
            ) {
              Canvas(modifier = Modifier.fillMaxSize()) {
                val s = size.width
                val numBlocks = 9
                val b = s / numBlocks

                // Draw QR-like pattern
                for (i in 0 until numBlocks) {
                  for (j in 0 until numBlocks) {
                    val isCorner = (i < 3 && j < 3) || (i < 3 && j > 5) || (i > 5 && j < 3)
                    val isPattern = (i * 3 + j * 7 + (i * j)) % 3 == 0 || isCorner
                    if (isPattern) {
                      drawRect(
                        color = if (isCorner) Color(0xFF0B1F33) else Color(0xFF1769AA),
                        topLeft = Offset(i * b, j * b),
                        size = androidx.compose.ui.geometry.Size(b - 2f, b - 2f)
                      )
                    }
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(uiState.userName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = NavyDeep)
            Text("${uiState.userRoleTitle} • ${uiState.userCompany}", style = MaterialTheme.typography.bodyMedium, color = TechBlue, fontWeight = FontWeight.SemiBold)
            Text(uiState.userSector, style = MaterialTheme.typography.labelSmall, color = TextSecondaryDark)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
              onClick = { viewModel.showToast("Badge enregistré dans votre portefeuille mobile") },
              colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().height(44.dp)
            ) {
              Icon(Icons.Default.Download, contentDescription = null)
              Spacer(modifier = Modifier.width(6.dp))
              Text("Télécharger mon Pass PDF")
            }
          }
        }
      }
    } else {
      // Scanner simulation
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = NavyDeep)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text("Viseur de Scanner QR", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Pointez la caméra vers le badge d'un visiteur ou le totem d'un stand pour l'enregistrer instantanément.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryLight, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
              onClick = { viewModel.simulateQrScan("STAND_B24_SEN_AURA") },
              colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
              Icon(Icons.Default.CameraAlt, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Simuler le scan d'un Stand", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

@Composable
fun AnalyticsScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text("EVENT ANALYTICS 📈", style = MaterialTheme.typography.labelSmall, color = CyanAccent, fontWeight = FontWeight.Bold)
            Text("Rapports & Indicateurs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White)
          }

          Button(
            onClick = { viewModel.showToast("Rapport analytique complet généré au format PDF") },
            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
            modifier = Modifier.height(36.dp)
          ) {
            Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Export PDF", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("Répartition Sectorielle des Visiteurs", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
            Spacer(modifier = Modifier.height(10.dp))

            val sectors = listOf(
              "IA & Data Science" to 0.38f,
              "Fintech & Banques" to 0.26f,
              "Télécoms & Cloud" to 0.18f,
              "Smart Cities & Énergie" to 0.12f,
              "Autres" to 0.06f
            )

            sectors.forEach { (sec, pct) ->
              Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                  Text(sec, style = MaterialTheme.typography.bodySmall, color = NavyDeep)
                  Text("${(pct * 100).toInt()}%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TechBlue)
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                  progress = { pct },
                  modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                  color = TechBlue,
                  trackColor = LightBackground
                )
              }
            }
          }
        }
      }

      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("Taux de Conversion Networking & RDV", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
            Spacer(modifier = Modifier.height(8.dp))
            Text("• 92% des demandes de rendez-vous ont été acceptées\n• Temps moyen de réponse : 14 minutes\n• Satisfaction globale de l'écosystème : 4.8 / 5.0 ⭐", style = MaterialTheme.typography.bodyMedium, color = TextSecondaryDark, lineHeight = 22.sp)
          }
        }
      }
    }
  }
}

@Composable
fun NotificationsScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text("CENTRE DE NOTIFICATIONS", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Alertes d'événements, rappels et invitations networking", style = MaterialTheme.typography.bodySmall, color = TextSecondaryLight)
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(uiState.notifications) { notif ->
        val iconColor = when (notif.type) {
          "ALERTE" -> ErrorRed
          "RDV" -> SuccessGreen
          "CONFERENCE" -> TechBlue
          else -> CyanAccent
        }

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.markNotificationAsRead(notif.id) }
            .shadow(2.dp, RoundedCornerShape(14.dp)),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (notif.isRead) Color.White else TechBlueContainer.copy(alpha = 0.4f)
          )
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = when (notif.type) {
                  "ALERTE" -> Icons.Default.Warning
                  "RDV" -> Icons.Default.Handshake
                  "CONFERENCE" -> Icons.Default.Mic
                  else -> Icons.Default.Info
                },
                contentDescription = null,
                tint = iconColor
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(notif.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
                Text(notif.timeAgo, style = MaterialTheme.typography.labelSmall, color = TextSecondaryDark)
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(notif.message, style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            }
          }
        }
      }
    }
  }
}

@Composable
fun PartnerPortalScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text("ESPACE PARTENAIRE OFFICIEL", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Suivi de visibilité, sponsoring et audience de marque", style = MaterialTheme.typography.bodySmall, color = TextSecondaryLight)
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("Visibilité du Sponsoring Platine", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
              StatCard("Impressions Écrans", "148 200", "Bandeaux & Totems", Icons.Default.Tv, TechBlue, modifier = Modifier.weight(1f))
              StatCard("Clics App & Web", "24 800", "Taux 16.7%", Icons.Default.TouchApp, CyanAccent, modifier = Modifier.weight(1f))
            }
          }
        }
      }

      item {
        SenAuraWatermark()
      }
    }
  }
}

@Composable
fun SpeakerPortalScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text("ESPACE CONFÉRENCIER & INTERVENANT", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Gérez vos sessions, supports de présentation et Q&A en direct", style = MaterialTheme.typography.bodySmall, color = TextSecondaryLight)
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("Votre Prochaine Session", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
            Spacer(modifier = Modifier.height(8.dp))
            Text("« L'Intelligence Artificielle au service de la transformation en Afrique »", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TechBlue)
            Text("Aujourd'hui à 09:30 • Salle Plénière Teranga", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            Spacer(modifier = Modifier.height(12.dp))
            Button(
              onClick = { viewModel.showToast("Support de présentation synchronisé avec la régie") },
              colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Icon(Icons.Default.UploadFile, contentDescription = null)
              Spacer(modifier = Modifier.width(6.dp))
              Text("Téléverser mes slides (PDF/PPTX)")
            }
          }
        }
      }

      item {
        SenAuraWatermark()
      }
    }
  }
}

@Composable
fun AdministrationScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text("CONSOLE D'ADMINISTRATION RBAC & SÉCURITÉ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Gestion des droits d'accès, clés API, logs d'audit et cybersécurité", style = MaterialTheme.typography.bodySmall, color = TextSecondaryLight)
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("Contrôle d'Accès Basé sur les Rôles (RBAC)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
            Spacer(modifier = Modifier.height(10.dp))
            UserRole.values().forEach { role ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(role.badgeColor)))
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(role.label, style = MaterialTheme.typography.bodyMedium, color = NavyDeep, fontWeight = FontWeight.SemiBold)
                }
                Text("Actif & Sécurisé", style = MaterialTheme.typography.labelSmall, color = SuccessGreen, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }

      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("Sécurité & Logs d'Audit", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Chiffrement TLS 1.3 de bout en bout activé\n• 0 tentative d'intrusion détectée\n• Intégrité des badges QR validée par signature cryptographique", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark, lineHeight = 20.sp)
          }
        }
      }

      item {
        SenAuraWatermark()
      }
    }
  }
}
