package com.example.ui.screens

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
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Conference
import com.example.model.Exhibitor
import com.example.model.NetworkingProfile
import com.example.ui.components.SectionHeader
import com.example.ui.components.SenAuraWatermark
import com.example.ui.theme.*
import com.example.viewmodel.EventUiState
import com.example.viewmodel.EventViewModel
import com.example.viewmodel.ScreenRoute

@Composable
fun VisitorDashboardScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // COUNTDOWN & GREETING HERO
    item {
      Surface(
        color = NavyDeep,
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
        shadowElevation = 4.dp
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Bonjour, ${uiState.userName} 👋",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = Color.White
              )
              Text(
                text = "${uiState.userRoleTitle} • ${uiState.userCompany}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondaryLight
              )
            }

            // Gamification Badge Pill
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = TechBlueContainer,
              border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent),
              modifier = Modifier
                .clickable { viewModel.navigateTo(ScreenRoute.Gamification) }
                .testTag("gamification_badge_button")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.EmojiEvents,
                  contentDescription = null,
                  tint = AlertOrange,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "${uiState.userPoints} pts",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Countdown Card
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = NavySurface)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Timer,
                  contentDescription = null,
                  tint = CyanAccent,
                  modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "Votre événement commence dans :",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondaryLight
                  )
                  Text(
                    text = "02 JOURS 14 H 32 MIN",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = CyanGlow,
                    letterSpacing = 1.sp
                  )
                }
              }

              Button(
                onClick = { viewModel.navigateTo(ScreenRoute.QrConnect) },
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.height(36.dp)
              ) {
                Icon(Icons.Default.QrCode, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Mon Badge", fontWeight = FontWeight.Bold, fontSize = 12.sp)
              }
            }
          }
        }
      }
    }

    // QUICK SHORTCUTS
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        QuickActionChip(
          icon = Icons.Default.SmartToy,
          label = "Assistant IA",
          color = TechBlue,
          onClick = { viewModel.navigateTo(ScreenRoute.AiAssistant) },
          modifier = Modifier.weight(1f)
        )
        QuickActionChip(
          icon = Icons.Default.Map,
          label = "Smart Map",
          color = CyanAccent,
          onClick = { viewModel.navigateTo(ScreenRoute.SmartMap) },
          modifier = Modifier.weight(1f)
        )
        QuickActionChip(
          icon = Icons.Default.ViewInAr,
          label = "Scan AR",
          color = SuccessGreen,
          onClick = { viewModel.startArScan() },
          modifier = Modifier.weight(1f)
        )
        QuickActionChip(
          icon = Icons.Default.HowToVote,
          label = "Sondages",
          color = AlertOrange,
          onClick = { viewModel.navigateTo(ScreenRoute.LivePolls) },
          modifier = Modifier.weight(1f)
        )
      }
    }

    // SECTION "POUR VOUS" — 3 RECOMMANDATIONS INTELLIGENTES IA
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionHeader(
          title = "Pour vous",
          subtitle = "Recommandations générées par l'IA SEN AURA",
          actionText = "Voir IA",
          onActionClick = { viewModel.navigateTo(ScreenRoute.AiAssistant) }
        )

        // 3 Smart Cards
        val recommendedConference = uiState.conferences.firstOrNull()
        val recommendedExhibitor = uiState.exhibitors.firstOrNull()
        val recommendedContact = uiState.networkingProfiles.firstOrNull()

        if (recommendedConference != null) {
          RecommendationCard(
            category = "CONFÉRENCE RECOMMANDÉE",
            title = recommendedConference.title,
            subtitle = "${recommendedConference.speakerName} • ${recommendedConference.time}",
            badge = "Match 98%",
            icon = Icons.Default.Mic,
            accentColor = TechBlue,
            onClick = { viewModel.navigateTo(ScreenRoute.ConferenceDetail(recommendedConference.id)) }
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (recommendedExhibitor != null) {
          RecommendationCard(
            category = "STAND RECOMMANDÉ",
            title = recommendedExhibitor.name,
            subtitle = "${recommendedExhibitor.standNumber} • ${recommendedExhibitor.category}",
            badge = "Pôle Innovation",
            icon = Icons.Default.Storefront,
            accentColor = CyanAccent,
            onClick = { viewModel.navigateTo(ScreenRoute.ExhibitorDetail(recommendedExhibitor.id)) }
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (recommendedContact != null) {
          RecommendationCard(
            category = "NETWORKING MATCH",
            title = "${recommendedContact.name} — ${recommendedContact.role}",
            subtitle = "${recommendedContact.company} • ${recommendedContact.interests.take(2).joinToString(" • ")}",
            badge = "${recommendedContact.compatibilityScore}% compatible",
            icon = Icons.Default.PersonSearch,
            accentColor = SuccessGreen,
            onClick = { viewModel.navigateTo(ScreenRoute.Networking) }
          )
        }
      }
    }

    // SECTION "VOTRE PROGRAMME & RENDEZ-VOUS"
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        SectionHeader(
          title = "Votre programme",
          subtitle = "Vos sessions enregistrées et rendez-vous du jour",
          actionText = "Tout voir",
          onActionClick = { viewModel.navigateTo(ScreenRoute.Programme) }
        )

        val bookmarkedConfs = uiState.conferences.filter { it.isBookmarked }
        if (bookmarkedConfs.isEmpty()) {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = LightSurfaceVariant)
          ) {
            Row(
              modifier = Modifier.padding(16.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.EventAvailable, contentDescription = null, tint = TechBlue)
              Spacer(modifier = Modifier.width(12.dp))
              Text(
                text = "Aucune conférence ajoutée pour l'instant. Explorez le programme !",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondaryDark
              )
            }
          }
        } else {
          bookmarkedConfs.take(2).forEach { conf ->
            ConferenceItemCard(
              conference = conf,
              onBookmarkToggle = { viewModel.toggleBookmarkConference(conf.id) },
              onClick = { viewModel.navigateTo(ScreenRoute.ConferenceDetail(conf.id)) }
            )
            Spacer(modifier = Modifier.height(8.dp))
          }
        }
      }
    }

    // SECTION "À PROXIMITÉ" — STANDS PROCHES
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        SectionHeader(
          title = "À proximité",
          subtitle = "Stands et espaces autour de votre localisation actuelle",
          actionText = "Ouvrir Carte",
          onActionClick = { viewModel.navigateTo(ScreenRoute.SmartMap) }
        )

        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(uiState.exhibitors) { exhibitor ->
            Card(
              modifier = Modifier
                .width(220.dp)
                .clickable { viewModel.navigateTo(ScreenRoute.ExhibitorDetail(exhibitor.id)) }
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
                  Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = TechBlueContainer
                  ) {
                    Text(
                      text = exhibitor.standNumber.split(" ").firstOrNull() ?: "Stand",
                      style = MaterialTheme.typography.labelSmall,
                      color = CyanGlow,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                  }

                  IconButton(
                    onClick = { viewModel.toggleFavoriteExhibitor(exhibitor.id) },
                    modifier = Modifier.size(24.dp)
                  ) {
                    Icon(
                      imageVector = if (exhibitor.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                      contentDescription = null,
                      tint = if (exhibitor.isFavorite) ErrorRed else TextSecondaryDark,
                      modifier = Modifier.size(18.dp)
                    )
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = exhibitor.name,
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = NavyDeep,
                  maxLines = 1
                )
                Text(
                  text = exhibitor.category,
                  style = MaterialTheme.typography.bodySmall,
                  color = TextSecondaryDark,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                  onClick = {
                    val mapLoc = uiState.mapLocations.firstOrNull { it.name.contains(exhibitor.name) }
                        ?: uiState.mapLocations.first()
                    viewModel.startNavigationTo(mapLoc)
                    viewModel.navigateTo(ScreenRoute.SmartMap)
                  },
                  colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                ) {
                  Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Me guider", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      }
    }

    // SECTION "NETWORKING"
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        SectionHeader(
          title = "Networking",
          subtitle = "12 nouvelles recommandations de décideurs",
          actionText = "Explorer",
          onActionClick = { viewModel.navigateTo(ScreenRoute.Networking) }
        )

        uiState.networkingProfiles.take(2).forEach { profile ->
          NetworkingProfileCard(
            profile = profile,
            onConnect = { viewModel.connectWithProfile(profile.id) },
            onBookMeeting = { viewModel.openBookingDialog(profile) }
          )
          Spacer(modifier = Modifier.height(8.dp))
        }
      }
    }

    item {
      SenAuraWatermark()
    }
  }
}

