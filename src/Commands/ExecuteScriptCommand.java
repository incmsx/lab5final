package Commands;

import Interfaces.ICommand;
import Managers.CommandInvoker;
import Managers.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

//Receiver
public class ExecuteScriptCommand implements ICommand {

    private static HashSet<String> handledScripts = new HashSet<>();
    @Override
    public void execute(String[] args)
    {
        String filePath;
        try
        {
            filePath = Parser.parseArgument(args);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Введите путь к файлу.");
            return;
        }

        File file = new File(filePath);

        handledScripts.add(filePath);
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(file);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String command = Parser.parseInputLine(line)[0];

                if(command.toLowerCase().equals("execute_script"))
                {
                    String filePathScript = Parser.parseInputLine(line)[1];
                    if(handledScripts.contains(filePathScript))
                    {
                        System.out.println("Обнаружена рекурсия, скипаем...");
                        continue;
                    }
                }

                CommandInvoker.execute(line);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Файл не найден...");
        }
        scanner.close();
        handledScripts.remove(filePath);
    }
}
