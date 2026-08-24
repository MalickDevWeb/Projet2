package com.example.data

import com.example.model.*

object SampleData {

  // Global KPIs
  const val TOTAL_VISITORS = "12 480"
  const val TOTAL_EXHIBITORS = "186"
  const val TOTAL_CONFERENCES = "56"
  const val TOTAL_MEETINGS = "1 240"
  const val TOTAL_INTERACTIONS = "4 820"
  const val TOTAL_QR_SCANS = "438"

  val initialConferences = listOf(
    Conference(
      id = "conf_1",
      title = "L'Intelligence Artificielle au service de la transformation économique en Afrique",
      description = "Comment les modèles souverains et l'IA générative accélèrent l'inclusion financière, la santé publique et l'agriculture intelligente sur le continent africain.",
      speakerName = "Awa Ndiaye",
      speakerRole = "Chief AI Officer",
      speakerCompany = "SEN AURA TECH",
      date = "26 Octobre 2026",
      time = "09:30 - 10:45",
      duration = "1h 15m",
      room = "Plénière Teranga (Salle A)",
      maxSeats = 350,
      reservedSeats = 312,
      isBookmarked = true,
      category = "Intelligence Artificielle",
      livePollId = "poll_1",
      topics = listOf("IA Souveraine", "Fintech", "Data Infrastructure", "Cloud Africain")
    ),
    Conference(
      id = "conf_2",
      title = "Fintech 3.0 & Interopérabilité des paiements transfrontaliers dans l'UEMOA",
      description = "Les nouveaux standards de paiement instantané, blockchain institutionnelle et API ouvertes pour fluidifier le commerce intra-africain.",
      speakerName = "Mamadou Lamine Diallo",
      speakerRole = "Directeur Général",
      speakerCompany = "Wave Mobile Money & Fintech Hub",
      date = "26 Octobre 2026",
      time = "11:15 - 12:30",
      duration = "1h 15m",
      room = "Auditorium Innovation (Salle B)",
      maxSeats = 200,
      reservedSeats = 185,
      isBookmarked = false,
      category = "Fintech & Finance",
      livePollId = "poll_2",
      topics = listOf("Paiements Numériques", "Interopérabilité", "Régulation BCEAO")
    ),
    Conference(
      id = "conf_3",
      title = "Cybersécurité & Souveraineté Numérique des Infrastructures Critiques",
      description = "Protection contre les cybermenaces émergentes, cryptographie post-quantique et conformité RGPD/CDP pour les gouvernements et banques.",
      speakerName = "Dr. Amadou Kane",
      speakerRole = "Expert en Cybersécurité",
      speakerCompany = "Gainde 2000 & CERT National",
      date = "26 Octobre 2026",
      time = "14:00 - 15:15",
      duration = "1h 15m",
      room = "Salle Cyber Defense (Salle C)",
      maxSeats = 150,
      reservedSeats = 142,
      isBookmarked = true,
      category = "Cybersécurité",
      topics = listOf("Zero Trust", "Cloud Souverain", "Résilience Opérationnelle")
    ),
    Conference(
      id = "conf_4",
      title = "Smart Cities & Énergie Verte : Construire les métropoles connectées de demain",
      description = "Déploiement de capteurs IoT, réseaux électriques intelligents (Smart Grid) et gestion prédictive des déchets pour Diamniadio et Dakar Smart City.",
      speakerName = "Fatou Kiné Diop",
      speakerRole = "Directrice Smart Infrastructure",
      speakerCompany = "Dakar Digital City",
      date = "27 Octobre 2026",
      time = "10:00 - 11:30",
      duration = "1h 30m",
      room = "Plénière Teranga (Salle A)",
      maxSeats = 350,
      reservedSeats = 240,
      isBookmarked = false,
      category = "Smart Cities & GreenTech",
      topics = listOf("IoT", "Énergie Solaire", "Mobilité Urbaine")
    ),
    Conference(
      id = "conf_5",
      title = "Venture Capital & Scale-Up : Attirer 500M$ de financements pour la Tech Africaine",
      description = "Table ronde avec les plus grands fonds d'investissement régionaux et internationaux sur les critères de valorisation et de sortie.",
      speakerName = "Sarah Johnson",
      speakerRole = "Managing Partner",
      speakerCompany = "AfriLabs Capital & Ventures",
      date = "27 Octobre 2026",
      time = "15:30 - 17:00",
      duration = "1h 30m",
      room = "Espace VIP & Business Lounge",
      maxSeats = 120,
      reservedSeats = 118,
      isBookmarked = false,
      category = "Investissement & Startups",
      topics = listOf("Venture Capital", "Scale-Up", "Levée de fonds")
    )
  )

