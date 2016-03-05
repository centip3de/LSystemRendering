import java.util.*

data class TurtleCommand(val rotation: Double, val forward : Boolean)
/*  A data class for Turtle commands.
    Members:
        rotation : Double - The degree of rotation to apply
        forward : Boolean - Whether or not to move. (This should probably be an Double #FIXME)
 */

data class Rule(val replacementChar : Char, val replacementString : String)
/*  A data class for production rules.
    Members:
        replacementChar : Char      - The character to replace in the string
        replacementString : String  - What to replace the replacementChar with.
 */

data class Meaning(val char : Char, val turtleCommand : TurtleCommand)
/*  A data class for meanings.
    Members:
        char : Char                     - The char to assign the given meaning to
        turtleCommand : TurtleCommand   - The meaning (command) to assign the char to
 */

class Parser(var text : String)
{
    /*  A parser for L-Systems. It's able to take basic production rules and meanings, and translate them into Turtle commands,
        to be fed into a Turtle graphics library (hopefully the one included).
        Args:
            text : String - The starting production rule. Also used internally (probably shouldn't be #FIXME)
     */

    fun generate(iterations : Int, rules : ArrayList<Rule>)
    {
        /*  Generates the final string, as according to the production rules, and sets the internal variable 'text' to it.
            Args:
                iterations : Int        - How many times to apply the production rules
                rules : ArrayList<Rule> - A list of rules to apply.
            Returns:
                Unit
         */

        for (i in 1..iterations)
        {
            var newText : String = ""

            for(c in text)
            {
                var wasReplaced : Boolean = false

                // Apply each rule possible to the given character.
                for(rule in rules)
                {
                    val (char, string) = rule

                    if (c == char)
                    {
                        wasReplaced = true
                        newText += string
                    }
                }

                // If no rule was applied, then no rule exists for it, and it should be kept in the string.
                if(!wasReplaced)
                {
                    newText += c
                }
            }
            text = newText
        }
    }

    fun parse(meanings : ArrayList<Meaning>) : ArrayList<TurtleCommand>
    {
        /*  Parses the internal variable, 'text', into TurtleCommands as specified in the meanings for each character.
            Args:
                meanings : ArrayList<Meaning>   - A list of all meanings to apply
            Returns:
                An ArrayList<TurtleCommand> containing each TurtleCommand to execute, in order to render the L-System.
         */

        var array = ArrayList<TurtleCommand>()
        for(c in text)
        {
            for(meaning in meanings)
            {
                val char : Char             = meaning.char
                val command : TurtleCommand = meaning.turtleCommand
                if(c == char)
                {
                    array.add(command)
                }
            }
        }

        return array
    }
}

