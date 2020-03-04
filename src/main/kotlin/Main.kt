import com.theapache64.quickemoji.core.EmojiFinder
import com.theapache64.quickemoji.models.Emoji
import com.theapache64.quickemoji.utils.SimpleCommandExecutor
import java.awt.Color
import java.awt.Font
import java.awt.GridLayout
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.awt.event.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import kotlin.system.exitProcess


class Main

// Main Frame
private const val WINDOW_WIDTH = 500
private const val WINDOW_HEIGHT = 600
private const val WINDOW_PADDING = 10
private const val MARGIN = 5

// Text Field
private const val TF_X = WINDOW_PADDING
private const val TF_Y = WINDOW_PADDING
private const val TF_WIDTH = WINDOW_WIDTH - (WINDOW_PADDING * 2)
private const val TF_HEIGHT = 30

// Emoji Panel
private const val EP_X = WINDOW_PADDING
private const val EP_Y = WINDOW_PADDING + TF_HEIGHT + MARGIN
private const val EP_WIDTH = WINDOW_WIDTH - (WINDOW_PADDING * 2)
private const val EP_HEIGHT = WINDOW_HEIGHT - TF_HEIGHT - WINDOW_PADDING - MARGIN


fun main(args: Array<String>) {
    val mainFrame = createMainFrame()
    val tfKeyword = createTextField()
    val emojisPanel = createEmojisPanel()

    // Adding to main frame
    mainFrame.run {
        add(tfKeyword)
        add(emojisPanel)
    }

    onTextUpdated(tfKeyword)


    mainFrame.isVisible = true
}

var jpEmojis: JPanel? = null
fun createEmojisPanel(): JScrollPane {

    // Reading emojis
    jpEmojis = JPanel().apply {
        layout = GridLayout(0, 3, 3, 2)
        setBounds(EP_X, EP_Y, EP_WIDTH, EP_HEIGHT)
    }

    return JScrollPane(jpEmojis).apply {
        setBounds(EP_X, EP_Y, EP_WIDTH, EP_HEIGHT)
        verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
    }
}

fun addEmojis(emojis: List<Emoji>) {
    for (emoji in emojis) {
        val bEmoji = createEmojiButton(emoji)
        jpEmojis!!.add(bEmoji)
    }
}

fun createEmojiButton(emoji: Emoji): JButton {
    return JButton(emoji.char).apply {
        font = Font("Twitter Color Emoji", Font.PLAIN, 20)
        setSize(10, 10)
        addActionListener {
            onEmojiSelected(emoji)
        }
    }
}

fun onEmojiSelected(emoji: Emoji) {
    // copy contents
    val selection = StringSelection(emoji.char)
    Toolkit.getDefaultToolkit().systemClipboard.setContents(
        selection,
        selection
    )

    mainFrame!!.isVisible = false
}


fun createTextField(): JTextField {
    return JTextField().apply {
        setBounds(TF_X, TF_Y, TF_WIDTH, TF_HEIGHT)
        background = Color.WHITE

        document.addDocumentListener(object : DocumentListener {
            override fun changedUpdate(p0: DocumentEvent?) {
                onTextUpdated(this@apply)
            }

            override fun insertUpdate(p0: DocumentEvent?) {
                onTextUpdated(this@apply)
            }

            override fun removeUpdate(p0: DocumentEvent?) {
                onTextUpdated(this@apply)
            }
        })
    }
}

fun onTextUpdated(jTextField: JTextField) {


    val keyword = jTextField.text.trim()
    println("Text updated $keyword")
    val emojis = if (keyword.isEmpty()) {
        EmojiFinder.all().subList(0, 10)
    } else {
        EmojiFinder.find(keyword)
    }

    jpEmojis!!.removeAll()
    if (emojis.isNotEmpty()) {
        addEmojis(emojis)
    }

    jpEmojis!!.updateUI()
}


/**
 * To create main frame
 */
var mainFrame: JFrame? = null

fun createMainFrame(): JFrame {
    mainFrame = JFrame("Quick Emoji").apply {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
        layout = null
        contentPane.background = Color.WHITE
        addComponentListener(object : ComponentListener {

            override fun componentMoved(p0: ComponentEvent?) {
            }

            override fun componentResized(p0: ComponentEvent?) {

            }

            override fun componentHidden(p0: ComponentEvent?) {
                println("Hidden")
                Thread {
                    println("Kill timer ready for 5 seconds")
                    Thread.sleep(5000)
                    println("Killed")
                    exitProcess(0)
                }.start()
            }

            override fun componentShown(p0: ComponentEvent?) {

            }
        })
    }
    return mainFrame!!
}

