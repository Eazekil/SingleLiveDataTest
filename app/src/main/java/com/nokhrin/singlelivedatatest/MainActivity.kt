package com.nokhrin.singlelivedatatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.nokhrin.singlelivedatatest.ui.theme.SingleLiveDataTestTheme
import com.siber.gsserver.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val resultMutableLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val resultLiveData: LiveData<String> = resultMutableLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SingleLiveDataTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            RfLogger.d("DmNokhrin", "MainActivity::onCreate: send firstMessage")//DmNokhrin
            resultMutableLiveData.postValue("firstMessage")
            delay(1500)
            withContext(Dispatchers.Main) {
                RfLogger.d("DmNokhrin", "MainActivity::onCreate: observe after delay")//DmNokhrin
                resultLiveData.observe(this@MainActivity){
                    RfLogger.d("DmNokhrin", "MainActivity::onCreate: observe message:$it")//DmNokhrin
                }
            }
            delay(1500)
            RfLogger.d("DmNokhrin", "MainActivity::onCreate: post second message")//DmNokhrin
            resultMutableLiveData.postValue("secondMessage")
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    Button(onClick = {
        RfLogger.d("DmNokhrin", "onClick::Greeting: ")//DmNokhrin
    }) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SingleLiveDataTestTheme {
        Greeting("Android")
    }
}