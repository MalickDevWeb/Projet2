package com.example.model

enum class UserRole(val label: String, val iconName: String, val badgeColor: Long) {
  VISITEUR("Visiteur", "person", 0xFF00A6C7),
  EXPOSANT("Exposant", "storefront", 0xFF1769AA),
  ORGANISATEUR("Organisateur", "dashboard", 0xFF16A36A),
  ADMINISTRATEUR("Administrateur", "admin_panel_settings", 0xFF0B1F33),
  PARTENAIRE("Partenaire", "handshake", 0xFFF59E0B),
  CONFERENCIER("Conférencier", "mic", 0xFF7D5260)
}

enum class AppointmentStatus(val label: String, val color: Long) {
  DEMANDE("Demandé", 0xFFF59E0B),
  ACCEPTE("Accepté", 0xFF16A36A),
  REFUSE("Refusé", 0xFFDC3545),
  TERMINE("Terminé", 0xFF4A5D6E)
}

enum class AffluenceLevel(val label: String, val colorHex: Long, val emoji: String) {
  FAIBLE("Faible", 0xFF16A36A, "🟢"),
  NORMALE("Normale", 0xFFF59E0B, "🟠"),
  FORTE("Forte", 0xFFDC3545, "🔴")
}

data class Conference(
  val id: String,
  val title: String,
  val description: String,
  val speakerName: String,
  val speakerRole: String,
  val speakerCompany: String,
  val speakerAvatar: String = "",
  val date: String,
  val time: String,
  val duration: String,
  val room: String,
  val maxSeats: Int,
  var reservedSeats: Int,
  var isBookmarked: Boolean = false,
  val category: String,
  val livePollId: String? = null,
  val topics: List<String> = emptyList()
)

data class ProductItem(
  val id: String,
  val name: String,
  val category: String,
  val description: String,
  val priceOrTag: String,
  val tags: List<String> = emptyList()
)

data class Exhibitor(
  val id: String,
  val name: String,
  val logo: String = "",
  val standNumber: String,
  val category: String,
  val description: String,
  val website: String,
  val contactEmail: String,
  val phone: String,
  val products: List<ProductItem> = emptyList(),
  var visitsCount: Int = 1245,
  var qrScansCount: Int = 438,
  var leadsCount: Int = 126,
  var meetingsCount: Int = 34,
  var isFavorite: Boolean = false,
  val locationZone: String = "Zone B - Innovation Hub"
)

data class NetworkingProfile(
  val id: String,
  val name: String,
  val role: String,
  val company: String,
  val sector: String,
  val interests: List<String>,
  val compatibilityScore: Int,
  val bio: String,
  var isConnected: Boolean = false,
  var meetingStatus: AppointmentStatus? = null
)

data class Appointment(
  val id: String,
  val personName: String,
  val personRole: String,
  val personCompany: String,
  val date: String,
  val time: String,
  val location: String,
  var status: AppointmentStatus = AppointmentStatus.DEMANDE,
  val notes: String = ""
)

data class MapLocation(
  val id: String,
  val name: String,
  val code: String,
  val type: String, // STAND, SALLE, RESTAURATION, TOILETTES, VIP, ENTREE, SORTIE
  val x: Float, // percentage 0..100
  val y: Float, // percentage 0..100
  val description: String,
  val affluence: AffluenceLevel = AffluenceLevel.NORMALE
)

data class DigitalScreen(
  val id: String,
  val name: String,
  val location: String,
  var currentContent: String,
  var status: String = "En ligne 🟢",
  var activeBanner: String = "Bienvenue à SEN AURA EVENT 2026",
  var isAlertActive: Boolean = false
) {
  val isOnline: Boolean get() = status.contains("ligne")
}

data class PollOption(
  val id: String,
  val text: String,
  var votes: Int,
  var percentage: Int = 0
)

data class LivePoll(
  val id: String,
  val conferenceTitle: String,
  val question: String,
  val options: List<PollOption>,
  var totalVotes: Int = 0,
  var userVotedOptionId: String? = null
)

data class ChallengeQuest(
  val id: String,
  val title: String,
  val description: String,
  val points: Int,
  var isCompleted: Boolean = false
)

data class LeaderboardEntry(
  val rank: Int,
  val name: String,
  val company: String,
  val points: Int,
  val isCurrentUser: Boolean = false
)

data class AppNotification(
  val id: String,
  val type: String, // CONFERENCE, RDV, NETWORKING, ORGANISATION, ALERTE, SYSTEME
  val title: String,
  val message: String,
  val timeAgo: String = "10 min",
  var isRead: Boolean = false
)

data class ChatMessage(
  val id: String,
  val isUser: Boolean,
  val text: String,
  val timestamp: String,
  val suggestedActions: List<String> = emptyList(),
  val linkedEntityId: String? = null,
  val linkedEntityType: String? = null // CONFERENCE, EXHIBITOR, PROFILE, MAP
)

data class ProspectLead(
  val id: String,
  val name: String,
  val company: String,
  val role: String,
  val email: String,
  val phone: String,
  val interestLevel: String, // Chaud, Tiède, Suivi
  val scannedAt: String,
  val notes: String
)
