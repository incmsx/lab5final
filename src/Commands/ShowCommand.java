package Commands;

import Interfaces.ICommand;
import Managers.CollectionManager;

//Receiver
public class ShowCommand implements ICommand {

    @Override
    public void execute(String argument, String element)
    {
        CollectionManager.showCollection();
    }
}
