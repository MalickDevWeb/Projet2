package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.UserRole
import com.example.ui.components.SenAuraWatermark
import com.example.ui.components.StatCard
import com.example.ui.theme.*
import com.example.viewmodel.EventViewModel
import com.example.viewmodel.ScreenRoute

@Composable
fun LandingScreen(viewModel: EventViewModel) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(NavyDeep),
    contentPadding = PaddingValues(bottom = 32.dp)
  ) {
    // HERO SECTION
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(NavyDeep, NavySurface)
            )
          )
          .padding(horizontal = 20.dp, vertical = 28.dp)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxWidth()
        ) {
          // Brand pill badge
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = TechBlueContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.5f))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = "🇸🇳", fontSize = 14.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "SEN AURA TECH INNOVATION",
                style = MaterialTheme.typography.labelSmall,
                color = CyanGlow,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = "SEN AURA EVENT",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center,
            letterSpacing = 1.5.sp
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "L'événement intelligent, connecté et interactif.",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = CyanAccent,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "« Découvrez les exposants, trouvez les conférences qui vous correspondent, développez votre réseau et profitez d'une expérience événementielle personnalisée. »",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(24.dp))

          // Primary Call to Actions
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Button(
              onClick = { viewModel.navigateTo(ScreenRoute.VisitorDashboard) },
              colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .testTag("explore_event_button")
            ) {
              Icon(
                imageVector = Icons.Default.Explore,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Explorer",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
              )
            }

            OutlinedButton(
              onClick = { viewModel.navigateTo(ScreenRoute.Register) },
              colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
              border = androidx.compose.foundation.BorderStroke(1.5.dp, TechBlueLight),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .testTag("register_event_button")
            ) {
              Icon(
                imageVector = Icons.Default.AppRegistration,
                contentDescription = null,
                tint = CyanAccent,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "S'inscrire",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          TextButton(
            onClick = { viewModel.navigateTo(ScreenRoute.Login) },
            modifier = Modifier.testTag("login_link_button")
          ) {
            Text(
              text = "Déjà inscrit ? Se connecter",
              color = TextSecondaryLight,
              style = MaterialTheme.typography.labelLarge
            )
          }
        }
      }
    }

    // LIVE EVENT STATS
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Text(
          text = "CHIFFRES CLÉS EN TEMPS RÉEL",
          style = MaterialTheme.typography.labelSmall,
          color = CyanAccent,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.2.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            title = "Visiteurs",
            value = SampleData.TOTAL_VISITORS,
            subtitle = "+24% vs 2025",
            icon = Icons.Default.People,
            accentColor = CyanAccent,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            title = "Exposants",
            value = SampleData.TOTAL_EXHIBITORS,
            subtitle = "Pavillons A-D",
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
            title = "Conférences",
            value = SampleData.TOTAL_CONFERENCES,
            subtitle = "3 Salles dédiées",
            icon = Icons.Default.Mic,
            accentColor = SuccessGreen,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            title = "Rendez-vous",
            value = SampleData.TOTAL_MEETINGS,
            subtitle = "B2B Qualifiés",
            icon = Icons.Default.Handshake,
            accentColor = AlertOrange,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // POURQUOI SEN AURA EVENT ?
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 16.dp)
      ) {
        Text(
          text = "POURQUOI SEN AURA EVENT ?",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Black,
          color = Color.White
        )
        Text(
          text = "Une infrastructure événementielle complète conçue pour l'excellence",
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondaryLight
        )

        Spacer(modifier = Modifier.height(14.dp))

        val features = listOf(
          FeatureItem("Intelligence Artificielle", "Recommandations ultra-ciblées de conférences, contacts et stands pertinents.", Icons.Default.SmartToy, CyanAccent),
          FeatureItem("Smart Networking", "Algorithme de compatibilité B2B et prise de rendez-vous en 1 clic.", Icons.Default.ConnectWithoutContact, TechBlueLight),
          FeatureItem("Smart Map & AR", "Guidage interactif pas-à-pas et réalité augmentée sur les stands.", Icons.Default.Map, SuccessGreen),
          FeatureItem("QR Connect", "Échange instantané de coordonnées et contrôle d'accès sécurisé.", Icons.Default.QrCodeScanner, AlertOrange),
          FeatureItem("Event Analytics", "Tableaux de bord de fréquentation et KPIs en temps réel pour organisateurs.", Icons.Default.QueryStats, CyanAccent),
          FeatureItem("Digital Signage", "Gestion centralisée et diffusion d'alertes sur écrans physiques.", Icons.Default.Tv, TechBlueLight)
        )

        features.forEach { feature ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 5.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = NavySurface)
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
                .background(feature.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = feature.icon,
                  contentDescription = feature.title,
                  tint = feature.color,
                  modifier = Modifier.size(24.dp)
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Text(
                  text = feature.title,
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = feature.desc,
                  style = MaterialTheme.typography.bodySmall,
                  color = TextSecondaryLight
                )
              }
            }
          }
        }
      }
    }

    // Role Fast Switch Demo Preview
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TechBlueContainer)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.SwitchAccount,
              contentDescription = null,
              tint = CyanAccent
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Mode Démonstration Multi-Profils",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Basculez instantanément dans la vue de chaque acteur de l'événement :",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.8f)
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = { viewModel.switchRole(UserRole.VISITEUR) },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
              modifier = Modifier.weight(1f)
            ) {
              Text("Visiteur", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Button(
              onClick = { viewModel.switchRole(UserRole.EXPOSANT) },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = TechBlue, contentColor = Color.White),
              modifier = Modifier.weight(1f)
            ) {
              Text("Exposant", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Button(
              onClick = { viewModel.switchRole(UserRole.ORGANISATEUR) },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen, contentColor = Color.White),
              modifier = Modifier.weight(1f)
            ) {
              Text("Organisateur", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        }
      }
    }

    // WATERMARK
    item {
      SenAuraWatermark()
    }
  }
}

