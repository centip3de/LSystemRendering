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

fun buildGUI()
{
    /*  Builds the GUI, setting up the paintComponent for Turtle rendering.
        Args:
            None
        Returns:
            Unit
     */

    val frame               = JFrame("Test")
    val frame_height : Int  = 800
    val frame_width  : Int  = 1000
    frame.setSize(frame_width, frame_height)

    /* Turtle test
    val turtle : Turtle = Turtle(200.0, 500.0)
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
    */

    frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
    frame.show()
}

fun main(args: Array<String>)
{
    /*  Entry point of the program.
        Args:
            args : Array<string> - All command line arguments.
        Returns:
           Unit
     */

    buildGUI()
}