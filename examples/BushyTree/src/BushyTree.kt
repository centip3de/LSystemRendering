
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

fun bushyTree() : ArrayList<TurtleCommand>
{
    var rules : ArrayList<Rule> = ArrayList<Rule>()
    var meanings : ArrayList<Meaning> = ArrayList<Meaning>()
    val parser = Parser("F")

    /*  Production Rules:
        F -> FF-[-F+F+F]+[+F-F-F]
     */
    rules.add(Rule('F', "FF-[-F+F+F]+[+F-F-F]"))
    val generated = parser.generate(5, rules)

    /*  Meanings:
        F = Forward 5
        + = Right 25
        - = Left 25
        [ = Push
        ] = Pop
     */
    meanings.add(Meaning('F', TurtleCommand(0.0, 5.0, StackCommand(false, false))))
    meanings.add(Meaning('+', TurtleCommand(25.0, 0.0, StackCommand(false, false))))
    meanings.add(Meaning('-', TurtleCommand(-25.0, 0.0, StackCommand(false, false))))
    meanings.add(Meaning('[', TurtleCommand(0.0, 0.0, StackCommand(true, false))))
    meanings.add(Meaning(']', TurtleCommand(0.0, 0.0, StackCommand(false, true))))
    return parser.parse(meanings, generated)
}

fun main(args: Array<String>)
{
    /*  Entry point of the program.
        Args:
            args : Array<string> - All command line arguments.
        Returns:
           Unit
     */

    val frame               = JFrame("Bushy Tree")
    val render : Render     = Render(frame)
    val frame_height : Int  = 800
    val frame_width  : Int  = 1000
    frame.setSize(frame_width, frame_height)

    val turtle : Turtle = Turtle(300.0, 700.0)
    turtle.setAngle(-90.0)
    render.render(turtle, bushyTree())

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