private data class FeatureItem(
  val title: String,
  val desc: String,
  val icon: ImageVector,
  val color: Color
)

@Composable
fun LoginScreen(viewModel: EventViewModel) {
  var emailOrPhone by remember { mutableStateOf("papa.malick@senauratech.sn") }
  var password by remember { mutableStateOf("••••••••••••") }
  var isPasswordVisible by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .shadow(4.dp, RoundedCornerShape(20.dp)),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(bottom = 16.dp)
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(NavyDeep),
            contentAlignment = Alignment.Center
          ) {
            Text("SA", color = CyanAccent, fontWeight = FontWeight.Black, fontSize = 16.sp)
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Connexion",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = NavyDeep
            )
            Text(
              text = "Accédez à votre espace événementiel",
              style = MaterialTheme.typography.bodySmall,
              color = TextSecondaryDark
            )
          }
        }

        OutlinedTextField(
          value = emailOrPhone,
          onValueChange = { emailOrPhone = it },
          label = { Text("Email ou Téléphone") },
          leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = TechBlue) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("login_email_input"),
          shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          label = { Text("Mot de passe") },
          leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = TechBlue) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("login_password_input"),
          shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = { viewModel.navigateTo(ScreenRoute.VisitorDashboard) },
          colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("login_submit_button")
        ) {
          Text("Se connecter", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextButton(onClick = { viewModel.showToast("Lien de réinitialisation envoyé par SMS/Email") }) {
            Text("Mot de passe oublié ?", color = TextSecondaryDark, fontSize = 12.sp)
          }
          TextButton(onClick = { viewModel.navigateTo(ScreenRoute.Register) }) {
            Text("Créer un compte", color = TechBlue, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      }
    }
  }
}