  val initialExhibitors = listOf(
    Exhibitor(
      id = "exh_1",
      name = "SEN AURA TECH",
      standNumber = "Stand B24 (Pavillon Principal)",
      category = "Éditeur Logiciel, IA & Cloud",
      description = "Leader ouest-africain en intelligence artificielle appliquée, plateformes événementielles intelligentes, solutions cloud souveraines et cybersécurité avancée.",
      website = "https://senauratech.sn",
      contactEmail = "contact@senauratech.sn",
      phone = "+221 33 800 24 24",
      locationZone = "Zone B - Innovation Hub",
      visitsCount = 1480,
      qrScansCount = 520,
      leadsCount = 164,
      meetingsCount = 42,
      isFavorite = true,
      products = listOf(
        ProductItem("p1", "SEN AURA EVENT OS", "Plateforme SaaS", "Solution tout-en-un de digitalisation événementielle avec IA, Smart Map et gestion de flux en temps réel.", "Enterprise Edition"),
        ProductItem("p2", "AuraVision AI", "Intelligence Artificielle", "Moteur de reconnaissance et analyse vidéo pour la sécurité et la signalétique intelligente.", "API & On-Premise"),
        ProductItem("p3", "Sovereign Cloud Gateway", "Infrastructure", "Passerelle de stockage chiffré hybride conforme aux réglementations de données régionales.", "Sur mesure")
      )
    ),
    Exhibitor(
      id = "exh_2",
      name = "Teranga Cloud Solutions",
      standNumber = "Stand A12 (Zone Infrastructures)",
      category = "Datacenters & Cloud",
      description = "Hébergement haute disponibilité, architecture cloud native kubernetes et connectivité fibre ultra-rapide.",
      website = "https://terangacloud.com",
      contactEmail = "sales@terangacloud.com",
      phone = "+221 33 824 10 10",
      locationZone = "Zone A - Datacenter & Telecom",
      visitsCount = 980,
      qrScansCount = 310,
      leadsCount = 88,
      meetingsCount = 28,
      isFavorite = false,
      products = listOf(
        ProductItem("p4", "Teranga VPS Tier III", "Cloud Hosting", "Serveurs virtuels performants hébergés localement à Dakar.", "À partir de 45 000 FCFA/mois"),
        ProductItem("p5", "Disaster Recovery Suite", "Sécurité", "Plan de reprise d'activité automatisé avec réplication temps réel.", "Sur devis")
      )
    ),
    Exhibitor(
      id = "exh_3",
      name = "Wave Business & API Connect",
      standNumber = "Stand B08 (Zone Fintech)",
      category = "Paiements & Fintech",
      description = "Solutions d'encaissement et de décaissement instantanées pour commerçants, grandes entreprises et plateformes e-commerce.",
      website = "https://wave.com",
      contactEmail = "business-sn@wave.com",
      phone = "+221 77 000 00 00",
      locationZone = "Zone B - Innovation Hub",
      visitsCount = 1320,
      qrScansCount = 490,
      leadsCount = 145,
      meetingsCount = 38,
      isFavorite = true,
      products = listOf(
        ProductItem("p6", "Wave Checkout API", "Fintech", "Intégration du paiement sans frais cachés en 3 lignes de code.", "1% par transaction"),
        ProductItem("p7", "Terminaux QR Marchands", "Matériel & Logiciel", "TPE autonomes pour encaissement physique immédiat.", "Disponible")
      )
    ),
    Exhibitor(
      id = "exh_4",
      name = "Gainde 2000 Cyber Labs",
      standNumber = "Stand C15 (Zone Sécurité)",
      category = "Cybersécurité & Dématérialisation",
      description = "Expertise pionnière en signature électronique qualifiée, guichet unique du commerce et audit de sécurité des systèmes d'information.",
      website = "https://gainde2000.com",
      contactEmail = "info@gainde2000.sn",
      phone = "+221 33 859 60 00",
      locationZone = "Zone C - Sécurité & Gouvernance",
      visitsCount = 760,
      qrScansCount = 240,
      leadsCount = 65,
      meetingsCount = 19,
      isFavorite = false,
      products = listOf(
        ProductItem("p8", "Orbus Sign Pro", "Signature Électronique", "Signature légale certifiée pour contrats et actes officiels.", "Licence annuelle"),
        ProductItem("p9", "SOC as a Service", "Cybersécurité", "Surveillance continue 24/7 des réseaux d'entreprise.", "Sur mesure")
      )
    ),
    Exhibitor(
      id = "exh_5",
      name = "Dakar Robotic & IoT Labs",
      standNumber = "Stand D04 (Zone Robotique)",
      category = "IoT & Hardware",
      description = "Conception de capteurs environnementaux intelligents, drones agricoles et stations météo connectées pour l'Afrique de l'Ouest.",
      website = "https://dakar-robotics.sn",
      contactEmail = "contact@dakar-robotics.sn",
      phone = "+221 78 450 12 34",
      locationZone = "Zone B - Innovation Hub",
      visitsCount = 890,
      qrScansCount = 370,
      leadsCount = 92,
      meetingsCount = 22,
      isFavorite = false,
      products = listOf(
        ProductItem("p10", "AgriSense Drone V2", "AgriTech", "Drone de cartographie multispectrale des cultures agricoles.", "Sur commande"),
        ProductItem("p11", "Smart Metering LoRaWAN", "IoT", "Compteurs d'eau et d'énergie communicants longue portée.", "Unité / Flotte")
      )
    )
  )