@Composable
private fun QuickActionChip(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  color: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .clickable { onClick() }
      .shadow(1.dp, RoundedCornerShape(12.dp)),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 6.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = label,
          tint = color,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = NavyDeep,
        fontSize = 11.sp,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
private fun RecommendationCard(
  category: String,
  title: String,
  subtitle: String,
  badge: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  accentColor: Color,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .shadow(2.dp, RoundedCornerShape(14.dp)),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(accentColor.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = category,
            style = MaterialTheme.typography.labelSmall,
            color = accentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
          )
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = accentColor.copy(alpha = 0.12f)
          ) {
            Text(
              text = badge,
              style = MaterialTheme.typography.labelSmall,
              color = accentColor,
              fontWeight = FontWeight.Bold,
              fontSize = 9.sp,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = title,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = NavyDeep,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryDark,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
      Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondaryDark, modifier = Modifier.size(18.dp))
    }
  }
}

@Composable
fun ConferenceItemCard(
  conference: Conference,
  onBookmarkToggle: () -> Unit,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
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
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = TechBlueContainer
        ) {
          Text(
            text = conference.category,
            style = MaterialTheme.typography.labelSmall,
            color = CyanGlow,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }

        IconButton(
          onClick = onBookmarkToggle,
          modifier = Modifier.size(32.dp).testTag("bookmark_btn_${conference.id}")
        ) {
          Icon(
            imageVector = if (conference.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = "Ajouter à l'agenda",
            tint = if (conference.isBookmarked) TechBlue else TextSecondaryDark
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = conference.title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = NavyDeep,
        lineHeight = 20.sp
      )

      Spacer(modifier = Modifier.height(6.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Person, contentDescription = null, tint = TechBlue, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "${conference.speakerName} (${conference.speakerCompany})",
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryDark,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Schedule, contentDescription = null, tint = TextSecondaryDark, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = "${conference.date} • ${conference.time}", style = MaterialTheme.typography.labelSmall, color = TextSecondaryDark)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Place, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = conference.room, style = MaterialTheme.typography.labelSmall, color = SuccessGreen, fontWeight = FontWeight.SemiBold)
        }
      }
    }
  }
}

