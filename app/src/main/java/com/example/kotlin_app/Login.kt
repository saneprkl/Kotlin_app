import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_app.LoggedInView
import com.example.kotlin_app.R

const val Home = "home"
const val Note = "note"

@Composable
fun MainView() {
    val view = viewModel<LoggedInView>()

    if(view.username.value.isEmpty()) {
        Login(view)
    } else {
        MainScaffold()
    }
}

@Composable
fun MainScaffold() {

    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(navController) },
        content = { ContentView(navController) }
    )
}

@Composable
fun TopBar() {
    val view = viewModel<LoggedInView>()

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Brush.horizontalGradient(colors = listOf( Color.Blue, Color.Red)))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = view.username.value)
        OutlinedButton(onClick = { view.logout() }) {
            Text(text = "Log out")
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Brush.horizontalGradient(colors = listOf( Color.Red, Color.Blue))),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically 
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_home_24),
            contentDescription = "home",
            modifier = Modifier.clickable { navController.navigate(Home) })
        Icon(
            painter = painterResource(id = R.drawable.ic_note),
            contentDescription = "note",
            modifier = Modifier.clickable { navController.navigate(Note) })
    }
}

@Composable
fun ContentView(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Home ) {
        composable( route = Home ) { HomeView() }
        composable( route = Note ) { NoteView() }
    }
}

@Composable
fun HomeView() {
    var inputPlaceholder by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = inputPlaceholder,
                onValueChange = { inputPlaceholder = it },
                label = { Text(text = "Search for a city ...") }
            )
            OutlinedButton(onClick = {  } ) {
                Text(text = "Search")
            }
        }
    }
}

@Composable
fun NoteView() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFC9AFE9))
    ) {

    }
}


// Login credentials: sane@sane.com, asd123
@Composable
fun Login(view: LoggedInView) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
            ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedButton(onClick = { view.login(email, password) } ) {
            Text(text = "Login")
        }


    }
}