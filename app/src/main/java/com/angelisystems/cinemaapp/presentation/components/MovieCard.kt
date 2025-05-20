package com.angelisystems.cinemaapp.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.angelisystems.cinemaapp.BuildConfig
import com.angelisystems.cinemaapp.R
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.model.RatingType
import com.angelisystems.cinemaapp.ui.theme.accentGold
import com.angelisystems.cinemaapp.ui.theme.heartFilled
import com.angelisystems.cinemaapp.ui.theme.ratingHigh
import com.angelisystems.cinemaapp.ui.theme.ratingLow
import com.angelisystems.cinemaapp.ui.theme.ratingMedium
import java.time.format.DateTimeFormatter

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "card_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium
            )
            .clickable(
                onClick = onClick,
                //onPressChange = { isPressed = it }
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                // Imagem do poster com placeholder
                AsyncImage(
                    model = movie.posterPath?.let { "${BuildConfig.IMAGE_BASE_URL}$it" },
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )

                // Gradiente sobre a imagem para melhorar legibilidade
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )

                // Ícone de favorito (coração) com fundo sombreado
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .size(36.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.45f),
                            shape = MaterialTheme.shapes.small
                        )
                        .clickable(onClick = onFavoriteClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) 
                            stringResource(R.string.remove_from_favorites) 
                        else 
                            stringResource(R.string.add_to_favorites),
                        tint = if (isFavorite) heartFilled else Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Badge de avaliação
                RatingBadge(
                    rating = movie.voteAverage,
                    ratingType = movie.ratingType(),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )

                // Título na parte inferior da imagem
                AppText(
                    text = movie.title,
                    size = TextSize.Title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                )
            }

            // Footer com data de lançamento
            movie.releaseDate?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppText(
                        text = it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        size = TextSize.Small,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Opcional: Ícone de favorito ou outro indicador
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        AppText(
                            text = if (movie.isRecommended()) stringResource(R.string.recommended) else "",
                            size = TextSize.ExtraSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RatingBadge(
    rating: Double,
    ratingType: RatingType,
    modifier: Modifier = Modifier
) {
    val formattedRating = String.format("%.1f", rating)
    val ratingColor = when(ratingType) {
        RatingType.RATING_HIGH -> ratingHigh
        RatingType.RATING_MEDIUM -> ratingMedium
        else -> ratingLow
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = ratingColor
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.rating),
                tint = accentGold,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            AppText(
                text = formattedRating,
                size = TextSize.ExtraSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
} 