  val initialNetworkingProfiles = listOf(
    NetworkingProfile(
      id = "net_1",
      name = "Awa Ndiaye",
      role = "CEO & Fondatrice",
      company = "Tech Africa Innovations",
      sector = "Intelligence Artificielle & Cloud",
      interests = listOf("IA", "Fintech", "Innovation", "Investissement"),
      compatibilityScore = 95,
      bio = "Passionnée par le déploiement de solutions d'IA éthiques pour le secteur bancaire et logistique en Afrique francophone.",
      isConnected = true,
      meetingStatus = AppointmentStatus.ACCEPTE
    ),
    NetworkingProfile(
      id = "net_2",
      name = "Moussa Diop",
      role = "Directeur de l'Innovation Numérique",
      company = "Sonatel / Orange MEA",
      sector = "Télécoms & Infrastructure",
      interests = listOf("5G", "IoT", "Data Centers", "Partenariats"),
      compatibilityScore = 91,
      bio = "En recherche de partenariats stratégiques avec des startups SaaS et créateurs de plateformes événementielles connectées.",
      isConnected = false,
      meetingStatus = null
    ),
    NetworkingProfile(
      id = "net_3",
      name = "Fatou Sow",
      role = "Head of Product & Growth",
      company = "Wave Mobile Money",
      sector = "Fintech & Mobile Banking",
      interests = listOf("UX Design", "Paiements Instantanés", "Scalabilité"),
      compatibilityScore = 88,
      bio = "Pilotage de la roadmap produit pour plus de 10 millions d'utilisateurs actifs. Ouverte aux échanges sur les API de facturation.",
      isConnected = false,
      meetingStatus = AppointmentStatus.DEMANDE
    ),
    NetworkingProfile(
      id = "net_4",
      name = "Jean-Marc Koffi",
      role = "Senior Investment Manager",
      company = "AfriVentures Fund",
      sector = "Venture Capital & Private Equity",
      interests = listOf("Seed Round", "SaaS B2B", "Financement Seed", "Scale-Up"),
      compatibilityScore = 84,
      bio = "Investisseur actif dans les solutions technologiques africaines en phase d'accélération (Series A & Seed).",
      isConnected = false,
      meetingStatus = null
    ),
    NetworkingProfile(
      id = "net_5",
      name = "Dr. Khady Ba",
      role = "Chercheure & Consultante IA",
      company = "Laboratoire d'Informatique & Systèmes",
      sector = "Recherche & Éducation",
      interests = listOf("Machine Learning", "Traitement du Langage Naturel (NLP)", "Langues Locales"),
      compatibilityScore = 82,
      bio = "Spécialiste des modèles linguistiques pour le Wolof, Bambara et Pulaar intégrés dans les assistants vocaux.",
      isConnected = false,
      meetingStatus = null
    )
  )

