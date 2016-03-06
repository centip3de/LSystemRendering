import LSystems.Parser.TurtleCommand
import LSystems.Turtle.PositionAngle
import LSystems.Turtle.Turtle
import java.util.*
import javax.swing.JFrame

class Render(val frame : JFrame)
{
    fun render(turtle : Turtle, commands : ArrayList<TurtleCommand>)
    {
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
    }
}

