import java.awt.Color
import javax.swing.JFrame
import java.util.*

data class Position(val x : Double, val y : Double)
data class Line(val posOne : Position, val posTwo : Position)

class Turtle(var x : Double, var y : Double)
{
    /*  A turtle graphics "engine".
        Args:
            x : Double - The x starting position
            y : Double - The y starting position
     */

    private var penDown : Boolean   = true
    private var angle : Double      = 0.0
    private var color : Color       = Color.BLACK
    private var width : Float       = 1f

    private var actions : ArrayList<Line> = ArrayList<Line>()

    fun getWidth() : Float
    {
        return width
    }

    fun setWidth(newWidth : Float)
    {
        width = newWidth
    }

    fun setColor(newColor : Color)
    {
        color = newColor
    }

    fun getColor() : Color
    {
        return color
    }

    fun getActions() : ArrayList<Line>
    {
        return actions
    }

    fun penDown()
    {
        penDown = true
    }

    fun penUp()
    {
        penDown = false
    }

    fun turn(degrees: Double)
    {
        /*  Turns the "turtle" however many degrees.
            Args:
                degrees : Double - The amount of degres to rotate
            Returns:
                Unit
         */

        angle += degrees
        angle = angle % 360
    }

    fun forward(pixels: Double, frame : JFrame)
    {
        /*  Moves the "turtle" however many pixels specified, and calls repaint on the passed in frame.
            Args:
                pixels : Double - The amount of pixels to move the "turtle"
                frame : JFrame  - The frame to call repaint on.
            Returns:
                Unit
         */
        val oldX  = x
        val oldY  = y
        x += pixels * Math.cos(Math.toRadians(angle))
        y += pixels * Math.sin(Math.toRadians(angle))
        if(penDown)
        {
            actions.add(Line(Position(oldX, oldY), Position(x, y)))
            frame.repaint()
        }
    }
}