  val initialAppointments = listOf(
    Appointment(
      id = "apt_1",
      personName = "Awa Ndiaye",
      personRole = "CEO",
      personCompany = "Tech Africa Innovations",
      date = "26 Octobre 2026",
      time = "14:30",
      location = "Stand B24 — Espace VIP SEN AURA",
      status = AppointmentStatus.ACCEPTE,
      notes = "Discussion sur le partenariat stratégique pour l'intégration de la solution IA."
    ),
    Appointment(
      id = "apt_2",
      personName = "Fatou Sow",
      personRole = "Head of Product",
      personCompany = "Wave Mobile Money",
      date = "27 Octobre 2026",
      time = "11:00",
      location = "Salon B2B — Table 14",
      status = AppointmentStatus.DEMANDE,
      notes = "Présentation de la passerelle de billetterie et paiement d'événements."
    ),
    Appointment(
      id = "apt_3",
      personName = "Cheikh Tidiane Gaye",
      personRole = "DSI",
      personCompany = "Banque Régionale de Solidarité",
      date = "25 Octobre 2026",
      time = "16:00",
      location = "Stand A12 (Teranga Cloud)",
      status = AppointmentStatus.TERMINE,
      notes = "Bilan sur la migration cloud hybride."
    )
  )

  val mapLocations = listOf(
    MapLocation("loc_1", "Stand B24 — SEN AURA TECH", "B24", "STAND", 48f, 42f, "Espace démonstration IA & Écosystème Smart Event", AffluenceLevel.FORTE),
    MapLocation("loc_2", "Plénière Teranga (Salle A)", "SALLE_A", "SALLE", 20f, 25f, "Grande salle des conférences magistrales (350 places)", AffluenceLevel.FORTE),
    MapLocation("loc_3", "Auditorium Innovation (Salle B)", "SALLE_B", "SALLE", 78f, 25f, "Espace démonstrations tech et tables rondes (200 places)", AffluenceLevel.NORMALE),
    MapLocation("loc_4", "Stand A12 — Teranga Cloud", "A12", "STAND", 25f, 55f, "Pavillon Datacenters & Serveurs", AffluenceLevel.NORMALE),
    MapLocation("loc_5", "Stand B08 — Wave Connect", "B08", "STAND", 62f, 45f, "Kiosque Fintech & Démonstrations TPE", AffluenceLevel.FORTE),
    MapLocation("loc_6", "Stand C15 — Gainde Cyber", "C15", "STAND", 35f, 75f, "Espace cybersécurité & audit", AffluenceLevel.FAIBLE),
    MapLocation("loc_7", "Espace Restauration & Café", "RESTAU", "RESTAURATION", 85f, 65f, "Buffet traiteur, snacks et boissons chaudes", AffluenceLevel.FORTE),
    MapLocation("loc_8", "Espace VIP & Business Lounge", "VIP", "VIP", 50f, 15f, "Salons privés pour signatures de contrats et rendez-vous VIP", AffluenceLevel.FAIBLE),
    MapLocation("loc_9", "Entrée Principale & Accréditations", "ENTREE", "ENTREE", 50f, 92f, "Bornes d'enregistrement QR Connect et accueil", AffluenceLevel.NORMALE),
    MapLocation("loc_10", "Toilettes & Services", "WC", "TOILETTES", 12f, 88f, "Sanitaires et point d'eau", AffluenceLevel.FAIBLE)
  )

