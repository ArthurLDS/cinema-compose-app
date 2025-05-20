package com.angelisystems.cinemaapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

/**
 * Componente de texto reutilizável para padronizar os textos do aplicativo
 * com diferentes tamanhos e estilos
 */
@Composable
fun AppText(
    textResId: Int,
    modifier: Modifier = Modifier,
    size: TextSize = TextSize.Medium,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    formatArgs: Array<Any> = emptyArray()
) {
    val style = when (size) {
        TextSize.ExtraSmall -> MaterialTheme.typography.labelSmall
        TextSize.Small -> MaterialTheme.typography.bodySmall
        TextSize.Medium -> MaterialTheme.typography.bodyMedium
        TextSize.Large -> MaterialTheme.typography.bodyLarge
        TextSize.Headline -> MaterialTheme.typography.headlineSmall
        TextSize.Title -> MaterialTheme.typography.titleMedium
        TextSize.LargeTitle -> MaterialTheme.typography.titleLarge
    }

    val textColor = if (color == Color.Unspecified) {
        when (size) {
            TextSize.Headline, TextSize.Title, TextSize.LargeTitle -> 
                MaterialTheme.colorScheme.onSurface
            else -> 
                MaterialTheme.colorScheme.onBackground
        }
    } else {
        color
    }

    val text = if (formatArgs.isEmpty()) {
        stringResource(textResId)
    } else {
        stringResource(textResId, *formatArgs)
    }

    Text(
        text = text,
        style = style,
        color = textColor,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

/**
 * Variante do AppText que aceita string direta para casos específicos
 * onde não se usa stringResource (como parâmetros dinâmicos complexos)
 */
@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    size: TextSize = TextSize.Medium,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val style = when (size) {
        TextSize.ExtraSmall -> MaterialTheme.typography.labelSmall
        TextSize.Small -> MaterialTheme.typography.bodySmall
        TextSize.Medium -> MaterialTheme.typography.bodyMedium
        TextSize.Large -> MaterialTheme.typography.bodyLarge
        TextSize.Headline -> MaterialTheme.typography.headlineSmall
        TextSize.Title -> MaterialTheme.typography.titleMedium
        TextSize.LargeTitle -> MaterialTheme.typography.titleLarge
    }

    val textColor = if (color == Color.Unspecified) {
        when (size) {
            TextSize.Headline, TextSize.Title, TextSize.LargeTitle -> 
                MaterialTheme.colorScheme.onSurface
            else -> 
                MaterialTheme.colorScheme.onBackground
        }
    } else {
        color
    }

    Text(
        text = text,
        style = style,
        color = textColor,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}

enum class TextSize {
    ExtraSmall,  // Para textos muito pequenos como labels
    Small,       // Para textos secundários
    Medium,      // Tamanho padrão para a maioria dos textos
    Large,       // Para textos com destaque
    Headline,    // Para títulos de seções
    Title,       // Para títulos de telas ou componentes
    LargeTitle   // Para títulos principais
} 