@Composable
fun NetworkingProfileCard(
  profile: NetworkingProfile,
  onConnect: () -> Unit,
  onBookMeeting: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .shadow(2.dp, RoundedCornerShape(14.dp)),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Avatar circle
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(
              Brush.linearGradient(listOf(TechBlue, CyanAccent))
            ),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = profile.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = profile.name,
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = NavyDeep
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = SuccessGreen.copy(alpha = 0.12f)
            ) {
              Text(
                text = "${profile.compatibilityScore}% compatible",
                style = MaterialTheme.typography.labelSmall,
                color = SuccessGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
          Text(
            text = "${profile.role} — ${profile.company}",
            style = MaterialTheme.typography.bodySmall,
            color = TechBlue,
            fontWeight = FontWeight.SemiBold
          )
          Text(
            text = profile.sector,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondaryDark
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Intérêts : ${profile.interests.joinToString(" • ")}",
        style = MaterialTheme.typography.labelSmall,
        color = TextSecondaryDark,
        fontWeight = FontWeight.Medium
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = onConnect,
          enabled = !profile.isConnected,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.weight(1f).height(38.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (profile.isConnected) SuccessGreen else TechBlue
          )
        ) {
          Icon(
            imageVector = if (profile.isConnected) Icons.Default.Check else Icons.Default.PersonAdd,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(if (profile.isConnected) "Connecté" else "Connecter", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = onBookMeeting,
          colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.weight(1f).height(38.dp)
        ) {
          Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Prendre RDV", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun ProgrammeScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  val categories = listOf("Toutes", "Intelligence Artificielle", "Fintech & Finance", "Cybersécurité", "Smart Cities & GreenTech", "Investissement & Startups")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 80.dp)
  ) {
    // Search & Filter header
    Surface(
      color = Color.White,
      shadowElevation = 2.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        OutlinedTextField(
          value = uiState.conferenceSearchQuery,
          onValueChange = { viewModel.setConferenceSearchQuery(it) },
          placeholder = { Text("Rechercher conférence, intervenant, salle...") },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TechBlue) },
          trailingIcon = {
            if (uiState.conferenceSearchQuery.isNotBlank()) {
              IconButton(onClick = { viewModel.setConferenceSearchQuery("") }) {
                Icon(Icons.Default.Close, contentDescription = null)
              }
            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("conference_search_input"),
          shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(categories) { category ->
            val isSelected = category == uiState.selectedConferenceCategory
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.setConferenceCategoryFilter(category) },
              label = { Text(category, fontSize = 12.sp) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = TechBlue,
                selectedLabelColor = Color.White
              ),
              shape = RoundedCornerShape(20.dp)
            )
          }
        }
      }
    }

    // Filtered List
    val filteredList = uiState.conferences.filter { conf ->
      val matchesCategory = uiState.selectedConferenceCategory == "Toutes" || conf.category == uiState.selectedConferenceCategory
      val matchesSearch = uiState.conferenceSearchQuery.isBlank() ||
          conf.title.contains(uiState.conferenceSearchQuery, ignoreCase = true) ||
          conf.speakerName.contains(uiState.conferenceSearchQuery, ignoreCase = true) ||
          conf.room.contains(uiState.conferenceSearchQuery, ignoreCase = true)
      matchesCategory && matchesSearch
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${filteredList.size} Conférences disponibles",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = NavyDeep
          )
          Text(
            text = "Jour 1 & Jour 2",
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondaryDark
          )
        }
      }

      items(filteredList) { conf ->
        ConferenceItemCard(
          conference = conf,
          onBookmarkToggle = { viewModel.toggleBookmarkConference(conf.id) },
          onClick = { viewModel.navigateTo(ScreenRoute.ConferenceDetail(conf.id)) }
        )
      }
    }
  }
}

