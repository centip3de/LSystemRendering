
import LSystems.Parser.Meaning
import LSystems.Parser.Parser
import LSystems.Parser.Rule
import LSystems.Parser.TurtleCommand
import LSystems.Turtle.Turtle
import java.awt.BasicStroke
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel

fun sierpinskiTriangle() : ArrayList<TurtleCommand>
{
    /*  Draws the Sierpinski triangle.
        Args:
            None
        Returns:
            An ArrayList<TurtleCommand> filled with all the TurtleCommands needed to draw the Sierpinski Triangle.
     */

    var rules : ArrayList<Rule>         = ArrayList<Rule>()
    var meanings : ArrayList<Meaning>   = ArrayList<Meaning>()

    /*  Production rules:
        Start -> A
        A -> +B-A-B+
        B -> -A+B+A-
    */
    val parser = Parser("A")
    rules.add(Rule('A', "+B-A-B+"))
    rules.add(Rule('B', "-A+B+A-"))

    /*  Meanings:
        A = Draw forward
        B = Draw forward
        + = Turn left by 60 degrees
        - = Turn right by 60 degrees
    */
    meanings.add(Meaning('A', TurtleCommand(0.0, 2.0)))
    meanings.add(Meaning('B', TurtleCommand(0.0, 2.0)))
    meanings.add(Meaning('-', TurtleCommand(60.0, 0.0)))
    meanings.add(Meaning('+', TurtleCommand(-60.0, 0.0)))
    val generated : String = parser.generate(8, rules)

    return parser.parse(meanings, generated)
}

fun main(args: Array<String>)
{
    val frame               = JFrame("Sierpinski Triangle")
    val frame_height : Int  = 800
    val frame_width  : Int  = 1000
    frame.setSize(frame_width, frame_height)

    val turtle : Turtle = Turtle(200.0, 500.0)
    val commands = sierpinskiTriangle()
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