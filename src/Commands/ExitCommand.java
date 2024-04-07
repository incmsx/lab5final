package Commands;

import Interfaces.ICommand;

//Receiver
public class ExitCommand implements ICommand {

    @Override
    public void execute(String argument, String element)
    {
        System.exit(0);
    }

    @Override
    public String toString() {
        return "exit: завершить программу (без сохранения в файл)";
    }
}
