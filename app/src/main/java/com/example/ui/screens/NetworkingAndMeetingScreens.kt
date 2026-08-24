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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Appointment
import com.example.model.AppointmentStatus
import com.example.model.NetworkingProfile
import com.example.ui.theme.*
import com.example.viewmodel.EventUiState
import com.example.viewmodel.EventViewModel

@Composable
fun NetworkingScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("Recommandations IA", "Mes Connexions", "Mes Rendez-vous")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    // Header & Search
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
          text = "SMART NETWORKING B2B",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
        Text(
          text = "Rencontrez les décideurs compatibles avec vos objectifs",
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryLight
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = uiState.networkingSearchQuery,
          onValueChange = { viewModel.setNetworkingSearchQuery(it) },
          placeholder = { Text("Rechercher un profil, entreprise, secteur...", color = TextSecondaryLight) },
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
            .testTag("networking_search_input"),
          shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tabs
        TabRow(
          selectedTabIndex = selectedTab,
          containerColor = NavyDeep,
          contentColor = CyanAccent
        ) {
          tabs.forEachIndexed { index, title ->
            Tab(
              selected = selectedTab == index,
              onClick = { selectedTab = index },
              text = {
                Text(
                  text = title,
                  color = if (selectedTab == index) CyanAccent else TextSecondaryLight,
                  fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                  fontSize = 12.sp
                )
              }
            )
          }
        }
      }
    }

    // Content based on tab
    when (selectedTab) {
      0 -> {
        val filteredProfiles = uiState.networkingProfiles.filter { prof ->
          uiState.networkingSearchQuery.isBlank() ||
              prof.name.contains(uiState.networkingSearchQuery, ignoreCase = true) ||
              prof.company.contains(uiState.networkingSearchQuery, ignoreCase = true) ||
              prof.sector.contains(uiState.networkingSearchQuery, ignoreCase = true)
        }

        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          item {
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = TechBlueContainer)
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "Algorithme SEN AURA actif : vos correspondances sont triées par affinité sectorielle et complémentarité.",
                  style = MaterialTheme.typography.labelSmall,
                  color = Color.White
                )
              }
            }
          }

          items(filteredProfiles) { profile ->
            NetworkingProfileCard(
              profile = profile,
              onConnect = { viewModel.connectWithProfile(profile.id) },
              onBookMeeting = { viewModel.openBookingDialog(profile) }
            )
          }
        }
      }

      1 -> {
        val connectedProfiles = uiState.networkingProfiles.filter { it.isConnected }
        if (connectedProfiles.isEmpty()) {
          Box(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(Icons.Default.PeopleOutline, contentDescription = null, tint = TechBlue, modifier = Modifier.size(48.dp))
              Spacer(modifier = Modifier.height(12.dp))
              Text("Aucune connexion pour le moment", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NavyDeep)
              Text("Invitez des participants depuis l'onglet Recommandations IA", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            }
          }
        } else {
          LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            items(connectedProfiles) { profile ->
              NetworkingProfileCard(
                profile = profile,
                onConnect = { viewModel.connectWithProfile(profile.id) },
                onBookMeeting = { viewModel.openBookingDialog(profile) }
              )
            }
          }
        }
      }

      2 -> {
        AppointmentsListContent(uiState = uiState, viewModel = viewModel)
      }
    }
  }
}

@Composable
fun AppointmentsListContent(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  var filterStatus by remember { mutableStateOf<AppointmentStatus?>(null) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.padding(bottom = 12.dp)
    ) {
      item {
        FilterChip(
          selected = filterStatus == null,
          onClick = { filterStatus = null },
          label = { Text("Tous (${uiState.appointments.size})") }
        )
      }
      items(AppointmentStatus.values()) { status ->
        val count = uiState.appointments.count { it.status == status }
        FilterChip(
          selected = filterStatus == status,
          onClick = { filterStatus = status },
          label = { Text("${status.label} ($count)") }
        )
      }
    }

    val displayed = uiState.appointments.filter { filterStatus == null || it.status == filterStatus }

    if (displayed.isEmpty()) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightSurfaceVariant)
      ) {
        Column(
          modifier = Modifier.padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Icon(Icons.Default.CalendarToday, contentDescription = null, tint = TechBlue, modifier = Modifier.size(36.dp))
          Spacer(modifier = Modifier.height(8.dp))
          Text("Aucun rendez-vous dans cette catégorie.", style = MaterialTheme.typography.bodyMedium, color = TextSecondaryDark)
        }
      }
    } else {
      LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(displayed) { apt ->
          AppointmentCard(
            appointment = apt,
            onAccept = { viewModel.updateAppointmentStatus(apt.id, AppointmentStatus.ACCEPTE) },
            onDecline = { viewModel.updateAppointmentStatus(apt.id, AppointmentStatus.REFUSE) },
            onComplete = { viewModel.updateAppointmentStatus(apt.id, AppointmentStatus.TERMINE) }
          )
        }
      }
    }
  }
}

@Composable
fun AppointmentCard(
  appointment: Appointment,
  onAccept: () -> Unit,
  onDecline: () -> Unit,
  onComplete: () -> Unit
) {
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
            text = appointment.personName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = NavyDeep
          )
          Text(
            text = "${appointment.personRole} • ${appointment.personCompany}",
            style = MaterialTheme.typography.bodySmall,
            color = TechBlue
          )
        }

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color(appointment.status.color).copy(alpha = 0.15f)
        ) {
          Text(
            text = appointment.status.label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(appointment.status.color),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.AccessTime, contentDescription = null, tint = TextSecondaryDark, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = "${appointment.date} à ${appointment.time}", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
      }

      Spacer(modifier = Modifier.height(4.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.LocationOn, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = appointment.location, style = MaterialTheme.typography.bodySmall, color = SuccessGreen, fontWeight = FontWeight.SemiBold)
      }

      if (appointment.notes.isNotBlank()) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "Note : « ${appointment.notes} »",
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryDark,
          fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      when (appointment.status) {
        AppointmentStatus.DEMANDE -> {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            OutlinedButton(
              onClick = onDecline,
              colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f).height(36.dp)
            ) {
              Text("Décliner", fontSize = 12.sp)
            }
            Button(
              onClick = onAccept,
              colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f).height(36.dp)
            ) {
              Text("Accepter", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        }

        AppointmentStatus.ACCEPTE -> {
          Button(
            onClick = onComplete,
            colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().height(36.dp)
          ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Marquer comme effectué", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }

        else -> {}
      }
    }
  }
}
