package com.example.flort.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flort.data.PartnerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkleEkrani(
    viewModel: PartnerViewModel,
    onKaydedildi: () -> Unit
) {
    val context = LocalContext.current
    var isim by remember { mutableStateOf("") }
    var yas by remember { mutableStateOf("") }
    var ozellikler by remember { mutableStateOf("") }
    var notlar by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    val fotoSecici = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // bazı sağlayıcılar kalıcı izin desteklemez, yine de göster
            }
            fotoUri = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("💕 Flört") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { fotoSecici.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (fotoUri != null) {
                    AsyncImage(
                        model = fotoUri,
                        contentDescription = "Seçilen fotoğraf",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Surface(
                        modifier = Modifier.size(120.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "Fotoğraf ekle"
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(6.dp))
            Text("Fotoğraf eklemek için dokun", style = MaterialTheme.typography.bodySmall)

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = isim,
                onValueChange = { isim = it },
                label = { Text("İsim") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = yas,
                onValueChange = { yas = it.filter { c -> c.isDigit() } },
                label = { Text("Yaş") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = ozellikler,
                onValueChange = { ozellikler = it },
                label = { Text("Sevdiği Özellikler") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = notlar,
                onValueChange = { notlar = it },
                label = { Text("Aklında Kalması Gerekenler") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (isim.isNotBlank()) {
                        viewModel.kaydet(
                            isim = isim,
                            yas = yas.toIntOrNull() ?: 0,
                            ozellikler = ozellikler,
                            notlar = notlar,
                            foto = fotoUri?.toString()
                        )
                        onKaydedildi()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kaydet")
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "💡 Unutmayın: Bilgileri kaydedip ilerde küçük bir jest yapmak ilişkinizi geliştirir.",
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 20.dp)
            )
        }
    }
}