  val digitalScreens = listOf(
    DigitalScreen("scr_1", "Écran Principal (Hall Central)", "Hall d'accueil", "Programme en direct & Sponsors Platinium", "En ligne 🟢", "CONFÉRENCE EN COURS : L'IA en Afrique (Salle A)"),
    DigitalScreen("scr_2", "Écran Entrée & Accréditations", "Porte 1", "Instructions QR Connect & Plan interactif", "En ligne 🟢", "Badgez votre QR Code à la borne pour accès rapide"),
    DigitalScreen("scr_3", "Écran Zone A (Datacenters)", "Pavillon A", "Sponsors & Temps d'attente", "En ligne 🟢", "Affluence normale dans le couloir A"),
    DigitalScreen("scr_4", "Écran Zone B (Innovation Hub)", "Pavillon B", "Flash Networking & Top Exposants", "En ligne 🟢", "STAND B24 : Démonstration live SEN AURA à 15h00"),
    DigitalScreen("scr_5", "Écran Conférence Salle A", "Entrée Plénière", "Titre de la session & Intervenants", "En ligne 🟢", "Prochaine conférence à 14h00 : Cybersécurité")
  )

  val livePolls = listOf(
    LivePoll(
      id = "poll_1",
      conferenceTitle = "L'Intelligence Artificielle au service de la transformation économique en Afrique",
      question = "Selon vous, quel secteur africain bénéficiera le plus de l'adoption de l'IA d'ici 2030 ?",
      options = listOf(
        PollOption("opt_1", "Fintech & Inclusion bancaire", 142, 42),
        PollOption("opt_2", "Santé & Télémédecine prédictive", 98, 29),
        PollOption("opt_3", "Agriculture intelligente & Climat", 68, 20),
        PollOption("opt_4", "Éducation & Formation adaptative", 31, 9)
      ),
      totalVotes = 339,
      userVotedOptionId = "opt_1"
    ),
    LivePoll(
      id = "poll_2",
      conferenceTitle = "Fintech 3.0 & Interopérabilité des paiements transfrontaliers",
      question = "Quel est le principal frein à l'interopérabilité bancaire selon votre expérience ?",
      options = listOf(
        PollOption("opt_5", "Complexité réglementaire multi-pays", 110, 50),
        PollOption("opt_6", "Frais de commission d'interchange", 55, 25),
        PollOption("opt_7", "Compatibilité technique des APIs", 35, 16),
        PollOption("opt_8", "Confiance des consommateurs", 20, 9)
      ),
      totalVotes = 220,
      userVotedOptionId = null
    )
  )

  val challengeQuests = listOf(
    ChallengeQuest("q1", "Visiter le Stand SEN AURA TECH", "Scannez le QR Code officiel sur le Stand B24", 150, true),
    ChallengeQuest("q2", "Participer à une Conférence", "Ajoutez et assistez à au moins 2 conférences majeures", 200, true),
    ChallengeQuest("q3", "Créer 3 Connexions Smart Networking", "Échangez vos cartes digitales avec 3 participants", 150, true),
    ChallengeQuest("q4", "Répondre à un Sondage en Direct", "Donnez votre avis lors d'une session plénière", 100, true),
    ChallengeQuest("q5", "Tester la Réalité Augmentée", "Scannez un stand en mode AR pour voir le produit 3D", 120, false)
  )

