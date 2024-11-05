package ar.edu.unicen.seminario.ui.movies

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.common.Constants
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    title: String,
    image: String?,
    similarMovies: Boolean,
    onClick: () -> Unit
){
        Column(
            modifier = Modifier
                .clickable { onClick() }
                .width(150.dp)
                .padding(all = 4.dp)
        ) {
            Column {


                if(image == null){
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Black
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    GlideImage(
                        modifier = Modifier.size( width = 150.dp, height = 200.dp),
                        model = R.drawable.image,
                        contentDescription = title
                    )


                } else {
                    val img = Constants.IMAGE_URL + image
                    GlideImage(
                        modifier = Modifier.defaultMinSize(150.dp),
                        model = img,
                        contentDescription = title
                    )
                    if(!similarMovies) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.DarkGray.copy(alpha = 0.2f),
                                            Color.DarkGray.copy(alpha = 0.7f),
                                            Color.DarkGray.copy(alpha = 1f)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White)
                        }
                    }
                }

            }

        }
}


@Composable
@Preview (showBackground = true)
private fun MovieItemPreview(

){
    MovieItem(
        title = "Vicky Lopez",
        image = "https://image.tmdb.org/t/p/original/lqoMzCcZYEFK729d6qzt349fB4o.jpg",
        similarMovies = false,
        onClick = {}
    )
}