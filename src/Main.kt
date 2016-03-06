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

fun test() : ArrayList<TurtleCommand>
{
    val array : ArrayList<TurtleCommand> = ArrayList<TurtleCommand>()
    array.add(TurtleCommand(0.0, 0.0, StackCommand(false, false)))
    return array
}

fun buildGUI()
{
    /*  Builds the GUI, setting up the paintComponent for Turtle rendering.
        Args:
            None
        Returns:
            Unit
     */

    val frame               = JFrame("Test")
    val render : Render     = Render(frame)
    val frame_height : Int  = 800
    val frame_width  : Int  = 1000
    frame.setSize(frame_width, frame_height)

    val turtle : Turtle = Turtle(300.0, 700.0)
    val commands = test()
    render.render(turtle, commands)

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