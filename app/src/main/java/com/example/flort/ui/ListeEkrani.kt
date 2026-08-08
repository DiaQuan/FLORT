package com.example.flort.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flort.data.Partner
import com.example.flort.data.PartnerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListeEkrani(
    viewModel: PartnerViewModel,
    onEkleTiklandi: () -> Unit
) {
    val kisiler by viewModel.tumKisiler.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("💕 Flört") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onEkleTiklandi) {
                Icon(Icons.Default.Add, contentDescription = "Yeni kişi ekle")
            }
        }
    ) { padding ->
        if (kisiler.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Henüz kimse eklenmedi.\nSağ alttaki + butonuna dokun.", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(kisiler, key = { it.id }) { kisi ->
                    KisiKarti(kisi = kisi, onSil = { viewModel.sil(kisi) })
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun KisiKarti(kisi: Partner, onSil: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(14.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (kisi.fotoUri != null) {
                AsyncImage(
                    model = kisi.fotoUri,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp).clip(CircleShape)
                )
            } else {
                Surface(
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, contentDescription = null)
                    }
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("${kisi.isim} (${kisi.yas})", style = MaterialTheme.typography.titleMedium)
                if (kisi.sevdigiOzellikler.isNotBlank()) {
                    Text("Sevdikleri: ${kisi.sevdigiOzellikler}", style = MaterialTheme.typography.bodySmall, maxLines = 2)
                }
                if (kisi.hatirlanacakSeyler.isNotBlank()) {
                    Text("Not: ${kisi.hatirlanacakSeyler}", style = MaterialTheme.typography.bodySmall, maxLines = 2)
                }
            }

            IconButton(onClick = onSil) {
                Icon(Icons.Default.Delete, contentDescription = "Sil")
            }
        }
    }
}
