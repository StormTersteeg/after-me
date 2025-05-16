package com.onstart.after_me

import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.WindowInsets
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val view: WebView = WebView(this)

            // Enable JavaScript
            view.settings.javaScriptEnabled = true

            // Additional settings (optional)
            view.settings.cacheMode = WebSettings.LOAD_DEFAULT
            view.settings.loadsImagesAutomatically = true
            view.settings.mediaPlaybackRequiresUserGesture = false

            // Hide system bars
            window.insetsController?.hide(WindowInsets.Type.systemBars())

            // HTML string
            var htmlString = """
<style>
html, body {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
  user-select: none;
}

body {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-wrap: wrap;
  background-color: #3b28cc;
  transition: 0.5s;
}

tile {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #2667ff;
  width: 40%;
  height: 40%;
  border-radius: 5px;

  color: #add7f6;
  font-family: 'Arial', sans-serif;
  font-size: 25vw;
}


circle {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #3f8efc;
  width: 50vw;
  height: 50vw;
  border-radius: 50%;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  transition: 0.3s;

  color: #add7f6;
  font-family: 'Arial', sans-serif;
  font-size: 25vw;
}

@keyframes click {
  0% {
    transform: scale(0.9) rotate(1deg);
  }
  100% {
    transform: scale(1) rotate(0deg);
  }
}

.clicked {
  animation: click 0.3s, blink 0.3s;
}

@keyframes blink {
  0% {
    filter: brightness(1.2);
  }
  100% {
    filter: brightness(1);
  }
}

.blink {
  animation: blink 0.3s;
}

.disabled {
  pointer-events: none;
  background-color: #222;
  filter: grayscale(1) contrast(0.75);
}

</style>


<body>
  <audio id="Ɲ" src="https://snips.dontdalon.com/snippets/Storm/a.mp3" preload="auto"></audio>
  <audio id="ß" src="https://snips.dontdalon.com/snippets/Storm/b.mp3" preload="auto"></audio>
  <audio id="Ƶ" src="https://snips.dontdalon.com/snippets/Storm/c.mp3" preload="auto"></audio>
  <audio id="ƕ" src="https://snips.dontdalon.com/snippets/Storm/d.mp3" preload="auto"></audio>
  <audio id="chord" src="https://snips.dontdalon.com/snippets/Storm/chord.mp3" preload="auto"></audio>

  <tile ontouchstart="touch_available=true; tile_click(this)" onclick="if(!touch_available) tile_click(this)">Ɲ</tile>
  <tile ontouchstart="touch_available=true; tile_click(this)" onclick="if(!touch_available) tile_click(this)">ß</tile>
  <tile ontouchstart="touch_available=true; tile_click(this)" onclick="if(!touch_available) tile_click(this)">Ƶ</tile>
  <tile ontouchstart="touch_available=true; tile_click(this)" onclick="if(!touch_available) tile_click(this)">ƕ</tile>

  <circle ontouchstart="touch_available=true; start(this)" onclick="if(!touch_available) start(this)">▶</circle>
</body>


<script>
var next_amount_of_clicks = 0
var user_input = []
var correct_input = []
var computer_input = false
var touch_available = false

function tile_click(tile) {
  play_sound(tile.textContent);

  tile.classList.add('clicked');

  setTimeout(function() {
    tile.classList.remove('clicked');
  }, 300);

  if (computer_input) {
    return;
  }

  user_input.push(tile.textContent);

  if (user_input.length === correct_input.length) {
    var correct = true;

    for (var i = 0; i < correct_input.length; i++) {
      if (user_input[i] !== correct_input[i]) {
        correct = false;
        break;
      }
    }

    if (correct) {
      setTimeout(function() {
        next_click();
      }, 1000);
    } else {
      play_sound('wrong');
      var circle = document.querySelector('circle');
      circle.innerHTML = 'X';
      circle.classList.add('blink');

      setTimeout(function() {
        circle.classList.remove('blink');
      }, 300);

      setTimeout(function() {
        circle.innerHTML = '▶';
      }, 500);
    }
  }
}

function start(circle) {
  play_sound('chord')

  circle.innerHTML = 0
  circle.classList.add('blink')

  setTimeout(function() {
    circle.classList.remove('blink')
  }, 300)

  next_amount_of_clicks = 1
  correct_input = []
  user_input = []

  setTimeout(function() {
    next_click()
  }, 2000)
}

function next_click() {
  document.querySelector('circle').innerHTML = next_amount_of_clicks;
  document.body.classList.add('disabled');
  computer_input = true;

  var tiles = document.querySelectorAll('tile');
  var circle = document.querySelector('circle');

  tiles.forEach(tile => tile.style.pointerEvents = 'none');

  let displaySequence = () => {
    correct_input.forEach((tileLabel, index) => {
      setTimeout(() => {
        let tileToHighlight = Array.from(tiles).find(tile => tile.textContent === tileLabel);
        tileToHighlight.classList.add('clicked');
        play_sound(tileLabel);
        setTimeout(() => {
          tileToHighlight.classList.remove('clicked');
        }, 300);
      }, 900 * index);
    });
  };

  setTimeout(() => {
    if (next_amount_of_clicks > 0) {
      let randomTileIndex = Math.floor(Math.random() * tiles.length);

      while (correct_input.length > 1 && tiles[randomTileIndex].textContent === correct_input[correct_input.length - 1] && tiles[randomTileIndex].textContent === correct_input[correct_input.length - 2]) {
        randomTileIndex = Math.floor(Math.random() * tiles.length);
      }

      let random_tile = tiles[randomTileIndex];
      correct_input.push(random_tile.textContent);
    }

    displaySequence();

    setTimeout(() => {
      user_input = [];
      tiles.forEach(tile => tile.style.pointerEvents = 'auto');
      computer_input = false;
      document.body.classList.remove('disabled');
    }, 900 * correct_input.length + 500);
  }, 500);

  next_amount_of_clicks++;
}

function play_sound(element) {
  var soundMap = {
    Ɲ: "https://snips.dontdalon.com/snippets/Storm/a.mp3",
    ß: "https://snips.dontdalon.com/snippets/Storm/b.mp3",
    Ƶ: "https://snips.dontdalon.com/snippets/Storm/c.mp3",
    ƕ: "https://snips.dontdalon.com/snippets/Storm/d.mp3",
    chord: "https://snips.dontdalon.com/snippets/Storm/chord.mp3",
    wrong: "https://snips.dontdalon.com/snippets/Storm/wrong.mp3"
  };

  var audioURL = soundMap[element];
  if (audioURL) {
    var audio = new Audio(audioURL);
    audio.play();
  }
}

</script>
            """.trimIndent()

            htmlString = Base64.encodeToString(htmlString.toByteArray(), Base64.NO_PADDING)

            // Load HTML
            view.loadData(htmlString, "text/html", "base64")

            // Set WebView as the content view
            setContentView(view)
        }
    }
}
