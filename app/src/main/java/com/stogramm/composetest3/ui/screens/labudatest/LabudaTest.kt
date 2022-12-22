package com.stogramm.composetest3.ui.screens.labudatest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ironmeddie.gridcomposable.VerticalGrid

@Composable
fun LabudaTest(){
    var color by remember {
        mutableStateOf(Color.Red)
    }
    LazyColumn(modifier = Modifier.fillMaxSize()){
        item { 
            Text(text = "LabudaTest LabudaTest LabudaTest LabudaTest")
        }
        item {
            VerticalGrid(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                , 4){
                for (i in 1 .. 5){
                    Box(
                        Modifier
                            .width(30.dp)
                            .height(150.dp)
                            .padding(5.dp)
                            .clickable { color = Color.White }
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.DarkGray), contentAlignment = Alignment.Center) {
                        Text(text = "element $i")
                    }
                }

                Box(modifier = Modifier
                    .size(20.dp)
                    .background(Color.Blue)
                    .clickable { })
                Box(modifier = Modifier
                    .size(35.dp)
                    .background(color))
                Box(modifier = Modifier
                    .size(500.dp)
                    .background(color))
                Box(modifier = Modifier
                    .size(46.dp)
                    .background(Color.Green))
                Box(modifier = Modifier
                    .size(46.dp)
                    .background(Color.Green))
                Box(modifier = Modifier
                    .size(46.dp)
                    .background(Color.Green))
                Box(modifier = Modifier
                    .size(46.dp)
                    .background(Color.Green))
                Box(modifier = Modifier
                    .size(76.dp)
                    .background(Color.Cyan))
                Box(modifier = Modifier
                    .size(86.dp)
                    .background(Color.Blue))
                Box(modifier = Modifier
                    .size(36.dp)
                    .background(Color.Black))


            }
        }
        item {
            Text(text = "LabudaTest LabudaTest LabudaTest LabudaTest")
        }
        item {
            Text(text = "LabudaTest LabudaTest LabudaTest LabudaTest")
        }
        item {
            Text(text = "LabudaTest LabudaTest LabudaTest LabudaTest")
        }
        item {
            Text(text = "LabudaTest LabudaTest LabudaTest LabudaTest")
        }
    }

}
