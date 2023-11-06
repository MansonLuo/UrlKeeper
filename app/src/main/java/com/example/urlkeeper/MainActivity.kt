package com.example.urlkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.urlkeeper.ui.theme.UrlKeeperTheme
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UrlKeeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val vm = viewModel<UrlKeeperViewModel>()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            AppTopAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.showUrlAddingDialog()
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "add")
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(vm.urls) { index, item ->

                    if (index > 0) Divider()

                    PageItem(
                        page = item,
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Click not implemented yet.")
                            }
                        },
                        onLongClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Long click not implemented yet.")
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageItem(
    page: Page,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
fun Preview_PageItem() {
    val vm = viewModel<UrlKeeperViewModel>()
    PageItem(
        page = vm.urls.first(),
        onClick = {},
        onLongClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlAddingAlertDialog(
    expended: Boolean,
    onComform: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (expended) {
        var text by remember {
            mutableStateOf("")
        }

        AlertDialog(
            title = {
                Text(text = "Adding Url")
            },
            text = {
                TextField(value = text, onValueChange = { text = it })
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(
                    onClick = { onComform(text) }
                ) {
                    Text(text = "Add")
                }
            }
        )
    }
}

@Preview
@Composable
fun Preview_UrlAddingAlertDialog() {
    UrlAddingAlertDialog(
        true,
        {},
        {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageItemDetailModifyDialog(
    title: String,
    description: String,
    expanded: Boolean,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var innerTitle by remember {
        mutableStateOf(title)
    }
    var innerDescription by remember {
        mutableStateOf(description)
    }

    if (!expanded) {
        return
    }

    AlertDialog(
        title = {
            Text(text = "修改")
        },
        text = {
            TextField(
                value = innerTitle,
                onValueChange = { innerTitle = it },
                placeholder = {
                    Text(text = "Title...")
                }
            )
            TextField(
                value = innerDescription,
                onValueChange = { innerDescription = it },
                placeholder = {
                    Text(text = "Description...")
                }
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(innerTitle, innerDescription)
                }
            ) {
                Text(text = "Comfirm")
            }
        }
    )
}
