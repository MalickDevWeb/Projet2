package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.model.ChatMessage
import com.example.ui.theme.*
import com.example.viewmodel.EventUiState
import com.example.viewmodel.EventViewModel
import com.example.viewmodel.ScreenRoute
import kotlinx.coroutines.launch

@Composable
fun AiAssistantScreen(
  uiState: EventUiState,
  viewModel: EventViewModel
) {
  var userInput by remember { mutableStateOf("") }
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  val fastSuggestions = listOf(
    "Quelles conférences me recommandez-vous ?",
    "Où se trouve le Stand SEN AURA TECH ?",
    "Quels exposants travaillent dans l'IA ?",
    "Qui puis-je rencontrer en networking ?",
    "Que puis-je faire aujourd'hui ?"
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(LightBackground)
      .padding(bottom = 75.dp)
  ) {
    // Header
    Surface(
      color = NavyDeep,
      shadowElevation = 4.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(Brush.linearGradient(listOf(CyanAccent, TechBlue))),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.SmartToy,
              contentDescription = "SEN AURA AI",
              tint = Color.White,
              modifier = Modifier.size(24.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "SEN AURA AI",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Spacer(modifier = Modifier.width(6.dp))
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = SuccessGreen
              ) {
                Text(
                  text = "En ligne",
                  color = Color.White,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
            Text(
              text = "Assistant événementiel intelligent & ancré",
              style = MaterialTheme.typography.labelSmall,
              color = CyanAccent
            )
          }
        }

        IconButton(
          onClick = { viewModel.showToast("Historique de conversation réinitialisé") }
        ) {
          Icon(Icons.Default.Refresh, contentDescription = "Effacer", tint = TextSecondaryLight)
        }
      }
    }

    // Chat Message List
    LazyColumn(
      state = listState,
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(uiState.chatMessages) { message ->
        ChatBubble(
          message = message,
          onSuggestionClick = { suggestion ->
            viewModel.sendAiMessage(suggestion)
            coroutineScope.launch {
              listState.animateScrollToItem(uiState.chatMessages.size)
            }
          },
          onNavigateToEntity = { entityType, entityId ->
            when (entityType) {
              "CONFERENCE" -> viewModel.navigateTo(ScreenRoute.ConferenceDetail(entityId ?: "conf_1"))
              "EXHIBITOR" -> viewModel.navigateTo(ScreenRoute.ExhibitorDetail(entityId ?: "exh_1"))
              "MAP" -> viewModel.navigateTo(ScreenRoute.SmartMap)
              "PROFILE" -> viewModel.navigateTo(ScreenRoute.Networking)
            }
          }
        )
      }
    }

    // Quick Horizontal Suggestion Chips
    Surface(
      color = Color.White,
      shadowElevation = 2.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      ) {
        LazyRow(
          contentPadding = PaddingValues(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(fastSuggestions) { suggestion ->
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = LightSurfaceVariant,
              border = androidx.compose.foundation.BorderStroke(1.dp, TechBlue.copy(alpha = 0.3f)),
              modifier = Modifier
                .clickable {
                  viewModel.sendAiMessage(suggestion)
                  coroutineScope.launch {
                    listState.animateScrollToItem(uiState.chatMessages.size)
                  }
                }
                .testTag("ai_suggestion_chip")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Bolt, contentDescription = null, tint = TechBlue, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = suggestion,
                  style = MaterialTheme.typography.labelSmall,
                  color = NavyDeep,
                  fontWeight = FontWeight.Medium
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Field and Send Button
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            placeholder = { Text("Posez votre question à SEN AURA AI...", fontSize = 14.sp) },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("ai_chat_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = LightBackground,
              unfocusedContainerColor = LightBackground
            )
          )

          Spacer(modifier = Modifier.width(8.dp))

          IconButton(
            onClick = {
              if (userInput.isNotBlank()) {
                viewModel.sendAiMessage(userInput)
                userInput = ""
                coroutineScope.launch {
                  listState.animateScrollToItem(uiState.chatMessages.size)
                }
              }
            },
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(TechBlue)
              .testTag("ai_send_button")
          ) {
            Icon(
              imageVector = Icons.Default.Send,
              contentDescription = "Envoyer",
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ChatBubble(
  message: ChatMessage,
  onSuggestionClick: (String) -> Unit,
  onNavigateToEntity: (String?, String?) -> Unit
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
  ) {
    Row(
      verticalAlignment = Alignment.Bottom,
      horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
      if (!message.isUser) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(NavyDeep),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.SmartToy, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
      }

      Surface(
        shape = RoundedCornerShape(
          topStart = 16.dp,
          topEnd = 16.dp,
          bottomStart = if (message.isUser) 16.dp else 4.dp,
          bottomEnd = if (message.isUser) 4.dp else 16.dp
        ),
        color = if (message.isUser) TechBlue else Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier.widthIn(max = 300.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = message.text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (message.isUser) Color.White else NavyDeep,
            lineHeight = 20.sp
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = message.timestamp,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 9.sp,
            color = if (message.isUser) Color.White.copy(alpha = 0.7f) else TextSecondaryDark,
            modifier = Modifier.align(Alignment.End)
          )
        }
      }
    }

    // Suggested actions attached to AI message
    if (!message.isUser && message.suggestedActions.isNotEmpty()) {
      Spacer(modifier = Modifier.height(8.dp))
      Column(
        modifier = Modifier.padding(start = 40.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        message.suggestedActions.forEach { action ->
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = TechBlueContainer,
            modifier = Modifier
              .clickable { onSuggestionClick(action) }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.ArrowForward, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(12.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = action,
                style = MaterialTheme.typography.labelSmall,
                color = CyanGlow,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }

        if (message.linkedEntityType != null) {
          Spacer(modifier = Modifier.height(4.dp))
          Button(
            onClick = { onNavigateToEntity(message.linkedEntityType, message.linkedEntityId) },
            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = NavyDeep),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            modifier = Modifier.height(32.dp)
          ) {
            Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = when (message.linkedEntityType) {
                "CONFERENCE" -> "Ouvrir la Conférence"
                "EXHIBITOR" -> "Voir le Stand Exposant"
                "MAP" -> "Ouvrir la Smart Map"
                else -> "Voir les correspondances"
              },
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          }
        }
      }
    }
  }
}
