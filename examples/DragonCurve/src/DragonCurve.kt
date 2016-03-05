
import LSystems.Parser.Meaning
import LSystems.Parser.Parser
import LSystems.Parser.Rule
import java.awt.BasicStroke
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import LSystems.Parser.TurtleCommand
import LSystems.Turtle.Turtle

fun dragonCurve() : ArrayList<TurtleCommand>
{
    /*  Creates the Dragon Curve.
        Args:
            None
        Returns:
            An ArrayList<TurtleCommand> filled with all the TurtleCommands needed to draw the Dragon Curve.
     */

    var rules : ArrayList<Rule>         = ArrayList<Rule>()
    var meanings : ArrayList<Meaning>   = ArrayList<Meaning>()

    /*  Production rules:
        Start -> FX
        X -> X+YF+
        Y -> -FX-Y
    */
    val parser = Parser("FX")
    rules.add(Rule('X', "X+YF+"))
    rules.add(Rule('Y', "-FX-Y"))
    val generated : String = parser.generate(10, rules)

    /*  Meanings:
        F = Draw forward
        - = Rotate right 90 degrees
        + = Rotate left 90 degrees
    */
    meanings.add(Meaning('F', TurtleCommand(0.0, 10.0)))
    meanings.add(Meaning('-', TurtleCommand(90.0, 0.0)))
    meanings.add(Meaning('+', TurtleCommand(-90.0, 0.0)))

    //println("${generated}")
    return parser.parse(meanings, generated) //generated)
}

fun main(args: Array<String>)
{
    val frame               = JFrame("Dragon Curve")
    val frame_height : Int  = 800
    val frame_width  : Int  = 1000
    frame.setSize(frame_width, frame_height)

    val turtle : Turtle                 = Turtle(200.0, 500.0)
    val commands = dragonCurve()
    for (command in commands)
    {
        val (angle, forward) = command
        if(forward != 0.0)
        {
            turtle.forward(forward, frame)
        }
        else
        {
            turtle.turn(angle)
        }
    }

    frame.add(object : JPanel()
    {
        protected override fun paintComponent(g : Graphics?)
        {
            super<JPanel>.paintComponent(g)

            val line    = Line2D.Double()
            val g2d     = g as Graphics2D
            g2d.color   = turtle.getColor()
            g2d.stroke  = BasicStroke(turtle.getWidth())

            for(item in turtle.getActions())
            {
                line.setLine(item.posOne.x, item.posOne.y, item.posTwo.x, item.posTwo.y)
                g2d.draw(line)
            }
        }
    })

    frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
    frame.show()
}