package com.nokhrin.singlelivedatatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nokhrin.singlelivedatatest.ui.theme.SingleLiveDataTestTheme
import com.siber.gsserver.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val observer = Observer<String> {
        RfLogger.d("DmNokhrin", "MainActivity::observerForever: message:$it")//DmNokhrin
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SingleLiveDataTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column() {
                        Button(
                            onClick = {
                                viewModel.resultLiveData.observe(this@MainActivity) {
                                    RfLogger.d("DmNokhrin", "MainActivity::${this@MainActivity}: first observer: message:$it")//DmNokhrin
                                }
                            }
                        ) {
                            Text("first observer")
                        }
                        Button(
                            onClick = {
                                viewModel.resultLiveData.observe(this@MainActivity) {
                                    RfLogger.d("DmNokhrin", "MainActivity::${this@MainActivity}: second observer: message:$it")//DmNokhrin
                                }
                            }
                        ) {
                            Text("second observer")
                        }
                        Button(
                            onClick = {
                                viewModel.resultLiveData.observeForever(observer)
                            }
                        ) {
                            Text("observe forever")
                        }
                        Button(
                            onClick = {
                                viewModel.resultLiveData.removeObserver(observer)
                            }
                        ) {
                            Text("remove forever")
                        }
                        Button(
                            onClick = {
                                viewModel.resultMutableLiveData.postValue("Message time: ${Date().time}")
                            }
                        ) {
                            Text("send message")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SingleLiveDataTestTheme {
        Greeting("Android")
    }
}