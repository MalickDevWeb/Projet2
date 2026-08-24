package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.UserRole
import com.example.ui.components.BookingModalDialog
import com.example.ui.components.RoleSwitcherBottomSheet
import com.example.ui.components.SenAuraBottomNavigationBar
import com.example.ui.components.SenAuraTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.SenAuraTheme
import com.example.viewmodel.EventViewModel
import com.example.viewmodel.ScreenRoute

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SenAuraTheme {
        val eventViewModel: EventViewModel = viewModel()
        SenAuraEventApp(viewModel = eventViewModel)
      }
    }
  }
}

@Composable
fun SenAuraEventApp(viewModel: EventViewModel) {
  val uiState by viewModel.uiState.collectAsState()
  var showRoleSwitcher by remember { mutableStateOf(false) }
  val snackbarHostState = remember { SnackbarHostState() }

  // Observe toast messages
  LaunchedEffect(uiState.toastMessage) {
    uiState.toastMessage?.let { msg ->
      snackbarHostState.showSnackbar(
        message = msg,
        duration = SnackbarDuration.Short
      )
      viewModel.clearToast()
    }
  }

  val isBottomBarVisible = when (uiState.currentRoute) {
    ScreenRoute.Landing,
    ScreenRoute.Login,
    ScreenRoute.Register,
    ScreenRoute.AugmentedReality -> false
    else -> true
  }

  val topBarTitle = when (uiState.currentRoute) {
    ScreenRoute.Landing -> "SEN AURA EVENT"
    ScreenRoute.Login -> "Connexion"
    ScreenRoute.Register -> "Inscription"
    ScreenRoute.VisitorDashboard -> "SEN AURA EVENT"
    ScreenRoute.Programme -> "Programme des Sessions"
    is ScreenRoute.ConferenceDetail -> "Détail de la Conférence"
    ScreenRoute.SmartMap -> "Smart Map & Guidage"
    ScreenRoute.AiAssistant -> "Assistant IA"
    ScreenRoute.Networking -> "Smart Networking"
    ScreenRoute.Appointments -> "Mes Rendez-vous B2B"
    ScreenRoute.ExhibitorDashboard -> "Espace Exposant"
    is ScreenRoute.ExhibitorDetail -> "Fiche Exposant"
    ScreenRoute.Prospects -> "Leads & Prospects"
    ScreenRoute.QrConnect -> "QR Connect & Badge"
    ScreenRoute.OrganizerDashboard -> "Event Control"
    ScreenRoute.SmartFlow -> "Smart Flow & Densité"
    ScreenRoute.DigitalSignage -> "Affichage Dynamique"
    ScreenRoute.Gamification -> "Event Challenge"
    ScreenRoute.LivePolls -> "Sondages en Direct"
    ScreenRoute.AugmentedReality -> "Réalité Augmentée"
    ScreenRoute.Analytics -> "Event Analytics"
    ScreenRoute.Notifications -> "Notifications"
    ScreenRoute.PartnerPortal -> "Espace Partenaire"
    ScreenRoute.SpeakerPortal -> "Espace Conférencier"
    ScreenRoute.Administration -> "Administration RBAC"
  }

  val topBarSubtitle = when (uiState.currentRoute) {
    ScreenRoute.VisitorDashboard -> "Édition Dakar 2026"
    ScreenRoute.OrganizerDashboard -> "Supervision en direct"
    ScreenRoute.ExhibitorDashboard -> "Stand B24"
    else -> null
  }

  val hasBackButton = when (uiState.currentRoute) {
    is ScreenRoute.ConferenceDetail,
    is ScreenRoute.ExhibitorDetail,
    ScreenRoute.Login,
    ScreenRoute.Register,
    ScreenRoute.Appointments,
    ScreenRoute.Prospects,
    ScreenRoute.SmartFlow,
    ScreenRoute.DigitalSignage,
    ScreenRoute.Gamification,
    ScreenRoute.LivePolls,
    ScreenRoute.Analytics,
    ScreenRoute.Notifications,
    ScreenRoute.PartnerPortal,
    ScreenRoute.SpeakerPortal,
    ScreenRoute.Administration,
    ScreenRoute.QrConnect -> true
    else -> false
  }

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      if (uiState.currentRoute != ScreenRoute.AugmentedReality) {
        SenAuraTopAppBar(
          title = topBarTitle,
          subtitle = topBarSubtitle,
          activeRole = uiState.activeRole,
          unreadNotificationsCount = uiState.notifications.count { !it.isRead },
          onRoleClick = { showRoleSwitcher = true },
          onNotificationClick = { viewModel.navigateTo(ScreenRoute.Notifications) },
          onQrClick = { viewModel.navigateTo(ScreenRoute.QrConnect) },
          onBackClick = if (hasBackButton) {
            {
              when (uiState.activeRole) {
                UserRole.VISITEUR -> viewModel.navigateTo(ScreenRoute.VisitorDashboard)
                UserRole.EXPOSANT -> viewModel.navigateTo(ScreenRoute.ExhibitorDashboard)
                UserRole.ORGANISATEUR -> viewModel.navigateTo(ScreenRoute.OrganizerDashboard)
                UserRole.ADMINISTRATEUR -> viewModel.navigateTo(ScreenRoute.Administration)
                UserRole.PARTENAIRE -> viewModel.navigateTo(ScreenRoute.PartnerPortal)
                UserRole.CONFERENCIER -> viewModel.navigateTo(ScreenRoute.SpeakerPortal)
              }
            }
          } else null
        )
      }
    },
    bottomBar = {
      if (isBottomBarVisible) {
        SenAuraBottomNavigationBar(
          currentRoute = uiState.currentRoute,
          onNavigate = { route -> viewModel.navigateTo(route) },
          onOpenAi = { viewModel.navigateTo(ScreenRoute.AiAssistant) }
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (val route = uiState.currentRoute) {
        ScreenRoute.Landing -> LandingScreen(viewModel = viewModel)
        ScreenRoute.Login -> LoginScreen(viewModel = viewModel)
        ScreenRoute.Register -> RegisterScreen(viewModel = viewModel)
        ScreenRoute.VisitorDashboard -> VisitorDashboardScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.Programme -> ProgrammeScreen(uiState = uiState, viewModel = viewModel)
        is ScreenRoute.ConferenceDetail -> ConferenceDetailScreen(conferenceId = route.conferenceId, uiState = uiState, viewModel = viewModel)
        ScreenRoute.SmartMap -> SmartMapScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.AiAssistant -> AiAssistantScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.Networking -> NetworkingScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.Appointments -> AppointmentsListContent(uiState = uiState, viewModel = viewModel)
        ScreenRoute.ExhibitorDashboard -> ExhibitorDashboardScreen(uiState = uiState, viewModel = viewModel)
        is ScreenRoute.ExhibitorDetail -> ExhibitorDetailScreen(exhibitorId = route.exhibitorId, uiState = uiState, viewModel = viewModel)
        ScreenRoute.Prospects -> ProspectsScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.QrConnect -> QrConnectScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.OrganizerDashboard -> OrganizerDashboardScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.SmartFlow -> SmartFlowScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.DigitalSignage -> DigitalSignageScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.Gamification -> GamificationScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.LivePolls -> LivePollsScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.AugmentedReality -> AugmentedRealityScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.Analytics -> AnalyticsScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.Notifications -> NotificationsScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.PartnerPortal -> PartnerPortalScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.SpeakerPortal -> SpeakerPortalScreen(uiState = uiState, viewModel = viewModel)
        ScreenRoute.Administration -> AdministrationScreen(uiState = uiState, viewModel = viewModel)
      }
    }
  }

  // Role Switcher Modal Bottom Sheet
  if (showRoleSwitcher) {
    RoleSwitcherBottomSheet(
      currentRole = uiState.activeRole,
      onRoleSelected = { selectedRole ->
        viewModel.switchRole(selectedRole)
      },
      onDismiss = { showRoleSwitcher = false }
    )
  }

  // Booking Modal Dialog
  if (uiState.isBookingDialogOpen && uiState.bookingTargetProfile != null) {
    BookingModalDialog(
      profile = uiState.bookingTargetProfile!!,
      onDismiss = { viewModel.closeBookingDialog() },
      onConfirm = { date, time, location, notes ->
        viewModel.confirmAppointment(uiState.bookingTargetProfile!!, date, time, location, notes)
      }
    )
  }
}
