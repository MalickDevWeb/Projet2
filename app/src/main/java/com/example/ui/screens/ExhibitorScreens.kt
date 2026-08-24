package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Exhibitor
import com.example.model.ProspectLead
import com.example.ui.components.SectionHeader
import com.example.ui.components.SenAuraWatermark
import com.example.ui.components.StatCard
import com.example.ui.theme.*
import com.example.viewmodel.EventUiState
import com.example.viewmodel.EventViewModel
import com.example.viewmodel.ScreenRoute

@Composable
fun ExhibitorDashboardScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  val exhibitor = uiState.exhibitors.firstOrNull { it.id == "exh_1" } ?: uiState.exhibitors.first()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // HERO STAND BANNER
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
                text = "ESPACE EXPOSANT",
                style = MaterialTheme.typography.labelSmall,
                color = CyanAccent,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
              )
              Text(
                text = exhibitor.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = Color.White
              )
              Text(
                text = "${exhibitor.standNumber} • Pavillon Principal (Zone B)",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondaryLight
              )
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = TechBlueContainer,
              border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent)
            ) {
              Text(
                text = "STAND B24",
                color = CyanGlow,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Quick Scan CTA
          Button(
            onClick = { viewModel.openQrScanner() },
            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("scan_visitor_badge_btn")
          ) {
            Icon(Icons.Default.QrCodeScanner, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Scanner le badge d'un visiteur", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // STAND METRICS & KPIS
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        SectionHeader(
          title = "Performances du stand",
          subtitle = "Statistiques d'engagement en direct"
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            title = "Visites Stand",
            value = exhibitor.visitsCount.toString(),
            subtitle = "+18% aujourd'hui",
            icon = Icons.Default.Visibility,
            accentColor = CyanAccent,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            title = "Scans QR",
            value = exhibitor.qrScansCount.toString(),
            subtitle = "Brochure & Fiches",
            icon = Icons.Default.QrCode,
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
            title = "Leads Qualifiés",
            value = exhibitor.leadsCount.toString(),
            subtitle = "Contacts chauds",
            icon = Icons.Default.ContactPhone,
            accentColor = SuccessGreen,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            title = "Rendez-vous",
            value = exhibitor.meetingsCount.toString(),
            subtitle = "Confirmés B2B",
            icon = Icons.Default.Handshake,
            accentColor = AlertOrange,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // QUALIFIED PROSPECTS PREVIEW
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionHeader(
          title = "Derniers prospects scannés",
          subtitle = "Vos contacts enregistrés sur le stand",
          actionText = "Gérer les leads",
          onActionClick = { viewModel.navigateTo(ScreenRoute.Prospects) }
        )

        uiState.prospectLeads.take(3).forEach { lead ->
          val statusColor = when (lead.interestLevel) {
            "Chaud" -> ErrorRed
            "Tiède" -> AlertOrange
            else -> TechBlue
          }
          val statusEmoji = when (lead.interestLevel) {
            "Chaud" -> "🔥"
            "Tiède" -> "⏳"
            else -> "📋"
          }

          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
              .shadow(2.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
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
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(statusColor.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(statusEmoji, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = lead.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = NavyDeep
                  )
                  Text(
                    text = "${lead.role} • ${lead.company}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryDark
                  )
                  Text(
                    text = "Scanné à ${lead.scannedAt}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TechBlue
                  )
                }
              }

              Surface(
                shape = RoundedCornerShape(8.dp),
                color = statusColor.copy(alpha = 0.12f)
              ) {
                Text(
                  text = "$statusEmoji ${lead.interestLevel}",
                  style = MaterialTheme.typography.labelSmall,
                  color = statusColor,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }
    }

    // PRODUCTS CATALOG SECTION
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        SectionHeader(
          title = "Produits & Solutions exposés",
          subtitle = "Présentés sur votre stand B24"
        )

        exhibitor.products.forEach { product ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
              .shadow(2.dp, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = product.name,
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = NavyDeep
                )
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = TechBlueContainer
                ) {
                  Text(
                    text = "DÉMO LIVE",
                    style = MaterialTheme.typography.labelSmall,
                    color = CyanGlow,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondaryDark
              )

              Spacer(modifier = Modifier.height(8.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                product.tags.forEach { tag ->
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = LightBackground
                  ) {
                    Text(
                      text = tag,
                      style = MaterialTheme.typography.labelSmall,
                      color = TechBlue,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                }
              }
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
fun ExhibitorDetailScreen(
  exhibitorId: String,
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  val exhibitor = uiState.exhibitors.firstOrNull { it.id == exhibitorId } ?: uiState.exhibitors.first()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // Header Hero
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(listOf(NavyDeep, NavySurface))
          )
          .padding(20.dp)
      ) {
        Column {
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
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }

            IconButton(
              onClick = { viewModel.toggleFavoriteExhibitor(exhibitor.id) }
            ) {
              Icon(
                imageVector = if (exhibitor.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = if (exhibitor.isFavorite) ErrorRed else Color.White
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = exhibitor.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            color = Color.White
          )

          Text(
            text = exhibitor.category,
            style = MaterialTheme.typography.bodyMedium,
            color = CyanAccent,
            fontWeight = FontWeight.SemiBold
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = exhibitor.description,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            lineHeight = 22.sp
          )
        }
      }
    }

    // Quick Actions: Me guider & Prendre RDV
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = {
            val mapLoc = uiState.mapLocations.firstOrNull { it.code == exhibitor.standNumber.split(" ").firstOrNull() }
                ?: uiState.mapLocations.first()
            viewModel.startNavigationTo(mapLoc)
            viewModel.navigateTo(ScreenRoute.SmartMap)
          },
          colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.weight(1f).height(48.dp)
        ) {
          Icon(Icons.Default.Navigation, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Itinéraire", fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = {
            val prof = uiState.networkingProfiles.firstOrNull { it.company.contains(exhibitor.name) }
                ?: uiState.networkingProfiles.first()
            viewModel.openBookingDialog(prof)
          },
          colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.weight(1f).height(48.dp)
        ) {
          Icon(Icons.Default.CalendarMonth, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Prendre RDV", fontWeight = FontWeight.Bold)
        }
      }
    }

    // PRODUCTS LIST
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionHeader(
          title = "Produits & Innovations exposés",
          subtitle = "Disponibles en démonstration sur le stand"
        )

        exhibitor.products.forEach { prod ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 6.dp)
              .shadow(2.dp, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(
                text = prod.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = NavyDeep
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = prod.description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryDark
              )
              Spacer(modifier = Modifier.height(10.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                prod.tags.forEach { tag ->
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = LightBackground
                  ) {
                    Text(
                      text = tag,
                      style = MaterialTheme.typography.labelSmall,
                      color = TechBlue,
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    // CONTACT INFO CARD
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Coordonnées de l'exposant", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NavyDeep)
          Spacer(modifier = Modifier.height(8.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Language, contentDescription = null, tint = TechBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(exhibitor.website, color = TechBlue, style = MaterialTheme.typography.bodySmall)
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Email, contentDescription = null, tint = TechBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(exhibitor.contactEmail, color = NavyDeep, style = MaterialTheme.typography.bodySmall)
          }
        }
      }
    }
  }
}

@Composable
fun ProspectsScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  var filterStatus by remember { mutableStateOf<String?>(null) }
  val levels = listOf("Chaud", "Tiède", "Suivi")

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
            Text(
              text = "PROSPECTS & LEADS CAPTURÉS",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = "${uiState.prospectLeads.size} contacts qualifiés sur votre stand",
              style = MaterialTheme.typography.bodySmall,
              color = TextSecondaryLight
            )
          }

          Button(
            onClick = { viewModel.showToast("Export CSV généré et envoyé à votre adresse email") },
            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
            modifier = Modifier.height(36.dp)
          ) {
            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Export CSV", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
      item {
        FilterChip(
          selected = filterStatus == null,
          onClick = { filterStatus = null },
          label = { Text("Tous les leads (${uiState.prospectLeads.size})") }
        )
      }
      items(levels) { level ->
        val count = uiState.prospectLeads.count { it.interestLevel == level }
        val emoji = when (level) {
          "Chaud" -> "🔥"
          "Tiède" -> "⏳"
          else -> "📋"
        }
        FilterChip(
          selected = filterStatus == level,
          onClick = { filterStatus = level },
          label = { Text("$emoji $level ($count)") }
        )
      }
    }

    val displayed = uiState.prospectLeads.filter { filterStatus == null || it.interestLevel == filterStatus }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(displayed) { lead ->
        val statusColor = when (lead.interestLevel) {
          "Chaud" -> ErrorRed
          "Tiède" -> AlertOrange
          else -> TechBlue
        }
        val statusEmoji = when (lead.interestLevel) {
          "Chaud" -> "🔥"
          "Tiède" -> "⏳"
          else -> "📋"
        }

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(14.dp)),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = lead.name,
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = NavyDeep
                )
                Text(
                  text = "${lead.role} — ${lead.company}",
                  style = MaterialTheme.typography.bodySmall,
                  color = TechBlue,
                  fontWeight = FontWeight.SemiBold
                )
              }

              Surface(
                shape = RoundedCornerShape(8.dp),
                color = statusColor.copy(alpha = 0.15f)
              ) {
                Text(
                  text = "$statusEmoji ${lead.interestLevel}",
                  style = MaterialTheme.typography.labelSmall,
                  color = statusColor,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Email, contentDescription = null, tint = TextSecondaryDark, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(lead.email, style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
              Spacer(modifier = Modifier.width(12.dp))
              Icon(Icons.Default.Phone, contentDescription = null, tint = TextSecondaryDark, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(lead.phone, style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = LightBackground,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "Note : ${lead.notes}",
                style = MaterialTheme.typography.bodySmall,
                color = NavyDeep,
                modifier = Modifier.padding(10.dp)
              )
            }
          }
        }
      }
    }
  }
}