@Composable
fun ConferenceDetailScreen(
  conferenceId: String,
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  val conference = uiState.conferences.firstOrNull { it.id == conferenceId } ?: uiState.conferences.first()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground),
    contentPadding = PaddingValues(bottom = 90.dp)
  ) {
    // Header Banner
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
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = TechBlueContainer
          ) {
            Text(
              text = conference.category,
              style = MaterialTheme.typography.labelSmall,
              color = CyanGlow,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = conference.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
            color = Color.White,
            lineHeight = 28.sp
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CalendarToday, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "${conference.date} • ${conference.time}", color = Color.White, style = MaterialTheme.typography.bodySmall)
          }

          Spacer(modifier = Modifier.height(6.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Room, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = conference.room, color = Color.White, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // SPEAKER CARD
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
          .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(CircleShape)
              .background(Brush.linearGradient(listOf(TechBlue, CyanAccent))),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
          }
          Spacer(modifier = Modifier.width(14.dp))
          Column {
            Text(
              text = conference.speakerName,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = NavyDeep
            )
            Text(
              text = "${conference.speakerRole} • ${conference.speakerCompany}",
              style = MaterialTheme.typography.bodySmall,
              color = TechBlue,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }

    // SYNOPSIS & TOPICS
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
          Text(
            text = "Description de la session",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = NavyDeep
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = conference.description,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryDark,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Thématiques clés :",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = NavyDeep
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            conference.topics.forEach { topic ->
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = LightBackground
              ) {
                Text(
                  text = topic,
                  style = MaterialTheme.typography.labelSmall,
                  color = TechBlue,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }
    }

    // LIVE SEATS PROGRESS
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Places disponibles",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = NavyDeep
            )
            Text(
              text = "${conference.reservedSeats} / ${conference.maxSeats} inscrits",
              style = MaterialTheme.typography.labelSmall,
              color = TextSecondaryDark
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          LinearProgressIndicator(
            progress = { conference.reservedSeats / conference.maxSeats.toFloat() },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = if (conference.reservedSeats > conference.maxSeats * 0.9) ErrorRed else SuccessGreen,
            trackColor = LightBackground
          )
        }
      }
    }

    // ACTION BUTTONS
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = { viewModel.toggleBookmarkConference(conference.id) },
          colors = ButtonDefaults.buttonColors(
            containerColor = if (conference.isBookmarked) SuccessGreen else TechBlue
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
          Icon(
            imageVector = if (conference.isBookmarked) Icons.Default.Check else Icons.Default.BookmarkAdd,
            contentDescription = null
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (conference.isBookmarked) "Inscrit dans votre agenda" else "Ajouter à mon agenda",
            fontWeight = FontWeight.Bold
          )
        }

        OutlinedButton(
          onClick = { viewModel.navigateTo(ScreenRoute.LivePolls) },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
          Icon(Icons.Default.HowToVote, contentDescription = null, tint = AlertOrange)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Participer au sondage en direct", color = NavyDeep, fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
          onClick = { viewModel.showToast("Lien de la conférence copié pour partage !") },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
          Icon(Icons.Default.Share, contentDescription = null, tint = TechBlue)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Partager la session", color = NavyDeep, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
