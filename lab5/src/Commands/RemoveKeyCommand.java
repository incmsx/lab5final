package Commands;

import Interfaces.ICommand;

//Receiver
public class RemoveKeyCommand implements ICommand {
    Terminal terminal;

    public RemoveKeyCommand(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void execute() {
        terminal.removeKey();
    }
}
