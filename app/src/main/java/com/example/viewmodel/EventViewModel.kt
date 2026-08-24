package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ScreenRoute {
  object Landing : ScreenRoute()
  object Login : ScreenRoute()
  object Register : ScreenRoute()
  object VisitorDashboard : ScreenRoute()
  object Programme : ScreenRoute()
  data class ConferenceDetail(val conferenceId: String) : ScreenRoute()
  object SmartMap : ScreenRoute()
  object AiAssistant : ScreenRoute()
  object Networking : ScreenRoute()
  object Appointments : ScreenRoute()
  object ExhibitorDashboard : ScreenRoute()
  data class ExhibitorDetail(val exhibitorId: String) : ScreenRoute()
  object Prospects : ScreenRoute()
  object QrConnect : ScreenRoute()
  object OrganizerDashboard : ScreenRoute()
  object SmartFlow : ScreenRoute()
  object DigitalSignage : ScreenRoute()
  object Gamification : ScreenRoute()
  object LivePolls : ScreenRoute()
  object AugmentedReality : ScreenRoute()
  object Analytics : ScreenRoute()
  object Notifications : ScreenRoute()
  object PartnerPortal : ScreenRoute()
  object SpeakerPortal : ScreenRoute()
  object Administration : ScreenRoute()
}

data class EventUiState(
  val currentRoute: ScreenRoute = ScreenRoute.Landing,
  val activeRole: UserRole = UserRole.VISITEUR,
  val userName: String = "Papa Malick",
  val userCompany: String = "SEN AURA TECH",
  val userRoleTitle: String = "Lead Architect & Developer",
  val userSector: String = "Intelligence Artificielle & Cloud",
  val userInterests: List<String> = listOf("IA", "Fintech", "Cloud Souverain", "Smart Cities"),
  val userObjective: String = "Découvrir des innovations & Partenariats",
  val userPoints: Int = 600,
  val userRank: Int = 4,

  val conferences: List<Conference> = SampleData.initialConferences,
  val exhibitors: List<Exhibitor> = SampleData.initialExhibitors,
  val networkingProfiles: List<NetworkingProfile> = SampleData.initialNetworkingProfiles,
  val appointments: List<Appointment> = SampleData.initialAppointments,
  val mapLocations: List<MapLocation> = SampleData.mapLocations,
  val digitalScreens: List<DigitalScreen> = SampleData.digitalScreens,
  val livePolls: List<LivePoll> = SampleData.livePolls,
  val challengeQuests: List<ChallengeQuest> = SampleData.challengeQuests,
  val leaderboard: List<LeaderboardEntry> = SampleData.leaderboard,
  val notifications: List<AppNotification> = SampleData.notifications,
  val prospectLeads: List<ProspectLead> = SampleData.prospectLeads,
  val chatMessages: List<ChatMessage> = SampleData.initialChatMessages,

  // Filters & State
  val selectedConferenceCategory: String = "Toutes",
  val conferenceSearchQuery: String = "",
  val exhibitorSearchQuery: String = "",
  val networkingSearchQuery: String = "",
  val mapSelectedLocation: MapLocation? = null,
  val isNavigatingToStand: Boolean = false,
  val navigationTarget: MapLocation? = null,
  val isArScanning: Boolean = false,
  val arScannedExhibitor: Exhibitor? = null,
  val qrScanResult: String? = null,
  val isQrScannerOpen: Boolean = false,
  val isBookingDialogOpen: Boolean = false,
  val bookingTargetProfile: NetworkingProfile? = null,
  val toastMessage: String? = null
)

class EventViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(EventUiState())
  val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

  fun navigateTo(route: ScreenRoute) {
    _uiState.update { it.copy(currentRoute = route) }
  }

  fun switchRole(role: UserRole) {
    _uiState.update { state ->
      val newRoute = when (role) {
        UserRole.VISITEUR -> ScreenRoute.VisitorDashboard
        UserRole.EXPOSANT -> ScreenRoute.ExhibitorDashboard
        UserRole.ORGANISATEUR -> ScreenRoute.OrganizerDashboard
        UserRole.ADMINISTRATEUR -> ScreenRoute.Administration
        UserRole.PARTENAIRE -> ScreenRoute.PartnerPortal
        UserRole.CONFERENCIER -> ScreenRoute.SpeakerPortal
      }
      state.copy(activeRole = role, currentRoute = newRoute)
    }
    showToast("Profil actif basculé en mode ${role.label}")
  }

  fun toggleBookmarkConference(conferenceId: String) {
    _uiState.update { state ->
      val updatedList = state.conferences.map { conf ->
        if (conf.id == conferenceId) {
          val newBookmark = !conf.isBookmarked
          val change = if (newBookmark) 1 else -1
          conf.copy(
            isBookmarked = newBookmark,
            reservedSeats = (conf.reservedSeats + change).coerceIn(0, conf.maxSeats)
          )
        } else conf
      }
      val isNowBookmarked = updatedList.firstOrNull { it.id == conferenceId }?.isBookmarked == true
      if (isNowBookmarked) {
        addPoints(50, "Conférence ajoutée à votre agenda")
      }
      state.copy(conferences = updatedList)
    }
  }

  fun toggleFavoriteExhibitor(exhibitorId: String) {
    _uiState.update { state ->
      val updatedList = state.exhibitors.map { exh ->
        if (exh.id == exhibitorId) exh.copy(isFavorite = !exh.isFavorite) else exh
      }
      state.copy(exhibitors = updatedList)
    }
  }

  fun connectWithProfile(profileId: String) {
    _uiState.update { state ->
      val updatedList = state.networkingProfiles.map { prof ->
        if (prof.id == profileId) prof.copy(isConnected = true) else prof
      }
      addPoints(100, "Nouvelle connexion réseau établie")
      state.copy(networkingProfiles = updatedList)
    }
  }

  fun openBookingDialog(profile: NetworkingProfile) {
    _uiState.update {
      it.copy(
        isBookingDialogOpen = true,
        bookingTargetProfile = profile
      )
    }
  }

  fun closeBookingDialog() {
    _uiState.update {
      it.copy(
        isBookingDialogOpen = false,
        bookingTargetProfile = null
      )
    }
  }

  fun confirmAppointment(
    profile: NetworkingProfile,
    date: String,
    time: String,
    location: String,
    notes: String
  ) {
    val newAppointment = Appointment(
      id = "apt_${System.currentTimeMillis()}",
      personName = profile.name,
      personRole = profile.role,
      personCompany = profile.company,
      date = date,
      time = time,
      location = location,
      status = AppointmentStatus.DEMANDE,
      notes = notes
    )
    _uiState.update { state ->
      val updatedAppointments = listOf(newAppointment) + state.appointments
      val updatedProfiles = state.networkingProfiles.map { prof ->
        if (prof.id == profile.id) prof.copy(meetingStatus = AppointmentStatus.DEMANDE) else prof
      }
      state.copy(
        appointments = updatedAppointments,
        networkingProfiles = updatedProfiles,
        isBookingDialogOpen = false,
        bookingTargetProfile = null
      )
    }
    addPoints(75, "Demande de rendez-vous envoyée à ${profile.name}")
  }

  fun updateAppointmentStatus(appointmentId: String, newStatus: AppointmentStatus) {
    _uiState.update { state ->
      val updatedAppointments = state.appointments.map { apt ->
        if (apt.id == appointmentId) apt.copy(status = newStatus) else apt
      }
      state.copy(appointments = updatedAppointments)
    }
    showToast("Statut du rendez-vous actualisé : ${newStatus.label}")
  }

  fun voteOnPoll(pollId: String, optionId: String) {
    _uiState.update { state ->
      val updatedPolls = state.livePolls.map { poll ->
        if (poll.id == pollId && poll.userVotedOptionId == null) {
          val newTotal = poll.totalVotes + 1
          val updatedOptions = poll.options.map { opt ->
            val optVotes = if (opt.id == optionId) opt.votes + 1 else opt.votes
            val pct = ((optVotes.toFloat() / newTotal) * 100).toInt()
            opt.copy(votes = optVotes, percentage = pct)
          }
          poll.copy(
            options = updatedOptions,
            totalVotes = newTotal,
            userVotedOptionId = optionId
          )
        } else poll
      }
      addPoints(100, "Vote enregistré sur le sondage en direct")
      state.copy(livePolls = updatedPolls)
    }
  }

  fun selectMapLocation(location: MapLocation?) {
    _uiState.update { it.copy(mapSelectedLocation = location) }
  }

  fun startNavigationTo(location: MapLocation) {
    _uiState.update {
      it.copy(
        navigationTarget = location,
        isNavigatingToStand = true,
        mapSelectedLocation = location
      )
    }
    showToast("Itinéraire activé vers ${location.name}")
  }

  fun stopNavigation() {
    _uiState.update {
      it.copy(
        navigationTarget = null,
        isNavigatingToStand = false
      )
    }
  }

  fun startArScan(exhibitor: Exhibitor? = null) {
    val targetExhibitor = exhibitor ?: _uiState.value.exhibitors.firstOrNull()
    _uiState.update {
      it.copy(
        isArScanning = true,
        arScannedExhibitor = targetExhibitor,
        currentRoute = ScreenRoute.AugmentedReality
      )
    }
    addPoints(120, "Scan de Réalité Augmentée réussi")
  }

  fun closeArScan() {
    _uiState.update {
      it.copy(
        isArScanning = false,
        arScannedExhibitor = null
      )
    }
  }

  fun openQrScanner() {
    _uiState.update { it.copy(isQrScannerOpen = true, qrScanResult = null) }
  }

  fun simulateQrScan(resultTag: String = "STAND_B24_SEN_AURA") {
    _uiState.update {
      it.copy(
        isQrScannerOpen = false,
        qrScanResult = resultTag
      )
    }
    addPoints(150, "QR Code scanné avec succès : Stand B24")
  }

  fun closeQrScanner() {
    _uiState.update { it.copy(isQrScannerOpen = false) }
  }

  fun updateDigitalScreenContent(screenId: String, newContent: String, isAlert: Boolean = false) {
    _uiState.update { state ->
      val updatedScreens = state.digitalScreens.map { scr ->
        if (scr.id == screenId) {
          scr.copy(
            activeBanner = newContent,
            isAlertActive = isAlert
          )
        } else scr
      }
      state.copy(digitalScreens = updatedScreens)
    }
    showToast("Affichage dynamique mis à jour sur l'écran sélectionné")
  }

  fun sendAiMessage(userText: String) {
    if (userText.isBlank()) return

    val userMessage = ChatMessage(
      id = "msg_${System.currentTimeMillis()}",
      isUser = true,
      text = userText.trim(),
      timestamp = "Maintenant"
    )

    _uiState.update { it.copy(chatMessages = it.chatMessages + userMessage) }

    // Smart semantic event grounding response
    viewModelScope.launch {
      val responseText = generateEventAiResponse(userText.trim())
      val aiMessage = ChatMessage(
        id = "msg_ai_${System.currentTimeMillis()}",
        isUser = false,
        text = responseText.text,
        timestamp = "Maintenant",
        suggestedActions = responseText.suggestions,
        linkedEntityId = responseText.entityId,
        linkedEntityType = responseText.entityType
      )
      _uiState.update { it.copy(chatMessages = it.chatMessages + aiMessage) }
    }
  }

  private data class AiResponseBundle(
    val text: String,
    val suggestions: List<String> = emptyList(),
    val entityId: String? = null,
    val entityType: String? = null
  )

  private fun generateEventAiResponse(prompt: String): AiResponseBundle {
    val lower = prompt.lowercase()
    return when {
      lower.contains("conférence") || lower.contains("programme") || lower.contains("recommande") -> {
        AiResponseBundle(
          text = "🎯 Voici mes meilleures recommandations pour vous :\n\n" +
              "1. « L'Intelligence Artificielle au service de la transformation en Afrique » par Awa Ndiaye (SEN AURA TECH) en Salle Plénière Teranga (09:30).\n\n" +
              "2. « Fintech 3.0 & Interopérabilité » par Mamadou Lamine Diallo (Wave) en Salle Innovation (11:15).\n\n" +
              "Souhaitez-vous que je les ajoute à votre agenda personnel ?",
          suggestions = listOf("Ajouter la conférence IA", "Voir tout le programme", "Où se trouve la Salle A ?"),
          entityId = "conf_1",
          entityType = "CONFERENCE"
        )
      }
      lower.contains("sen aura") || lower.contains("b24") || lower.contains("où se trouve") || lower.contains("stand") -> {
        AiResponseBundle(
          text = "📍 Le Stand SEN AURA TECH est situé au Stand B24 dans le Pavillon Principal (Zone B - Innovation Hub).\n\n" +
              "Vous y découvrirez des démonstrations en direct de la suite logicielle événementielle, des solutions de Cloud Souverain et l'IA AuraVision.\n\n" +
              "Je peux lancer le guidage interactif sur la Smart Map pour vous y amener immédiatement.",
          suggestions = listOf("Me guider vers Stand B24", "Voir les produits SEN AURA", "Prendre rendez-vous avec un expert"),
          entityId = "loc_1",
          entityType = "MAP"
        )
      }
      lower.contains("ia") || lower.contains("intelligence artificielle") || lower.contains("exposant") -> {
        AiResponseBundle(
          text = "🤖 Les principaux acteurs exposant dans l'Intelligence Artificielle et le Cloud sont :\n\n" +
              "• SEN AURA TECH (Stand B24) — IA générative & vision par ordinateur\n" +
              "• Teranga Cloud Solutions (Stand A12) — Infrastructures GPU & Datacenters souverains\n" +
              "• Dakar Robotics & IoT Labs (Stand D04) — IA embarquée et capteurs intelligents.",
          suggestions = listOf("Voir le profil de SEN AURA TECH", "Découvrir Teranga Cloud", "Lancer la Réalité Augmentée"),
          entityId = "exh_1",
          entityType = "EXHIBITOR"
        )
      }
      lower.contains("rencontrer") || lower.contains("networking") || lower.contains("qui") -> {
        AiResponseBundle(
          text = "🤝 Basé sur vos centres d'intérêt (IA, Fintech, Cloud), voici vos meilleures correspondances networking :\n\n" +
              "• Awa Ndiaye (CEO Tech Africa Innovations) — 95% compatible\n" +
              "• Moussa Diop (Directeur Innovation Sonatel) — 91% compatible\n" +
              "• Fatou Sow (Head of Product Wave) — 88% compatible\n\n" +
              "Vous pouvez leur envoyer une invitation ou planifier un entretien 1-to-1.",
          suggestions = listOf("Voir mon Smart Networking", "Prendre RDV avec Awa Ndiaye", "Afficher mon badge QR"),
          entityId = "net_1",
          entityType = "PROFILE"
        )
      }
      else -> {
        AiResponseBundle(
          text = "✨ Bien reçu ! SEN AURA EVENT regroupe 186 exposants, 56 conférences et plus de 12 480 professionnels connectés. Que souhaitez-vous explorer ?",
          suggestions = listOf(
            "Quelles conférences me recommandez-vous ?",
            "Où se trouve le Stand SEN AURA TECH ?",
            "Quels exposants travaillent dans l'IA ?",
            "Qui puis-je rencontrer en networking ?"
          )
        )
      }
    }
  }

  fun addPoints(pointsEarned: Int, reason: String) {
    _uiState.update { state ->
      val newPoints = state.userPoints + pointsEarned
      val updatedQuests = state.challengeQuests.map { q ->
        if (!q.isCompleted && q.points <= pointsEarned) q.copy(isCompleted = true) else q
      }
      val updatedLeaderboard = state.leaderboard.map { entry ->
        if (entry.isCurrentUser) entry.copy(points = newPoints) else entry
      }.sortedByDescending { it.points }.mapIndexed { index, entry ->
        entry.copy(rank = index + 1)
      }
      state.copy(
        userPoints = newPoints,
        userRank = updatedLeaderboard.firstOrNull { it.isCurrentUser }?.rank ?: state.userRank,
        challengeQuests = updatedQuests,
        leaderboard = updatedLeaderboard,
        toastMessage = "+$pointsEarned pts ! $reason"
      )
    }
  }

  fun setConferenceCategoryFilter(category: String) {
    _uiState.update { it.copy(selectedConferenceCategory = category) }
  }

  fun setConferenceSearchQuery(query: String) {
    _uiState.update { it.copy(conferenceSearchQuery = query) }
  }

  fun setExhibitorSearchQuery(query: String) {
    _uiState.update { it.copy(exhibitorSearchQuery = query) }
  }

  fun setNetworkingSearchQuery(query: String) {
    _uiState.update { it.copy(networkingSearchQuery = query) }
  }

  fun markNotificationAsRead(notificationId: String) {
    _uiState.update { state ->
      val updated = state.notifications.map { n ->
        if (n.id == notificationId) n.copy(isRead = true) else n
      }
      state.copy(notifications = updated)
    }
  }

  fun showToast(message: String) {
    _uiState.update { it.copy(toastMessage = message) }
  }

  fun clearToast() {
    _uiState.update { it.copy(toastMessage = null) }
  }
}