@Composable
fun RegisterScreen(viewModel: EventViewModel) {
  var currentStep by remember { mutableIntStateOf(1) }

  // Step 1
  var fullName by remember { mutableStateOf("Papa Malick") }
  var email by remember { mutableStateOf("papa.malick@senauratech.sn") }
  var phone by remember { mutableStateOf("+221 77 800 24 24") }

  // Step 2
  var company by remember { mutableStateOf("SEN AURA TECH") }
  var jobTitle by remember { mutableStateOf("Lead Architect & AI Engineer") }

  // Step 3
  var selectedSector by remember { mutableStateOf("Intelligence Artificielle & Cloud") }

  // Step 4
  val allInterests = listOf("IA & Deep Learning", "Fintech & Mobile Money", "Cloud Souverain", "Cybersécurité", "Smart Cities", "IoT & Hardware", "Venture Capital")
  var selectedInterests by remember { mutableStateOf(setOf("IA & Deep Learning", "Cloud Souverain", "Fintech & Mobile Money")) }

  // Step 5
  val allObjectives = listOf(
    "Rencontrer des clients",
    "Trouver des partenaires",
    "Rechercher des investisseurs",
    "Découvrir des innovations",
    "Participer aux conférences"
  )
  var selectedObjective by remember { mutableStateOf("Trouver des partenaires") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Stepper header
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = NavyDeep)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Inscription — Étape $currentStep / 5",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
          Text(
            text = "${(currentStep * 20)}%",
            color = CyanAccent,
            fontWeight = FontWeight.Black
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
          progress = { currentStep / 5f },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = CyanAccent,
          trackColor = NavySurface
        )
      }
    }

    // Step contents
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .shadow(4.dp, RoundedCornerShape(20.dp)),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp)
      ) {
        when (currentStep) {
          1 -> {
            Text("Étape 1 : Identité & Contact", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NavyDeep)
            Text("Ces informations apparaîtront sur votre badge QR officiel.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
              value = fullName,
              onValueChange = { fullName = it },
              label = { Text("Nom et Prénom") },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
              value = email,
              onValueChange = { email = it },
              label = { Text("Adresse Email") },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
              value = phone,
              onValueChange = { phone = it },
              label = { Text("Numéro Téléphone / WhatsApp") },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp)
            )
          }

          2 -> {
            Text("Étape 2 : Organisation & Rôle", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NavyDeep)
            Text("Pour optimiser le matchmaking B2B.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
              value = company,
              onValueChange = { company = it },
              label = { Text("Entreprise / Organisation") },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
              value = jobTitle,
              onValueChange = { jobTitle = it },
              label = { Text("Fonction / Titre du poste") },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp)
            )
          }

          3 -> {
            Text("Étape 3 : Secteur d'activité", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NavyDeep)
            Text("Sélectionnez votre domaine d'expertise principal.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            Spacer(modifier = Modifier.height(16.dp))

            val sectors = listOf("Intelligence Artificielle & Cloud", "Fintech & Banques", "Télécoms & Réseaux", "Cybersécurité", "Smart Cities & Énergie", "Santé & MedTech", "Agriculture & AgriTech", "Gouvernement & Institutionnel")
            sectors.forEach { sector ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .background(if (selectedSector == sector) TechBlue.copy(alpha = 0.1f) else LightBackground)
                  .clickable { selectedSector = sector }
                  .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                RadioButton(selected = selectedSector == sector, onClick = { selectedSector = sector })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = sector, style = MaterialTheme.typography.bodyMedium, color = NavyDeep, fontWeight = if (selectedSector == sector) FontWeight.Bold else FontWeight.Normal)
              }
            }
          }

          4 -> {
            Text("Étape 4 : Vos centres d'intérêt", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NavyDeep)
            Text("Notre moteur de recommandation IA vous proposera les meilleures conférences.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            Spacer(modifier = Modifier.height(16.dp))

            allInterests.forEach { interest ->
              val isChecked = selectedInterests.contains(interest)
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .background(if (isChecked) CyanAccent.copy(alpha = 0.1f) else LightBackground)
                  .clickable {
                    selectedInterests = if (isChecked) selectedInterests - interest else selectedInterests + interest
                  }
                  .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Checkbox(checked = isChecked, onCheckedChange = { checked ->
                  selectedInterests = if (checked) selectedInterests + interest else selectedInterests - interest
                })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = interest, style = MaterialTheme.typography.bodyMedium, color = NavyDeep)
              }
            }
          }

          5 -> {
            Text("Étape 5 : Objectif de participation", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NavyDeep)
            Text("Quel est votre objectif prioritaire pour cet événement ?", style = MaterialTheme.typography.bodySmall, color = TextSecondaryDark)
            Spacer(modifier = Modifier.height(16.dp))

            allObjectives.forEach { obj ->
              val isSelected = selectedObjective == obj
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .background(if (isSelected) SuccessGreen.copy(alpha = 0.1f) else LightBackground)
                  .clickable { selectedObjective = obj }
                  .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                RadioButton(selected = isSelected, onClick = { selectedObjective = obj })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = obj, style = MaterialTheme.typography.bodyMedium, color = NavyDeep, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Navigation buttons
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      if (currentStep > 1) {
        OutlinedButton(
          onClick = { currentStep-- },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.weight(1f)
        ) {
          Text("Précédent")
        }
        Spacer(modifier = Modifier.width(12.dp))
      }

      Button(
        onClick = {
          if (currentStep < 5) {
            currentStep++
          } else {
            viewModel.addPoints(200, "Profil complété à 100%")
            viewModel.navigateTo(ScreenRoute.VisitorDashboard)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.weight(1f).height(48.dp)
      ) {
        Text(
          text = if (currentStep == 5) "Terminer & Accéder" else "Continuer",
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}