  val leaderboard = listOf(
    LeaderboardEntry(1, "Awa Ndiaye", "Tech Africa Innovations", 980, false),
    LeaderboardEntry(2, "Moussa Diop", "Sonatel / Orange", 850, false),
    LeaderboardEntry(3, "Fatou Sow", "Wave Mobile Money", 720, false),
    LeaderboardEntry(4, "Papa Malick (Vous)", "SEN AURA TECH", 600, true),
    LeaderboardEntry(5, "Amadou Kane", "Gainde 2000", 540, false),
    LeaderboardEntry(6, "Sarah Johnson", "AfriLabs Capital", 490, false)
  )

  val notifications = listOf(
    AppNotification("notif_1", "CONFERENCE", "Conférence imminente dans 15 min", "« L'Intelligence Artificielle en Afrique » démarre en Salle A (Plénière Teranga).", "Il y a 10 min", false),
    AppNotification("notif_2", "RDV", "Rendez-vous confirmé avec Awa Ndiaye", "Rendez-vous à 14h30 au Stand B24 (Espace VIP SEN AURA).", "Il y a 45 min", false),
    AppNotification("notif_3", "NETWORKING", "12 Nouvelles recommandations de profils", "L'algorithme IA a identifié des correspondances clés avec vos intérêts Tech & Fintech.", "Il y a 2h", true),
    AppNotification("notif_4", "ORGANISATION", "Nouvelle session spéciale ajoutée", "Une table ronde d'investisseurs VIP a été ajoutée à l'Espace Business Lounge à 17h.", "Il y a 4h", true),
    AppNotification("notif_5", "ALERTE", "Affluence modérée au Hall Central", "Fluidité optimale aux comptoirs d'accueil et points de restauration.", "Il y a 5h", true)
  )

  val prospectLeads = listOf(
    ProspectLead("lead_1", "Papa Demba Ndao", "Banque Islamique du Sénégal", "Directeur de la Transformation Digitale", "p.ndao@bis-bank.sn", "+221 77 123 45 67", "Chaud 🔥", "Aujourd'hui à 11:24", "Très intéressé par la suite SEN AURA EVENT pour leur convention annuelle de 2 000 collaborateurs."),
    ProspectLead("lead_2", "Mariama Diallo", "Ministère de l'Économie Numérique", "Conseillère Technique Stratégie", "mariama.diallo@numerique.gouv.sn", "+221 78 987 65 43", "Chaud 🔥", "Aujourd'hui à 10:45", "Souhaite un devis d'infrastructure événementielle pour le sommet panafricain de 2027."),
    ProspectLead("lead_3", "Koffi Mensah", "Abidjan Tech Hub", "Managing Director", "koffi@abidjan-tech.ci", "+225 07 08 09 10", "Tiède ⏳", "Aujourd'hui à 09:50", "Demande de documentation API et compatibilité TPE Wave."),
    ProspectLead("lead_4", "Aminata Traoré", "Sahel AgriTech Ventures", "Responsable Partenariats", "aminata@sahelagri.ml", "+223 66 55 44 33", "Suivi 📋", "Hier à 16:30", "Intéressée par les capteurs IoT pour le monitoring des salons agricoles.")
  )

  val initialChatMessages = listOf(
    ChatMessage(
      id = "msg_1",
      isUser = false,
      text = "Bonjour 👋 Je suis SEN AURA AI, votre assistant événementiel intelligent propulsé par SEN AURA TECH. Comment puis-je vous accompagner aujourd'hui ?",
      timestamp = "Maintenant",
      suggestedActions = listOf(
        "Quelles conférences me recommandez-vous ?",
        "Où se trouve le Stand SEN AURA TECH ?",
        "Quels exposants travaillent dans l'IA ?",
        "Qui puis-je rencontrer en networking ?"
      )
    )
  )
}
