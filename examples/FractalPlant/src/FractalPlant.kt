
import LSystems.Parser.*
import LSystems.Turtle.PositionAngle
import LSystems.Turtle.Turtle
import java.awt.BasicStroke
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel

fun fractalPlant() : ArrayList<TurtleCommand>
{
    var rules       : ArrayList<Rule>       = ArrayList<Rule>()
    var meanings    : ArrayList<Meaning>    = ArrayList<Meaning>()
    val parser      : Parser                = Parser("X")

    /*  Production rules:
        X -> F-[[X]+X]+F[+FX]-X
        F -> FF
     */
    rules.add(Rule('X', "F-[[X]+X]+F[+FX]-X"))
    rules.add(Rule('F', "FF"))
    val generated : String = parser.generate(6, rules)

    /*  Meanings:
        F = Draw forward
        - = Turn left 25 degrees
        + = Turn right 25 degrees
        X = Nothing
        [ = Push current position to stack
        ] = Pop current position to stack
     */
    meanings.add(Meaning('F', TurtleCommand(0.0, 5.0, StackCommand(false, false))))
    meanings.add(Meaning('-', TurtleCommand(-25.0, 0.0, StackCommand(false, false))))
    meanings.add(Meaning('+', TurtleCommand(25.0, 0.0, StackCommand(false, false))))
    meanings.add(Meaning('[', TurtleCommand(0.0, 0.0, StackCommand(true, false))))
    meanings.add(Meaning(']', TurtleCommand(0.0, 0.0, StackCommand(false, true))))

    return parser.parse(meanings, generated)
}

fun main(args: Array<String>)
{
   val frame               = JFrame("Fractal Plant")
    val frame_height : Int  = 800
    val frame_width  : Int  = 1000
    frame.setSize(frame_width, frame_height)

    val turtle : Turtle = Turtle(300.0, 800.0)
    turtle.setAngle(-90.0)
    val commands = fractalPlant()
    for (command in commands)
    {
        val (angle, forward, stackCommand) = command
        val (push, pop) = stackCommand
        if(forward != 0.0)
        {
            turtle.forward(forward, frame)
        }
        else if(push)
        {
            val x = turtle.x
            val y = turtle.y
            turtle.stack.push(PositionAngle(x, y, turtle.getAngle()))
        }
        else  if(pop)
        {
            val (x, y, stackAngle) = turtle.stack.pop()
            turtle.x = x
            turtle.y = y
            turtle.setAngle(stackAngle)
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