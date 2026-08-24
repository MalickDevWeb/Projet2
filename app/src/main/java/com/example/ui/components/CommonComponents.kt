package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NetworkingProfile
import com.example.model.UserRole
import com.example.ui.theme.*
import com.example.viewmodel.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenAuraTopAppBar(
  title: String,
  subtitle: String? = null,
  activeRole: UserRole,
  unreadNotificationsCount: Int = 2,
  onRoleClick: () -> Unit,
  onNotificationClick: () -> Unit,
  onQrClick: () -> Unit,
  onBackClick: (() -> Unit)? = null
) {
  Surface(
    color = NavyDeep,
    shadowElevation = 4.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          if (onBackClick != null) {
            IconButton(
              onClick = onBackClick,
              modifier = Modifier.testTag("top_bar_back_button")
            ) {
              Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Retour",
                tint = Color.White
              )
            }
            Spacer(modifier = Modifier.width(4.dp))
          } else {
            // Brand Logo Aura Mark
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                  Brush.linearGradient(
                    listOf(CyanAccent, TechBlue)
                  )
                ),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "SA",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
          }

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "🇸🇳",
                fontSize = 14.sp
              )
            }
            if (subtitle != null) {
              Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = CyanAccent,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          }
        }

        // Action controls
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Role switch chip
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(activeRole.badgeColor).copy(alpha = 0.2f),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(activeRole.badgeColor)),
            modifier = Modifier
              .clickable { onRoleClick() }
              .testTag("role_switcher_button")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(Color(activeRole.badgeColor))
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = activeRole.label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
              )
              Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Changer de rôle",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          Spacer(modifier = Modifier.width(8.dp))

          // QR Fast Action
          IconButton(
            onClick = onQrClick,
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(NavySurface)
              .testTag("qr_quick_action_button")
          ) {
            Icon(
              imageVector = Icons.Default.QrCodeScanner,
              contentDescription = "Mon QR Code / Scanner",
              tint = CyanAccent,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          // Notification icon with badge
          IconButton(
            onClick = onNotificationClick,
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(NavySurface)
              .testTag("notifications_button")
          ) {
            BadgedBox(
              badge = {
                if (unreadNotificationsCount > 0) {
                  Badge(
                    containerColor = AlertOrange,
                    contentColor = NavyDeep
                  ) {
                    Text(unreadNotificationsCount.toString())
                  }
                }
              }
            ) {
              Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun SenAuraBottomNavigationBar(
  currentRoute: ScreenRoute,
  onNavigate: (ScreenRoute) -> Unit,
  onOpenAi: () -> Unit
) {
  Surface(
    color = NavyDeep,
    shadowElevation = 16.dp,
    modifier = Modifier
      .fillMaxWidth()
      .windowInsetsPadding(WindowInsets.navigationBars)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      NavTabItem(
        icon = Icons.Default.Home,
        label = "Accueil",
        isSelected = currentRoute is ScreenRoute.VisitorDashboard || currentRoute is ScreenRoute.Landing,
        onClick = { onNavigate(ScreenRoute.VisitorDashboard) },
        testTag = "nav_home"
      )

      NavTabItem(
        icon = Icons.Default.CalendarMonth,
        label = "Programme",
        isSelected = currentRoute is ScreenRoute.Programme || currentRoute is ScreenRoute.ConferenceDetail,
        onClick = { onNavigate(ScreenRoute.Programme) },
        testTag = "nav_programme"
      )

      // Central AI Assistant Action
      Surface(
        modifier = Modifier
          .offset(y = (-10).dp)
          .size(54.dp)
          .shadow(8.dp, CircleShape)
          .clip(CircleShape)
          .background(
            Brush.linearGradient(
              listOf(CyanAccent, TechBlue)
            )
          )
          .clickable { onOpenAi() }
          .testTag("nav_ai_fab"),
        color = Color.Transparent
      ) {
        Column(
          modifier = Modifier.fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.SmartToy,
            contentDescription = "SEN AURA AI Assistant",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
          Text(
            text = "IA",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }

      NavTabItem(
        icon = Icons.Default.Map,
        label = "Carte",
        isSelected = currentRoute is ScreenRoute.SmartMap,
        onClick = { onNavigate(ScreenRoute.SmartMap) },
        testTag = "nav_map"
      )

      NavTabItem(
        icon = Icons.Default.PeopleAlt,
        label = "Réseau",
        isSelected = currentRoute is ScreenRoute.Networking || currentRoute is ScreenRoute.Appointments,
        onClick = { onNavigate(ScreenRoute.Networking) },
        testTag = "nav_networking"
      )
    }
  }
}

@Composable
private fun NavTabItem(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  testTag: String
) {
  Column(
    modifier = Modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() }
      .padding(horizontal = 12.dp, vertical = 6.dp)
      .testTag(testTag),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = if (isSelected) CyanAccent else Color.White.copy(alpha = 0.6f),
      modifier = Modifier.size(22.dp)
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = if (isSelected) CyanAccent else Color.White.copy(alpha = 0.6f),
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
      fontSize = 10.sp
    )
  }
}

@Composable
fun NetworkingProfileCard(
  profile: NetworkingProfile,
  onConnect: () -> Unit,
  onBookMeeting: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
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
        Row(verticalAlignment = Alignment.CenterVertically) {
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
          Column {
            Text(
              text = profile.name,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = NavyDeep
            )
            Text(
              text = "${profile.role} • ${profile.company}",
              style = MaterialTheme.typography.bodySmall,
              color = TechBlue,
              fontWeight = FontWeight.Medium
            )
          }
        }

        // Match Score Badge
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = TechBlueContainer
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${profile.compatibilityScore}% match",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = CyanGlow
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = profile.bio,
        style = MaterialTheme.typography.bodySmall,
        color = TextSecondaryDark,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Interests Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        items(profile.interests) { interest ->
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = LightBackground
          ) {
            Text(
              text = interest,
              style = MaterialTheme.typography.labelSmall,
              color = NavyDeep,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = onConnect,
          colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (profile.isConnected) SuccessGreen else TechBlue
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.weight(1f).height(40.dp)
        ) {
          Icon(
            imageVector = if (profile.isConnected) Icons.Default.Check else Icons.Default.PersonAdd,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (profile.isConnected) "Connecté" else "Se connecter",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Button(
          onClick = onBookMeeting,
          colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.weight(1f).height(40.dp)
        ) {
          Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text("Prendre RDV", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun BookingModalDialog(
  profile: NetworkingProfile,
  onDismiss: () -> Unit,
  onConfirm: (date: String, time: String, location: String, notes: String) -> Unit
) {
  var selectedDate by remember { mutableStateOf("Aujourd'hui") }
  var selectedTime by remember { mutableStateOf("14:30") }
  var selectedLocation by remember { mutableStateOf("Espace B2B - Table 12") }
  var notes by remember { mutableStateOf("") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = "Rendez-vous B2B",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = NavyDeep
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = "Proposer un rendez-vous avec ${profile.name} (${profile.company})",
          style = MaterialTheme.typography.bodyMedium,
          color = TextSecondaryDark
        )

        OutlinedTextField(
          value = selectedDate,
          onValueChange = { selectedDate = it },
          label = { Text("Date du RDV") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
          value = selectedTime,
          onValueChange = { selectedTime = it },
          label = { Text("Créneau horaire") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
          value = selectedLocation,
          onValueChange = { selectedLocation = it },
          label = { Text("Lieu de rencontre") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
          value = notes,
          onValueChange = { notes = it },
          label = { Text("Objet de la rencontre (optionnel)") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp)
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onConfirm(selectedDate, selectedTime, selectedLocation, notes)
          onDismiss()
        },
        colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
        shape = RoundedCornerShape(8.dp)
      ) {
        Text("Envoyer la demande", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Annuler", color = TextSecondaryDark)
      }
    }
  )
}

@Composable
fun StatCard(
  title: String,
  value: String,
  subtitle: String? = null,
  icon: ImageVector,
  accentColor: Color = CyanAccent,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.shadow(2.dp, RoundedCornerShape(16.dp)),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = NavyCard)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(accentColor.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = accentColor,
          modifier = Modifier.size(22.dp)
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = value,
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Black,
          color = Color.White
        )
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium,
          color = TextSecondaryLight
        )
        if (subtitle != null) {
          Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = accentColor,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}

@Composable
fun SectionHeader(
  title: String,
  subtitle: String? = null,
  actionText: String? = null,
  onActionClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .width(4.dp)
            .height(16.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(CyanAccent)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = NavyDeep
        )
      }
      if (subtitle != null) {
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryDark,
          modifier = Modifier.padding(start = 12.dp)
        )
      }
    }

    if (actionText != null && onActionClick != null) {
      TextButton(
        onClick = onActionClick,
        modifier = Modifier.testTag("section_action_${title.take(10)}")
      ) {
        Text(
          text = actionText,
          style = MaterialTheme.typography.labelMedium,
          color = TechBlue,
          fontWeight = FontWeight.SemiBold
        )
        Icon(
          imageVector = Icons.Default.ChevronRight,
          contentDescription = null,
          tint = TechBlue,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}

@Composable
fun SenAuraWatermark(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = "Powered by",
        style = MaterialTheme.typography.labelSmall,
        color = TextSecondaryDark.copy(alpha = 0.7f)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "SEN AURA TECH 🇸🇳",
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = TechBlue
      )
    }
    Text(
      text = "INNOVER • CONSTRUIRE • MAINTENIR • IMPACTER",
      style = MaterialTheme.typography.labelSmall,
      fontSize = 9.sp,
      color = TextSecondaryDark.copy(alpha = 0.5f),
      letterSpacing = 0.8.sp
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSwitcherBottomSheet(
  currentRole: UserRole,
  onRoleSelected: (UserRole) -> Unit,
  onDismiss: () -> Unit
) {
  ModalBottomSheet(
    onDismissRequest = onDismiss,
    containerColor = NavyDeep,
    dragHandle = { BottomSheetDefaults.DragHandle(color = Color.White.copy(alpha = 0.4f)) }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
        .padding(bottom = 32.dp)
    ) {
      Text(
        text = "Changer de Rôle Utilisateur",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
      Text(
        text = "Explorez l'écosystème événementiel sous différents angles et permissions",
        style = MaterialTheme.typography.bodySmall,
        color = TextSecondaryLight
      )

      Spacer(modifier = Modifier.height(16.dp))

      UserRole.values().forEach { role ->
        val isSelected = role == currentRole
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
              onRoleSelected(role)
              onDismiss()
            }
            .testTag("role_option_${role.name}"),
          colors = CardDefaults.cardColors(
            containerColor = if (isSelected) NavyCard else NavySurface
          ),
          border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, CyanAccent) else null
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
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(Color(role.badgeColor)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = when (role) {
                    UserRole.VISITEUR -> Icons.Default.Person
                    UserRole.EXPOSANT -> Icons.Default.Storefront
                    UserRole.ORGANISATEUR -> Icons.Default.Dashboard
                    UserRole.ADMINISTRATEUR -> Icons.Default.AdminPanelSettings
                    UserRole.PARTENAIRE -> Icons.Default.Handshake
                    UserRole.CONFERENCIER -> Icons.Default.Mic
                  },
                  contentDescription = role.label,
                  tint = Color.White,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = role.label,
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
                Text(
                  text = when (role) {
                    UserRole.VISITEUR -> "Parcours visiteur, agenda, IA & networking"
                    UserRole.EXPOSANT -> "Dashboard stand, leads, catalogue & scans QR"
                    UserRole.ORGANISATEUR -> "Event Control, Smart Flow & affichage dynamique"
                    UserRole.ADMINISTRATEUR -> "Gestion RBAC, sécurité, logs & configuration"
                    UserRole.PARTENAIRE -> "Visibilité de marque, sponsoring & métriques"
                    UserRole.CONFERENCIER -> "Fiche conférencier, Q&A & sondages"
                  },
                  style = MaterialTheme.typography.labelSmall,
                  color = TextSecondaryLight
                )
              }
            }

            if (isSelected) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Sélectionné",
                tint = CyanAccent,
                modifier = Modifier.size(22.dp)
              )
            }
          }
        }
      }
    }
  }
}
