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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_app.*
import com.example.kotlin_app.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val Home = "home"
const val Note = "note"
const val Error = "error"

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
        .background(Brush.horizontalGradient(colors = listOf(Color.Blue, Color.Red)))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = Color.White,
            fontSize = 22.sp,
            text = view.username.value)
        OutlinedButton(onClick = { view.logout() }) {
            Text(text = "Log out")
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Brush.horizontalGradient(colors = listOf(Color.Red, Color.Blue))),
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
        composable( route = Home ) { HomeView(navController) }
        composable( route = Note ) { NoteView() }
        composable( route = Error ) { ErrorView() }
    }
}

@Composable
fun HomeView(navController: NavHostController) {
    var input by remember { mutableStateOf("") }
    val fetchCity = viewModel<WeatherViewModel>()

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
                value = input,
                onValueChange = { input = it },
                label = { Text(text = "Search for a city ...") }
            )
            OutlinedButton(onClick = { /*fetchCity.getCityData()*/navController.navigate(Error) } ) {
                Text(text = "Search")
            }
        }
    }
}

@Composable
fun NoteView() {
    val fireStore = Firebase.firestore
    val user = FirebaseUser("Santeri", "Lehtonen")

    fireStore
        .collection("users")
        .document("0000")
        .set(user)

    var note by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFB5CA7A)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome " + user.fname + " " + user.lname, modifier = Modifier.padding(10.dp))
        Text(text = "Send notes ", modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            modifier = Modifier.padding(10.dp),
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Title") }
        )

        OutlinedTextField(
            modifier = Modifier.padding(10.dp),
            value = date,
            onValueChange = { date = it },
            label = { Text(text = "Date") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .height(200.dp),
            value = note,
            onValueChange = { note = it },
            label = { Text(text = "My notes") }
        )

        OutlinedButton(onClick = { fireStore.collection("notes").add(Notes(title, date, note)) } ) {
            Text(text = "Send note", modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun ErrorView () {
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Error",
            color = Color.Red,
            fontSize = 34.sp)
    }
}


// Login credentials: email: sane@sane.com, password: asd123
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