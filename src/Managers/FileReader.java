package Managers;

import InputData.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;


public class FileReader {

    /**
     * Читает содержимое файла и парсит его в объекты Person для добавления в коллекцию.
     *
     * @param file файл для чтения
     */
    public static void readFile(File file)
    {

        Document document = null;
        try {
            document = buildDocument(file);
        } catch (SAXException | ParserConfigurationException e) {
            if (file.length() == 0) {
                System.out.println("Файл пустой");
                return;
            }
            System.out.println("Не удалось распарсить.");
            System.exit(1);
        } catch (IOException e) {
            if (!file.exists()) {
                System.out.println("Файла не существует.");
                System.exit(1);
            }
            if (!file.canRead()) {
                System.out.println("Нет доступа к файлу.");
                System.exit(1);
            }

        }

        if (document == null) {
            System.out.println("Укажите полный путь к файлу...");
            System.exit(0);
        }

        Node root = document.getFirstChild();

        NodeList rootChildren = root.getChildNodes();
        for (int i = 0; i < rootChildren.getLength(); i++) {
            if (rootChildren.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (rootChildren.item(i).getNodeName().equals("InputData.Person"))
            {
                Person person = Parser.parsePerson(rootChildren.item(i));
                CollectionManager.addToCollection(person);
                System.out.printf("Персонаж с  id = %s успешно добавлен в коллекцию! \n", person.getId());
            }
            else
            {
                findPerson(rootChildren.item(i));
            }
        }
    }

    public static void findPerson(Node node) {

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() != Node.ELEMENT_NODE)
            {
                continue;
            }

            if (nodeList.item(i).getNodeName().equals("InputData.Person"))
            {

                Person person = Parser.parsePerson(nodeList.item(i));
                CollectionManager.addToCollection(person);
                System.out.printf("Персонаж с  id = %s успешно добавлен в коллекцию! \n", person.getId());
            }
            else
            {
                findPerson(nodeList.item(i));
            }
//            while(!nodeList.item(i).getNodeName().toLowerCase().equals("person")
//                    || !nodeList.item(i).getNodeName().toLowerCase().equals("InputData.Person"))
//            {
//                try
//                {
//                    findPerson(nodeList.item(i));
//                }
//                catch (StackOverflowError e)
//                {
//                    System.out.println("Person не найден");
//                    System.exit(0);
//                }
//            }
//            if (nodeList.item(i).getNodeName().toLowerCase().equals("person")
//                    || nodeList.item(i).getNodeName().toLowerCase().equals("InputData.Person"))
//            {
//
//            }
//        }
        }
    }


        /**
         Создает XML-документ из файла.
         @param file файл для создания документа
         @return XML-документ
         @throws ParserConfigurationException если происходит ошибка конфигурации
         @throws IOException если происходит ошибка ввода-вывода
         @throws SAXException если происходит ошибка при разборе
         */
        private static Document buildDocument (File file) throws ParserConfigurationException, IOException, SAXException
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Document document = dbf.newDocumentBuilder().parse(new BufferedInputStream(new FileInputStream(file)));
            if (file.length() == 0)
            {
                System.out.println("Файл пустой.");
                return null;
            }
            return document;
        }
    }


